
package abstractclasses;

public abstract class Product {
    private int vendorProductId; //vendorProductId....each vendor will control the ids of its products  (vendor-scoped IDs)
    private String type;
    private double price;
    private int stock;
    private int ownerVendorId; 

    //constructors
    //no argument constructor (just so the subclasses that could potentially use no-argumuent constructor don't crash, I don't see a case where we need to use this)
    public Product() {
        this.vendorProductId = 0; //0 for ids for our project means "not a valid id"
        this.type = "default";
        this.price = 0;
        this.stock = 0;
        this.ownerVendorId = 0;
    }

    //product with id, type, price, stock (Matcha Vendor needs to use this if we don't want to give an ID to every matchas that the Matcha Vendor sells)
    //(BULK PRODUCTS)
    public Product(int vendorProductId, String type, double price, int stock, int ownerVendorId){

        if(vendorProductId <= 0) throw new IllegalArgumentException("vendorProductId must be > 0!");
        if(type == null || type.isBlank()) throw new IllegalArgumentException("type must be non-empty!");
        if(price < 0) throw new IllegalArgumentException("price must be >= 0");
        if(stock < 0) throw new IllegalArgumentException("stock must be >= 0");
        if(ownerVendorId <= 0) throw new IllegalArgumentException("OwnerVendorID must be > 0!");

        this.vendorProductId = vendorProductId;
        this.type = type;
        this.price = Math.round(price * 100.0) / 100.0;
        this.stock = stock;
        this.ownerVendorId = ownerVendorId;
    }

    //creating a new product with stock of 1 (Labubu and DigiCam needs to use this to have specific id for each item) 
    //SERIALIZED PRODUCTS 
    public Product(int vendorProductId, String type, double price, int ownerVendorId){

        if(vendorProductId <= 0) throw new IllegalArgumentException("vendorProductId must be > 0!");
        if(type == null || type.isBlank()) throw new IllegalArgumentException("type must be non-empty!");
        if(price < 0) throw new IllegalArgumentException("price must be >= 0");
        if(ownerVendorId <= 0) throw new IllegalArgumentException("OwnerVendorID must be > 0!");

        this.vendorProductId = vendorProductId;
        this.type = type;
        this.price = Math.round(price * 100.0) / 100.0;
        this.stock = 1;
        this.ownerVendorId = ownerVendorId;
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
        if(discountFraction < 0.0 || discountFraction > 1.0 ){ //getting 0% or 100% discount is okay and applyable
            throw new IllegalArgumentException("discount fraction must be between 0 and 1 (inclusive)!"); 
        }
        
        //price after applying discount
        double actualPrice =  price - (price * discountFraction);

        //round the price to 2 decimal places
        actualPrice = Math.round(actualPrice * 100.0) / 100.0;

        return actualPrice; 
    }

    //won't be lettting a Product set its own price, its Vendor will handle this
    //this will update current price with inflation
    public void updatePrice(double newPrice) {

        //check newPrice is valid
        if(newPrice < 0){
            throw new IllegalArgumentException("Price must be >= 0");
        }
        this.price = Math.round(newPrice * 100.0) / 100.0; 
    }

    //overriden toString method for Product
    @Override 
    public String toString(){

        //to make sure that we don't print no more than 2 decimals
        String formattedPrice = String.format("%.2f", price);

        return "Product{" +
                "vendorProductId= " + this.vendorProductId + ", " + 
                "type=" + this.type + ", " + 
                "price=" + formattedPrice + ", " +
                "stock=" + this.stock + ", " +
                "ownerVendorId=" + this.ownerVendorId + "}";
    }

    //belows are setters and getters
    
    //for id
    public void setId(int id){ 
        if(id <= 0) throw new IllegalArgumentException("id must be > 0!");
        this.id = id; 
    }
    public int getId() { return this.id; }

    //for type
    public void setType(String type) {
        if(type == null) throw new IllegalArgumentException("type must be non-empty!");
        this.type = type; 
    }
    public String getType() {return this.type; }


    //for price
    public void setPrice(double price) {
        if(price < 0) throw new IllegalArgumentException("price must be >= 0");
        this.price = Math.round(price * 100.0) / 100.0; 
    }
    public double getPrice() {return Math.round(this.price * 100.0) / 100.0; }

    //for stock
    public void setStock(int stock) {
        if(stock < 0) throw new IllegalArgumentException("stock must be >= 0");
        this.stock = stock; 
    }
    public int getStock() {return this.stock; }

    //for ownerVendorId
    public void setOwnerVendorId(int ownerVendorId) { 
        if(id <= 0) throw new IllegalArgumentException("Owner Vendor Id must be > 0!");
        this.ownerVendorId = ownerVendorId; 
    }

    public int getOwnerVendorId() { return this.ownerVendorId; }

    //
}