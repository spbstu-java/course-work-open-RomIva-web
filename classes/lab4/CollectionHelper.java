package classes.lab4;

import java.util.Collection;
import java.util.NoSuchElementException;

public class CollectionHelper {
    public static <T> T getLastElement(Collection<T> collection) {
        return collection.stream()
                .reduce((first, second) -> second)
                .orElseThrow(() -> new NoSuchElementException("Collection is empty"));
    }
}