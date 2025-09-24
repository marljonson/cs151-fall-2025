package src.main.java.project.interfaces;

import src.main.java.project.abstractclasses.Product;

public interface Rentable {
    boolean isRentable();

    double getRentalPrice();

    void rentalReturn(Customer customer);

    void rent(Customer customer);
}