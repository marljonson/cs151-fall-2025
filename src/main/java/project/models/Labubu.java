package models;

import CS151Project1.src.Customer;
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
        this.isRented = false;
        this.isRare = false;
    }

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

    // Rentable interface methods
    @Override
    public boolean isRentable() {
        return super.getStock() > 0;
    }

    @Override
    public double getRentalPrice() {
        return super.getPrice();
    }

    @Override
    public void rentalReturn(Customer customer) {
    }

    @Override
    public void rent(Customer customer) {
    }
}

