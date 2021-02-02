package com.company.main.java.days;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day4 {
    static class Pair {
        private String id;
        private String value;

        public Pair(String id, String value) {
            this.id = id;
            this.value = value;
        }
    }
    static class PersonDetails{
        private Pair byr;
        private Pair iyr;
        private Pair eyr;
        private Pair hgt;
        private Pair hcl;
        private Pair ecl;
        private Pair pid;
        private Pair cid;

        public Pair getByr() {
            return byr;
        }

        public void setByr(Pair byr) {
            this.byr = byr;
        }

        public Pair getIyr() {
            return iyr;
        }

        public void setIyr(Pair iyr) {
            this.iyr = iyr;
        }

        public Pair getEyr() {
            return eyr;
        }

        public void setEyr(Pair eyr) {
            this.eyr = eyr;
        }

        public Pair getHgt() {
            return hgt;
        }

        public void setHgt(Pair hgt) {
            this.hgt = hgt;
        }

        public Pair getHcl() {
            return hcl;
        }

        public void setHcl(Pair hcl) {
            this.hcl = hcl;
        }

        public Pair getEcl() {
            return ecl;
        }

        public void setEcl(Pair ecl) {
            this.ecl = ecl;
        }

        public Pair getPid() {
            return pid;
        }

        public void setPid(Pair pid) {
            this.pid = pid;
        }

        public Pair getCid() {
            return cid;
        }

        public void setCid(Pair cid) {
            this.cid = cid;
        }
    }

    public static List<PersonDetails> readingAFile(){
        List<PersonDetails> people=new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/com/company/main/resources/passports.txt"), "UTF-8"))) {
            String positions = null;
            PersonDetails person = new PersonDetails();
            while ((positions=br.readLine())!=null) {
                if(positions.isEmpty()){
                    people.add(person);
                    person = new PersonDetails();
                    continue; }
                String[] ID = positions.split(" ");
                    for (String s : ID) {
                        String[] id = s.split(":");
                        switch (id[0]) {
                            case "byr":
                                person.setByr(new Pair("byr", id[1]));
                                break;
                            case "iyr":
                                person.setIyr(new Pair("iyr", id[1]));
                                break;
                            case "eyr":
                                person.setEyr(new Pair("eyr", id[1]));
                                break;
                            case "hgt":
                                person.setHgt(new Pair("hgt", id[1]));
                                break;
                            case "hcl":
                                person.setHcl(new Pair("hcl", id[1]));
                                break;
                            case "ecl":
                                person.setEcl(new Pair("ecl", id[1]));
                                break;
                            case "pid":
                                person.setPid(new Pair("pid", id[1]));
                                break;
                            case "cid":
                                person.setCid(new Pair("cid", id[1]));
                                break;
                        }
                }
            }
            people.add(person);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        for (PersonDetails p: people)
//            if(p.getByr()!=null)
//                System.out.println(p.getByr().id +" " + p.getByr().value);

        //System.out.println(people.size());
        return people;
    }

    public static int countValidPassports(){
        int nrOfValidPassports=0;
        List<PersonDetails> passports= readingAFile();
        for(PersonDetails pd : passports){
            if(pd.getByr()!=null && pd.getEcl()!=null && pd.getEyr()!=null && pd.getHcl()!=null
                    && pd.getHgt()!=null && pd.getIyr()!=null && pd.getPid()!=null)
                nrOfValidPassports++;
        }
        return nrOfValidPassports;
    }

    enum eyeColour{
        amb, gry, blu, grn, brn, hzl, oth;

    }
    public static boolean between(int value, int leftCoordinate, int rightCoordinate){
        if(IntStream.rangeClosed(leftCoordinate,rightCoordinate).boxed().collect(Collectors.toList()).contains(value))
            return true;
        return false;
    }

    private static boolean validateByr(PersonDetails pd){
        if(pd.getByr().value.matches("^[0-9]*$") && pd.getByr().value.length() ==4 ){
            if (between(Integer.parseInt(pd.getByr().value), 1920, 2002))
                return true;
        }
        return false;
    }
    private static boolean validateIyr(PersonDetails pd){
        if(pd.getIyr().value.matches("^[0-9]*$") && pd.getIyr().value.length()==4 )
            if (between(Integer.parseInt(pd.getIyr().value), 2010, 2020))
                return true;
        return false;
    }
    private static boolean validateEyr(PersonDetails pd){
        if(pd.getEyr().value.matches("^[0-9]*$") && pd.getEyr().value.length()==4 )
            if (between(Integer.parseInt(pd.getEyr().value), 2020, 2030))
                return true;
        return false;
    }
    private static boolean validatePid(PersonDetails pd){
        if(pd.getPid().value.matches("^[0-9]*$") && pd.getPid().value.length()==9)
                return true;
        return false;
    }

    private static boolean validateHcl(PersonDetails pd){
        if(pd.getHcl().value.charAt(0)=='#')
            if (pd.getHcl().value.matches("^[#,0-9,a-f]*$") && pd.getHcl().value.length()==7 )
                return true;
        return false;
    }
    private static boolean validateEcl(PersonDetails pd) {
        return Stream.of(eyeColour.values()).anyMatch(x->x.name().equals(pd.getEcl().value));
    }


    private static boolean validateHgt(PersonDetails pd){
        String heightString = pd.getHgt().value;
        if(heightString.endsWith("cm")) {
            int heightCmIndex = heightString.indexOf("cm");
            int height = Integer.parseInt(heightString.substring(0, heightCmIndex));
            return between(height, 150, 193);
        }
        else if(heightString.endsWith("in")){
            int heightInIndex = heightString.indexOf("in");
            int height = Integer.parseInt(heightString.substring(0, heightInIndex));
            return between(height, 59, 76);
        }
        else return false;
    }

    public static int validateInputInPassports(){
        int nrOfValidPassports=0;
        List<PersonDetails> passportWithAllProperties= new LinkedList<>();
        for(PersonDetails pd : readingAFile())
            if(pd.getByr()!=null && pd.getEcl()!=null && pd.getEyr()!=null && pd.getHcl()!=null && pd.getHgt()!=null && pd.getIyr()!=null && pd.getPid()!=null)
                passportWithAllProperties.add(pd);

        for(PersonDetails pd : passportWithAllProperties)
           if(validateByr(pd)  && validateEyr(pd) && validateIyr(pd) && validateHcl(pd) && validateHgt(pd)  && validatePid(pd) && validateEcl(pd))
                nrOfValidPassports++;
        return nrOfValidPassports;
    }

}
