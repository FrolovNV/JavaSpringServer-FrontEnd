package main.details;

import java.util.Date;

public class DetailsFunction {

    static public boolean checkString(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i)) && (str.charAt(i) != '-' )) {
                return false;
            }
        }
        return true;
    }

    static public String[][] parsingData(String bigData, int countColumn) {
        if (bigData.isEmpty()) {
            System.out.println((new Date()) + " TRY AGAIN PLEASE: You didn't have price in this plane");
            return null;
        }
        String[] lines = bigData.split("\n");
        String[][] strings = new String[lines.length][countColumn];
        for (int i = 0; i < lines.length; i++){
            String[] line = lines[i].split(" ");
            for (int j = 0; j < countColumn; j++) {
                strings[i][j] = line[j];
            }
        }
        return strings;
    }

    static public boolean CheckNumber(String number) {
        if (number.length() != 6) {
            return false;
        }
        if (!Character.isDigit(number.charAt(1)) || !Character.isDigit(number.charAt(2)) ||
                !Character.isDigit(number.charAt(3)) || !Character.isAlphabetic(number.charAt(0))
                || !Character.isAlphabetic(number.charAt(4)) || !Character.isAlphabetic(number.charAt(5)))   {
            return false;
        }
        return true;
    }

    static public String[][] filter(String bigData, String status) {
        if (bigData.isEmpty()) {
            System.out.println((new Date()) + " TRY AGAIN PLEASE: You didn't have price in this plane");
            return null;
        }
        String str[][] = DetailsFunction.parsingData(bigData, 3);
        Integer count = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i][2].equals(status)) {
                continue;
            }
            count += 1;
        }
        Integer iS = 0;
        String strings[][] = new String[count][3];
        for (int i = 0; i < str.length; i++) {
            if (str[i][2].equals(status)) {
                continue;
            }
            for (int j = 0; j < 3; j++) {
                strings[iS][j] = str[i][j];
            }
            iS += 1;
        }
        return strings;
    }

    public static String[][] filterIn(String bigData) {
        String[][] str = parsingData(bigData, 5);
        Integer count = 0;
        for (int i = 0; i < str.length; i++) {
            if (str[i][1].equals("Car_In_Park")) {
                count += 1;
            }
        }
        Integer iS = 0;
        String strings[][] = new String[count][5];
        for (int i = 0; i < str.length; i++) {
            if (str[i][1].equals("Car_In_Park")) {
                for (int j = 0; j < 5; j++) {
                    strings[iS][j] = str[i][j];
                }
                iS += 1;
            }
        }
        return strings;
    }

    public static String[][] filterOut(String bigData) {
        String[][] str = parsingData(bigData, 5);
        Integer count = 0;
        for (int i = 0; i < str.length; i++) {
            if ((!str[i][1].equals("Car_In_Park")) && (str[i][2].equals("Car_Didn't_Return_Yet"))) {
                count += 1;
            }
        }
        Integer iS = 0;
        String strings[][] = new String[count][5];
        for (int i = 0; i < str.length; i++) {
            if ((!str[i][1].equals("Car_In_Park")) && (str[i][2].equals("Car_Didn't_Return_Yet"))) {
                for (int j = 0; j < 5; j++) {
                    strings[iS][j] = str[i][j];
                }
                iS += 1;
            }
        }
        return strings;
    }

    public static String[][] filterBack(String bigData) {
        String[][] str = parsingData(bigData, 5);
        Integer count = 0;
        for (int i = 0; i < str.length; i++) {
            if ((!str[i][1].equals("Car_In_Park")) && (!str[i][2].equals("Car_Didn't_Return_Yet")) &&
                    (!str[i][2].equals("Car_In_Park"))) {
                count += 1;
            }
        }
        Integer iS = 0;
        String strings[][] = new String[count][5];
        for (int i = 0; i < str.length; i++) {
            if ((!str[i][1].equals("Car_In_Park")) && (!str[i][2].equals("Car_Didn't_Return_Yet")) &&
                    (!str[i][2].equals("Car_In_Park"))) {
                for (int j = 0; j < 5; j++) {
                    strings[iS][j] = str[i][j];
                }
                iS += 1;
            }

        }
        return strings;
    }
}
