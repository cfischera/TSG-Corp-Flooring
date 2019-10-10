package com.tsg.fischer.flooring.controller;

import com.tsg.fischer.flooring.dao.PersistenceException;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

public interface ServiceLayer {

    public void loadAll() throws PersistenceException;

    public boolean saveAll() throws PersistenceException;

    public List<String> orderStringList(String s) throws FileNotFoundException;

    public String addOrder(String date, String name, String state, String product, BigDecimal area);

    public List<String> productStringList();

    public List<String> stateStringList();

    public boolean isValidDate(String date, boolean mustBeFuture);

    public void addOrderConfirm(boolean confirmation);

    public String removeOrder(String date, int orderNumber);

    public void removeOrderConfirm(boolean confirmation);

    public List<String> getEditOrder(String date, int orderNumber);

    public String editOrder(String name, String state, String product, BigDecimal area);

    public void editOrderConfirm(boolean confirmation);

    public boolean isProduction();

    public boolean isValidName(String name);

    public boolean isValidState(String state);

    public boolean isValidProduct(String product);

    public boolean isValidArea(String area);
}
