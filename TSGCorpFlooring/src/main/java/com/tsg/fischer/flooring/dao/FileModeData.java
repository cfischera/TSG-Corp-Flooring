package com.tsg.fischer.flooring.dao;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileModeData implements ModeAccessor {

    public static final String FILENAME = "Data/Mode.txt";
    public static final String DELIMITER = "::";

    private String mode;

    @Override
    public void load() throws PersistenceException {
        Scanner sc = null;

        try {
            sc = new Scanner(new BufferedReader(new FileReader(FILENAME)));

            String data = sc.nextLine();

            String[] dataSplit = data.split(DELIMITER);

            this.mode = dataSplit[1];

        } catch(FileNotFoundException | NoSuchElementException e) {
            throw new PersistenceException("Could not load mode data into memory.", e);
        } finally {
            if(sc != null) {
                sc.close();
            }
        }
    }

    @Override
    public String getMode() {
        return this.mode;
    }
}
