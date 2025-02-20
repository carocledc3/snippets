import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.System;
public class carodc3_30CF_02L1 {

    public static String decimalToBinary(int decimalInput) {
        // thanks https://www.geeksforgeeks.org/java-program-for-decimal-to-binary-conversion/
        int remainder; // create remainder variable
        int quotient = decimalInput; // input is first quotient
        String binaryResult = ""; // empty string at first
        while (quotient > 0) {
            
            remainder = quotient % 2;
            binaryResult = Integer.toString(remainder) + binaryResult; // remainders add to the left of the string
            quotient /= 2; // quot/2 becomes new value of quotient, then loops until its zero
        }
        return binaryResult;
    }

    @SuppressWarnings("resource")
    public static void main(String[] args) {

        Scanner input; // define the Scanner object first

        while(true) { // infinite loop

            // ask for input
            System.out.print("Decimal number to convert: ");
            input = new Scanner(System.in);

            // get the input
            String proc = input.next(); // thanks https://coderanch.com/t/664113/java/break-loop-Scanner-input
            
            try { // try first if input can be converted to int...

                int procToNum = Integer.parseInt(proc); // if yes, then convert it
                System.out.println("  > Binary: " + decimalToBinary(procToNum));

            } catch (NumberFormatException ex) {
                String procString = proc; // if not, convert it to string; if it's "STOP"; then stp, else throw error
                if(procString.startsWith("STOP")) { break; } else {throw new InputMismatchException("Cannot really convert a string to a binary number, can you? (Well technically you can, but it's not in the scope of the lesson)");}
            }
            
        } 
    }
}