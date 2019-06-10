/**
 * Node of the tree.  It's a standard BST node except for the parent ptr.
 * There are some helper methods here specific to the application.
 */
class Node {

    public Point record;
    public String satellite;
    public Node left, right, parent;

    Node(Point rec, String sat)
    {
        record = rec;
        satellite = sat;
    }

    public String toString()
    {
        return "record = " + record + ", satellite = " + satellite;
    }

    // add more methods as appropriate
}