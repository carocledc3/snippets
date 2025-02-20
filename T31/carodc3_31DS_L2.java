// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, CITCS-1F
// CC4/DATA STRUCTURES AND ALGORITHMS
// [31DS-L2] Laboratory Activity #2
// 2 Feb. 2025 S

import java.io.IOException;
import java.util.Scanner;

public class carodc3_31DS_L2 {

    final static int[] failcode = { -1 };

    // splits String into String[] array with commas (and any amount of whitespace)
    // as delimiter
    private static String[] commaSeparate(String applejuice) {
        return applejuice.split(",\s*");
    }

    // method to check whether single string resolves to an int
    private static int intParser(String inp) {
        int res;
        try {
            res = Integer.parseInt(inp);
        } catch (Exception e) {
            return -1;
        }
        return res;
    }

    // method to check whether user input (of dimensions or of coordinates) is
    // indeed valid
    private static boolean intsParser(String orangejuice) {
        String[] orange = commaSeparate(orangejuice); // split input into String array based on commas
        for (String drop : orange) {
            try { // for each element in the split input,
                Integer.parseInt(drop); // check if it's an integer
            } catch (Exception e) {
                return false; // and return false if there's one that's not one
            }
        }
        // else, if it gets to this point, that means every part of the input is valid;
        // return true
        return true;
    }

    // input decoding. decodes a String input of comma-separated ints
    static int[] intsDecoder(String cheddar) {
        if (!intsParser(cheddar)) {
            int[] fail = { -1 }; // if input is not valid in the first place, return {-1}
            return fail;
        } else { // else, we know that the input is valid;
            String[] swissCheese = commaSeparate(cheddar); // separate input based on commas
            int[] parmesan = new int[swissCheese.length]; // and add each element of that to this new int[]
            for (int i = 0; i < swissCheese.length; i++) {
                parmesan[i] = Integer.parseInt(swissCheese[i]);
            }
            return parmesan; // return the int[]
        }
    }

    // simple method to calculate the product of all elements in an int array
    static int getTotalProduct(int[] pizza) {
        int result = 1;
        for (int topping : pizza) {
            result *= topping;
        }
        return result;
    }

    // method to return esize of specified data type
    static int getEpsilon(String weez) {
        int size = 0;
        if (weez.equals("char")) {
            size = 1;
        } else if (weez.equals("int")) {
            size = 2;
        } else if (weez.equals("float")) {
            size = 4;
        } else if (weez.equals("double")) {
            size = 8;
        } else {
            size = 0;
        }
        return size;
    }

    // method to get cumulative record size based on inputted primitive data types
    static int getRecordSize(String input) {

        // oo wee oo i look just like buddy holly
        String[] weezer = commaSeparate(input);

        int size = 0; // initial size is 0
        for (String weez : weezer) { // for each string in the comma-separated array...
            if (weez.contains("[") && weez.contains("]")) { // if it contains [], it's an array type declaration, probably with a size enclosed. treat it as such
                int ha = weez.indexOf('['); // find index of each of the brackets
                int hb = weez.indexOf(']');
                String arrtype = weez.substring(0, ha); // we know the type of the array will be everything to the left of the bracket
                int extract = intParser(weez.substring(ha + 1, hb)); // and we know that the size of the array willbe  between the brackets
                size += getEpsilon(arrtype) * extract; // get the epsilon of the array type, multiply it by the array size, and add it to the record size
            } else { // else if not an array, just get the epsilon and add it to the record size
                size += getEpsilon(weez);
            }
        }
        return size;
    }

    // method to get memory address of element in n-dimensional array with specified
    // dimensions and coordinates.
    // master formula for all of this for N dimensions is
    // α + ε · Σ_(x=0)^N [ k_N · λ_N ]
    // where λ_N = Π_(y=0)^(N-1) [ b_N ]

    static int getMemoryAddress(int[] dimensions, int[] coords, int alpha, int epsilon) {

        // if dimensions and coordinate arrays aren't the same length, return fail code
        if (dimensions.length != coords.length) {
            return -1;
        }

        // address starts off as base address
        int address = alpha;

        // for each of the coordinates...
        for (int x = 0; x < coords.length; x++) {
            int bsize = 1;

            // for each dimension, calculate λ_N...
            for (int y = x + 1; y < coords.length; y++) {
                bsize *= dimensions[y]; // multiply by the size of each n-level sub-array. this is the Π_(y=0)^(N-1) [ b_N ] part
            }
            address += epsilon * bsize * coords[x]; // then add to the address the size * the coordinate * the epsilon
                                                    // for the array. this is the ε · Σ_(x=0)^N [ k_N · λ_N ] part
        }
        return address;
    }

    // lines for æsthetic purposes
    final static void line() {
        System.out.println("------------------------------------------------------------");
    }

    final static void doubleLine() {
        System.out.println("============================================================");
    }

