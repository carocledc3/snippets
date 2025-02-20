// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, BSIT-1L
// CC2/Introduction to Computer Programming
// [30IP-02L4] Laboratory Challenge №4
// 26 SEPTEMBER 2024

/*
>> GROCERY STORE DISCOUNT CALCULATOR (USING IF-ELSE) <<

- PROBLEM STATEMENT: A grocery store offers discounts based on the total purchase amount. Write a program that calculates the final price after applying the appropriate discount based on the following rules:
    - If the total purchase amount is less than 1,000 PHP, there is no discount.
    - If the total purchase amount is between 1,000 PHP and 5,000 PHP, apply a 5% discount.
    - If the total purchase amount is between 5,001 PHP and 10,000 PHP, apply a 10% discount.
    - If the total purchase amount is more than 10,000 PHP, apply a 15% discount.
    The program should ask the user for the total purchase amount, calculate the final price after applying the discount, and display the discount and the final price.
    
- STEPS:
    - Use the `Scanner` class to input the total purchase amount.
    - Use `if-else` statements to apply the correct discount based on the rules.
    - Calculate and display the discount and the final price.
 */


import java.util.Scanner;

public class carodc3_ITCP_02L4 {

    static double[] applyDiscount(double amount) {

        // apologies po; this is a weird way to go around it; i made the discounting method return an array because i wanted to have the discount and the discounted amount accesable from the same (preferrably static) object so i don't have to manually instantiate things
    
        double discount = 0; // declare discount variable with default value of 0

        if (amount < 1000) {
            // if inputted amount is less than 0, do nothing
        } else if (amount >= 1000 && amount <= 5000) {
            discount = 0.05; // set discount to 5%
            amount *= (1 - discount); // subtract discount from amount
        } else if (amount > 5000 && amount <= 10000) {
            discount = 0.10; // set discount to 10%
            amount *= (1 - discount); // subtract discount from amount
        } else {
            discount = 0.15; // set discount to 15%
            amount *= (1 - discount); // subtract discount from amount
        }

        double[] results = {amount, discount * 100};
            // first element of the array == results[0] == final amount
            // second == results[1] == the discount (times 100 because it will be printed with the percent sign)
        return results; // return this array
    }

    @SuppressWarnings("resource") // so that vs code doesnt complain about not closing the scanner object (how does one close a scanner object?)
    public static void main(String[] args) {
        System.out.print("Enter total purchase amount: ₱"); // prompt user input
        Scanner amamam = new Scanner(System.in); // user inputs here, store in double in next line
        double banana = amamam.nextDouble(); // this throws an error if input is not a double!
        System.out.printf("Discount applied: %.0f%%\n", applyDiscount(banana)[1]); // print discount
        System.out.printf("Price after discount: ₱%,.2f\n", applyDiscount(banana)[0]); // print discounted price
        // number formatting reference https://docs.oracle.com/javase/tutorial/java/data/numberformat.html
    }
}