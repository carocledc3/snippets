// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, BSIT-1L
// CC2/Introduction to Computer Programming
// [30IP-01L1] Laboratory Challenge №1
// 9 SEPTEMBER 2024
// All external code sources have been cited in comments.

/*
>> CREATING A SIMPLE CONSOLE-BASED INVOICE <<

- OBJECTIVE: To practice using `print`, `println`, tab characters (`\t)`, and escape sequences (`\n`, `\"`) in a single Java program

- ACTIVITY DESCRIPTION: In this activity, you will create a simple console-based invoice for a fictional store. The invoice should display the store's name, a list of purchased items with their quantities and prices, and the total amount due. You will use `print` and `println` to control the output format, tabs to align text, and escape sequences to format the output correctly.

- STEPS:
    1. STORE INFORMATION: Print the store's name and address at the top of the invoice. Use escape sequences (`\n`) to move to new lines and (`\"`) to include any quotation marks in the store's name.
    2. ITEMISED LIST: Display an itemized list of purchased items, using `\t` to align the item names, quantities, and prices neatly in columns. Each item should be on a new line.
    3. TOTAL AMOUNT: Print the total amount due at the bottom of the invoice, using a combination of `print` and `println` to format the output correctly.
    4. ADDITIONAL NOTES: Add a "Thank you" message at the bottom of the invoice, using escape sequences for formatting

*/

public class carodc3_ITCP_01L1 {
    // thanks https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    // text colouring; only works on linux according to source
        public static String ANSI_RESET = "\u001B[0m";
        public static String ANSI_BLACK = "\u001B[30m";
        public static String ANSI_RED = "\u001B[31m";
        public static String ANSI_GREEN = "\u001B[32m";
        public static String ANSI_YELLOW = "\u001B[33m";
        public static String ANSI_BLUE = "\u001B[34m";
        public static String ANSI_PURPLE = "\u001B[35m";
        public static String ANSI_CYAN = "\u001B[36m";
        public static String ANSI_WHITE = "\u001B[37m";
        public static String LINE = "--------------------------------------------------------";
    public static void main(String[] args) {
        System.out.println(ANSI_BLUE + "Aspire Stationery Co.\n" + ANSI_CYAN + "720 Via Sáint-Stivan\n" + ANSI_RESET + "Bluebanks, Medleshire, Sevdenia 2456-10");
        System.out.println();
        System.out.println("Invoice, Date: " + ANSI_CYAN + "9 SEP 2037" + ANSI_RESET);
        System.out.println("");
        System.out.println(ANSI_RED + "Item \t \t \t Quantity \tTotal Price" + ANSI_RESET);
        System.out.println(LINE);
        System.out.println("Mongol Pencil, No.2 \t 560 \t \t1,960 kr.");
        System.out.println("Yellow Pad \t \t 200 \t \t1,034 kr.");
        System.out.println("Gel Pen, Black \t \t 2,000 \t \t8,420 kr.");
        System.out.println(LINE);
        System.out.println(ANSI_YELLOW + "Total Amount Due: \t \t \t11,414 kr." + ANSI_RESET);
        System.out.println(ANSI_CYAN + "Payment Due Date: \t \t \t" + "9 OCT 2037" + ANSI_RESET);
        System.out.println(LINE);
        System.out.println("Thank you for choosing "+ ANSI_BLUE +  "Aspire Stationery Co."+ ANSI_RESET + "!");
    }

    
}