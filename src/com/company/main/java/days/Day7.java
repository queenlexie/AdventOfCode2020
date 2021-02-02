package com.company.main.java.days;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day7 {
    private static class Bag{
        private String color;
        private String content;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
    private static Map<String, List<Bag>> readingAFile(String fileName) {
        Map<String, List<Bag>> listOfBags = new HashMap<>();
        List<Bag> bags;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                bags = new LinkedList<>();
                String[] tmp = line.split("contain");
                if (!tmp[1].endsWith("no other bags."))
                    for (String s : tmp[1].split(",")) {
                        String[] descriptionOfOneConsistency = s.trim().split(" ");
                        Bag bag =new Bag();
                        bag.setColor(descriptionOfOneConsistency[1] + " " + descriptionOfOneConsistency[2]);
                        bag.setContent(descriptionOfOneConsistency[0]);
                        bags.add(bag);}
                listOfBags.put(tmp[0].replaceAll("bags", "").trim(), bags);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfBags;
    }


    public static int howManyBagColorsCanHoldDShinyGold() {
        int numbersOfColours = 0;
        Map<String, List<Bag>> listOfBags = readingAFile("src/com/company/main/resources/bags.txt");
        for (String bag : listOfBags.keySet())
            if (recursiveCheckingBags(listOfBags, bag))
                numbersOfColours++;
        return numbersOfColours;
    }

    public static int howManyBagsCanShinyGoldBagHold(){
        Map<String, List<Bag>> listOfBags = readingAFile("src/com/company/main/resources/bags.txt");
        return checkingNumberOfBags(listOfBags, "shiny gold");
    }

    private static boolean recursiveCheckingBags(Map<String, List<Bag>> allBags, String bagName) {
        List<Bag> allowedBags = allBags.get(bagName);
        if (allowedBags.stream().anyMatch(bag -> bag.getColor().equals("shiny gold")))
            return true;
        else if (allowedBags.isEmpty())
            return false;
        else
            return allowedBags
                    .stream()
                    .anyMatch(bag -> recursiveCheckingBags(allBags, bag.getColor()));
    }

    private static int checkingNumberOfBags(Map<String, List<Bag>> allBags, String bagName) {
        List<Bag> allowedBags = allBags.get(bagName);
        if (allowedBags.isEmpty())
            return 0;
        else
            return allowedBags.stream()
                              .map(bag -> Integer.parseInt(bag.getContent()) *(1+ checkingNumberOfBags(allBags, bag.getColor())))
                              .reduce(0, Integer::sum);
    }

}
