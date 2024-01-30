package org.xpdojo;

import java.util.Vector;
import java.util.Enumeration;

public class Customer {
	
	private String name;
    private Vector rentals = new Vector();
    
    double totalAmount = 0;
    int frequentRenterPoints = 0;
    
    public Customer(String name) {
        this.name = name;
    }

    public void addRental(Rental rental) {
        rentals.addElement(rental);
    }

    public String getName() {
        return name;
    }

    public String statement() {
        
        Enumeration rentals = getRentals();
        String result = "Rental Record for " + getName() + "\n";
        
        while (rentals.hasMoreElements()) {
            double thisAmount = 0;
            Rental each = (Rental) rentals.nextElement();
            
            thisAmount = getAmountAndUpdateTotalAmount(thisAmount, each);
            
            result += "\t" + each.getMovie().getTitle() + "\t" + String.valueOf(thisAmount) + "\n";  
            
            calculateFrequentRenterPoints(each);
        }
        
        result += "You owed " + String.valueOf(getAmountOwed()) + "\n";
        result += "You earned " + String.valueOf(getFrequentRenterPoints()) + " frequent renter points\n";

        return result;
    }

	private double getAmountAndUpdateTotalAmount(double thisAmount, Rental each) {
		thisAmount = calculateAmount(thisAmount, each);
		totalAmount+=thisAmount;
		return thisAmount;
	}

	private void calculateFrequentRenterPoints(Rental each) {
		frequentRenterPoints++;
		if (each.getMovie().getPriceCode() == Movie.NEW_RELEASE && each.getDaysRented() > 1)
		    frequentRenterPoints++;
	}

	private Enumeration getRentals() {
		Enumeration rentals = this.rentals.elements();
		return rentals;
	}
    
    
    /**
     * @return totalAmount
     */
    public double getAmountOwed() {
    	return totalAmount;
    }

	private double calculateAmount(double thisAmount, Rental each) {
		// determines the amount for each line
		switch (each.getMovie().getPriceCode()) {
		    case Movie.REGULAR:
		        thisAmount += 2;
		        if (each.getDaysRented() > 2)
		            thisAmount += (each.getDaysRented() - 2) * 1.5;
		        break;
		    case Movie.NEW_RELEASE:
		        thisAmount += each.getDaysRented() * 3;
		        break;
		    case Movie.CHILDREN:
		        thisAmount += 1.5;
		        if (each.getDaysRented() > 3)
		            thisAmount += (each.getDaysRented() - 3) * 1.5;
		        break;
		}
		return thisAmount;
	}
    
    
    /**
     * @return The rentals
     */
    public int getFrequentRenterPoints() {
    	return frequentRenterPoints;
    }
}
