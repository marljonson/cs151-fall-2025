package interfaces;

public interface Buyable {
    boolean isBuyable();

    double getPrice();

    void buy();

    void buy(int amount);
}