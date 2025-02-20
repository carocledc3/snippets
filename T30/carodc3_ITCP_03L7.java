// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, BSIT-1L
// CC2/Introduction to Computer Programming
// [30IP-03L7] Laboratory Challenge №7
// 4 OCTOBER 2024

/*
>> PASSWORD VALIDATION SYSTEM << 

PROBLEM STATEMENT: Create a password validation system. The program should:
    - Ask the user to input a password.
    - Check if the password meets specific criteria (e.g., at least 8 characters long, contains a number, and contains an uppercase letter).
    - If the password does not meet the criteria, ask the user to input it again until a valid password is provided.

STEPS:
    1. Use a loop to ask the user to input a password.
    2. Check whether the password meets the required criteria.
    3. If the password is invalid, continue asking the user for input.
    4. Once a valid password is provided, confirm and exit the program.

*/


import java.util.Scanner;

public class carodc3_ITCP_03L7 {

    static int requiredLength = 16; // exposes the required length of the password so that the developer can edit it easily

    // method to check if password is valid; returns boolean
    static boolean isValidPassword(String password) {

        int passLength = password.length(); // length of user-inputted password

        // CRITERIA; all of these must return true for the password to be considered valid
        boolean isRequiredLength = (passLength >= requiredLength); // is the password greater than or equal to the required length?
        boolean hasUppercase = false; // does it have an uppercase letter?
        boolean hasLowercase = false; // does it have a lowercase letter?
        boolean hasNumber = false; // does it have a letter?
        boolean doesntHaveLiteralWordPassword = !password.contains("password"); // does it not contain the literal word 'password'?

        for (int i = 0; i < passLength; i++) {
            char w = password.charAt(i); // iterate through every character in the password
            // thanks https://www.javaguides.net/2024/06/java-character-class-methods.html
            if (Character.isDigit(w)) { hasNumber = true;} // if the character is a number, flip hasNumber to true
            if (Character.isUpperCase(w)) { hasUppercase = true;} // same with this one and if it has an Uppercase letter
            if (Character.isLowerCase(w)) { hasLowercase = true;} // same here
        }

        return (isRequiredLength && hasUppercase && hasLowercase && hasNumber && doesntHaveLiteralWordPassword); // return true if and only if all criteria are true
    }

    public static void main(String[] args) {
        Scanner inp; String selectedPass; // declare scanner and string objects. i delcared
        do { // loop infinite on purpose; only breaks out of it if the user's inputted password is valid
            System.out.print("Enter your new password: "); // prompt input..
            inp = new Scanner(System.in);
            selectedPass = inp.next(); // and store it here

            if(isValidPassword(selectedPass)) {
                break; // if password is valid, break out of the loop
            }

            // else, it gets to this point; prints error message and password criteria
            System.out.println("Invalid password!");
            System.out.printf("Password must:\n  - Be at least %s characters long\n  - Contain at least one uppercase and lowercase letter\n  - Contain at least one number\n  - Not contain the literal word 'password'\n", requiredLength);

        } while(true);

        // if program gets out of the loop and to this point, then it implies the password is valid
        inp.close(); // close the scanner object
        System.out.println("Your password is valid!"); // print success message YIPPEE
        
    }
}
