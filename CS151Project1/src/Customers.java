
public class Customers {
	private String firstName;
	private String lastName;
	private int userId;
	
	private int numOfUsers = 0;
	
	
	public Customers(String firstName, String lastName, int userId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userId = userId;
		this.numOfUsers++;
	}
}
