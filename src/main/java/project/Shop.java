import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    	System.out.println("W E L C O M E   T O   O U R   S H O P\n");
    	
    	
    	System.out.println("Are you a member with us?");
    	System.out.println("1 : yes");
    	System.out.println("2 : no");
    	System.out.println("3 : exit");
    	String answer;
    	do {
    		answer = scnr.nextLine().toLowerCase();
    		if (answer.equals("yes")) {
    			System.out.print("Enter your email : ");
    			String email = scnr.nextLine().toLowerCase();
    			System.out.print("Enter your full name : ");
    			String userInput = scnr.nextLine();
    			for (Customer c : customerList) {
    				if (c.getEmail().equals(email) && c.getFullName().equals(userInput)) {
    					System.out.println("Welcome " + c.getFullName() + "(" + c.getMembershipType() + " member)\n");
    					customerInteraction(c);
    				}
    			}
    			
    			
    		}
    		
    		else if (answer.equals("no")) {
    			System.out.println("Do you want to sign up with us?");
    			System.out.println("1 : yes");
    	    	System.out.println("2 : no");
    	    	String input;
    	    	do {
    	    		input = scnr.nextLine().toLowerCase();
    	    		if (input.equals("yes")) {
    	    			addCustomers(customerList);
    	    		}
    	    		else if (input.equals("no")) {
    	    			System.out.println("Thank you for shopping with us. We hope to see you again!");
    	    		}
    	    		else {
    	    			System.out.println("The only acceptable inputs are yes or no. Please type again");
    	    		}
    	    	} while (!input.equals("yes") || !input.equals("no"));
    		}
    		
    		else if (answer.equals("exit")) {
    			System.out.println("Thank you for shopping with us. We hope to see you again!");
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
    
    public static void addCustomers(ArrayList<Customer> customerList) {
    	System.out.print("Enter your first name : ");
    	String firstName = scnr.nextLine();
    	System.out.print("Enter your last name : ");
    	String lastName = scnr.nextLine();
    	System.out.print("Enter your email address : ");
    	String email = "";
    	boolean valid = false;
    	while (!valid) {
    		email = scnr.nextLine().toLowerCase();
    		if (!email.endsWith("@gmail.com") && !email.endsWith("@sjsu.edu")) {
    			System.out.print("Invalid form of email address. Enter your email again : ");
    			continue;
    		}
    		boolean exist = false;
    		for (Customer c : customerList) {
    			if (c.getEmail().equals(email)) {
    				System.out.println("Customer with " + email + " already exists. Please choose another email address.");
    				exist = true;
    				break;
    			}
    		}
    		if (!exist) {
    			valid = true;
    		}
    	}
    	System.out.print("Enter the amount you want to depost : ");
    	double deposit = scnr.nextDouble();
    	while (deposit <=0 ) {
    		System.out.println("Your deposit amount must be greater than 0. Enter the amount again : ");
    		deposit = scnr.nextDouble();
    	}
    	scnr.nextLine();
    	Customer newCustomer = new Customer(firstName, lastName, email, deposit);
        customerList.add(newCustomer);
        System.out.println("Customer added: " + newCustomer.getFullName() + " (ID: " + newCustomer.getUserId() 
                + ", Email: " + newCustomer.getEmail() + ")");
    }
    
    public void chooseVendor() {
    	
    	
    }
    
    public static void customerInteraction(Customer c) {
    	int choice = -1;
    	do {
    		System.out.println("How may I help you today?");
    		System.out.println("1 : Update Membership");
    		
    		System.out.println("3 : Depost more cash");
    		System.out.println("4 : Inquiry about balance");
    		System.out.println("5 : Change your email address");
    		System.out.println("6 : Inquiry about membership type");
    		System.out.println("7 : Inquiry about total spending");
    		System.out.println("8 : Inquiry about purchased product");
    		System.out.println("9 : Exit");
    		System.out.print("Please type just the number : ");
    		
    		if (scnr.hasNextInt()) {
    			choice = scnr.nextInt();
    			scnr.nextLine();
    		}
    		else {
    			System.out.println("Please enter a valid number.");
    			scnr.nextLine();
    			continue;
    		}
    		switch (choice) {
    		case 1 : {
    			System.out.print("Which membership you want to buy : ");
    			String membership = scnr.nextLine().trim();
    			
    			c.updateMembershipType(membership);
    			System.out.print("Your membership was promoted to " + c.getMembershipType());
    			System.out.println();
    			break;
    		}
    		
    		case 3 : {
    			System.out.print("How much you want to deposit : ");
    			double amount = scnr.nextDouble();
    			scnr.nextLine();
    			c.addCash(amount);
    			System.out.println("Deposit successful. New balance: $" + c.getBalance());
    			System.out.println();
    			break;
    		}
    		case 4 : {
    			double balance = c.getBalance();
    			System.out.println(c.getFullName() + " balance is " + balance);
    			System.out.print("Your membership was promoted from " + c.getMembershipType() + " to ");
    			break;
    		}
    		case 5 : {
    			System.out.print("Enter new email address in valid form : ");
    			String email = "";
    	    	boolean valid = false;
    	    	while (!valid) {
    	    		email = scnr.nextLine().toLowerCase();
    	    		if (!email.endsWith("@gmail.com") && !email.endsWith("@sjsu.edu")) {
    	    			System.out.print("Invalid form of email address. Enter your email again : ");
    	    			continue;
    	    		}
    	    		boolean exist = false;
    	    		for (Customer customer : customerList) {
    	    			if (customer.getEmail().equals(email)) {
    	    				System.out.println("Customer with " + email + " already exists. Please choose another email address.");
    	    				exist = true;
    	    				break;
    	    			}
    	    		}
    	    		if (!exist) {
    	    			valid = true;
    	    		}
    	    	}
    	    	c.setEmail(email);
    	    	System.out.println("Email updated to " + c.getEmail());	
    	    	System.out.println();
    	    	break;
    		}
    		case 6 : {
    			System.out.println("Your membership type is " + c.getMembershipType());
    			System.out.println();
    			break;
    		}
    		case 7 : {
    			System.out.println("Your total spending is $" + c.getTotalSpending());
    			System.out.println();
    			break;
    		}
    		case 8 : {
    			System.out.print("Your membership was promoted from " + c.getMembershipType() + " to ");
    			Map<Integer, Product> catalog = new HashMap<>();
    		    for (Product p : Shop.productList) {
    		        catalog.put(p.getId(), p);
    		    }
    		    c.getOwnedItems(catalog);
    		    System.out.println();
    		    break;
    		}
    		default : 
    			System.out.println("Invalid input. Please try again\n");
    		} 		
    	} while (choice != 9);
    }
}
