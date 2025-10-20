package classes.lab4;

import java.util.List;

public class AverageCalculator {
    public static double calculateAverage(List<Integer> numbers) {
        return numbers.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }
}