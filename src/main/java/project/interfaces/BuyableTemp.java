package interfaces;

import java.time.Instant;

public interface BuyableTemp {

    boolean isBuyable(int userInputQty); //is there stock >= user's input quantity? check
    
    //might move this 2 to product as concrete methods 
    double getUnitPrice(); //same as getPrice (we might need to change this to BigDecimal because double don't work well with money, then round that to 2 decimals)
    double getEffectiveUnitPrice(Instant now); //if the vendor(owner) has an active promo window, 
                                                //we will apply discountFraction to the unitPrice 
                                                //Instant now is the time customer try to buy an item
    
    double quote(int amount, Instant now);//must use getEffectiveUnitPrice(Instant now), (only create 1 Instant now when the user try to buy for consistency (same time))
    double buy(int amount, Instant quotedAt, double expectedTotal); //only call this after Giving the user selects to see Quote 
                                                                    //must use quotedAt time so honor the quote, expectedTotal is to avoid inconsistent checking out total price problems
                                                                    //if recomputedTotal != expectedTotal, use expectedTotal
                            //decrement stock, multiply it with the amount and 
                            //return the total amount customer need to pay/(outside of buy() use this return to add to the owner's balance 

    //added these here just so we can get 4 methods for this interface
    int availableQty(); 
    int getMinPurchaseQty(); 
    int getMaxPurchaseQty();
}
