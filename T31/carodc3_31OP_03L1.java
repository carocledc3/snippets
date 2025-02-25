// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, CITCS-1F
// CC3/OBJECT-ORIENTED PROGRAMMING
// [31OP-03L1] Unit Converter Application
// 25 February 2025

// OBJECTIVE: To create a Java program that simulates a basic unit converter, allowing users to perform various conversions until they choose to exit the application.
// INSTRUCTIONS:
/*

1. CLASS STRUCTURE
    - Create a class named `Converter` to handle the conversion logic.
    - Within the `Converter class`, define private fields to store the values for conversion, such as `kilometres`, `miles`, `celsius`, `fahrenheit`, `usDollars`, and `philippinePesos`.
    - Implement properties with get and set methods for each field to ensure encapsulation.
    - Use `double` for declarations/definining.

2. CONVERSION METHODS:
- Implement public methods within the Converter class to perform the following conversions:
    - ConvertKilometresToMiles
    - ConvertMilesToKilometres
    - ConvertCelsiusToFahrenheit
    - ConvertFahrenheitToCelsius
    - ConvertUSDollarsToPhilippinePesos
    - ConvertPhilippinePesosToUSDollars
    Ensure that each method calculates and returns the converted value based on the provided input.
    Use these conversions:
        - 1 Dollar = 56.21 Pesos
        - 1 mile = 1.60934 kilometres
        - °F = 9C/5 + 32
        - °C = F-32 * (5/9)

3. MAIN PROGRAM:
    - In the Main method of your program, create an instance of the Converter class.
    - Use a loop to continuously prompt the user for a conversion command until they choose to exit the application.
    - Inside the loop, display the available conversion options to the user and prompt them to select one.
    - Based on the user's selection, prompt them to enter the input values required for the chosen conversion.
    - Call the appropriate method from the Converter class to perform the conversion and display the result to the user.
    - Allow the user to perform additional conversions or exit the application based on their choice.
    - Use Switch Case

 */

import java.util.Scanner;

class Converter {
    
    // initial result. we should only use one result attribute because we're only doing one conversion at a time anyway
    private double result;
    public double getResult() { return result;} // and getter because the setting only happens with the conversion methods anyway

    // for storing the last input
    private double lastInput;
    public double getLastInput() { return lastInput;}
    public void setLastInput(double newInput) { lastInput = newInput; }

    // storing the last conversion type
    private int lastConversion = 0;

    // conversion format display based on the last conversion type. because we want to be fancy and remember what's the last conversion the user did
    public String lastConversionFormat() {
        String format = switch(lastConversion) {
            case 1 -> "%.2f km == %.2f mi";
            case 2 -> "%.2f mi == %.2f km";
            case 3 -> "$%.2f == ₱%.2f";
            case 4 -> "₱%.2f == $%.2f";
            case 5 -> "%.2f°C == %.2f°F";
            case 6 -> "%.2f°F == %.2f°C";
            default -> "[None]"; // default if no conversion has been done yet
        };
        return format;
    }

    // CONVERSION METHODS
    // each of these methods does two things:
    // 1) sets the lastCovnversion attribute to the int corresponding to the conversion type, and
    // 2) does the conversion and sets the result to it. therefore these also act as setter methods to the result attribute

    // miles <-> km
    private final double mileConstant = 1.69034; // the factor by which these conversions are done
    public void km_mi(double km) { lastConversion = 1; result = km / mileConstant; }
    public void mi_km(double mi) { lastConversion = 2; result = mi * mileConstant; }

    // USD <-> PHP
    private final double phpConstant = 57.89; // PHP-USD exchange rate as of 24 feb 2025
    public void usd_php(double usd) { lastConversion = 3; result = usd * phpConstant; }
    public void php_usd(double php) { lastConversion = 4; result = php / phpConstant; }

    // fahrenheit <-> celsius
    public void Cel_Fahr(double C) { lastConversion = 5; result = (9*C)/5 + 32; }
    public void Fahr_Cel(double F) { lastConversion = 6; result = (F - 32) * 5 / 9; }
}

public class carodc3_31OP_03L1 {
    
