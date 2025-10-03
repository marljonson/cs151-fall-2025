package models;

import abstractclasses.Product;
import interfaces.Rentable;

public class DigiCam extends Product implements Rentable {
    private static int nextId = 1;
    private static final String TYPE = "DigiCam";
    private String model;
    private int batteryLife;
    private boolean isRented;
    private boolean isOn;

    // No-argument constructor
    public DigiCam() {
        super(nextId++, TYPE, 0.0, stock);
        this.model = "";
        this.isRented = false;
        this.isOn = false;

        // Randomly set the battery between 0 to 100%
        this.batteryLife = (int) (Math.random() * 101);
    }

    // Product with type, price, stock, model, and isRented
    // id and batteryLife is assigned automatically
    public DigiCam(double price, String model, boolean isRented) {
        super(nextId++, TYPE, price, stock);
        this.model = model;
        this.isRented = isRented;
        this.isOn = false;
        this.batteryLife = (int) (Math.random() * 101);
    }

    // Override methods from the Product class
    @Override
    public void describe() {
        System.out.println("This is the ultimate DigiCam that will change your photo experience!");
    }

    @Override
    public void usageInstruction() {
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
        if (isOn) {
            System.out.println("Camera is already on!");
            return;
        }
        if (batteryLife >= 10) {
            batteryLife -= 10;
            isOn = true;
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

    public void checkPower() {
        System.out.println("Battery is " + batteryLife + "%");
    }

    // Rentable interface methods
    @Override
    public boolean isRentable() {
        return !isRented;
    }

    @Override
    public double getRentalPrice() {
        return super.getPrice();
    }

    @Override
    public void rentalReturn() {
        if (!isRented) {
            System.out.println("This camera was not rented yet.");
        } else {
            setStock(getStock() + 1);       
            isRented = false;
            powerOff(); 
        }
    }

    @Override
    public void rent() {
        if (isRented) {
            System.out.println("This camera is already rented.");
        } else {
            setStock(getStock() - 1);         
            isRented = true;
        }
    }

    @Override
    public String toString() {
        return "DigiCam {" +
                "id=" + getId() +
                ", model='" + model + 
                ", batteryLife=" + batteryLife +
                ", price=" + getPrice() +
                ", isRented=" + isRented +
                ", isOn=" + isOn +
                '}';
    }
}
