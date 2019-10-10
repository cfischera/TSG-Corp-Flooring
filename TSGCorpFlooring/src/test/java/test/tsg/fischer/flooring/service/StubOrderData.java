package test.tsg.fischer.flooring.service;

import com.tsg.fischer.flooring.dao.DataAccessor;
import com.tsg.fischer.flooring.dao.PersistenceException;
import com.tsg.fischer.flooring.dto.Order;

import java.math.BigDecimal;
import java.util.*;

public class StubOrderData implements DataAccessor<Map<Integer, Order>> {

    private Map<String, Map<Integer, Order>> orders = new TreeMap<>();

    @Override
    public void loadAll() throws PersistenceException {
        Map<Integer, Order> testMap = new TreeMap<>();

        Order testOrder = new Order(1, "Ada Lovelace", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
                                    new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("871.50"),
                                    new BigDecimal("1033.35"), new BigDecimal("476.21"), new BigDecimal("2381.06"));

        testMap.put(testOrder.getNumber(), testOrder);

        orders.put("Orders_06012020.txt", testMap);
    }

    @Override
    public void saveAll() throws PersistenceException {
        // not needed
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
}
