package com.company.main.java.days;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class Day13 {
    private static class Pair {
        private int minutes;
        private List<Integer> listOfBuses;
    }

    private static Pair readingAFile(String fileName) throws IOException {
        List<String> inputList = Files.readAllLines(Path.of(fileName));
        Pair pair = new Pair();
        List<Integer> outputList = new LinkedList<>();
        pair.minutes = Integer.parseInt(inputList.get(0));
        for (String s : inputList.get(1).split(","))
            if (!s.equals("x"))
                outputList.add(Integer.parseInt(s));
        pair.listOfBuses = outputList;
        return pair;
    }

    private static Map<Integer, Integer> interpretList(Pair pair) {
        Map<Integer, Integer> mapOfBuses = new HashMap<>();
        for (int i : pair.listOfBuses) {
            mapOfBuses.put(i, findClosestValue(pair.minutes, i));
        }
        return mapOfBuses;
    }

    private static int findClosestValue(int mainValue, int other) {
        return (other - mainValue % other) + mainValue;
    }

    private static int findMinInMap(Map<Integer, Integer> mapOfBuses) {
        return Collections.min(mapOfBuses.values());
    }

    private static <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static int findTheBus() throws IOException {
        Pair pair = readingAFile("src/com/company/main/resources/bus.txt");
        Map<Integer, Integer> mapOfBuses = interpretList(pair);
        int minTimeToWait = findMinInMap(mapOfBuses);
        int index = getKey(mapOfBuses, minTimeToWait);
        return index * (minTimeToWait - pair.minutes);

    }

    private static String[] readingAnotherFile(String fileName) throws IOException {
        List<String> inputList = Files.readAllLines(Path.of(fileName));
        return inputList.get(1).split(",");
    }
    private static long gcdByEuclidsAlgorithm(long n1, long n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcdByEuclidsAlgorithm(n2, n1 % n2);
    }

    private static long lcm(long number1, long number2) {
        if (number1 == 0 || number2 == 0)
            return 0;
        else {
            long gcd = gcdByEuclidsAlgorithm(number1, number2);
            return Math.abs(number1 * number2) / gcd;
        }
    }

    private static long findNumber(long number1, long number2, int index) {
        long output=0;

    }

    public static long findTimestamp() throws IOException {
        String[] input = readingAnotherFile("src/com/company/main/resources/bus_test.txt");
        //List<Integer> busList=improvingBusList(input);
        long timestamp=Integer.parseInt(input[0]);
        int i=0;
        for(String s: input){
            if(!s.equals("x"))
                //timestamp=lcm(timestamp, Integer.parseInt(s))-i-1;
            i++;
        }
        return timestamp;
    }

}
