// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, BSIT-1L
// CC2/Introduction to Computer Programming
// [30IP-02L5] Laboratory Challenge №4
// 27 SEPTEMBER 2024

/*
>> RESTAURANT ORDER SYSTEM WITH QUANTITY INPUT <<

- PROBLEM STATEMENT: Create a restaurant order system that allows customers to order a single item from a menu. The program should:
    1. Display a menu with items and prices.
    2. Prompt the user to select an item by entering the corresponding menu number.
    3. Ask the user to input the quantity for the selected item.
    4. Calculate the total amount for the item.
    5. Display the total bill at the end of the order.

- INSTRUCTIONS:
    1. DISPLAY THE MENU: The program should display the following menu: 
        MENU
        1. Burger    - PHP 100
        2. Fries     - PHP  50
        3. Soda      - PHP  30
        4. Ice Cream - PHP  45 
        5. Exit
    2. GET USER INPUT FOR MENU SELECTION:
        - Prompt the user to enter the menu item number they wish to order.
        - Use a `switch` statement to process the user’s selection.
    3. GET USER INPUT FOR QUANTITY:
        - After selecting an item, prompt the user to enter the quantity of the selected item.
        - Calculate the total amount for that item by multiplying the item’s price by the quantity.
    4. DISPLAY THE TOTAL BILL:
        - Once the user has selected an item and provided the quantity, calculate the total cost and display it to the user.

- STEPS TO COMPLETE THE CHALLENGE:
    1. CREATE A SCANNER OBJECT: Use the `Scanner` class to take user input for the menu selection and quantity.
    2. MENU DISPLAY: Display the menu using `System.out.println()` to print each item and its price.
    3. USE A SWITCH STATEMENT: Implement a `switch` statement to handle different menu selections and calculate the total cost based on the quantity entered by the user.
    4. TRACK THE TOTAL BILL: 
        - Use a variable to store the total amount of the selected item.
        - Update the total amount based on the item’s price and the quantity ordered.
    5. OUTPUT THE FINAL TOTAL:
 */

import java.util.Scanner;

public class carodc3_ITCP_02L5 {

        // method to get price of item. returns double because prices are usually formatted as such
    static double ItemPrice(int item) {
        double price; // declare price variable
        price = switch (item) {
            case 1 -> 100;
            case 2 -> 50;
            case 3 -> 30;
            case 4 -> 45;
            default -> 0;
        }; // switch case that changes the value of `price` depending on what value `item` is
        // default price is 0; but ideally this case should never be reached bc invalid integer inputs are handled in lines 52 and 53 anyway
        return price; // return the changed variable
    }

    // method to get item name. the item names are stored in here and not hardcoded in the main method either; the listing process in the main method calls both this and ItemPrice()
    static String ItemName(int item) {
        String name; // declare name variable
        name = switch (item) {
            case 1 -> "Burger";
            case 2 -> "Fries";
            case 3 -> "Soda";
            case 4 -> "Ice Cream";
            default -> "Nothing";
        }; // switch case that changes the value of `name` depending on what value `item` is
        return name; // return the changed variable
    }

    @SuppressWarnings("resource") // so vs code doesn't complain about closing the string object
    public static void main(String[] args) {
        Scanner itemIn; Scanner itemQuan; // declare scanner variables; it's a helpful tool that we can use later

        System.out.println("-<[ MENU ]>-------------------------------------------"); // this println is for æsthetic purposes only

        for(int i = 1; i <= 4; i++) {
            // display all available item names..
            System.out.print(i + ". " + ItemName(i) + " \t : ₱");
            System.out.printf("%4.2f%n", ItemPrice(i)); // ..and their prices, formatted (thanks https://docs.oracle.com/javase/tutorial/java/data/numberformat.html)
        }
        System.out.println(5 + ". [Exit] \t"); // display exit option

        System.out.print("Enter the menu number of your choice: "); // prompt user input for which item they want
        itemIn = new Scanner(System.in); 
        int itemSelection = itemIn.nextInt(); // capture the input to an int; this throws an InputMismatchException if the input isn't an int

        if (itemSelection == 5) { System.exit(0); } // if item 5 is selected, exit; thanks https://www.baeldung.com/java-system-exit
        else if (itemSelection > 5 || itemSelection < 1) { System.out.println("Invalid input! Exiting..."); System.exit(0); } // if item number is greater than 5 or less than 1, print warning, then exit too

        // if it gets to this point, then it means the input is an integer AND within 1-4 inclusive; meaning they have actually selected a valid item. we can now prompt for the quantity:
        System.out.print("Enter the quantity: ");
        itemQuan = new Scanner(System.in); // user inputs here
        int itemQuantity = itemQuan.nextInt(); // capture quantity input to an int

        System.out.println("-<[ ORDER ]>-------------------------------------------"); // this println is for æsthetic purposes only; to keep neat

        System.out.println("You ordered: " + ItemName(itemSelection) + ", ×" + itemQuantity); // display order and quantity; call ItemName() to get the item's name
        System.out.printf("Total amount: ₱%,.2f%n", ItemPrice(itemSelection) * itemQuantity ); // calculate total amount as the result of ItemPrice() multiplied by the inputted quantity, then format it
        
        System.out.println("- Thank you for dining at Montabarnac's Eatery! -------"); // closing remark

    }

}