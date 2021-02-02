package com.company.main.java.days;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Stream;

public class Day11 {
    private static class Matrix {
        private int rows;
        private int columns;
        private final char[][] data;

        public int getRows() {
            return rows;
        }
        public void setRows(int rows) {
            this.rows = rows;
        }
        public int getColumns() {
            return columns;
        }
        public void setColumns(int columns) {
            this.columns = columns;
        }

        public Matrix(int rows, int columns) {
            setRows(rows);
            setColumns(columns);
            data = new char[rows][columns];
        }
        protected char getValue(int row, int column){
            return data[row][column];
        }
        protected void setValue(char value, int row, int column){
            data[row][column]=value;
        }
        protected boolean exists(int row, int column){
            if(0<=row && row<rows && column>=0 && column<columns)
                return true;
            return false;
        }

    }

    private static Matrix readingAFile(String fileName) throws IOException {
        List<String> inputList = Files.readAllLines(Path.of(fileName));
        int columns = inputList.get(0).length();
        int rows = inputList.size();
        Matrix matrix = new Matrix(rows, columns);
        int i = 0;
        for(String line: inputList){
            for (int j = 0; j < columns; j++)
                matrix.setValue(line.charAt(j), i, j);
            i++;
        }
        return matrix;
    }
    private static List<Character> getNeighbours(Matrix matrix, int row, int column){
        List<Character> neighbours=new LinkedList<>();
        if(matrix.exists(row-1, column-1))
            neighbours.add(matrix.getValue(row-1, column-1));
        if(matrix.exists(row, column-1))
            neighbours.add(matrix.getValue(row, column-1));
        if(matrix.exists(row+1, column-1))
            neighbours.add(matrix.getValue(row+1, column-1));

        if(matrix.exists(row-1, column+1))
            neighbours.add(matrix.getValue(row-1, column+1));
        if(matrix.exists(row+1, column+1))
            neighbours.add(matrix.getValue(row+1, column+1));
        if(matrix.exists(row, column+1))
            neighbours.add(matrix.getValue(row, column+1));

        if(matrix.exists(row-1, column))
            neighbours.add(matrix.getValue(row-1, column));
        if(matrix.exists(row+1, column))
            neighbours.add(matrix.getValue(row+1, column));

        return neighbours;
    }

    protected static int countOccupiedNeighbours(Matrix matrix, int row, int column){
        int counter=0;
        List<Character> listOfNeighbours= getNeighbours(matrix, row, column);
        for(char c: listOfNeighbours)
            if(c=='#')
                counter++;
            return counter;
    }

    protected static int countOccupiedSeatsInMatrix(Matrix matrix){
        int counter=0;
        for(int i=0; i<matrix.getRows(); i++)
            for(int j=0; j<matrix.getColumns(); j++)
                if(matrix.getValue(i,j)=='#')
                    counter++;
        return counter;
    }
    private static Matrix changing(Matrix input){
        int rows=input.getRows();
        int columns=input.getColumns();
        Matrix changesForOneIteration= new Matrix(rows, columns);
        for(int i=0; i<rows; i++)
            for(int j=0; j<columns; j++) {
                char sign=input.getValue(i,j);
                int counter = countOccupiedNeighbours(input, i, j);
                if(sign=='L' && counter==0)
                    changesForOneIteration.setValue('#', i, j);
                else if(sign=='#' && counter>=4)
                    changesForOneIteration.setValue('L', i, j);
                else
                    changesForOneIteration.setValue(sign, i, j);
            }
        return changesForOneIteration;
    }

    public static int seatingSystem() throws IOException {
        int occupiedSeats=0;
        int numberOfTakenSeatsInRound=0;
        Matrix input = readingAFile("src/com/company/main/resources/seats_day11.txt");
        do{
            occupiedSeats=numberOfTakenSeatsInRound;
            Matrix changesForOneIteration= changing(input);
            numberOfTakenSeatsInRound=countOccupiedSeatsInMatrix(changesForOneIteration);
            input=changesForOneIteration;
        }while(occupiedSeats!=numberOfTakenSeatsInRound);
        return occupiedSeats;
    }

    private static List<Character> getListsOfNeighbours(Matrix matrix, int row, int column){
        List<Character> neighbours = new LinkedList<>();
        for(int i=-1; i<=1; i++) {
            for(int j=-1; j<=1; j++)
                if(i!=0 || j!=0){
                    findFirstNonFloor(matrix, row, column, i, j).ifPresent(neighbours::add);
                }
        }
        return neighbours;
    }

    private static Optional<Character> findFirstNonFloor(
        Matrix matrix,
        int row,
        int col,
        int rowStepper,
        int columnStepper
    ) {
        var currentRow =  row + rowStepper;
        var currentCol =  col + columnStepper;
        Character foundNonFloor = null;
        while(matrix.exists(currentRow, currentCol)){
            var cellValue = matrix.getValue(currentRow, currentCol);
            if(cellValue != '.') {
                foundNonFloor = cellValue;
                break;
            }
            else {
                currentCol+=columnStepper;
                currentRow+=rowStepper;
            }
        }
        return Optional.ofNullable(foundNonFloor);
    }

    protected static int countOccupiedNeighboursForLine(Matrix matrix, int row, int column){
        int counter=0;
        List<Character> listOfNeighbours= getListsOfNeighbours(matrix, row, column);
        for(char c: listOfNeighbours)
            if(c == '#') counter++;
        return counter;
    }
    private static Matrix changingForOneLine(Matrix input){
        int rows=input.getRows();
        int columns=input.getColumns();
        Matrix changesForOneIteration= new Matrix(rows, columns);
        for(int i=0; i<rows; i++)
            for(int j=0; j<columns; j++) {
                char sign=input.getValue(i,j);
                int counter = countOccupiedNeighboursForLine(input, i, j);
                if(sign=='L' && counter==0)
                    changesForOneIteration.setValue('#', i, j);
                else if(sign=='#' && counter>=5)
                    changesForOneIteration.setValue('L', i, j);
                else
                    changesForOneIteration.setValue(sign, i, j);
            }
        return changesForOneIteration;
    }
    public static int seatingSystemInLines() throws IOException {
        int occupiedSeats=0;
        int numberOfTakenSeatsInRound=0;
        Matrix input = readingAFile("src/com/company/main/resources/seats_day11.txt");
        do{
            occupiedSeats=numberOfTakenSeatsInRound;
            Matrix changesForOneIteration= changingForOneLine(input);
            numberOfTakenSeatsInRound=countOccupiedSeatsInMatrix(changesForOneIteration);
            input=changesForOneIteration;
        }while(occupiedSeats!=numberOfTakenSeatsInRound);
        return occupiedSeats;
    }

    }
