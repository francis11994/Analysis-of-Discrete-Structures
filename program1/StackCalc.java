/*
 * StackCalc.java : This class is create Stack, and put numbers into Stack.
 */

public class StackCalc {
	
	private LLInt node;
	private Node2 head=null;
	private Node2 temp;
	private boolean addStack=false;
	
	public StackCalc(){
		
	}
	// Push input into node
	void push(String token) {
		
		node=null;
		int n=token.length()-1;
		int digit;
		while(n>=0){
			try{
			digit = Integer.parseInt(""+token.charAt(n));
			if(node==null){
				node = new LLInt(digit); // create new node and save into node
			}else{
				node.insert(digit); // keep save into node
			}
			n--;
			}catch(Exception e){
				System.out.println("Please input number, not character"); // error print out when we input character
				System.exit(1);
			}
		}
		
		insertLLInt(node); //insert created node into Stack
	}
	//insert LLInt into stack
	void insertLLInt(LLInt node){
		
		if(head==null){
			head = new Node2(node); //insert created node into first Stack
			temp=head;
			
		}else{
			
			Node2 newStack = new Node2(node);
			
			if(addStack == false){ // if addStack is false, then push into Stack
			newStack.next=temp;
			temp=newStack;
			}else if(addStack == true){ // if addStack is true, then push sum or multiplication of two numbers into Stack
				newStack.next=temp.next;
				temp=newStack;
				addStack=false;
			}
		}
	}
	
	//pops two value of the Stack, add them, pushes into Stack, and print adding numbers
	String add() {
		addStack=true;
		Node2 up = pop();
		
		temp=temp.next;
		
		if(temp==null){
			System.out.println("You need to push more value");
			System.exit(1);
		}
		
		Node2 down = pop();

		LLInt copy = node.addition(up.llint, down.llint); 
		
		insertLLInt(copy);  
		
		return toString(temp.llint);
	}
	
	//pops two value of the Stack, multiply them, pushes into Stack, and print multiplying numbers
	String multiply() {
		addStack=true;
		Node2 up = pop();
		temp=temp.next;
	
		if(temp==null){
			System.out.println("You need to push more value");
			System.exit(1);
		}
		Node2 down = pop();
		
		LLInt copy = node.multiplication(up.llint, down.llint); 
		
		insertLLInt(copy);  
		
		return toString(temp.llint);
	}
	// pop the current value of Stack 
	Node2 pop(){
		
		return temp;
	}
	
	// this is return whole numbers calculated from two value of Stack
	String toString(LLInt llint){
		
		return llint.toString();
	}
	
	// this make Stack
	public static class Node2{
		Node2 next;
		LLInt llint;
		public Node2(LLInt llint){
			this.llint=llint;
			next=null;
		}
		
	}
}
