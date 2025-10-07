package project.models;

import java.util.Scanner;
import java.time.Instant;
import java.util.InputMismatchException; //for scanner, throw if wrong type
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import project.exceptions.InvalidUserChoice;
import project.abstractclasses.Product;
import project.interfaces.RentableTemp;

public class ShopTemp {

    private static List<VendorTemp> vendorsList = new ArrayList<>();

    public ShopTemp(){
        createVendors();
    }

    //preset datas for Vendors and their products (CONFIRMED AND TESTED)
    protected void createVendors(){

        //create Vendors
        VendorTemp labubuVendor = new VendorTemp("Labubu Rental Shop", "rentALabubu@gmail.com");
        VendorTemp digiCamVendor = new VendorTemp("Digicam Rental Shop", "rentDigitalCamera@gmail.com"); 
        
        //Vendors stock items to inventory (Vendor buys random labubus)
        Random rand = new Random(); 
        String[] possibleColors = {"Luck", "Macaron", "Lychee", "Berry", "Love", "Hope"}; //possible labubu's color
        String[] possibleModels = DIGICAM_MODELS; //possible digicam models

        //max 100 instances for Labubu class
        for(int i = 0; i < 100; i++){

            double price = 10 + rand.nextDouble() * 20; //random price between 10 and 30 (random.nextDouble() gives [0.0 (inclusive), 1.0 (exlusive)])
            String color = possibleColors[rand.nextInt(possibleColors.length)]; //random color
            boolean isRare = rand.nextBoolean(); //true or false for isRare chosen randomly (this is 50% rare, need to look up syntax to decrease the amount of rare labubus)
            labubuVendor.stockLabubu((isRare ? price * 1.5 : price), color, isRare); //50% price increase if isRare
        }

        //only 50 instances for Digicam class
        for(int i = 0; i < 50; i++){

            double price = 50 + rand.nextDouble() * 150; //random price b/w 50-200 range
            String model = possibleModels[rand.nextInt(possibleModels.length)]; //random model
            digiCamVendor.stockDigicam(price, model);
        }

        //add the vendors to the Shop's vendorsList
        vendorsList.add(labubuVendor);
        vendorsList.add(digiCamVendor);
    }//end for createVendors()

    //make the user who uses our system pick a role first
    public void displayRoleMenu(){
        System.out.println("===== CS 151 Pop-Up Plaza =====");
        System.out.println("[1] I'm a Vendor");
        System.out.println("[2] I'm a Customer");
        System.out.println("[0] Exit :( ");
        System.out.println("Type an integer (0, 1, or 2)");
        System.out.println("=========================");
        System.out.print("Enter your choice: ");
    }

    public void displayVendorMenu(){
        System.out.println("===== CS 151 Vendor Menu =====");
        System.out.println("[1] Update price by productId");
        System.out.println("[2] List my inventory");
        System.out.println("[3] Discontinue an item by productId");
        System.out.println("[4] Set Promo");
        System.out.println("[5] Cancel Promo");
        System.out.println("[6] Add Funds");
        System.out.println("[7] Withdraw Cash");
        System.out.println("[8] Log out");
        System.out.println("[0] Exit");
        System.out.println("=========================");
        System.out.print("Enter your choice: ");
    }

    public void displayCustomerMenu(){
        System.out.println("===== CS 151 Customer Menu =====");
        System.out.println("[1] List vendors"); //TODO: ADD a method in ShopTemp to display vendors from vendorsList
        System.out.println("[2] Browse vendor Inventory by ventorId");
        System.out.println("[3] Quote a product"); //must use product.quoteRental(Instant now)
        System.out.println("[4] Rent the quoted product");
        System.out.println("[5] Return Product");
        System.out.println("[6] Add Funds");
        System.out.println("[7] View balance");
        System.out.println("[8] Log out");
        System.out.println("[0] Exit");
        System.out.println("=========================");
        System.out.print("Enter your choice: ");
    }

    public int getRole(Scanner sc){ //NOTE: this is helper method for handleRole()
        while(true){
            displayRoleMenu();
            try{
                int choice = sc.nextInt();
                sc.nextLine(); //must do this because nextInt() leaves the line 
                if(choice < 0 || choice > 2){
                    throw new InvalidUserChoice("\n\nEnter 0, 1 or 2!\n");
                }
                return choice;
            }catch (InputMismatchException e){

                sc.nextLine(); //since exception is thrown, new line is not handled
                System.out.println("\n\nInvalid input: you must enter an integer\n");
            }catch(InvalidUserChoice e){

                //if just invalidUserChoice, newline is handled above
                System.out.println(e.getMessage());
            }
        }
    }//end of getRole() //make sure to handle Exit [0] after this call