    @SuppressWarnings({ "deprecation", "UseSpecificCatch" })
    public final static void clearConsole() { // method to clear console, thank you
                                              // https://stackoverflow.com/questions/2979383/how-to-clear-the-console-using-java
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                System.out.println("\u001b[2J" + "\u001b[H");
            }
        } catch (Exception E) {
            // je m'en câlisse
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner inp = new Scanner(System.in);
        // main app loop...
        do {
            clearConsole(); // clear console every finished iteration for ✨Cleanliness✨
            doubleLine();
            System.out.println("ARRAY AND RECORD ADDRESS CALCULATOR");
            doubleLine();
            System.out.println("  [1] Calculate Array Element Address");
            System.out.println("  [2] Calculate Record Address");
            System.out.println("  [X] Exit");
            line();

            // prompt user input. don't bother converting or checking bc the app will do
            // nothing if the user enters a choice that isn't in the options
            System.out.print("Select action: ");
            String chock = inp.nextLine();

            // option to calculate array element addresses...
            if (chock.equals("1")) {
                // declare all variables so they're globally scoped
                int[] arrayDimensions;
                int[] elementCoords;
                int epsilon;
                int alpha;
                doubleLine();

                for (int i = 0; i < 1; i++) {
                    clearConsole();
                    line();

                    // DO INPUT CHECKS FIRST BEFORE WRITING TO THE ARRAY !!!! else i-- to re-prompt
                    // input won't work for some reason

                    System.out.print("Input the dimensions of the array as comma-separated values: \n  > "); // prompt user input...
                    String arrayInp = inp.nextLine(); // process it...

                    // intsParser() checks if the input is a valid sequence of ints. if not,
                    // reprompt...
                    if (!intsParser(arrayInp)) {
                        System.out.println("[!] Invalid input! Say it again?");
                        System.out.println("Press any key to continue...");
                                            System.in.read();
                        i--;
                    } else {
                        // if valid, then set the array dimensions to whatever the user inputted
                        arrayDimensions = intsDecoder(arrayInp);

                        String dimsDisplay = "";
                        for (int d = 0; d < arrayDimensions.length; d++) {
                            if (d + 1 == arrayDimensions.length) {
                                dimsDisplay += String.format("%,d",arrayDimensions[d]);
                            } else {
                                dimsDisplay += String.format("%,d",arrayDimensions[d]) + " * ";
                            }
                        } // and display it so that the user knows
                        System.out.printf("[i] Your array's dimensions are %s.%n", dimsDisplay);
                        // display total number of elements the array has based on its dimensions
                        System.out.printf("[i] Your array has a total of %,d elements.%n", getTotalProduct(arrayDimensions));

                        line();
                        // next prompt for the size in memory (epsilon/ε) of each element
                        System.out.print("Input the size in memory (in bytes) of each element of the array:\n  > ");
                        String epsilonTemp = inp.nextLine(); // catch input...

                        if (intParser(epsilonTemp) == -1) { // process it and reprompt if invalid...
                            System.out.println("[!] Invalid input! Say it again?");
                            System.out.println("Press any key to continue...");
                            System.in.read();
                            i--;
                        } else {

                            // else if valid, set the inputted value as epsilon...
                            epsilon = intParser(epsilonTemp);
                            System.out.printf("[i] Size in memory of each element (ε): %,d bytes%n", epsilon);

                            line();

                            // same set of steps for the starting address: prompt, check if valid, reprompt if false, accept if true
                            System.out.print("Input the starting address in memory (in bytes) of the array:\n  > ");
                            String alphaTemp = inp.nextLine();
                            if (intParser(alphaTemp) == -1) {
                                System.out.println("[!] Invalid input! Say it again?");
                                System.out.println("Press any key to continue...");
                                            System.in.read();
                                i--;
                            } else {
                                alpha = intParser(alphaTemp);
                                System.out.printf("[i] Starting address (α): %,d bytes%n", alpha);

                                line();
                                // coordinates input...
                                System.out.print(
                                "Input the coordinates of the array element as comma-separated values: \n  > ");
                                String coordsInp = inp.nextLine(); // prompt input...

                                // check if input is valid...
                                if (intsDecoder(coordsInp)[0] == -1) {
                                    System.out.println("[!] Invalid input! Say it again?");
                                    System.out.println("Press any key to continue...");
                                            System.in.read();
                                    i--; // reprompt if invalid input...
                                } else {
                                    elementCoords = intsDecoder(coordsInp);
                                    boolean outOfBounds = false; // check if corrdinates are out of bounds of earlier-specified dimensions...
                                    for(int k = 0; k < elementCoords.length; k++) {
                                        if(elementCoords[k] >= arrayDimensions[k]) {
                                            outOfBounds = true; break;
                                        }
                                    }
                                    if(outOfBounds) {
                                        System.out.println("[!] Index out of bounds! Say it again?");
                                        System.out.println("Press any key to continue...");
                                            System.in.read(); 
                                        i--; // reprompt if out of bounds...
                                    } else {
                                        // else, display them for confirmation...
                                        String coordsDisplay = "";
                                    for (int d = 0; d < elementCoords.length; d++) {
                                        if (d + 1 == elementCoords.length) {
                                            coordsDisplay += String.format("%,d",elementCoords[d]);
                                        } else {
                                            coordsDisplay += String.format("%,d",elementCoords[d]) + ", ";
                                        }
                                    }
                                    System.out.printf("[i] Your element's coordinates are %s.%n", coordsDisplay);
                                    doubleLine();

                                    // and calculate and display the memory address
                                    System.out.printf("[i] Your element's address in memory is %,d bytes.%n",
                                            getMemoryAddress(arrayDimensions, elementCoords, alpha, epsilon));
                                            System.out.println("Press any key to continue...");
                                            System.in.read();
                                    }
                                }
                            }
                        } // holy mother of nested if-else
                    }
                }
            } else if (chock.equals("2")) {

                for (int i = 0; i < 1; i++) {
                    clearConsole();
                    line();
                    System.out.print("Input the primitive data types that make up your Record as comma-separated values:\n  > ");
                    String recordTypes = inp.nextLine();

                    int[] arrayDimensions;
                    int[] elementCoords;
                    int alpha;

                    int epsilon = getRecordSize(recordTypes); 

                    System.out.printf("[i] The size (ε) of each record is %,d bytes.%n", epsilon);

                    // after that it's pretty much just the same as with the arrays, just without the epsilon part bc that's already done beforehand
                    line();
                    System.out.print("Input the dimensions of the record array as comma-separated values: \n  > "); // prompt user input...
                    String arrayInp = inp.nextLine(); // process it...

                    // intsParser() checks if the input is a valid sequence of ints. if not,
                    // reprompt...
                    if (!intsParser(arrayInp)) {
                        System.out.println("[!] Invalid input! Say it again?");
                        System.out.println("Press any key to continue...");
                                            System.in.read();
                        i--;
                    } else {
                        arrayDimensions = intsDecoder(arrayInp);
                        String dimsDisplay = "";
                        for (int d = 0; d < arrayDimensions.length; d++) {
                            if (d + 1 == arrayDimensions.length) {
                                dimsDisplay += String.format("%,d",arrayDimensions[d]);
                            } else {
                                dimsDisplay += String.format("%,d",arrayDimensions[d]) + " * ";
                            }
                        }
                        System.out.printf("[i] Your array's dimensions are %s.%n", dimsDisplay);
                        System.out.printf("[i] Your array has a total of %,d records.%n", getTotalProduct(arrayDimensions));


                            line();
                            System.out.print("Input the starting address in memory (in bytes) of the array:\n  > ");
                            String alphaTemp = inp.nextLine();
                            if (intParser(alphaTemp) == -1) {
                                System.out.println("[!] Invalid input! Say it again?");
                                System.out.println("Press any key to continue...");
                                            System.in.read();
                                i--;
                            } else {
                                alpha = intParser(alphaTemp);
                                System.out.printf("[i] Starting address (α): %,d bytes%n", alpha);

                                line();
                                System.out.print(
                                        "Input the coordinates of the record in the array as comma-separated values: \n  > ");
                                String coordsInp = inp.nextLine();

                                if (intsDecoder(coordsInp)[0] == -1) {
                                    System.out.println("[!] Invalid input! Say it again?");
                                    System.out.println("Press any key to continue...");
                                            System.in.read();
                                    i--;
                                } else {
                                    elementCoords = intsDecoder(coordsInp);
                                    boolean outOfBounds = false;
                                    for(int k = 0; k < elementCoords.length; k++) {
                                        if(elementCoords[k] >= arrayDimensions[k]) {
                                            outOfBounds = true; break;
                                        }
                                    }
                                    if(outOfBounds) {
                                        System.out.println("[!] Index out of bounds! Say it again?");
                                        System.out.println("Press any key to continue...");
                                            System.in.read(); 
                                        i--;
                                    } else {
                                        String coordsDisplay = "";
                                    for (int d = 0; d < elementCoords.length; d++) {
                                        if (d + 1 == elementCoords.length) {
                                            coordsDisplay += String.format("%,d",elementCoords[d]);
                                        } else {
                                            coordsDisplay += String.format("%,d",elementCoords[d]) + ", ";
                                        }
                                    }
                                    System.out.printf("[i] Your record's coordinates are %s.%n", coordsDisplay);
                                    doubleLine();
                                    System.out.printf("[i] Your record's address in memory is %,d bytes.%n",
                                            getMemoryAddress(arrayDimensions, elementCoords, alpha, epsilon));
                                            System.out.println("Press any key to continue...");
                                            System.in.read();
                                    }
                                }
                        } // holy mother of nested if-else
                    }
                }

            } else if (chock.equals("X")) {
                break;
            }

        } while (true);
    }
}
