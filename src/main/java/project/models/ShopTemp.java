/*
 * Vendors will have to verify their email address before they can update their inventory (I only added email address verification (not the password) to shorten the validate the process of validateVendorAndReturnVendor)
 * labubu vendor's email = rentlabubu11@gmail.com
 * digicam vendor's email = rentdigitalcam22@gmail.com
 * 
 * Note: I am not using any database, all the options work in correspondence when a user chooses option "[8] Log out" 
 * 
 * every step of the program can be shut down by typing in "exit"
 * 
 * typing in "0" when prompted "type 0 to go back" will go 1 level above
 */

package project.models;

import java.util.Scanner;
import java.time.Instant;
import java.util.InputMismatchException; //for scanner, throw if wrong type
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import project.exceptions.InvalidUserChoice;
import project.exceptions.OverHundredObjects;
import project.exceptions.ProductNotFound;
import project.exceptions.VendorNotFound;
import project.abstractclasses.Product;
import project.interfaces.RentableTemp;

public class ShopTemp implements Cloneable {

    private List<VendorTemp> vendorsList = new ArrayList<>();
    private CustomerTemp currCus;

    //for 100 maximum instances requirement
    public static final int MAXIMUM_INSTANCES = 100;

    //I'll be creating instances of these classes 
    public static int instancesVendorTemp = 0;
    public static int instancesCustomerTemp = 0;
    public static int instancesLabubu = 0;
    public static int instancesDigicam = 0;

    public ShopTemp(){
        createVendors();
        createCustomer();
    }

