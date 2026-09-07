package project.abstractclasses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import project.models.VendorTemp;
import project.models.Labubu;

class ProductTest {

    private VendorTemp vendor;
    private Labubu product;

    @BeforeEach
    void setUp() {
        vendor = new VendorTemp("Test Vendor", "vendor@test.com");
        product = new Labubu(1, 50.00, vendor, "Red", false);
    }

    @Test
    void testCalculateActualPrice_NoDiscount() {
        double actual = product.calculateActualPrice(0.0);
        assertEquals(50.00, actual);
    }

    @Test
    void testCalculateActualPrice_FiftyPercentDiscount() {
        double actual = product.calculateActualPrice(0.5);
        assertEquals(25.00, actual);
    }

    @Test
    void testCalculateActualPrice_TwentyPercentDiscount() {
        double actual = product.calculateActualPrice(0.2);
        assertEquals(40.00, actual);
    }

    @Test
    void testCalculateActualPrice_InvalidDiscountTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            product.calculateActualPrice(1.5);
        });
    }

    @Test
    void testCalculateActualPrice_InvalidDiscountNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            product.calculateActualPrice(-0.1);
        });
    }

    @Test
    void testSetPrice_Valid() {
        product.setPrice(75.50);
        assertEquals(75.50, product.getPrice());
    }

    @Test
    void testSetPrice_Negative() {
        assertThrows(IllegalArgumentException.class, () -> {
            product.setPrice(-10.0);
        });
    }

    @Test
    void testSetStock_Valid() {
        product.setStock(5);
        assertEquals(5, product.getStock());
    }

    @Test
    void testSetStock_Negative() {
        assertThrows(IllegalArgumentException.class, () -> {
            product.setStock(-1);
        });
    }

    @Test
    void testGetVendorProductId() {
        assertEquals(1, product.getVendorProductId());
    }

    @Test
    void testGetType() {
        assertEquals("Labubu", product.getType());
    }

    @Test
    void testPriceRounding() {
        product.setPrice(19.99999);
        assertEquals(20.00, product.getPrice());
    }
}
