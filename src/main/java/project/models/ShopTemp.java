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
import project.exceptions.ProductNotFound;
import project.exceptions.VendorNotFound;
import project.abstractclasses.Product;
import project.interfaces.RentableTemp;

public class ShopTemp {

    private List<VendorTemp> vendorsList = new ArrayList<>();

    public ShopTemp(){
        createVendors();
    }

    //preset datas for Vendors and their products (CONFIRMED AND TESTED)
    private void createVendors(){

        //create Vendors
        VendorTemp labubuVendor = new VendorTemp("Labubu Rental Shop", "rentlabubu11@gmail.com");
        VendorTemp digiCamVendor = new VendorTemp("Digicam Rental Shop", "rentdigitalcamera22@gmail.com"); 
        
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
        System.out.println("[2] Browse vendor inventory by ventorId");
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
            System.out.print(" \n\nEnter your email address: ");
            String userEmail = sc.nextLine().trim().toLowerCase();

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
            }
        }
    }//end of handleVendorChoice

    //if the user is a customer
    public void handleCustomerChoice(Scanner sc){//NOTE: this is helper method for handleRole()

        CustomerTemp currCus = new CustomerTemp("guest", "01", "firstCustomer@default.com", 200);

        if(sc == null) throw new NullPointerException("Scanner cannot be null");

        while(true){
            int customerChoice = getCustomerChoice(sc);

            switch (customerChoice){
                case 0 -> helperExit(sc);
                case 1 -> listAllVendors();
                case 2 -> helperCustomerCase2(sc);
                case 3 -> quoteAndRent(sc, currCus);
                case 4 -> currCus.viewMyRentals();
                case 5 -> helperCustomerCase5(sc, currCus);
                case 6 -> helperCustomerCase6(sc, currCus);
                case 7 -> {
                    System.out.println("===== My Wallet =====");
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
        System.out.println("=============================\n\n");
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
                int vendorProductId = sc.nextInt();
                sc.nextLine();
                //System.out.println();

                if(vendorProductId == 0) return; //user type 0 -> return //OR should I throw a custom exception here?

                System.out.print("\n\nEnter the updated price (or 0 to go back): ");
                double price = sc.nextDouble();
                sc.nextLine();
                //System.out.println();
                if(price == 0) return;//user type 0 -> return

                vendor.updatePriceById(vendorProductId, price);
                return; //price is successfully updated -> return 
                
            }catch (ProductNotFound e){ 
                System.out.println(e.getMessage());
            }
            catch (InputMismatchException e){
                sc.nextLine(); //must have this since nextInt() and nextDouble() leave a line behind
                System.out.println("Enter an integer for product ID and a double for updated price");
            }
        }
    }//end of vendor's case 1

    //Vendor's case 3 --> Discontinue a product by id
    public void helperVendorCase3(Scanner sc, VendorTemp vendor){
        
        while(true){
            try{
                System.out.print("\n\nEnter the product's ID (or 0 to go back): ");
                int vendorProductId = sc.nextInt();
                sc.nextLine();
                //System.out.println();

                if(vendorProductId == 0) return; //user type 0 -> return

                vendor.discontinueProductById(vendorProductId);
                return; //successfully discontinued -> return
            }catch (ProductNotFound e){ 
                System.out.println(e.getMessage());
            }
            catch (InputMismatchException e){
                sc.nextLine(); //must have this since nextInt() and nextDouble() leave a line behind
                System.out.println("Enter an integer for product ID");
            }
        }
    }//end of vendor's case 3

    //Vendor's case 4 -> set promo (give discount for every product for a period)
    public void helperVendorCase4(Scanner sc, VendorTemp vendor){

        while(true){
            try{
                System.out.print("\n\nEnter discount in fraction between 0.0 and 1.0 (or 0 to go back): ");
                double discountFraction = sc.nextDouble();
                sc.nextLine();

                if(discountFraction == 0) return; //user type 0 -> return

                //in reality, we should ask the vendor to input startTime and endTime
                //but I have yet to learn how to convert user's input to Instant object
                Instant start = Instant.now().minusSeconds(60);     // starts 60 seconds ago
                Instant end = Instant.now().plusSeconds(7 * 24 * 3600); // 7 days later

                vendor.setPromo(discountFraction, start, end);
                System.out.println("A promo window is created. Every product from your inventory is " + discountFraction * 100 + "% off!");
                return;
            }catch(InputMismatchException e){
                  sc.nextLine(); //must have this since nextInt() and nextDouble() leave a line behind
                System.out.println("discount fraction must be a number between 0.0 and 1.0");
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }//end of vendor's case 4

    //Vendor's case 6 --> add funds
    public void helperVendorCase6(Scanner sc, VendorTemp vendor){

        while(true){
            try{
                System.out.print("Enter the amount of credit (or 0 to go back): ");
                double amount = sc.nextDouble();
                sc.nextLine();

                if(amount == 0) return; //user type 0, exit
                
                vendor.credit(amount);
                return;
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }catch (InputMismatchException e){
                sc.nextLine();
                System.out.println("Invalid input: please enter a number.");
            }
        }
    }//end of vendor's case 6

    //Vendor's case 7 --> Withdraw Cash
    public void helperVendorCase7(Scanner sc, VendorTemp vendor){

        while(true){
            try{
                System.out.println("Enter the amount to withdraw (or 0 to go back): ");
                double amount = sc.nextDouble();
                sc.nextLine();

                if(amount == 0) return; //user type 0 --> return 

                vendor.debit(amount);
                return;
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }catch(IllegalStateException e) {
                System.out.println(e.getMessage());
            }catch (InputMismatchException e){
                sc.nextLine();
                System.out.println("Invalid input: please enter a number.");
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
                System.out.print("Enter Vendor ID (or 0 to go back): ");
                int vId = sc.nextInt();
                sc.nextLine();

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
            }catch(InputMismatchException e){
                sc.nextLine();
                System.out.println("Vendor ID must be an integer");
            }catch(VendorNotFound e){
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
                System.out.print("Enter Vendor ID (or 0 to go back): ");
                int vId = sc.nextInt();
                sc.nextLine();

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

                System.out.print("Enter the product ID you'd like to quote (or 0 to go back): "); //customer will see the price of the product with discount if there is any
                int vendorProductId = sc.nextInt();
                sc.nextLine();
                
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
                    System.out.println("Your remaining balance: " + currCus.getBalance() );
                    return;
                }
                else if(answer.equals("n")) return; //handle in the main case to show the customer menu again***

            }catch (ProductNotFound e){
                System.out.println(e.getMessage());
            }catch (VendorNotFound e){
                System.out.println(e.getMessage());
            }catch(InputMismatchException e){
                sc.nextLine();
                System.out.println("Vendor ID and product ID must be integers");
            }catch (IllegalStateException e){
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
                 System.out.print("Enter Vendor ID (or 0 to go back): ");
                int vId = sc.nextInt();
                sc.nextLine();

                if(vId == 0) return; //user type 0 -> return

                VendorTemp currVendor = null;
                for(VendorTemp vendor : vendorsList){
                    if(vendor.getVendorId() == vId){
                        currVendor = vendor;
                        break; //break early after we find the vendor
                    } 
                }

                if (currVendor == null) throw new VendorNotFound("Vendor with this ID does not exist");

                System.out.print("Enter the product ID you'd like to return (or 0 to go back): "); //customer will see the price of the product with discount if there is any
                int vendorProductId = sc.nextInt();
                sc.nextLine();
                
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

            }catch (IllegalStateException e){
                System.out.println(e.getMessage());
            }catch (VendorNotFound e){
                System.out.println(e.getMessage());
            }catch (InputMismatchException e){
                sc.nextLine();
                System.out.println("Vendor ID and product ID must be integers");
            }
        }
    }//end of helperCustomerCase5()

    public void helperCustomerCase6(Scanner sc, CustomerTemp currCus){

        while(true){
            try{
                System.out.println("Enter the amount of money to add (or 0 to go back): ");
                double amount = sc.nextDouble();
                sc.nextLine();

                if(amount == 0) return; //user type 0 -> exit

                currCus.addFunds(amount);
                return;
            }catch(InputMismatchException e){
                sc.nextLine();
                System.out.println("Amount must be > 0");
            }catch (IllegalArgumentException e){
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
