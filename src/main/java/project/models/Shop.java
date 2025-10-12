import java.util.ArrayList;
import java.util.Scanner;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.InputMismatchException;
import java.util.List;

public class Shop {
	public static Scanner scnr = new Scanner(System.in);
	private static List<Customer> customerList = new ArrayList<>();
    private static final String CUSTOMER_FILE = "customers.txt";
    
	public static ArrayList<Vendor> vendorList = new ArrayList<>(Arrays.asList(
    		new Vendor("Myo's Shop", "myo412@gmail.com"),
    		new Vendor("Miyuki's Shop", "miyuki@gmail.com"),
    		new Vendor("Dee's Shop", "dee@gmail.com"),
    		new Vendor("Marl's Electronics", "marl@gmail.com")
    ));
    
    public static ArrayList<Product> productList = new ArrayList<>(Arrays.asList(
    		new Labubu("labubu", 30, 50, "pink", false),
    		new Labubu("labubu", 40, 50, "blue", true),
    		new Labubu("labubu", 35, 50, "green", false),
    		new DigiCam("digicam", 35, 70, "2025"),
    		new DigiCam("digicam", 25, 50, "2018")
    ));
    
   
    public static void main(String[] args) {
    	for (int i = 0; i < vendorList.size(); i++) {
    		for (int j = 0; j < productList.size(); j++) {
    			vendorList.get(i).addProduct(productList.get(j), 10);
    		}
    		System.out.println();
    		//System.out.println(productList.get(0).getId());
    	}
    	
    	Shop shop = new Shop();           // create an instance so you can call non-static methods
        shop.loadCustomersFromFile();  
        
    	/*System.out.print("Enter the id of the vendor you want to add the prodyct to : ");
		int vendor = scnr.nextInt();
		scnr.nextLine();
		System.out.print("Enter the id of the vendor you want to add the prodyct to : ");
		int product = scnr.nextInt();
		scnr.nextLine();
		System.out.print("Enter thequantity of the product you want to add : ");
		int quantity = scnr.nextInt();
		scnr.nextLine();
		vendorList.get(vendor - 1).addProduct(productList.get(product - 1), quantity);
		System.out.println(vendorList.get(vendor - 1).getName() + "added " + quantity + " " + productList.get(product - 1).getType());
		System.out.println(productList.get(product - 1).getStock());*/
    	
    	String userInput;
    	do {
    		System.out.println("W E L C O M E   T O   O U R   S H O P\n");
        	System.out.println("Please choose an option:");
        	System.out.println("1. Proceed as Management");
        	System.out.println("2. Proceed as Customer");
        	System.out.println("3. Exit");
        	System.out.print("Please type only the number(1-3) : ");
    		userInput = scnr.nextLine().toLowerCase();
    		
    		//Run as management
    		if (userInput.equals("1")) {
    			managementInteraction();
    		}
    		
    		//Run as a customer
    		else if (userInput.equals("2")) {
    			System.out.println("\nAre you a member with us?");
    	    	System.out.println("1 : yes");
    	    	System.out.println("2 : no");
    	    	System.out.println("3 : exit");
    	    	System.out.print("Please type only the number(1-3) : ");
    	    	String answer;
    	    	do {
    	    		answer = scnr.nextLine().toLowerCase();
    	    		if (answer.equals("yes") || answer.equals("1")) {
    	    			System.out.print("Enter your email : ");
    	    			String email = scnr.nextLine().toLowerCase();
    	    			System.out.print("Enter your full name : ");
    	    			String name = scnr.nextLine();
    	    			Customer foundUser = null;
    	    			for (Customer c : customerList) {
    	    				if (c.getEmail().equals(email) && c.getFullName().equals(name)) {		
    	    					foundUser = c;
    	    					break;
    	    				}
    	    			}
    	    			if (foundUser != null) {
    	    				System.out.println("Welcome " + foundUser.getFullName() + "(" + foundUser.getMembershipType() + " member)");
    	    				customerInteraction(foundUser);
    	    			}
    	    			else {
    	    				System.out.println("No account found!");
    	    				signUpOffer();
    	    			}
    	    			answer = "exit";
    	    		}
    	    		else if (answer.equals("no") || answer.equals("2")) {
    	    			signUpOffer();
    	    			answer = "exit";
    	    		}
    	    		else if (answer.equals("exit") || answer.equals("3")) {
    	    			System.out.println("Thank you for shopping with us. We hope to see you again!\n");
    	    		}
    	    		else {
    	    			System.out.println("The only acceptable inputs are yes or no. Please type again");
    	    		}
    	    	} while (!answer.equals("exit") && !answer.equals("3")); 
    		}
    		else if (userInput.equals("3")) {
    			System.out.println("\nGood Bye. This is the end of the program\n");
    		}
    		else {
    			System.out.println("The only acceptable inputs are 1,2 and 3. Please type again");
    		}
    	} while (!userInput.equals("3") );	
    }
    
