package interfaces;

import java.time.Instant;

public interface BuyableTemp {

    boolean isBuyable(int userInputQty); //is there stock >= user's input quantity? check
    
    double getUnitPrice(); //same as getPrice (we might need to change this to BigDecimal because double don't work well with money then round to 2 decimals)
    double getEffectiveUnitPrice(Instant now); //if the vendor(owner) has an active promo window, 
                                                //we will apply discountFraction to the unitPrice 
                                                //Instant now is the time customer try to buy an item
    
    double buy(int amount, Instant now); //must use getEffectiveUnitPrice(Instant now), (only create 1 Instant now when the user try to buy for consistency (same time))
                            //decrement stock, multiply it with the amount and 
                            //return the total amount customer need to pay/(outside of buy() use this return to add to the owner's balance 
}
