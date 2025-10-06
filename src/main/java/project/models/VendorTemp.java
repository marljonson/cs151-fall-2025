package models;

import java.util.HashMap;
import java.util.TreeMap; //for sorting by vendorProductId
import java.util.Map;
import java.util.NoSuchElementException;
import java.time.Instant;

import abstractclasses.Product;
import interfaces.BuyableTemp;

public class VendorTemp {

    private Map<Integer, Product> productList = new HashMap<>(); //product list(a map) for the vendor (key = vendorProductId)
    private String name;
    private final int vendorId; //immutable, set this using nextVendorId in the constructor
    private String email;
    private double balance; //changed it to balance, so that vendor can withdraw money
    private static int nextVendorId = 1; //Vendor level counter, for assigning vendorId
    private int nextVendorProductId = 1; //Vendor will control the ids of their products

    private PromoWindow vendorPromo = null; //PromoWindow only exists within the vendor, null until vendor creates a promowindow
    
    //avoid using no-args constructor 
    protected VendorTemp(){
        this.name = "";
        this.vendorId = nextVendorId++; //unique per vendor, start at 1 and postfix
        this.email = "";
        this.balance = 0.0;
    }

    public VendorTemp(String name, String email){
        
        if(name == null || name.isBlank()) throw new IllegalArgumentException("name cannot be null or blank");
        if(email == null || email.isBlank()) throw new IllegalArgumentException("email cannot be null or blank");

        this.name = name.trim();
        this.vendorId = nextVendorId++;
        this.email = email.trim();
        this.balance = 0.0;
    }

    //create and add Products to the Vendor's product list
    //Only Vendor can create and add products 
    public void createLabubu(double price, String color, boolean isRare){ //can skip all the error checks, did those in Product's Serialized Constructor and the check for color in Labubu's constructor
        int currId = nextVendorProductId++;

        Product labubu = new Labubu(currId, price, this, color, isRare); //Serialized Labubu constructor 

        //add labubu to the vendor's product list
        productList.put(labubu.getVendorProductId(), labubu);
    }

    //same logic
    public void createDigicam(double price, String model){
        int currId = nextVendorProductId++;

        Product digiCam = new DigiCamTemp(currId, price, this, model);
        productList.put(digiCam.getVendorProductId(), digiCam);
    }

    //TODO- same logic for Matcha but Matcha must use Product's Bulk Constructor
    //TODO-after Matcha is updated
    //Remember- a type of Matcha can have more that 1 stock unlike Rentable products 

    //updateStock should only be for Matcha (Buyable Products) since Rentable Products are each with stock of 0 or 1, and a unique ID
    //renamed updateStock to restock
    public void restockById(int vendorProductId, int qty){

        //look for the product with vendorProductId
        Product currProduct = productList.get(vendorProductId);

        if(qty <= 0) { throw new IllegalArgumentException("restock quantity cannot be less than or equal to 0"); }
        if(currProduct == null) { throw new NoSuchElementException("No such product exists at this vendor"); }
        if( !(currProduct instanceof BuyableTemp) ) {throw new IllegalStateException("this product is not restockable, create a new product and add it instead!"); }

        int updatedStock = currProduct.getStock() + qty; //previousStock + restock qty

        currProduct.setStock(updatedStock); //this will also get Updated in the productList (we don't need a separete vendorStock)
        
        System.out.println("Stock for the product with ID: " + currProduct.getVendorProductId()+ " and type: " + currProduct.getType() + " has now been set to " + updatedStock);
    }

    public void updatePriceById(int vendorProductId, double price){

        //look for the product with vendorProductId
        Product currProduct = productList.get(vendorProductId);

        //don't need this, we are using setPrice() which checks this if(price < 0) throw new IllegalArgumentException("price must be >= 0");
        if(currProduct == null) { throw new NoSuchElementException("No such product exists at this vendor"); }

        currProduct.setPrice(price);
    }

    public void printInventory(){

        //NOTE: I haven't learned lambda expressions or Comparable<> TT, but I know that TreeMap sorts the key naturally
        //create a new TreeMap and put the existing map (productList) inside it
        Map<Integer, Product> treeMap = new TreeMap<>(productList);

        for(Product p: treeMap.values()){
            System.out.println(p); //print using Product's overriden toString (COOL TIP: RHS Object Reference will be executed at the Runtime!)
        }
    }

    public void discontinueProductById(int vendorProductId){ //flip the product's stock

        //look for the product with vendorProductId
        Product currProduct = productList.get(vendorProductId);
        if(currProduct == null) { throw new NoSuchElementException("No such product exists at this vendor"); }

        if(currProduct.getStock() == 0) return; //already discontinued
        
        currProduct.setStock(0);

        //TODO : print with comment stating this product with id, type, is discontinued ...
        //TODO HERE
    }

    //PromoWindow
    //for now, a vendor can set a promowindow for every product of his (can change it to specific types or IDs later)
    public void setPromo(double discountFraction, Instant startTime, Instant endTime){

        this.vendorPromo = new PromoWindow(discountFraction, startTime, endTime); //checks are already handled in PromoWindow constructor 

    }

    public void cancelPromo(){
        
        //simply set the vendorPromo = null
        this.vendorPromo = null;
    }

    //below are getters and setters
    //for name
    protected void setName(String name){

        if(name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be null or blank");
        this.name = name.trim();
    }
    public String getName() { return this.name; }

    //for vendorId
    public int getVendorId() {return this.vendorId; }// note: vendorId is final

    //for email
    protected void setEmail(String email){
        if(email == null || email.isBlank()) throw new IllegalArgumentException("Email cannot be null or blank");
        this.email = email.trim();
    }
    public String getEmail() { return this.email; }

    //for balance
    protected void credit(double amount){
        if(amount < 0) throw new IllegalArgumentException("amount must be >= 0");
        this.balance += amount;
        this.balance = Math.round(this.balance * 100.0) / 100.0;
    }
    protected void debit(double amount){
        if(amount < 0) throw new IllegalArgumentException("amount must be >= 0");
        if(amount > this.balance) throw new IllegalStateException("You don't have that much fund to debit");

        if(amount == this.balance){
            this.balance = 0.0;
            return;
        }
        this.balance -= amount;
        this.balance = Math.round(this.balance * 100.0) / 100.0;

    }
    public double getBalance(){ return this.balance; }

    //for promoWindow
    public PromoWindow getPromoWindow(){ return this.vendorPromo; } //here the name don't match, I am using getPromoWindow() in Product, can change later 

}
