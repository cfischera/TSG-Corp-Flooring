package test.tsg.fischer.flooring.dao;

import com.tsg.fischer.flooring.dao.FileStateTaxData;
import com.tsg.fischer.flooring.dto.StateTax;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class FileStateTaxDataTest {
    FileStateTaxData testDao;

    public FileStateTaxDataTest() {
        testDao = new FileStateTaxData();
    }

    @Test
    public void unmarshalItemTest() {

        String stateTaxText = "TX,Texas,4.45";

        StateTax processed = testDao.unmarshalStateTax(stateTaxText);

        Assertions.assertEquals("TX", processed.getAbbreviation(), "abbr should be TX");
        Assertions.assertEquals("Texas", processed.getName(), "name should be Texas");
        Assertions.assertEquals(new BigDecimal("4.45"), processed.getTaxRate(), "tax rate should be 4.45");
    }

    @Test
    public void marshalItemTest() {
        StateTax entered = new StateTax("WY", "Wyoming", new BigDecimal("5.67"));

        String stateTaxText = testDao.marshalStateTax(entered);

        Assertions.assertEquals("WY,Wyoming,5.67", stateTaxText, "Lines should match");
    }

    @Test
    public void circleOfMarshalingTest(){

        StateTax entered = new StateTax("KY", "Kentucky", new BigDecimal("6.66"));

        String tempStateTaxText = testDao.marshalStateTax(entered);
        StateTax processed = testDao.unmarshalStateTax(tempStateTaxText);

        Assertions.assertEquals(entered, processed, "State Taxes should match");
    }
}
