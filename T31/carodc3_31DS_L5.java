// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, CITCS-1F
// CC4/DATA STRUCTURES AND ALGORITHMS
// [31DS-L5] Laboratory Activity 5: Binary Trees
// 9 March 2025

// BINARY SEARCH TREE Representation
// - Accept integer input from the user
// - Display the 1-D array representation
// - Display the three tree traversals: Preorder, Inorder, Postorder
// - Option to try again

// BINARY SEARCH TREE Definition: 

// code from https://www.geeksforgeeks.org/implementing-a-binary-tree-in-java/ and https://www.techiedelight.com/deletion-from-bst/
// comments by yours truly :3

// imports...
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
    int key, index; // it has a key (data) and an index (position)
    Node left, right; // it can have child nodes to the left and right
    boolean hasSibling; // boolean to check if the node has a sibling
    NodeDirection dir; // direction from parent

    // constructor
    public Node(int item) {
        key = item;
        index = 0; // initial index is 0. this will be updated by BinarySearchTree.updateIndices()
        left = right = null; // no children by default
    }
}

class BinarySearchTree {

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

    Node root; // every tree is defined by its root
    int selected; // which node is selected (for highlighting in insert & search operations)
    int size = 0; // number of nodes in the tree
    String indicesRepr = ""; // representation of indices and elements for 1D traversals
    String elementsRepr = "";
    String repr = ""; // representation for tree visualisation
    String[] lastOperation = {String.format(WHITE_HL, "(none)"), ""}; // last operation done on the tree

    // constructor. blank tree by default
    public BinarySearchTree() {
        root = null;
    }

    // Insertion operation
    void insert(int key) {
        root = insertRec(root, key); // do the recursive inserting operation
        lastOperation[0] = "INSERT"; lastOperation[1] = key + ""; // update the last operation done
        updateIndices(); // update the indices
    }

    // recursive inserting operation
    private Node insertRec(Node root, int key) {
        if (root == null) { // if there's a space to insert the node, then do so
            root = new Node(key); // make the new node
            size += 1; // increase the size
            selected = key; // set that node to be selected
            return root; // return it
        }
        if (key < root.key) // if the new node's value is less than the current node, go left and check again
            root.left = insertRec(root.left, key);
        else if (key > root.key) // else, go right and check again
            root.right = insertRec(root.right, key);
        
        return root; // then return it
    }

    // deleting operation
    void delete(int key) {
        if(size == 1 && root.key == key) { // if there's only one node in the tree...
            root = null; // delete it
            size = 0; // update the size
            lastOperation[0] = "DELETE"; lastOperation[1] = key + ""; // update the last operation
        }
        // else, run the recursive deleting operation
        else if (root != null) { deleteNode(root, key); }
        updateIndices(); // and update the indices
    }

    // recursive deleting operation
    private Node deleteNode(Node root, int key)
    // comments in this section partially from https://www.techiedelight.com/deletion-from-bst/. they explain it way better than geeksForGeeks
    {
        // if the key is not found in the tree, return null and don't update anything
        if (root == null) { return null; }

        // if the given key is less than the root node, go to the left subtree and check again
        if (key < root.key) { root.left = deleteNode(root.left, key); }

        // if the given key is more than the root node, go to the right subtree and check again
        else if (key > root.key) { root.right = deleteNode(root.right, key); }

        // if the key to be deleted is found...
        else {
            // Case 1: node to be deleted has no children (it is a leaf node)
            if (root.left == null && root.right == null)
            {
                // update root to null
                lastOperation[0] = "DELETE"; lastOperation[1] = key + ""; // update last operation
                size -= 1; // update size
                return null;
            }

            // Case 2: node to be deleted has two children
            else if (root.left != null && root.right != null)
            {
                // find its inorder predecessor node
                Node predecessor = findMaximumKey(root.left);

                // copy value of the predecessor to the current node
                root.key = predecessor.key;

                // recursively delete the predecessor. note that the predecessor will have at most one child (left child)
                root.left = deleteNode(root.left, predecessor.key);
                lastOperation[0] = "DELETE"; lastOperation[1] = key + ""; // update last operation
                size -= 1; // update size
            }

            // Case 3: node to be deleted has only one child
            else {
                // choose a child node
                Node child = (root.left != null) ? root.left: root.right; // choose which child will replace the node based on which child actually exists
                root = child; // update it
                lastOperation[0] = "DELETE"; lastOperation[1] = key + ""; // update last operation
                size -= 1; // update size
            }
        }
        return root;
    }

    // all other sources say we find the inorder successor but Canvas says to find the predecessor instead
    // so i had to go out of my way to find a method that did just that cńi so ivmin magistáír giod o siin habdruǵiad
    // hó Déo peǵa na utam arhannavis lasod, elle cé mánin magistáir giod
    private Node findMaximumKey(Node ptr)
    {
        // keep going right while there is a right node
        while (ptr.right != null) {
            ptr = ptr.right;
        }
        return ptr; // and return whatever the result is when it's done
    }

