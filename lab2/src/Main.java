import java.time.LocalDate;
import java.util.Arrays;

abstract class Person {
    protected String name;
    protected LocalDate birthdate;

    public Person(String name, LocalDate birthdate) {
        this.name = name;
        this.birthdate = birthdate;
    }

    Person() {}

    public String getName() {
        return name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }
    @Override
    public String toString() {
        return name + "\t" + birthdate;
    }
}

class Student extends Person {
    private Long regNumber;

    public Student(String name, LocalDate birthdate, Long regNumber) {
        this.name=name;
        this.birthdate=birthdate;
        this.regNumber = regNumber;
    }

    public Long getRegNumber() {
        return regNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null ||  !(obj instanceof Student))
            return false;
        Student student = (Student) obj;
        return regNumber.equals(student.regNumber);
    }
}

class Project {
    public enum ProjectType { PRACTICAL, THEORETICAL }

    private String projectName;
    private ProjectType type;

    public Project(String projectName, ProjectType type) {
        this.projectName = projectName;
        this.type = type;
    }

    public String getProjectName() {
        return projectName;
    }

    public ProjectType getType() {
        return type;
    }

    @Override
    public String toString() {
        return projectName + "\t" + type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null ||  !(obj instanceof Project))
            return false;
        Project project = (Project) obj;
        return projectName.equals(project.projectName);
    }
}

class Teacher extends Person {
    private Project[] projects=new Project[0];

    public Teacher(String name, LocalDate birthdate) {
        this.name= name;
        this.birthdate = birthdate;
    }

    public Project[] getProjects() {
        return projects;
    }

    public void addProject(Project project) {
        if (!containsProject(project)) {
            Project[] newProjects = new Project[projects.length + 1];
            for (int i = 0; i < projects.length; i++) {
                newProjects[i] = projects[i];
            }
            newProjects[projects.length] = project;
            projects = newProjects;
        }
    }

    private boolean containsProject(Project project) {
        for (Project p : projects) {
            if (p.equals(project)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null ||  !(obj instanceof Teacher))
            return false;
        Teacher teacher = (Teacher) obj;
        return name.equals(teacher.name);
    }
}

class Problem {
    private Student[] students;
    private Teacher[] teachers;

    public Problem() {
        this.students = new Student[0];
        this.teachers = new Teacher[0];
    }

    public void addStudent(Student student) {
        if (!containsStudent(student)) {
            Student[] newStudents = new Student[students.length + 1];
            for (int i = 0; i < students.length; i++) {
                newStudents[i] = students[i];
            }
            newStudents[students.length] = student;
            students = newStudents;
        }
    }

    public void addTeacher(Teacher teacher) {
        if (!containsTeacher(teacher)) {
            Teacher[] newTeachers = new Teacher[teachers.length + 1];
            for (int i = 0; i < teachers.length; i++) {
                newTeachers[i] = teachers[i];
            }
            newTeachers[teachers.length] = teacher;
            teachers = newTeachers;
        }
    }

    private boolean containsStudent(Student student) {
        for (Student s : students) {
            if (s.equals(student)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsTeacher(Teacher teacher) {
        for (Teacher t : teachers) {
            if (t.equals(teacher)) {
                return true;
            }
        }
        return false;
    }

    public Person[] getAllPersons() {
        Person[] persons = new Person[students.length + teachers.length];
        int index = 0;
        for (int i = 0; i < students.length; i++) {
            persons[index++] = students[i];
        }
        for (int i = 0; i < teachers.length; i++) {
            persons[index++] = teachers[i];
        }
        return persons;
    }
}



public class Main {
    public static void main(String[] args) {
        Problem problem = new Problem();

        Student s1 = new Student("Alice", LocalDate.of(2000, 1, 15), 123L);
        Student s2 = new Student("Bob", LocalDate.of(1999, 5, 22), 456L);

        problem.addStudent(s1);
        problem.addStudent(s2);

        Teacher t1 = new Teacher("Ana",LocalDate.of(1990, 8, 30));
        Teacher t2 = new Teacher("Alex", LocalDate.of(1981, 9, 20));

        problem.addTeacher(t1);
        problem.addTeacher(t2);

        Project p1 = new Project("AI", Project.ProjectType.THEORETICAL);
        Project p2 = new Project("BD", Project.ProjectType.PRACTICAL);

        t1.addProject(p1);
        t1.addProject(p2);


        for (Person p : problem.getAllPersons()) {
            System.out.println(p);
        }
    }
}
