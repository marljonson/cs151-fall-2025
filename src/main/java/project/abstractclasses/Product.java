
package abstractclasses;

import java.time.Instant;

import project.models.Vendor;

public abstract class Product {
    private int vendorProductId; //vendorProductId....each vendor will control the ids of its products  (vendor-scoped IDs)
    private String type;
    private double price; //will need to change it later to BigDecimal
    private int stock;
    private Vendor owner; 

    //constructors
    //no argument constructor (just so the subclasses that could potentially use no-argumuent constructor don't crash, I don't see a case where we need to use this)
    protected Product() { //only subclasses can call this (BUT DON'T CALL THIS, NullPtrException for owner)
        this.vendorProductId = 0; //0 for ids for our project means "not a valid id"
        this.type = "";
        this.price = 0;
        this.stock = 0;
        this.owner = null;
    } 

    //product with id, type, price, stock (Matcha Vendor needs to use this if we don't want to give an ID to every matchas that the Matcha Vendor sells)
    //(BULK PRODUCTS)
    protected Product(int vendorProductId, String type, double price, int stock, Vendor owner){

        if(vendorProductId <= 0) throw new IllegalArgumentException("vendorProductId must be > 0!");
        if(type == null || type.isBlank()) throw new IllegalArgumentException("type must be non-empty!");
        if(price < 0) throw new IllegalArgumentException("price must be >= 0");
        if(stock < 0) throw new IllegalArgumentException("stock must be >= 0");
        if(owner == null) throw new IllegalArgumentException("owner can't be null!");

        this.vendorProductId = vendorProductId;
        this.type = type.trim();
        this.price = Math.round(price * 100.0) / 100.0;
        this.stock = stock;
        this.owner= owner;
    }

    //creating a new product with stock of 1 (Labubu and DigiCam needs to use this to have specific id for each item) 
    //SERIALIZED PRODUCTS 
    protected Product(int vendorProductId, String type, double price, Vendor owner){

        if(vendorProductId <= 0) throw new IllegalArgumentException("vendorProductId must be > 0!");
        if(type == null || type.isBlank()) throw new IllegalArgumentException("type must be non-empty!");
        if(price < 0) throw new IllegalArgumentException("price must be >= 0");
        if(owner == null) throw new IllegalArgumentException("owner can't be null!");

        this.vendorProductId = vendorProductId;
        this.type = type.trim();
        this.price = Math.round(price * 100.0) / 100.0;
        this.stock = 1;
        this.owner= owner;
    }

    //methods every class that extends Product must implement //***can return String for flexibility, will discuss on monday */
    public abstract void describe();
    public abstract void usageInstruction();


    //methods subclass don't have to override
    public void showAvailability() {

        if(this.stock <= 0) { System.out.println("Out of stock!"); }
        else { System.out.println("Available (stock = " + this.stock + ")"); }
    }

    //if the vendor has applied a discount in a PromoWindow 
    //discount is a fraction in [0.0, 1.0]
    public double calculateActualPrice(double discountFraction){

        //apply discount, get the updated price

        //throw exception if the discountPercent is invalid 
        if(discountFraction < 0.0 || discountFraction > 1.0 ){ //getting 0% or 100% discount is okay and applicable
            throw new IllegalArgumentException("discount fraction must be between 0 and 1 (inclusive)!"); 
        }
        
        //price after applying discount
        double actualPrice =  price - (price * discountFraction);

        //round the price to 2 decimal places
        actualPrice = Math.round(actualPrice * 100.0) / 100.0;

        return actualPrice; 
    }

    //won't be letting a Product set its own price, its Vendor will handle this
    //this will update current price with inflation
    protected void updatePrice(double newPrice) {

        //check newPrice is valid
        if(newPrice < 0){
            throw new IllegalArgumentException("Price must be >= 0");
        }
        this.price = Math.round(newPrice * 100.0) / 100.0; 
    }

    //overridden toString method for Product
    @Override 
    public String toString(){

        //to make sure that we don't print no more than 2 decimals
        String formattedPrice = String.format("%.2f", price);

        return "Product{" +
                "vendorProductId= " + this.vendorProductId + ", " + 
                "type=" + this.type + ", " + 
                "price=" + formattedPrice + ", " +
                "stock=" + this.stock + ", " +
                "owner=" + (this.owner == null ? "null" : this.owner.getName()) + "}"; //****vendor owner must have getName() */
    }

    //always use this as a UnitPrice for every product whether rentable or buyable
    //this is to see if a vendor has a promoWindow for his products
    public double getEffectiveUnitPrice(Instant quotedAt){ //"at" is the time the user quoted the product

    if (owner.vendorPromo == null) { return this.price; } //if no promo, return the original price

    //if there is vendorPromo object, check if the promo is active at the time "at"
    PromoWindow promo = owner.vendorPromo;

    if (promo.activeNow(quotedAt)) { //there is a PromoWindow for the owner (vendor), check if the user quotedAt the active promo window, if so apply discount and return the unitprice after applying discount
        double unitPriceAfterDiscount = price - (price * promo.discountFraction);
        unitPriceAfterDiscount = Math.round(unitPriceAfterDiscount * 100.0) / 100.0;
        return unitPriceAfterDiscount;
    }

    return this.price;
    }

    //belows are setters and getters
    
    //for id
    private void setVendorProductId(int vendorProductId){  //might not need this, ID should be immutable after creation
        if(vendorProductId <= 0) throw new IllegalArgumentException("vendorProductId must be > 0!");
        this.vendorProductId = vendorProductId; 
    }
    public int getVendorProductId() { return this.vendorProductId; }

    //for type
    protected void setType(String type) {
        if(type == null || type.isBlank()) throw new IllegalArgumentException("type must be non-empty!");
        this.type = type.trim(); 
    }
    public String getType() {return this.type; }


    //for price
    //(we might need to change this to BigDecimal because double don't work well with money, then round that to 2 decimals)
    protected void setPrice(double price) { //protected since only Vendor should be able to set price
        if(price < 0) throw new IllegalArgumentException("price must be >= 0");
        this.price = Math.round(price * 100.0) / 100.0; 
    }
    public double getPrice() {return this.price; } //don't need to reround again since I round the price on assignment

    //for stock
    protected void setStock(int stock) { //protected since I enforce stock = 1 for serialized products
        if(stock < 0) throw new IllegalArgumentException("stock must be >= 0");
        this.stock = stock; 
    }
    public int getStock() {return this.stock; }

    //for owner
    protected void setOwner(Vendor owner) { 
        if(owner == null) throw new IllegalArgumentException("owner can't be null!");
        this.owner = owner; 
    }

    public Vendor getOwner() { return this.owner; }
}