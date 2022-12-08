package tracker;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;


public class Stats {


    private static List<RecordPoints> records = new ArrayList<>();

    public static List<RecordPoints> getRecords() {
        return records;
    }

    public static void setRecords(List<RecordPoints> records) {
        Stats.records = records;
    }

    public static HashMap<String, List<String>> getStatistics() {
        HashMap<String, List<String>> statisticsMap = findMostLeastPopularCourse();
        statisticsMap.putAll(findHighestLowestCourseActivity());
        statisticsMap.putAll(findHardestEasiestCourse());
        return statisticsMap;
    }

    private static Map<String, ? extends List<String>> findHardestEasiestCourse() {
        double javaAverageGrade = calcAverageGrade(GlobalVariables.JAVA_COURSE);
        double dsaAverageGrade = calcAverageGrade(GlobalVariables.DSA_COURSE);
        double databasesAverageGrade = calcAverageGrade(GlobalVariables.DATABASES_COURSES);
        double springAverageGrade = calcAverageGrade(GlobalVariables.SPRING_COURSE);
        HashMap<String, List<String>> hardestEasiestCourseMap = new HashMap<>();
        List<Double> allAverageGrades = Arrays.asList(javaAverageGrade, dsaAverageGrade, databasesAverageGrade, springAverageGrade);
        double maxAverageGrade = allAverageGrades.stream().max(Double::compareTo).get();
        double minAverageGrade = allAverageGrades.stream().min(Double::compareTo).get();
        List<String> hardestCourses = new ArrayList<>();
        List<String> easiestCourses = new ArrayList<>();
        if (javaAverageGrade == maxAverageGrade) easiestCourses.add(GlobalVariables.JAVA_COURSE);
        if (dsaAverageGrade == maxAverageGrade) easiestCourses.add(GlobalVariables.DSA_COURSE);
        if (databasesAverageGrade == maxAverageGrade) easiestCourses.add(GlobalVariables.DATABASES_COURSES);
        if (springAverageGrade == maxAverageGrade) easiestCourses.add(GlobalVariables.SPRING_COURSE);
        if (javaAverageGrade == minAverageGrade) hardestCourses.add(GlobalVariables.JAVA_COURSE);
        if (databasesAverageGrade == minAverageGrade) hardestCourses.add(GlobalVariables.DATABASES_COURSES);
        if (dsaAverageGrade == minAverageGrade) hardestCourses.add(GlobalVariables.DSA_COURSE);
        if (springAverageGrade == minAverageGrade) hardestCourses.add(GlobalVariables.SPRING_COURSE);
        hardestEasiestCourseMap.put("HARDEST_COURSE", hardestCourses);
        hardestEasiestCourseMap.put("EASIEST_COURSE", easiestCourses);
        return hardestEasiestCourseMap;
    }

    private static double calcAverageGrade(String courseName) {
        return records.stream()
                .filter(x -> x.getNameCourse().equals(courseName))
                .filter(x -> x.getPoints() > 0)
                .map(x -> x.getPoints())
                .mapToInt(Integer::intValue)
                .average()
                .orElse(Double.NaN);
    }

    private static HashMap<String, List<String>> findMostLeastPopularCourse() {
        int javaEnrollments = checkNumberEnrollmentsPerCourse(GlobalVariables.JAVA_COURSE);
        int dsaEnrollments = checkNumberEnrollmentsPerCourse(GlobalVariables.DSA_COURSE);
        int databasesEnrollments = checkNumberEnrollmentsPerCourse(GlobalVariables.DATABASES_COURSES);
        int springEnrolments = checkNumberEnrollmentsPerCourse(GlobalVariables.SPRING_COURSE);
        if (javaEnrollments == 0 & dsaEnrollments == 0 & databasesEnrollments == 0 & springEnrolments == 0) {
            return new HashMap<>(Map.ofEntries(
                    Map.entry("MOST_POPULAR", new ArrayList<>()),
                    Map.entry("LEAST_POPULAR", new ArrayList<>())
            ));
        }
        List<Integer> listAllEnrollments = Arrays.asList(javaEnrollments, dsaEnrollments, databasesEnrollments, springEnrolments);
        int maxValue = listAllEnrollments
                .stream().max(Integer::compare).get();
        int minValue = listAllEnrollments
                .stream().min(Integer::compare).get();
        List<String> mostPopular = new ArrayList<>();
        List<String> leastPopular = new ArrayList<>();
        if (maxValue == javaEnrollments) mostPopular.add("Java");
        if (maxValue == dsaEnrollments) mostPopular.add("DSA");
        if (maxValue == databasesEnrollments) mostPopular.add("Databases");
        if (maxValue == springEnrolments) mostPopular.add("Spring");
        if (minValue == javaEnrollments) leastPopular.add("Java");
        if (minValue == dsaEnrollments) leastPopular.add("DSA");
        if (minValue == databasesEnrollments) leastPopular.add("Databases");
        if (minValue == springEnrolments) leastPopular.add("Spring");
        HashMap<String, List<String>> mostLeastPopularMap = new HashMap<>();
        mostLeastPopularMap.put("MOST_POPULAR", mostPopular);
        for (String course : mostPopular) {
            leastPopular.remove(course);
        }
        mostLeastPopularMap.put("LEAST_POPULAR", leastPopular);
        return mostLeastPopularMap;
    }

