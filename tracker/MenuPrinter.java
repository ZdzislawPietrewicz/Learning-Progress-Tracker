package tracker;

public class MenuPrinter {
    public static void printMenu(String input) {
        boolean flag;
        switch (input) {
            case "null":
                Message.printMessage(Message.NO_INPUT);
                break;
            case "exit":
                Message.printMessage(Message.EXIT);
                System.exit(0);
                break;
            case "add students":
                Message.printMessage(Message.ADD_STUDENT);
                do {
                    flag = StudentManager.addStudent();
                }
                while (flag == true);
                Message.printMessage(Message.NUMBER_STUDENTS_ADDED_MESSAGE, StudentManager.getNumberStudentsAdded());
                break;
            case "back":
                Message.printMessage(Message.EXIT_PROGRAM);
                break;
            case "list":
                Message.printListStudents();
                break;
            case "add points":
                Message.printMessage(Message.ENTER_ID_AND_POINTS);
                do {
                    String[] data = InputParser.parseInput().split(" ");
                    flag = StudentManager.addPoints(data);
                } while (flag == true);
                break;
            case "find":
                Message.printMessage(Message.ENTER_ID_TO_FIND);
                do {
                    flag = StudentManager.findStudent();
                } while (flag == true);
                break;
            case "statistics":
                String courseName;
                Message.printMessage(Message.ENTER_NAME_OF_THE_COURSE);
                Message.printStatistics(Stats.getStatistics());
                do {
                    courseName = InputParser.parseInput();
                    if (!courseName.equals(StudentManager.BACK_TO_MAIN_MENU)) Message.printCourseStatistics(courseName);
                } while (!courseName.equals(StudentManager.BACK_TO_MAIN_MENU));
                break;
            case "get all records":
                Stats.getRecords().stream().forEach(System.out::println);
                break;
            case "notify":
                int numberStudentsWhoAccomplishedCourse = StudentManager.notifyStudent();
                System.out.printf("Total %d students have been notified\n", numberStudentsWhoAccomplishedCourse);
                break;

            default:
                Message.printMessage(Message.UNKNOWN);
                break;
        }
    }
}
