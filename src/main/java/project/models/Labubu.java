<<<<<<< HEAD
package main.java.project.models;

import main.java.project.abstractclasses.Product;

public class Labubu extends Product{

    //attributes
    private String color;
    private boolean isRented;
    private boolean isRare;

    //Constructors (will provide 2 constructors)
    public Labubu(){

        super();
        this.color = "default";
=======
package models;

import abstractclasses.Product;
import interfaces.Rentable;

class Labubu extends Product implements Rentable {
    private static int nextId = 1;
    private String color;
    private boolean isRented;
    private boolean isRare;
    private boolean isReserved;

    // Custom no-args constructor
    public Labubu() {
        super(nextId++, "Unknown", 0.0, 0);
        this.color = "";
>>>>>>> origin/main
        this.isRented = false;
        this.isRare = false;
    }

<<<<<<< HEAD
    public Labubu(int id, String type, double price, int stock, String color, boolean isRented, boolean isRare){

        super(id, type, price, stock);
        this.color = color;
        this.isRented = isRented;
        this.isRare = isRare;
    }

    //override abstract methods of Product
    @Override
    public void describe(){
        System.out.print("Your labubu is ");

        //check Rare or not 
        if (this.isRare == true)
            System.out.println("very rare!"); //if rare
        else
            System.out.println("very cute!"); //if not rare
    }

    @Override
    public void usageInstruction(){
        System.out.println("Please keep the box and the card if you want to preserve resale or collection value!");
        System.out.println("Please keep them away from direct sunlight to prevent color fading");
    }

    //override methods of Rentable interface

    //waiting for Rentable interface to be implemented by Miyuki


    //Labubu's specific method
   
    //if a Labubu becomes Rare, changes its price
    public void markAsRare(){
        double currPrice = getPrice();
        this.isRare = true;
        setPrice(currPrice + (currPrice * 0.5));
    }

    //getters and setters 

    //for color
    public void setColor(String color){ this.color = color; }
    public String getColor(){ return this.color; }

    //for isRented
    public void setIsRented(boolean isRented) { this.isRented = isRented; }
    public boolean getIsRented() { return this.isRented};

    //for isRare
    public void setIsRare(boolean isRare) { this.isRare = isRare; }
    public boolean getIsRare() { return this.isRare; }


}
=======
    // Constructor
    public Labubu(String color, double price) {
        this.color = color;
    }

    @Override
    public void describe() {
        System.out.println("Charm all your friends (and potential lovers) with a friendly Labubu!");
    }

    @Override
    public void usageInstruction() {
        System.out.println("Hang on your backpack or on your waist with a carabiner.");
    }

    // Getters and setters
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean getIsRented() {
        return isRented;
    }

    public void setIsRented(boolean isRented) {
        this.isRented = isRented;
    }

    public boolean getIsRare() {
        return isRare;
    }

    public void setIsRare(boolean isRare) {
        this.isRare = isRare;
    }

    public boolean getIsReserved() {
        return isReserved;
    }

    public void setIsReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }
}

>>>>>>> origin/main
