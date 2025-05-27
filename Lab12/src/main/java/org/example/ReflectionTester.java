package org.example;

import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;
import java.util.jar.*;
import java.nio.file.*;

public class ReflectionTester {

    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java ReflectionTester <path-to-class|dir|jar>");
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
            } else if (input.getName().endsWith(".class")) {
                System.out.println("Adding class file: " + input.getName());
                classFiles.add(input);
            }
        } else if (input.isDirectory()) {
            System.out.println("Scanning directory for class files...");
            Files.walk(input.toPath())
                    .filter(path -> path.toString().endsWith(".class"))
                    .forEach(path -> {
                        System.out.println("Found class file: " + path);
                        classFiles.add(path.toFile());
                    });
        }

        URL[] urls = {input.toURI().toURL()};
        URLClassLoader classLoader = new URLClassLoader(urls);

        for (File classFile : classFiles) {
            System.out.println("Processing file: " + classFile.getAbsolutePath());
            String className = getClassNameFromFile(input, classFile);
            System.out.println("Resolved class name: " + className);
            if (className != null) {
                try {
                    System.out.println("Loading class: " + className);
                    Class<?> clazz = classLoader.loadClass(className);
                    printClassPrototype(clazz);
                    runTests(clazz);
                } catch (Throwable e) {
                    System.err.println("Failed to load: " + className + " (" + e + ")");
                }
            }
        }

        printStats();
    }

    private static void analyzeJar(File jarFile) throws IOException, ClassNotFoundException {
        URL jarURL = jarFile.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[]{jarURL});
        try (JarFile jar = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry e = entries.nextElement();
                if (e.getName().endsWith(".class")) {
                    String className = e.getName().replace("/", ".").replace(".class", "");
                    try {
                        System.out.println("Loading class from JAR: " + className);
                        Class<?> clazz = classLoader.loadClass(className);
                        printClassPrototype(clazz);
                        runTests(clazz);
                    } catch (Throwable ex) {
                        System.err.println("Failed to load: " + className);
                    }
                }
            }
        }
    }
    private static String getClassNameFromFile(File root, File classFile) {
        try {
            // If root is a .class file, assume its root is 3 levels up (i.e., target/classes)
            if (root.isFile() && root.getName().endsWith(".class")) {
                root = root.toPath()
                        .getParent()     // myclass
                        .getParent()     // classes
                        .toFile();       // target/classes
            }

            Path rootPath = root.toPath().toAbsolutePath();
            Path classPath = classFile.toPath().toAbsolutePath();
            Path relativePath = rootPath.relativize(classPath);

            String className = relativePath.toString()
                    .replace(File.separatorChar, '.')
                    .replaceAll("\\.class$", "");

            System.out.println("Resolved class name: " + className);
            return className;

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
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class)) {
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
            else args[i] = null;  // unsupported
        }
        return args;
    }

    private static void printStats() {
        System.out.println("\n==== Test Summary ====");
        System.out.println("Total tests: " + totalTests);
        System.out.println("Passed     : " + passedTests);
        System.out.println("Failed     : " + failedTests);
    }
}
