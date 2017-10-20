package Repository;

import IO.OutputWriter;
import StaticData.ExceptionMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

/**
 * Created by teodor donchev on 10/18/2017.
 */
public class RepositoryFilters {

    public static void printFilteredStudents(
            HashMap<String, ArrayList<Integer>> courseData,
            String filterType, Integer numberOfStudents){

        Predicate<Double> filter = createFilter(filterType);

        filterAndTake(filter, courseData, numberOfStudents);
    }

    private static Predicate<Double> createFilter(String filterType) {
        switch (filterType){
            case "excellent":
                return  mark -> mark >= 5.0;

            case "average":
                return  mark -> mark < 5.0 && mark >= 3.5;

            case "poor":
                return  mark -> mark < 3.5;

            default: return null;
        }
    }

    private static void filterAndTake(
            Predicate<Double> filter,
            HashMap<String, ArrayList<Integer>> courseData,
            Integer numberOfStudents){

        if(filter == null){
            OutputWriter.displayException(ExceptionMessages.INVALID_FILTER);
            return;
        }

        int studentsCount = 0;
        for (String student : courseData.keySet()) {
            if(studentsCount == numberOfStudents){
                break;
            }

            ArrayList<Integer> marks = courseData.get(student);
            Double averageMark = marks.stream()
                    .mapToInt(Integer::valueOf)
                    .average()
                    .getAsDouble();

            Double percentageOfFulfilment = averageMark / 100;
            Double mark = percentageOfFulfilment * 4 + 2;

            if(filter.test(mark)){
                OutputWriter.printStudent(student, marks);
                studentsCount++;
            }
        }
    }
}