    // Search operation
    boolean search(int key) {
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


    // Inorder traversal and printing. is called IN-order because it goes left -> root -> right, with the root IN BETWEEN the instructions to go left and right
    void inorder() {
         // initialise indices and elements row labels
        indicesRepr = String.format("%-10s", "Indices");
        elementsRepr = String.format(RED_HL, String.format("%-10s", "Elements"));
        inorderRec(root); // run recursive in-order traversal function...
        System.out.println(indicesRepr + "\n" + elementsRepr); // and print the results
    }

    // recursive in-order traversal function
    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left); // go left first...
            // read root...
            indicesRepr += String.format(WHITE_HL, String.format("%5s", "@ " + root.index)) + " ";
            elementsRepr += String.format(RED_HL, String.format("%6s", String.format("[%s]", root.key)));
            // then go right
            inorderRec(root.right);
        }
    }

    // Preorder traversal and printing. is called PRE-order because it goes root -> left -> right, with the root BEFORE the instructions to go left and right
    void preorder() {
         // initialise indices and elements row labels
        indicesRepr = String.format("%-10s", "Indices");
        elementsRepr = String.format(RED_HL, String.format("%-10s", "Elements"));
        preorderRec(root); // run recursive pre-order traversal function...
        System.out.println(indicesRepr + "\n" + elementsRepr); // and print the results
    }

    // recursive pre-order traversal function
    private void preorderRec(Node root) {
        if (root != null) {
            // read root first...
            indicesRepr += String.format(WHITE_HL, String.format("%5s", "@ " + root.index)) + " ";
            elementsRepr += String.format(RED_HL, String.format("%6s", String.format("[%s]", root.key)));
            preorderRec(root.left); // then go left
            preorderRec(root.right); // then go right
        }
    }

    // Postorder traversal and printing. is called POST-order because it goes left -> right -> root , with the root AFTER the instructions to go left and right
    void postorder() {
         // initialise indices and elements row labels
        indicesRepr = String.format("%-10s", "Indices"); elementsRepr = String.format(RED_HL, String.format("%-10s", "Elements"));
        postorderRec(root); // run recursive post-order traversal function...
        System.out.println(indicesRepr + "\n" + elementsRepr); // and print the results
    }

    // recursive post-order traversal function
    private void postorderRec(Node root) {
        if (root != null) {
            postorderRec(root.left); // go left first...
            postorderRec(root.right); // then go right
            // then read root
            indicesRepr += String.format(WHITE_HL, String.format("%5s", "@ " + root.index)) + " ";
            elementsRepr += String.format(RED_HL, String.format("%6s", String.format("[%s]", root.key)));
        }
    }

    // (abstraction) method to update the indices of every node in the tree
    void updateIndices() {
        updateIndicesRec(root, 1, NodeDirection.root);
    }

    // method to recursively update the indices
    private void updateIndicesRec(Node root, int i, NodeDirection direction) {
        if(root != null){
            // uses preorder traversal to get all nodes in the tree

            // set the current node's index first
            root.index = i - 1;

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
    void clear() { root = null; size = 0; }

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

    // Integer.parseInt with an exception silencer. invalid values return -(2^31)
    @SuppressWarnings("UseSpecificCatch")
    static int intParse(String input) { try { return Integer.parseInt(input); } catch (Exception e) { return Integer.MIN_VALUE; } }

    // command processor to process inputs
    public void processCommand(String inp){
        // anything after a space will be ignored
        String command = inp.split("\s+")[0];

        if(command.startsWith("+")) {
            // if the command starts with a +, add a new node with the key being the number following (ignoring if the input isn't an int)
            Integer query = intParse(command.replace("+", ""));
            if(query != Integer.MIN_VALUE) { insert(query); }
        } else if(command.startsWith("!")) {
            // if the command starts with a !, delete the node with the key being the number following (ignoring if the input isn't an int)
            Integer query = intParse(command.replace("!", ""));
            if(query != Integer.MIN_VALUE) { delete(query); }
        } else if(command.startsWith("?")) {
            // if the command starts with a ?, search for the node with the key being the number following (ignoring if the input isn't an int)
            Integer query = intParse(command.replace("?", ""));
            if(query != Integer.MIN_VALUE) {
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
        }
    }

    // actual printing of the tree
    void print() {
        repr = "";
        if(size == 0) { // if the tree is empty, just show that the tree is empty
            repr = String.format(WHITE_HL, "(empty tree)") + "\n";
        } else {
            // if it's not empty, print the tree
            printTree(root, null, "", 0);
            line();
            System.out.println("PREORDER TRAVERSAL:");
            preorder();
            line();
            System.out.println("INORDER TRAVERSAL:");
            inorder();
            line();
            System.out.println("POSTORDER TRAVERSAL:");
            postorder();
        }
        System.out.print(repr);
    }
}

public class carodc3_31DS_L5 {

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
        BinarySearchTree tree = new BinarySearchTree();
        Scanner inp = new Scanner(System.in);
        
        do {
            // clear the console to reset it after each operation
            clearConsole();
            // title and last operation of the tree
            System.out.println(ANSI_RESET + "BINARY SEARCH TREE VISUALISER");
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
            System.out.print("Enter command: " + ANSI_RED); // prompt input and display the input in red
            String h = inp.next(); // take input
            // if the input is 'exit' (case insensitive), exit the program
            if (h.toLowerCase().equals("exit")) { break; 
            } else { tree.processCommand(h); } // if not, process the command
        } while(true);
    }
}