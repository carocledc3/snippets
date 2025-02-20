// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, CITCS-1F
// CC3/OBJECT-ORIENTED PROGRAMMING
// [31OP-01L1] Calculator Class
// 3 Feb 2025 M

import java.util.Scanner;

public class carodc3_31OP_01L1 {

    // static so i don't have to instantiate it anymore
    static class Calculator {

        // variable to store the result
        static private String result;

        static String getResult() {return result;}
        
        // splits String into String[] array with commas (and any amount of whitespace) as delimiter
        static String[] commaSeparate(String applejuice) { return applejuice.split(",\s*"); }

        // method to parse inputs if they're valid or not; returns actual result if valid, if not returns NaN
        static double inputParse(String input) {
            double res;
            try {
                res = Double.parseDouble(input);
            } catch (Exception e) {
                return Math.sqrt(-1);
            }
            return res;
        }

        static final String displayFormat = "%,.2f";
        // operations. these change the value of `result`
        static void add(double addend1, double addend2) { result = String.format(displayFormat, addend1 + addend2); }
        static void subtract(double minuend, double subtrahend) { result = String.format(displayFormat, minuend - subtrahend); }
        static void multiply(double factor1, double factor2) { result = String.format(displayFormat, factor1 * factor2); }
        static void divide(double numerator, double denominator) { result = String.format(displayFormat, numerator / denominator); }
        static void oddOrEven(double arg) { result = (arg % 2 == 0.0) ? "True" : "False"; }

    }

    // lines for æsthetic purposes
    final static void line() { System.out.println("------------------------------------"); }
    final static void doubleLine() { System.out.println("===================================="); }

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

    public static void main(String[] args) {
        // main app loop...
        Scanner inp = new Scanner(System.in);
        do { 
                // clear console each time for Cleanliness
                clearConsole();
                doubleLine();
                System.out.println("CONSOLE CALCULATOR");
                doubleLine();
                System.out.printf(">> LAST RESULT: %s <<%n", Calculator.result);
                System.out.println("  [1] Add two numbers");
                System.out.println("  [2] Subtract two numbers");
                System.out.println("  [3] Multiply two numbers");
                System.out.println("  [4] Divide two numbers");
                System.out.println("  [5] Check if number is odd/even");
                System.out.println("  [X] Exit");
                line();

                // prompt user choice...
                System.out.print("Select action: \n  > ");
                String choice = inp.nextLine();
    
                // for all these, with inputs that resolve to more than three operands, only the first two opreands will actually be processed; the rest will be ignored 
                if(choice.equals("1")) {
                    // addition
                    System.out.print("Input the two numbers to be added, separated by a comma:\n  > ");
                    String[] addendsTemp = Calculator.commaSeparate(inp.nextLine());
                    Calculator.add(Calculator.inputParse(addendsTemp[0]), Calculator.inputParse(addendsTemp[1]));
                    
    
                } else if(choice.equals("2")) {
                    // subtraction
                    System.out.print("Input the two numbers to be subtracted, separated by a comma:\n  > ");
                    String[] addendsTemp = Calculator.commaSeparate(inp.nextLine());
                    Calculator.subtract(Calculator.inputParse(addendsTemp[0]), Calculator.inputParse(addendsTemp[1]));
                    
    
                } else if(choice.equals("3")) {
                    // multiplication
                    System.out.print("Input the two numbers to be multiplied, separated by a comma:\n  > ");
                    String[] addendsTemp = Calculator.commaSeparate(inp.nextLine());
                    Calculator.multiply(Calculator.inputParse(addendsTemp[0]), Calculator.inputParse(addendsTemp[1]));
                    
    
                } else if(choice.equals("4")) {
                    // division
                    System.out.print("Input the two numbers to be divided, separated by a comma:\n  > ");
                    String[] addendsTemp = Calculator.commaSeparate(inp.nextLine());
                    Calculator.divide(Calculator.inputParse(addendsTemp[0]), Calculator.inputParse(addendsTemp[1]));
                    
    
                } else if(choice.equals("5")) {
                    // odd/even
                    System.out.print("Input the number to be checked for oddness/evenness:\n  > ");
                    Calculator.oddOrEven(Calculator.inputParse(inp.nextLine()));

                } else if(choice.equals("X")) {
                    //exit
                    break;
                }
        } while (true);
    }
}
