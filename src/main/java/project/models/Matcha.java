package models;

import abstractclasses.Product;
import interfaces.Buyable;

class Matcha extends Product implements Buyable {
    private static int nextId = 1;
    private int iceCubes;
    private boolean hasToppings;
    
    // Custom no-args constructor
    public Matcha() {
        super(nextId++, "Unknown", 0.0, 0);
        this.iceCubes = 0;
        this.hasToppings = false;
    }

    // Constructor
    public Matcha(String type, double price, int stock, int iceCubes, boolean hasToppings) {
        super(nextId++, type, price, stock);
        this.iceCubes = iceCubes;
        this.hasToppings = hasToppings;
    }

    @Override
    public void describe() {
        System.out.println("Take sips to your heart's content");
    }

    @Override
    public void usageInstruction() {
        System.out.println("Hold in your hand; make sure everyone can see it, of course!");
    }

    // Getters and setters
    public int getIceCubes() {
        return iceCubes;
    }

    public void setIceCubes() {

    }

    // Unique methods for Matcha class
    public void addIce() {
        iceCubes++;
    }

    public boolean hasToppings() {
        return hasToppings;
    }

    // Buyable interface methods
    @Override
    public boolean isBuyable() {
        return false; // fix later
    }

    public double getPrice() {
        return 0.0; // fix later
    }

    public void buy() {

    }
}
