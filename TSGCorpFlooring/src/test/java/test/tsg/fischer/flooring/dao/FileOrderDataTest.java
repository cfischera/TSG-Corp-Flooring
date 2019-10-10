package test.tsg.fischer.flooring.dao;

import com.tsg.fischer.flooring.dao.FileOrderData;
import com.tsg.fischer.flooring.dto.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class FileOrderDataTest {
    FileOrderData testDao;

    public FileOrderDataTest() {
        testDao = new FileOrderData();
    }

    @Test
    public void unmarshalItemTest() {

        String orderText = "1,Ada Lovelace,CA,25.00,Tile,249.00,3.50,4.15,871.50,1033.35,476.21,2381.06";

        Order processed = testDao.unmarshalOrder(orderText);

        Assertions.assertEquals(1, processed.getNumber(), "number should be 1");
        Assertions.assertEquals("Ada Lovelace", processed.getCustomerName(), "customer name should be Ada Lovelace");
        Assertions.assertEquals("CA", processed.getStateTerritory(), "state territory should be CA");
        Assertions.assertEquals(new BigDecimal("25.00"), processed.getTaxRate(), "tax rate should be 25.00");
        Assertions.assertEquals("Tile", processed.getProductType(), "product type should be tile");
        Assertions.assertEquals(new BigDecimal("249.00"), processed.getArea(), "area should be 249.00");
        Assertions.assertEquals(new BigDecimal("3.50"), processed.getCostPerSquareFoot(), "cost per square foot should be 3.50");
        Assertions.assertEquals(new BigDecimal("4.15"), processed.getLaborCostPerSquareFoot(), "labor cost per square foot should be 4.15");
        Assertions.assertEquals(new BigDecimal("871.50"), processed.getMaterialCost(), "material cost should be 871.50");
        Assertions.assertEquals(new BigDecimal("1033.35"), processed.getLaborCost(), "labor cost should be 1033.35");
        Assertions.assertEquals(new BigDecimal("476.21"), processed.getTax(), "tax should be 476.21");
        Assertions.assertEquals(new BigDecimal("2381.06"), processed.getTotal(), "total should be 2381.06");
    }

    @Test
    public void marshalItemTest() {
        Order entered = new Order(2, "Doctor Who", "WA", new BigDecimal("9.25"),
                "Wood", new BigDecimal("243.00"), new BigDecimal("5.15"), new BigDecimal("4.75"),
                new BigDecimal("1251.45"), new BigDecimal("1154.25"), new BigDecimal("216.51"),
                new BigDecimal("2622.21"));

        String orderText = testDao.marshalOrder(entered);

        Assertions.assertEquals("2,Doctor Who,WA,9.25,Wood,243.00,5.15,4.75,1251.45,1154.25,216.51,2622.21",
                orderText, "Lines should match");
    }

    @Test
    public void circleOfMarshalingTest(){

        Order entered = new Order(3, "Albert Einstein", "KY", new BigDecimal("6.00"),
                "Carpet", new BigDecimal("217.00"), new BigDecimal("2.25"), new BigDecimal("2.10"),
                new BigDecimal("488.25"), new BigDecimal("455.70"), new BigDecimal("56.64"),
                new BigDecimal("1000.59"));

        String tempOrderText = testDao.marshalOrder(entered);
        Order processed = testDao.unmarshalOrder(tempOrderText);

        Assertions.assertEquals(entered, processed, "Orders should match");
    }
}
