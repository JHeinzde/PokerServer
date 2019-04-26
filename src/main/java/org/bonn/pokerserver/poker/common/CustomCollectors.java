package org.bonn.pokerserver.poker.common;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CustomCollectors {

    private CustomCollectors(){
        // Hides the default public constructor
    }

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
