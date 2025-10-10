public class DigiCam extends Product implements Rentable {
    private String model;
    private int batteryLife;
    //private boolean isRented;
    private boolean isOn;

    public DigiCam() {
        super();
        this.model = "";
        // Randomly set the battery between 0 to 100%
        this.batteryLife = (int) (Math.random() * 101);
    }

    public DigiCam(String type, double price, int stock, String model) {
        super(type, price, stock);
        this.model = model;
        this.batteryLife = (int) (Math.random() * 101);
    }

    @Override
    public void describe() {
        System.out.println("This is the ultimate DigiCam that will change your photo expereince!");
    }

    @Override
    public void usageInstruction() {
        // More detailed instruction to be implemented
        System.out.println("1. Press the power on button.");
        System.out.println("2. Take a photo!");
        System.out.println("3. Turn off the camera when not in use.");
    }

    // Getters and Setters
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getBatteryLife() {
        return batteryLife;
    }

    public void setBatteryLife(int batteryLife) {
        this.batteryLife = batteryLife;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

    public void powerOn() {
        if (batteryLife >= 10) {
            batteryLife -= 10;
        } else {
            batteryLife = 0;
            System.out.println("Battery is 0%. Please replace your battery.");
        }
    }

    /*
    @Override
    public boolean isRentable() {
        return super.getStock() > 0;
        super.setStock(super.getStock() - 1);
    }
    */
    
    //newly added method
    @Override
    public void rent(int quantity) {
    	if (quantity <= 0) {
    		throw new IllegalArgumentException("Quantity must be positive");
    	}
    	if (quantity > getStock()) {
    		throw new IllegalStateException("Not enough stock available");
    	}
    	sellOrRent(quantity);
    }
    
    @Override
    public void reserve(int quantity) {
    	if (quantity <= 0) {
    		throw new IllegalArgumentException("Quantity must be positive");
    	}
    	if (quantity > getStock()) {
    		throw new IllegalStateException("Not enough stock available");
    	}
    	sellOrRent(quantity);
    }
    
    @Override
    public String displayInfo() {
        return getInfo() + " | $" + getPrice() + " | " + getStock();
    }
    
    @Override
    public String getInfo() {
    	return "ID: " + getId() + " | " + getType() + " (" + getModel() + ")";
    }

}
