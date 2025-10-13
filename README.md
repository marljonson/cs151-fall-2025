# Project 1: Performative Pop Shop
CS 151 (Fall 2025) with Professor Telvin Zhong<br>
Contributors: Dee Aein, Marl Jonson, Miyuki Tokuhara, Myo Thant Zin<br>

## Overview
You wake up in a cold sweat. Not from a nightmare, but from the gnawing realization that your aesthetic isn't <i>giving</i> anymore. Your Miffy keychain isn't impressing anyone (tragic), your mom's Canon has dust on the lens (inauthentic), and your matcha supply ran out two days ago (criminal). It's time. You must embark on your sacred pilgrimage. You must go… to the <b>Performative Pop Shop</b>!

This is your temple. The walls are plastered with ironic posters of 2000s cartoons and vintage anime no one actually watched. The air smells like incense and expensive soy. You pick up a <b>Labubu</b> that's weird enough to match your curated detachment. You snag a disposable <b>Digicam</b> for posting blurry flash selfies on your close friends story. You leave the Performative Pop Shop with a tote bag full of aura. The world may never understand you, but at least your Instagram followers will.

## Design
<ul>
  <li>ShopTemp - main driver class with modes for main menu, customer, and management</li>
  <li>VendorTemp - vendor details and product management</li>
  <li>Product - base product class
    <ul>
      <li>DigicamTemp - subclass for camera products</li>
      <li>Labubu - subclass for Labubu products</li>
    </ul>
  </li>
  <li>CustomerTemp - customer profile, purchase history, membership and balance</li>
</ul>

## Installation Instructions
Option 1 - Running on Eclipse
<ol>
  <li>Clone the repo</li>
  Type this in your terminal - git clone https://github.com/<your-username>/pps-CS151-Fall-2025.git
  <li>Then open Eclipse and you will see the project folder(pps-CS151-Fall-2025) in your project explorer</li>
  <li>If you click on the folder, you will find the src and then src/main/java/project/models/ShopTemp.java</li>
  <li>Right click on Shop.java and run as Java application</li>
</ol>
<br>
Option 2 - Running on Visual Studio Code
<ol>
  <li>Clone the repo</li>
  Type this in your terminal - git clone https://github.com/<your-username>/pps-CS151-Fall-2025.git
  <li>Go to file and then open folder and select the project folder(pps-CS151-Fall-2025)</li>
  <li>Open the main file src/main/java/project/models/ShopTemp.java</li>
  <li>Click the run button or press Ctrl + F5 on Windows/Linux, or press Cmd + F5 on Mac</li>
</ol>
Option 3 - Running from the Command Line (no IDE)
<ol>
  <li>Open a terminal and navigate to the directory containing ShopTemp.java</li>
  <li>Compile the code: `javac ShopTemp.java`</li>
  <li>Run the program: `java ShopTemp`</li>
</ol>

## Usage
Run the program and follow the console prompts (type a number between 1–11) to browse products, read their descriptions, and more. Build your curated collection of performative goods and explore what each vendor has to offer.
Run the program and follow the console prompts (type a number between 1–11) to:
<ul>
  <li>Browse vendors and products</li>
  <li>Sign up or log in as a customer</li>
  <li>Manage purchases, memberships, and balances</li>
  <li>Enter management mode (if you are an authorized user) to add/remove products and update prices</li>
  <li>Exit safely to save all data</li>
</ul>

## Contributions
* Dee Aein - Shop, interface files
* Marl Jonson - @Override methods, UML diagram, README.md
* Miyuki Tokuhara - Interface files, @Override methods, UML diagram
* Myo Thant Zin - Vendor, CustomerTemp (especially early prototypes), now-deprecated Main
