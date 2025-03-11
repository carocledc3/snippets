// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, CITCS-1F
// CC4/DATA STRUCTURES AND ALGORITHMS
// [31DS-L6] Laboratory Activity 5: AVL Trees
// 12 March 2025

// code from https://github.com/BerWalker/AVLTree-Java

// imports...
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// making node direction from parent a normalised Thing (for the sake of printing)
enum NodeDirection {
    root, left, right;
    @Override
    public String toString() {
        String str = "";
        switch (this) {
            case root -> {str = "root";}
            case left -> {str = "left";}
            case right -> {str = "right";}
        }
        return str;
    }
}

// define what a Node even is...
class Node {
    int key, index, height; // it has a key (data) and an index (position) and a height
    Node left, right; // it can have child nodes to the left and right
    boolean hasSibling; // boolean to check if the node has a sibling
    NodeDirection dir; // direction from parent

    // constructor
    public Node(int item) {
        key = item;
        index = 0; // initial index is 0. this will be updated by BinarySearchTree.updateIndices()
        height = 1;
        left = right = null; // no children by default
    }

    
}

class AVLTree {

    // colour hacks for printing
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static String ANSI_PINK = "\u001B[35m";
    public static String ANSI_WHITE = "\u001B[37m";
    public static String ANSI_BLUE = "\u001B[34m";
    public static String ANSI_CYAN = "\u001B[36m";
    public static String RED_HL = ANSI_RED + "%s" + ANSI_RESET;
    public static String WHITE_HL = ANSI_WHITE + "%s" + ANSI_RESET;
    public static String PINK_HL = ANSI_PINK + "%s" + ANSI_RESET;
    public static String CYAN_HL = ANSI_CYAN + "%s" + ANSI_RESET;
    public static String BLUE_HL = ANSI_BLUE + "%s" + ANSI_RESET;
    // lines for æsthetic purposes
    final static void line() {
        System.out.println("---------------------------------------------------------------------");
    }

    private Node root; // every tree is defined by its root
    private int selected; // which node is selected (for highlighting in insert & search operations)
    private int size = 0; // number of nodes in the tree
    private int lastIndex = 0;
    private int depth = 0;
    
    private int[] arrayRepr;
    private String indicesRepr = ""; // representation of indices and elements for 1D traversals
    private ArrayList<Integer> elements;
    private String elementsRepr = "";
    private String repr = ""; // representation for tree visualisation
    public String[] lastOperation = {String.format(WHITE_HL, "(none)"), ""}; // last operation done on the tree

    // constructor. blank tree by default
    public AVLTree() {
        root = null;
    }

    // Get maximum of two integers
    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    public Node getRoot() {
        return root;
    }

    public void insert(int value) {
        root = insertRecursive(root, value);
        lastOperation[0] = lastOperation[0] = "INSERT"; lastOperation[1] = value + "";
        updateIndices(); size += 1;
    }

    private Node insertRecursive(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }

        if (value < node.key) {
            node.left = insertRecursive(node.left, value);
        } else if (value > node.key) {
            node.right = insertRecursive(node.right, value);
        }

