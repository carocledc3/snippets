// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, CITCS-1L
// CC2/Introduction to Computer Programming
// [30IP-05L1] Final Laboratory Challenge №1
// 1 NOVEMBER 2024

/*
>> RETAIL STORE INVENTORY MANAGEMENT <<

OBJECTIVE: Manage and calculate stock levels in a retail store.

SCENARIO: You are managing the stock of 10 products in a retail store. You want to track how much of each product is left in stock and calculate the total value of the stock based on the product quantities and prices.

TASK:
    - Use a one-dimensional array to store the stock levels of 10 different products.
    - Use another one-dimensional array to store the prices of those products.
    - Write a program that calculates the total value of each item and all items in stock.

HINT: Total value for each product = quantity * price.
*/


import java.util.Scanner;
public class carodc3_ITCP_05L1 {
    // whenever i need lines; for æsthetic/readability purposes only
    static void line() { System.out.println("-------------------------------------------------------------------------"); } 
    static void doubleLine() { System.out.println("========================================================================="); }
    public static void main(String[] args) {

        final int numberOfProducts = 10; // number of products that the program will account for. adjust this and the whole program adjusts automatically
        Scanner input; // one scanner object

        // arrays for the product's information with the size equal to numberOfProducts
        String[] productNames = new String[numberOfProducts];
        int[] productQuantities = new int[numberOfProducts];
        double[] productPrices = new double[numberOfProducts];

        System.out.println("INVENTORY MANAGER");
        doubleLine();

        int curp = 1; // stores what product the loop is on; needs to be on a separate counter that will only be incremented once all information about the product has been filled out
        for (int i = 1; i <= numberOfProducts * 3; i++) {
            // weird program flow ensures that i-- will only make the user re-enter the last incorrect input
            // i.e. if the price per unit is wrong then the user wouldn't have to go back and reenter the name all over again
            // this loop just basically runs 3 separate steps (asking for product name,
            // quantity, price) 10 times (or whatever the value of numberOfProducts is)

            switch (i % 3) {
            // the decision on which step will run is based on the iteration number % 3
            // so the loop will go -> 1. name, quantity, price, 2. name, quantity, price, 3. name, quantity, price..
                case 1 -> {
                    // LOOP STARTS HERE because initial value of i % 3 == 1 % 3 == 1.
                    // this is the case for the 1st, 4th, etc. iterations of the loop
                    System.out.print("Enter the name of product #" + curp + ": "); // prompt input for product name (not in the instructions but i think it would be practical to have the names listed as well; for readability purposes)
                    input = new Scanner(System.in);
                    productNames[curp - 1] = input.next(); // why curp-1? because product counting starts at 1 but array indexing starts at 0
                }
                
                case 2 -> {
                    // case for the 2nd, 5th, etc
                    System.out.print("How much of \"" + productNames[curp - 1] + "\" are in stock?: "); // prompt input for product quantity
                    input = new Scanner(System.in);
                    if (!input.hasNextInt()) { // thanks https://www.w3schools.com/Java/ref_scanner_hasnextint.asp
                        // check if input is not an int...
                        System.out.println("Invalid input! Input must be an integer.");
                        i -= 1; // prompt reentry if so
                    } else {
                        int testQuantity = input.nextInt(); // capture int input to testable quantity
                        if(testQuantity <= 0) { // check if input is lesser than 0..
                            System.out.println("Invalid input! Input must be greater than 0.");
                            i -= 1; // prompt reentry if so
                        }
                        productQuantities[curp - 1] = testQuantity; // if it's passed all that, log the test quantity to the array of product quantities
                    }
                }
                
                case 0 -> {
                    System.out.print("How much is each unit of \"" + productNames[curp - 1] + "\"?: ₱"); // prompt input for product price per unit
                    input = new Scanner(System.in);
                    if (!input.hasNextDouble()) { // if input is not a double..
                        System.out.println("Invalid input! Input must be a number.");
                        i -= 1; // display error and prompt reentry
                    } else {
                        double testPrice = input.nextDouble(); // else, put input into testable quantity
                        if(testPrice <= 0) { // if input is less than 0..
                            System.out.println("Invalid input! Input must be greater than 0.");
                            i -= 1; // display error and prompt reentry 
                        }
                        productPrices[curp - 1] = testPrice; // else, log test quantity to array of product prices
                        curp += 1; // if it's reached this point, then that means that the name, quantity, and price of the product have all been logged. therefore, move onto the next product
                        if (i != numberOfProducts * 3) {line();} // if not final iteration, print single line
                    }
                    
                    if(i == numberOfProducts * 3) { 
                        // if it's the final iteration, print a double line and close the Scanner for good
                        doubleLine();
                        input.close();
                    }
                }
            }
        }
        String invHeaderPrintFormat = "%-20s%-10s%-15s%-20s%n";
        String invPrintFormat = "%-20s%-10s₱%-15.2f₱%-20.2f%n"; // formatting for inventory printing, thanks https://stackoverflow.com/questions/6000810/printing-with-t-tabs-does-not-result-in-aligned-columns
        double totalStockValue = 0; // to calculate total stock value..
        for (int i = 0; i < numberOfProducts; i++) {
            // take the product of the quantity and price per unit for each product then add them together
            /*
            numberofProducts
                Σ           productQuantities[i] * productPrices[i]
              i = 0
            */
            totalStockValue += productQuantities[i] * productPrices[i];
        }

        System.out.println("PRODUCT INVENTORY:"); // printing of inventory levels
        
        System.out.printf(
            invHeaderPrintFormat,
            "Product",
            "Quantity",
            "Price Per Unit",
            "Total Stock Price"
        ); // print formatted header

        line(); // divider line

        for(int i = 1; i <= numberOfProducts; i ++) { // print formatted information of each product..
            int j = i - 1;
            System.out.printf(
                invPrintFormat,
                i + ". " + productNames[j],
                productQuantities[j],
                productPrices[j],
                productQuantities[j] * productPrices[j]
            );
        }
        line();
        System.out.println("Total stock value: " + String.format("₱%.2f",totalStockValue)); // print total stock value
    }
}