    public static void displayVendors(ArrayList<Vendor> vendorList) {
    	System.out.println("\nList of Vendors");
    	for (Vendor v : vendorList) {
    		System.out.println("ID : " + v.getID() + " | name : " + v.getName() + " | email : " + v.getEmail());
    		v.displayItem();
    		System.out.println();
    	}
    	System.out.println();
    }
    
    public static void addCustomers(List<Customer> customerList) {
    	System.out.print("Enter your first name : ");
    	String firstName = scnr.nextLine();
    	System.out.print("Enter your last name : ");
    	String lastName = scnr.nextLine();
    	System.out.print("Enter your email address : ");
    	String email = "";
    	boolean valid = false;
    	String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    	while (!valid) {
    		email = scnr.nextLine().trim().toLowerCase();
    		if (!email.matches(emailRegex)) {
                System.out.print("Invalid email format. Enter again: ");
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
    	System.out.print("Enter the amount you want to depost : ");
    	double deposit = 0;
    	boolean validDeposit = false;

    	while (!validDeposit) {
    	    System.out.print("Enter deposit amount : ");
    	    try {
    	        deposit = scnr.nextDouble();
    	        scnr.nextLine(); 

    	        if (deposit > 0) {
    	            validDeposit = true; 
    	        } else {
    	            System.out.println("Deposit must be greater than 0.");
    	        }
    	    } catch (InputMismatchException e) {
    	        System.out.println("Invalid input. Please enter a valid number.");
    	        scnr.nextLine(); 
    	    }
    	}
    	Customer newCustomer = new Customer(firstName, lastName, email, deposit);
        customerList.add(newCustomer);
        Shop shop = new Shop();
        shop.saveCustomerToFile(newCustomer);
        System.out.println("Customer added: " + newCustomer.getFullName() + " (ID: " + newCustomer.getUserId() 
                + ", Email: " + newCustomer.getEmail() + ")\n");
    }
    
    public static void customerInteraction(Customer c) {
    	int choice = -1;
    	do {
    		System.out.println("\nHow may I help you today?");
    		System.out.println("1 : Update Membership");
    		System.out.println("2 : Purchase product");
    		System.out.println("3 : Depost more cash");
    		System.out.println("4 : Inquiry about balance");
    		System.out.println("5 : Change your email address");
    		System.out.println("6 : Inquiry about membership type");
    		System.out.println("7 : Inquiry about total spending");
    		System.out.println("8 : Inquiry about purchased product");
    		System.out.println("9 : Reserve product");
    		System.out.println("10 : Return product");
    		System.out.println("11 : Exit");
    		System.out.print("Please type only the number(1-11) : ");
    		
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
    			System.out.println("Which membership you want to buy : ");
    			c.getMemberShipDetail();
    			System.out.print("Please type Diamond, Gold or Platinum : ");
    			String membership = scnr.nextLine().trim();
    			c.updateMembershipType(membership);
    			System.out.print("Your membership was promoted to " + c.getMembershipType());
    			System.out.println();
    			break;
    		}
    		case 2 : {
    			displayVendors(vendorList);
    			System.out.print("Enter the id of the vendor you want to buy from : ");
    			//System.out.println("Enter the id of the vendor you want to return to : ");
    			Vendor chosenVendor = chooseVendor(vendorList);
    			chosenVendor.displayItem();
    			
    			System.out.print("Enter the id of the product you want to buy : ");
    			int productId = scnr.nextInt();
    			scnr.nextLine();
    			Product chosenProduct = chosenVendor.getProductList().get(productId);
    			if (chosenProduct == null) {
    		        System.out.println("Invalid product ID. Please select a valid product from this vendor.");
    		        break;
    		    }
    			boolean validQuantity = false;
    		    while (!validQuantity) {
    		        try {
    		            System.out.print("Enter the quantity to purchase: ");
    		            int quantity = scnr.nextInt();
    		            scnr.nextLine();

    		            if (quantity <= 0) {
    		                System.out.println("Quantity must be greater than 0.");
    		                continue;
    		            }

    		            c.makePurchase(chosenProduct, quantity, chosenVendor);	
    		            //System.out.println(c.getFullName() + " successfully returned " + quantity + " × " + chosenProduct.getType());
    		            validQuantity = true;

    		        } catch (InputMismatchException e) {
    		            System.out.println("Please enter a valid number for quantity.");
    		            scnr.nextLine(); 
    		        }
    		    }
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
    			System.out.println(c.getFullName() + "'s balance is $" + balance);
    			break;
    		}
    		case 5 : {
    			System.out.print("Enter new email address in valid form : ");
    			String email = "";
    	    	boolean valid = false;
    	    	String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    	    	while (!valid) {
    	    		email = scnr.nextLine().trim().toLowerCase();
    	    		if (!email.matches(emailRegex)) {
    	                System.out.print("Invalid email format. Enter again: ");
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
    			getPurchasedItem(c);
    			break;
    		}
    		case 9 : {
    			displayVendors(vendorList);
    			System.out.print("Enter the id of the vendor you want to reserve from : ");
    			//System.out.println("Enter the id of the vendor you want to return to : ");
    			Vendor chosenVendor = chooseVendor(vendorList);
    			chosenVendor.displayItem();
    			
    			System.out.print("Enter the id of the product you want to reserve : ");
    			int productId = scnr.nextInt();
    			scnr.nextLine();
    			Product chosenProduct = chosenVendor.getProductList().get(productId);
    			
    			if (chosenProduct == null) {
    				System.out.println("Invalid product ID. Please select a valid product from this vendor.");
    		        break; 
    			}
    			boolean validQuantity = false;
    		    while (!validQuantity) {
    		        try {
    		            System.out.print("Enter the quantity to reserve: ");
    		            int quantity = scnr.nextInt();
    		            scnr.nextLine();

    		            if (quantity <= 0) {
    		                System.out.println("Quantity must be greater than 0.");
    		                continue;
    		            }

    		            if (quantity > chosenProduct.getStock()) {
    		                System.out.println("We only have " + chosenProduct.getStock() + " left in stock");
    		                continue;
    		            }
    		            c.reserveProduct(chosenProduct, quantity, chosenVendor);
    		            //System.out.println(c.getFullName() + " successfully returned " + quantity + " × " + chosenProduct.getType());
    		            validQuantity = true;

    		        } catch (InputMismatchException e) {
    		            System.out.println("Please enter a valid number for quantity.");
    		            scnr.nextLine(); 
    		        }
    		    }	
    			break;
    		}
    		case 10 : {
    			displayVendors(vendorList);
    			System.out.println("Enter the id of the vendor you want to return to : ");
    			Vendor chosenVendor = chooseVendor(vendorList);
    			getPurchasedItem(c);
    			System.out.print("Enter the id of the product you want to return : ");
    			int productId = scnr.nextInt();
    			scnr.nextLine();
    			Product chosenProduct = chosenVendor.getProductList().get(productId);
    			
    			if (chosenProduct == null) {
    				System.out.println("Invalid product ID. Please select a valid product from this vendor.");
    		        break; 
    			}
    			if (!c.hasRented(productId)) {
    		        System.out.println("Only rented items can be returned.");
    		        break;
    		    }
    			int rentedQty = c.getRentedQuantity(productId);
    		    boolean validQuantity = false;
    		    while (!validQuantity) {
    		        try {
    		            System.out.print("Enter the quantity to return: ");
    		            int quantity = scnr.nextInt();
    		            scnr.nextLine();

    		            if (quantity <= 0) {
    		                System.out.println("Quantity must be greater than 0.");
    		                continue;
    		            }

    		            // Optional: check if they’re not returning more than they purchased
    		            if (quantity > rentedQty) {
    		                System.out.println("You only rented " + rentedQty + " of this product.");
    		                continue;
    		            }

    		            // Process return
    		            c.returnProduct(chosenProduct, quantity, chosenVendor);
    		            //System.out.println(c.getFullName() + " successfully returned " + quantity + " × " + chosenProduct.getType());
    		            validQuantity = true;

    		        } catch (InputMismatchException e) {
    		            System.out.println("Please enter a valid number for quantity.");
    		            scnr.nextLine(); 
    		        }
    		    }
    		    displayVendors(vendorList);
    		    break;
    		}
    		case 11 : {
    			System.out.println("\nThis is the end of Customer interaction. Thank you\n");
    			saveAllCustomersToFile();
    			break;
    		}
    		default : 
    			System.out.println("Invalid input. Please try again\n");
    		} 		
    	} while (choice != 11);
    }
    
    public static void getPurchasedItem(Customer c) {
    	Map<Integer, Product> catalog = new HashMap<>();
	    for (Product p : Shop.productList) {
	        catalog.put(p.getId(), p);
	    }
	    c.getOwnedItems(catalog);
	    System.out.println();
    }
    
    public static Vendor chooseVendor(ArrayList<Vendor> vendorList) {
		Vendor chosenVendor = null;
		while (chosenVendor == null) {
			int vendorId = scnr.nextInt();
			scnr.nextLine();
			for (Vendor v : vendorList) {
				if (v.getID() == vendorId) {
					chosenVendor = v;
					break;
				}
			}
			if (chosenVendor == null) {
				System.out.print("Such vendor does not exist. Please choose again");
			}
		}
		System.out.println("You choose " + chosenVendor.getName());
		return chosenVendor;
    }
    
    public static void signUpOffer() {
    	System.out.println("Do you want to sign up with us?");
		System.out.println("1 : yes");
    	System.out.println("2 : no");
    	System.out.print("Please type only the number(1-2) or yes or no : ");
    	String input;
    	do {
    		input = scnr.nextLine().toLowerCase();
    		if (input.equals("yes") || input.equals("1")) {
    			addCustomers(customerList);
    			input = "no";
    		}
    		else if (input.equals("no") || input.equals("2")) {
    			System.out.println("Thank you for shopping with us. We hope to see you again!");
    		}
    		else {
    			System.out.println("The only acceptable inputs are yes or no. Please type again");
    		}
    	} while (!input.equals("no"));
    }
    
    public static void managementInteraction() {
    	int choice = -1;
    	do {
    		System.out.println("\nPlease choose an option:");
    		System.out.println("1 : Add Product");
    		System.out.println("2 : Remove Product");
    		System.out.println("3 : Update Price");
    		System.out.println("4 : Display Items Offered by Each Vendor");
    		System.out.println("5 : Total Sales");
    		System.out.println("6 : exit");
    		System.out.print("Please type only the number(1-6) : ");
    		
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
    			displayVendors(vendorList);
    			System.out.print("Enter the id of the vendor you want to interact with : ");
    			//System.out.println("Enter the id of the vendor you want to return to : ");
    			Vendor chosenVendor = chooseVendor(vendorList);
    			chosenVendor.displayItem();
    			
    			System.out.println("Available Products");
    			for (Product p : productList) {
    			    System.out.println(p.displayInfo());
    			}
    			
    			System.out.print("Enter the id of the product you want to add : ");
    			int productId = scnr.nextInt();
    			scnr.nextLine();
    			Product selectedProduct = null;
    			for (Product p : productList) {
    				if (productId == p.getId()) {
    					selectedProduct = p;
    					break;
    				}
    			}
    			if (selectedProduct == null) {
    				System.out.println("Invalid product ID. No such product found.");
    		        break;
    		    }
    			boolean validQuantity = false;
    		    while (!validQuantity) {
    		    	try {
    		    		System.out.print("Enter the quantity you want to add : ");
    		    		int newQuantity = scnr.nextInt();
    		    		scnr.nextLine();
    		    		
    		    		if (newQuantity <= 0) {
    		    			System.out.println("New Price must be positvie.");
    		    			continue;
    		    		}
    		    		if (newQuantity > selectedProduct.getStock()) {
    		    			System.out.println("Cannot add more than we have.");
    		    			continue;
    		    		}
    		    		chosenVendor.addProduct(selectedProduct, newQuantity);
    		    		displayVendors(vendorList);
    		    		validQuantity = true;
    		    	} catch (InputMismatchException e) {
    		    		System.out.println("Please enter a valid number for the price");
    		    		scnr.nextLine();
    		    	}
    		    }
    			break;	
    		}
    		case 2 : {
    			displayVendors(vendorList);
    			System.out.print("Enter the id of the vendor you want to interact with : ");
    			//System.out.println("Enter the id of the vendor you want to return to : ");
    			Vendor chosenVendor = chooseVendor(vendorList);
    			chosenVendor.displayItem();
    			System.out.print("Enter the id of the product you want to remove : ");
    			int productId = scnr.nextInt();
    			scnr.nextLine();
    			Product selectedProduct = null;
    			for (Product p : productList) {
    				if (productId == p.getId()) {
    					selectedProduct = p;
    				}
    			}
    			if (selectedProduct == null) {
    				System.out.println("Invalid product ID. No such product found.");
    		        break;
    		    }
    			chosenVendor.removeProduct(selectedProduct);
    			chosenVendor.displayItem();
    			break;			
    		}
    		case 3 : {
    			System.out.println("Available Products");
    			for (Product p : productList) {
    			    System.out.println(p.displayInfo());
    			}

    		    System.out.print("\nEnter the ID of the product you want to adjust price: ");
    		    int productId = scnr.nextInt();
    		    scnr.nextLine();
    		    
    		    Product targetProduct = null;
    		    for (Product p : productList) {
    		        if (p.getId() == productId) {
    		            targetProduct = p;
    		            break;
    		        }
    		    }
    		    if (targetProduct == null) {
    		        System.out.println("Invalid product ID. No such product found.");
    		        break;
    		    }
    		    boolean validPrice = false;
    		    while (!validPrice) {
    		    	try {
    		    		System.out.print("Enter the new Price : ");
    		    		double newPrice = scnr.nextDouble();
    		    		scnr.nextLine();
    		    		
    		    		if (newPrice <= 0) {
    		    			System.out.println("New Price must be positvie.");
    		    			continue;
    		    		}
    		    		targetProduct.setPrice(newPrice);
    		    		displayVendors(vendorList);
    		    		validPrice = true;
    		    	} catch (InputMismatchException e) {
    		    		System.out.println("Please enter a valid number for the price");
    		    		scnr.nextLine();
    		    	}
    		    }
    			break;
    		}
    		case 4 : {
    			displayVendors(vendorList);
    			break;
    		}
    		case 5 : {
    			for (Vendor v : vendorList) {
    				System.out.println("ID : " + v.getID() + " | name : " + v.getName() + " | email : " + v.getEmail() 
    				+ " | Total Earning : $" + v.getTotalEarning());			
    			}
    			break;
    		}
    		case 6 : {
    			System.out.println("\nThis is the end of management interaction. Thank you\n");
    			break;
    		}
    		default : 
    			System.out.println("Invalid input. Please try again\n");
    		} 		
    	} while (choice != 6);
    }
    
