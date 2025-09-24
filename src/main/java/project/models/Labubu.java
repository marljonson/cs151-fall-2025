package main.java.project.models;

import main.java.project.abstractclasses.Product;
import main.java.project.interfaces.Rentable;

public class Labubu extends Product implements Rentable {
    private static int nextId = 1;
    private String color;
    private boolean isRented;
    private boolean isRare;
    private boolean isReserved;

    // Custom no-args constructor
    public Labubu() {
        super(nextId++, "Unknown", 0.0, 0);
        this.color = "";
        this.isRented = false;
        this.isRare = false;
    }

    // Constructor
    public Labubu(String color, double price) {
        this.color = color;
    }

    public Labubu(int id, String type, double price, int stock, String color, boolean isRented, boolean isRare){
        super(id, type, price, stock);
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
        return true;
    }

    @Override
    public double getRentalPrice(){
        return 5.0; //$5 to rent a labubu
    }

    @Override
    public void rentalReturn(Customer customer){
        //continue after importing Customer to the correct package

    }

    @Override
    public void rent(Customer customer){
         //continue after importing Customer to the correct package

    }


    //Labubu Specific Method
    //if a Labubu becomes Rare, changes its price
    public void markAsRare(){
        double currPrice = getPrice();
        this.isRare = true;
        setPrice(currPrice + (currPrice * 0.5));
    }

    // Getters and setters
    public String getColor() { return color;}
    public void setColor(String color) { this.color = color;}

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

