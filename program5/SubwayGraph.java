import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/*
 * allowed libraries:

import java.lang.Comparable;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.lang.StringBuilder;
import java.lang.Math;
import java.lang.InstantiationException;
*/
/*	Author: Francis Kim
 *  Project: Program 5
 *  Date: 12/05/2016 6:00am
 *  Purpose: In this project we will implement a graph data structure and breadth-first search, then use it to find paths through a fictional subway system.  
 *  How to solve: I made node contains Point, String, Left, Right, bfs parent, point of arraylist. I put all connected vertices into arraylist 
 *  			  and connected each vertices with parents of bfs. And if I found the target node, print out all node that connected with target node from
 *                parent of bfs. Then it will show the shortest length.
 * 
 * 
 */
class SubwayGraph {

	private Node root;
	private int makeRoot=0;
	// construct an empty graph
	public SubwayGraph()
	{
	}

	// Add vertex to the graph
	// (Point class is from project 4)
	// insert(Point loc, String sat) -- this make binary search tree by inserting node
	public void insert(Point loc, String sat)
	{
		Node newNode = null;	// new Node
    	Node rootNode;			// root of the binary tree
    	ArrayList<Point> adjacent1 = new ArrayList<Point>();
    	if(makeRoot==0){
    		rootNode = new Node(loc, sat, false);		// make root node
    		rootNode.left = null;
    		rootNode.right = null;
    		rootNode.adjacent = adjacent1;
    		root = rootNode;					// root point rootNode
    		makeRoot++;							// if makeRoot over 0, then make newNode and attach to root
    	}else{
    		newNode = new Node(loc, sat, false);		// create new node
    		newNode.right = null;				// set right to null
    		newNode.left = null;				// set left to null
    		newNode.adjacent = adjacent1;
    		insert_node(newNode);					// insert newNode, and connected all
    		
    	}
    	
	}
	
	// insert_node(Node newNode) -- this is helper method to insert node to make binary tree
	public void insert_node(Node newNode){
		Node temp = root;			// temp point to root
    	Node previousTemp = null;	// this point previous temp node
    	int select=0;
    	while(temp != null){		// loop until temp is not null
    		if(temp.record.compareTo(newNode.record) > 0){ // newNode > root
    			previousTemp = temp;						// previousTemp point to temp
    			temp = temp.right;							// temp go to right side of node
    			select = 0;									// this give direction which side node will attached
    		}else if(temp.record.compareTo(newNode.record) < 0){ // newNode < root
    			previousTemp = temp;						// previousTemp point to temp
    			temp = temp.left;							// temp go to left side of node
    			select = 1;									// this give direction which side node will attached
    		}else{
    			select = 2;
    			break;
    		}
    	}
    	
    	if(select == 0){
    		previousTemp.right = newNode;					// right side of previousTemp node connected with new Node
    		
    	}else if(select == 1){
    		previousTemp.left = newNode;					// left side of previousTemp node connected with new Node
    	}else{
    		temp.satellite = newNode.satellite;				// change the satellite string
    	}
	}
	
	// Add edge to the graph
	// adjacent(Point loc1, Point loc2) -- this making connected with loc1, loc2 vertices 
	public void adjacent(Point loc1, Point loc2)
	{
		if(search(loc1, loc2)==2){							// if there is no loc1 or loc2, then do nothing
			if(duplicate(loc1, loc2)==0){					// if there is already connected node exist, then do nothing
				connect1(loc1, loc2);						// connect loc1 with loc2 in the arrayList
				connect2(loc1, loc2);						// connect loc2 from loc1 in the arrayList
			}
		}
	}

	// connect1(Point loc1, Point loc2) -- this make loc1 have loc2 inside of loc1 arraylist
	public void connect1(Point loc1, Point loc2){
		Node tempRoot = root;				// tempRoot points root
		
		while(tempRoot != null){			// loop until tempRoot is null
    		if(tempRoot.record.compareTo(loc1) > 0){ // loc1 > root
    			tempRoot = tempRoot.right;		// tempRoot goes to right side of tempRoot
    		}else if(tempRoot.record.compareTo(loc1) < 0){ // loc1 < root
    			tempRoot = tempRoot.left;		// tempRoot goes to left side of tempRoot
    		}else if(tempRoot.record.compareTo(loc1) == 0){			// loc1 = root
    			tempRoot.adjacent.add(loc2);						// add loc2 inside of loc1 arraylist
    			break;
    		}
    	}
	}
	
