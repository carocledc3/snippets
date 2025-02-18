/// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, CITCS-1F
// CC4/DATA STRUCTURES & ALGORITHMS
// [31DS-L3] Laboratory Exercise #3 
// 7 Feb 2025

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.Scanner;


// decided to define a custom type for both of them so i can have custom Display methods
class CharStackOrQueue {

    // stacks and queues are essentially lists at their core so i decided to extend them from the same class
    ArrayList<Character> list = new ArrayList<Character>();

    // display method. lists the members of the list (and their indices) in a horizontal list
    void display(boolean isQueue) {

        // strings for members and indices
        String charsDisplay = "";
        String indicesDisplay = "";

        for(int i = 0; i < list.size(); i++) {
            // append the actual members of the list to the display strings...
            if((!isQueue && i == (list.size()-1)) || (isQueue && i == 0)) {
                // last element in a stack and the first element in a queue get marked in [brackets] to indicate that they are the first to be popped
                charsDisplay += String.format("[%3s]", list.get(i));
                indicesDisplay += String.format("[%3d]", i);
            } else {
                charsDisplay += String.format(" %3s ", list.get(i));
                indicesDisplay += String.format(" %3d ", i);
            }
        }
        // if empty, display error message. else, display the contents
        if(length() == 0) {
            System.out.println(" [!] List is empty!");
        } else {
            System.out.println(charsDisplay);
            System.out.println(indicesDisplay);
        }
    }

    void command() {}
    void clear() {list.clear();}

    // derivable attributes 
    int length() {return list.size();}
    
}


// stack class
class CharStack extends CharStackOrQueue {

    // push and pop (add and remove) methods. pop() appends to the end of the stack, and push() removes from the same end
    Character pop() {Character inq = list.getLast(); list.removeLast(); return inq;}
    void push(Character inp) {list.addLast(inp);}
    

    // input processor.
    // if the user inputs a "+", then every character after the + will be added to the stack.
    // for example: "+pingas" will mean that 'p' 'i' 'n' 'g' 'a' 's' will be added to the stack
    // if the user inputs a "-", then elements will be popped from the stack, corresponding to the number of dashes
    void command(String input) throws InterruptedException {
        if(input.startsWith("+")) {
            for(int i = 1; i < input.length(); i++) {
                push(input.charAt(i));
            }
        } else if (input.startsWith("-")) {
            System.out.printf("[i] Character '%s' popped!%n", pop()); Thread.sleep(500);
            for(int i = 1; i < input.length(); i++) {
                if(input.charAt(i) == '-') { System.out.printf("[i] Character '%s' popped!%n", pop()); Thread.sleep(500); };
            }
        } else if (input.toLowerCase().equals("clear")) {
            // if user inputs 'clear', clear the whole thing
            clear();
            System.out.println("[i] List cleared!"); Thread.sleep(1000);
        }
    }

}

class CharQueue extends CharStackOrQueue {

    // enqueue & dequeue (add and remove) methods. enqueue() appends to the end of the stack, and dequeue() removes from the start
    Character dequeue() {Character inq = list.getFirst(); list.removeFirst(); return inq;}
    void enqueue(Character inp) {list.addLast(inp);}

    // input processor.
    // if the user inputs a "+", then every character after the + will be added to the queue.
    // for example: "+pingas" will mean that 'p' 'i' 'n' 'g' 'a' 's' will be added to the queue
    // if the user inputs a "-", then elements will be dequeued from the queue, corresponding to the number of dashes
    void command(String input) throws InterruptedException {
        if(input.startsWith("+")) {
            for(int i = 1; i < input.length(); i++) {
                enqueue(input.charAt(i));
            }
        } else if (input.startsWith("-")) {
            System.out.printf("[i] Character '%s' dequeued!%n", dequeue()); Thread.sleep(500);
            for(int i = 1; i < input.length(); i++) {
                if(input.charAt(i) == '-') { System.out.printf("[i] Character '%s' dequeued!%n", dequeue()); Thread.sleep(500); };
            }
        } else if (input.toLowerCase().equals("clear")) {
            // if user inputs 'clear', clear the whole thing
            clear();
            System.out.println("[i] List cleared!"); Thread.sleep(1000);
        }
    }
}


public class carodc3_31DS_L3 {

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

    public static void main(String[] args) throws InterruptedException {
        // declare stacks and queues that the app will use
        CharStack stackOne = new CharStack();
        CharQueue queueOne = new CharQueue();
        // scanner object for user inputs
        Scanner inp = new Scanner(System.in);

        // main app loop...
        do { 
            clearConsole();
            doubleLine();
            System.out.println("STACK AND QUEUE SIMULATOR");
            doubleLine();
            System.out.println("  [1] Stack Simulator\n  [2] Queue Simulator\n  [X] Exit");
            System.out.print("> Enter choice: ");
            String choice = inp.nextLine();
            line();
            if(choice.equals("1")) {
                do {
                    clearConsole();
                    // print headers then display contents...
                    System.out.println(">> STACK of characters <<");
                    line();
                    stackOne.display(false);
                    line();
                    System.out.print("> Enter command: "); // prompt user input...
                    String command = inp.nextLine();
                    // command "X" or "EXIT" to exit to main menu
                    if(command.toUpperCase().equals("X") || command.toUpperCase().equals("EXIT")) {
                        break;
                    } else {
                        stackOne.command(command);
                    }
                
                } while(true);
            } else if (choice.equals("2")) {
                do {
                    clearConsole();
                    // print headers then display contents...
                    System.out.println(">> QUEUE of characters <<");
                    line();
                    queueOne.display(true);
                    line();
                    System.out.print("> Enter command: "); // prompt user input...
                    String command = inp.nextLine();
                    // command "X" or "EXIT" to exit to main menu
                    if(command.toUpperCase().equals("X") || command.toUpperCase().equals("EXIT")) {
                        break;
                    } else {
                        queueOne.command(command);
                    }
                
                } while(true);
            } else if (choice.toUpperCase().equals("X")) {
                // command "X" to exit program
                break;
            }
        } while (true);
    }
}