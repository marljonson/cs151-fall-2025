package models;

import abstractclasses.Product;
import interfaces.Rentable;

public class Labubu extends Product implements Rentable {

    //constant type for all Labubu
    public static final String TYPE = "Labubu";

    private static int nextId = 1;
    private String color;
    private boolean isRented;
    private boolean isRare;
    //private boolean isReserved;

    // Custom no-args constructor
    public Labubu() {
        super(nextId++, TYPE , 0.0, 0);
        this.color = "";
        this.isRented = false;
        this.isRare = false;
    }

     //what is this?
    /* 
    public Labubu(String color, double price) {
        this.color = color;
    }
    */

    // Constructor
    public Labubu(int id,  double price, int stock, String color, boolean isRented, boolean isRare){
        super(id, TYPE, price, stock);

        if(color == null) throw new IllegalArgumentException("Color must be non-empty!");
        this.color = color;
        this.isRented = isRented;
        this.isRare = isRare;
    }

    //override abstract methods of Product
    @Override
    public void describe() {
        System.out.println("Charm all your friends (and potential lovers) with a friendly Labubu!");
    }

    @Override
    public void usageInstruction() {
        System.out.println("Hang on your backpack or on your waist with a carabiner.");
    }

    //override methods from Rentable Interface 
    @Override
    public boolean isRentable(){
        
        return !isRented && getStock() > 0; //are we gonna treat each Labubu as 1 item or multiple items
    }

    @Override
    public double getRentalPrice(){
        return 5.0; //$5 to rent a labubu
    }

    @Override
    public void rentalReturn(){

        if(!isRented){
            System.out.println("Invalid Return: item is not currently rented"); 
            return;
        }
        //else 
        setStock(getStock() + 1);
        isRented = false;
        //System.out.println(customer.userId + "return the product!"); //won't be using any parameter
    }

    @Override
    public void rent(){

        if(!isRentable()){ //reusing isRentable()
            System.out.println("This Labubu is not available now");
            return;
        }

        //else
        setStock(getStock() - 1);
        isRented = true;
        //System.out.println(customer.userId + "return the product");  //won't be using any parameter for now
    }

    @Override 
    public String toString(){

        String formattedPrice = String.format("%.2f", getPrice());
        return "Labubu{" +
            "id=" + getId() +
            ", type=" + getType() +
            ", price=" + formattedPrice +
            ", stock=" + getStock() +
            ", color=" + this.color +
            ", isRented=" + isRented +
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
    public void setColor(String color) { 
        if(color == null) throw new IllegalArgumentException("Color must be non-empty!");
        this.color = color;
    }

    public boolean getIsRented() { return isRented; }
    public void setIsRented(boolean isRented) { this.isRented = isRented; }

    public boolean getIsRare() { return isRare; }
    public void setIsRare(boolean isRare) { this.isRare = isRare; }

    //won't be using these
    /* 
    public boolean getIsReserved() { return isReserved; }
    public void setIsReserved(boolean isReserved) { this.isReserved = isReserved; }
    */
}

