package com.company.main.java.days;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Day5 {

    private static class Pair{
        private int leftCoordinate;
        private int rightCoordinate;

        public Pair(int leftCoordinate, int rightCoordinate) {
            this.leftCoordinate = leftCoordinate;
            this.rightCoordinate = rightCoordinate;
        }

        public int getLeftCoordinate() {
            return leftCoordinate;
        }

        public int getRightCoordinate() {
            return rightCoordinate;
        }
    }
    public static List<String> readingAFile(String fileName) {
        List<String> listOfSeats = new LinkedList<>();
        try {
            Scanner input = new Scanner(new FileReader(fileName));
            while (input.hasNext())
                listOfSeats.add(input.next());
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfSeats;
    }

    private static Pair upperHalf(int left, int right){
        return new Pair((int) (right-Math.ceil((right-left)/2)), right);
    }

    private static Pair lowerHalf(int left, int right){
        return new Pair(left, (int) (left +Math.floor((right-left)/2)));
    }


    public static int highestSeatNumber() throws IOException {
        List<String> listOfSeats=readingAFile("src/com/company/main/resources/seats.txt");
        int highest=0;
        FileWriter fileWriter = new FileWriter("src/com/company/main/resources/seats_decrypted.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for(String seatNumber: listOfSeats){
            Pair rangeRow=new Pair(0,127);
            Pair rangeColumn=new Pair(0,7);
            for(char letter: seatNumber.toCharArray()){
                Pair tmp= rangeRow;
                Pair tmpColumn =rangeColumn;
                if(letter=='F')
                    rangeRow=lowerHalf(tmp.getLeftCoordinate(), tmp.getRightCoordinate());
                else if(letter == 'B')
                    rangeRow=upperHalf(tmp.getLeftCoordinate(), tmp.getRightCoordinate());
                else {
                    if(letter=='R')
                        rangeColumn=upperHalf(tmpColumn.getLeftCoordinate(), tmpColumn.getRightCoordinate());
                    else if(letter=='L')
                        rangeColumn=lowerHalf(tmpColumn.getLeftCoordinate(), tmpColumn.getRightCoordinate());
                }
            }
            int seatID=rangeRow.getLeftCoordinate()*8+rangeColumn.getLeftCoordinate();
            printWriter.println(String.valueOf(seatID));
            //System.out.println(row + " " + column);
            if(seatID>highest)
                highest=seatID;
        }
        printWriter.close();
        return highest;
    }



    public static int mySeatNumber(){
        int mySeatNumber=0;
        List<String> listOfSeats=readingAFile("src/com/company/main/resources/seats_decrypted.txt");
        List<Integer> integerListOfSeats = listOfSeats.stream().map(s-> Integer.parseInt(s)).sorted().collect(Collectors.toList());
        for(int i=1; i<=integerListOfSeats.get(integerListOfSeats.size()-1); i++)
            if(!integerListOfSeats.contains(i) && integerListOfSeats.contains(i-1) && integerListOfSeats.contains(i+1))
                mySeatNumber=i;
        return mySeatNumber;
    }
}