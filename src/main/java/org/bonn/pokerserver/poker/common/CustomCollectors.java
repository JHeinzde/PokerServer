package org.bonn.pokerserver.poker.common;

import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * This class implements any custom collectors needed by other parts of this software
 */
public class CustomCollectors {

    private CustomCollectors(){
        // Hides the default public constructor
    }

    /**
     * The returned collector collects the result of a stream, and if it is size 1 it returns the single object
     * @param <T> The object type for which the singleton collector should be built
     * @return A Collector that takes a stream that only returns a single value and returns this single value.
     */
    public static <T> Collector<T, ?, T> toSingleton() {
        return Collectors.collectingAndThen(Collectors.toList(),
                list -> {
                    if (list.size() == 1) {
                        return list.get(0);
                    } else {
                        throw new IllegalStateException("List size is != 1");
                    }
                });
    }

}
