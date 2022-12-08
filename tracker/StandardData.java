package tracker;


public class StandardData {
    public static void addStandardData() {

        Student student1 = StudentManager.createNewStudent("John Doe johnd@email.net".split(" "));
        Student student2 = StudentManager.createNewStudent("Jane Spark jspark@yahoo.com".split(" "));

        StudentManager.getStudentHashMap().put(10000, student1);
        StudentManager.getStudentHashMap().put(10001, student2);
        StudentManager.addPoints("10000 8 7 7 5".split(" "));
        StudentManager.addPoints("10000 7 6 9 7".split(" "));
        StudentManager.addPoints("10000 6 5 5 0".split(" "));
        StudentManager.addPoints("10001 8 0 8 6".split(" "));
        StudentManager.addPoints("10001 7 0 0 0".split(" "));
        StudentManager.addPoints("10001 9 0 0 5".split(" "));
    }
}
