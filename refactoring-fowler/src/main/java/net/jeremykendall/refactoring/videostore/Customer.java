package net.jeremykendall.refactoring.videostore;

import java.util.Enumeration;
import java.util.Vector;

public class Customer {
    private String _name;
    private Vector _rentals = new Vector();

    public Customer(String name) {
        _name = name;
    }

    public String statement() {
        double totalAmount = 0;
        int frequentRenterPoints = 0;
        Enumeration rentals = _rentals.elements();
        String result = "Rental Record for " + getName() + "\n";
        while (rentals.hasMoreElements()) {
            Rental each = (Rental) rentals.nextElement();
            Movie movie = each.getMovie();
            int daysRented = each.getDaysRented();
            int priceCode = movie.getPriceCode();

            double thisAmount = movie.determineAmount(daysRented);

            frequentRenterPoints += movie.getFrequentRenterPoints(frequentRenterPoints, daysRented);

            //show figures for this rental
            result += rentalInfo(movie.getTitle(), thisAmount);
            totalAmount += thisAmount;
        }
        result = addFooter(totalAmount, frequentRenterPoints, result);
        return result;
    }

    private String rentalInfo(String title, double thisAmount) {
        return ("\t" + movie.getTitle() + "\t" + String.valueOf(thisAmount) + "\n");
    }

    private String addFooter(double totalAmount, int frequentRenterPoints, String result) {
        return result +
                ("Amount owed is " totalAmount + "\n"
                + "You earned " + frequentRenterPoints + " frequent renter points");
    }

    public void addRental(Rental arg) {
        _rentals.addElement(arg);
    }

    public String getName() {
        return _name;
    }
}
