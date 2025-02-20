// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, BSIT-1L
// CC2/Introduction to Computer Programming
// [30IP-03L6] Laboratory Challenge №6
// 2 OCTOBER 2024

/*
>> CLASSROOM ATTENDANCE SYSTEM <<

- PROBLEM STATEMENT: Write a program to take attendance for a class of students. The system should:
    - Ask for the total number of students.
    - Repeatedly ask whether each student is present or absent.
    - After taking attendance for all students, display the number of students who were present and absent.

- STEPS:
    1. Input the total number of students in the class.
    2. Use a loop to ask whether each student is present or absent.
    3. Keep count of how many students are present and how many are absent.
    4. Display the final counts at the end.
 */


import java.util.Scanner;

public class carodc3_ITCP_03L6 {

    @SuppressWarnings("resource")
    public static void main(String[] args) {

        // declare scanner objects first
        Scanner input;

        // declare present and absent counters outside the scope of the main loop-de-loop
        int present = 0;
        int absent = 0;

        // input number of students
        System.out.print("Enter number of Students: ");
        input = new Scanner(System.in);
        int classSize = input.nextInt();
        // and capture it to an int variable

        // main loop
        // run time is of course proportional to the class size inputted by the user
        for (int i = 1; i <= classSize; i++) {

            // asks if student number N is present
            System.out.print("Is student #" + i + " present? (Y/N): ");
            input = new Scanner(System.in); // user inputs Y/N here
            char status = input.next().charAt(0); // no nextChar() method, so capture first char of next() instead; thanks https://www.geeksforgeeks.org/gfact-51-java-scanner-nextchar/
            switch (status) {
                // more compressed ‹rule switch› format; thanks https://stackoverflow.com/questions/77889555/what-is-the-difference-between-a-regular-switch-and-a-rule-switch-in-java
                case 'Y', 'y' -> present += 1; // if status inputted is 'Y' or 'y', increment the present counter
                case 'N', 'n' -> absent += 1; // if status inputted is 'N' or 'n', increment the absent counter
                default -> {
                    System.out.println("Invalid status. Please input again.");
                    i -= 1;
                }
            }
        }

        // after all that; print the total present and absent
        input.close();
        System.out.println("Present: " + present);
        System.out.println("Absent: " + absent);

    }
}