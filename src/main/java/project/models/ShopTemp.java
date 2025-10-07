package project.models;

import java.time.Instant;
import project.abstractclasses.Product;
import project.interfaces.RentableTemp;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class ShopTemp {

    private static List<VendorTemp> vendorsList = new ArrayList<>();

    public ShopTemp(){
        createVendors();
    }

    //preset datas for Vendors and their products
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

        //max 100 instances for Digicam class
        for(int i = 0; i < 100; i++){

            double price = 50 + rand.nextDouble() * 150; //random price b/w 50-200 range
            String model = possibleModels[rand.nextInt(possibleModels.length)]; //random model
            digiCamVendor.stockDigicam(price, model);
        }

        //add the vendors to the Shop's vendorsList
        vendorsList.add(labubuVendor);
        vendorsList.add(digiCamVendor);
    }







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

        /* This works! 
         * try printing out all the products of each vendor
         
        for(VendorTemp v : ShopTemp.vendorsList){
            v.debug();
        }
        */
       


    }

}
