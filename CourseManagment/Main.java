import java.util.ArrayList;
import java.util.Scanner;
// Using ArrayList to store multiple objects and Scanner to get user input.

public class Main {
    // Lists to hold all courses and grades
    private static ArrayList<Course> courses = new ArrayList<>();
    private static ArrayList<GradeEntry> grades = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    // Scanner reads what the user types in the console

    public static void main(String[] args) {
        // Program starts here
        displayMenu();
    }

    private static void displayMenu() {
        // Main menu loop
        while (true) {
            System.out.println("\nCourse Enrollment and Grade Management System");
            System.out.println("1. Add Course");
            System.out.println("2. Enroll Student");
            System.out.println("3. Assign Grade");
            System.out.println("4. Calculate Overall Grade");
            System.out.println("5. Admin Mode");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clears leftover newline

            switch (choice) {
                case 1:
                    addCourse();
                    break;
                case 2:
                    enrollStudent();
                    break;
                case 3:
                    assignGrade();
                    break;
                case 4:
                    calculateOverallGrade();
                    break;
                case 5:
                    adminMode();
                    break;
                case 6:
                    exit();
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    private static void addCourse() {
        // Lets the user add a new course
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();

        System.out.print("Enter course name: ");
        String courseName = scanner.nextLine();

        System.out.print("Enter maximum capacity: ");
        int maxCapacity = scanner.nextInt();
        scanner.nextLine(); // Clear newline

        Course course = new Course(courseCode, courseName, maxCapacity);
        courses.add(course);
        System.out.println("Course added successfully!");
    }

    private static void enrollStudent() {
        // Enrolls a student into a course
        if (courses.isEmpty()) {
            System.out.println("No courses available. Add a course first.");
            return;
        }

        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine();

        System.out.print("Enter student ID: ");
        int studentID = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\nAvailable Courses:");
        displayCourses();

        System.out.print("Select course index: ");
        int courseIndex = scanner.nextInt();
        scanner.nextLine();

        if (courseIndex < 0 || courseIndex >= courses.size()) {
            System.out.println("Invalid course index.");
            return;
        }

        Course course = courses.get(courseIndex);
        Student student = new Student(studentName, studentID);
        course.enrollStudent(student);
    }

    private static void assignGrade() {
        // Assigns a grade to a student for a specific course
        if (courses.isEmpty()) {
            System.out.println("No courses available. Add one first.");
            return;
        }

        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine();

        System.out.print("Enter student ID: ");
        int studentID = scanner.nextInt();
        scanner.nextLine();

        Student student = new Student(studentName, studentID);

        System.out.println("\nAvailable Courses:");
        displayCourses();
        System.out.print("Select course index: ");
        int courseIndex = scanner.nextInt();
        scanner.nextLine();

        if (courseIndex < 0 || courseIndex >= courses.size()) {
            System.out.println("Invalid course index.");
            return;
        }

        Course course = courses.get(courseIndex);

        System.out.print("Enter grade (0-100): ");
        int grade = scanner.nextInt();
        scanner.nextLine();

        GradeEntry gradeEntry = new GradeEntry(student, course, grade);
        grades.add(gradeEntry);

        System.out.println("Grade recorded successfully!");
    }

    private static void calculateOverallGrade() {
        // Calculates the student's average grade
        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine();

        System.out.print("Enter student ID: ");
        int studentID = scanner.nextInt();
        scanner.nextLine();

        Student student = new Student(studentName, studentID);

        int totalGrade = 0;
        int totalCourses = 0;

        for (GradeEntry entry : grades) {
            if (entry.getStudent().equals(student)) {
                totalGrade += entry.getGrade();
                totalCourses++;
            }
        }

        if (totalCourses > 0) {
            double average = (double) totalGrade / totalCourses;
            System.out.println(student.getName() + "'s average grade: " + average);
        } else {
            System.out.println("No grades found for " + student.getName());
        }
    }

    private static void adminMode() {
        // Shows all courses and their enrolled students
        System.out.println("\n=== Admin Mode ===");

        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }

        for (Course course : courses) {
            System.out.println("\nCourse: " + course.getName());
            System.out.println("Enrolled Students:");

            ArrayList<Student> enrolled = course.getEnrolledStudents();
            if (enrolled.isEmpty()) {
                System.out.println("  (none enrolled)");
            } else {
                for (Student s : enrolled) {
                    System.out.println("  - " + s.getName() + " (ID: " + s.getID() + ")");
                    boolean foundGrade = false;

                    for (GradeEntry g : grades) {
                        if (g.getStudent().equals(s) && g.getCourse().equals(course)) {
                            System.out.println("     Grade: " + g.getGrade());
                            foundGrade = true;
                            break;
                        }
                    }

                    if (!foundGrade) {
                        System.out.println("     Grade: not assigned yet");
                    }
                }
            }
        }
    }

    private static void displayCourses() {
        // Lists all courses with their index numbers
        for (int i = 0; i < courses.size(); i++) {
            System.out.println(i + ". " + courses.get(i).getName());
        }
    }

    private static void exit() {
        // Closes the scanner and exits
        System.out.println("Goodbye!");
        scanner.close();
        System.exit(0);
    }

    // ---------------- Inner Classes ----------------

    static class Student {
        private String name;
        private int ID;

        public Student(String name, int ID) {
            this.name = name;
            this.ID = ID;
        }

        public String getName() {
            return name;
        }

        public int getID() {
            return ID;
        }

        // Checks if two Student objects are the same person
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Student s = (Student) o;
            return ID == s.ID && name.equals(s.name);
        }

        @Override
        public int hashCode() {
            return 31 * name.hashCode() + ID;
        }
    }

    static class Course {
        private String courseCode;
        private String name;
        private int maxCapacity;
        private ArrayList<Student> enrolled = new ArrayList<>();

        public Course(String courseCode, String name, int maxCapacity) {
            this.courseCode = courseCode;
            this.name = name;
            this.maxCapacity = maxCapacity;
        }

        public String getName() {
            return name;
        }

        public void enrollStudent(Student student) {
            // Adds a student to the course if there’s room
            if (enrolled.size() < maxCapacity) {
                enrolled.add(student);
                System.out.println("Student enrolled in " + name);
            } else {
                System.out.println("Course is full. Couldn’t add " + student.getName());
            }
        }

        public ArrayList<Student> getEnrolledStudents() {
            return enrolled;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Course c = (Course) o;
            return courseCode.equals(c.courseCode) && name.equals(c.name);
        }

        @Override
        public int hashCode() {
            return 31 * courseCode.hashCode() + name.hashCode();
        }
    }

    static class GradeEntry {
        private Student student;
        private Course course;
        private int grade;

        public GradeEntry(Student student, Course course, int grade) {
            this.student = student;
            this.course = course;
            this.grade = grade;
        }

        public Student getStudent() {
            return student;
        }

        public Course getCourse() {
            return course;
        }

        public int getGrade() {
            return grade;
        }
    }
}
