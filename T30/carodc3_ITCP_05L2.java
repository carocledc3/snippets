// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, CITCS-1L
// CC2/Introduction to Computer Programming
// [30IP-05L2] Final Laboratory Challenge №2
// 2 NOVEMBER 2024

/*
>> WEATHER DATA TRACKER <<

OBJECTIVE: Track daily temperature data for multiple cities and analyze the temperature trends.

SCENARIO: You are working for a meteorological department that tracks the daily temperatures for 3 cities over a week (7 days). You need to store the temperature data, calculate the average temperature for each city, and find the highest and lowest temperature recorded during the week for each city.

TASK:
    - Use a two-dimensional array to store the daily temperature readings for 3 cities over 7 days.
    - Calculate the average temperature for each city.
    - Find the highest and lowest temperature recorded for each city.
 */

import java.util.Scanner;
public class carodc3_ITCP_05L2 {

    static void line() { System.out.println("-------------------------------------------------------------------------"); } 
    static void doubleLine() { System.out.println("========================================================================="); }
    // lines for æsthetic purposes

    static final int trackableDays = 7; // number of days to be tracked. can be adjusted

    // method to get the average of a double[] array
    static double getAverage(double[] input) {
        double avg = 0.0; // initial value
        for (int i = 0; i < input.length; i++) {
            avg += input[i] / input.length;
            // alternate method of getting the arithmetic mean
            // works because (α+β+γ+δ+ε+...)/n = (α/n)+(β/n)+(γ/n)+(δ/n)+(ε/n)+...
        }
        return avg;
    }

    // method to get the maximum and minimum of a double[] array
    static double[] getMaxAndMin(double[] input) {
        double max = input[0]; // initial values...
        double min = input[0];
        for (int i = 0; i < input.length; i++) { // for each item in the input array...
            max = input[i] > max ? input[i] : max; // assign it to the maximum if greater than the current value of max
            min = input[i] < min ? input[i] : min; // assign it to the minimum if lesser than the current value of min
        }
        double[] extremes = {max, min}; // array extremes will consist of the maximum and the minimum
        return extremes;
    }

    // string splitter; thanks https://www.w3schools.com/java/ref_string_split.asp
    static String[] commaSeparate(String applejuice) {
        return applejuice.split(",\s*"); // splits String into String[] array with commas (and any amount of whitespace) as delimiter
    }

    public static void main(String[] args) {

        String[] cities; // array of city names
        
        Scanner inp; // declare scanner object
        System.out.println("CITY TEMPERATURE STATISTICS LOGGER");
        doubleLine();
        System.out.print("Enter names of cities, comma-separated:\n  > "); // prompt input of city names
        inp = new Scanner(System.in); // input cities...
        cities = commaSeparate(inp.nextLine()); // and separate them into an array
        
        // table of temperatures
        // number of rows == how many cities there are
        // number of columns == number of trackable days 
        double[][] temperatures = new double[cities.length][trackableDays];

        for(int i = 0; i < cities.length; i++) { // main loop to get city entries. this works for any number of cities due to the way i set up the comma separation

            System.out.printf("Enter temperatures of %s for %d days in °C, comma-separated:%n  > ", cities[i], trackableDays);
            // input temperatures for each city...
            
            String[] tempsAsStrings = commaSeparate(inp.next()); // and separate them into an array, as a string first so we can parse each into a double

            if(tempsAsStrings.length != trackableDays) { // check first if entry length matches number of days
                    System.out.printf("Input must be %s entries long!%n", trackableDays); // if not, prompt reentry
                    i--;
            } else {
                try {
                for (int j = 0; j < tempsAsStrings.length; j++) {
                     // check if inputs are valid...
                        temperatures[i][j] = Double.parseDouble(tempsAsStrings[j]); // thanks https://www.geeksforgeeks.org/double-parsedouble-method-in-java-with-examples/
                    } } catch (NumberFormatException ie) { // if someone has a brave idea and tries to input things that are not numbers, this will run:
                            System.out.println("Invalid input! Temperatures must be numbers.");
                            i--; // prompt reentry if temperatures are not numbers
                    } 
                }
            }
            doubleLine();
            inp.close(); // close scanner

            double[] maximums = new double[cities.length]; // declare array for maximums
            double[] minimums = new double[cities.length]; // declare array for minimums
            double[] averages = new double[cities.length]; // declare array for averages

            for (int i = 0; i < cities.length; i++) {
                // fill the arrays for each city..
                // since temperatures[][] is a 2D array, temperatures[i] means each 1D array inside temperatures[][]
                maximums[i] = getMaxAndMin(temperatures[i])[0];
                minimums[i] = getMaxAndMin(temperatures[i])[1];
                averages[i] = getAverage(temperatures[i]);
            }

            String headerFormat = "%-16s%12s%12s%12s%n"; // header formatting
            String displayFormat = "%-16s %8.2f °C %8.2f °C %8.2f °C%n"; // formatting for temperature display
            

            System.out.println("TEMPERATURE STATISTICS");
            System.out.printf(
                headerFormat,
                "City", "Maximum", "Minimum", "Average" // print header format...
            );
            line();
            for (int i = 0; i < cities.length; i++) { // for each city...
                System.out.printf(
                    displayFormat, // print its name, max, min, and avg. temps; formatted
                    cities[i], maximums[i], minimums[i], averages[i]
                );
            }
        }
    }
