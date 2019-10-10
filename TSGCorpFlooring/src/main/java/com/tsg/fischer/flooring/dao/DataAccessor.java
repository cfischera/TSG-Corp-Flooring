package com.tsg.fischer.flooring.dao;

import java.util.List;

public interface DataAccessor<T> {

    public void loadAll() throws PersistenceException;

    public void saveAll()throws PersistenceException;

    public void addOne(String key, T t);

    public T getOne(String id);

    public List<T> getAll();
}
