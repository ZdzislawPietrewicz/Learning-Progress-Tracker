package tracker;

public class RecordPoints {
    private int StudentId;
    private String nameCourse;
    private int points;

    public int getStudentId() {
        return StudentId;
    }

    public void setStudentId(int studentId) {
        StudentId = studentId;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public RecordPoints(int studentId, String nameCourse, int points) {
        StudentId = studentId;
        this.nameCourse = nameCourse;
        this.points = points;
    }
}
