package com.tsg.fischer.flooring.dao;

public class PersistenceException extends Exception {

    public PersistenceException(String msg) {
        super(msg);
    }

    public PersistenceException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
