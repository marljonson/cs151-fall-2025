package project.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LabubuTest {

    private VendorTemp vendor;
    private Labubu labubu;

    @BeforeEach
    void setUp() {
        vendor = new VendorTemp("Pop Shop", "shop@example.com");
    }

    @Test
    void testLabubuCreation() {
        labubu = new Labubu(1, 25.00, vendor, "White", false);
        assertNotNull(labubu);
        assertEquals("White", labubu.getColor());
        assertEquals(false, labubu.getIsRare());
    }

    @Test
    void testLabubuPrice() {
        labubu = new Labubu(1, 25.00, vendor, "Pink", false);
        assertEquals(25.00, labubu.getPrice());
    }

    @Test
    void testLabubuType() {
        labubu = new Labubu(2, 30.00, vendor, "Blue", true);
        assertEquals("Labubu", labubu.getType());
    }

    @Test
    void testMarkAsRare() {
        labubu = new Labubu(1, 20.00, vendor, "White", false);
        labubu.markAsRare();
        assertEquals(30.00, labubu.getPrice()); // 20 + (20 * 0.5)
        assertEquals(true, labubu.getIsRare());
    }

    @Test
    void testLabubuIsRentable() {
        labubu = new Labubu(1, 25.00, vendor, "White", false);
        assertTrue(labubu.isRentable()); // stock is 1 by default
    }

    @Test
    void testLabubuInvalidColor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Labubu(1, 25.00, vendor, "", false);
        });
    }

    @Test
    void testLabubuNullColor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Labubu(1, 25.00, vendor, null, false);
        });
    }
}
