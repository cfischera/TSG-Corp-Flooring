package com.tsg.fischer.flooring.dao;

public interface ModeAccessor {

    public void load() throws PersistenceException;

    public String getMode();
}
