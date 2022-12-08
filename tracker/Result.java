package tracker;

import java.math.BigDecimal;

public class Result implements Comparable<Result> {
    private int studentId;
    private int points;
    private BigDecimal completed;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public BigDecimal getCompleted() {
        return completed;
    }

    public void setCompleted(BigDecimal completed) {
        this.completed = completed;
    }

    public Result(int studentId, int points, BigDecimal completed) {
        this.studentId = studentId;
        this.points = points;
        this.completed = completed;
    }

    public Result() {
    }

    @Override
    public int compareTo(Result otherResult) {
        if (points > otherResult.getPoints()) {
            return -1;
        } else if (points < otherResult.getPoints()) {
            return 1;
        } else {
            if (studentId > otherResult.getStudentId()) {
                return 1;
            } else return -1;
        }
    }
}
