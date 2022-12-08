package tracker;

import java.util.*;

public class Message {
    public static final String EXIT = "Bye!";
    public static final String NO_INPUT = "No input.";
    public static final String UNKNOWN = "Unknown command!";
    public static final String TITLE = "Learning Progress Tracker";
    public static final String ADD_STUDENT = "Enter student credentials or 'back' to return:";
    public static final String EXIT_PROGRAM = "Enter 'exit' to exit the program.";
    public static final String ENTER_ID_AND_POINTS = "Enter an id and points or 'back' to return:";
    public static final String ENTER_ID_TO_FIND = "Enter an id or 'back' to return:";
    public static final String NUMBER_STUDENTS_ADDED_MESSAGE = "Total %d students has been added.\n";
    public static final String ENTER_NAME_OF_THE_COURSE = "Type the name of a course to see details or 'back' to quit:";

    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static void printMessage(String message, int numberStudentsAdded) {
        System.out.printf(NUMBER_STUDENTS_ADDED_MESSAGE, numberStudentsAdded);
    }

    public static void printListStudents() {
        if (StudentManager.getStudentHashMap().isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("Students:");
            StudentManager.getStudentHashMap().keySet().stream()
                    .forEach(System.out::println);
        }
    }

    public static void printStatistics(HashMap<String, List<String>> statsMap) {
        System.out.printf("Most popular:%s\n", buildString(statsMap.get("MOST_POPULAR")));
        System.out.printf("Least popular:%s\n", buildString(statsMap.get("LEAST_POPULAR")));
        System.out.printf("Highest activity:%s\n", buildString(statsMap.get("HIGHEST_ACTIVITY")));
        System.out.printf("Lowest activity:%s\n", buildString(statsMap.get("LOWEST_ACTIVITY")));
        System.out.printf("Easiest course:%s\n", buildString(statsMap.get("EASIEST_COURSE")));
        System.out.printf("Hardest course:%s\n", buildString(statsMap.get("HARDEST_COURSE")));
    }

    private static String buildString(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append(" ");
            stringBuilder.append(list.get(i));
            stringBuilder.append(",");
        }
        if (list.isEmpty()) {
            stringBuilder.append("n/a");
        } else {
            int indexToDelete = stringBuilder.lastIndexOf(",");
            stringBuilder.deleteCharAt(indexToDelete);
        }
        return stringBuilder.toString();
    }

    public static void printCourseStatistics(String courseName) {
        List<Result> resultList = new ArrayList<>();
        switch (courseName.toLowerCase(Locale.ROOT)) {
            case "java":
                System.out.println(GlobalVariables.JAVA_COURSE);
                printColumns();
                resultList = Stats.getStatisticsPerCourse(GlobalVariables.JAVA_COURSE);
                printResult(resultList);
                break;
            case "dsa":
                System.out.println(GlobalVariables.DSA_COURSE);
                printColumns();
                resultList = Stats.getStatisticsPerCourse(GlobalVariables.DSA_COURSE);
                printResult(resultList);
                break;
            case "databases":
                System.out.println(GlobalVariables.DATABASES_COURSES);
                printColumns();
                resultList = Stats.getStatisticsPerCourse(GlobalVariables.DATABASES_COURSES);
                printResult(resultList);
                break;
            case "spring":
                System.out.println(GlobalVariables.SPRING_COURSE);
                printColumns();
                resultList = Stats.getStatisticsPerCourse(GlobalVariables.SPRING_COURSE);
                printResult(resultList);
                break;
            default:
                System.out.println("Unknown course");
                break;

        }

    }

    private static void printResult(List<Result> resultList) {
        for (Result result : resultList) {
            System.out.printf("%d \t %d \t %.1f%%  \n", result.getStudentId(), result.getPoints(), result.getCompleted());
        }
    }

    private static void printColumns() {
        System.out.printf("id\t\tpoints\tcompleted\n");
    }

    public static void sendEmail(List<ResultToNotify> accomplished) {
        for (ResultToNotify result : accomplished) {
            Student student = StudentManager.getStudentHashMap().get(result.getStudentId());
            System.out.printf("To: %s\n", student.getEmail());
            System.out.printf("Re: Your Learning Progress\n");
            System.out.printf("Hello, %s %s! You have accomplished our %s course\n", student.getFirstName(), student.getLastName(), result.getNameCourse());

        }

    }
}
