package org.example;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.lang.reflect.Type;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import javax.tools.*;
import org.objectweb.asm.*;

public class ReflectionTester {

    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java ReflectionTester <path-to-java|class|jar>");
            return;
        }

        File input = new File(args[0]);
        List<File> classFiles = new ArrayList<>();

        System.out.println("Received input: " + input.getAbsolutePath());

        if (input.isFile()) {
            System.out.println("Input is a file.");
            if (input.getName().endsWith(".jar")) {
                System.out.println("Analyzing JAR file...");
                analyzeJar(input);
                return;
            } else if (input.getName().endsWith(".java")) {
                compileJavaFile(input);
                File classFile = new File(input.getParentFile(), input.getName().replace(".java", ".class"));
                if (classFile.exists()) {
                    classFiles.add(classFile);
                    input = input.getParentFile();
                }
            } else if (input.getName().endsWith(".class")) {
                classFiles.add(input);
            }
        } else if (input.isDirectory()) {
            System.out.println("Scanning directory for class files...");
            Files.walk(input.toPath())
                    .filter(path -> path.toString().endsWith(".class"))
                    .forEach(path -> classFiles.add(path.toFile()));
        }

        URL[] urls = {input.toURI().toURL()};
        URLClassLoader classLoader = new URLClassLoader(urls);

        for (File classFile : classFiles) {
            String className = getClassNameFromFile(input, classFile);
            System.out.println("Processing class: " + className);
            if (className != null) {
                try {
                    byte[] originalBytes = Files.readAllBytes(classFile.toPath());
                    byte[] instrumentedBytes = instrumentClass(originalBytes, className.replace('.', '/'));
                    Class<?> clazz = defineInstrumentedClass(instrumentedBytes, className);
                    printClassPrototype(clazz);
                    runTests(clazz);
                } catch (Throwable e) {
                    System.err.println("Failed to load: " + className + " (" + e + ")");
                }
            }
        }

        printStats();
    }

    private static void compileJavaFile(File javaFile) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new IllegalStateException("Java compiler not available. Run with a JDK, not a JRE.");
        }
        System.out.println("Compiling Java file: " + javaFile.getName());
        int result = compiler.run(null, null, null, javaFile.getAbsolutePath());
        if (result != 0) throw new IOException("Compilation failed for: " + javaFile.getName());
    }

    private static void analyzeJar(File jarFile) throws IOException {
        URL jarURL = jarFile.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry e = entries.nextElement();
                if (e.getName().endsWith(".class")) {
                    String className = e.getName().replace("/", ".").replace(".class", "");
                    try (InputStream is = jar.getInputStream(e)) {
                        byte[] originalBytes = is.readAllBytes();
                        byte[] instrumentedBytes = instrumentClass(originalBytes, className.replace('.', '/'));
                        Class<?> clazz = defineInstrumentedClass(instrumentedBytes, className);
                        printClassPrototype(clazz);
                        runTests(clazz);
                    } catch (Throwable ex) {
                        System.err.println("Failed to load: " + className);
                    }
                }
            }
        }
    }

    private static byte[] instrumentClass(byte[] originalClass, String internalName) {
        ClassReader reader = new ClassReader(originalClass);
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM9, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new MethodVisitor(Opcodes.ASM9, mv) {
                    @Override
                    public void visitCode() {
                        super.visitCode();
                        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                        mv.visitLdcInsn(">> Entering method: " + name);
                        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",
                                "(Ljava/lang/String;)V", false);
                    }
                };
            }
        };
        reader.accept(visitor, 0);
        return writer.toByteArray();
    }

    private static Class<?> defineInstrumentedClass(byte[] bytes, String className) {
        return new ClassLoader() {
            public Class<?> load() {
                return defineClass(className, bytes, 0, bytes.length);
            }
        }.load();
    }

    private static String getClassNameFromFile(File root, File classFile) {
        try {
            if (root.isFile() && root.getName().endsWith(".class")) {
                root = root.toPath().getParent().getParent().toFile();
            }
            Path rootPath = root.toPath().toAbsolutePath();
            Path classPath = classFile.toPath().toAbsolutePath();
            Path relativePath = rootPath.relativize(classPath);
            return relativePath.toString().replace(File.separatorChar, '.').replaceAll("\\.class$", "");
        } catch (Exception e) {
            System.err.println("Error resolving class name: " + e);
            return null;
        }
    }

    private static void printClassPrototype(Class<?> clazz) {
        System.out.println("====== Class: " + clazz.getName() + " ======");
        int mods = clazz.getModifiers();
        System.out.print(Modifier.toString(mods) + " class " + clazz.getSimpleName());
        Type superclass = clazz.getGenericSuperclass();
        if (superclass != null && !superclass.getTypeName().equals("java.lang.Object")) {
            System.out.print(" extends " + superclass.getTypeName());
        }
        Type[] interfaces = clazz.getGenericInterfaces();
        if (interfaces.length > 0) {
            System.out.print(" implements ");
            for (int i = 0; i < interfaces.length; i++) {
                System.out.print(interfaces[i].getTypeName());
                if (i < interfaces.length - 1) System.out.print(", ");
            }
        }
        System.out.println(" {\n");

        for (Method method : clazz.getDeclaredMethods()) {
            System.out.println("  " + Modifier.toString(method.getModifiers()) +
                    " " + method.getReturnType().getSimpleName() +
                    " " + method.getName() + "(" +
                    Arrays.stream(method.getParameterTypes())
                            .map(Class::getSimpleName)
                            .reduce((a, b) -> a + ", " + b).orElse("") +
                    ");");
        }

        System.out.println("}\n");
    }

    private static void runTests(Class<?> clazz) {
        if (!Modifier.isPublic(clazz.getModifiers())) {
            System.out.println("Skipping non-public class: " + clazz.getName());
            return;
        }

        Object instance = null;

        try {
            Class<?> testAnnotationClass = clazz.getClassLoader().loadClass("org.example.Test");

            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent((Class<? extends Annotation>) testAnnotationClass)) {
                    totalTests++;
                    try {
                        if (!Modifier.isStatic(method.getModifiers())) {
                            if (instance == null) instance = clazz.getDeclaredConstructor().newInstance();
                        }
                        Object[] args = generateMockArgs(method.getParameterTypes());
                        method.setAccessible(true);
                        method.invoke(Modifier.isStatic(method.getModifiers()) ? null : instance, args);
                        System.out.println("✔ Passed: " + method.getName());
                        passedTests++;
                    } catch (Throwable e) {
                        System.out.println("✘ Failed: " + method.getName() + " - " + e.getCause());
                        failedTests++;
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Test annotation class not found: " + e.getMessage());
        }
    }

    private static Object[] generateMockArgs(Class<?>[] types) {
        Object[] args = new Object[types.length];
        for (int i = 0; i < types.length; i++) {
            Class<?> t = types[i];
            if (t == int.class || t == Integer.class) args[i] = 42;
            else if (t == boolean.class || t == Boolean.class) args[i] = true;
            else if (t == String.class) args[i] = "test";
            else if (t == double.class || t == Double.class) args[i] = 3.14;
            else if (t == float.class || t == Float.class) args[i] = 1.0f;
            else if (t == long.class || t == Long.class) args[i] = 100L;
            else args[i] = null;
        }
        return args;
    }

    private static void printStats() {
        System.out.println("\n==== Test Summary ====");
        System.out.println("Total tests: " + totalTests);
        System.out.println("Passed     : " + passedTests);
        System.out.println("Failed     : " + failedTests);
    }

    public @interface Test {}
}
