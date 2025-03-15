import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;



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

class Student extends Person {
    private Long regNumber;
    private Project[] favoriteProjects= new Project[0];

    public Student(String name, LocalDate birthdate, Long regNumber) {
        this.name=name;
        this.birthdate=birthdate;
        this.regNumber = regNumber;
    }

    public void addProject(Project project) {
        if (!containsProject(project)) {
            Project[] newProjects = new Project[favoriteProjects.length + 1];
            for (int i = 0; i < favoriteProjects.length; i++) {
                newProjects[i] = favoriteProjects[i];
            }
            newProjects[favoriteProjects.length] = project;
            favoriteProjects = newProjects;
        }
    }

    private boolean containsProject(Project project) {
        for (Project p : favoriteProjects) {
            if (p.equals(project)) {
                return true;
            }
        }
        return false;
    }

    public Long getRegNumber() {
        return regNumber;
    }
    public Project[] getFavoriteProjects() {
        return favoriteProjects;
    }

    public void printProjects() {
        for (Project p : favoriteProjects) {
            System.out.print(p+" ");
            System.out.println();
        }
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
    protected Student[] students;
    protected Teacher[] teachers;
    protected Project[] projects;

    public Problem() {
        this.students = new Student[0];
        this.teachers = new Teacher[0];
        this.projects = new Project[0];
    }

    public Problem(Student[] students, Teacher[] teachers, Project[] projects) {
        this.students = students;
        this.teachers = teachers;
        this.projects = projects;
    }

    public Student[] getStudents() {
        return students;
    }
    public Teacher[] getTeachers() {
        return teachers;
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
    private boolean containsProject(Project project) {
        for (Project p : projects) {
            if (p.equals(project)) {
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

class Solution extends Problem {
    private boolean hallConditionSatisfied;
    private Project[] finalProjects;

    public Solution(Student[] students, Teacher[] teachers, Project[] projects) {
        this.students = students;
        this.teachers = teachers;
        this.projects = projects;
        this.finalProjects = new Project[students.length];
        this.hallConditionSatisfied = true;
    }
    public void greedy(int k) {
        if (k == students.length) {
            for (Project project : finalProjects) {
                System.out.print(project + " ");
            }
            System.out.println();
            return;
        }

        Project[] preferredProjects = students[k].getFavoriteProjects();
        for (Project project : preferredProjects) {
            if (!viz(project)) {
                finalProjects[k] = project;
                greedy(k + 1);
                finalProjects[k] = null;
            }
        }
    }
    private boolean viz(Project project) {
        for (Project p : finalProjects) {
            if (p != null && p.equals(project)) {
                return true;
            }
        }
        return false;
    }

    public boolean canAllocateProjects() {
        generateSubsets(0, new Student[students.length], 0);
        return hallConditionSatisfied;
    }

    private void generateSubsets(int index, Student[] currentSubset, int subsetSize) {
        if (!hallConditionSatisfied) return;

        if (index == students.length) {
            if (subsetSize > 0 && !checkHallCondition(currentSubset, subsetSize)) {
                hallConditionSatisfied = false;
            }
            return;
        }
        generateSubsets(index + 1, currentSubset, subsetSize);
        currentSubset[subsetSize] = students[index];
        generateSubsets(index + 1, currentSubset, subsetSize + 1);
        currentSubset[subsetSize] = null;
    }

    private boolean checkHallCondition(Student[] subset, int subsetSize) {
        Set<Project> subsetProjects = new HashSet<>();
        for (int i = 0; i < subsetSize; i++) {
            Student student = subset[i];
            for (Project project : student.getFavoriteProjects()) {
                subsetProjects.add(project);
            }
        }
        return subsetProjects.size() >= subsetSize;
    }
}

public class Main {
    public static void main(String[] args) {
        Problem problem = new Problem();

        Student s1 = new Student("Alice", LocalDate.of(2000, 1, 15), 123L);
        Student s2 = new Student("Bob", LocalDate.of(1999, 5, 22), 456L);

        s1.addProject(new Project("AI", Project.ProjectType.THEORETICAL));
        s1.addProject(new Project("LFAC", Project.ProjectType.PRACTICAL));
        s2.addProject(new Project("AI", Project.ProjectType.THEORETICAL));

        problem.addStudent(s1);
        problem.addStudent(s2);

        Teacher t1 = new Teacher("Ana", LocalDate.of(1990, 8, 30));
        Teacher t2 = new Teacher("Alex", LocalDate.of(1981, 9, 20));

        t1.addProject(new Project("AI", Project.ProjectType.THEORETICAL));
        t1.addProject(new Project("BD", Project.ProjectType.PRACTICAL));
        t2.addProject(new Project("LFAC", Project.ProjectType.PRACTICAL));

        problem.addTeacher(t1);
        problem.addTeacher(t2);

        Solution solution = new Solution(problem.getStudents(), problem.getTeachers(), problem.getProjects());
        boolean possible = solution.canAllocateProjects();
        System.out.println(possible);
    }
}