	// connect1(Point loc1, Point loc2) -- this make loc2 have loc1 inside of loc2 arraylist
	public void connect2(Point loc1, Point loc2){
		Node tempRoot = root;
		
		while(tempRoot != null){			// loop until tempRoot is null
    		if(tempRoot.record.compareTo(loc2) > 0){ // loc2 > tempRoot
    			tempRoot = tempRoot.right;		// tempRoot goes to right side of tempRoot
    		}else if(tempRoot.record.compareTo(loc2) < 0){ // loc2 < tempRoot
    			tempRoot = tempRoot.left;		// tempRoot goes to left side of tempRoot
    		}else if(tempRoot.record.compareTo(loc2) == 0){			// loc2 = tempRoot
    			tempRoot.adjacent.add(loc1);						// add loc1 inside of loc2 arraylist
    			break;
    		}
    	}
	}
	
	// duplicate(Point loc1, Point loc2) -- if there is already connected vertices, then do nothing.
	public int duplicate(Point loc1, Point loc2){
		Node tempRoot = root;
		int result=0;
		while(tempRoot != null){			// loop until tempRoot is null
    		if(tempRoot.record.compareTo(loc1) > 0){ // loc1 > tempRoot
    			tempRoot = tempRoot.right;		// tempRoot goes to right side of tempRoot
    		}else if(tempRoot.record.compareTo(loc1) < 0){ // loc1 < tempRoot
    			tempRoot = tempRoot.left;		// tempRoot goes to left side of tempRoot
    		}else if(tempRoot.record.compareTo(loc1) == 0){			// loc1 = tempRoot
    			break;
    		}
    	}
		int size = tempRoot.adjacent.size();						// size of arraylist of loc1
		for(int a=0; a < size; a++){
			if(tempRoot.adjacent.get(a).compareTo(loc2)==0){
				result = 1;											// if there is loc 2 already, then result = 1
			}
		}
		
		return result;
	}
	
