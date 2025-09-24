package models;

public class Matcha {
    private static int nextId = 1;
    private String type;
    private double price;
    
    public Matcha() {
        super(nextId++, "Unknown", type, price);
    }

    public Matcha() {

    }
}
