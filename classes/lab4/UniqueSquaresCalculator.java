package classes.lab4;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UniqueSquaresCalculator {
    public static List<Integer> getUniqueSquares(List<Integer> numbers) {
        return numbers.stream()
                .filter(n -> Collections.frequency(numbers, n) == 1)
                .map(n -> n * n)
                .collect(Collectors.toList());
    }
}