    //preset datas for Vendors and their products (CONFIRMED AND TESTED)
    private void createVendors(){

        while(true){ //if more than 100 instances are made, system will display an error without crashing the program (but this is preset, this won't crash or throw)
            try{
                //create Vendors
                VendorTemp labubuVendor = new VendorTemp("Labubu Rental Shop", "rentlabubu11@gmail.com");
                instancesVendorTemp++;

                if(instancesVendorTemp > MAXIMUM_INSTANCES) throw new OverHundredObjects("Vendor instances must be less than 100");
            
                VendorTemp digiCamVendor = new VendorTemp("Digicam Rental Shop", "rentdigitalcam22@gmail.com"); 
                instancesVendorTemp++;

                if(instancesVendorTemp > MAXIMUM_INSTANCES) throw new OverHundredObjects("Vendor instances must be less than 100");

                
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

                    //stockLabubu() calls Labubu constructor 1 time
                    instancesLabubu++;
                    if(instancesLabubu > MAXIMUM_INSTANCES) throw new OverHundredObjects("Labubu instances must be less than 100");
                }

                //only 50 instances for Digicam class
                for(int i = 0; i < 50; i++){

                    double price = 50 + rand.nextDouble() * 150; //random price b/w 50-200 range
                    String model = possibleModels[rand.nextInt(possibleModels.length)]; //random model
                    digiCamVendor.stockDigicam(price, model);

                    //stockDigicam() calls Digicam constructor 1 time
                    instancesDigicam++;
                    if(instancesDigicam > MAXIMUM_INSTANCES) throw new OverHundredObjects("DigiCamTemp instances must be less than 100");
                }

                //add the vendors to the Shop's vendorsList
                vendorsList.add(labubuVendor);
                vendorsList.add(digiCamVendor);
                return;
            }catch(OverHundredObjects e){
                System.out.println(e.getMessage());
            }
        }
    }//end for createVendors()

    private void createCustomer(){
        try{
            this.currCus = new CustomerTemp("guest", "01", "firstCustomer@default.com", 200);
            if(instancesCustomerTemp > MAXIMUM_INSTANCES) throw new OverHundredObjects("CustomerTemp instances must be less than 100");
        }catch(OverHundredObjects e){
            System.out.println(e.getMessage());
        }
    }

    //make the user who uses our system pick a role first
    public void displayRoleMenu(){
        System.out.println("\n\n===== CS 151 Pop-Up Plaza =====");
        System.out.println("[1] I'm a Vendor");
        System.out.println("[2] I'm a Customer");
        System.out.println("[0] Exit :( ");
        System.out.println("Type an integer (0, 1, or 2)");
        System.out.println("=========================");
        System.out.print("Enter your choice: ");
    }

    public void displayVendorMenu(){
        System.out.println("\n\n===== CS 151 Vendor Menu =====");
        System.out.println("[1] Update price by productId");
        System.out.println("[2] List my inventory");
        System.out.println("[3] Discontinue an item by productId");
        System.out.println("[4] Set Promo");
        System.out.println("[5] Cancel Promo");
        System.out.println("[6] Add Funds");
        System.out.println("[7] Withdraw Cash");
        System.out.println("[8] Log out");
        System.out.println("[9] View Funds"); //Note: I added this at the last and want to keep using 8 (same number to log out) for both vendor and customer
        System.out.println("[0] Exit");
        System.out.println("=========================");
        System.out.print("Enter your choice: ");
    }

    public void displayCustomerMenu(){
        System.out.println("\n\n===== CS 151 Customer Menu =====");
        System.out.println("[1] List vendors"); 
        System.out.println("[2] Browse vendor inventory by vendorId");
        System.out.println("[3] Quote and Rent a product"); //must use product.quoteRental(Instant now)
        System.out.println("[4] View my active rentals");
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

                //updated here to handle "exit"
                String userInput = sc.nextLine().trim();
                helperCatchUserTypedExit(sc, userInput);

                int choice = Integer.parseInt(userInput);

                if(choice < 0 || choice > 2){
                    throw new InvalidUserChoice("\n\nEnter 0, 1 or 2!\n");
                }
                return choice;
            }catch(NumberFormatException e){
                System.out.println("\n\nInput must be an integer or type exit to exit the program");
            }catch (InputMismatchException e){
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
                //I had to update these to handle "exit" logic
                String userInput = sc.nextLine().trim();
                helperCatchUserTypedExit(sc, userInput);

                int choice = Integer.parseInt(userInput); //same as before, I just changed from sc.nextInt() to Integer.parseInt(userInput) to change String to int for our options
             
                if(choice < 0 || choice > 9){
                    throw new InvalidUserChoice("\n\nYour choice must be between 0 and 8 (inclusive)!\n");
                }
                return choice;
            }catch (NumberFormatException e){
                System.out.println("\n\nInvalid input: Enter an integer or type exit\n");
            }catch (InputMismatchException e){ 
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

                //updated here to handle "exit"
                String userInput = sc.nextLine().trim();
                helperCatchUserTypedExit(sc, userInput); //program will exit if the user type "exit" in userchoice method //I will need to handle other steps somewhere else to keep this "exit" logic alive

                int choice = Integer.parseInt(userInput);//change userInput from String to integer

                if (choice < 0 || choice > 8){
                    throw new InvalidUserChoice("\n\nYour choice must be between 0 and 8 (inclusive)!\n");
                }
                return choice;
            }catch (NumberFormatException e){
                System.out.println("\n\nInvalid input: Enter an integer or type exit\n");
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
            case 1 -> {
                VendorTemp currVendor = validateVendorAndReturnVendor(sc);
                if(currVendor == null) return; //MUST HANDLE IN THE MAIN (may be a while loop) --> when return goes back to handleRole(sc), we can calll handleRole(sc) here directly but this could cause StackOverflowError
                handleVendorChoice(sc, currVendor);
            }
            case 2 -> {
                handleCustomerChoice(sc);
            }
            default -> {
                System.out.println("this default branch will/should never happen");
                throw new IllegalStateException("Default branch is getting executed");
            }
        }

    }//end of handleRole()

    public VendorTemp validateVendorAndReturnVendor(Scanner sc){//validate Vendor if "I am a Vendor" is chosen

        while(true){
            System.out.print(" \n\nEnter your email address (or type 0 to go back): ");
            String userEmail = sc.nextLine().trim().toLowerCase();

            if(userEmail.equalsIgnoreCase("exit")){
                helperExit(sc);
            }

            if(userEmail.equals("0")){
                return null; //user type 0 -> exit
            }
            System.out.println();

            for(VendorTemp vendor : vendorsList){
                if(vendor.getEmail().equals(userEmail)){
                    return vendor;//found the valid vendor, return vendor 
                }
            }
            
            System.out.println("\n\nNo vendor found with that email, Please try again or type 0 to go back.");
        }
    }

    public void helperExit(Scanner sc){
        System.out.println("\n\nI am sad to see you go :'(\n");
        sc.close();
        System.exit(0);
    }//end of helperExit

    //I had to fix my whole program because I missed the requirement that says a user can exit the program anywhere anytime when he types "exit" (my program before was implemented so that a user can exit when type "0")
    public void helperCatchUserTypedExit (Scanner sc, String userInput) throws InvalidUserChoice{ //now i will change the logic inside the getCustomerChoice and getVendorChoice to let the user exit when type "exit" anywhere

        if(userInput == null || userInput.isBlank()){
            throw new InvalidUserChoice("Input cannot be null or blank");
        }

        if(userInput.equalsIgnoreCase("exit")){
            helperExit(sc);
        }
    }


    //if the user is a vendor
    public void handleVendorChoice(Scanner sc, VendorTemp currVendor){ //NOTE: this is helper method for handleRole()
       
        if(sc == null) throw new NullPointerException("Scanner cannot be null");

        while(true){
            int vendorChoice = getVendorChoice(sc);

            switch (vendorChoice){
                case 0 -> helperExit(sc);
                case 1 -> helperVendorCase1(sc, currVendor);
                case 2 -> currVendor.printInventory();
                case 3 -> helperVendorCase3(sc, currVendor);
                case 4 -> helperVendorCase4(sc, currVendor);
                case 5 -> currVendor.cancelPromo();
                case 6 -> helperVendorCase6(sc, currVendor);
                case 7 -> helperVendorCase7(sc, currVendor);
                case 8 -> { 
                    System.out.println("\n\nSuccessfully logged out!\n");
                    return; 
                    //handleRole(sc); //this could cause StackOverFlowError //instead of calling handleRole() we should handle this with a loop at a top level (either in main) //this works fine for now, I can only fix this if I have time. I have yet to review Collection lecture TT
                }
                case 9 -> { //check earnings case 

                    System.out.println("\n\n===== Your Funds =====");
                    System.out.println("$ " + currVendor.getBalance() + "\n\n");

                }
            }
        }
    }//end of handleVendorChoice

    //if the user is a customer
    public void handleCustomerChoice(Scanner sc){//NOTE: this is helper method for handleRole()

        if(sc == null) throw new NullPointerException("Scanner cannot be null");

        while(true){
            int customerChoice = getCustomerChoice(sc);

            switch (customerChoice){
                case 0 -> helperExit(sc);
                case 1 -> listAllVendors();
                case 2 -> helperCustomerCase2(sc);
                case 3 -> quoteAndRent(sc, this.currCus);
                case 4 -> currCus.viewMyRentals();
                case 5 -> helperCustomerCase5(sc, this.currCus);
                case 6 -> helperCustomerCase6(sc, this.currCus);
                case 7 -> {
                    System.out.println("\n\n===== My Wallet =====");
                    System.out.println("Total balance: $"+ currCus.getBalance() +"\n");
                }
                case 8 -> {
                    System.out.println("\n\nSuccessfully logged out!\n");
                    return;
                }
            }
        }
        
    }//end of handleCustomerChoice

    public void listAllVendors(){

        if(vendorsList.isEmpty()){ 
            System.out.println("Sorry, we are temporarily closed"); 
            return;
        }

        System.out.println("\n\n===== Vendor Directory =====");
        for(VendorTemp vendor : vendorsList){

            System.out.println("[Vendor ID: " + vendor.getVendorId() + "] " + vendor.getName());
            System.out.print("Promo: ");

            PromoWindow currPromo = vendor.getPromoWindow();
            if(currPromo != null && currPromo.activeNow(Instant.now())){
                System.out.print(vendor.getPromoWindow().getDiscountFraction() * 100 + "% off!\n");
            }
            else{
                System.out.print("None\n");
            } 
        }//end iterating thru vendorsList
        System.out.println("=============================");
    }


    /*
     *below are all helper methods for a vendor (specifically for switch statment's cases)
     *
     * 
     */

    //Vendor's case 1 -> update price by product id
    public void helperVendorCase1(Scanner sc, VendorTemp vendor){

        while(true){
            try{
                System.out.print("\n\nEnter the product's ID (or 0 to go back): ");

                String userInput = sc.nextLine().trim(); //updated here to fix user typed "exit" logic
                helperCatchUserTypedExit(sc, userInput);

                int vendorProductId = Integer.parseInt(userInput);
                //System.out.println();

                if(vendorProductId == 0) return; //user type 0 -> return //OR should I throw a custom exception here?

                System.out.print("\n\nEnter the updated price (or 0 to go back): ");
                double price = sc.nextDouble();
                sc.nextLine();
                //System.out.println();
                if(price == 0) return;//user type 0 -> return

                vendor.updatePriceById(vendorProductId, price);
                return; //price is successfully updated -> return 
                
            }catch(NumberFormatException e){
                System.out.println("Either type valid number or \"exit\"");
            }catch (ProductNotFound e){ 
                System.out.println(e.getMessage());
            }
            catch (InputMismatchException e){ //may not need this error anymore since I handle user input with my helperCatchUserTypedExit() and won't be using sc.nextInt() anymore
                System.out.println("Enter an integer for product ID and a double for updated price");
            }catch (InvalidUserChoice e){
                System.out.println(e.getMessage());
            }
        }
    }//end of vendor's case 1

    //Vendor's case 3 --> Discontinue a product by id
    public void helperVendorCase3(Scanner sc, VendorTemp vendor){
        
        while(true){
            try{
                System.out.print("\n\nEnter the product's ID (or 0 to go back): ");
                String userInput = sc.nextLine().trim();
                helperCatchUserTypedExit(sc, userInput);

                int vendorProductId = Integer.parseInt(userInput);
               
                //System.out.println();

                if(vendorProductId == 0) return; //user type 0 -> return

                vendor.discontinueProductById(vendorProductId);
                return; //successfully discontinued -> return

            }catch(NumberFormatException e){
                System.out.println("Either type valid number or \"exit\"");
            }catch (ProductNotFound e){ 
                System.out.println(e.getMessage());
            }
            catch(InvalidUserChoice e){
                System.out.println(e.getMessage());
            }
        }
    }//end of vendor's case 3

    //Vendor's case 4 -> set promo (give discount for every product for a period)
    public void helperVendorCase4(Scanner sc, VendorTemp vendor){

        while(true){
            try{
                System.out.print("\n\nEnter discount in fraction between 0.0 and 1.0 (or 0 to go back): ");
                String userInput = sc.nextLine().trim();
                helperCatchUserTypedExit(sc, userInput);

                double discountFraction = Double.parseDouble(userInput);

                if(discountFraction == 0) return; //user type 0 -> return

                //in reality, we should ask the vendor to input startTime and endTime
                //but I have yet to learn how to convert user's input to Instant object
                Instant start = Instant.now().minusSeconds(60);     // starts 60 seconds ago
                Instant end = Instant.now().plusSeconds(7 * 24 * 3600); // 7 days later

                vendor.setPromo(discountFraction, start, end);
                System.out.println("A promo window is created. Every product from your inventory is " + discountFraction * 100 + "% off!");
                return;

            }catch(NumberFormatException e){
                System.out.println("Either type valid number or \"exit\"");
            }catch(InvalidUserChoice e){
                System.out.println(e.getMessage());
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }//end of vendor's case 4

    //Vendor's case 6 --> add funds
    public void helperVendorCase6(Scanner sc, VendorTemp vendor){

        while(true){
            try{
                System.out.print("\n\nEnter the amount of credit (or 0 to go back): ");
                String userInput = sc.nextLine().trim();
                helperCatchUserTypedExit(sc, userInput);

                double amount = Double.parseDouble(userInput);
        
                if(amount == 0) return; //user type 0, exit
                
                vendor.credit(amount);
                return;

            }catch(NumberFormatException e){
                System.out.println("Either type valid number or \"exit\"");
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }catch (InputMismatchException e){
                System.out.println("Invalid input: please enter a number.");
            }catch (InvalidUserChoice e){
                System.out.println(e.getMessage());
            }
        }
    }//end of vendor's case 6

    //Vendor's case 7 --> Withdraw Cash
    public void helperVendorCase7(Scanner sc, VendorTemp vendor){

        while(true){
            try{
                System.out.print("\n\nEnter the amount to withdraw (or 0 to go back): ");
                String userInput = sc.nextLine().trim();
                helperCatchUserTypedExit(sc, userInput);

                double amount = Double.parseDouble(userInput);

                if(amount == 0) return; //user type 0 --> return 

                vendor.debit(amount);
                return;

            }catch(NumberFormatException e){
                System.out.println("Either type valid number or \"exit\"");
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }catch(IllegalStateException e) {
                System.out.println(e.getMessage());
            }catch (InputMismatchException e){
                System.out.println("Invalid input: please enter a number.");
            }catch(InvalidUserChoice e){
                System.out.println(e.getMessage());
            }
        }
    }//end of helperVendorCase7


    /*
     *below are all helper methods for a customer (specifically for switch statment's cases)
     *
     * 
     */

    //Customer's case 2 -> Browse vendor inventory by vendorID
    public void helperCustomerCase2(Scanner sc){

        listAllVendors(); //in case customer forgets vendor's ID //will only display 1 time
        while(true){
            try{
                System.out.print("\n\nEnter Vendor ID (or 0 to go back): ");
                String userInput = sc.nextLine().trim();
                helperCatchUserTypedExit(sc, userInput);

                int vId = Integer.parseInt(userInput);
                
                if(vId == 0) return; //user type 0 -> return

                VendorTemp currVendor = null;
                for(VendorTemp vendor : vendorsList){
                    if(vendor.getVendorId() == vId){
                        currVendor = vendor;
                        break; //break early after we find the vendor
                    } 
                }

                if (currVendor == null) throw new VendorNotFound("Vendor with this ID does not exist");
                currVendor.printInventory();
                return;

            }catch(NumberFormatException e){
                System.out.println("Either type valid number or \"exit\"");
            }catch(InputMismatchException e){ //we might not need this here anymore since we are not gonna use Scanner's nextInt()
                 System.out.println("Vendor ID must be an integer");
            }catch(VendorNotFound e){
                System.out.println(e.getMessage());
            }catch(InvalidUserChoice e){
                System.out.println(e.getMessage());
            }
        }
    }//end for helperCustomerCase2

    //Customer's case 3 -> Quote and rent a product 
    public void quoteAndRent(Scanner sc, CustomerTemp currCus){

        //same logic as helperCustomerCase2, I should have created 1 helper method where custmers can findVendorById
        listAllVendors(); //in case customer forgets vendor's ID //will only display 1 time
        while(true){
            try{
                System.out.print("\n\nEnter Vendor ID (or 0 to go back): ");
                String userInput = sc.nextLine().trim();
                helperCatchUserTypedExit(sc, userInput);

                int vId = Integer.parseInt(userInput);

                if(vId == 0) return; //user type 0 -> return

                VendorTemp currVendor = null;
                for(VendorTemp vendor : vendorsList){
                    if(vendor.getVendorId() == vId){
                        currVendor = vendor;
                        break; //break early after we find the vendor
                    } 
                }

                if (currVendor == null) throw new VendorNotFound("Vendor with this ID does not exist");
                System.out.println("===== Vendor Inventory =====");
                currVendor.printInventory();
                System.out.println("==============================");

                System.out.print("\n\nEnter the product ID you'd like to quote (or 0 to go back): "); //customer will see the price of the product with discount if there is any
                String userInput1 = sc.nextLine().trim();
                helperCatchUserTypedExit(sc, userInput1);

                int vendorProductId = Integer.parseInt(userInput1);
                
                if(vendorProductId == 0) return; 

                //go thru the productList of the vendor and get the info of that product
                Product p = currVendor.getProductList().get(vendorProductId);

                if (p == null ) throw new ProductNotFound("Product with such ID doesn't exist");

                if(!(p instanceof RentableTemp)){ throw new IllegalStateException("Product is not rentable"); }

                Instant quotedAt = Instant.now();
                double quotedPrice = ((RentableTemp)p).quoteRental(quotedAt); //basically Instant.now()

                //show user quoted price
                System.out.println("This rental costs $ " + quotedPrice + " (after discount).");

                //give 1 more choice for user if he wants to proceed with renting or not
                System.out.println("Proceed with renting? (type ONLY \"y\" or \"n\")"); //if not y or n, this will loop back to Vendor selection
                String answer = sc.nextLine().trim().toLowerCase();

                if(answer.equals("y")){

                    currCus.rentProduct(p, quotedAt);
                    System.out.println("\nYour remaining balance: " + currCus.getBalance() );
                    return;
                }
                else if(answer.equals("n")) return; //handle in the main case to show the customer menu again***

            }catch(NumberFormatException e){
                System.out.println("Either type valid number or \"exit\"");
            }catch (ProductNotFound e){
                System.out.println(e.getMessage());
            }catch (VendorNotFound e){
                System.out.println(e.getMessage());
            }catch(InputMismatchException e){
                sc.nextLine();
                System.out.println("Vendor ID and product ID must be integers");
            }catch (IllegalStateException e){
                System.out.println(e.getMessage());
            }catch (InvalidUserChoice e) {
                System.out.println(e.getMessage());
            }
        
        }
    }//end of quoteAndRent

    //Customer's case 5 -->Return a product
    public void helperCustomerCase5(Scanner sc, CustomerTemp currCus){

        //show customers what he rented //will display only 1 time
        currCus.viewMyRentals();

        if (currCus.getRentedProducts().size() == 0) {
            //System.out.println("you have no active rentals"); //don't need it since I print it in viewMyRentals()
            return;
        }

        while(true){

            try{
                //same logic asking user input for vendor Id and product id
                System.out.print("\n\nEnter Vendor ID (or 0 to go back): ");
                String userInput = sc.nextLine().trim();
                helperCatchUserTypedExit(sc, userInput);

                int vId = Integer.parseInt(userInput);

                if(vId == 0) return; //user type 0 -> return

                VendorTemp currVendor = null;
                for(VendorTemp vendor : vendorsList){
                    if(vendor.getVendorId() == vId){
                        currVendor = vendor;
                        break; //break early after we find the vendor
                    } 
                }

                if (currVendor == null) throw new VendorNotFound("Vendor with this ID does not exist");

                System.out.print("\n\nEnter the product ID you'd like to return (or 0 to go back): "); //customer will see the price of the product with discount if there is any
                String userInput1 = sc.nextLine().trim();
                helperCatchUserTypedExit(sc, userInput1);

                int vendorProductId = Integer.parseInt(userInput1);
                
                if(vendorProductId == 0) return; 
                
                //now I will check if that product with (product Id and vendor id) exists in the customer's rentedProduct list

                //pls remember that I use Map<String, Product> for rentedProducts inside Customer and use makeKey() to make key String type
                String key = CustomerTemp.makeKey(currVendor.getVendorId(), vendorProductId);

                //check if that key exists in customer's rentedProduct
                if(!currCus.getRentedProducts().containsKey(key)){
                    throw new IllegalStateException("You have never rented this product");
                }

                Product p = currCus.getRentedProducts().get(key);

                currCus.returnRental(p, Instant.now());
                return; //return after success 

            }catch(NumberFormatException e){
                System.out.println("Either type valid number or \"exit\"");
            }catch (IllegalStateException e){
                System.out.println(e.getMessage());
            }catch (VendorNotFound e){
                System.out.println(e.getMessage());
            }catch (InputMismatchException e){
                System.out.println("Vendor ID and product ID must be integers");
            }catch (InvalidUserChoice e) {
                System.out.println(e.getMessage());
            }
        }
    }//end of helperCustomerCase5()

    public void helperCustomerCase6(Scanner sc, CustomerTemp currCus){

        while(true){
            try{
                System.out.println("\n\nEnter the amount of money to add (or 0 to go back): ");
                String userInput = sc.nextLine().trim();
                helperCatchUserTypedExit(sc, userInput);

                double amount = Double.parseDouble(userInput);
                
                if(amount == 0) return; //user type 0 -> exit

                currCus.addFunds(amount);
                return;

            }catch(NumberFormatException e){
                System.out.println("Either type valid number or \"exit\"");
            }catch(InputMismatchException e){
                System.out.println("Amount must be > 0");
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }catch (InvalidUserChoice e) {
                System.out.println(e.getMessage());
            }

        }
    }//end of helperCustomrCase6 




    //getter for vendorsList
    public List<VendorTemp> getVendorsList(){ return vendorsList; }

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
        
        while(true){
            shop.handleRole(sc); 
        }
        
    }

    //4 overridden methods
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Shop{vendors=")
          .append(vendorsList.size())
          .append("}\n");
        for(VendorTemp vendor : vendorsList){
            sb.append(vendor.toString()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        ShopTemp shop = (ShopTemp) obj;
        return this.vendorsList.equals(shop.vendorsList); 
    }
    @Override
    public int hashCode(){
        return vendorsList.hashCode();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ShopTemp copy = (ShopTemp) super.clone();
        copy.vendorsList = new ArrayList<>(this.vendorsList); 
        return copy;
    }

}
