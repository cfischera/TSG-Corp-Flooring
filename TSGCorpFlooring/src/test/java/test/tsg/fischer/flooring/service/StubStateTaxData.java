package test.tsg.fischer.flooring.service;

import com.tsg.fischer.flooring.dao.DataAccessor;
import com.tsg.fischer.flooring.dao.PersistenceException;
import com.tsg.fischer.flooring.dto.StateTax;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class StubStateTaxData implements DataAccessor<StateTax> {

    private Map<String, StateTax> stateTaxes = new TreeMap<>();

    @Override
    public void loadAll() throws PersistenceException {
        StateTax cali = new StateTax("CA", "California", new BigDecimal("25.00"));
        StateTax kenty = new StateTax("KY", "Kentucky", new BigDecimal("6.00"));
        StateTax tex = new StateTax("TX", "Texas", new BigDecimal("4.45"));

        stateTaxes.put(cali.getAbbreviation(), cali);
        stateTaxes.put(kenty.getAbbreviation(), kenty);
        stateTaxes.put(tex.getAbbreviation(), tex);
    }

    @Override
    public void saveAll() throws PersistenceException {
        // not needed
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
}
