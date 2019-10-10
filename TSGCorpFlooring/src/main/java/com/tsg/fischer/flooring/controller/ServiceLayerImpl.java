package com.tsg.fischer.flooring.controller;

import com.tsg.fischer.flooring.dao.DataAccessor;
import com.tsg.fischer.flooring.dao.ModeAccessor;
import com.tsg.fischer.flooring.dao.PersistenceException;
import com.tsg.fischer.flooring.dto.Order;
import com.tsg.fischer.flooring.dto.Product;
import com.tsg.fischer.flooring.dto.StateTax;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class ServiceLayerImpl implements ServiceLayer {

    private DataAccessor orderData, productData, stateTaxData;
    private ModeAccessor modeData;

    private String tempDate;
    private Order tempOrder;


    public ServiceLayerImpl(DataAccessor orderData, DataAccessor productData, DataAccessor stateTaxData,
                            ModeAccessor modeData) {
        this.orderData = orderData;
        this.productData = productData;
        this.stateTaxData = stateTaxData;
        this.modeData = modeData;
    }

    @Override
    public boolean isProduction() {
        return modeData.getMode().equals("Production");
    }

    @Override
    public void loadAll() throws PersistenceException {
        this.orderData.loadAll();

        this.productData.loadAll();

        this.stateTaxData.loadAll();

        this.modeData.load();
    }

    @Override
    public boolean saveAll() throws PersistenceException {
        if (this.isProduction()) {
            this.orderData.saveAll();

            this.productData.saveAll();

            this.stateTaxData.saveAll();

            return true;
        }
        else {
            return false;
        }
    }

    private Map<Integer, Order> getOneOrderMap(String s) {
            return (Map) orderData.getOne("Orders_" + s + ".txt");
    }

    @Override
    public List<String> orderStringList(String s) throws FileNotFoundException {

        List<String> strings = new ArrayList<>();

        try {
            Map<Integer, Order> map = getOneOrderMap(s);

            List<Order> newOrders = new ArrayList<>(map.values());
            for (Order o : newOrders) {
                strings.add(o.toString());
            }
        } catch(NullPointerException e) {
            throw new FileNotFoundException("No such file exists.");
        }
        return strings;
    }

    @Override
    public String addOrder(String date, String name, String state, String product, BigDecimal area) {
        tempDate = date;
        int number = calculateNumber();
        area = area.setScale(2, RoundingMode.HALF_UP);
        BigDecimal costPerSquareFoot = getCostPerSquareFoot(product).getCostPerSquareFoot();
        BigDecimal laborCostPerSquareFoot = getLaborCostPerSquareFoot(product).getLaborCostPerSquareFoot();
        BigDecimal materialCost = area.multiply(costPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
        BigDecimal laborCost = area.multiply(laborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
        BigDecimal taxRate = getTaxRate(state).getTaxRate();
        BigDecimal tax = calculateTax(materialCost, laborCost, taxRate).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = materialCost.add(laborCost).add(tax).setScale(2, RoundingMode.HALF_UP);
        tempOrder = new Order(number, name, state, taxRate, product, area,
                costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, tax, total);
        return tempOrder.toString();
    }

    public void addOrderConfirm(boolean confirmation) {
        if(confirmation) {
            addOrder(tempDate, tempOrder);
        }
        tempDate = null;
        tempOrder = null;
    }

    private void addOrder(String date, Order order) {
        Map<Integer, Order> map = getOneOrderMap(date);
        if(map != null) {
            map.put(order.getNumber(), order);
        }
        else {
            map = new TreeMap<>();
            map.put(order.getNumber(), order);
            orderData.addOne("Orders_" + date + ".txt", map);
        }
    }

    @Override
    public List<String> productStringList() {

        List<Product> products = productData.getAll();

        List<String> productStrings = new ArrayList<>();

        for(Product p: products) {
            productStrings.add(p.getType());
        }
        return productStrings;
    }

    @Override
    public List<String> stateStringList() {

        List<StateTax> states = stateTaxData.getAll();

        List<String> stateStrings = new ArrayList<>();

        for(StateTax s: states) {
            stateStrings.add(s.getAbbreviation());
        }
        return stateStrings;
    }

    @Override
    public String removeOrder(String date, int orderNumber) {
        Map<Integer, Order> map = getOneOrderMap(date);
        tempDate = date;
        tempOrder = map.get(orderNumber);
        return map.get(orderNumber).toString();
    }

    @Override
    public void removeOrderConfirm(boolean confirmation) {
        if(confirmation) {
            removeOrder(tempDate, tempOrder);
        }
        tempDate = null;
        tempOrder = null;
    }

    private void removeOrder(String date, Order toBeRemoved) {
        Map<Integer, Order> map = getOneOrderMap(date);
        map.remove(toBeRemoved.getNumber());
    }

    @Override
    public List<String> getEditOrder(String date, int orderNumber) {
        Map<Integer, Order> map = getOneOrderMap(date);
        tempDate = date;
        tempOrder = map.get(orderNumber);
        List<String> orderInfo = new ArrayList<>();
        orderInfo.add(0, map.get(orderNumber).getCustomerName());
        orderInfo.add(1, map.get(orderNumber).getStateTerritory());
        orderInfo.add(2, map.get(orderNumber).getProductType());
        orderInfo.add(3, map.get(orderNumber).getArea().toString());
        return orderInfo;
    }

    @Override
    public String editOrder(String name, String state, String product, BigDecimal area) {
        area = area.setScale(2, RoundingMode.HALF_UP);
        BigDecimal costPerSquareFoot = getCostPerSquareFoot(product).getCostPerSquareFoot();
        BigDecimal laborCostPerSquareFoot = getLaborCostPerSquareFoot(product).getLaborCostPerSquareFoot();
        BigDecimal materialCost = area.multiply(costPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
        BigDecimal laborCost = area.multiply(laborCostPerSquareFoot).setScale(2, RoundingMode.HALF_UP);
        BigDecimal taxRate = getTaxRate(state).getTaxRate();
        BigDecimal tax = calculateTax(materialCost, laborCost, taxRate).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = materialCost.add(laborCost).add(tax).setScale(2, RoundingMode.HALF_UP);
        tempOrder = new Order(tempOrder.getNumber(), name, state, taxRate, product, area,
                costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, tax, total);
        return tempOrder.toString();
    }

    @Override
    public void editOrderConfirm(boolean confirmation) {
        if(confirmation) {
            editOrder(tempDate, tempOrder);
        }
        tempDate = null;
        tempOrder = null;
    }

    private void editOrder(String date, Order toBeEdited) {
        Map<Integer, Order> map = getOneOrderMap(date);
        map.put(toBeEdited.getNumber(), toBeEdited);
    }

    @Override
    public boolean isValidDate(String date, boolean mustBeFuture) {
        // Valid dates range from the years 2000 to the year 2029
        if(!mustBeFuture) {
            Pattern pattern = Pattern.compile("^(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[01])(20[0-2][0-9])$");
            return pattern.matcher(date).matches();
        }
        else {

            Pattern pattern = Pattern.compile("^(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[01])(20[0-2][0-9])$");
            if (!pattern.matcher(date).matches()) {
                return false;
            }
            return isFutureDate(date);
        }
    }

    private boolean isFutureDate(String date) {
        //this method inspired by user Prabhath kesav on Stack Overflow
        try {
            Date today = new Date();
            SimpleDateFormat df = new SimpleDateFormat("MMddyyyy");
            Date entered = df.parse(date);
            Long l = entered.getTime();
            Date next = new Date(l);

            if(next.after(today)) {
                return true;
            } else {
                return false;
            }
        } catch(ParseException e) {
            System.out.println("You broke my date regex.");
        }
        return false;
    }

    @Override
    public boolean isValidName(String name) {
        // only accepts letters, numbers, and whitespace
        Pattern pattern = Pattern.compile("^[a-zA-z0-9\\s]+$");
        return pattern.matcher(name).matches();
    }

    @Override
    public boolean isValidState(String state) {
        List<String> states = stateStringList();

        for(String validState: states) {
            if(state.equals(validState)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isValidProduct(String product) {
        List<String> products = productStringList();

        for(String validProduct: products) {
            if(product.equals(validProduct)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isValidArea(String areaString) {
        try {
            BigDecimal area = new BigDecimal(areaString);
            return (area.compareTo(new BigDecimal("100")) >= 0);
        } catch(NumberFormatException e) {
            return false;
        }
    }

    private int calculateNumber() {
        int orderNumber = 1;
        List<Map<Integer, Order>> orderList = orderData.getAll();
        for(Map map: orderList) {
            if (map != null) {
                Set<Integer> keys = map.keySet();
                for (Integer num : keys) {
                    if (orderNumber <= num) {
                        orderNumber = num + 1;
                    }
                }
            }
        }
        return orderNumber;
    }

    private StateTax getTaxRate(String state) {
        return (StateTax)stateTaxData.getOne(state);
    }

    private Product getLaborCostPerSquareFoot(String product) {
        return (Product)productData.getOne(product);
    }

    private Product getCostPerSquareFoot(String product) {
        return (Product)productData.getOne(product);
    }

    private BigDecimal calculateTax(BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxRate) {
        BigDecimal mCAndLabor = materialCost.add(laborCost);
        BigDecimal myTaxRate = taxRate.divide(new BigDecimal("100"));
        BigDecimal tax = mCAndLabor.multiply(myTaxRate);
        return tax;
    }
}
