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
	
	public String getEmail() {
	    return this.email;
	}
	
	public double getTotalSpending() {
		return amountSpent;
	}
	//needs to be updated after creating other class
	public void makePurcahse(Product product, double quantity) {
		double cost = product.getPrice() * quantity;
		
		if (cost > balance) {
			System.out.println("Insufficient Balance! Please add more cash!");
			return;
		}
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
		this.balance -= cost;
		this.amountSpent += cost;
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
	
}
