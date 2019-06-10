/**
 * Splay tree (BST invented by Sleator and Tarjan).
 * 
 * Name: Francis Kim
 * Date: 11/15/2016 6:00am
 * Description: Implement a Dictionary ADT using a Splay Tree. The dictionary will store record where the keys are user-supplied (x, y)
 * 				points with integer (int) coordinates, and the satellite data is a user supplied String.
 * 				A splay tree is a self-adjusting binary search tree with the additional property that recently accessed elements are quick 
 * 				to access again.
 * 
 */
class SplayTree
{
    // tbd
	private Node root, current=null;
	private String searchSat;
	private int sumRightLeft, first, reverse, c;
	private int startDirection;	// this represent which direction start firt (left, right)
	private int makeRoot=0;		
	public SplayTree(){
		
	}
    /** Portray tree as a string.  Optional but recommended. */
    public String toString()
    {
    	return null;
    }


    /**
     * Search tree for key k.  Return its satellite data if found,
     * and splay the found node.
     * If not found, return null, and splay the would-be parent node.
     */
    // String lookup -- this method is query
    public String lookup(Point key) // query
    {	
    	int height; // this is the height of the tree
    	Point keyCopy = keyDecide(key);	// this convert to proper Point value
    	first = 4;
    		while((height=height(keyCopy))!=0){		// loop until height is 0
    		//	System.out.println(height + "dddd");
    		//	System.out.println(sumRightLeft);
    		//	System.out.println(startDirection);
    		//	System.out.println(first + " dddasdfasdf ");
    			reverse=3;
    			if(height == 1 && sumRightLeft == -1 && startDirection == 0){	// ZigRight (start with left side)   o
    				ZigRight(current.parent);
    				
    			}else if(height >= 2 && sumRightLeft == -2 && first == 1){ // ZigZigRight (start with left side)   
    				reverse=1;				
    				ZigRight(current.parent.parent);
    				ZigRight(current.parent);
    				
    			}else if(height >= 2 && sumRightLeft == 0 && first == 1){ // ZigZagLeftRight (start with left side)
    				if(height == 2){
	    				reverse = 0;									// reverse represent how to connect the node
	    				ZigLeft(current.parent);						// ZigLeft
	    				ZigRight(current.parent);						//ZigRight
    				}else{
    					if(c == -1){									// if c = -1 represent structure
    					reverse = 0;									// reverse represent how to connect the node
    					ZigLeft(current.parent);
    					reverse = 1;									// reverse represent how to connect the node
    					ZigRight(current.parent);
    					}else{
    						reverse = 1;								// reverse represent how to connect the node
    						ZigLeft(current.parent);					// ZigLeft
        					reverse = 1;								// reverse represent how to connect the node
        					ZigRight(current.parent);					// ZigLeft
    					}
    				}
    			}else if(height >= 2 && sumRightLeft == 0 && first == 0){ // ZigZagRightLeft (start with left side)
    				if(height == 2){
	    				reverse = 0;									// reverse represent how to connect the node
	    				ZigRight(current.parent);						// Zig Right
	    				ZigLeft(current.parent);						// Zig Left
    				}else{
    					if(c == -1){
    					reverse = 0;									// reverse represent how to connect the node
	    				ZigRight(current.parent);
	    				reverse = 1;									// reverse represent how to connect the node
	    				ZigLeft(current.parent);						// Zig Left
    					}else{
    						reverse = 0;								// reverse represent how to connect the node
    						ZigRight(current.parent);					// Zig Right
    	    				reverse = 0;								// reverse represent how to connect the node
    	    				ZigLeft(current.parent);					// Zig Left
    					}
    				}
    				
    			}else if(height == 1 && sumRightLeft == 1 && startDirection == 1){	// ZigLeft (start with Right side)
    				ZigLeft(current.parent);
    			
    			}else if(height >= 2 && sumRightLeft == 2 && first == 0){ //ZigZigLeft (start with Right side)
    				reverse=1;		// this is case of ZigZig
    				ZigLeft(current.parent.parent);
    				ZigLeft(current.parent);
    				
    			}
    			
    			if(current.parent == null){
    				root = current;		// if node's parent is null, then that node is root
    				break;
    			}
    		}

    	if(search(key)==1){	// if there is not key, then return 0, if not return 1
    		
    	}else{
    		searchSat=null;
    	}
    	
    	
    	return searchSat;
    }

