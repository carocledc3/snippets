import java.util.Scanner;

// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, BSIT-1L
// CC2/Introduction to Computer Programming
// [30IP-01L3] Laboratory Challenge №3
// 16 SEPTEMBER 2024


/*
>> GROCERY SHOPPING CALCULATOR <<

- PROBLEM STATEMENT: You are creating a program to help customers calculate their total bill when shopping for groceries. The customer buys several items, each with a specific quantity and price. The program should calculate the total cost of the groceries, apply a discount, and then add a sales tax to determine the final amount to be paid.

- STEPS TO SOLVE:
    1. INPUT: The program should ask the user to input the price and quantity of three different items.
    2. CALCULATIONS:
        - Calculate the total cost for each item by multiplying the price by the quantity.
        - Add the costs of all items together to get the subtotal.
        - Apply a 5% discount to the subtotal.
        - Add a 12% sales tax to the discounted amount to get the final total.
    3. OUTPUT: Display the subtotal, discounted amount, tax, and final total the customer has to pay.
*/

public class carodc3_ITCP_01L3 {

    // i want a custom method just for printing a horizontal line in the terminal
    public static String horizontalLine = "-----------------------------------------------";
    public static void phr() {System.out.println(horizontalLine);}

    // so VS Code doesnt complain about not closing the Scanner resource
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        
        // Price of item 1
        System.out.print("Enter the price of Item 1:\t");
        Scanner scanOnePrice = new Scanner(System.in); // ask for input
        double Price1 = scanOnePrice.nextDouble(); // capture input

        // Quantity of item 1
        System.out.print("Enter the quantity of Item 1:\t");
        Scanner scanOneQuantity = new Scanner(System.in); // ask for input
        double Quan1 = scanOneQuantity.nextDouble(); // capture input

        // Price of item 2
        System.out.print("Enter the price of Item 2:\t");
        Scanner scanTwoPrice = new Scanner(System.in); // ask for input
        double Price2 = scanTwoPrice.nextDouble(); // capture input

        // Quantity of item 2
        System.out.print("Enter the quantity of Item 2:\t");
        Scanner scanTwoQuantity = new Scanner(System.in); // ask for input
        double Quan2 = scanTwoQuantity.nextDouble(); // capture input

        // Price of item 3
        System.out.print("Enter the price of Item 3:\t");
        Scanner scanThreePrice = new Scanner(System.in); // ask for input
        double Price3 = scanThreePrice.nextDouble(); // capture input

        // Quantity of item 3
        System.out.print("Enter the quantity of Item 3:\t");
        Scanner scanThreeQuantity = new Scanner(System.in); // ask for input
        double Quan3 = scanThreeQuantity.nextDouble(); // capture input

        phr(); // separator

        double subtotal = (Price1 * Quan1) + (Price2 * Quan2) + (Price3 * Quan3); // calculate subtotal as price*quan of each item
        double discount = subtotal * 0.05; // calculate discount
        double discountedPrice = subtotal - discount; // subtract it
        double salesTax = discountedPrice * 0.12; // calculate sales tax
        double finalTotal = discountedPrice + salesTax; // calculate final total

        // printf() with "%.2f" formats each item with 2 decimal places
        System.out.printf("Subtotal: \t₱%.2f" , subtotal);
        System.out.printf("\nDiscount: \t₱%.2f", discount);
        System.out.printf("\nSales Tax: \t₱%.2f", salesTax);
        System.out.printf("\nFinal Total: \t₱%.2f\n",  finalTotal);
    }
}
