import java.util.ArrayList;

/**
 * Node of the tree.  It's a standard BST node except for the parent ptr.
 * There are some helper methods here specific to the application.
 */
class Node {

    public Point record;
    public String satellite;
    public boolean vis;
    public Node left, right, bfsParent;
    public ArrayList<Point> adjacent;

    // Node has Point, String, Visted status
    public Node(Point rec, String sat, boolean visit)
    {
        record = rec;
        satellite = sat;
        vis = visit;
    }

    
    // toString() -- returns string of record and satellite
	public String toString()
    {
        return "Station " + record + " " + satellite;
    }

    // add more methods as appropriate
}