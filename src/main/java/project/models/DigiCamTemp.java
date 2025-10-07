
package project.models;

import project.models.VendorTemp;
import project.abstractclasses.Product;
import project.interfaces.RentableTemp;

import java.time.Instant;

//TODO: this class name is DigiCamTemp, be careful when you rewrite them in your DigiCam
public class DigiCamTemp extends Product implements RentableTemp {

    //no ID field; Vendors will create new products and take care of the IDS
    //no isRented; each DigiCam will have a uique ID and only 1 stock exists (when the getStock() returns 1, it is not rented, when returns 0, it is rented)  
    //stock is always 1 (don't need to set stock here, Product's Serialized Constructor takes care of the stock
    //use (Product's getStock()) Please refer to Product's Serialized Constructor for more info
    
    private static final String TYPE = "DigiCam";
    private String model; 
    private int batteryLife;  
    private boolean isOn; 

    //this is just to apply our knowledge that when we have other constructors, Java will not provide no-args constructor
    //Never use this
    public DigiCamTemp(){
        super();
        this.model = "";
        this.isOn = false;
        this.batteryLife = (int) (Math.random() * 101);
    }

    //Constructor: Serialized DigiCam Constructor
    public DigiCamTemp(int vendorProductID, double price, VendorTemp owner, String model){

        super(vendorProductID, TYPE, price, owner); //Calling Product's Serialized Constructor

        if(model == null || model.isBlank()) throw new IllegalArgumentException("model must be non-empty");

        this.model = model.trim(); //trim() is just getting rid of spaces before and after 
        this.isOn = false;
        this.batteryLife = (int) (Math.random() * 101); //0 to 100 inclusive
    }

    //Overriding abstract methods from Product
    @Override
    public void describe() {
        System.out.println("This is the ultimate DigiCam that will change your photo experience!");
    }

    @Override
    public void usageInstruction() {
        System.out.println("1. Press the power on button.");
        System.out.println("2. Take a photo!");
    }

    //TODO: most of the methods behave the same as Rentable Labubu, so I copy pasted some, pls carefully confirm that I don't use Labubu here
    //Overriding methods from Rentable Interface //moved these up to make sure for us to see easily that every method get overriden 
    @Override
    public boolean isRentable(){
        return (this.getStock() > 0); //remember we are treating each DigiCam as 1 item with a unique ID //we will use this concept that "something isRentable if it is an instanceof Rentable"
    }

    @Override
    public double quoteRental(Instant now){ //"now" is the time the customer chooses to see the quote (the concept is already taken care of in Product, see Product's getEffectiveUnitPrice)
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

    //No major change is made for your Unique methods 
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
        this.batteryLife = batteryLife; //added "this" to look nice (don't really matter if you don't use it)
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

    //Added getType here
    @Override //because I have included getType() in my Product (haven't checked this part yet, I will come back)
    public String getType(){ return TYPE; } //didn't use "this" here (static)

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
