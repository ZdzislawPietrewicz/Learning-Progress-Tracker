package tracker;

import java.math.BigDecimal;

public class ResultToNotify extends Result {
    private String nameCourse;

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    public ResultToNotify(int studentId, int points, BigDecimal completed, String nameCourse) {
        super(studentId, points, completed);
        this.nameCourse = nameCourse;
    }
}
