package project.models;

import project.abstractclasses.Product;
import project.interfaces.RentableTemp;

import java.util.HashMap;
import java.util.Map; 
import java.time.Instant;
import java.util.List;
import java.util.ArrayList; 

public class CustomerTemp {
    private String firstName;
    private String lastName;
    private String email;
    private final int customerId; //immutable, set this using nextCustomerId
    //private String membershipType; //Silver, Gold, Platinum, Diamond
    private double balance;
    private double amountSpent; 
    private static int nextCustomerId = 1;
    private List<Product> rentalHistory = new ArrayList<>(); //can add a PurchaseRecord class if I have time and use that as a type
    //private Map<Integer, Product> rentedProduct = new HashMap<>(); //running into problem where ids from different vendors collide
    private Map<String, Product> rentedProducts = new HashMap<>(); //key = "vendorId:vendorProductId" , value = Product
    public static String makeKey(Product p){ //making a String using vendor Id and product Id to use this String as a key for the HashMap
        return p.getOwner().getVendorId() + ":" + p.getVendorProductId();
    }
    public static String makeKey(int vendorId, int vendorProductId){ //Overloaded the method here (I am so proud of the idea that popped up on my mind)
        return vendorId + ":" + vendorProductId;
    }
    
    //no-arg constructor
    //we won't be using this but just in case 
    public CustomerTemp() {
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.customerId = nextCustomerId++;
        this.balance = 0;
        this.amountSpent = 0;

    }

    public CustomerTemp(String firstName, String lastName, String email, double cash){

        if(firstName == null || firstName.isBlank()) throw new IllegalArgumentException("firstName cannot be null or blank");
        if(lastName == null || lastName.isBlank()) throw new IllegalArgumentException("lastName cannot be null or blank");
        if(email == null || email.isBlank()) throw new IllegalArgumentException("email cannot be null or blank");
        if(cash < 0) throw new IllegalArgumentException("cash cannot be negative");

        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.email = email.trim();
        this.balance = Math.round(cash * 100.0) / 100.0;
        this.amountSpent = 0.0;
        this.customerId = nextCustomerId++;
    }

    //view products from a vendor
    public void browseCatalog(VendorTemp vendor){
        
        System.out.println("\nViewing the inventory of " + vendor.getName());
        vendor.printInventory();
    }

    //rent Rentable products
    public void rentProduct(Product product, Instant quotedAt){

        if(product == null) throw new IllegalArgumentException("product cannot be null");
        if(quotedAt == null) throw new IllegalArgumentException("quotedAt instant cannot be null");

        //making sure product is type "Rentable"
        if( !(product instanceof RentableTemp)){
            throw new IllegalStateException("This product " + product.getVendorProductId() + " type " + product.getType() + " is not rentable");
        }

        //checking if the product "isRentable()" this method check the stock 
        //first cast it into RentableTemp and call its isRentable()
        if( !((RentableTemp) product).isRentable()){
            throw new IllegalStateException("This product " + product.getVendorProductId() + " is currently unavailable for rent!");
        }

        RentableTemp p = (RentableTemp) product;

        //use the quoteRental which uses getEffectiveUnitPrice() for the product's actual price 
        double productActualPrice = p.quoteRental(quotedAt);

        if(productActualPrice > this.balance){
            throw new IllegalStateException("You don't have sufficient funds to rent this");
        }

        double charged = p.rent(quotedAt, productActualPrice); //this method updates the stock of the product;

        //subtract the charged price from the customer
        this.balance -= charged;
        this.amountSpent += charged;

         //credit the owner
        VendorTemp owner = product.getOwner();
        owner.credit(charged);

        //add it to the customer's rentedProducts
        rentedProducts.put(makeKey(product), product); //should actually check if the key already exists first

        System.out.println("You have successfully rented the product with ID: " + product.getVendorProductId() + " Type: " + product.getType() + " for $ " + (Math.round(charged * 100.0) / 100.0));
    }

    //list all the products the customer has rented
    public void viewMyRentals(){

        if(rentedProducts.isEmpty()){
            System.out.println("You have no active rentals");
            return;
        }

        System.out.println("\n\nYou have rented the following:\n");
        for(Product p : rentedProducts.values()){
            System.out.println("item " + p.getType() + " with ID " + p.getVendorProductId() + " from vendor " + p.getOwner().getName() + " with Vendor ID: " + p.getOwner().getVendorId());
        }

        System.out.println("\n");
    }

