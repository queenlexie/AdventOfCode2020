package com.company.main.java.days;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day8 {
    private static class GameInstructions {
        private String instruction;
        private String value;
        private boolean visited;
        private boolean tested;

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        public boolean isTested() {
            return tested;
        }

        public void setTested(boolean tested) {
            this.tested = tested;
        }

        public String getInstruction() {
            return instruction;
        }

        public void setInstruction(String instruction) {
            this.instruction = instruction;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    private static class GameAccumulationPair {
        private boolean terminated;
        private int accumulation;

        public boolean isTerminated() {
            return terminated;
        }

        public void setTerminated(boolean canTerminate) {
            this.terminated = canTerminate;
        }

        public int getAccumulation() {
            return accumulation;
        }

        public void setAccumulation(int accumulation) {
            this.accumulation = accumulation;
        }

    }

    private static List<GameInstructions> readingAFile(String fileName) {
        List<GameInstructions> game = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tmp = line.split(" ");
                GameInstructions gameinstructions = new GameInstructions();
                gameinstructions.setInstruction(tmp[0]);
                gameinstructions.setValue(tmp[1]);
                gameinstructions.setVisited(false);
                game.add(gameinstructions);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return game;
    }

    private static int acc(GameInstructions gameInstruction, int accumulator) {
        return accumulator + Integer.parseInt(gameInstruction.getValue());
    }

    private static int jmp(GameInstructions gameInstruction, int position) {
        return position + Integer.parseInt(gameInstruction.getValue());
    }

    public static int valueInAccumulator() {
        List<GameInstructions> game = readingAFile("src/com/company/main/resources/game_console.txt");
        GameAccumulationPair pair = canTerminate(game);
        System.out.println(pair.isTerminated());
        return pair.getAccumulation();
    }

    private static GameAccumulationPair canTerminate(List<GameInstructions> game) {
        for(GameInstructions instructions : game)
            instructions.setVisited(false);

        boolean hasInfiniteLoop = true;
        int position = 0;
        int valueInAccumulator = 0;

        GameAccumulationPair gamePair = new GameAccumulationPair();
        GameInstructions gameInstruction = game.get(position);

        while (!gameInstruction.isVisited()) {
            gameInstruction.setVisited(true);
            if (gameInstruction.getInstruction().equals("acc")) {
                position++;
                valueInAccumulator = acc(gameInstruction, valueInAccumulator);
            } else if (gameInstruction.getInstruction().equals("jmp")) {
                position = jmp(gameInstruction, position);
            } else if (gameInstruction.getInstruction().equals("nop"))
                position++;
            if(position >= game.size())
                hasInfiniteLoop = false;
            else
                gameInstruction = game.get(position);
        }
        gamePair.setAccumulation(valueInAccumulator);
        gamePair.setTerminated(!hasInfiniteLoop);
        return gamePair;
    }

    public static int terminateTheGame() {
        List<GameInstructions> game = readingAFile("src/com/company/main/resources/game_console.txt");
        for(GameInstructions instr: game) {
            if(instr.getInstruction().equals("acc")) continue;
            else {
                instr.setTested(true);
                if (instr.getInstruction().equals("jmp"))
                    instr.setInstruction("nop");
                else
                    instr.setInstruction("jmp");
                var gameOutput = canTerminate(game);
                if (gameOutput.isTerminated()) {
                    return gameOutput.getAccumulation();
                }
                if (instr.getInstruction().equals("nop"))
                    instr.setInstruction("jmp");
                else
                    instr.setInstruction("nop");
            }
        }
        return 0;
    }

}

