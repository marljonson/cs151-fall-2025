import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class Shop {
	public static Scanner scnr = new Scanner(System.in);
	
	public static ArrayList<Vendor> vendorList = new ArrayList<>(Arrays.asList(
    		new Vendor("Myo's Shop", "myo412@gmail.com"),
    		new Vendor("Miyuki's Shop", "miyuki@gmail.com"),
    		new Vendor("Dee's Shop", "dee@gmail.com"),
    		new Vendor("Marl's Electronics", "marl@gmail.com")
    ));
    
    public static ArrayList<Product> productList = new ArrayList<>(Arrays.asList(
    		new Labubu("Labubu", 30, 50, "pink", false),
    		new Labubu("Labubu", 40, 50, "blue", true),
    		new Labubu("Labubu", 35, 50, "green", false),
    		new DigiCam("DigiCam", 35, 50, "2025"),
    		new DigiCam("DigiCam", 25, 50, "2018")
    ));
    
    public static ArrayList<Customer> customerList = new ArrayList<>(Arrays.asList(
    		new Customer("Myo Thant", "Zin", "myothantzin@sjsu.edu", 300),
    		new Customer("Miyuki", "Tokuhara", "miyuki@sjsu.edu", 300),
    		new Customer("Dee", "Eain", "deeeain@sjsu.edu", 300),
    		new Customer("Marl", "Johnson", "marljohnson@sjsu.edu", 300)
    		));
   
    public static void main(String[] args) {
    	System.out.println("W E L C OM E   T O   O U R   S H O P\n");
    	
    	
    	System.out.println("Are you a member with us?");
    	System.out.println("1 : yes");
    	System.out.println("2 : no");
    	System.out.println("3 : exit");
    	String answer;
    	do {
    		answer = scnr.nextLine().toLowerCase();
    		if (answer.equals("yes")) {
    			System.out.print("Enter your email or user id number to confirm your identity : ");
    			answer = scnr.nextLine().toLowerCase();
    			for (int i = 0; i < customerList.size(); i++) {
    				if (customerList.get(i).getName().equals(answer) || answer.equals(customerList.get(i).getID()) {
    					
    				}
    			}
    		}
    		else if (answer.equals("no")) {
    			System.out.println("Do you want to sign up with us?");
    		}
    		else if (answer.equals("exit")) {
    			System.out.println("We hope to see you again!");
    		}
    		else {
    		System.out.println("The only acceptable inputs are yes or no. Please type again");
    		}
    	} while (!answer.equals("yes") || !answer.equals("no") || !answer.equals("exit"));
    	
    	displayVendors(vendorList);
    	
    	
    	
        
    }
    
    public static void displayVendors(ArrayList<Vendor> vendorList) {
    	System.out.println("List of Vendors");
    	for (Vendor v : vendorList) {
    		System.out.println("ID : " + v.getID() + " | name : " + v.getName() + " | email : " + v.getEmail());
    	}
    	System.out.println();
    }
    
    
    
}