    public void returnRental(Product product, Instant returnedAt){ //might not need returntedAt Instant if I don't have time to implement late fee concept

         if(product == null) throw new IllegalArgumentException("product cannot be null");
         if(returnedAt == null) throw new IllegalArgumentException("returnedAt cannot be null");
         
        //does the customer actually have this product rented?
        Product rentedP = rentedProducts.get(makeKey(product)); //get(key) = value
        if(rentedP == null) throw new IllegalStateException("You have never rented this product");

        //making sure product is type "Rentable"
        if( !(rentedP instanceof RentableTemp)){
            throw new IllegalStateException("This product " + product.getVendorProductId() + " type " + product.getType() + " is not rentable");
        }

        //return
        ((RentableTemp) rentedP).rentalReturn(returnedAt); //update the stock back 

         //remove the product from customer's rentedProduct
        rentedProducts.remove(makeKey(rentedP));

        System.out.println("You have successfully returned " + "item " + rentedP.getType() + " with ID " + rentedP.getVendorProductId() + " from vendor " + rentedP.getOwner().getName());

        rentalHistory.add(rentedP);
    }

    //products customer has rented and already returned
    public void printRentalHistory(){

      if(rentalHistory.isEmpty()){
        System.out.println("No past rentals");
        return;
      }

      System.out.println("\nYour Rental History: ");
      for(Product p : rentalHistory){
        System.out.println("item: " + p.getType() + " with ID " + p.getVendorProductId() + " from vendor " + p.getOwner().getName());
      }
    }

    //below are getters and setters
    //for name
    public void setFirstName(String name){
        if(name == null || name.isBlank()) throw new IllegalArgumentException("firstName cannot be null or blank");
        this.firstName = name.trim();
    }
    public String getFirstName(){ return this.firstName; }

    public void setLastName(String name){
        if(name == null || name.isBlank()) throw new IllegalArgumentException("lastName cannot be null or blank");
        this.lastName = name.trim();
    }
    public String getLastName() { return this.lastName; }

    public String getFullName() { return this.firstName + " " + this.lastName; }

    //for email
    public void setEmail(String email){
        if(email == null || email.isBlank()) throw new IllegalArgumentException("Email cannot be null or blank");
        this.email = email.trim();
    }
    public String getEmail() { return this.email; }

    //for customerId
    public int getCustomerId() { return this.customerId; }

    //for balance

    public void addFunds(double amount){
        if(amount < 0) throw new IllegalArgumentException("amount cannot be negative");
        this.balance = Math.round((this.balance + amount) * 100.0) / 100.0;
        System.out.println("Your updated balance: "+ this.balance);
    
    }
    public void deduct(double amount){
        if(amount < 0) throw new IllegalArgumentException("amount cannot be negative");
        if(amount > this.balance) throw new IllegalStateException("You don't have enough funds");

        this.balance = Math.round((this.balance -  amount) * 100.0) / 100.0;
    }
    public double getBalance(){ return (Math.round(this.balance * 100.0)/100.0); }

    //for amountSpent
    public double getAmountSpent() { return this.amountSpent; }

    public Map<String, Product> getRentedProducts(){
        return this.rentedProducts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Customer:{")
          .append("customerId=").append(customerId)
          .append(", name='").append(firstName).append(" ").append(lastName).append("\"")
          .append(", email='").append(email).append("\"")
          .append(", balance=$").append(String.format("%.2f", balance))
          .append(", amountSpent=$").append(String.format("%.2f", amountSpent))
          .append(", activeRentals=").append(rentedProducts.size())
          .append(", rentalHistoryCount=").append(rentalHistory.size())
          .append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // same exact object
        if (obj == null || getClass() != obj.getClass()) return false; // check null; check class type

        CustomerTemp other = (CustomerTemp) obj;

        // to identify a customer, it is sufficient to use just customerId and email
        return  this.customerId == other.customerId &&
                this.email.equalsIgnoreCase(other.email);
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(customerId);
        result = 31 * result + email.toLowerCase().hashCode();
        return result;
    }
}
