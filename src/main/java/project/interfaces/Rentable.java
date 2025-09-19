package src.main.java.project.interfaces;

public interface Rentable {
    boolean isRentable();

    double getRentalPrice();

    void rentalReturn();

    void rent();
}