import java.lang.*;
import java.util.Arrays;

public class DSA_01SAC2 {

    final static int arrsize = 1000;

    public static int pigeonhole_sort(int arr[], int n) {

        int count = 0;
        int min = arr[0]; count++;
        int max = arr[0]; count++;
        int range, i, j, index; count++;
 
        for (int a = 0; a < n; a++) {
            if (arr[a] > max)
                max = arr[a]; count++;
            if (arr[a] < min)
                min = arr[a]; count++;
        }
 
        range = max - min + 1; count++;
        int[] phole = new int[range]; count++;
        Arrays.fill(phole, 0); count++;
 
        for (i = 0; i < n; i++)
            phole[arr[i] - min]++; count++;
 
        index = 0; count++;
 
        for (j = 0; j < range; j++)
            while (phole[j]-- > 0)
                arr[index++] = j + min; count++;
        
        return count;
    }

    static int[] getMax(int arr[], int n) { 
        int mx = arr[0]; 
        int count = 0;
        for (int i = 1; i < n; i++) 
            if (arr[i] > mx) 
                mx = arr[i]; count++;

        int[] returns = {mx, count};
        return returns; 
    } 

    // A function to do counting sort of arr[] according to 
    // the digit represented by exp. 
    static int countSort(int arr[], int n, int exp) { 
        int accesses = 0;
        int output[] = new int[n];accesses++; // output array 
        int i; accesses++;
        int count[] = new int[10]; accesses++;
        Arrays.fill(count,0); accesses++;

        // Store count of occurrences in count[] 
        for (i = 0; i < n; i++) 
            count[ (arr[i]/exp)%10 ]++; accesses++;

        // Change count[i] so that count[i] now contains 
        // actual position of this digit in output[] 
        for (i = 1; i < 10; i++) 
            count[i] += count[i - 1]; accesses++;

        // Build the output array 
        for (i = n - 1; i >= 0; i--) 
        { 
            output[count[ (arr[i]/exp)%10 ] - 1] = arr[i]; accesses++;
            count[ (arr[i]/exp)%10 ]--; accesses++;
        } 

        // Copy the output array to arr[], so that arr[] now 
        // contains sorted numbers according to current digit 
        for (i = 0; i < n; i++) 
            arr[i] = output[i]; accesses++;

        return accesses;
    } 

    // The main function to that sorts arr[] of size n using 
    // Radix Sort 
    static int radixSort(int arr[], int n) { 
        // Find the maximum number to know number of digits 

        int count = 0;

        int m = getMax(arr, n)[0];
        count += getMax(arr, n)[1];

        // Do counting sort for every digit. Note that instead 
        // of passing digit number, exp is passed. exp is 10^i 
        // where i is current digit number 
        for (int exp = 1; m/exp > 0; exp *= 10) 
            count += countSort(arr, n, exp); 
        
        return count;
    } 

    public static void main(String[] args) {

        System.out.println("==================================================");

        

        System.out.println(">> ARRAY SIZE: " + arrsize + " <<");
        System.out.println("OPERATION COUNTS:");

        int[] ascendingArray = new int[arrsize];
        int[] descendingArray = new int[arrsize];
        int[] randArray = new int[arrsize];
        for(int i = 0; i < arrsize; i++){
            ascendingArray[i] = i+1;
            descendingArray[i] = arrsize-i;
            randArray[i] = (int)(Math.random()*arrsize);
        }

        int[][] testSubjects = {ascendingArray, descendingArray, randArray};

        int[][] accessCounts = new int[2][3];

        for (int i = 0; i < 2; i++) {
            accessCounts[0][i] = radixSort(testSubjects[i], testSubjects[i].length);
            accessCounts[1][i] = pigeonhole_sort(testSubjects[i], testSubjects[i].length);
        }

        String displayFormat = " %-28s%12d%n";

        for (int i = 0; i < 2; i++) {
            System.out.println("--------------------------------------------------");
            switch(i) {
                case 0 -> System.out.println("# RADIX SORT BASE 10");
                case 1 -> System.out.println("# PIGEONHOLE SORT");
                default -> System.out.println("");
            }

            System.out.printf(
                displayFormat,
                " >> 1-" + arrsize+ " Ascending Array:",
                accessCounts[i][0]
            );

            System.out.printf(
                displayFormat,
                " >> 1-" + arrsize+ " Descending Array:",
                accessCounts[i][1]
            );

            System.out.printf(
                displayFormat,
                " >> 1-" + arrsize+ " Jumbled Array:",
                accessCounts[i][1]
            );
        }

        System.out.println("==================================================");
        
    }
}
