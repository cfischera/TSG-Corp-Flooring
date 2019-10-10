package com.tsg.fischer.flooring.view;

import java.util.List;

public class View {

    private UserIO io;

    public View(UserIO io) {
        this.io = io;
    }

    public void displayWelcome() {
        io.print("");
        io.print("* * * * * * * * * * *");
        io.print("* * * TSG CORP * * *");
        io.print("* FLOORING ORDERS * *");
        io.print("* * * * * * * * * * *");
        io.print("");
        hold();
    }

    public void displayMenu() {
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("*  <<Flooring Program>>");
        io.print("* 1. Display Orders");
        io.print("* 2. Add an Order");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove an Order");
        io.print("* 5. Save Current Work");
        io.print("* 6. Quit");
        io.print("*");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }

    public int getMenuChoice() {
        return io.readInt("Please enter your menu choice.", 1, 6);
    }

    public void displayFarewell() {
        io.print("* * * * * * * *");
        io.print("* * GOODBYE * *");
        io.print("* * * * * * * *");
    }

    public String getDate() {
        return io.readString("Please enter a date (MMDDYYYY):");
    }

    public String getName(String prevName) {
        String input = io.readString("Please enter a name ("+prevName+"): ");
        if(input.equals("")) {
            return prevName;
        }
        else {
            return input;
        }
    }

    public String getState(List<String> stateStrings, String prevState) {
        String input = io.readString("Please enter a state ("+prevState+"): ");
        if(input.equals("")) {
            return prevState;
        }
        else {
            return input;
        }
    }

    public String getProduct(List<String> productStrings, String prevProduct) {
        showProducts(productStrings);
        String input = io.readString("Please enter a product ("+prevProduct+"): ");
        if(input.equals("")) {
            return prevProduct;
        }
        else {
            return input;
        }
    }

    public String getArea(String prevArea) {
        String input = io.readString("Please enter an area ("+prevArea+"): ");
        if(input.equals("")) {
            return prevArea;
        }
        else {
            return input;
        }
    }

    public String getName() {
        return io.readString("Please enter a customer name: ");
    }

    public String getState(List<String> stateStrings) {
        showStates(stateStrings);
        return io.readString("Please enter a state: ");
    }

    public String getProduct(List<String> productStrings) {
        showProducts(productStrings);
        return io.readString("Please choose a product: ");
    }

    public String getArea() {
        return io.readString("Please enter an area: ");
    }

    public String confirmOrder(String orderString, String action) {
        io.print(orderString);
        return io.readString("Would you like to "+action+" this order? (Y/N): ");
    }

    public int getOrderNumber() {
        return io.readInt("Please enter an order number: ");
    }


    public void displayOrders(List<String> orderStrings) {
        io.print("Showing Orders:");
        for(String s: orderStrings) {
            io.print(s);
        }
        hold();
    }

    public void showProducts(List<String> productStrings) {
        io.print("Products:");
        for(String s: productStrings) {
            io.print(s);
        }
    }

    public void showStates(List<String> stateStrings) {
        io.print("States:");
        for(String s: stateStrings) {
            io.print(s);
        }
    }

    public String askForSave() {
        return io.readString("Would you like to save your work? (Y/N): ");
    }

    public void displaySave() {
        io.print("Data Saved.");
    }

    public void displayError(String e) {
        io.print("Error: "+e);
        hold();
    }

    public void hold() {
        io.print("");
        io.readString("Press Enter to continue.");
        io.print("");
    }
}
