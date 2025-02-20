// CARLOS ROMEO CLEMENTE DEL CASTILLO III
// UNIVERSITY OF THE CORDILLERAS, BSIT-1L
// 30IP: CC2/Introduction to Computer Programming
// [30IP-06L4] Final Laboratory Challenge №4: Linked Lists Application 
// 22 NOVEMBER 2024

/*
INSTRUCTIONS:
OPTION 2) MUSIC PLAYLIST MANAGER

OBJECTIVE: Implement a Circular Doubly Linked List to manage a music playlist that supports continuous looping.

SCENARIO: You are creating a music playlist application. The playlist is circular, meaning it loops back to the beginning after reaching the end. You should be able to:
    - Add a song to the playlist.
    - Remove a song from the playlist.
    - Move to the next or previous song.
    - Display the current song and playlist.

REQUIREMENTS:
    - Add Song: Insert a song node into the playlist.
    - Remove Song: Delete a song from the playlist, adjusting links to maintain the circular structure.
    - Next and Previous: Move forward or backward through the playlist.
    - Loop Continuously: When reaching the end, the playlist should loop back to the first song.
*/

import java.util.Scanner;

class Node {
    String data; // the actual data of the Node
    Node next; // connector to the next node
    Node prev; // connector to the previous node
    static int count = -1; // background counter to be used for making indices easier
    int index; // index for the node. this will make things easier when it comes to things like insertion and deletion

    Node (String data) { // attributes of a node:
        this.data = data; // it has data
        this.next = null; // it has a next node
        this.prev = null; // it has a previous node
        count += 1;
        this.index = count; // it has an index
    }
}

// now define that a LoopedList is composed of nodes...
class LoopedList { // because the classname DoubleCircularLinkedList is a bit too long...

    // ATTRIBUTES
    String name; // internal name of the looped list. just for fun. can have spaces and such; useful for playlists
    Node head = null; // first node of the looped list
    Node foot = null; // last node
    Node selected = null; // selected node. this kinda list can only ever have one selected node so i put it as an attribute of LoopedList instead of having isSelected as an attribute of Node like i had originally planned
    int length = 0; // length

    // CONSTRUCTOR
    LoopedList(String name) { // constructor; sets the name
        this.name = name;
    }
    
    // initialisation...
    // TAKE NOTE! this is a DOUBLY CIRCULAR linked list. so every node has two connections and it loops over in both directions
    void push(String newData) { // method to insert new data at the END of the LoopedList
        length += 1;
// being honest: almost of this code is from https://www.geeksforgeeks.org/insertion-in-doubly-circular-linked-list/ and slightly modified. however the comments are all made by me since the original code didn't have any comments at all as to what it was doing and it was a pain to understand. of course that is not my style and i don't just insert code without having at least an understanding of how it works. i think i might have an understanding of what it is now

        Node newNode = new Node(newData); // make new node. this is the one we're going to be inserting

        if (head == null) {
            // if list is empty...
            // make new node
            newNode.next = newNode.prev = newNode; // set newNode's next and prev to itself. didn't know you can do double or n-tuple assignments in Java until now. #TheMoreYouLearn
            head = newNode; // then, set newNode as the head
        } else { // if the list is Not empty...

            // set the foot
            foot = head.prev; // set the list's foot as the previous node of the head

            // ensure it loops Forward correctly...
            foot.next = newNode; // with the old foot, connect it so that its next is the newNode
            newNode.next = head; // and then by default, it will be the new last element (foot) since it's inserted in the end, so set its next to loop back to the head so its loops in the forward direction

            // ensure it loops Backward correctly...
            newNode.prev = foot; // for the new node, since its the new one at the end of the list, set its previous node to the old foot
            head.prev = newNode; // then for the head, set its new prev to be the newNode
        }
    }

    void printList() { // method to print the data of all of the nodes in the list...
        if (head == null && length == 0) {
            System.out.println("  > EMPTY PLAYLIST <");
            return;
        }

        // set a current working node, set it to the head
        Node curr = head;
        do { // keep looping on the nodes until it goes back to the head
             // print data
            if(curr == selected) {
                System.out.print(" >> " + (curr.index + 1) + ". " + curr.data + " << \n"); // with little markers if the current node is selected
            } else {
                System.out.print("    " +( curr.index + 1) + ". " + curr.data + "\n");
            }
            
            curr = curr.next; // move to the next
        } while (curr != head); // ...while it hasn't hit the head again
    }

