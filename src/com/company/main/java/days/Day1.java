package com.company.main.java.days;

import java.util.ArrayList;
import java.util.stream.IntStream;

class Pair {
    private int left;
    private int right;
}

public class Day1 {
    public static int searchFor2020(ArrayList<Integer> scores){
        int multiplication = 0;
        for(int a: scores)
            for(int b: scores)
                if(a+b==2020)
                    multiplication=a*b;
        return multiplication;
    }
    public static int tripleSearchFor2020(ArrayList<Integer> scores){
        int multiplication = 0;
        for(int a: scores)
            for(int b: scores)
                for(int c: scores)
                if(a+b+c==2020)
                    multiplication=a*b*c;
        return multiplication;
    }
    public static void anotherSearchFor2020(ArrayList<Integer> input) {
        IntStream.range(0, input.size())
            .forEach(i ->
                IntStream.range(i+1, input.size())
                        .filter(j-> input.get(i) + input.get(j) == 2020)
                        .forEach(j-> System.out.println(i*j))
            );
    }
}