    /**
     * Insert a new record.
     * First we do a search for the key.
     * If the search fails, we insert a new record.
     * Otherwise we update the satellite data with sat.
     * Splay the new, or altered, node.
     */
    // insert_record(Point key, String sat)  -- this method is insert node
    public void insert_record(Point key, String sat)
    {
    	Node newNode = null;	// new Node
    	Node rootNode;
    	
    	if(makeRoot==0){
    		rootNode = new Node(key, sat);		// make root node
    		rootNode.left = null;
    		rootNode.right = null;
    		rootNode.parent = null;
    		root = rootNode;					// root point rootNode
    		
    		makeRoot++;							// if makeRoot over 0, then make newNode and attach to root
    	}else{
    		newNode = new Node(key, sat);		// create new node
    		newNode.right = null;				// set right to null
    		newNode.left = null;				// set left to null
    	
    		insert(newNode);					// insert newNode, and connected all
    		lookup(newNode.record);				// splay that node
    		
    		Node temp1 = root;
    		
    	}
    	/*
    	Node temp1 = root;
    	while(temp1.right != null){
    		temp1 = temp1.right;
    	}
    	while(temp1.parent != null){
    		System.out.println(temp1.toString());
    		temp1 = temp1.parent;
    	}
    	*/
    }

    // insert(Node newNode) -- this is helper method to connect newNode to root
    public void insert(Node newNode){
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
    		
    		}else{			// newNode = root
    			select = 2;									// this give direction which side node will attached
    			break;
    		}
    	}
    	if(select == 0){
    		previousTemp.right = newNode;					// right side of previousTemp node connected with new Node
    		newNode.parent = previousTemp;					// new Node parent is previousTemp node
    		//System.out.println(newNode.parent.satellite);
    		
    	}else if(select == 1){
    		previousTemp.left = newNode;					// left side of previousTemp node connected with new Node
    		newNode.parent = previousTemp;					// new Node parent is previousTemp node
    	}else{
    		temp.satellite = newNode.satellite;				// change the satellite string
    	}
    	
    }
    
    // search(Point key) -- this method search specific data
    public int search(Point key){
    	int findNode = 0;
    	Node temp = root;			// temp is point to root
    	
    	searchSat = null;
    	while(temp != null){			// loop until temp is null
    		if(temp.record.compareTo(key) > 0){ // newNode > root
    			temp = temp.right;		// temp goest to right side of temp
    		}else if(temp.record.compareTo(key) < 0){ // newNode < root
    			temp = temp.left;		// temp goest to left side of temp
    		}else if(temp.record.compareTo(key) == 0){			// newNode = root
    			findNode = 1;			// this give we find same node
    			
    			break;
    		}
    	}
    	if(findNode == 1){			// if find node
    		searchSat = temp.satellite; // searchSat is current satellite
    	}else{
    		searchSat = null;		// if not find, return null
    	}
    	return findNode;
    }
    
    // keyDecide(Point key) -- this method give the proper key of Point
    public Point keyDecide(Point key){
    	int find=0;
    	Point keyReturn=null;		// result value of this method
    	Node temp = root;			// temp point to root
    	Node previousTemp=null;		
    	
    	while(temp != null){
    		if(temp.record.compareTo(key) > 0){ // newNode > root
    			previousTemp = temp;
    			temp = temp.right;				// temp go to right side of temp
    		}else if(temp.record.compareTo(key) < 0){ // newNode < root
    			previousTemp = temp;
    			temp = temp.left;				// temp go to left side of temp
    		}else if(temp.record.compareTo(key) == 0){			// newNode = root
    			keyReturn = key;				// keyReturn is original value
    			find=1;
    			break;
    		}
    		
    	}
    	
    	if(find==0){
    		keyReturn = previousTemp.record;	// if key is not found, then it is parent of key
    	}
    	return keyReturn;
    }
    
    // height(Point key) -- this calculate height of tree
    public int height(Point key){
    	int height = 0, oneTime=0;
    			
    	Node temp = root;
    	Node previousTemp, copy;
    	
    	while(temp != null){		// loop until temp is null
    		if(temp.record.compareTo(key) > 0){ // newNode > root
    			previousTemp = temp;
    		//	System.out.println(temp.satellite);
    			temp = temp.right;		// temp go to right side of temp
    			if(oneTime==0){
    				startDirection = 1;
    			}
    			height++;
    			
    		}else if(temp.record.compareTo(key) < 0){ // newNode < root
    			previousTemp = temp;
    			//System.out.println(temp.satellite);
    			temp = temp.left;
    			if(oneTime==0){
    				startDirection = 0;
    			}
    			height++;
    		
    		}else if(temp.record.compareTo(key) == 0){			// newNode = root
    			current = temp;
    			
    			break;
    			
    		}
    		oneTime++;
    	}
    	
    	copy = new Node(current.record, current.satellite);
    	//System.out.println(current.satellite);
    	sumRightLeft = direction(copy, height);
    	return height;
    }
   // direction(Node copy, int height) -- this method tell the direction of node
    public int direction(Node copy, int height){
    	int left=-1, right=1, returnValue=0, a=0;
    	Node grandParent, parent, grandParentParent;
    	first = 3;
    	if(height==1){
    		parent = current.parent;
    		while(parent != null){
    			if(parent.record.compareTo(copy.record) > 0){ // current > parent 
    				returnValue = returnValue + right; 		// returnValue = returnValue + 1
    				parent = parent.right;
    			}else if(parent.record.compareTo(copy.record) < 0){ // current < parent
    				returnValue = returnValue + left;		// returnValue = returnValue + -1
    				parent = parent.left;
    			}else if(parent.record.compareTo(copy.record)==0){	// if value is same, then break
    				break;
    			}
    	//		System.out.println("error");
    		}
    	}else if(height >= 2){										// when height is over 2
    		a=0;
    		c=0;
    		//System.out.println(current.satellite.toString());
    		grandParent = current.parent.parent;
    		while(grandParent != null){
    			//System.out.println("parint1");
    			if(grandParent.record.compareTo(copy.record) > 0){ // current > parent 
    				returnValue = returnValue + right; 
    				grandParent = grandParent.right;
    				if(a==0){
    					first = 0;				// right side
    					a++;
    				}
    			}else if(grandParent.record.compareTo(copy.record) < 0){ // current < parent
    				returnValue = returnValue + left;
    				grandParent = grandParent.left;
    				if(a==0){
    					first = 1;				// left side
    					a++;
    				}
    			}else if(grandParent.record.compareTo(copy.record)==0){
    				break;
    			}
    		}
    			if(height >= 3){
    				grandParentParent = current.parent.parent.parent;
    				while(grandParentParent != null){
    					
    					if(grandParentParent.record.compareTo(copy.record) > 0){
    						c = c + right;									// c decide which structure use
    						grandParentParent = grandParentParent.right;
    					}else if(grandParent.record.compareTo(copy.record) < 0){
    						c = c + left;									// c decide which structure use
    						grandParentParent = grandParentParent.left;
    					}else if(grandParentParent.record.compareTo(copy.record)==0){
    					//	System.out.println("this is error");
    	    				break;
    					}
    	    		}	
    			}
    	
    		}
    		
    	

    	return returnValue;
    }
    // ZigRight(Node target) -- this make ZigRight node
    public void ZigRight(Node target){
    	Node kid = target.left;			// kid is the child of target
    	
    	if(target.parent == null){			// if target parent is null, then we can zig one time
    		target.left = kid.right;		// target left is connected with kid right
    		if(kid.right != null){			// if there is value of kid right, then connect with target
    			kid.right.parent = target;
    		}
    		kid.parent = target.parent;		// connected kid's parent with target's parent
    		target.parent = kid;			// target parent connected with kid
			kid.right = target;				// kid's right conneted with target
			
    	}else if(target.parent != null){	// if target parent is not null, then there are severl nodes up
			
    		target.left = kid.right;		// target left is connected with kid's right
			if(kid.right != null){			// if kid's right is not null, then connect kid's right with target
				kid.right.parent = target;
			}
			kid.parent = target.parent;		// kid's parent connect with target parent
			if(reverse == 0){			// if ZigZag
				target.parent.right = kid;	// connect with kid
			}else if(reverse==1){	// if ZigZig
				target.parent.left = kid;	// connect with kid
			}
			target.parent = kid;			// target's parent connect with kid
			kid.right = target;				// kid's right connect with target
			
    	}
    	//current = kid;
    }
    // ZigLeft(Node target) -- this make ZigLeft node
    public void ZigLeft(Node target){
    	Node kid = target.right;
    	//System.out.println(kid.satellite);
    	if(target.parent == null){			// if target parent is null, then we can zig one time
    		target.right = kid.left;		// target left is connected with kid right
    		if(kid.left != null){			// if there is value of kid right, then connect with target
			kid.left.parent = target;
    		}
    		kid.parent = target.parent;		// connected kid's parent with target's parent
    		target.parent = kid;			// target parent connected with kid
			
			kid.left = target;				// kid's right conneted with target
			
    	}else if(target.parent != null){	// if target parent is not null, then there are severl nodes up
			
    		target.right = kid.left;		// target left is connected with kid's right
			if(kid.left != null){			// if kid's right is not null, then connect kid's right with target
			kid.left.parent = target;
			}
			kid.parent = target.parent;		// kid's parent connect with target parent
			if(reverse==0){			// if ZigZag
				target.parent.left = kid;	// connect with kid
			}else if(reverse==1){	// if ZigZig
				target.parent.right = kid;	// connect with kid
			}	
			target.parent = kid;			// target's parent connect with kid
			kid.left = target;				// kid's right connect with target
			
    	}
    	//current = kid;
    }
 
    /**
     * Remove a record.
     * Search for the key.  If not found, return null.
     * If found, save the satellite data, remove the record, and splay
     * appropriately.  Use one of the two following methods and update
     * this comment to document which method you actually use.
     *
     * Appropriate method 1:  splay the bereaved parent.
     *   some node x will be deleted, so splay the parent of x.
     * Appropriate method 2:  splay twice.
     *   Find the node u with the key.  Splay u, then immediately splay
     *   the successor of u.  Finally, remove u by splicing.
     *
     * Return the satellite data from the deleted node.
     */
    
    // delete(Point key) -- delete node
    public String delete(Point key)
    {	
    	Point keyCopy = key;	// this is original key
    	
    	Node temp = root;		// temp is root
    	Node noRightSubTree;
    	String copySearchSat;	// copy the satallite
    	if(search(keyCopy)==1){
    		copySearchSat = searchSat;	
    		lookup(keyCopy);	// query node that I selected
    		
    		temp = temp.right;	// temp go to right side of temp
    		if(temp==null){
    			temp = root;
    			temp = temp.left;
    			if(temp == null){
    				root = null;
    				makeRoot = 0;
    			}else{
    				root = root.left;
    				root.parent = null;
    			}
    		}
    		else{
    			while(temp.left != null){			// find a larger number
    				
    				temp = temp.left;			// temp goes to next
    			}
    			lookup(temp.record);			// query larger number
    			//System.out.println(keyCopy.toString());
    			deleteTarget(keyCopy);				// delete number
    		}		
    		
    	}else{
    		copySearchSat=null;
    	}
        // tbd
    	
    	return copySearchSat;
    }
    // deleteTarget(Point key) -- this delete the node
    public void deleteTarget(Point key){
    	Node temp = root;
    	Node target = null;
    	int direction = 3;
    	while(temp != null){
    		if(temp.record.compareTo(key) > 0){ // newNode > root
    			direction=1;
    			temp = temp.right;
    		}else if(temp.record.compareTo(key) < 0){ // newNode < root
    			direction=2;
    			temp = temp.left;
    		}else if(temp.record.compareTo(key) == 0){			// newNode = root
    			target = temp;					// target is temp
    			break;
    		}
    		
    	}
    	
    	if(target.left == null && target.right == null){			// if target has no chile
    			target = null;										// target is null
    	}else if(target.left == null && target.right != null){		// if target left is null, and right is not null
    		if(direction==1){		// right
    			target.parent.right = target.right;					// connect whole right side of subtree to target
    			target.right.parent = target.parent;
    			target=null;
    		}else if(direction==2){		// left
    			target.parent.left = target.right;					// connect whold left side of subtree to target
    			target.right.parent = target.parent;
    			target=null;
    		}
    	}else if(target.left != null & target.right == null){		// if target left is not null, target right is null
    		if(direction==1){ // right
    			target.parent.left = target.left;					// connect whole right side of subtree to target
    			target.left.parent = target.parent;
    			target=null;
    		}else if(direction==2){	//left
    			target.parent.right = target.left;					// connect whold left side of subtree to target
    			target.left.parent = target.parent;
    			target=null;
    		}
    	}else{														// if both of child is not null
    		Node endOfNode = target.left;
    		while(endOfNode.right != null){							// trace the end of right side node
    			
    			endOfNode = endOfNode.right;
    		}
    		endOfNode.right = target.right;							// connect with target right
    		target.right.parent = endOfNode;						// connect target right with end of node
    		target.right = null;									// target right set to null
    		target.parent.left = target.left;						// target parent connect with target left
    		target.left.parent = target.parent;						// target left connect with target parent
    		target = null;											// target null
    	}
    	
    }
    
    
    
    
}