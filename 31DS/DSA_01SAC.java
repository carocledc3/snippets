// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// with: NOTO, CLAVIO, BERSALONA, BASILIO

// UNIVERSITY OF THE CORDILLERAS, CITCS-1F
// 31DS/CC4: DATA STRUCTURES AND ALGORITHMS
// [31DS-01L1] LABORATORY ACTIVITY 1
// 17 JAN 2024 F

// THIS CODE IS NOT TO BE SUBMITTED

// code to test efficiency of data sorting algorithms. code copied from GeeksForGeeks, edited to increment a counter for every operation they do. if you want to edit the size of the arrays, edit the variable 'arrsize' at line 15. don't bother editing anything else. just run the file

import java.util.Arrays;

public class DSA_01SAC {

    // THIS IS THE ONLY PART YOU SHOULD BOTHER EDITING. this variable sets the size of the arrays to be (automatically filled and) sorted later on
    final static int arrsize = 1000;

// ---------------------------------------------------------------------------------
// ABANDON HOPE ALL YE WHO ENTER HERE
// ---------------------------------------------------------------------------------

    static int[][] accessCounts = new int[5][3];

    final static int insertionSort(int[] a) {
        int count = 0;
        // thanks https://www.geeksforgeeks.org/java-program-for-insertion-sort/
            int n = a.length; count++;
            for (int i = 1; i < n; ++i) {
                int k = a[i]; count++;
                int j = i - 1; count++;
                // Move elements of arr[0..i-1], that are
                // greater than key, to one position ahead
                // of their current position
                while (j >= 0 && a[j] > k) {
                    a[j + 1] = a[j]; count++;
                    j = j - 1; count++;
                }
                a[j + 1] = k; count++;
        }
        return count;
    }

        // Merges two subarrays of a[]
        static int merge(int a[], int l, int m, int r) {
            int count = 0;
            int n1 = m - l + 1; count++;
            int n2 = r - m; count++;
            int L[] = new int[n1]; count++;
            int R[] = new int[n2]; count++;

            for (int i = 0; i < n1; ++i)
                L[i] = a[l + i]; count++;
    
            for (int j = 0; j < n2; ++j)
                R[j] = a[m + 1 + j]; count++;
    
            // Merge the temp arrays
            // Initial indexes of first and second subarrays
            int i = 0, j = 0; count++;
    
            int k = l; count++;
            while (i < n1 && j < n2) {
                if (L[i] <= R[j]) {
                    a[k] = L[i]; count++;
                    i++; count++;
                }
                else {
                    a[k] = R[j]; count++;
                    j++; count++;
                }
                k++; count++;
            }
    
            while (i < n1) {
                a[k] = L[i]; count++;
                i++; count++;
                k++; count++;
            }
    
            while (j < n2) {
                a[k] = R[j]; count++;
                j++; count++;
                k++; count++;
            }
            return count;
        }
    
        // Main function that sorts a[l..r] using
        // merge()
        static int mergeSort(int a[], int l, int r) {
            int count = 0;
            if (l < r) {
                int m = (l + r) / 2; count++;
                // Sort first and second halves
                count += mergeSort(a, l, m); count++;
                count += mergeSort(a, m + 1, r); count++;
                // Merge the sorted halves
                count += merge(a, l, m, r); count++;
            }
            return count;
        }

        // # HEAP SORT
        // thanks https://www.geeksforgeeks.org/java-program-for-heap-sort/
        static int heapSort(int arr[]) {

        int count = 0;
        int n = arr.length; count++;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            count += heapify(arr, n, i); count++;

        // One by one extract an element from heap
        for (int i = n - 1; i >= 0; i--) {
            // Move current root to end
            int temp = arr[0]; count++;
            arr[0] = arr[i]; count++;
            arr[i] = temp; count++;

            // call max heapify on the reduced heap
            count += heapify(arr, i, 0); count++;
        }

        return count;
    }

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    static int heapify(int arr[], int n, int i) {
        int count = 0;

        int largest = i; count++; // Initialize largest as root
        int l = 2 * i + 1; count++; // left = 2*i + 1
        int r = 2 * i + 2; count++; // right = 2*i + 2

        // If left child is larger than root
        if (l < n && arr[l] > arr[largest])
            largest = l; count++;

        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest])
            largest = r; count++;

        // If largest is not root
        if (largest != i) {
            int swap = arr[i]; count++;
            arr[i] = arr[largest]; count++;
            arr[largest] = swap; count++;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest); count++;
        }
        return count;
    }

    static int[] partition(int a[], int low, int high) {

        int count = 0;

        int pivot = a[high]; count++;
        int i = (low-1); count++;
        for (int j=low; j<high; j++)
        {
    
            // If current element is smaller than or
            // equal to pivot
            if (a[j] <= pivot)
            {
                i++; count++;

                int temp = a[i]; count++;
                a[i] = a[j]; count++;
                a[j] = temp; count++;
            }
        }

        int temp = a[i+1]; count++;
        a[i+1] = a[high]; count++;
        a[high] = temp; count++;

        int[] returns = {i+1, count};
        return returns;
    }

    static int quickSort(int a[], int l, int h) {
        int count = 0;
        if (l < h)
        {
            int pi = partition(a, l, h)[0];
            count += partition(a, l, h)[1];

            // Recursively sort elements before
            // partition and after partition
            count += quickSort(a, l, pi-1);
            count += quickSort(a, pi+1, h);
        }
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

        int[][] accessCounts = new int[5][3];

        for (int i = 0; i < 3; i++) {
            accessCounts[0][i] = insertionSort(testSubjects[i]);
            accessCounts[1][i] = mergeSort(testSubjects[i], 0, testSubjects[i].length-1);
            accessCounts[2][i] = heapSort(testSubjects[i]);
            accessCounts[3][i] = quickSort(testSubjects[i], 0, testSubjects[i].length-1);
            accessCounts[4][i] = radixSort(testSubjects[i], testSubjects[i].length);
        }

        String displayFormat = " %-28s%12d%n";

        for (int i = 0; i < 5; i++) {
            System.out.println("--------------------------------------------------");
            switch(i) {
                case 0 -> System.out.println("# INSERTION SORT");
                case 1 -> System.out.println("# MERGE SORT");
                case 2 -> System.out.println("# HEAP SORT");
                case 3 -> System.out.println("# QUICK SORT");
                case 4 -> System.out.println("# RADIX SORT BASE 10");
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