    // METHOD TO RETURN SPECIFIC NODE IN THE LIST
    // okay now this section i havent just nicked from GeeksForGeeks
    Node selectNode(int selIndex) { // to return the node at a specific index. couldn't find a solution to do this in O(1) time so here's an O(n) solution instead

        if (selIndex >= length) { // if the user is trying to select something whose index is out of bounds, throw this error message
            System.out.println("Index not in list!");
            return null; // and don't return anything
        } else {
            // black magic
            Node curr = head; // set a current working node to the head
            while (curr.index < selIndex) { // and while that node's index is not the selected index...
                curr = curr.next; // keep going to the next node until its found the node with the right index
            }
            return curr; // then return that node
        }
    }

    // NODE PICKING METHODS
    void pickNode(Node n) { selected = n; } // to set a node status to current
    void pickNodePrev() {
        if (selected == null) { selected = selectNode(length-1); } else { selected = selected.prev;} // picks previous node. if no previous node, selects last
    } 
    void pickNodeNext() {
        if (selected == null) { selected = head; } else { selected = selected.next;}
        // picks next node. if no next node, selects first
    } 

    // method to update indices after node deletion. funfact i added this because there was a bug in the first submission; after one deletion, subsequent deletions wont work because deletion is based on node indices, which.. weren't updated after a node in the middle was deleted. this fixes it
    void updateIndices() {
        Node curwa = head; // start with the head
        int indicount = 0; // node counter
        while(curwa.next != head) { // while it hasnt hit the head again...
            curwa.index = indicount; // set the current node's index to the current count,
            indicount += 1; // increment the count,
            curwa = curwa.next; // and move to the next node
        }
    }

    // METHOD TO DELETE NODES BASED ON INDEX
    // thanks www.geeksforgeeks.org/deletion-in-doubly-circular-linked-list/
    int remove(int index) { // int type bc there's multiple ways it can fail

        if (index >= length) { // if the user is trying to select something whose index is out of bounds, throw this error message
            System.out.println("Cannot remove item: index not in list!");
            return -1; // return code -1 if index not in list
        }

        if (head == null) return -2; // obviously if the list is empty we don't have anything to delete; return code -2

        Node current = selectNode(index); // select the target node to be deleted...
        Node cMinusOne = current.prev; // and its previous node
        Node cPlusOne = current.next; // and its next node

        if (length == 1) { // if there's only one element in the list...

            head = null; // deregister the head
            length -= 1; // register the deletion to the length property of the list
            return 1; // success if this is returned

        } else if(current == head) { // if the current node is the head, then...
    
            // disconnect the head node and set its next as the new head
            cMinusOne = (head).prev; // set c-1 to be the head's prev (which here is the last element)
            head = (head).next; // set the new head to be the next node from the old head
            cMinusOne.next = head; // set the next connection of the c-1 to be this new, updated head
            (head).prev = cMinusOne; // and set the new head's prev to the c-1
            length -= 1; // register the deletion to the length property of the list
            updateIndices(); // update indices after
            return 2; // success if this is returned

        } else { // else if the selected node is NOT the head, then...

            current.next = null; // disconnect the next and prev connections from the current node
            current.prev = null;
            cMinusOne.next = cPlusOne; // set the next of c-1 to be c+1, sort of skipping over the node that was removed 
            cPlusOne.prev = cMinusOne; // and same with setting the new prev of c+1 to be c-1
            length -= 1; // register the deletion to the length property of the list
            updateIndices(); // update indices after
            return 3; // success if this is returned   

        }
        
    }
}

// MAIN METHOD
// finally after so many lines we're here. but since we're making an App there's bound to be an app loop i need to do. maybe a do-while...
public class carodc3_ITCP_06L4 {

    static void line() {
        System.out.println("-------------------------------------------------------------------------");
    }
    // lines for æsthetic purposes
    static void doubleLine() {
        System.out.println("=========================================================================");
    }

    // method to parse index inputs. if it fails, returns -1, but if not, returns the int input
    static int indexInputParser(String input) {
        try {
            int k = Integer.parseInt(input);
            return k;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @SuppressWarnings({"deprecation", "UseSpecificCatch"})
    public final static void clearConsole() { // method to clear console, thank you https://stackoverflow.com/questions/2979383/how-to-clear-the-console-using-java
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                System.out.println("\u001b[2J" + "\u001b[H");
            }
        } catch(Exception E){
            // je m'en câlisse
        }
    }

