package com.company.main.java.days;

import java.io.*;
import java.util.LinkedList;

public class Day3 {

    public static LinkedList<char[]> readingAFile(){
        LinkedList<char[]> trajectory = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream ("src/com/company/main/resources/tobogganTrajectory.txt"), "UTF-8"))) {
            String positions = null;
            while ((positions=br.readLine())!=null)
                trajectory.add(positions.toCharArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (char[] c : trajectory)
                System.out.println(c);
        return trajectory;
    }
    public static void howManyTrees(){
        LinkedList<char[]> trajectory =readingAFile();
        trajectory.pop();
        //System.out.println(trajectory.size());
        int sumOfTrees=0;
        int rightCoordinate=3;
        for(char[] signs: trajectory) {
            rightCoordinate = rightCoordinate % signs.length;
            if(signs[rightCoordinate]=='#')
                sumOfTrees++;
            rightCoordinate=rightCoordinate+3;
        }
        System.out.println(sumOfTrees);
        }

    public static int howManyTreesForOneDown(int value){
        LinkedList<char[]> trajectory =readingAFile();
        trajectory.pop();
        int sumOfTrees=0;
        int rightCoordinate=value;
        for(char[] signs: trajectory) {
            rightCoordinate = rightCoordinate % signs.length;
            if(signs[rightCoordinate]=='#')
                sumOfTrees++;
            rightCoordinate=rightCoordinate+value;
        }
        System.out.println(sumOfTrees);
        return sumOfTrees;
    }

    public static int howManyTreesForTwoDown(){
        LinkedList<char[]> trajectory =readingAFile();
        int sumOfTrees=0;
        int rightCoordinate=1;
        for(int iterator=2; iterator<trajectory.size(); iterator=iterator+2) {
            char[] signs = trajectory.get(iterator);
            rightCoordinate = rightCoordinate % signs.length;
            if(signs[rightCoordinate]=='#')
                sumOfTrees++;
            rightCoordinate=rightCoordinate+1;
        }
        System.out.println(sumOfTrees);
        return sumOfTrees;
    }


}