	// search(Point loc1, Point loc2) -- if there is no loc1 or loc2 in binary tree, then do nothing
	public int search(Point loc1, Point loc2){
		
		int result = 0;
    	Node temp = root;			// temp is point to root
    	
    	while(temp != null){			// loop until temp is null
    		if(temp.record.compareTo(loc1) > 0){ // loc1 > root
    			temp = temp.right;		// temp goes to right side of temp
    		}else if(temp.record.compareTo(loc1) < 0){ // loc1 < root
    			temp = temp.left;		// temp goes to left side of temp
    		}else if(temp.record.compareTo(loc1) == 0){			// loc1 = root
    			result = result + 1;			// this give we find loc1 in binary tree
    			break;
    		}
    	}
    	temp = root;
    	while(temp != null){			// loop until temp is null
    		if(temp.record.compareTo(loc2) > 0){ // newNode > root
    			temp = temp.right;		// temp goes to right side of temp
    		}else if(temp.record.compareTo(loc2) < 0){ // newNode < root
    			temp = temp.left;		// temp goes to left side of temp
    		}else if(temp.record.compareTo(loc2) == 0){			// newNode = root
    			result = result + 1;			// this give we find loc2 in binary tree
    			break;
    		}
    	}
    	
    	return result;
	}
	// Try to find a shortest path between vertices
	// route(Point start, Point finish) -- this gives a route of shortest path from start to finish
	public String route(Point start, Point finish)
	{
		if(search(start, finish)==2){					// check there is start and finish node in binary tree
						Node tempRoot = root;
						Node tempRoot1 = root;
						Node dequeue = null;
						int count=0;
						int result=0;
						Queue<Node> q = new LinkedList<Node>();
						
						// find start node in binary tree
						while(tempRoot != null){			// loop until tempRoot is null
				    		if(tempRoot.record.compareTo(start) > 0){ // start > tempRoot
				    			tempRoot = tempRoot.right;		// tempRoot goes to right side of tempRoot
				    		}else if(tempRoot.record.compareTo(start) < 0){ // start < tempRoot
				    			tempRoot = tempRoot.left;		// tempRoot goes to left side of tempRoot
				    		}else if(tempRoot.record.compareTo(start) == 0){			// loc2 = tempRoot
				    			break;
				    		}
				    	}
						tempRoot.bfsParent = null;				// make bfs parent of start node = null
						tempRoot.vis = true;					// make visited of start node = true
						q.add(tempRoot);						// add start node in the queue
						while(!q.isEmpty()){					// Loop queue is going to empty
						
							dequeue = q.remove();				// Do FIFO, and get first node in the queue
							if(finish.compareTo(dequeue.record)==0){		// if queue gives finish node then break;
								result = 1;
							
								break;
							}
							if(count > 0){						// if count > 0, then find dequeue node
								tempRoot = root;
								while(tempRoot != null){
									if(tempRoot.record.compareTo(dequeue.record) > 0){ // dequeue > tempRoot
						    			tempRoot = tempRoot.right;		// tempRoot goes to right side of tempRoot
						    		}else if(tempRoot.record.compareTo(dequeue.record) < 0){ // dequeue < tempRoot
						    			tempRoot = tempRoot.left;		// tempRoot goes to left side of tempRoot
						    		}else if(tempRoot.record.compareTo(dequeue.record) == 0){			// dequeue = tempRoot
						    			break;
						    		}
								}
							}
							for(int a=0; a < tempRoot.adjacent.size(); a++){				// get a adjacent node from dequeue node
								
								tempRoot1 = root;
								// find a adjacent node in binary tree
								while(tempRoot1 != null){
									if(tempRoot1.record.compareTo(tempRoot.adjacent.get(a)) > 0){ // dequeue adjacent node > tempRoot
						    			tempRoot1 = tempRoot1.right;		// tempRoot goes to right side of tempRoot
						    		}else if(tempRoot1.record.compareTo(tempRoot.adjacent.get(a)) < 0){ // dequeue adjacent node < tempRoot
						    			tempRoot1 = tempRoot1.left;		// tempRoot goes to left side of tempRoot
						    		}else if(tempRoot1.record.compareTo(tempRoot.adjacent.get(a)) == 0){			// dequeue adjacent node = tempRoot
						    			break;
						    		}
								}
								if(tempRoot1.vis==false){					// if adjacent node is not visited
								
									tempRoot1.vis=true;						// then make visited
									tempRoot1.bfsParent = tempRoot;			// connected bfs parent with dequeue node
									q.add(tempRoot1);						// add adjacent node in queue
									
								}
							}
							count++;
						}
						
						if(result==0){
							reset(start, dequeue.record);
							return "NO PATH";							// this make reset of all vertices of visited status and bfs parent, so we can start over again
						}else{
							Node tempRoot2 = root;
							while(tempRoot2 != null){
								if(tempRoot2.record.compareTo(finish) > 0){ // newNode > root
					    			tempRoot2 = tempRoot2.right;		// temp goes to right side of temp
					    		}else if(tempRoot2.record.compareTo(finish) < 0){ // newNode < root
					    			tempRoot2 = tempRoot2.left;		// temp goes to left side of temp
					    		}else if(tempRoot2.record.compareTo(finish) == 0){			// newNode = root
					    			break;
					    		}
							}
							
							printReverse(tempRoot2, finish);			// print out reverse of linked list of what we found
							reset(start, finish);						// this make reset of all vertices of visited status and bfs parent, so we can start over again
						}	
		}else{
			return "NO PATH";											// if there are no start and finish node, then print out NO PATH
		}
		
		return "";														// I already print linked list in above, so return ""
	}

