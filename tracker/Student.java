package tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Student {
    private String firstName;
    private String lastName;
    private String email;
    private List<Course> courseList = new ArrayList<>();

    public Student(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        courseList.add(new Course(GlobalVariables.JAVA_COURSE, 0, false, false));
        courseList.add(new Course(GlobalVariables.DSA_COURSE, 0, false, false));
        courseList.add(new Course(GlobalVariables.DATABASES_COURSES, 0, false, false));
        courseList.add(new Course(GlobalVariables.SPRING_COURSE, 0, false, false));

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", courseList=" + courseList +
                '}';
    }
}
