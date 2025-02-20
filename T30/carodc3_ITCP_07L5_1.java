// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, CITCS-1L
// 30IP: CC2/Introduction to Computer Programming
// [30IP-07L5-1] Final Laboratory Challenge №5.1: Funds Withdrawal Application 
// 4 DECEMBER 2024

/*
>> EXCEPTION HANDLING LABORATORY CHALLENGES <<

OBJECTIVE: In this assignment, you will apply your knowledge of exception handling by solving two real-life problems. You will select any two of the three provided problems and create solutions that demonstrate proper exception handling. Each solution should include custom exceptions where applicable, along with clear, user-friendly output.

PROBLEM OPTIONS:
    1. [THIS FILE] BANKING SYSTEM - INSUFFICIENT FUNDS EXCEPTION: Implement a program that allows users to withdraw from their bank account. If the withdrawal amount exceeds the current balance, the program should throw a custom InsufficientFundsException and display a message informing the user.

INSTRUCTIONS:
    - SELECT AND IMPLEMENT: Choose two of the three problems listed above. Implement a solution for each selected problem, creating a separate program for each.
    - DEFINE CUSTOM EXCEPTIONS: For each problem, define a custom exception class (e.g., InsufficientFundsException, InvalidQuantityException, or NoSeatsAvailableException) to handle the specific error condition described in the problem.
    - IMPLEMENT EXCEPTION HANDLING: Use try-catch blocks in each program to handle the custom exceptions and display informative messages to the user when errors occur.
    - USER-FRIENDLY OUTPUT: Ensure that your program provides clear and informative output, guiding the user through the input process and explaining any errors that are caught.

 */


import java.util.Scanner;
public class carodc3_ITCP_07L5_1 {

    // exception that is thrown when you're too broke to withdraw the amount you're trying to get
    static class InsufficientFundsException extends Exception {
        public InsufficientFundsException() {
            System.out.println("[!] You are trying to withdraw more money than you have.");
        }
    }

    // method to parse string input into a double. if input is not a floating point number, output -1.0. if it is, return said floating point number
    static double amountParser(String input) {
        try {
            double res = Double.parseDouble(input); // parse the input...
            return res; // return if success
        } catch (NumberFormatException e) { // if not a number, then don't exit; instead return -1.0
            return -1.0;
        }
    }

    public final static void clearConsole() { // method to clear console, thank you https://stackoverflow.com/questions/2979383/how-to-clear-the-console-using-java
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                System.out.println("\u001b[2J" + "\u001b[H");
            }
        } catch(Exception E){
            // je m'en câlisse
        }
    }

    static void line() {
        System.out.println("-------------------------------------------------------------------------");
    }
    // lines for æsthetic purposes
    static void doubleLine() {
        System.out.println("=========================================================================");
    }

    public static void main(String[] args) throws InsufficientFundsException, InterruptedException {
        double accountBalance = 0.0;
        Scanner inp = new Scanner(System.in);
        // main app loop...
        do {
            clearConsole();
            // clear console to stay clean
            doubleLine();

            // bank name
            System.out.println("[ BANK OF THE PHILIPPINE ISLANDS ]\n[ Command-Line Account Manager ]");
            doubleLine();
            // account holder info
            System.out.println("Account Holder: >> EFRAIM JEDEDIA PANGAN <<");
            System.out.printf("Current Account Balance: ₱%,3.2f%n", accountBalance);
            line();
            // actions menu
            System.out.println("ACTIONS:");
            System.out.println("> [1] Deposit Amount...");
            System.out.println("> [2] Withdraw Amount...");
            System.out.println("> [X] Log Out");
            doubleLine();
            // prompt action input
            System.out.print("Input action: ");
            String choice = inp.nextLine();

            if("1".equals(choice)) {
                // for depositing amounts...
                
                for ( int i = 0; i < 1; i++) { // one-step loop means i can easily reprompt user input by going to the previous loop iteration
                    System.out.print(">> Enter amount to deposit: ₱");
                    double depAmount = amountParser(inp.nextLine()); // parse input. this will return -1 if the input is not a number
                    if(depAmount == -1.0 || depAmount < 0) {
                        // if not a number, display message and reprompt
                        System.out.println("[!] Input should be a positive number!");
                        i--;
                    } else {
                        // else add the amount to the balance
                        accountBalance += depAmount;
                        System.out.println("[✔] Deposit success!");
                        Thread.sleep(400); // display success message and exit to menu
                    }
                }

            } else if("2".equals(choice)) {
                // code for withdrawing ammounts
                try {
                    for ( int i = 0; i < 1; i++) { // one-step loop means i can easily reprompt user input by going to the previous loop iteration
                        System.out.print(">> Enter amount to withdraw: ₱");
                        double widAmount = amountParser(inp.nextLine()); // parse input. this will return -1 if the input is not a number
                        if(widAmount == -1.0 || widAmount < 0) {
                            // if not a number, display message and reprompt
                            System.out.println("[!] Input should be a positive number!");
                            i--;
                        } else if (widAmount > accountBalance) {
                            throw new InsufficientFundsException(); // if the amount exceeds the balance, 
                        }else {
                            // else subtract the amount from the balance
                            accountBalance -= widAmount;
                        System.out.println("[✔] Withdraw success!");
                            Thread.sleep(400); // display success message and exit to menu
                        }
                    }
                } catch(InsufficientFundsException e) {
                    Thread.sleep(800); // allow the exception's message to show
                    continue; //exit to menu
                }


            } else if("X".equals(choice)) {
                clearConsole(); // if inputted X, display message and exit program
                line();
                System.out.println("Logging out...\nThank you for choosing BPI.");
                line();
                Thread.sleep(750);
                
                break;
            }
        } while(true);
        inp.close();
    }
}