package com.company.main.java.days;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class Day9 {
    private static ArrayList<Long> readingAFile(String fileName) {
        ArrayList<Long> xmasValues = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"))) {
            String positions = null;
            while ((positions = br.readLine()) != null)
                xmasValues.add(Long.parseLong(positions));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xmasValues;
    }
    public static long encodingError(ArrayList<Long> xmasValues, int preambleLength){
        //ArrayList<Long> xmasValues = readingAFile("src/com/company/main/resources/xmas.txt");
        long firstInvalidNumber=xmasValues.get(preambleLength);
        for(int i=preambleLength; i<xmasValues.size(); i++) {
            boolean existsMatching=false;
            for (int j = i - preambleLength; j <= i - 1; j++) {
                if (xmasValues.contains(Math.abs(xmasValues.get(i)-xmasValues.get(j))))
                    existsMatching=true;
            }
            if(!existsMatching) {
                firstInvalidNumber = xmasValues.get(i);
                break;}
        }
                return firstInvalidNumber;
    }
    private static ArrayList<Long> reducedInputList(ArrayList<Long> xmasValues, long invalidNumber){
        ArrayList<Long> xmasValuesUntilInvalidNumber=new ArrayList<>();
        for(int i=0; i<xmasValues.indexOf(invalidNumber); i++)
            xmasValuesUntilInvalidNumber.add(xmasValues.get(i));
        return xmasValuesUntilInvalidNumber;
    }
    private static long sumOfSequence(ArrayList<Long> sequence){
        long sum=0;
        for(long l: sequence)
            sum+=l;
        return sum;
    }
    private static ArrayList<Long> createSequence(ArrayList<Long> xmasValues, int sequenceLength){
        ArrayList<Long> sequence= new ArrayList<>();
        for(int i=0; i<sequenceLength; i++)
            sequence.add(xmasValues.get(i));
        return sequence;
    }
    private static ArrayList<Long> findPreamble(ArrayList<Long> xmasValues, long invalidNumber){
        ArrayList<Long> xmasValuesUntilInvalidNumber=reducedInputList(xmasValues, invalidNumber);
        ArrayList<Long> sequence;
        for(int sequenceLength=2; sequenceLength<xmasValuesUntilInvalidNumber.size(); sequenceLength++){
            sequence=createSequence(xmasValuesUntilInvalidNumber, sequenceLength);
            for(int i=0; i<xmasValuesUntilInvalidNumber.size()-sequenceLength; i++) {
                if (sumOfSequence(sequence) == invalidNumber)
                    return sequence;
                else {
                    sequence.remove(0);
                    sequence.add(xmasValuesUntilInvalidNumber.get(sequenceLength+i));
                }
            }
            }
        return null;
    }
    private static long sumOfMinAndMaxInPreamble(ArrayList<Long> preamble){
        int minIndex = preamble.indexOf(Collections.min(preamble));
        int maxIndex = preamble.indexOf(Collections.max(preamble));
        return preamble.get(minIndex) + preamble.get(maxIndex);
    }
    public static long encryptionWeakness(){
        ArrayList<Long> xmasValues = readingAFile("src/com/company/main/resources/xmas.txt");
        long invalidNumber=encodingError(xmasValues,25);
        ArrayList<Long> preamble = findPreamble(xmasValues, invalidNumber);
        return sumOfMinAndMaxInPreamble(preamble);
    }
    }
