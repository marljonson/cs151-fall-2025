package project.interfaces;

import java.time.Instant;

public interface BuyableTemp { //We won't be using BuyableTemp and Matcha or Product's Bulk constructor 

    boolean isBuyable(int userInputQty); //is there stock >= user's input quantity? check
    
    //moved this to product as concrete methods 
    double getEffectiveUnitPrice(Instant now); //if the vendor(owner) has an active promo window, 
                                                //we will apply discountFraction to the unitPrice 
                                                //Instant now is the time customer try to buy an item
    
    double quoteBuy(int amount, Instant now);//must use getEffectiveUnitPrice(Instant now), (only create 1 Instant now when the user try to buy for consistency (same time))
    double buy(int amount, Instant quotedAt, double expectedTotal); //only call this after Giving the user selects to see Quote 
                                                                    //must use quotedAt time so honor the quote, expectedTotal is to avoid inconsistent checking out total price problems
                                                                    //if recomputedTotal != expectedTotal, use expectedTotal
                            //decrement stock, multiply it with the amount and 
                            //return the total amount customer need to pay/(outside of buy() use this return to add to the owner's balance 

    //added these here just so we can get 4 methods for this interface
    /* 
    int availableQty(); 
    int getMinPurchaseQty(); 
    int getMaxPurchaseQty();
    */
}
