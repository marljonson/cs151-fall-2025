
package abstractclasses;

public abstract class Product {
    private int id;
    private String type;
    private double price;
    private int stock;

    //constructors
    //no argument constructor 
    public Product() {
        this.id = 1; //check this back 
        this.type = "default";
        this.price = 0;
        this.stock = 0;
    }

    //product with id, type, price, stock
    public Product(int id, String type, double price, int stock){

        if(id <= 0) throw new IllegalArgumentException("id must be > 0!");
        if(type == null) throw new IllegalArgumentException("type must be non-empty!");
        if(price < 0) throw new IllegalArgumentException("price must be >= 0");
        if(stock < 0) throw new IllegalArgumentException("stock must be >= 0");

        this.id = id;
        this.type = type;
        this.price = Math.round(price * 100.0) / 100.0;
        this.stock = stock;
    }

    //creating a new product with stock of 1
    public Product(int id, String type, double price){

        if(id <= 0) throw new IllegalArgumentException("id must be > 0!");
        if(type == null) throw new IllegalArgumentException("type must be non-empty!");
        if(price < 0) throw new IllegalArgumentException("price must be >= 0");

        this.id = id;
        this.type = type;
        this.price = Math.round(price * 100.0) / 100.0;
        this.stock = 1; 
    }

    //methods every class that extends Product must implement
    public abstract void describe();
    public abstract void usageInstruction();


    //methods subclass don't have to override

    //discountPercentage is a fraction in [0.0, 1.0]
    public void discount(double discountPercentage){

        //apply discount, update product's price

        //throw exception if the discountPercent is invalid 
        if(discountPercentage < 0 || discountPercentage > 1){
            throw new IllegalArgumentException("discount percentage must be between 0 and 1!"); 
        }
        
        //price after applying discount
        this.price =  price - (price * discountPercentage);

        //round the price to 2 decimal places
        this.price = Math.round(this.price * 100.0) / 100.0;
        
        //else we can print something out or throw an error
    }

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
                "id= " + id + ", " + 
                "type=" + type + ", " + 
                "price=" + formattedPrice + ", " +
                "stock=" + stock + "}";
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
}