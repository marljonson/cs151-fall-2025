public interface Buyable {
    //boolean isBuyable();

    double getPrice();

    void buy(int quantity); //added the argument
    void adjustPriceOnRarity(); //newly added method
}