	// reset(Point start, Point finish) -- this make all vertices node of visited and bfs parent reset, similar logic with route method, we can keep track of visited=true node
	public void reset(Point start, Point finish){
		Node tempRoot = root;				// tempRoot point to root in binary tree
		Node dequeue = null;
		int count = 0;
		Node tempRoot1;
		Queue<Node> q = new LinkedList<Node>();
		
		//find start node in binary tree
		while(tempRoot != null){			// loop until tempRoot is null
    		if(tempRoot.record.compareTo(start) > 0){ // start > tempRoot
    			tempRoot = tempRoot.right;		// tempRoot goes to right side of tempRoot
    		}else if(tempRoot.record.compareTo(start) < 0){ // start < root
    			tempRoot = tempRoot.left;		// tempRoot goes to left side of tempRoot
    		}else if(tempRoot.record.compareTo(start) == 0){			// start = tempRoot
    			break;
    		}
    	}
		tempRoot.bfsParent = null;				// make start node of bfs parent = null
		tempRoot.vis = false;					// make start node of visited = false
		q.add(tempRoot);						// add start node into queue
		while(!q.isEmpty()){
			dequeue = q.remove();
			if(finish.compareTo(dequeue.record)==0){
				break;
			}
			if(count > 0){
				tempRoot = root;
				while(tempRoot != null){
					if(tempRoot.record.compareTo(dequeue.record) > 0){ // newNode > root
		    			tempRoot = tempRoot.right;		// temp goes to right side of temp
		    		}else if(tempRoot.record.compareTo(dequeue.record) < 0){ // newNode < root
		    			tempRoot = tempRoot.left;		// temp goes to left side of temp
		    		}else if(tempRoot.record.compareTo(dequeue.record) == 0){			// newNode = root
		    			break;
		    		}
				}
			}
			for(int a=0; a < tempRoot.adjacent.size(); a++){			// get a adjacent node from dequeue node
				tempRoot1 = root;
				while(tempRoot1 != null){
					if(tempRoot1.record.compareTo(tempRoot.adjacent.get(a)) > 0){ // dequeue adjacent node > tempRoot
		    			tempRoot1 = tempRoot1.right;		// temp goes to right side of temp
		    		}else if(tempRoot1.record.compareTo(tempRoot.adjacent.get(a)) < 0){ // dequeue adjacent node < tempRoot
		    			tempRoot1 = tempRoot1.left;		// temp goes to left side of temp
		    		}else if(tempRoot1.record.compareTo(tempRoot.adjacent.get(a)) == 0){			// dequeue adjacent node = tempRoot
		    			break;
		    		}
				}
				if(tempRoot1.vis==true){					// if adjacent node is visited
					tempRoot1.vis=false;					// then make adjacent node is not visited
					tempRoot1.bfsParent = null;				// set adjacent bfs parent = null
					q.add(tempRoot1);						// add into queue
				}
			}
			
			count++;
		}
	}
	// printReverse(Node tempRoot2, Point finish) -- this print out linked list reverse
	public void printReverse(Node tempRoot2, Point finish){
		
		if(tempRoot2==null){
			return;
		}
		printReverse(tempRoot2.bfsParent, finish);				// recursive to print out reverse
		if(tempRoot2.record.compareTo(finish)==0){
			System.out.print(tempRoot2.toString());				// print out Point and String of tempRoot2
		}else{
			System.out.println(tempRoot2.toString());			// print out Point and String of tempRoot2 (this doesn't have newline)
		}
		
	}
	
