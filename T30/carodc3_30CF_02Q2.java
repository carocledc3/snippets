import java.util.Scanner; import java.lang.System; import java.util.ArrayList;

public class carodc3_30CF_02Q2 {

    // custom string reversing method
    static String reverseString(String input) {
        String res = "";
        char[] chars = input.toCharArray();
        for (char c : chars) {
            res = c + res; // for each character, add it to the left instead of the right
            // so if input is "ABCDEF"
            // it will go
            // "A"
            // "BA"
            // "CBA"
            // "DCBA"
            // "EDCBA"
            // "FEDCBA"
        }
        return res;
    }

    // convert input number to decimal first (knowing the radix) so we can deal with the number as an int type and do maths on it
    static int SomeBaseToDecimal(String input, int base) {

        // check first if the inputs are valid
        // if the base is outside 2-16, throw error
        if(base < 2 || base > 16) { throw new IndexOutOfBoundsException("Base must be between 2 and 16 inclusive!");}
        // if input contains a - sign, throw error
        if(input.contains("-")) { throw new IndexOutOfBoundsException("Input number must not be negative!"); }

        // reverse the input so we could deal with it from smallest-place digit to biggest-place digit
        String revinput = reverseString(input);

        // define array of mga pwedeng digits
        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        ArrayList<Character> digitsList = new ArrayList<Character>(); 
        for (int j = 0; j < digits.length; j++) { digitsList.add(digits[j]); }

        // convert reversed input to char array
        char[] revinputchars = revinput.toCharArray();
        
        // define result variable; we'll be storing our converted number here
        int result = 0;

        // loop to convert each digit to its decimal value
        for (int i = 0; i < revinputchars.length; i++) {
            result += Math.pow(base, i) * digitsList.indexOf(revinputchars[i]);
        }
            //       (base ^ place value) * ^^ value of digit ^^
            // do that for every digit then add the results together
            // the Base here is the one the user inputted
            // and the i here becomes the position of the digit we're currently dealing with
            // the Value of Digit thing basically asks "what is the position of the current digit we're dealing with, in the list of valid digits?" and it outputs an integer 0-15, which is the value of the digit
        
            // so for example our input is "BEEF", and our base is 16
            // our revinput becomes reverseString("BEEF") = "FEEB"
            // our revinputchars becomes revinput.toCharArray() = {'F', 'E', 'E', 'B'}
            //                                    with positions    0 ,  1 ,  2 ,  3
            //                             and with digit values   15 , 14 , 14 , 11
            // so if we put it into the loop it becomes
            // value of 'F' in base 16, position 0 = 15*(16^0) = 15*1   = 15
            // value of 'E' in base 16, position 1 = 14*(16^1) = 14*16  = 224
            // value of 'E' in base 16, position 2 = 14*(16^2) = 14*256 = 3584
            // value of 'F' in base 16, position 3 = 11*(16^3) = 14*256 = 45056
            // add them all together; results in 48879
            // which is what will be returned here in the next line as the resulting value
        return result; 
    }


    // convert decimal to output base
    static String DecimalToSomeBase(int input, int outputRadix) {

        String res = ""; // our result string; empty string at first

        // define array of every possible digit representation
        String[] digits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        // positions of each element in an array count up from zero, so the positions of these are
        //                  0    1    2    3    4    5    6    7    8    9   10   11   12   13   14   15
        // corresponding neatly with the actual value of each digit
        // so digits[15] returns "F"

        int remainder; // create remainder variable
        int quotient = input; // input is first quotient

        while (quotient > 0) {
            // all of this loops until the quotient is zero
            remainder = quotient % outputRadix;
            res = digits[remainder] + res; // find the element with the (remainder)th position in the array of digits
            // then add that to the left of the string (kind of like in binary where bottom->top yung pagbasa ng mga remainders)
            quotient = quotient / outputRadix; // quotient/outputRadix becomes new value of quotient
            // this automatically drops the decimal part! since we defined quotient in line 80 to be an int
        }
        // return result string as the resulting value of this function
        return res;
    }

    // now combine the two
    static String BaseToBase (String input, int baseA, int baseB) {
        return DecimalToSomeBase(
            SomeBaseToDecimal(input, baseA),
            baseB
        );
        // this basically says:
        // convert the input to decimal first, knowing its origin Base (base A)
        // then convert the output of THAT to Base B
        // this also returns a String bc DecimalToSomeBase returns a String
    }

    // print every base for any input with given radix
    static void printBases(String input, int baseA) {

        // names of the bases sourced from Wikipedia bc i wanna feel fancy
        // "Binary" here is in position 2-2 = 0, "Ternary" is in pos. 3-2 = 1, etc
        // basically the name for base N is in position N-2 in this array
        String[] bases = {"Binary", "Ternary", "Quaternary", "Quinary", "Senary", "Septenary", "Octal", "Nonary", "Decimal", "Undecimal", "Duodecimal", "Tridecimal", "Quattuordecimal", "Quindecimal", "Hexadecimal"};
        
        // prints the inputted number and its base
        System.out.println("INPUT: " + input + " in base " + baseA);
        for (int i = 2; i <= 16; i++) {
            // for each N from 2-16, print the base, its name, and the conversion of the input from its inputted base to Base N
            System.out.println( "  > Base " + i + " | " + bases[i-2] + ": " + BaseToBase(input, baseA, i));
        }
    }

    @SuppressWarnings("resource")
    // now FINALLY we get to the main method/function
    public static void main(String[] args) {
        // declare the scanners outside the infinite loop first so they dont keep getting redefined
        Scanner input;
        Scanner inputRadix;
        // infinite loop
        while(true) {
            // at the beginning of each turn, print a line, then ask for input
            System.out.println("----------------------------------------------------");

            // ask for which number to convert. String type
            System.out.print("Number to convert: ");
            input = new Scanner(System.in); // then give definition to the scanners here inside the loop; ask for input
            String proc = input.next(); // store the input with the catch-all .next() method, which captures anything the user inputs
            if(proc.startsWith("STOP")) { break; } // if the stored input starts with "STOP", exit the loop and therefore the program

            // ask for the base. int type
            System.out.print("Radix (Base) of input: ");
            inputRadix = new Scanner(System.in);
            int procRadix = inputRadix.nextInt(); // store the inputted base in an int 
            
            // call out printBases method from line 112 with input = proc, and baseA = procRadix
            printBases(proc, procRadix);
                
        }
    }
}