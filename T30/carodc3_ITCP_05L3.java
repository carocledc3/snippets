// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, CITCS-1L
// 30IP: CC2/Introduction to Computer Programming
// [30IP-05L3] Final Laboratory Challenge №3
// 11 NOVEMBER 2024

/*
>> UNIVERSITY COURSE ENROLLMENT AND GRADES TRACKER <<

OBJECTIVE: Manage student enrollment, track grades for multiple subjects, and calculate average grades for each student.

SCENARIO: You are tasked with creating a system that tracks students' enrollment and their grades for different subjects in a university course. You need to:
    - Track student names using a one-dimensional array.
    - Store grades for each student across multiple subjects using a two-dimensional array.
    - Calculate each student’s average grade and display their name along with their average grade.

TASK BREAKDOWN:
    - Create a one-dimensional array to store the names.
    - Create a two-dimensional array to store the grades of each student.
    - Calculate the average grade for each student.
    - Display each student’s name along with their average grade.
 */


import java.util.Scanner;

public class carodc3_ITCP_05L3 {

    static void line() {
        System.out.println("-------------------------------------------------------------------------");
    }

    static void doubleLine() {
        System.out.println("=========================================================================");
    }

    // method to get the average on int[] array
    static double getAverage(int[] input) {
        double avg = 0.0; // initial value
        for (int i = 0; i < input.length; i++) {
            avg += input[i] / input.length;
            // alternate method of getting the arithmetic mean
            // works because (α+β+γ+δ+ε+...)/n = (α/n)+(β/n)+(γ/n)+(δ/n)+(ε/n)+...
        }
        return avg;
    }

    // string splitter; thanks https://www.w3schools.com/java/ref_string_split.asp
    static String[] commaSeparate(String applejuice) {
        // splits String into String[] array with commas (and any amount of whitespace)
        // as delimiter
        return applejuice.split(",\s*");
    }

    // GRADE CHECKER
    static final boolean isValidGrade(int gr) {
        return !(gr > 99 || gr < 70);
    } // returns true if 70 < grade < 99

    // DISPLAY FORMATTING
    // BTW VS CODE why do you recommend me to put getters and setters on these. these are just formatting strings. calme-toi crisse
    static final String displayFormat = "%2d. %-32s%8.2f%n";
    static final String displayHeaderFormat = "%2s  %-32s%10s%n";

    // PARAMETERS
    static int maxStudents = 10;
    static int maxSubjects = 10;

    // METHOD TO CHECK IF INPUTTED GRADE LIST IS VALID (for given grade list and
    // required length)
    static int isValidGradeList(String bar, int reql) {
        // separate them into an array, as a string first so we can parse each into a
        // double
        String[] tempsAsStrings = commaSeparate(bar);

        // check first if entry length is valid...
        if (tempsAsStrings.length != reql) {
            return -1;
        } // error code if wrong length, to be processed later
        else {
            try { // https://open.spotify.com/track/7ayoL7WrBQP3ccJqELQXFK
                int w = 0; // temporary variable
                for (String tempsAsString : tempsAsStrings) {
                    w = Integer.parseInt(tempsAsString); // throws NumberFormatException if inputs are not integers
                    if (!isValidGrade(w)) {
                        return -3;
                    } // error code if inputs are not within valid range
                }
            } catch (NumberFormatException ie) {
                return -2; // error code if inputs are not integers
            }
        }

        // if it gets to this point; that means its passed the other tests; and
        // therefore it's valid
        return 1;
    }

    // METHOD TO PARSE A string[] GRADE LIST INTO AN int[] LIST
    static int[] parseGradeList(String bar) {
        String[] parsee = commaSeparate(bar);
        int[] câlisse = new int[parsee.length];
        for (int i = 0; i < câlisse.length; i++) {
            câlisse[i] = Integer.parseInt(parsee[i]);
        }
        return câlisse;
    }

