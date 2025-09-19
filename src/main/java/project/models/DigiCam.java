package src.main.java.project.models;

import java.util.Random;

class DigiCam extends Product {
    private String model;
    private int batteryLife;
    private boolean isRented;
    private boolean isOn;

    public DigiCam() {
        super();
        this.model = "";
        this.isRented = false;
        this.reservedBy = -1;

        // Randomly set the battery between 0 to 100%
        this.batteryLife = (int) (Math.random() * 101);
    }

    public DigiCam(int ID, String type, double price, int stock, String model, boolean isRented) {
        super(ID, type, price, stock);
        this.model = model;
        this.isRented = isRented;
        this.batteryLife = (int) (Math.random() * 101);
    }

    @Override
    public void describe() {
        System.out.println("This is the ultimate DigiCam that will change your photo expereince!");
    }

    @Override
    public void usuageInstruction() {
        // More detailed instruction to be implemented
        System.out.println("1. Press the power on button.");
        System.out.println("2. Take a photo!");
    }

    // Getters and Setters
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

    public void powerOn() {
        if (batteryLife >= 10) {
            batteryLife -= 10;
        } else {
            batteryLife = 0;
            System.out.println("Battery is 0%. Please replace your battery.");
        }
    }

    public void powerOff() {

    }

}
