package com.tsg.fischer.flooring.dao;

import com.tsg.fischer.flooring.dto.StateTax;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class FileStateTaxData implements DataAccessor<StateTax> {

    public static final String FILENAME = "Data/Taxes.txt";
    public static final String DELIMITER = ",";

    private Map<String, StateTax> stateTaxes = new TreeMap<>();

    @Override
    public void loadAll() throws PersistenceException {
        Scanner sc = null;

        try {
            sc = new Scanner(new BufferedReader(new FileReader(FILENAME)));

            String currentLine;

            StateTax currentItem;

            sc.nextLine();

            while(sc.hasNextLine()) {
                currentLine = sc.nextLine();
                currentItem = unmarshalStateTax(currentLine);
                stateTaxes.put(currentItem.getAbbreviation(), currentItem);
            }
        } catch(FileNotFoundException e) {
            throw new PersistenceException("Could not load state tax data into memory.", e);
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
            throw new PersistenceException("Could not save state tax data.", e);
        }

        out.println("StateAbbreviation,StateName,TaxRate");

        String itemAsText;
        List<StateTax> itemList = this.getAll();
        for(StateTax currentItem : itemList) {
            itemAsText = marshalStateTax(currentItem);
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }

    @Override
    public void addOne(String key, StateTax sT) {
        stateTaxes.put(sT.getAbbreviation(), sT);
    }

    @Override
    public StateTax getOne(String id) {
        return stateTaxes.get(id);
    }

    @Override
    public List<StateTax> getAll() {
        return new ArrayList<>(stateTaxes.values());
    }

    public String marshalStateTax(StateTax sT) {
        return ""+sT.getAbbreviation()+DELIMITER+sT.getName()+DELIMITER+sT.getTaxRate();
    }

    public StateTax unmarshalStateTax(String s) {

        String[] stateTaxTokens = s.split(DELIMITER);

        return new StateTax(stateTaxTokens[0], stateTaxTokens[1], new BigDecimal(stateTaxTokens[2]));
    }
}
