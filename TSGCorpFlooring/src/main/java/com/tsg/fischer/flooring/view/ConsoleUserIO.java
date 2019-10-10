package com.tsg.fischer.flooring.view;

import java.util.Scanner;

public class ConsoleUserIO implements UserIO {

    private Scanner sc = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        double result;

        print(prompt);
        result = sc.nextDouble();
        sc.nextLine();

        return result;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double result = 0;
        boolean badInput = true;

        while (badInput) {
            result = readDouble(prompt);
            if (result >= min && result <= max) {
                badInput = false;
            } else {
                print("Input needs to be >= " + min + " and <= " + max);
            }
        }

        return result;

    }

    @Override
    public float readFloat(String prompt) {
        float result;

        print(prompt);
        result = sc.nextFloat();
        sc.nextLine();

        return result;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float result = 0;
        boolean badInput = true;

        while (badInput) {
            result = readInt(prompt);
            if (result >= min && result <= max) {
                badInput = false;
            } else {
                print("Input needs to be >= " + min + " and <= " + max);
            }
        }

        return result;
    }


    @Override
    public int readInt(String prompt) {
        int result;

        print(prompt);
        result = sc.nextInt();
        sc.nextLine();

        return result;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int result = 0;
        boolean badInput = true;

        while (badInput) {
            result = readInt(prompt);
            if (result >= min && result <= max) {
                badInput = false;
            } else {
                print("Input needs to be >= " + min + " and <= " + max);
            }
        }

        return result;
    }

    @Override
    public long readLong(String prompt) {
        long result;

        print(prompt);
        result = sc.nextLong();
        sc.nextLine();

        return result;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long result = 0;
        boolean badInput = true;

        while (badInput) {
            result = readLong(prompt);
            if (result >= min && result <= max) {
                badInput = false;
            } else {
                print("Input needs to be >= " + min + " and <= " + max);
            }
        }

        return result;

    }

    @Override
    public String readString(String prompt) {
        String result = "";

        print(prompt);
        result = sc.nextLine();

        return result;
    }
}
