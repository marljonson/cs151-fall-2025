package models;

import java.time.Instant;

import models.Vendor;
import abstractclasses.Product;
import interfaces.Rentable;

public class DigiCam extends Product implements Rentable {

    private static final String TYPE = "DigiCam";
    private String model; 
    private int batteryLife;  
    private boolean isOn; 


    // No-argument constructor
    public DigiCam(){
        super();
        this.model = "";
        this.isOn = false;
        this.batteryLife = (int) (Math.random() * 101);
    }

    //Constructor: Serialized DigiCam Constructor
    public DigiCam(int vendorProductID, double price, VendorTemp owner, String model){

        super(vendorProductID, TYPE, price, owner); //Calling Product's Serialized Constructor

        if(model == null || model.isBlank()) throw new IllegalArgumentException("model must be non-empty");

        this.model = model.trim(); //trim() is just getting rid of spaces before and after 
        this.isOn = false;
        this.batteryLife = (int) (Math.random() * 101); //0 to 100 inclusive
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

    //Override methods from Rentable interface 
    @Override
    public boolean isRentable(){
        return (this.getStock() > 0); 
    }

    @Override
    public double quoteRental(Instant now){ //"now" is the time the customer chooses to see the quote 
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
        if(this.getStock() != 0) throw new IllegalStateException("Invalid Return: this DigiCam is never rented!");

        //restock
        this.setStock(1);
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
        if(!isOn){
            System.out.println("Camera is already off!");
            return;
        }

        isOn = false;
        System.out.println("Camera powered off.");
    }

    public void replaceBattery() {
        batteryLife = 100;
        System.out.println("Battery replaced. Battery life is now 100%");
    }

    public void checkPower() {
        System.out.println("Battery is " + batteryLife + "%");
    }

    //Getters and Setters
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        if(model == null || model.isBlank()) throw new IllegalArgumentException("model must be non-empty"); //added exception here just to be consistent 
        this.model = model.trim(); //added trim() here
    }

    public int getBatteryLife() {
        return this.batteryLife;
    }

    public void setBatteryLife(int batteryLife) {
        if(batteryLife < 0 || batteryLife > 100) throw new IllegalArgumentException("batteryLife can't be lower than 0 or greater than 100");  //added exception here just to be consistent 
        this.batteryLife = batteryLife; 
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }
    
    @Override 
    public String getType(){ 
        return TYPE; 
    } 

    @Override
    public String toString() {
        return "DigiCam {" +
                "id=" + getVendorProductId() + //because I changed the name in Product //I am using the concept that Products exist inside Vendors, each vendor will take care of the ids of their products
                ", type=" + getType() + //added this
                ", model='" + model + "\'" + //extra ' in your toString, is that for 'model'? pls check and change up to your style
                ", batteryLife=" + batteryLife +
                ", price=" + getPrice() +
                ", isOn=" + isOn +
                '}';
    }
}
