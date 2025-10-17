package project.models;

import project.models.VendorTemp;
import project.abstractclasses.Product;
import project.interfaces.RentableTemp;
import java.math.*;

import java.time.Instant;

public class Labubu extends Product implements RentableTemp {

    //constant type for all Labubu
    public static final String TYPE = "Labubu";
    private String color;
    private boolean isRare;

    private String generateLabubu(){
        String colors = "Red, Blue, Green, Yellow, Pink, Black, White, Purple, Orange, Cyan, Magenta";
        String[] colorArray = colors.split(", ");
        int randomIndex = (int)(Math.random() * colorArray.length);
        color = colorArray[randomIndex];
        if (color.equals("Black") || color.equals("White")){
            isRare = true;
        } else {
            isRare = false;
        }
        return color;
    }   

    //private boolean isRented; //don't need to use this because if stock <= 0, the labubu is rented

    //I don't expect us to use this anywhere but keep this here to have a no-args constructor since Java will not give us one
    //no-args constructor
    //never use this
    public Labubu() {
        super();
        this.color = "";
        this.isRare = false;
    }

    //Constructor: Serialized Labubus Constructor (each labubu has a unique ID)
    public Labubu(int vendorProductId, double price, VendorTemp owner, String color, boolean isRare){
        super(vendorProductId, TYPE, price, owner); //call Product's constructor first

        if(color == null || color.isBlank()) throw new IllegalArgumentException("color can't be null or blank.");
        
        this.color = color.trim();
        this.isRare = isRare;
    }

    //must override abstract methods of Product
    @Override
    public void describe() {
        System.out.println("Charm all your friends (and potential lovers) with a friendly Labubu!");
    }

    @Override
    public void usageInstruction() {
        System.out.println("Hang on your backpack or on your waist with a carabiner.");
    }

    //must override methods from Rentable Interface 
    @Override
    public boolean isRentable(){
        return this.getStock() > 0; //remember we are treating each Labubu as 1 item with a unique ID
    }

    @Override
    public double quoteRental(Instant now){
        double unitPriceAfterDiscount = this.getEffectiveUnitPrice(now);
        return unitPriceAfterDiscount;
    }

    @Override
    public double rent(Instant quotedAt, double expectedTotal){

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
    public void rentalReturn(Instant returnedAt){ //might not need returnedAt if we won't charge late fees but looks really good to have this


        if(returnedAt == null) throw new IllegalArgumentException("returnedAt time cannot be null");
        if(this.getStock() != 0) throw new IllegalStateException("Invalid Return: this labubu is never rented!");

        //restock
        this.setStock(1);
    }

    //to make it easier to debug
    @Override 
    public String toString(){

        String formattedPrice = String.format("%.2f", getPrice());
        return "Labubu{" +
            "id=" + getVendorProductId() +
            ", type=" + getType() +
            ", price=" + formattedPrice +
            ", stock=" + getStock() +
            ", color=" + this.color +
            ", isRare=" + isRare + 
            "}"; 

    }

    //Labubu Specific Method
    //if a Labubu becomes Rare, changes its price
    public void markAsRare(){
        double currPrice = getPrice();
        this.isRare = true;
        setPrice(currPrice + (currPrice * 0.5)); //increase the price by 50%
    }
    
    // Getters and setters
    public String getColor() { return color;}
    protected void setColor(String color) { 
        if(color == null || color.isBlank()) throw new IllegalArgumentException("Color must be non-empty!");
        this.color = color.trim();
    }

    public boolean getIsRare() { return isRare; }
    protected void setIsRare(boolean isRare) { this.isRare = isRare; }

}

