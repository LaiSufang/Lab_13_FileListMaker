import java.util.Scanner;

public class SafeInput {

    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retString;
        do {
            System.out.print(prompt); // show prompt
            retString = pipe.nextLine();
            //retString = retString.toLowerCase();
        } while (retString.isEmpty());
        return retString;
    }

    public static int getInt(Scanner pipe, String prompt) {
        int intNum = 0;
        boolean validInt = false;
        do {
            System.out.print(prompt);
            if (pipe.hasNextInt()) {
                intNum = pipe.nextInt();
                validInt = true;
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                pipe.next();
            }
        } while (!validInt);

        return intNum;
    }

    public static double getDouble(Scanner pipe, String prompt) {
        double doubleNum = 0;
        boolean validDouble = false;
        do {
            System.out.print(prompt);
            if (pipe.hasNextDouble()) {
                doubleNum = pipe.nextDouble();
                validDouble = true;
            } else {
                System.out.println("Invalid input. Please enter a decimal number.");
                pipe.next();
            }
        } while (!validDouble);

        return doubleNum;
    }

    public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
        int userInt = 0;
        boolean validInt = false;
        do {
            System.out.print(prompt);
            if (pipe.hasNextInt()) {
                userInt = pipe.nextInt();
                if (userInt >= low && userInt <= high) {
                    validInt = true;
                } else {
                    System.out.println("Invalid input. Please enter an integer between " + low + " and " + high + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                pipe.next(); // Consume the invalid input
            }
        } while (!validInt);
        pipe.nextLine(); // Move to the next line
        return userInt;
    }

    public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {
        double inputDouble = 0;
        boolean validDouble = false;
        do {
            System.out.print(prompt);
            if (pipe.hasNextDouble()) {
                inputDouble = pipe.nextDouble();
                if (inputDouble >= low && inputDouble <= high) {
                    validDouble = true;
                } else {
                    System.out.println("Invalid input. Please enter a decimal number between " + low + " and " + high + ".");
                }
            }
        }
        while (!validDouble);
        return inputDouble;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        String userResponse = "";
        boolean validResponse = false;
        boolean confirm = false;
        do {
            System.out.print(prompt);
            userResponse = pipe.nextLine();
            if (userResponse.equalsIgnoreCase("y") || userResponse.equalsIgnoreCase("n")) {
                validResponse = true;
                if (userResponse.equalsIgnoreCase("y")) {
                    confirm = true;
                }
            }
            else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        } while (!validResponse);
        return confirm;
    }

    public static String getRegExString(Scanner pipe, String prompt, String regEx) {
        String retString = "";
        boolean validString = false;
        do {
            System.out.print(prompt);
            retString = pipe.nextLine();
            if (retString.matches(regEx)) {
                validString = true;
            } else {
                System.out.println("Invalid input. Please enter a valid string.");
            }
        } while (!validString);
        return retString;
    }

    public static void prettyHeader(String msg) {
        int msgLength = msg.length();
        int space = (60 - msgLength - 6) / 2;
        for (int i = 0; i < 60; i++) {
            System.out.print("*");
        }
        System.out.println();
        System.out.print("***");
        for (int i = 0; i < space; i++) {
            System.out.print(" ");
        }
        System.out.print(msg);
        for (int i = 0; i < space; i++) {
            System.out.print(" ");
        }
        System.out.println("***");
        for (int i = 0; i < 60; i++) {
            System.out.print("*");
        }
        System.out.println();
    }
}