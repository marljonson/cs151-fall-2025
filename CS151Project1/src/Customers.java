
public class Customers {
	private String firstName;
	private String lastName;
	private int userId = 0;;
	private String email;
	private String password;
	private String membershipType; //Silver, Gold, Platinum, Diamond
	private double cash;
	private double amountPurchased;
	private static int numOfUsers = 0;
	
	public Customers(String firstName, String lastName, String email, double cash) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = "welcomeToLabubuShop1@"; //default password which can later be updated in main
		this.cash = cash;
		this.amountPurchased = 0; 
		this.membershipType = "Silver";   //default type which can later be updated according to amount spent with us
		numOfUsers++;
		this.userId = numOfUsers;
	}
	
	public String getFullName() {
		return firstName + " " + lastName;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void updatePassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return this.password;
	}
	public boolean passwordMatched(String password) {
		return this.password.equals(password);
	}
	
	public void updateCash(double cash) {
		this.cash += cash;
	}
	
	public double getBalance() {
		return cash;
	}
	
	public double getTotalSpent() {
		return amountPurchased;
	}
	//needs to be updated after creating other class
	public void makePurcahse(double amount) {
		if (amount > cash) {
			System.out.println("Insufficient Balance! Please add more cash!");
			return;
		}
		double cost = amount;
		if (this.membershipType.equals("Gold")) {
			cost = amount * 0.95;
		}
		else if (this.membershipType.equals("Platinum")) {
			cost = amount * 0.9;
		}
		else if (this.membershipType.equals("Diamond")) {
			cost = amount * 0.8;
		}
		else {
			cost = amount;
		}
		this.cash -= cost;
		this.amountPurchased += amount;
		updateMembershipType();
	}
	
	public void updateMembershipType() {
		if (amountPurchased > 2000) {
			this.membershipType = "Diamond";
		}
		else if (amountPurchased > 1000) {
			this.membershipType = "Platinum";
		}
		else if (amountPurchased > 500) {
			this.membershipType = "Gold";
		}
		else {
			this.membershipType = "Silver";
		}
	}
	
	public String getMembershipType() {
		return this.membershipType;
	}
}