    //use this method, when the choice returned from getRole() is "[1] I'm a vendor"
    //same getChoice logic
    public int getVendorChoice(Scanner sc){ //NOTE: this is helper method for handleVendorChoice()

        while(true){
            displayVendorMenu();
            try{
                int choice = sc.nextInt();
                sc.nextLine();
                if(choice < 0 || choice > 8){
                    throw new InvalidUserChoice("\n\nYour choice must be between 0 and 8 (inclusive)!\n");
                }
                return choice;
            }catch (InputMismatchException e){
                sc.nextLine(); 
                System.out.println("\n\nInvalid input: you must enter an integer\n");

            }catch(InvalidUserChoice e){
                System.out.println(e.getMessage());
            }
        }
    }//end of getVendorChoice(), make sure to handle exit (0) and log out (if log out, do getRole again)

    //same logic again, there might be a way to make this short, I don't think I'll have time to do that for this project TT
    public int getCustomerChoice(Scanner sc){ //NOTE: this is helper method for handleCustomerChoice()

        while(true){
            displayCustomerMenu();
            try{
                int choice = sc.nextInt();
                sc.nextLine();
                if (choice < 0 || choice > 8){
                    throw new InvalidUserChoice("\n\nYour choice must be between 0 and 8 (inclusive)!\n");
                }
                return choice;
            }catch (InputMismatchException e){
                sc.nextLine(); 
                System.out.println("\n\nInvalid input: you must enter an integer\n");

            }catch(InvalidUserChoice e){
                System.out.println(e.getMessage());
            }
        }
    }//end of getCustomerChoice(), make sure to handle exit (0) and log out (if log out, do getRole again)

    public void handleRole(Scanner sc){
        
        if(sc == null) throw new NullPointerException("Scanner cannot be null");

        int roleChoice = getRole(sc);
        
        switch(roleChoice){
            case 0 -> helperExit(sc);
            case 1 -> handleVendorChoice(sc);
            case 2 -> handleCustomerChoice(sc);
            default -> {
                System.out.println("this default branch will/should never happen");
                throw new IllegalStateException("Default branch is getting executed");
            }
        }

    }//end of handleRole()

    public void helperExit(Scanner sc){
        System.out.println("\n\nI am sad to see you go :'(");
        sc.close();
        System.exit(0);
    }//end of helperExit


    public void handleVendorChoice(Scanner sc){ //NOTE: this is helper method for handleRole()
       
        if(sc == null) throw new NullPointerException("Scanner cannot be null");

        int vendorChoice = getVendorChoice(sc);

        switch (vendorChoice){
            case 0 -> helperExit(sc);
            case 8 -> {
                System.out.println("Successfully logged out!");
                handleRole(sc); 
            }
             //TODO: all 1-7 cases
        }
    }//end of handleVendorChoice

    public void handleCustomerChoice(Scanner sc){//NOTE: this is helper method for handleRole()

        if(sc == null) throw new NullPointerException("Scanner cannot be null");

        int customerChoice = getCustomerChoice(sc);

        switch (customerChoice){
            case 0 -> helperExit(sc);
            case 8 -> {
                System.out.println("Successfully logged out!");
                handleRole(sc); 
            }
            //TODO: all 1-7 cases
        }
        
    }//end of handleCustomerChoice






    //getter for vendorsList
    public static List<VendorTemp> getVendorsList(){ return vendorsList; }

    //popular models for DigiCams (I got this from ChatGPT)
    //I will take more models later so that each digicam having a uique ID makes sense
    private static final String[] DIGICAM_MODELS = {
    "Canon PowerShot A70",
    "Canon PowerShot G7 X",
    "Sony Cyber-shot DSC-RX100",
    "Sony Alpha a6000",
    "Nikon Coolpix P950",
    "Nikon Z50",
    "Fujifilm X100V",
    "Panasonic Lumix DMC-LX10",
    "Olympus PEN E-PL10",
    "GoPro HERO10 Black"
};

    public static void main(String[] args){
        
        ShopTemp shop = new ShopTemp();

        /* This (presetting demo data) works! 
         * try printing out all the products of each vendor
         
        for(VendorTemp v : ShopTemp.vendorsList){
            v.debug();
        }
        */

        Scanner sc = new Scanner(System.in);
        
        shop.handleRole(sc);
        
       


    }

}
