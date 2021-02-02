package com.company.main.java.days;

import java.io.*;
import java.util.*;

public class Day6 {
    private static List<Set<Character>> readingAFile(String fileName) {
        Set<Character> lettersAnswered = new HashSet<>();
        List<Set<Character>> listOfAnswers = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"))) {
            String positions;
            while ((positions = br.readLine()) != null) {
                for (char c : positions.toCharArray())
                    lettersAnswered.add(c);
                if (positions.isEmpty()) {
                    listOfAnswers.add(lettersAnswered);
                    lettersAnswered = new HashSet<>();
                }
            }
            listOfAnswers.add(lettersAnswered);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return listOfAnswers;
    }

    public static int countNumberOfAnswers(BufferedReader br) {
        List<Set<Character>> listOfAnswers = readingAFile("src/com/company/main/resources/questions.txt");
        int sum = 0;
        for (Set s : listOfAnswers)
            sum += s.size();
        return sum;
    }

    public static int readingAndCounting() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/com/company/main/resources/questions.txt"), "UTF-8"))) {
            return countNumberOfAnswers(br);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return 0;
    }

    private static int countNumberOfFullAnswers(BufferedReader br) throws IOException {
        int sum = 0;
        String positions;
        Set<Character> commonLetters = mkAlphabet();
        while ((positions = br.readLine()) != null) {
            if (positions.isEmpty()) {
                sum += commonLetters.size();
                commonLetters = mkAlphabet();
                continue;
            } else {
                var letters = new HashSet<>();
                for(char c: positions.toCharArray())
                    letters.add(c);
                commonLetters.retainAll(letters);
            }
        }
        sum += commonLetters.size();
        return sum;
    }

    private static Set<Character> mkAlphabet() {
        Set<Character> alphabet = new HashSet<>();
        for (char c = 'a'; c <= 'z'; ++c)
            alphabet.add(c);
        return alphabet;
    }
}