    //cleaner alternative to using Thread.sleep() in loops
    @SuppressWarnings("UseSpecificCatch")
    final static void pressEnterToContinue() {
        System.out.println("Press " + HL("Enter") + " to continue...");
        try { System.in.read(); } catch(Exception e) {}  
    }

    @SuppressWarnings({"deprecation", "UseSpecificCatch"})
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

    // lines for æsthetic purposes
    final static void line() { System.out.println("--------------------------------------------"); }
    final static void doubleLine() { System.out.println("============================================"); }
    // highlight method
    final static String HL(String text) { return String.format("\u001B[34m%s\u001B[0m",text);}

    @SuppressWarnings({"UseSpecificCatch", "ConvertToTryWithResources"})
    public static void main(String[] args) throws InterruptedException {

        // initialise converter and scanner objects
        Converter conv = new Converter();
        Scanner inp = new Scanner(System.in);

        // main app loop...
        do {
            clearConsole(); // clear console each time so it's cleaner
            System.out.println(HL(">> UNIT CONVERTER APPLICATION <<")); // title
            System.out.println("Last conversion: " + HL(String.format(conv.lastConversionFormat(), conv.getLastInput(), conv.getResult())));
            doubleLine(); // display last conversion

            // display options
            System.out.println("CONVERSION OPTIONS:");
            System.out.println(HL("[1] ") + "Kilometres -> Miles");
            System.out.println(HL("[2] ") + "Miles -> Kilometres");
            System.out.println(HL("[3] ") + "US Dollars -> Philippine Pesos");
            System.out.println(HL("[4] ") + "Philippine Pesos -> US Dollars");
            System.out.println(HL("[5] ") + "°Celsius -> °Fahrenheit");
            System.out.println(HL("[6] ") + "°Fahrenheit -> °Celsius");
            System.out.println(HL("[X] ") + "Exit");
            line();
            // prompt user input
            System.out.print("Select option: ");
            String choice = inp.nextLine();

            if(choice.toUpperCase().equals("X")) {
                break; // if input is X, exit application
            } else { // else, process input
                switch(choice) {
                    case "1" -> {
                        // KILOMETRES TO MILES CONVERSION
                        doubleLine();
                        // one-step loop ensures i can reprompt user entry if it's incorrect
                        for (int i = 0; i < 1; i++) {
                            System.out.print("Enter value in " + HL("kilometres: "));
                            String num = inp.nextLine();
                            // try-catch to catch incorrect inputs
                            try{
                                // if user inputs 'X', go back to the main menu
                                if (num.toUpperCase().equals("X")) { break; } 
                                double proc = Double.parseDouble(num); // else, parse and store the input...
                                conv.setLastInput(proc); // remember the last input...
                                conv.km_mi(proc); // and do the conversion

                                System.out.println(HL(String.format(conv.lastConversionFormat(), conv.getLastInput(), conv.getResult()))); // print result for a bit
                                pressEnterToContinue();
                            } catch (Exception e) {
                                // if user inputs anything that can't be a number, it reprompts
                                i--;
                            }
                        }
                    }
                    case "2" -> {
                        // MILES TO KILOMETRES CONVERSION
                        doubleLine();
                        // one-step loop ensures i can reprompt user entry if it's incorrect
                        for (int i = 0; i < 1; i++) {
                            System.out.print("Enter value in "+ HL("miles: "));
                            String num = inp.nextLine();
                            // try-catch to catch incorrect inputs
                            try{
                                // if user inputs 'X', go back to the main menu
                                if (num.toUpperCase().equals("X")) { break; } 
                                double proc = Double.parseDouble(num); // else, parse and store the input...
                                conv.setLastInput(proc); // remember the last input...
                                conv.mi_km(proc); // and do the conversion

                                System.out.println(HL(String.format(conv.lastConversionFormat(), conv.getLastInput(), conv.getResult()))); // print result for a bit
                                pressEnterToContinue();
                            } catch (Exception e) {
                                // if user inputs anything that can't be a number, it reprompts
                                i--;
                            }
                        }
                    }
                    case "3" -> {
                        // USD TO PHP CONVERSION
                        doubleLine();
                        // one-step loop ensures i can reprompt user entry if it's incorrect
                        for (int i = 0; i < 1; i++) {
                            System.out.print("Enter value in "+ HL("US$: "));
                            String num = inp.nextLine();
                            // try-catch to catch incorrect inputs
                            try{
                                // if user inputs 'X', go back to the main menu
                                if (num.toUpperCase().equals("X")) { break; } 
                                double proc = Double.parseDouble(num); // else, parse and store the input...
                                conv.setLastInput(proc); // remember the last input...
                                conv.usd_php(proc); // and do the conversion

                                System.out.println(HL(String.format(conv.lastConversionFormat(), conv.getLastInput(), conv.getResult()))); // print result for a bit
                                pressEnterToContinue();
                            } catch (Exception e) {
                                // if user inputs anything that can't be a number, it reprompts
                                i--;
                            }
                        }
                    }
                    case "4" -> {
                        // PHP TO USD CONVERSION
                        doubleLine();
                        // one-step loop ensures i can reprompt user entry if it's incorrect
                        for (int i = 0; i < 1; i++) {
                            System.out.print("Enter value in "+ HL("PH₱: "));
                            String num = inp.nextLine();
                            // try-catch to catch incorrect inputs
                            try{
                                // if user inputs 'X', go back to the main menu
                                if (num.toUpperCase().equals("X")) { break; } 
                                double proc = Double.parseDouble(num); // else, parse and store the input...
                                conv.setLastInput(proc); // remember the last input...
                                conv.php_usd(proc); // and do the conversion
                                System.out.println(HL(String.format(conv.lastConversionFormat(), conv.getLastInput(), conv.getResult()))); // print result for a bit
                                pressEnterToContinue();
                            } catch (Exception e) {
                                // if user inputs anything that can't be a number, it reprompts
                                i--;
                            }
                        }
                    }
                    case "5" -> {
                        // CELSIUS TO FAHRENHEIT CONVERSION
                        doubleLine();
                        // one-step loop ensures i can reprompt user entry if it's incorrect
                        for (int i = 0; i < 1; i++) {
                            System.out.print("Enter value in "+ HL("degrees Celsius: "));
                            String num = inp.nextLine();
                            // try-catch to catch incorrect inputs
                            try{
                                // if user inputs 'X', go back to the main menu
                                if (num.toUpperCase().equals("X")) { break; } 
                                double proc = Double.parseDouble(num); // else, parse and store the input...
                                conv.setLastInput(proc); // remember the last input...
                                conv.Cel_Fahr(proc); // and do the conversion

                                System.out.println(HL(String.format(conv.lastConversionFormat(), conv.getLastInput(), conv.getResult()))); // print result for a bit
                                pressEnterToContinue();
                            } catch (Exception e) {
                                // if user inputs anything that can't be a number, it reprompts
                                i--;
                            }
                        }
                    }
                    case "6" -> {
                        // FAHRENHEIT TO CELSIUS CONVERSION
                        doubleLine();
                        // one-step loop ensures i can reprompt user entry if it's incorrect
                        for (int i = 0; i < 1; i++) {
                            System.out.print("Enter value in "+ HL("degrees Fahrenheit: "));
                            String num = inp.nextLine();
                            // try-catch to catch incorrect inputs
                            try{
                                // if user inputs 'X', go back to the main menu
                                if (num.toUpperCase().equals("X")) { break; } 
                                double proc = Double.parseDouble(num); // else, parse and store the input...
                                conv.setLastInput(proc); // remember the last input...
                                conv.Fahr_Cel(proc); // and do the conversion

                                System.out.println(HL(String.format(conv.lastConversionFormat(), conv.getLastInput(), conv.getResult()))); // print result for a bit
                                pressEnterToContinue();
                            } catch (Exception e) {
                                // if user inputs anything that can't be a number, it reprompts
                                i--;
                            }
                        }
                    }
                    default -> {continue;} // if the user enters anything other than the valid options, ignore it
                }
            }
        } while (true);
        inp.close();
    }
}