package src.main.java.project.models;

class Labubu extends Product implements Rentable {
    private static int nextId = 1;
    private String color;
    private boolean isRented;
    private boolean isRare;

    public Labubu() {
        super(nextId++, "Unknown", 0.0, 0);
        this.color = "";
        this.isRented = false;
        this.isRare = false;
    }

    public Labubu(String color, double price) {
        this.color = color;
    }
}

