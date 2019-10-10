package test.tsg.fischer.flooring.dao;

import com.tsg.fischer.flooring.dao.FileProductData;
import com.tsg.fischer.flooring.dto.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class FileProductDataTest {
    FileProductData testDao;

    public FileProductDataTest() {
        testDao = new FileProductData();
    }

    @Test
    public void unmarshalItemTest() {

        String productText = "Tile,3.50,4.15";

        Product processed = testDao.unmarshalProduct(productText);

        Assertions.assertEquals("Tile", processed.getType(), "type should be tile");
        Assertions.assertEquals(new BigDecimal("3.50"), processed.getCostPerSquareFoot(), "costPerSquareFoot should be 3.50");
        Assertions.assertEquals(new BigDecimal("4.15"), processed.getLaborCostPerSquareFoot(), "laborCostPerSquareFoot should be 4.15");
    }

    @Test
    public void marshalItemTest() {
        Product entered = new Product("Hardwood", new BigDecimal("2.99"), new BigDecimal("5.67"));

        String productText = testDao.marshalProduct(entered);

        Assertions.assertEquals("Hardwood,2.99,5.67", productText, "Lines should match");
    }

    @Test
    public void circleOfMarshalingTest(){

        Product entered = new Product("Carpet", new BigDecimal("0.76"), new BigDecimal("6.66"));

        String tempProductText = testDao.marshalProduct(entered);
        Product processed = testDao.unmarshalProduct(tempProductText);

        Assertions.assertEquals(entered, processed, "Products should match");
    }
}
