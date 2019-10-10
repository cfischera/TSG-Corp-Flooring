package com.tsg.fischer.flooring.controller;

import com.tsg.fischer.flooring.dao.PersistenceException;
import com.tsg.fischer.flooring.view.View;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public class Controller {

    private ServiceLayer service;
    private View view;

    public Controller(ServiceLayer service, View view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        start();

        boolean keepRunning = true;

        while(keepRunning) {
            showMenu();
            int menuChoice = getMenuChoice();
            switch(menuChoice) {
                case 1:
                    showOrders();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    save();
                    break;
                case 6:
                    keepRunning = false;
                    break;
                default:
                    break;
            }
        }
        if(service.isProduction()) {
            String toSave = view.askForSave();
            while(!validConfirmation(toSave)) {
                view.displayError(("Invalid Response"));
                toSave = view.askForSave();
            }
            if(toSave.equalsIgnoreCase("Y")) {
                save();
            }
        }
        end();
    }

    private void start() {
        try {
            service.loadAll();
        } catch(PersistenceException e) {
            view.displayError(e.getMessage());
            end();
        }
        view.displayWelcome();
    }

    private void showMenu() {
        view.displayMenu();
    }

    private int getMenuChoice() {
        return view.getMenuChoice();
    }

    private void showOrders() {
        try {
            String date = view.getDate();
            while((!service.isValidDate(date, false))) {
                view.displayError("Invalid Date");
                date = view.getDate();
            }
            view.displayOrders(service.orderStringList(date));
        } catch(FileNotFoundException e) {
            view.displayError("No Orders Exist for that Date");
        }
    }

    private void addOrder() {
        String date = view.getDate();
        while((!service.isValidDate(date, true))) {
            view.displayError("Invalid Date");
            date = view.getDate();
        }
        String name = view.getName();
        while((!service.isValidName(name))) {
            view.displayError("Invalid Name");
            name = view.getName();
        }
        String state = view.getState(service.stateStringList());
        while((!service.isValidState(state))) {
            view.displayError("Invalid State");
            state = view.getState(service.stateStringList());
        }
        String product = view.getProduct(service.productStringList());
        while((!service.isValidProduct(product))) {
            view.displayError("Invalid Product");
            product = view.getProduct(service.productStringList());
        }
        String area = view.getArea();
        while((!service.isValidArea(area))) {
            view.displayError("Invalid Area (must be 100.00 or greater)");
            area = view.getArea();
        }
        String orderString = service.addOrder(date, name, state, product, new BigDecimal(area));
        String confirmation = view.confirmOrder(orderString, "add");
        while(!validConfirmation(confirmation)) {
            confirmation = view.confirmOrder(orderString, "add");
        }
        service.addOrderConfirm(confirmation.equalsIgnoreCase("Y"));
    }

    private boolean validConfirmation(String confirmation) {
        if(confirmation.length() != 1) {
            return false;
        }
        else if(confirmation.equalsIgnoreCase("Y")) {
            return true;
        }
        else {
            return confirmation.equalsIgnoreCase("N");
        }
    }

    private void editOrder() {
        try {
            String date = view.getDate();
            while((!service.isValidDate(date, false))) {
                view.displayError("Invalid Date");
                date = view.getDate();
            }
            view.displayOrders(service.orderStringList(date));

            List<String> editInfo = service.getEditOrder(date, view.getOrderNumber());

            String name = view.getName(editInfo.get(0));
            while((!service.isValidName(name))) {
                view.displayError("Invalid Name");
                name = view.getName(editInfo.get(0));
            }
            String state = view.getState(service.stateStringList(), editInfo.get(1));
            while((!service.isValidState(state))) {
                view.displayError("Invalid State");
                state = view.getState(service.stateStringList(), editInfo.get(1));
            }
            String product = view.getProduct(service.productStringList(), editInfo.get(2));
            while((!service.isValidProduct(product))) {
                view.displayError("Invalid Product");
                product = view.getProduct(service.productStringList(), editInfo.get(2));
            }
            String area = view.getArea(editInfo.get(3));
            while((!service.isValidArea(area))) {
                view.displayError("Invalid Area (must be greater than 100)");
                area = view.getArea(editInfo.get(3));
            }

            String editString = service.editOrder(name, state, product, new BigDecimal(area));


            String confirmation = view.confirmOrder(editString, "edit");
            while(!validConfirmation(confirmation)) {
                confirmation = view.confirmOrder(editString, "edit");
            }
            service.editOrderConfirm(confirmation.equalsIgnoreCase("Y"));
        } catch(FileNotFoundException e) {
            view.displayError("No Orders Exist for that Date");
        } catch(NullPointerException e) {
            view.displayError("No such Order Exists");
        }
    }

    private void removeOrder() {
        try {
            String date = view.getDate();
            while((!service.isValidDate(date, false))) {
                view.displayError("Invalid Date");
                date = view.getDate();
            }
            view.displayOrders(service.orderStringList(date));
            String removeString = service.removeOrder(date, view.getOrderNumber());
            String confirmation = view.confirmOrder(removeString, "remove");
            while(!validConfirmation(confirmation)) {
                confirmation = view.confirmOrder(removeString, "remove");
            }
            service.removeOrderConfirm(confirmation.equalsIgnoreCase("Y"));
        } catch(FileNotFoundException e) {
            view.displayError("No Orders Exist for that Date");
        } catch(NullPointerException e) {
            view.displayError("No such Order Exists");
        }
    }

    private void save() {
        try {
            if(service.saveAll()) {
                view.displaySave();
            }
            else {
                view.displayError("Invalid Mode");
            }
        } catch(PersistenceException e) {
            view.displayError(e.getMessage());
            end();
        }
    }


    private void end() {
        view.displayFarewell();
        System.exit(0);
    }
}
