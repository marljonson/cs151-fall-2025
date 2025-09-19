public interface Rentable {
    boolean isRentable();

    double getRentalPrice();

    void rentalReturn();

    void rent();
}