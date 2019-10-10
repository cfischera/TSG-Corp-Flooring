
package com.tsg.fischer.flooring.dao;

import com.tsg.fischer.flooring.dto.Order;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class FileOrderData implements DataAccessor<Map<Integer, Order>> {

    public static final String FOLDER_NAME = "Orders";
    public static final String DELIMITER = ",";

    private Map<String, Map<Integer, Order>> orders = new TreeMap<>();

    @Override
    public void loadAll() throws PersistenceException {
        File folder = new File(FOLDER_NAME);
        File[] listOfFiles = folder.listFiles();

        for(File f: listOfFiles) {
            Map<Integer, Order> ordersInOrders = new TreeMap<>();

            Scanner sc = null;

            try {
                sc = new Scanner(new BufferedReader(new FileReader(f)));
                String currentLine;

                Order currentItem;

                sc.nextLine();

                while(sc.hasNextLine()) {
                    currentLine = sc.nextLine();
                    currentItem = unmarshalOrder(currentLine);
                    ordersInOrders.put(currentItem.getNumber(), currentItem);
                }

                orders.put(f.getName(), ordersInOrders);
            } catch(FileNotFoundException | NoSuchElementException e) {
                throw new PersistenceException("Could not load order data into memory.", e);
            } finally {
                if(sc != null) {
                    sc.close();
                }
            }
        }
    }

    @Override
    public void saveAll() throws PersistenceException {

        for(String key: orders.keySet()) {

            if(getOne(key).isEmpty()) {
                File emptyFile = new File(FOLDER_NAME+"/"+key);
                emptyFile.delete();
            }
            else {
                PrintWriter out;

                try {
                    out = new PrintWriter(new FileWriter(new File(FOLDER_NAME+"/"+key)));
                } catch (IOException e) {
                    throw new PersistenceException("Could not save product data.", e);
                }

                out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");

                String itemAsText;

                List<Order> itemList = new ArrayList<>(this.getOne(key).values());

                for (Order currentItem : itemList) {
                    itemAsText = marshalOrder(currentItem);
                    out.println(itemAsText);
                    out.flush();
                }
                out.close();
            }
        }
    }

    @Override
    public void addOne(String key, Map<Integer, Order> orderMap) {
        orders.put(key, orderMap);
    }

    @Override
    public Map<Integer, Order> getOne(String id) {
        return orders.get(id);
    }

    @Override
    public List<Map<Integer, Order>> getAll() {
        return new ArrayList<>(orders.values());
    }

    public String marshalOrder(Order o) {
        return ""+o.getNumber()+DELIMITER+o.getCustomerName()+DELIMITER+o.getStateTerritory()+DELIMITER+
                o.getTaxRate()+DELIMITER+o.getProductType()+DELIMITER+o.getArea()+DELIMITER+
                o.getCostPerSquareFoot()+DELIMITER+o.getLaborCostPerSquareFoot()+DELIMITER+
                o.getMaterialCost()+DELIMITER+o.getLaborCost()+DELIMITER+o.getTax()+DELIMITER+o.getTotal();
    }

    public Order unmarshalOrder(String s) {
        String[] orderTokens = s.split(DELIMITER);

        return new Order(Integer.parseInt(orderTokens[0]), orderTokens[1], orderTokens[2], new BigDecimal(orderTokens[3]), orderTokens[4],
                new BigDecimal(orderTokens[5]), new BigDecimal(orderTokens[6]), new BigDecimal(orderTokens[7]), new BigDecimal(orderTokens[8]),
                new BigDecimal(orderTokens[9]), new BigDecimal(orderTokens[10]), new BigDecimal(orderTokens[11]));
    }
}