        return balance(node); // AVL Balancing
    }

    public void remove(int value) {
        root = removeRecursive(root, value);
        updateIndices();
        lastOperation[0] = "DELETE"; lastOperation[1] = value + "";
    }

    private Node removeRecursive(Node node, int value) {
        if (node == null) {
            return null;
        }

        if (value < node.key) {
            node.left = removeRecursive(node.left, value);
        } else if (value > node.key) {
            node.right = removeRecursive(node.right, value);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }

            // Replace the value being removed with the smallest value on the right
            Node maxValLeft = findMax(node.left); // Find the smallest value
            node.key = maxValLeft.key; // Replace the removed value with the smallest one

            // Remove the old position of the smallest value
            node.left = removeRecursive(node.left, maxValLeft.key);
        }

        return balance(node); // AVL Balancing
    }

    private Node findMax(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    // Search operation
    private boolean search(int key) {
        boolean found = searchRec(root, key); // run the recursive search operation and store the result in this boolean
        // update last operation
        lastOperation[0] = "SEARCH";
        lastOperation[1] = found ? key + " (true)" : key + " (false)";
        return found;
    }

    // recursive search operation
    private boolean searchRec(Node root, int key) {
        // traverse the tree in pre-order: check root first, then go left, then go right
        if (root == null) // if the key isn't found, return false
            return false;
        if (root.key == key) {
            // if the key is found, return true and update the selected value
            selected = key;
            return true;
        }
        if (root.key < key)
            return searchRec(root.right, key); // if the key being searched is more than the current node's key, then keep going right
        return searchRec(root.left, key); // else, go left
    }

    // AVL Tree Helper Methods

    private int height(Node node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    private int balanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    private Node balance(Node node) {
        int bf = balanceFactor(node);

        if (bf > 1) { // Left imbalance
            if (balanceFactor(node.left) < 0) {
                node.left = rotateLeft(node.left); // Left-right rotation (double right)
            }
            return rotateRight(node);
        }

        if (bf < -1) { // Right imbalance
            if (balanceFactor(node.right) > 0) {
                node.right = rotateRight(node.right); // Right-left rotation (double left)
            }
            return rotateLeft(node);
        }

        return node;
    }

    private Node rotateRight(Node grandparent) {
        Node parent = grandparent.left;
        Node temp = parent.right;

        parent.right = grandparent;
        grandparent.left = temp;

        return parent;
    }

    private Node rotateLeft(Node parent) {
        Node grandparent = parent.right;
        Node temp = grandparent.left;

        grandparent.left = (parent);
        parent.right = temp;

        return grandparent;
    }

    // Preorder traversal and printing. is called PRE-order because it goes root -> left -> right, with the root BEFORE the instructions to go left and right
    private void preorder() {
        elements = new ArrayList<Integer>();
        elementsRepr = ""; // initialise element text
        preorderRec(root); // run recursive pre-order traversal function...
        for(int k = 0; k < elements.size(); k++) {
            elementsRepr += !(k == elements.size() - 1) ? String.format(RED_HL, elements.get(k) + ", ") : String.format(RED_HL, elements.get(k) + "");
        }
        System.out.println("PREORDER TRAVERSAL: " + String.format("[%s]", elementsRepr)); // and print the results
    }

    // recursive pre-order traversal function
    private void preorderRec(Node root) {
        if (root != null) {
            // read root first...
            elements.add(root.key);
            preorderRec(root.left); // then go left
            preorderRec(root.right); // then go right
        }
    }

    // Inorder traversal and printing. is called IN-order because it goes left -> root -> right, with the root IN BETWEEN the instructions to go left and right
    private void inorder() {
        elements = new ArrayList<Integer>();
        // initialise indices and elements row labels
        elementsRepr = ""; // initialise element text
        inorderRec(root); // run recursive post-order traversal function...
        for(int k = 0; k < elements.size(); k++) {
            elementsRepr += !(k == elements.size() - 1) ? String.format(RED_HL, elements.get(k) + ", ") : String.format(RED_HL, elements.get(k) + "");
        }
        System.out.println("INORDER TRAVERSAL: " + String.format("[%s]", elementsRepr)); // and print the results
    }

    // recursive in-order traversal function
    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left); // go left first...
            // read root...
            elements.add(root.key);
            // then go right
            inorderRec(root.right);
        }
    }

    // Postorder traversal and printing. is called POST-order because it goes left -> right -> root , with the root AFTER the instructions to go left and right
    private void postorder() {
        elements = new ArrayList<Integer>();
        elementsRepr = ""; // initialise element text
        postorderRec(root); // run recursive post-order traversal function...
        for(int k = 0; k < elements.size(); k++) {
            elementsRepr += !(k == elements.size() - 1) ? String.format(RED_HL, elements.get(k) + ", ") : String.format(RED_HL, elements.get(k) + "");
        }
        System.out.println("POSTORDER TRAVERSAL: " + String.format("[%s]", elementsRepr)); // and print the results
    }

    // recursive post-order traversal function
    private void postorderRec(Node root) {
        if (root != null) {
            postorderRec(root.left); // go left first...
            postorderRec(root.right); // then go right
            // then read root
            elements.add(root.key);
        }
    }

    // method to update the 1D array representation
    private void updateArrayRepresentation() {
        // what this line does: calculate the (1-indexed) depth of the tree.
        // 1. get the maximum index + 1
        // 2. get the log base 2 of it (log_k n = log(n) / log(k))
        // 3. floor it (best achieved by turning it into an int) then add 1.
        // et voilà
        depth = (int)(Math.log(lastIndex + 1) / Math.log(2)) + 1;

        // make a new array with the size 2^depth - 1
        arrayRepr = new int[(int)(Math.pow(2, depth) - 1)];

        // fill it with 0s to represent blank nodes
        Arrays.fill(arrayRepr, 0);

        // run the recursive update arrays function
        updateArrayReprRec(root);
    }

    private void updateArrayReprRec(Node root) {
        // preorder-traverses the tree, gets the index of each node, and replaces its corresponding entry in the array representation with the node data
        if (root != null) {
            arrayRepr[root.index] = root.key;
            updateArrayReprRec(root.left);
            updateArrayReprRec(root.right);
        }
    }

    

    // (abstraction) method to update the indices of every node in the tree
    private void updateIndices() {
        updateIndicesRec(root, 1, NodeDirection.root);
        updateArrayRepresentation(); // and update the array representation while you're at it
    }

    // method to recursively update the indices
    private void updateIndicesRec(Node root, int i, NodeDirection direction) {
        if(root != null){
            // uses preorder traversal to get all nodes in the tree

            // set the current node's index first and update the lastIndex attribute if the current index is greater than the last one
            root.index = i - 1;
            if(root.index > lastIndex) { lastIndex = root.index; }

            // then update its hasSibling and direction attributes
            if(root.index == 0) { root.hasSibling = false; }
            root.dir = direction;

            // update hasSibling attributes for the left and right nodes if they aren't alone...
            if(root.left != null) {
                if(root.right == null) { root.left.hasSibling = false; } else { root.left.hasSibling = true; } 
                updateIndicesRec(root.left, i * 2, NodeDirection.left);
            }
            
            if(root.right != null) {
                if(root.left == null) { root.right.hasSibling = false; } else { root.right.hasSibling = true; } 
                updateIndicesRec(root.right, (i * 2) + 1, NodeDirection.right);
            }

            updateIndicesRec(root.left, i * 2, NodeDirection.left); // if there's a left node, go there and repeat
            updateIndicesRec(root.right, (i * 2) + 1, NodeDirection.right); // if there's a right node, go there too and repeat
        }
    }

    // method to clear the whole tree. sets the root to null and the size to zero
    private void clear() { root = null; size = 0; updateIndices();}

    // recursive method to print the whole tree
    private void printTree(Node root, Node previous, String offset, int depth) {
        
        Node rootroot = this.root; // get the very rootest of all the roots of the tree

        if(root != null) {
            // prefix for the node
            String off = "";

            // buncha maths to determine if the node is in the left half of the tree or not
            double localH = (int)(Math.pow(2, depth + 1) - (root.index + 1));
            double localP = (int)(Math.pow(2, depth));
            boolean isLeftHalf = ( localH / localP ) > 0.5;
            
            if (depth > 0) { // for all nodes theat aren't the root
                for (int i = 1; i < depth; i++) {
                    if(isLeftHalf) {
                        // buncha logic to determine if the connector symbol should be printed or not
                        if (i == 1 && rootroot.right != null) {
                            off += " ║     "; 
                        } else if ( i == 2 && ((previous.dir == NodeDirection.left) && previous.hasSibling)) {
                            off += " ║     ";
                        } else if (i > 2 && ((previous.dir == NodeDirection.left) && previous.hasSibling)) {
                            off += " ║     ";
                        } else {
                            off += "       ";
                        }
                    } else {
                        if ((i == depth - 1 || i > 1) && depth > 2 && (previous.dir == NodeDirection.left)) {
                            off += " ║     ";
                        } else {
                            off += "       ";
                        }
                    }
                }
    
                // if it's a left node with a sibling, print the ╠ version of the node connector, else print the ╚ version
                if ( root.dir.equals(NodeDirection.left) && root.hasSibling ) {
                    off += " ╠══";
                } else  {
                    off += " ╚══";
                }

                // display of node directions from parent...
                switch(root.dir) {
                    case NodeDirection.root -> {off += String.format(WHITE_HL, " #");}
                    case NodeDirection.left -> {off += String.format(CYAN_HL, " <");}
                    case NodeDirection.right -> {off += String.format(BLUE_HL, " >");}
                }
            }

            // finally, if the node is the selected one, print it in pink, else print it in red
            if (root.key == selected) {
                repr += String.format(
                off + String.format(PINK_HL, "[%s] ") + String.format(WHITE_HL, "@" + root.index) + "%n",
                root.key
                );
            } else {
                repr += String.format(
                off + String.format(RED_HL, "[%s] ") + String.format(WHITE_HL, "@" + root.index) + "%n",
                root.key
                );
            }
            
            // go left and go right
            printTree(root.left, root, off, depth + 1); 
            printTree(root.right, root, off, depth + 1);
        }
    }

    // method to print the array representation
    private void printArrayRepr() {
        int[] arr = arrayRepr; // making a copy of the current arrayRepr just to be safe
        // initialise row headers...
        indicesRepr = String.format("%-10s", "Indices");
        elementsRepr = String.format(RED_HL, String.format("%-10s", "Elements"));
        // and fill the representations with the indices and strings
        for(int k = 0; k < arr.length; k++) {
            indicesRepr += String.format(WHITE_HL, "@" + String.format("%-5s", k));
            elementsRepr += String.format(RED_HL, String.format("%-6s", String.format("[%s]", arr[k])));
        }
        // do the actual printing
        System.out.println("1D ARRAY REPRESENTATION: \n" + indicesRepr + "\n" + elementsRepr);
    }

    // Integer.parseInt with an exception silencer. invalid values return 0
    @SuppressWarnings("UseSpecificCatch")
    private int intParse(String input) { try { return Integer.parseInt(input); } catch (Exception e) { return 0; } }

    // command processor to process inputs
    public void processCommand(String inp){
        // commands separated by spaces
        String[] commands = inp.split("\s+");

        for(String command : commands) {
            if(command.startsWith("+")) {
                // if the command starts with a +, add a new node with the key being the number following (ignoring if the input isn't an int)
                Integer query = intParse(command.replace("+", ""));
                if(query != 0) { insert(query); }
            } else if(command.startsWith("!")) {
                // if the command starts with a !, delete the node with the key being the number following (ignoring if the input isn't an int)
                Integer query = intParse(command.replace("!", ""));
                if(query != 0) { remove(query); }
            } else if(command.startsWith("?")) {
                // if the command starts with a ?, search for the node with the key being the number following (ignoring if the input isn't an int)
                Integer query = intParse(command.replace("?", ""));
                if(query != 0) {
                    search(query);
                }
            } else if(command.toLowerCase().equals("clear")) {
                // if the command is "clear" (case-insensitive), clear the tree
                clear();
                lastOperation[0] = "CLEAR TREE"; // update last operation
                lastOperation[1] = "";
            } else if(command.toLowerCase().equals("size")) {
                // if the command is "size" (case-insensitive), get and display the size
                lastOperation[0] = "GET SIZE"; // update last operation
                lastOperation[1] = String.format("(%d)", size);
                System.out.print(size);
            } else if(command.toLowerCase().equals("depth")) {
                // if the command is "size" (case-insensitive), get and display the size
                lastOperation[0] = "GET DEPTH"; // update last operation
                lastOperation[1] = String.format("(%d)", depth);
                System.out.print(depth);
            }
        }
        
    }

    // actual printing of the tree
    public void print() {
        repr = "";
        if(size == 0) { // if the tree is empty, just show that the tree is empty
            repr = String.format(WHITE_HL, "(empty tree)") + "\n";
        } else {
            // if it's not empty, print the tree
            printTree(root, null, "", 0);
        }
        System.out.print(repr);
        if(size != 0) {
            line();
            printArrayRepr();
            line();
            preorder();
            line();
            inorder();
            line();
            postorder();
        }
    }
}

