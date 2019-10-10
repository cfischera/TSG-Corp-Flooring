package test.tsg.fischer.flooring.service;

import com.tsg.fischer.flooring.controller.ServiceLayer;
import com.tsg.fischer.flooring.controller.ServiceLayerImpl;
import com.tsg.fischer.flooring.dao.DataAccessor;
import com.tsg.fischer.flooring.dao.ModeAccessor;
import com.tsg.fischer.flooring.dto.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

public class ServiceLayerTest {

    private ServiceLayer service;

    DataAccessor stubOrder, stubProduct, stubStateTax;
    ModeAccessor stubMode;

    public ServiceLayerTest() {
        this.stubOrder = new StubOrderData();
        this.stubProduct = new StubProductData();
        this.stubStateTax = new StubStateTaxData();
        this.stubMode = new StubModeData();

        this.service = new ServiceLayerImpl(stubOrder, stubProduct, stubStateTax, stubMode);
    }

    @Test
    public void testMode() {
        try {
            stubMode.load();
            Assertions.assertFalse(service.isProduction());

            stubMode.load();
            Assertions.assertFalse(service.isProduction());

            stubMode.load();
            Assertions.assertTrue(service.isProduction());

            stubMode.load();
        } catch (Exception e) {
            System.out.println("Error: Mode Test failed: "+e.toString());
        }
    }

    @Test
    public void testDateValidation() {
        // any date 2000-2029
        Assertions.assertFalse(service.isValidDate("", false));
        Assertions.assertFalse(service.isValidDate(",af24tf", false));
        Assertions.assertFalse(service.isValidDate("12345678", false));
        Assertions.assertFalse(service.isValidDate("0101999", false));
        Assertions.assertFalse(service.isValidDate("01012030", false));

        Assertions.assertTrue(service.isValidDate("09192019", false));
        Assertions.assertTrue(service.isValidDate("01012000", false));
        Assertions.assertTrue(service.isValidDate("12122029", false));


        // future date up to 2029
        Assertions.assertFalse(service.isValidDate("10102030", true));
        Assertions.assertFalse(service.isValidDate("05102010", true));
        Assertions.assertFalse(service.isValidDate("09192019", true));

        Assertions.assertTrue(service.isValidDate("10102019", true));
        Assertions.assertTrue(service.isValidDate("05052025", true));
        Assertions.assertTrue(service.isValidDate("02202020", true));
    }

    @Test
    public void testNameValidation() {
        Assertions.assertFalse(service.isValidName(""));
        Assertions.assertFalse(service.isValidName("$,wef189bf"));
        Assertions.assertFalse(service.isValidName(","));

        Assertions.assertTrue(service.isValidName("Ada Lovelace"));
        Assertions.assertTrue(service.isValidName("sdfgafs 32fqsadf2 f2f2sfd2"));
        Assertions.assertTrue(service.isValidName("harold"));
    }

    @Test
    public void testAreaValidation() {
        Assertions.assertFalse(service.isValidArea(""));
        Assertions.assertFalse(service.isValidArea("sdfggaw"));
        Assertions.assertFalse(service.isValidArea("50"));

        Assertions.assertTrue(service.isValidArea("100"));
        Assertions.assertTrue(service.isValidArea("25376098471"));
        Assertions.assertTrue(service.isValidArea("200.309471794510489"));
    }

    @Test
    public void testProductDataAndValidation() {
        try {
            stubProduct.loadAll();

            Assertions.assertFalse(service.isValidProduct(""));
            Assertions.assertFalse(service.isValidProduct("asdf2t24t35"));
            Assertions.assertFalse(service.isValidProduct("tile"));

            Assertions.assertTrue(service.isValidProduct("Tile"));
            Assertions.assertTrue(service.isValidProduct("Carpet"));
            Assertions.assertTrue(service.isValidProduct("Laminate"));
        } catch(Exception e) {
            System.out.println("Error: Valid Product Test failed: "+e.toString());
        }
    }

    @Test
    public void testStateDataAndValidation() {
        try {
            stubStateTax.loadAll();

            Assertions.assertFalse(service.isValidState(""));
            Assertions.assertFalse(service.isValidState("wjfsasgfi34224"));
            Assertions.assertFalse(service.isValidState("California"));

            Assertions.assertTrue(service.isValidState("CA"));
            Assertions.assertTrue(service.isValidState("KY"));
            Assertions.assertTrue(service.isValidState("TX"));
        } catch(Exception e) {
            System.out.println("Error: Valid State Test failed: "+e.toString());
        }
    }

    @Test
    public void testOrderManipulation() {
        try {
            stubOrder.loadAll();

            service.addOrder("06012020", "Ada Lovelace", "CA", "Tile", new BigDecimal("249.00"));
            service.addOrderConfirm(true);

            Order testOrder = new Order(1, "Ada Lovelace", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
                    new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("871.50"),
                    new BigDecimal("1033.35"), new BigDecimal("476.21"), new BigDecimal("2381.06"));

            Map<Integer, Order> map = (Map<Integer, Order>) stubOrder.getOne("Orders_06012020");

            Assertions.assertEquals(map.get(1), testOrder, "Orders should match");
        } catch(Exception e) {
            System.out.println("Error: Manipulate Order Test failed: "+e.toString());
        }
    }
}
