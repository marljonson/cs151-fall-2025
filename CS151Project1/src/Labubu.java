public class Labubu extends Product implements Buyable{

    //attributes
    private String color;
    //private boolean isRented;
    private boolean isRare;
    private boolean priceAdjustedForRarity = false;

    //Constructors (will provide 2 constructors)
    public Labubu(){

        super();
        this.color = "default";
        this.isRare = false;
    }
    
    public Labubu(String type, double price, int stock, String color){

        super(type, price, stock);
        this.color = color;
        this.isRare = false;
    }
    
    public Labubu(String type, double price, int stock, String color, boolean isRare){

        super(type, price, stock);
        this.color = color;
        this.isRare = isRare;
    }

    //override abstract methods of Product
    @Override
    public void describe(){
        System.out.print("Your labubu is ");

        //check Rare or not 
        if (this.isRare == true)
            System.out.println("very rare!"); //if rare
        else
            System.out.println("very cute!"); //if not rare
    }

    @Override
    public void usageInstruction(){
        System.out.println("Please keep the box and the card if you want to preserve resale or collection value!");
        System.out.println("Please keep them away from direct sunlight to prevent color fading");
    }

    //override methods of Rentable interface

    //waiting for Rentable interface to be implemented by Miyuki


    //Labubu's specific method
   
    //if a Labubu becomes Rare, changes its price
    /*
    public void markAsRare(){
        double currPrice = getPrice();
        this.isRare = true;
        setPrice(currPrice + (currPrice * 0.5));
    } */
    
    //newly added method
    @Override
    public void adjustPriceOnRarity() {
    	if (isRare && !priceAdjustedForRarity) {
            setPrice(getPrice() * 1.15);
            priceAdjustedForRarity = true;
        }
    	else {
    		setPrice(getPrice());
    	}
    }
    //getters and setters 

    //for color
    public void setColor(String color){ this.color = color; }
    public String getColor(){ return this.color; }

    //for isRare
    public void setIsRare(boolean isRare) { this.isRare = isRare; }
    public boolean getIsRare() { return this.isRare; }
    
    //newly added method
    @Override
    public void buy(int quantity) {
    	if (quantity <= 0) {
    		throw new IllegalArgumentException("Quantity must be positive");
    	}
    	if (quantity > getStock()) {
    		throw new IllegalStateException("Not enough stock available");
    	}
    	sellOrRent(quantity);
    }

    @Override
    public String displayInfo() {
        return getInfo() + " | $" + getPrice() + " | " + getStock();
    }
    
    @Override
    public String getInfo() {
    	return "ID: " + getId() + " | " + getType() + " (" + getColor() + ")";
    }
    
  
}
