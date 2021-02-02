package com.company.main.java.days;

import java.io.*;
import java.util.*;

public class Day10 {
    private static ArrayList<Integer> readingAFile(String fileName) {
        ArrayList<Integer> batteries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"))) {
            String positions = null;
            while ((positions = br.readLine()) != null)
                batteries.add(Integer.valueOf(positions));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return batteries;
    }
    public static int adaptJolatage(){
        ArrayList<Integer> batteries= readingAFile("src/com/company/main/resources/joltage.txt");
        batteries.add(0);
        Collections.sort(batteries);
        batteries.add(Collections.max(batteries)+3);
        int oneDifference=0;
        int threeDifference=0;
        for(int i=0; i<batteries.size()-1; i++){
            if(batteries.get(i+1)-batteries.get(i)==1)
                oneDifference++;
            else if(batteries.get(i+1)-batteries.get(i)==3)
                threeDifference++;
        }
        return oneDifference*threeDifference;
    }
}
