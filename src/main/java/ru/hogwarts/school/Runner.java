package ru.hogwarts.school;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Runner {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

//        int sum = Stream.iterate(1, a -> a + 1)
//                .limit(1_000_000)
//                .mapToInt(e -> e).sum();

        int sum = IntStream.range(0, 1_000_000).sum();
        long end = System.currentTimeMillis();
        System.out.println("sum= " + sum + " , time=" + (end - start));
    }
}
//sum= 1784293664 , time=55 default
//sum= 1784293664 , time=129 parallel
//sum= 1784293664 , time=36   int sum = Stream.iterate(1, a -> a + 1).limit(1_000_000).mapToInt(e -> e).sum();
//sum= 1784293664 , time=16  int sum= IntStream.iterate(1, a -> a + 1).limit(1_000_000).sum();
//sum= 1783293664 , time=9  int sum = IntStream.range(0, 1_000_000).sum();
