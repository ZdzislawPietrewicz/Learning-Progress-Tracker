package tracker;

public class Course {
    private String name;
    private int points;
    private boolean notified;
    private boolean accomplished;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public boolean isAccomplished() {
        return accomplished;
    }

    public void setAccomplished(boolean accomplished) {
        this.accomplished = accomplished;
    }

    public Course(String name, int points, boolean notified, boolean accomplished) {
        this.name = name;
        this.points = points;
        this.notified = notified;
        this.accomplished = accomplished;
    }
}
