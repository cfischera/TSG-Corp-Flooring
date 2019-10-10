package test.tsg.fischer.flooring.service;

import com.tsg.fischer.flooring.dao.DataAccessor;
import com.tsg.fischer.flooring.dao.PersistenceException;
import com.tsg.fischer.flooring.dto.Product;

import java.math.BigDecimal;
import java.util.*;

public class StubProductData implements DataAccessor<Product> {


    Map<String, Product> products = new TreeMap<>();

    @Override
    public void loadAll() throws PersistenceException {
        Product carpet = new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10"));
        Product laminate = new Product("Laminate", new BigDecimal("1.75"), new BigDecimal("2.10"));
        Product tile = new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"));

        products.put(carpet.getType(), carpet);
        products.put(laminate.getType(), laminate);
        products.put(tile.getType(), tile);
    }

    @Override
    public void saveAll() throws PersistenceException {
        //not needed
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
}
