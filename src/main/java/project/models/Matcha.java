package project.models;

import project.models.VendorTemp;
import project.abstractclasses.Product;
import project.interfaces.RentableTemp;

import java.time.Instant;
public class Matcha extends Product implements RentableTemp{
    public static final String TYPE = "Matcha";
    private double cupEmptiness;

    public Matcha(int emptiness){
        super();
        this.cupEmptiness = cupEmptiness;
    }

    public double getCupEmptiness(){
        return cupEmptiness;
    }

    @Override
    public boolean isRentable() {
        return (this.getStock() > 0);
    }
    @Override
    public double quoteRental(Instant now) {
        double unitPriceAfterDiscount = this.getEffectiveUnitPrice(now);
        return unitPriceAfterDiscount;
    }
    @Override
    public double rent(Instant quotedAt, double expectedTotal) {
        if(quotedAt == null) throw new IllegalArgumentException("quotedAt can't be null");
        if(expectedTotal < 0) throw new IllegalArgumentException("expectedTotal can't be negative");
        if(!isRentable()) throw new IllegalStateException("Unavailable to rent!"); //will add a custom exception later if I have time 
        //recompute the unitPrice to be safe
        double unitPriceAfterDiscount = this.getEffectiveUnitPrice(quotedAt);
        //safeguard to avoid mismatch only due to the rounding error
        unitPriceAfterDiscount = Math.round(unitPriceAfterDiscount * 100.0) / 100.0; 
        double expected = Math.round(expectedTotal * 100.0) / 100.0;
        //honor the quotedAt time
        if (unitPriceAfterDiscount != expected){
            unitPriceAfterDiscount = expected;
        }
        //update/decrement stock
        this.setStock(this.getStock() - 1);

        return unitPriceAfterDiscount;
    }

    @Override
    public void rentalReturn(Instant returnedAt) {
        if(returnedAt == null) throw new IllegalArgumentException("returnedAt time cannot be null");
        if(this.getStock() != 0) throw new IllegalStateException("Invalid Return: this matcha is never rented!");
        this.setStock(1);
    }

    @Override
    public void describe() {
        System.out.println("Try the greenest, most eco-friendly, sustainable, ethical, gorgeous, tasty matcha.");
    }

    @Override
    public void usageInstruction() {
        System.out.println("1. Insert Straw");
        System.out.println("2. Have a drink");

    }    
    
}
