package classes.lab4;

public class EvenSumCalculator {
    public static int calculateEvenSum(int[] numbers) {
        return java.util.Arrays.stream(numbers)
                .filter(n -> n % 2 == 0)
                .sum();
    }
}