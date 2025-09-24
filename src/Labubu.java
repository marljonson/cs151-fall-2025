package main.java.project.models;

public class Labubu extends Product{

    //attributes
    private String color;
    private boolean isRented;
    private boolean isRare;

    //Constructors (will provide 2 constructors)
    public Labubu(){

        super();
        this.color = "default";
        this.isRented = false;
        this.isRare = false;
    }

    public Labubu(int id, String type, double price, int stock, String color, boolean isRented, boolean isRare){

        super(id, type, price, stock);
        this.color = color;
        this.isRented = isRented;
        this.isRare = isRare;
    }

    //overridden methods
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

    //Labubu's specific method

    //if a Labubu becomes Rare, changes its price
    public void markAsRare(){

        this.isRare = true;

        this.price = price + (price * 0.5);

    }

}