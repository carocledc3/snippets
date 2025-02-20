import java.util.Scanner;

// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, BSIT-1L
// CC2/Introduction to Computer Programming
// [30IP-01L2] Laboratory Challenge №2
// 16 SEPTEMBER 2024
// CODED RIGHT HERE IN M303

/*
>> CALCULATING THE AREA AND PERIMETER OF A RECTANGLE <<

- PROBLEM STATEMENT: You are tasked with helping a construction company estimate the materials needed to build a rectangular garden. The company needs to know the area and perimeter of the garden based on its fixed dimensions. Write a Java program to calculate and display the area and perimeter of the rectangle using predefined values for the length and width.

1. GIVEN VALUES:
    - The length of the garden is ___ meters;
    - The width of the garden is ___ meters.
2. CALCULATIONS:
    - The area of a rectangle is calculated as Area = length * width.
    - The perimeter of a rectangle is calculated as Perimeter = 2 * (length + width).
3. OUTPUT:
    - Display the length, width, area, and perimeter of the garden.
 */

public class carodc3_ITCP_01L2 {
    public static String hr = "-----------------------------------------------";
    public static void phr() {System.out.println(hr);}
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        // TITLE
        System.out.println("Garden Dimensions Calculator");
        phr();
        // ENTER LENGTH
        System.out.print("Enter garden length in metres: ");
        Scanner readLength = new Scanner(System.in);
        int length = readLength.nextInt(); 
        // ENTER WIDTH
        System.out.print("Enter garden width in metres: ");
        Scanner readWidth = new Scanner(System.in);
        int width = readWidth.nextInt(); 

        phr();

        int gardenArea = length * width; // 60
        int gardenPerimeter = 2 * (length + width);
        
        System.out.println("Garden length: " + length + " metres");
        System.out.println("Garden width: " + width + " metres");
        System.out.println("Garden area: " + gardenArea + " square metres");
        System.out.println("Garden perimeter: " + gardenPerimeter + " metres");

    }
}
