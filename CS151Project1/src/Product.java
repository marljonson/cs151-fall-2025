public abstract class Product {
    private int id;
    private String type;
    private double price;
    private int stock;
    private static int nextId = 1;
    //constructors (will provide 2 constructors)
    //no argument constructor 
    public Product() {
        this.id = -9999;
        this.type = "default";
        this.price = 9999;
        this.stock = 9999;
    }

    //product with id, type, price, stock
    public Product(String type, double price, int stock){
        this.id = nextId++;
        this.type = type;
        this.price = price;
        this.stock = stock;
    }

    //methods every class that extends Product must implement
    public abstract void describe();
    public abstract void usageInstruction();
    public abstract String displayInfo();
    public abstract String getInfo();
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
    
    //newly added method to manipulate stock
    public void sellOrRent(int quantity) {
    	if (quantity <= 0) {
            throw new IllegalArgumentException("Sell or rent quantity must be positive");
        }
    	stock -= quantity;
    	//System.out.println("Stock updated: " + stock + " " + type + " left");
    }
    
    public void returnProduct(int quantity) {
    	if (quantity <= 0) {
            throw new IllegalArgumentException("Return quantity must be positive");
        }
    	stock += quantity;
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
