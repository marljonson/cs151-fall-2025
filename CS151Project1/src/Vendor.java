import java.util.HashMap;
import java.util.Map;

public class Vendor {
	private Map<Integer, Product> productList; //product list for the vendor
	private Map<Integer, Integer> vendorStock; //to track stocks available at this vendpr
	private String name;
	private int id;
	private String email;
	private double earning;
	private static int numOfVendors = 0;
	
	public Vendor() {
		this.name = null;
		this.email = null;
		this.earning = 0;
		numOfVendors++;
		this.id = numOfVendors;
		this.productList = new HashMap<>();
		this.vendorStock = new HashMap<>();
	}
	
	public Vendor(String name, String email) {
		this.name = name;
		this.email = email;
		this.earning = 0;
		numOfVendors++;
		this.id = numOfVendors;
		this.productList = new HashMap<>();
		this.vendorStock = new HashMap<>();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public double getTotalEarning() {
		return earning;
	}
	
	public int getID() {
		return id;
	} 
	
	public Map<Integer, Product> getProductList() {
		return productList;
	}
	
	public Map<Integer, Integer> getVendorStock() {
		return vendorStock;
	}
	
	public void addProduct(Product product) {
		if (product == null) {
			throw new IllegalArgumentException("Cannot add a null product");
		}
		productList.put(product.getId(), product);
		vendorStock.put(product.getId(), 0);
		System.out.println("Vendor " + name + "added " + product.getType());
	}
	
	public void addProduct(Product product, int quantity) {
		if (product == null) {
			throw new IllegalArgumentException("Cannot add a null product");
		}
		if (quantity < 0) {
			throw new IllegalArgumentException("Quantity cannot be less than zero");
		}
		productList.put(product.getId(), product);
		vendorStock.put(product.getId(), vendorStock.getOrDefault(product.getId(), 0) + quantity);
		System.out.println("Vendor " + name + "added " + quantity + " " + product.getType());
	}
	
	public void removeProduct(Product product) {
		if (product == null) {
			throw new IllegalArgumentException("Cannot remove a null product");
		}
		if (productList.containsKey(product.getId())) {
			productList.remove(product.getId()); 
			vendorStock.remove(product.getId());
			System.out.println("Vendor  "+ name + "removed " + product.getType());
		}
		else {
			System.out.println(product.getType() + " is not available at thsi vendor");
		}
	}
	
	public void removeProduct(int productId) {
		if (productId < 0) {
			throw new IllegalArgumentException("Cannot remove a non-existing product");
		}
		if (productList.containsKey(productId)) {
			Product removed = productList.remove(productId); 
			vendorStock.remove(productId);
			System.out.println("Vendor  "+ name + "removed " + removed.getType());
		}
		else {
			System.out.println("This product is not available at thsi vendor");
		}
	}
	
	public void updatePrice(int productId, double newPrice) {
		if (newPrice < 0) {
			throw new IllegalArgumentException("The price cannot be negative");
		}
		Product product = productList.get(productId);
		if (product != null) {
			System.out.print("The price of " + product.getType() + " is increased from " + product.getPrice());
			product.setPrice(newPrice);
			System.out.println(" to " + product.getPrice());
		}
		else {
			System.out.println("Such product does not exist at this vendor");
		}
	}
	
	public void displayitem() {
		System.out.println("Products offered by the vendor " + name);
		for (Product product : productList.values()) {
			int stock = vendorStock.getOrDefault(product.getId(), 0);
			if (product instanceof Labubu labubu) {
				System.out.println(labubu.getId() + " | " + labubu.getType() + " " + labubu.getColor() + " | $" + labubu.getPrice() +
					" | " + stock + "left ");
			}
			else if (product instanceof DigiCam digiCam) {
				System.out.println(digiCam.getId() + " | " + digiCam.getType() + " " + digiCam.getModel() + " | $" + digiCam.getPrice() +
						" | " + stock + "left ");
			}
		}
	}
	
	public void updateStock(int productId, int quantity) {
		Product product = productList.get(productId);
		if (product != null) {
			if (quantity > product.getStock()) {
				System.out.println("We only have " + product.getStock() + " " + product.getType());
				return;
			}
			else {
				vendorStock.put(productId, quantity);
			}
		}
		else {
			System.out.println("Such product does not exist at thsi vendor");
		}
	}
	
	public void makeSale(int productId, int quantity, double amount) {
		earning += amount;	
		int stock = vendorStock.getOrDefault(productId, 0);
		vendorStock.put(productId, stock - quantity);
	}
	
}
