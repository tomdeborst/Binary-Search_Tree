import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author thomas de borst ID: 1004302
 */

/**
 * Class for Binary Search Tree
 */
public class BSTLex {

    public String data;
    public BSTLex left, right;
    private BSTLex root, current;

    //Constructor
    public BSTLex() {
        current = root = null;
    }

    /**
     * Method to return list of nodes starting at the root
     * @return
     */
    public Stream<String> retrieve() {
        return recursive(root).stream();
    }

    /**
     * Method to cycle through nodes recursively and return a list of nodes
     * @param node method takes a node
     * @return returns a list of strings
     */
    private List<String> recursive(BSTLex node) {
        List<String> temp = new ArrayList<>();
        if (node == null) {
            return temp;
        }
        temp.add(node.data);
        temp.addAll(recursive(node.left));
        temp.addAll(recursive(node.right));

        return temp;
    }

    public BSTLex(String data) {
        this.data = data;
        left = right = null;
    }

    /**
     * Method for finding a BST node with the given value
     *
     * @param v Method is passed a String value
     * @return Method returns either true or false based on whether the value was found in the BST
     */
    public boolean findValue(String v) {

        BSTLex p = root;
        while (p != null) {
            System.out.println(Arrays.toString(retrieve().toArray()));
            current = p;
            if (v.equals(p.data)) {
                return true;
            } else if (v.compareTo(p.data) > 0) {
                p = p.left;
            } else {
                p = p.right;
            }
        }
        return false;
    }

    /**
     * Method to insert a new node into the BST. Duplicate nodes(even words) are deleted.
     * Uses recursion to populate tree branches down to the leaf
     *
     * @param node node where new information should be stored
     * @param val  String value of data
     * @return returns either true or false depending if node was added or deleted.
     */
    private boolean insert(BSTLex node, String val) {
        BSTLex p = current;
        if (findValue(val)) {
            current = p;
            removeVal(val);
            return false;
        }
        current = p;

        BSTLex temp = new BSTLex(val);
        //If value given is less than 0 and left node is not null then insert at left.
        if (val.compareTo(node.data) > 0) {
            if (node.left != null) {
                insert(node.left, val);
                //Else set as temp
            } else node.left = temp;
        } else {
            //If value given is greater than 0 and right node is not null then insert at right.
            if (node.right != null) {
                insert(node.right, val);
                //Else set as temp
            } else node.right = temp;
        }
        //Set current to temp
        current = temp;
        return true;
    }

    /**
     * Method for inserting a string value into the BST
     * This method calls the method above in the event the node is not created at the root
     *
     * @param val String value passed in
     * @return Returns true when node is added.
     */
    public boolean insert(String val) {
        if (root == null) {
            current = root = new BSTLex(val);
            System.out.println(val.concat(" INSERTED"));
            return true;
        } else if (val.compareTo(root.data) < 0) {
            BSTLex temp = root;
            root = new BSTLex(val);
            root.left = temp;
            return true;
        }
        if (insert(root, val)) {
            System.out.println(val.concat(" INSERTED"));
            return true;
        }
        return false;
    }

    /**
     * Method for removing node based on String value passed in
     *
     * @param k String value passed to the method
     * @return returns true or false depending on whether the given value was present found and deleted
     */
    public boolean removeVal(String k) {

        //Search for k
        String k1 = k;
        BSTLex p = root;
        BSTLex q = null; //Parent of p
        while (p != null) {

            if (k1.compareTo(p.data) > 0) {
                q = p;
                p = p.left;
            } else if (k1.compareTo(p.data) < 0) {
                q = p;
                p = p.right;
            } else { //Found the key

                //Check the three cases
                if ((p.left != null) && (p.right != null)) { // Case 3: two
                    //Children

                    //Search for the min in the right subtree
                    BSTLex min = p.right;
                    q = p;
                    while (min.left != null) {
                        q = min;
                        min = min.left;
                    }
                    p.data = min.data;
                    k1 = min.data;
                    p = min;
                    //Now fall back to either case 1 or 2
                }

                //The subtree rooted at p will change here
                if (p.left != null) { // One child
                    p = p.left;
                } else {
                    //One or no children
                    p = p.right;
                }

                //No parent for p, root must change
                if (q == null) {
                    root = p;
                } else {
                    if (k1.compareTo(q.data) > 0) {
                        q.left = p;
                    } else {
                        q.right = p;
                    }
                }
                current = root;
                System.out.println(k.concat(" DELETED"));
                return true;

            }
        }

        // Not found
        return false;
    }
}
