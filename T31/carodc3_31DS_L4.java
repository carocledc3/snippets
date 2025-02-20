// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, CITCS-1F
// CC4/DATA STRUCTURES AND ALGORITHMS
// [31DS-L4] Laboratory Activity 4: Evaluation of Expressions
// 16 February 2025

// TODO: Add simulation/evaluation code

import java.util.ArrayList;
import java.util.Scanner;

// custom Stack class just so i can override the print method
// the <E extends Object> thing is so it can accept any data type
class Stack<E extends Object> {

    ArrayList<E> core = new ArrayList<E>(); // arraylist as core

    public E peek() { return core.getLast(); } // peek at top

    public E pop() { E obj = core.getLast(); core.removeLast(); return obj; } // pop from top

    public void push(E obj) { core.addLast(obj);  } // push to top

    public boolean isEmpty() { return core.isEmpty(); } // check if empty

    public int size() { return core.size(); } // check size

    // override print result, thanks https://stackoverflow.com/questions/60113812/overriding-system-out-println
    @Override
    public String toString() {
        String contents = "";
        for (E elem : core) { contents += elem + " "; }
        return contents;
    }
}

public class carodc3_31DS_L4 {

    
    
    // lines for æsthetic purposes
    final static void line() {
        System.out.println("------------------------------------------------------------");
    }

    final static void doubleLine() {
        System.out.println("============================================================");
    }

    public static String ANSI_RESET = "\u001B[0m";
    public static String ANSI_RED = "\u001B[31m";
    public static String RED_HL= ANSI_RED + "%s" + ANSI_RESET;

    // method to detect if character is an operand or not
    static boolean isOperator(char input) {
        String operands = "+-*/%^";
        if(operands.contains( Character.toString(input))) { return true; } else {return false;}
    }

    // method for figuring out the priority of an operand
    static int priority(char input) {
        String inp = Character.toString(input);
        if( !isOperator(input) ) return -1;
        
        
        else if ( "^".contains(inp) ) return 3; // ^ highest
        else if ( "*/%".contains(inp)) return 2; // * / % higher
        else if ( "+-".contains(inp)) return 1; // + -  at lowest
        // effectively PEMMDAS (Parentheses, Exponent, Modulo, Multiplication, Division, Addition, Subtraction)
        else return 0;
    }

    // check what kinda operand it is
    static int isValidOperand(String term) {
        if(term.matches("\\d+")) return 1; // digit
        else if(term.matches("\\w+")) return 2; // letter
        else if(term.matches("\\d+\\w+") ) return 3; // digit+letter
        else return 0; // not an operand
    }

    // Integer.parseInt with an exception silencer. invalid values return 0
    static int intParse(String input) { try { return Integer.parseInt(input); } catch (Exception e) { return 0; } }

    // method to convert infix to postfix
    static String postfixConverter(String input) {
        // stack of operators
        Stack<Character> operators = new Stack<Character>();

        // output string
        String postfix = ""; 

        // display formatting
        String displayFormat = "%-6s %-24s %-48s%n";

        // to start, push # (meaning ‹nothing follows› )to the stack
        operators.push('#');

        // print headers
        doubleLine();
        System.out.println(String.format(RED_HL, ">> CONVERSION <<"));
        System.out.println("INPUT EXPRESSION (INFIX): " + ANSI_RED + input + ANSI_RESET);
        doubleLine();
        System.out.printf(displayFormat, "TOKEN", "STACK", "OUTPUT");
        line();
        char[] tokens = input.toCharArray();

        // for each character...
        for (int k = 0; k < input.length(); k++) {

            // set char c as current character
            char c = tokens[k];

            // skip if character is a space
            if( Character.isSpaceChar(c) ) { continue; }
            
            // if the character is a letter or a digit, then it's an operand; add it to the result string
            if( Character.isLetterOrDigit(c) ) {
                if(k+1 < input.length() && Character.isLetterOrDigit( tokens[k+1] ) ) {
                    // if the next character is also a letter or a digit, then it's a multicharacter term; don't add a space after
                    postfix += c;
                } else {
                    // else, add a space after the term
                    postfix += c + " ";
                }
                
            }
            // if the character is ( , push it into the operators stack
            else if (c == '(') { operators.push(c); }

            // if it's ) , pop everything in the operators stack until it reaches the ( , then pop the (
            else if (c == ')') {
                while(operators.peek() != '(') {postfix += operators.pop() + " "; }
                operators.pop();
            }

            // else...
            else {
                // pop to the result string: every operator in the stack that has a higher or equal priority than the current operator
                while (!operators.isEmpty() && ( priority(operators.peek()) >= priority(c) )) {
                    postfix += operators.pop() + " ";
                }
                // then push the current operator to the stack
                operators.push(c);
            }
            // and finally display the current derivation
            System.out.printf(displayFormat, c, operators, postfix);
            
        }
        
        // after all that, pop everything to the result string until the # is reached 
        while(operators.peek() != '#') { postfix += operators.pop() + " "; }

        // then display that step
        System.out.printf(displayFormat, '#', operators, postfix);
    
        // display the final result
        doubleLine();
        System.out.println("FINAL POSTFIX EXPRESSION: " + ANSI_RED + postfix + ANSI_RESET);
        return postfix;
    }

