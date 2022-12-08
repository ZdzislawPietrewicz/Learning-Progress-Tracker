package tracker;


import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class StudentManager {


    private static HashMap<Integer, Student> studentHashMap = new HashMap<>();
    private static int studentId = 10000;
    private static final int NUMBERS_OF_COURSES = 4;
    private static int numberStudentsAdded = 0;
    public static final String BACK_TO_MAIN_MENU = "back";


    public static HashMap<Integer, Student> getStudentHashMap() {
        return studentHashMap;
    }

    public static boolean addStudent() {
        String studentCredentials = InputParser.parseInput();
        if (studentCredentials.equals(BACK_TO_MAIN_MENU)) return false;
        String[] studentDataArray = studentCredentials.split(" ");
        if (studentDataArray.length < 3) {
            System.out.println("Incorrect credentials");
        } else {
            Student student = createNewStudent(studentDataArray);
            boolean areCredentialsValid = verifyStudentCredentials(student);
            if (areCredentialsValid) {
                numberStudentsAdded++;
                studentHashMap.put(studentId, student);
                studentId++;
                System.out.println("The student has been added");
            }
        }
        return true;
    }

    static Student createNewStudent(String[] studentDataArray) {
        String lastName = Arrays.stream(studentDataArray)
                .limit(studentDataArray.length - 1)
                .skip(1)
                .collect(Collectors.joining(" "));
        String firstName = studentDataArray[0];
        String email = studentDataArray[studentDataArray.length - 1];
        return new Student(firstName, lastName, email);
    }

    private static boolean verifyStudentCredentials(Student student) {
        String firstNameRegex = "^[A-Za-z]+['-]?[A-Za-z]+$";
        String lastNameRegex = "^[A-Za-z]+(['\\s-]?[A-Za-z]+)+$";
        String emailRegex = "[A-Za-z0-9]+([-.][A-Za-z0-9]+)?@[A-Za-z0-9]+\\.[A-Za-z0-9]+";

        if (verifyUniqueEmail(student.getEmail())) {
            System.out.println("This email is already taken");
            return false;
        }
        if (student.getFirstName().matches(firstNameRegex) && student.getLastName().matches(lastNameRegex) && student.getEmail().matches(emailRegex)) {
            return true;
        } else {
            if (!student.getFirstName().matches(firstNameRegex)) {
                System.out.println("Incorrect first name.");
            } else if (!student.getLastName().matches(lastNameRegex)) {
                System.out.println("Incorrect last name.");
            } else if (!student.getEmail().matches(emailRegex)) {
                System.out.println("Incorrect email.");
            }
        }
        return false;
    }

    private static boolean verifyUniqueEmail(String email) {
        return studentHashMap.values().stream()
                .anyMatch(x -> x.getEmail().equals(email));
    }

    static int getNumberStudentsAdded() {
        return numberStudentsAdded;
    }


    public static boolean addPoints(String[] data) {
        if (data[0].equals(BACK_TO_MAIN_MENU)) return false;
        Optional<Integer> keyStudent = getKeyStudent(data[0]);
        if (keyStudent.isEmpty())
            System.out.printf("No student is found for id=%s\n", data[0]);
        else {
            int counter = 0;
            try {
                counter = (int) Arrays.stream(data).skip(1)
                        .map(Integer::parseInt)
                        .filter(x -> x >= 0)
                        .count();
            } catch (NumberFormatException e) {
                System.out.println("Incorrect points format.");
            }

            if (NUMBERS_OF_COURSES == counter) {
                List<Integer> pointsList = Arrays.stream(data).map(Integer::parseInt).collect(Collectors.toList());

                Stats.getRecords().add(new RecordPoints(keyStudent.get(), GlobalVariables.JAVA_COURSE, pointsList.get(1)));
                Stats.getRecords().add(new RecordPoints(keyStudent.get(), GlobalVariables.DSA_COURSE, pointsList.get(2)));
                Stats.getRecords().add(new RecordPoints(keyStudent.get(), GlobalVariables.DATABASES_COURSES, pointsList.get(3)));
                Stats.getRecords().add(new RecordPoints(keyStudent.get(), GlobalVariables.SPRING_COURSE, pointsList.get(4)));


                Student student = studentHashMap.get(keyStudent.get());
                for (int i = 0; i < 4; i++) {
                    student.getCourseList().get(i).setPoints(student.getCourseList().get(i).getPoints() + Integer.parseInt(data[i + 1]));
                }
                studentHashMap.put(Integer.parseInt(data[0]), student);
                System.out.println("Points updated");
            } else System.out.println("Incorrect points format.");
        }
        return true;
    }

    private static Optional<Integer> getKeyStudent(String key) {
        Optional<Integer> keyStudent = Optional.empty();
        try {
            keyStudent = studentHashMap.keySet().stream()
                    .filter(x -> x == Integer.parseInt(key))
                    .findAny();
        } catch (NumberFormatException e) {

        }
        return keyStudent;
    }

    public static boolean findStudent() {
        String input = InputParser.parseInput();
        if (input.equals(BACK_TO_MAIN_MENU)) return false;
        Optional<Integer> keyStudent = getKeyStudent(input);
        keyStudent.ifPresentOrElse(x -> {
            Student student = studentHashMap.get(x);
            System.out.printf("%d points: Java=%d; DSA=%d, Databases=%d, Spring=%d\n"
                    , x
                    , student.getCourseList().get(0).getPoints()
                    , student.getCourseList().get(1).getPoints()
                    , student.getCourseList().get(2).getPoints()
                    , student.getCourseList().get(3).getPoints());
        }, () -> System.out.printf("No student if found for id=%s\n", input));
        return true;
    }

    public static int notifyStudent() {
        List<ResultToNotify> accomplished = new ArrayList<>();
        getStudentHashMap().forEach(
                (id, student) -> {
                    student.getCourseList().forEach(
                            (course -> {
                                if (((course.getName().equals(GlobalVariables.JAVA_COURSE) && (course.getPoints() >= GlobalVariables.maxPointsJava)) ||
                                        (course.getName().equals(GlobalVariables.DSA_COURSE) && (course.getPoints() >= GlobalVariables.maxPointsDsa)) ||
                                        (course.getName().equals(GlobalVariables.DATABASES_COURSES) && (course.getPoints() >= GlobalVariables.maxPointsDatabases)) ||
                                        (course.getName().equals(GlobalVariables.SPRING_COURSE) && (course.getPoints() >= GlobalVariables.maxPointsSpring))) &&
                                        course.isAccomplished() == false) {
                                    course.setAccomplished(true);

                                    accomplished.add(new ResultToNotify(id, course.getPoints(), new BigDecimal(100), course.getName()));
                                }
                            })
                    );

                }
        );
        int numberStudentsAccomplished = (int) accomplished.stream()
                .map(x -> x.getStudentId())
                .distinct()
                .count();
        Message.sendEmail(accomplished);
        return numberStudentsAccomplished;
    }

}