    public void printAllCustomers() {
        System.out.println("=== Customer List ===");
        for (Customer c : customerList) {
            System.out.println(c.getFullName() + " | " + c.getEmail() + " | $" + c.getBalance());
        }
    }
    
    private void saveCustomerToFile(Customer customer) {
        try (java.io.FileWriter fw = new java.io.FileWriter(CUSTOMER_FILE, true)) {
        	fw.write("\n");
            fw.write(customer.getFirstName() + "\n");
            fw.write(customer.getLastName() + "\n");
            fw.write(customer.getEmail() + "\n");
            fw.write(customer.getBalance() + "\n");
            fw.write(customer.getTotalSpending() + "\n");
            fw.write(customer.getMembershipType() + "\n");
            //fw.write("\n");
        } catch (Exception e) {
            System.out.println("Error saving customer: " + e.getMessage());
            //System.out.println();
        }
    }
    
    private void loadCustomersFromFile() {
        try (Scanner fileScanner = new Scanner(new java.io.File(CUSTOMER_FILE))) {
            while (fileScanner.hasNextLine()) {
                String firstName = skipEmpty(fileScanner);
                if (firstName == null) break;

                String lastName = skipEmpty(fileScanner);
                if (lastName == null) break;

                String email = skipEmpty(fileScanner);
                if (email == null) break;

                String balanceLine = skipEmpty(fileScanner);
                if (balanceLine == null) break;
                
                String spendingLine = skipEmpty(fileScanner);
                if (spendingLine == null) break;

                String membershipType = skipEmpty(fileScanner);
                if (membershipType == null) break;
                
                double balance = Double.parseDouble(balanceLine);
                double spending = Double.parseDouble(spendingLine);
                
                Customer customer = new Customer(firstName, lastName, email, balance);
                customer.setTotalSpending(spending);
                customer.setMembershipType(membershipType);

                customerList.add(customer);
            }

            System.out.println("Loaded " + customerList.size() + " customers from file.");
        } catch (java.io.FileNotFoundException e) {
            System.out.println("Customer file not found — starting with an empty list.");
        } catch (Exception e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }

    private String skipEmpty(Scanner sc) {
        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (!line.isEmpty()) return line;
        }
        return null;
    }
    
    private static void saveAllCustomersToFile() {
        try (java.io.PrintWriter out =
                 new java.io.PrintWriter(new java.io.FileWriter(CUSTOMER_FILE, false))) {
            for (Customer c : customerList) {
                out.println(c.getFirstName());
                out.println(c.getLastName());
                out.println(c.getEmail());
                out.println(c.getBalance());
                out.println(c.getTotalSpending());
                out.println(c.getMembershipType());
                out.println(); // blank line between customers
            }
        } catch (Exception e) {
            System.out.println("Error saving customers: " + e.getMessage());
        }
    }

    	
}
