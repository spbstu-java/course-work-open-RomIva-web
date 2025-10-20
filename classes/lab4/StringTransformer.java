package classes.lab4;

import java.util.List;
import java.util.stream.Collectors;

public class StringTransformer {
    public static List<String> transformStrings(List<String> strings) {
        return strings.stream()
                .map(s -> "_new_" + s.toUpperCase())
                .collect(Collectors.toList());
    }
}