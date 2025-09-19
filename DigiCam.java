import java.util.Random;

class DigiCam extends Product {
    private String model;
    private int batteryLife;
    private boolean isRented;
    private int reservedBy;

    public DigiCam() {
        super();
        this.model = "";
        this.isRented = false;
        this.reservedBy = -1;

        // Randomly set the battery between 0 to 100%
        this.batteryLife = (int) (Math.random() * 101);
    }

    public DigiCam(int ID, String type, double price, int stock, String model, boolean isRented,
            int reserevedBy) {
        super(ID, type, price, stock);
        this.model = model;
        this.isRented = isRented;
        this.reservedBy = reserevedBy;
        this.batteryLife = (int) (Math.random() * 101);
    }

    @Override
    public void describe() {
        System.out.println("This is the ultimate DigiCam that will change your photo expereince!");
    }

    @Override
    public void usuageInstruction() {
        // More detailed instruction to be implemented
        System.out.println("1. Press the power on button.");
        System.out.println("2. Take a photo!");
    }

    public void replaceBattery() {
        batteryLife = 100;
    }

}
