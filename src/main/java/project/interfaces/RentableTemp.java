package interfaces;

import java.time.Instant;

public interface RentableTemp {

    boolean isRentable(); //is there stock >= 1 ? check (don't need the parameter if user can only rent 1 item at a time)
    
    double quoteRental(Instant now); 
    /* pure review
     * use Product's getEffectiveUnitPrice at now
     */
    double rent(Instant quotedAt, double expectedTotal);
    /*
     * give the customer an option to rent after quoted
     * if the customer chooses rent, rent the product with the quotedAt price (the price the user saw)
     * set stock = 0, return the charged price (s.t we can subtract that from customer's balance and add it to the vendor)
     */

    void rentalReturn(Instant returnedAt); //might not need returnedAt if we won't charge late fees but looks really good to have this
}
