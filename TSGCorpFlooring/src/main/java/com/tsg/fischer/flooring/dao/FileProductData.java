package com.tsg.fischer.flooring.dao;

import com.tsg.fischer.flooring.dto.Product;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class FileProductData implements DataAccessor<Product> {

    public static final String FILENAME = "Data/Products.txt";
    public static final String DELIMITER = ",";

    Map<String, Product> products = new TreeMap<>();

    @Override
    public void loadAll() throws PersistenceException {
        Scanner sc = null;

        try {
            sc = new Scanner(new BufferedReader(new FileReader(FILENAME)));

            String currentLine;

            Product currentItem;

            sc.nextLine();

            while(sc.hasNextLine()) {
                currentLine = sc.nextLine();
                currentItem = unmarshalProduct(currentLine);
                products.put(currentItem.getType(), currentItem);
            }
        } catch(FileNotFoundException | NoSuchElementException e) {
            throw new PersistenceException("Could not load product data into memory.", e);
        } finally {
            if(sc != null) {
                sc.close();
            }
        }
    }

    @Override
    public void saveAll() throws PersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(FILENAME));
        } catch (IOException e) {
            throw new PersistenceException("Could not save product data.", e);
        }

        out.println("ProductType,CostPerSquareFoot,LaborCostPerSquareFoot");

        String itemAsText;
        List<Product> itemList = this.getAll();
        for(Product currentItem : itemList) {
            itemAsText = marshalProduct(currentItem);
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }

    @Override
    public void addOne(String key, Product p) {
        products.put(p.getType(), p);
    }

    @Override
    public Product getOne(String id) {
        return products.get(id);
    }

    @Override
    public List<Product> getAll() {
        return new ArrayList<>(products.values());
    }

    public String marshalProduct(Product p) {
        return ""+p.getType()+DELIMITER+p.getCostPerSquareFoot()+DELIMITER+p.getLaborCostPerSquareFoot();
    }

    public Product unmarshalProduct(String s) {
        String[] productTokens = s.split(DELIMITER);

        return new Product(productTokens[0], new BigDecimal(productTokens[1]), new BigDecimal(productTokens[2]));
    }
}