    private static int checkNumberEnrollmentsPerCourse(String courseName) {
        return (int) StudentManager.getStudentHashMap().values().stream()
                .flatMap(x -> x.getCourseList().stream())
                .filter(course -> course.getName().equals(courseName) && course.getPoints() > 0)
                .count();
    }

    private static HashMap<String, List<String>> findHighestLowestCourseActivity() {
        int javaActivity = checkNumberOfActivities("Java");
        int dsaActivity = checkNumberOfActivities("DSA");
        int springActivity = checkNumberOfActivities("Spring");
        int databasesActivity = checkNumberOfActivities("Databases");
        if (javaActivity == 0 & dsaActivity == 0 & springActivity == 0 & databasesActivity == 0) {
            return new HashMap<>(Map.ofEntries(
                    Map.entry("HIGHEST_ACTIVITY", new ArrayList<>()),
                    Map.entry("LOWEST_ACTIVITY", new ArrayList<>())
            ));
        }
        List<Integer> listAllAcitivities = Arrays.asList(javaActivity, dsaActivity, springActivity, databasesActivity);
        int maxActivity = listAllAcitivities.stream().max(Integer::compare).get();
        int minActivity = listAllAcitivities.stream().min(Integer::compare).get();
        List<String> highestActivity = new ArrayList<>();
        List<String> lowestActivity = new ArrayList<>();
        if (maxActivity == javaActivity) highestActivity.add("Java");
        if (maxActivity == dsaActivity) highestActivity.add("DSA");
        if (maxActivity == databasesActivity) highestActivity.add("Databases");
        if (maxActivity == springActivity) highestActivity.add("Spring");
        if (minActivity == javaActivity) lowestActivity.add("Java");
        if (minActivity == dsaActivity) lowestActivity.add("DSA");
        if (minActivity == databasesActivity) lowestActivity.add("Databases");
        if (minActivity == springActivity) lowestActivity.add("Spring");
        for (String course : highestActivity) {
            lowestActivity.remove(course);
        }
        HashMap<String, List<String>> highestLowestActivity = new HashMap<>();
        highestLowestActivity.put("HIGHEST_ACTIVITY", highestActivity);
        highestLowestActivity.put("LOWEST_ACTIVITY", lowestActivity);
        return highestLowestActivity;
    }

    private static int checkNumberOfActivities(String courseName) {
        return (int) records.stream()
                .filter(record -> record.getNameCourse().equals(courseName) && record.getPoints() > 0)
                .count();
    }

    public static List<Result> getStatisticsPerCourse(String courseName) {
        List<Result> resultList = new ArrayList<>();
        int maxPoints = 0;
        int points = 0;
        switch (courseName) {
            case (GlobalVariables.JAVA_COURSE):
                maxPoints = GlobalVariables.maxPointsJava;
                break;
            case (GlobalVariables.DSA_COURSE):
                maxPoints = GlobalVariables.maxPointsDsa;
                break;
            case (GlobalVariables.SPRING_COURSE):
                maxPoints = GlobalVariables.maxPointsSpring;
                break;
            case (GlobalVariables.DATABASES_COURSES):
                maxPoints = GlobalVariables.maxPointsDatabases;
                break;
        }
        int finalMaxPoints = maxPoints;
        Map<Integer, Integer> statsPerCourse = records.stream()
                .filter(record -> record.getNameCourse().equals(courseName) && record.getPoints() > 0)
                .collect(Collectors.groupingBy(
                        RecordPoints::getStudentId,
                        Collectors.summingInt(RecordPoints::getPoints)
                ));
        statsPerCourse.forEach(
                (id, valPoints) -> {
                    resultList.add(new Result(id, valPoints, new BigDecimal(100 * (double) valPoints / (double) finalMaxPoints)));
                }
        );
        Collections.sort(resultList);
        return resultList;

    }
}



