package com.company.main.java;

import com.company.main.java.days.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        ArrayList<Integer> scores = new ArrayList<>();
        try {
            Scanner input = new Scanner(new FileReader("src/com/company/main/resources/scores.txt"));
            while (input.hasNext())
                scores.add(input.nextInt());
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(Day1.searchFor2020(scores));
        //System.out.println(Day1.tripleSearchFor2020(scores));
        //Day1.anotherSearchFor2020(scores);

        //Day2.howManyPwd();
        //Day2.howManyPwdWithModification();

        //Day3.howManyTrees();
        //System.out.println(Day3.howManyTreesForOneDown(1)*Day3.howManyTreesForOneDown(3)*Day3.howManyTreesForOneDown(5)*Day3.howManyTreesForOneDown(7));
        //System.out.println(Day3.howManyTreesForTwoDown());

        //Day4.readingAFile();
        //System.out.println(Day4.countValidPassports());
        //System.out.println(Day4.validateInputInPassports());

        //System.out.println(Day5.highestSeatNumber());
        //System.out.println(Day5.mySeatNumber());

        //System.out.println(Day6.countNumberOfAnswers());
        //System.out.println(Day6.readingAndCounting());

        //System.out.println(Day7.howManyBagColorsCanHoldDShinyGold());
        //System.out.println(Day7.howManyBagsCanShinyGoldBagHold());

        //System.out.println(Day8.valueInAccumulator());
        //System.out.println(Day8.terminateTheGame());

        //System.out.println(Day9.encodingError(5));
        //System.out.println(Day9.encryptionWeakness());

        //System.out.println(Day10.adaptJolatage());

        //System.out.println(Day11.seatingSystem());
        //System.out.println(Day11.seatingSystemInLines());

        //System.out.println(Day12.positionSum());

        //System.out.println(Day13.findTheBus());
        System.out.println(Day13.findTimestamp());


    }
}
