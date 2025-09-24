
package abstractclasses;


public abstract class Product {
    private int id;
    private String type;
    private double price;
    private int stock;

    //constructors (will provide 2 constructors)
    //no argument constructor 
    public Product() {
        this.id = -9999;
        this.type = "default";
        this.price = 9999;
        this.stock = 9999;
    }

    //product with id, type, price, stock
    public Product(int id, String type, double price, int stock){
        this.id = id;
        this.type = type;
        this.price = price;
        this.stock = stock;
    }

    //creating a new product
    public Product(int id, String type, double price){
        this.id = id;
        this.type = type;
        this.price = price;
        this.stock++; 
    }

    //methods every class that extends Product must implement
    public abstract void describe();
    public abstract void usageInstruction();


    //methods subclass don't have to override
    public void discount(double discountPercentage){

        //apply discount, update product's price
        //dicountPercentage must be in double (2decimal)

        //chech the discountPercent is valid 
        if(discountPercentage > 0 && discountPercentage < 1){
            this.price =  price - (price * discountPercentage);
        }
        //else we can print something out or throw an error
    }

    //this will update current price with inflation
    public void updatePrice(double newPrice) {
        //check newPrice is valid
        if (newPrice > 0){
            this.price = newPrice; 
        }
    }

    //belows are setters and getters
    
    //for id
    public void setId(int id)  { this.id = id; }
    public int getId() { return this.id; }

    //for type
    public void setType(String type) {this.type = type; }
    public String getType() {return this.type; }


    //for price
    public void setPrice(double price) {this.price = price; }
    public double getPrice() {return this.price; }

    //for stock
    public void setStock(int stock) {this.stock = stock; }
    public int getStock() {return this.stock; }
}