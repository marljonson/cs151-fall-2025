import java.util.HashMap;
import java.util.Map;

public class Customer {
	private String firstName;
	private String lastName;
	private int userId = 0;
	private String email;
	//private String password;
	private String membershipType; //Silver, Gold, Platinum, Diamond
	private double balance;
	private double amountSpent;
	private static int numOfCustomers = 0;
	private Map<Integer, Integer> rentedProducts = new HashMap<>();
	private Map<Integer, Integer> boughtProducts = new HashMap<>();
	
	public Customer() {
		this.firstName = null;
		this.lastName = null;
		this.email = null;
		//this.password = "welcomeToLabubuShop1@"; //default password which can later be updated in main
		this.balance = 999;
		this.amountSpent = 0; 
		this.membershipType = "Silver";   //default type which can later be updated according to amount spent with us
		numOfCustomers++;
		this.userId = numOfCustomers;
	}
	public Customer(String firstName, String lastName, String email, double cash) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		//this.password = "welcomeToLabubuShop1@"; //default password which can later be updated in main
		this.balance = cash;
		this.amountSpent = 0; 
		this.membershipType = "Silver";   //default type which can later be updated according to amount spent with us
		numOfCustomers++;
		this.userId = numOfCustomers;
	}
	
	public void setFirstName(String name) {
		this.firstName = name;
	}
	
	public void setLastName(String name) {
		this.lastName = name;
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	public void setUserId(int id) {
		this.userId = id;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void addCash(double cash) {
		this.balance += cash;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
	    return this.email;
	}
	
	public double getTotalSpending() {
		return amountSpent;
	}
	
	//needs to be updated after creating other class
	public void makePurchase(Product product, int quantity, Vendor vendor) {
		if (quantity <= 0) {
	        throw new IllegalArgumentException("Quantity must be positive");
	    }
		
		//Check if the vendor has the product you want to buy
		if (!vendor.getProductList().containsKey(product.getId())) {
			System.out.println(product.getType() + " is not available at the vendor " + vendor.getName());
			return;
		}
		
		//Check if vendor has enough stock
		int availableStock = vendor.getVendorStock().getOrDefault(product.getId(), 0);
		if (quantity > availableStock) {
			System.out.println(vendor.getName() + " has only " + availableStock + " left for " + product.getType());
			return;
		}
		
		double cost = product.getPrice() * quantity;
		
		if (product instanceof Buyable buyable) {
			buyable.adjustPriceOnRarity();
			cost = buyable.getPrice() * quantity;
			cost = applyMembershipBenefit(cost);
			if (cost > balance) {
	            System.out.println("Insufficient balance! Please add more cash.");
	            return;
			}
			buyable.buy(quantity);
			System.out.println(this.getFullName() + " bought " + quantity + " " + product.getType());
			product.usageInstruction();
			customerCheckOut(cost);
			boughtProducts.put(product.getId(), boughtProducts.getOrDefault(product.getId(), 0) + quantity);
			vendor.makeSale(product.getId(), quantity, cost);
		}
		else if (product instanceof Rentable rentable) {
			cost = applyMembershipBenefit(cost);
			if (cost > balance) {
	            System.out.println("Insufficient balance! Please add more cash.");
	            return;
			}
			rentable.rent(quantity);
			System.out.println(this.getFullName() + " rented " + quantity + " " + product.getType());
			product.usageInstruction();
			customerCheckOut(cost);
			rentedProducts.put(product.getId(), rentedProducts.getOrDefault(product.getId(), 0) + quantity);
			vendor.makeSale(product.getId(), quantity, cost);
			
		}
		else {
			System.out.println("This product cannot be bought or rented. Please choose other product");
		}
	}
	
	public void customerCheckOut(double cost) {
        this.balance -= cost;
        System.out.println(this.getFullName() + " reamining balance is $ " + balance);
        this.amountSpent += cost;
        System.out.println(this.getFullName() + " total spent so far is $" + amountSpent);
	}
	
	public double applyMembershipBenefit(double cost) {
		if (this.membershipType.equals("Gold")) {
			cost = cost * 0.95;
		}
		else if (this.membershipType.equals("Platinum")) {
			cost = cost * 0.9;
		}
		else if (this.membershipType.equals("Diamond")) {
			cost = cost * 0.8;
		}
		else {
			cost = cost * 1;
		}
		return cost;
	}
	
	public void updateMembershipType(String type) {
		if (type.equals("Diamond")) {
			this.membershipType = "Diamond";
			System.out.print("We charge you $100 upon purchasing membership. You will have 20% discount on every purcahse ");
			System.out.println("and you will be able to reserve the products you want to rent or buy");
			balance -= 100;
			this.amountSpent += 100;
			System.out.println("Your remaining balance is " + balance);
		}
		else if (type.equals("Platinum")) {
			this.membershipType = "Platinum";
			System.out.print("We charge you $80 upon purchasing membership. You will have 10% discount on every purcahse ");
			System.out.println("and you will be able to reserve the products you want to rent or buy");
			balance -= 80;
			this.amountSpent += 80;
			System.out.println("Your remaining balance is " + balance);
		}
		else if (type.equals("Gold")) {
			this.membershipType = "Gold";
			System.out.println("We charge you $50 upon purchasing membership. You will have 50% discount on every purcahse ");
			balance -= 50;
			this.amountSpent += 50;
			System.out.println("Your remaining balance is " + balance);
		}
		else {
			this.membershipType = "Silver";
		}
	}
	
	public String getMembershipType() {
		return this.membershipType;
	}
	
	public void returnProduct(Product product, int quantity) {
	    if (quantity <= 0) {
	        throw new IllegalArgumentException("Quantity must be positive");
	    }
	    
	    if (product instanceof Rentable) {
	    	int productId = product.getId();
		    int productCustomerRented = rentedProducts.getOrDefault(productId, 0);
		    if (productCustomerRented == 0) {
		    	System.out.println(getFullName() + " cannot return " + product.getType()
	            	+ " because they never bought/rented it.");
		    	return;
		    }
		    
		    if (productCustomerRented < quantity) {
		        System.out.println(getFullName() + " cannot return " + quantity
		            + " " + product.getType() + " (only rented " + productCustomerRented + ")");
		        return;
		    }
	        product.returnProduct(quantity);
	        System.out.println(getFullName() + " returned " + quantity + " rented " + product.getType());
	        rentedProducts.put(productId, productCustomerRented - quantity);
	    } 
	    else {
	        System.out.println("This product type cannot be returned.");
	    }
	}
	
	public void getOwnedItems(Map<Integer, Product> productCatalog) {
	    System.out.println("=== Items owned by " + getFullName());

	    if (boughtProducts.isEmpty() && rentedProducts.isEmpty()) {
	        System.out.println("No items bought or rented yet.\n");
	        return;
	    }
	    
	    if (!boughtProducts.isEmpty()) {
	        System.out.println("-- Bought Products --");
	        for (Map.Entry<Integer, Integer> entry : boughtProducts.entrySet()) {
	            int productId = entry.getKey();
	            int qty = entry.getValue();
	            Product product = productCatalog.get(productId); // look up by ID
	            if (product != null && qty > 0) {
	                System.out.println(product.getType() + " (x" + qty + ")");
	            }
	        }
	    }

	    if (!rentedProducts.isEmpty()) {
	        System.out.println("-- Rented Products --");
	        for (Map.Entry<Integer, Integer> entry : rentedProducts.entrySet()) {
	            int productId = entry.getKey();
	            int qty = entry.getValue();
	            Product product = productCatalog.get(productId);
	            if (product != null && qty > 0) {
	                System.out.println(product.getType() + " (x" + qty + ")");
	            }
	        }
	    }
	}
	
	public void reserveProduct(Product product, int quantity, Vendor vendor) {
		if (quantity <= 0) {
	        throw new IllegalArgumentException("Quantity must be positive");
	    }
		
		//Check if the vendor has the product you want to buy
		if (!vendor.getProductList().containsKey(product.getId())) {
			System.out.println(product.getType() + " is not available at the vendor " + vendor.getName());
			return;
		}
		
		//Check if vendor has enough stock
		int availableStock = vendor.getVendorStock().getOrDefault(product.getId(), 0);
		if (quantity > availableStock) {
			System.out.println(vendor.getName() + " has only " + availableStock + " left for " + product.getType());
			return;
		}
		
		if (product instanceof Rentable rentable) {
			if (membershipType.equals("Gold") || membershipType.equals("Diamond")) {
				rentable.reserve(quantity);
				System.out.println(this.getFullName() + " reserved " + quantity + " " + product.getType());
			}
			else {
				System.out.println("Your need to be diamond or gold member to be able to reserve");
			}
		}
		else {
			System.out.println("This item cannot be reserved.");
		}
	}
	
	public void getMemberShipDetail() {
		System.out.println("The cost for Diamond member is $100 and Diamond type member are eligible for 20% discount for every"
				+ "purchase and they will be able to reserve the product they want to buy or reserve.");
		System.out.println("The cost for Platinum member is $80 and Platinum type member are eligible for 10% discount for every"
				+ "purchase and they will be able to reserve the product they want to buy or reserve.");
		System.out.println("The cost for Gold member is $50 and Gold type member are eligible for 5% discount for every"
				+ "purchase");
		System.out.println("The cost for Silver member is $0 and there is no benefits for Silver type member.");
	}
	
	public void getCustomerDetail() {
		System.out.println("C u s t o m e r   D e t a i l ");
	    System.out.println("ID          : " + userId);
	    System.out.println("Name        : " + getFullName());
	    System.out.println("Email       : " + email);
	    System.out.println("Balance     : $" + balance);
	    System.out.println("Total Spent : $" + amountSpent);
	    System.out.println("Membership  : " + membershipType);
	    System.out.println();
	}
}
