package src.main.java.project.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.main.java.project.abstractclasses.Product;
import src.main.java.project.interfaces.*;

class DigiCam extends Product implements Rentable, Buyable {
    private static int nextId = 1;
    private String model;
    private int batteryLife;
    private boolean isRented;
    private boolean isOn;

    // No argument constructor
    public DigiCam() {
        super(nextId++, "Unknown", 0.0, 0);
        this.model = "";
        this.isRented = false;

        // Randomly set the battery between 0 to 100%
        this.batteryLife = (int) (Math.random() * 101);
    }

    // Product with type, price, stock, model, and isRented
    // id and batteryLife is assigned automatically
    public DigiCam(String type, double price, int stock, String model, boolean isRented) {
        super(nextId++, type, price, stock);
        this.model = model;
        this.isRented = isRented;
        this.batteryLife = (int) (Math.random() * 101);
    }

    @Override
    public void describe() {
        System.out.println("This is the ultimate DigiCam that will change your photo expereince!");
    }

    @Override
    public void usageInstruction() {
        // More detailed instruction to be implemented
        System.out.println("1. Press the power on button.");
        System.out.println("2. Take a photo!");
    }

    // Getters and setters
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getBatteryLife() {
        return batteryLife;
    }

    public void setBatteryLife(int batteryLife) {
        this.batteryLife = batteryLife;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        this.isRented = rented;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

    // Unique methods for DigiCam class
    public void powerOn() {
        if (batteryLife >= 10) {
            batteryLife -= 10;
        } else {
            batteryLife = 0;
            System.out.println("Battery is 0%. Please replace your battery.");
        }
    }

    public void powerOff() {
        isOn = false;
    }

    public void replaceBattery() {
        batteryLife = 100;
    }

    // Interfaces
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
        if (isRentable()) {
            super.setStock(super.getStock() + 1);
            customer.returnProduct(this);
        }
    }

    @Override
    public void rent(Customer customer) {
        super.setStock(super.getStock() - 1);
        customer.rentProduct(this);
    }
}
