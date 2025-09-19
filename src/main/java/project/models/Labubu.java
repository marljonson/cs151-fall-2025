package main.java.project.models;

import main.java.project.abstractclasses.Product;

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
    public void markAsRare(){
        double currPrice = getPrice();
        this.isRare = true;
        setPrice(currPrice + (currPrice * 0.5));
    }

    //getters and setters 

    //for color
    public void setColor(String color){ this.color = color; }
    public String getColor(){ return this.color; }

    //for isRented
    public void setIsRented(boolean isRented) { this.isRented = isRented; }
    public boolean getIsRented() { return this.isRented};

    //for isRare
    public void setIsRare(boolean isRare) { this.isRare = isRare; }
    public boolean getIsRare() { return this.isRare; }


}