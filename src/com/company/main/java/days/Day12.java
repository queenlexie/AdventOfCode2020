package com.company.main.java.days;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day12 {
    private static class WorldDirections {
        int EW;
        int NS;

        public WorldDirections() {
        }

        public WorldDirections(int EW, int NS) {
            this.EW = EW;
            this.NS = NS;
        }
    }

    private static class InstructionsPair {
        private char direction;
        private int value;

        public char getDirection() {
            return direction;
        }

        public int getValue() {
            return value;
        }

        protected InstructionsPair(char direction, int value) {
            this.direction = direction;
            this.value = value;
        }
    }

    private static List<InstructionsPair> readingAFile(String fileName) throws IOException {
        List<String> inputList = Files.readAllLines(Path.of(fileName));
        List<InstructionsPair> outputList = new LinkedList<>();
        for (String s : inputList)
            outputList.add(new InstructionsPair(s.charAt(0), Integer.parseInt(s.substring(1))));
        return outputList;
    }

    enum position {
        north, east, south, west
    }

    private static WorldDirections interpretNavigations(List<InstructionsPair> instructions) {
        position p = position.east;
        int NScounter = 0;
        int EWcounter = 0;
        for (InstructionsPair pair : instructions) {
            char c = pair.getDirection();
            int value = pair.getValue();
            switch (c) {
                case 'N':
                    NScounter += value;
                    break;
                case 'S':
                    NScounter -= value;
                    break;
                case 'F':
                    if (p == position.north)
                        NScounter += value;
                    else if (p == position.south)
                        NScounter -= value;
                    else if (p == position.east)
                        EWcounter += value;
                    else
                        EWcounter -= value;
                    break;
                case 'E':
                    EWcounter += value;
                    break;
                case 'W':
                    EWcounter -= value;
                    break;
                default:
                    p = leftOrRight(p, c, value);
                    break;
            }
        }
        return new WorldDirections(Math.abs(EWcounter), Math.abs(NScounter));
    }

    private static position leftOrRight(position p, char c, int value) {
        var newPosition = p;
        switch (value) {
            case 90:
                if ((c == 'L' && p == position.north) || (c == 'R' && p == position.south))
                    newPosition = position.west;
                else if ((c == 'R' && p == position.north) || (c == 'L' && p == position.south))
                    newPosition = position.east;
                else if ((c == 'L' && p == position.east) || (c == 'R' && p == position.west))
                    newPosition = position.north;
                else if ((c == 'R' && p == position.east) || (c == 'L' && p == position.west))
                    newPosition = position.south;
                break;

            case 180:
                if (p == position.east)
                    newPosition = position.west;
                if (p == position.west)
                    newPosition = position.east;
                if (p == position.south)
                    newPosition = position.north;
                if (p == position.north)
                    newPosition = position.south;
                break;

            case 270:
                if ((c == 'L' && p == position.north) || (c == 'R' && p == position.south))
                    newPosition = position.east;
                else if ((c == 'R' && p == position.north) || (c == 'L' && p == position.south))
                    newPosition = position.west;
                else if ((c == 'L' && p == position.east) || (c == 'R' && p == position.west))
                    newPosition = position.south;
                else if ((c == 'R' && p == position.east) || (c == 'L' && p == position.west))
                    newPosition = position.north;
                break;

            case 360:
                break;
        }
        return newPosition;
    }

    public static int positionSum() throws IOException {
        List<InstructionsPair> input = readingAFile("src/com/company/main/resources/navigations.txt");
        WorldDirections ship = interpretNavigationsWithWaypoint(input);
        return Math.abs(ship.NS) + Math.abs(ship.EW);
    }

    private static class WaypointAndShip {
        private WorldDirections waypoint;
        private WorldDirections ship;

        public WaypointAndShip(WorldDirections waypoint, WorldDirections ship) {
            this.waypoint = waypoint;
            this.ship = ship;
        }
    }

    private static WorldDirections interpretNavigationsWithWaypoint(List<InstructionsPair> instructions) {
        WorldDirections waypoint = new WorldDirections(10, 1);
        WorldDirections ship = new WorldDirections(0, 0);
        WaypointAndShip waypointAndShip = new WaypointAndShip(waypoint, ship);
        for (InstructionsPair pair : instructions) {
            char c = pair.getDirection();
            int value = pair.getValue();
            switch (c) {
                case 'N':
                    waypoint.NS += value;
                    break;
                case 'S':
                    waypoint.NS -= value;
                    break;
                case 'F':
                    ship = moveForward(new WaypointAndShip(waypoint, ship), value);
                    break;
                case 'E':
                    waypoint.EW += value;
                    break;
                case 'W':
                    waypoint.EW -= value;
                    break;
                default:
                    waypoint = leftOrRight(waypoint, value);
                    break;
            }
            System.out.println(waypoint.EW + " " + waypoint.NS + " ship:" + ship.EW + " " + ship.NS);
        }
        return ship;
    }

    private static WorldDirections moveForward(WaypointAndShip waypointAndShip, int value) {
        WorldDirections ship = waypointAndShip.ship;
        ship.EW += waypointAndShip.waypoint.EW * value;
        ship.NS += waypointAndShip.waypoint.NS * value;
        return ship;
    }

    private static WorldDirections leftOrRight(WorldDirections waypointIn, int value) {
        WorldDirections waypointOut = new WorldDirections();
        if (value == 90) {
            waypointOut.NS = -waypointIn.EW;
            waypointOut.EW = waypointIn.NS;
        } else if (value == 180)
            waypointOut = leftOrRight(leftOrRight(waypointIn, 90), 90);
        else if (value == 270)
            waypointOut = leftOrRight(leftOrRight(waypointIn, 180), 90);
        else
            waypointOut = waypointIn;
        return waypointOut;
    }

}