    // split String into string array with space as delimiter
    static String[] spaceSeparate(String applejuice) {
        return applejuice.split("\s+");
    }

    static String postfixEvaluator(String input) {

    doubleLine();
    System.out.println(String.format(RED_HL, ">> SIMULATION <<"));
    System.out.println( "  = " + input);
        // stack of evaluated stuff
        Stack<Integer> evalStack = new Stack<Integer>();

        // separate array into elements, separated by spaces
        String[] evalArray = spaceSeparate(input);
        
        // check for compound terms
        for ( String h : evalArray) {
            if (!isOperator(h.charAt(0)) && isValidOperand(h) != 1) {
                System.out.println(String.format(RED_HL, "[!]")+ " Expressions with compound terms cannot be evaluated!\n");
                return "";
            }
        }

        // for each element...
        for( int k = 0; k < evalArray.length; k++ ) {
            // store current token as String
            String current = evalArray[k];

            // if operand is detected
            if (isValidOperand(current) == 1) { evalStack.push(intParse(current)); }

            // if operator is detected
            else if (k > 1 && isOperator(current.charAt(0))) {

                // get operators and operands
                char operator = current.charAt(0);
                int operand2 = evalStack.pop();
                int operand1 = evalStack.pop();
                
                // display current calculation
                System.out.println("  = " + input.replace(
                    String.format("%s %s %s", operand1, operand2, operator),
                    String.format(String.format(RED_HL, "%s %s %s"), operand1, operand2, operator)
                ));

                // apply operations:
                switch(operator) {
                    case '+' -> { evalStack.push((operand1 + operand2));}
                    case '-' -> { evalStack.push((operand1 - operand2));}
                    case '*' -> { evalStack.push((operand1 * operand2));}
                    case '/' -> { evalStack.push((operand1 / operand2));}
                    case '%' -> { evalStack.push((operand1 % operand2));}
                    case '^' -> {
                        Double exp = Math.pow(operand1, operand2);
                        evalStack.push((exp.intValue()));
                    }
                }
                
                // modify current calculation display
                input = input.replace(
                String.format("%s %s %s", operand1, operand2, operator),
                String.format("%s", String.valueOf(evalStack.peek()))
                );
            } 
        }
        // display and return final result
        System.out.println("  = " +  String.format(RED_HL, evalStack.peek()));
        return evalStack.toString();
    }

    static void processAndEvalInfix(String input) {
        postfixEvaluator(postfixConverter(input));
    }

    public static void main(String[] args) {

        Scanner inp = new Scanner(System.in);
        // main app loop...
        do {
            // prompt user input...
            System.out.print("Input infix-form expression, or 'exit': \n  > ");
            String input = inp.nextLine();

            // if user inputs ‹exit›, then exit
            if(input.toUpperCase().equals("EXIT")) { break; }

            // if not, process the input
            processAndEvalInfix(input);

        } while( true );
    }
}