	// search2(Point loc) -- search loc node in bianry tree
	public int search2(Point loc){
		int result=0;
		Node temp = root;
		while(temp != null){			// loop until temp is null
    		if(temp.record.compareTo(loc) > 0){ // loc > root
    			temp = temp.right;		// temp goes to right side of temp
    		}else if(temp.record.compareTo(loc) < 0){ // loc < root
    			temp = temp.left;		// temp goes to left side of temp
    		}else if(temp.record.compareTo(loc) == 0){			// loc = root
    			result = result + 1;			// this give we find same node
    			break;
    		}
    	}
		return result;
	}
	// extra credit -- remove a vertex and all incident edges
	// delete(Point loc) -- delete loc vertices in binary tree
	public void delete(Point loc)
	{
		Node tempRoot = root;
		Node previousTempRoot = null;
		int select=0;
		int count2=0;
		
		if(search2(loc)==1){
				while(tempRoot != null){
					if(tempRoot.record.compareTo(loc) > 0){ // newNode > root
						if(count2==0){					
							select=1;						// if select is 1, then it is start from right side of binary tree
							count2++;
						}
		    			previousTempRoot = tempRoot;		
						tempRoot = tempRoot.right;			// tempRoot goes to right side of tempRoot
		    		}else if(tempRoot.record.compareTo(loc) < 0){ // loc < tempRoot
		    			if(count2==0){
		    				select=2;						// if select is 2, then it is start from left side of binary tree
		    				count2++;
		    			}
		    			previousTempRoot = tempRoot;
		    			tempRoot = tempRoot.left;			// tempRoot goes to left side of tempRoot
		    		}else if(tempRoot.record.compareTo(loc) == 0){			// loc = tempRoot1
		    			if(count2==0){						
		    				select=0;					// if select is 0, then it point root
		    			}
		    			break;
		    		}
				}
				if(tempRoot.bfsParent !=null){				// if loc node have bfs parent
					
					int size1 = tempRoot.bfsParent.adjacent.size();
					int index=0;
					for(int c=0; c < size1; c++){	// find a loc node in adjacent node
						if(tempRoot.bfsParent.adjacent.get(c).compareTo(tempRoot.record)==0){
							index = c;				// Found loc node, and get index from adjacent arraylist
						}
					}
					tempRoot.bfsParent.adjacent.remove(index);		// remove target node in linked list
				}
				tempRoot.bfsParent = null;					// make bfs parent of loc node = null
				int size = tempRoot.adjacent.size();		// get a size of adjacent loc node
				
				for(int a=0; a < size; a++){
					Node tempRoot1 = root;
					while(tempRoot1 != null){
						if(tempRoot1.record.compareTo(tempRoot.adjacent.get(a)) > 0){ // adjacent vertices of loc node > tempRoot1
			    			tempRoot1 = tempRoot1.right;		// tempRoot1 goes to right side of tempRoot1
			    		}else if(tempRoot1.record.compareTo(tempRoot.adjacent.get(a)) < 0){ // adjacent vertices of loc node < tempRoot1
			    			tempRoot1 = tempRoot1.left;		// tempRoot1 goes to left side of tempRoot1
			    		}else if(tempRoot1.record.compareTo(tempRoot.adjacent.get(a)) == 0){			// adjacent vertices of loc node = tempRoot1
			    			break;
			    		}
					}
					tempRoot1.bfsParent = null;		// Connect bfsParent to bfsParent of remove node
					int size1 = tempRoot1.adjacent.size();
					int index=0;
					for(int c=0; c < size1; c++){	// find a loc node in adjacent node
						if(tempRoot1.adjacent.get(c).compareTo(tempRoot.record)==0){
							index = c;				// Found loc node, and get index from adjacent arraylist
						}
					}
					tempRoot1.adjacent.remove(index);		// remove target node in linked list
				}
				
				if(select==0){		// remove root
					Node t = null;
					if(tempRoot.right != null){				// if right side of loc node is not null in binary tree
						t = tempRoot.right;					// t is right child node of root
						root = t;							// and root is move to t
					}else{									// if right side of loc node is null in binary tree
						t = tempRoot;						
						root = t.left;						// root is just move to left child node
					}
					
					if(tempRoot.right != null){				// if right side of loc node is not null
						while(t.left != null){				// find a smallest node in right side of binary tree
							t = t.left;						
						}
						t.left = tempRoot.left;				// connect original left side of binary to smallest right side of node in binary tree
					}
				}else if(select==1){						// remove right side of node from root
					Node t = null;
					if(tempRoot.right != null){				// if right side of loc node is not null
						t = tempRoot.right;					// t = right side of loc node
						previousTempRoot.right = t;			// right side of previousTempRoot is t
						while(t.left != null){
							t = t.left;
						}
						t.left = tempRoot.left;				// left side of t is left side of loc node
					}else{
						t = tempRoot;						// if right side of loc node is null
						previousTempRoot.right = t.left;	// right side of previousTempRoot is leftside of t
					}
					tempRoot.right = null;					// right side of loc node = null
				}else if(select==2){						// remove right side of node from root
					Node t = null;
					if(tempRoot.left != null){				// if left side of loc node is not null
						t = tempRoot.left;					// t = left side of loc node
						previousTempRoot.left = t;			// left side of previousTempRoot is t
						while(t.right != null){
							t = t.right;
						}
						t.right = tempRoot.right;			// right side of t is right side of loc node
					}else{
						t = tempRoot;						// if left side of loc node is null
						previousTempRoot.left = t.right;	// left side of previousTempRoot is right side of t
					}	
					tempRoot.left = null;					// left side of loc node = null
				}
		}
	}
}