    @SuppressWarnings({ "unused" })
    public static void main(String[] args) throws InterruptedException {
        
        LoopedList playlist = new LoopedList("Playlist 1"); // create playlist object
        Scanner inp; // create scanner obj for user input

        // UNCOMMENT THESE FOR DEBUG
        // playlist.push("Alfa");
        // playlist.push("Bravo");
        // playlist.push("Charlie");
        // playlist.push("Delta");
        // playlist.push("Echo");
        // playlist.push("Foxtrot");
        // playlist.push("Golf");
        // playlist.push("Hotel");
        // playlist.push("India");
        // playlist.push("Juliett");
        // playlist.push("Kilo");
        // playlist.push("Lima");
        // playlist.push("Mike");
        // playlist.push("November");
        // playlist.push("Oscar");
        // playlist.push("Papa");
        // playlist.push("Quebec");
        // playlist.push("Romeo");
        // playlist.push("Sierra");
        // playlist.push("Tango");
        // playlist.push("Uniform");
        // playlist.push("Victor");
        // playlist.push("Whiskey");
        // playlist.push("Xray");
        // playlist.push("Yankee");
        // playlist.push("Zulu");

        // main app loop
        do { 
            clearConsole(); // to keep clean and not make the terminal too cluttered
            // title
            System.out.println("CURRENT PLAYLIST: " + playlist.name);
            doubleLine();
            playlist.printList(); // print the playlist
            doubleLine();
            // print the possible actions...
            System.out.println("ACTIONS:");
            System.out.println("  [1] Add Song to Playlist");
            System.out.println("  [2] Remove Song from Playlist");
            System.out.println("  [3] Previous Song");
            System.out.println("  [4] Next Song");
            System.out.println("  [5] Select Song...");
            System.out.println("  [X] Exit");
            line();
            // then the area for user input..
            System.out.print("Input Action (1/2/3/4/5/X): ");
            // get and read user input
            inp = new Scanner(System.in);
            String choice = inp.nextLine();
            doubleLine();

            // Interface to add songs
            if("1".equals(choice)) {
                System.out.println("Enter name of song to add:"); 
                System.out.print("  > "); // user enters name of song to add
                playlist.push(inp.nextLine()); // and it is pushed to the playlist
            }

            // Interface to remove songs
            if("2".equals(choice)) {
                for(int i = 0; i < 1; i++) { // one-step loop technique ensures i can re-request user input when needed
                    System.out.println("Enter number of song to remove:");
                    System.out.print("  > ");
                    int removeChoice = indexInputParser(inp.nextLine()); // request and parse input in the same line
                    if (removeChoice == -1) {
                        System.out.println("Input must be a number! Exiting to menu..."); // else, if input is invalid, exit to menu
                        Thread.sleep(1000);
                        break; } // if invalid choice, reprompt
                    else if (removeChoice > playlist.length) {
                        System.out.println("Index not in list! Input again:"); // else, if out of bounds, display this error message and reprompt
                        Thread.sleep(1000); i--;
                    } else {
                        playlist.remove(removeChoice - 1); // else, remove the song with that index
                    }
                }
            }

            // Interface to select songs
            if("3".equals(choice)) { playlist.pickNodePrev(); } // pick previous
            if("4".equals(choice)) { playlist.pickNodeNext(); } // pick next

            // Interface to select song based on index
            if("5".equals(choice)) {
                for(int i = 0; i < 1; i++) { // one step loop again
                    System.out.println("Enter number of song to select:");
                    System.out.print("  > ");
                    int selChoice = indexInputParser(inp.nextLine()); // request and parse input
                    // same logic really as with the deleting process
                    if (selChoice == -1) {
                        System.out.println("Input must be a number! Exiting to menu..."); // else, if input is invalid, exit to menu
                        Thread.sleep(1000);
                        break;
                     } // if invalid choice, reprompt
                    else if (selChoice > playlist.length) { // if out of bounds, reprompt
                        System.out.println("Index not in list! Input again:");
                        Thread.sleep(1000);
                    } else {
                        playlist.pickNode(playlist.selectNode(selChoice-1)); // else, its valid; select the node at that index
                    }
                }
            }
            if("X".equals(choice)) { break; } // if input is "X", break
        } while (true);
        inp.close(); // close the scanner so vs code is happy
    }
} // i need to catch up on my data structures or else im gonna have a hard time in DSA come 2nd term