    @SuppressWarnings("StringEquality")
    public static void main(String[] args) {
        // PARAMETERS
        Scanner inp; // declare scanner object
        int nStudents = 0; // initalise number of students
        int nSubjects = 0; // initalise number of subjects
        String sectionName; // course name for æsthetic purposes

        String[] studentNames;
        int[][] studentGrades; // declare arrays for names and grades...
        double[] averages; // and averages too

        // title
        System.out.println("ENROLLMENT AND GRADES TRACKER");
        doubleLine();

        do {
            System.out.print("Input section name: "); // input course name...
            inp = new Scanner(System.in);
            sectionName = inp.nextLine(); // and store it
        } while ((sectionName == "" || sectionName == " ")); // error handling to avoid blank section names
        
        // INPUTTING OF NUMBER OF STUDENTS
        line();
        for (int i = 0; i < 1; i++) { // weird one-iteration loop thing so i can reprompt user input when needed
            
            try {
                String temp; // temporary string to be check if its an int or not
                do {
                    // input number of students...
                    System.out.printf("Input number of students in section « %s »: ", sectionName);
                    inp = new Scanner(System.in);
                    temp = inp.nextLine();
                } while (temp == "");

                // reprompt entry when input is more than the maximum
                if (Integer.parseInt(temp) > maxStudents) {
                    System.out.printf("ERROR » Section cannot have more than %d students!%n", maxStudents);
                    i--;
                }
                // and also when its lower than 2
                if (Integer.parseInt(temp) <= 1) {
                    System.out.println("ERROR » Invalid input. How are there less than 2 students in the section?");
                    i--;
                }

                // if it gets to this point, that means its passed all the tests and we can
                // assign the value of the number of students
                nStudents = Integer.parseInt(temp);

            } catch (NumberFormatException ie) {
                // reprompt entry if input is not an integer
                System.out.println("ERROR » Input must be an integer!");
                i--;
            }
        }
        studentNames = new String[nStudents]; // declare names array

        // INPUT NUMBER OF SUBJECTS
        line();
        for (int i = 0; i < 1; i++) { // weird one-iteration loop thing so i can reprompt user input when needed
            // INPUTTING OF NUMBER OF STUDENTS
            try {
                String temp; // temporary string to be check if its an int or not
                do {
                    System.out.print("Input the number of subjects the students have: "); // input number of students...
                    inp = new Scanner(System.in);
                    temp = inp.nextLine();
                } while (temp == "");

                // reprompt entry when input is more than the maximum
                if (Integer.parseInt(temp) > maxSubjects) {
                    System.out.printf("ERROR » Section cannot have more than %d subjects!%n", maxSubjects);
                    i--;
                }
                // and also when its lower than 2
                if (Integer.parseInt(temp) <= 1) {
                    System.out.println("ERROR » Invalid input. Surely they have more than 1 subject, no?");
                    i--;
                }

                // if it gets to this point, that means its passed all the tests and we can
                // assign the value of the number of subjects
                nSubjects = Integer.parseInt(temp);

            } catch (NumberFormatException ie) {
                // reprompt entry if input is not an integer
                System.out.println("ERROR » Input must be an integer!");
                i--;
            }
        }

        // INPUT STUDENT NAMES
        line();
        System.out.printf("One by one, input the names of the %d students:%n", nStudents);
        for (int i = 1; i <= nStudents; i++) {
            System.out.printf("  %2d. ", i);
            inp = new Scanner(System.in);
            // one input sequence per name. decided to not make this an «input comma-separated» thing bc what if the uesr inputs summat in the format LASTNAME, FIRSTNAME and then it gets commaseparated out and we don't want that do we? MINIONS! TONIGHT, WE STEAL, THE MOON!
            studentNames[i - 1] = inp.nextLine(); // put the inputted name into the studentNames array
        }

        studentGrades = new int[nStudents][nSubjects];
        
        // INPUT STUDENT GRADES
        line();
        for (int i = 0; i < studentNames.length; i++) {
            System.out.printf("Input the grades for « %s », comma-separated: %n  > ", studentNames[i]);
            String ingrade = inp.nextLine(); // prompt student grades input...
            int status = isValidGradeList(ingrade, nSubjects);

            // check if input is valid...
            switch (status) {
                case -1 -> {
                    // case of isValidGradeList when input has wrong length
                    System.out.printf("ERROR » Grades list should be %s entries long!", nSubjects);
                    i -= 1;
                }
                case -2 -> {
                    // case of isValidGradeList when inputs are not integers
                    System.out.println("ERROR » Grades should be integers!");
                    i -= 1;
                }
                case -3 -> {
                    // case of isValidGradeList when input's integers are in wrong range
                    System.out.println("ERROR » Grades should be integers between 70 and 99 inclusive!");
                    i -= 1;
                }
                case 1 -> // case of isValidGradeList when everything passes
                    studentGrades[i] = parseGradeList(ingrade);
                default -> {
                }
            }
        }

        inp.close();

        // FILL AVERAGES
        averages = new double[nStudents];
        for (int i = 0; i < nStudents; i++) {
            averages[i] = getAverage(studentGrades[i]); // get the average of each 1D array in studentGrades[][] and store them in averages[]
        }

        // DISPLAY
        doubleLine();
        System.out.printf(displayHeaderFormat, "#", "STUDENT NAME", "AVERAGE"); // display header...
        line();
        for (int i = 0; i < nStudents; i++) { // and display details
            System.out.printf(displayFormat, i + 1, studentNames[i], averages[i]);
        }
    }
} // et voilà