public class carodc3_31DS_L6 {

    // colour hacks for printing
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static String ANSI_WHITE = "\u001B[37m";
    public static String RED_HL= ANSI_RED + "%s" + ANSI_RESET;
    public static String WHITE_HL= ANSI_WHITE + "%s" + ANSI_RESET;

    // lines for æsthetic purposes
    final static void line() {
        System.out.println("---------------------------------------------------------------------");
    }
    final static void doubleLine() {
        System.out.println("=====================================================================");
    }

    @SuppressWarnings({ "deprecation", "UseSpecificCatch" })
    public final static void clearConsole() { // method to clear console, thank you https://stackoverflow.com/questions/2979383/how-to-clear-the-console-using-java
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
    // finally we get to the main method
    public static void main(String[] args){
        // create the tree and scanner objects
        AVLTree tree = new AVLTree();
        Scanner inp = new Scanner(System.in);
        
        do {
            // clear the console to reset it after each operation
            clearConsole();
            // title and last operation of the tree
            System.out.println(ANSI_RESET + "AVL TREE VISUALISER");
            System.out.println("LAST OPERATION: " + String.format(RED_HL, tree.lastOperation[0] + " " + tree.lastOperation[1]));
            doubleLine(); tree.print(); doubleLine(); // print the tree 
            // display available commands...
            System.out.println(
                "Commands: Insert "
                + String.format(RED_HL, "(+)")
                + ", delete "
                + String.format(RED_HL, "(!)")
                + ", search "
                + String.format(RED_HL, "(?)")
                + ", "
                + String.format(RED_HL, "clear")
                + ", get "
                + String.format(RED_HL, "size")
                + ", or "
                + String.format(RED_HL, "exit")
                );
            System.out.print("Enter commands (separated by spaces): " + ANSI_RED); // prompt input and display the input in red
            String h = inp.next(); // take input
            // if the input is 'exit' (case insensitive), exit the program
            if (h.toLowerCase().equals("exit")) { break; 
            } else { tree.processCommand(h); } // if not, process the command
        } while(true);
    }
}