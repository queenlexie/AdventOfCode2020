package com.company.main.java.days;

import java.io.*;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;


public class Day2 {
    static class Pair {
        private String left;
        private char right;

        public String getLeft() {
            return left;
        }

        public void setLeft(String left) {
            this.left = left;
        }

        public char getRight() {
            return right;
        }

        public void setRight(char right) {
            this.right = right;
        }
    }

    public static Map<Pair, String> readingAFile(){
        Map<Pair, String> passwords = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/com/company/main/resources/passwords.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tmp = line.split(" ");
                for (int i = 0; i < tmp.length; i = i + 3) {
                    Pair keys = new Pair();
                    keys.setLeft(tmp[i]);
                    keys.setRight(tmp[i + 1].charAt(0));
                    passwords.put(keys, tmp[i + 2]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Pair keys : passwords.keySet()) {
            System.out.println(keys.left + " " +keys.right + " "+ passwords.get(keys));
        }
        System.out.println(passwords.size());
        return passwords;
    }


    public static void howManyPwd() throws IOException {

        int sumOfValidPasswords = 0;
        Map<Pair, String> passwords = readingAFile();
        for (Pair keys : passwords.keySet()) {
            String[] tmp = (keys.getLeft().split("-"));
            long count = passwords.get(keys).chars().filter(ch -> ch == keys.getRight()).count();
            if(count>=Integer.valueOf(tmp[0]) && count<= Integer.valueOf(tmp[1]))
                sumOfValidPasswords++;
        }
        System.out.println(sumOfValidPasswords);
    }

    public static void howManyPwdWithModification(){
        int sumOfValidPasswords = 0;
        Map<Pair, String> passwords = readingAFile();
        for (Pair keys : passwords.keySet()) {
            String[] tmp = (keys.getLeft().split("-"));
            if(Integer.valueOf(tmp[0])<=passwords.get(keys).length() && keys.getRight()==passwords.get(keys).charAt(Integer.valueOf(tmp[0])-1) ^ keys.getRight()==passwords.get(keys).charAt(Integer.valueOf(tmp[1])-1))
                sumOfValidPasswords++;
        }
        System.out.println(sumOfValidPasswords);
    }
}
