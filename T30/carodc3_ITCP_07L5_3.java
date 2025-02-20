// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, CITCS-1L
// 30IP: CC2/Introduction to Computer Programming
// [30IP-07L5-2] Final Laboratory Challenge №5.2: Flight Booking Application
// 4 DECEMBER 2024

/*
>> EXCEPTION HANDLING LABORATORY CHALLENGES <<

OBJECTIVE: In this assignment, you will apply your knowledge of exception handling by solving two real-life problems. You will select any two of the three provided problems and create solutions that demonstrate proper exception handling. Each solution should include custom exceptions where applicable, along with clear, user-friendly output.

PROBLEM OPTIONS:
    3. [THIS FILE] FLIGHT BOOKING SYSTEM - NO SEATS AVAILABLE EXCEPTION: Design a flight booking system that lets users attempt to book a seat. If the flight is fully booked, throw a custom NoSeatsAvailableException and inform the user that no seats are available.

INSTRUCTIONS:
    - SELECT AND IMPLEMENT: Choose two of the three problems listed above. Implement a solution for each selected problem, creating a separate program for each.
    - DEFINE CUSTOM EXCEPTIONS: For each problem, define a custom exception class (e.g., InsufficientFundsException, InvalidQuantityException, or NoSeatsAvailableException) to handle the specific error condition described in the problem.
    - IMPLEMENT EXCEPTION HANDLING: Use try-catch blocks in each program to handle the custom exceptions and display informative messages to the user when errors occur.
    - USER-FRIENDLY OUTPUT: Ensure that your program provides clear and informative output, guiding the user through the input process and explaining any errors that are caught.

 */

import java.util.Scanner;
public class carodc3_ITCP_07L5_3 {

    // plane: i'm so full of passengers yummmmm
    static class FullPlaneException extends Exception {
        public FullPlaneException() {
            System.out.println("[!] The plane is full! No more seats are available.");
        }
    }

    // if you want to catch Fish
    // put this here specifically because of that joke about catching Fish in java
    static class Fish extends Exception {
        public Fish() {
            System.out.println("Successfully caught Fish!");
            System.out.println("|>( *) |>( *) |>( *) |>( *) |>( *) |>( *)");
            System.out.println("glub glub glub... flplfplpflfplfplfplfplflpf");
        }
    }

    // method to parse index inputs. if it fails, returns -1, but if not, returns the int input
    static int indexInputParser(String input) {
        try {
            int k = Integer.parseInt(input);
            return k;
        } catch (NumberFormatException e) {
            return -1;
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

    // admin password required to change the amount of available seats
    static final String adminPassword = "youcantakemehottogo";

    public static void main(String[] args) throws InterruptedException, Fish, FullPlaneException {
        int numSeats = 250; // number of total seats
        int bookedSeats = 0; // number of occupied seats
        int selSeat = -1; // index of the user's booked seats; is -1 when there's no booking
        Scanner inp = new Scanner(System.in);

        // main app loop...
        do { 
            clearConsole();
            // airline name
            System.out.println("[ PHILIPPINE AIRLINES ]\n[ FLIGHT 567: MANILA <-> GOTHAM ]");
            System.out.println("[ Command-Line Boarding Manager ]");
            // display available seats
            System.out.printf("Available Seats: %3d / %d%n", numSeats-bookedSeats, numSeats);
            if(selSeat != -1) { // if the user has booked a seat, display it
                System.out.printf(">> User Seat Number: #%03d <<%n", selSeat);
            }
            doubleLine();
            // actions menu
            System.out.println("ACTIONS:");
            System.out.println("> [1] ADMIN: Set Amount of Booked Seats...");
            System.out.println("> [2] Book Seat...");
            System.out.println("> [X] Exit");
            doubleLine();
            // prompt action input
            System.out.print("Input action: ");
            String choice = inp.nextLine();

            if("1".equals(choice)) {
                // code to manually set number of booked seats
                // require admin password...
                System.out.print("Enter admin password: ");
                    String inpass = inp.nextLine();
                    if(!inpass.equals(adminPassword)) {
                        // if incorrect password, display message and exit to menu
                        System.out.println("[!] Incorrect admin password!");
                        Thread.sleep(800);
                        continue;
                    }
                    // if it's reached this point, it means login was successful
                clearConsole();
                doubleLine(); System.out.println("ADMIN MODE"); line();
                
                for ( int i = 0; i < 1; i++) { // onestep loop means i can easily reprompt user input
                    
                    System.out.print("Input new number of booked seats: ");
                    int newBookedSeatAmount = indexInputParser(inp.nextLine()); // parse input. this will return -1 if the input is not a number
                    if(newBookedSeatAmount == -1 || newBookedSeatAmount < 0) {
                        // if not a number, display message and reprompt
                        System.out.println("[!] Input should be a positive number!");
                        i--;
                    } else if (newBookedSeatAmount > numSeats) {
                        // if input exceeds seat count, display message and reprompt
                        System.out.println("[!] Input exceeds the number of total seats on the plane!");
                        i--;
                    }  else {
                        // else re-set the num of booked seats to the input
                        bookedSeats = newBookedSeatAmount;
                        System.out.println("[✔] Setting success!");
                        Thread.sleep(400); // display success message and exit to menu
                    }
                }

            } else if("2".equals(choice)) {
                // code for trying to book a flight
                // selects seat automatically based on number of available seats

                if(selSeat != -1) { // if the user has already booked a seat, display this message then exit to menu
                    System.out.println("[!] You already have a seat!");
                    Thread.sleep(2000);
                    continue;
                }

                // display progress...
                System.out.print("[*] Looking for next available seat.");
                Thread.sleep(500); System.out.print(".");
                Thread.sleep(500); System.out.print(".");
                Thread.sleep(500); System.out.println(".");

                try {
                    if(bookedSeats + 1 <= numSeats) { // if the user can still fit in the plane...
                        selSeat = bookedSeats + 1; // automatically select the next free seat number for them
                        bookedSeats += 1; // and add 1 to the number of booked seats
                        System.out.printf("[✔] Booking success! Your seat number is >> #%03d. <<%n", selSeat); // display success message
                        System.out.println("[*] Exiting to menu...");
                        Thread.sleep(1500);
                    } else { //  if not, throw the FullPlaneException
                        throw new FullPlaneException();
                    }
                } catch (FullPlaneException e) { //upon throwing the exception, allow the exception to display its message, then exit to menu
                    Thread.sleep(2000);
                }
                


            } else if("X".equals(choice)) {
                clearConsole(); // if inputted X, display message and exit program
                line();
                System.out.println("Exiting...\nThank you for flying with Philippine Airlines.");
                line();
                Thread.sleep(750);
                break;

            } else if(choice.toUpperCase().equals("FISH")) {
                // easter egg
                try { throw new Fish(); } catch (Fish bangus) { Thread.sleep(2000); }
            }

        } while (true);

    }

}
