/*
 * LLInt.java : LLInt class save each numbers into node, and calculate addition and multiply from the two value of Stack.
 */
public class LLInt {
	
	private int totalSum; // this is return totalSum
	Node head=null;
	private static final int RADIX=10;
	
	public LLInt(int val){
		head = new Node(val);
	}
	// insert each number into each node
	void insert(int val){
		
		if(head==null){
			head=new Node(val);
		}
		else{
			Node temp=head;
			Node newNode;
			while(temp.next!=null){
				temp=temp.next;
			}
			newNode = new Node(val);
			temp.next = newNode;
		}
	}
	// add two numbers between first Stack and Second Stack
	public LLInt addition(LLInt a, LLInt b){
		
		Node nodeA, nodeB;
		LLInt sum=null;
		int firstNodeNum,check1=1,check2=1;
		int secondNodeNum;
		int firstDigit, carry=0;
		
		nodeA=a.head;
		nodeB=b.head;
		
		// do all add calculation
		while(check1!=0 || check2!=0){
			if(check1==1){
				firstNodeNum=nodeA.num;
			}else{
				firstNodeNum=0;
			}
			if(check2==1){
				secondNodeNum=nodeB.num;
			}else{
				secondNodeNum=0;
			}
			totalSum = firstNodeNum+secondNodeNum+carry; // this is each digits addition with carry
			if(totalSum >= RADIX){ // if addition is bigger than 10
				firstDigit=totalSum%10; // it makes first digit number
				carry=1; // makes carry 1
				if(sum==null)
					sum = new LLInt(firstDigit); // if this is first try to add number, it make new node 
				else
					sum.insert(firstDigit); // keep insert numbers
			}else if(totalSum < RADIX){ // if addition is less than 10
				carry=0; // it makes carry 0
				if(sum==null)
					sum = new LLInt(totalSum); // if this is first try to add number, it make new node
				else
					sum.insert(totalSum); // keep insert numbers
			}
			if(nodeA.next!=null){
			nodeA=nodeA.next; // move to next number of first Node
			}
			else{ 
				check1=0; // if check1 is 0, it means there is no more number on nodeA which is first value of Stack
			}
			if(nodeB.next!=null){
			nodeB=nodeB.next; // move to next number of Second Node
			}
			else{
				//nodeB.num=0;
				check2=0; // if check2 is 0, it means there is no more number on nodeB which is second value of Stack
			}
		}
		if(totalSum >= RADIX){
			sum.insert(1); // if two values add and make three numbers result then add 1 into first digit
		}
		return sum;
	}
	
	
	// Do multiplication a and b
	public LLInt multiplication(LLInt a, LLInt b){
		int count_b=0; // the number of digits in LLInt a which is second node from the Stack
		LLInt mul=new LLInt(0); 
		Node a_node=a.head,b_node=b.head;
		while(b_node!=null){
			int count_a=0; // the number of digits in LLInt a which is first node from the Stack
			Node a_temp=a_node;
			while(a_temp!=null){
				mul=addition(multiplicationDegit(count_a, count_b,a_temp.num,b_node.num),mul); // do addition of each multiplicaion number
				a_temp=a_temp.next;
				count_a++;
			}
			count_b++;
			b_node=b_node.next;
		}
		return mul;
	}
	

	// Multiply each digit number and return result
	private LLInt multiplicationDegit(int a, int b, int num, int num2) {
		LLInt result=null;
		Node temp=null;
		if(a+b>0){ // if sum of digits number of a and b bigger than 0
			result=new LLInt(0);
			temp=result.head;
			int i=1;
			while(i<a+b){
				temp.next=new Node(0);
				temp=temp.next;
				i++;
			}
			num=num*num2;
			
			if(num<RADIX)
				temp.next=new Node(num); // save left number into node when multiply is less than 10
			else{
				temp.next=new Node(num%RADIX); // save left number into node
				temp.next.next=new Node(num/RADIX); // save real number into node
			}
		}
		else{ // if sum of digits number of a and b less or equal to 0
			num=num*num2; // multiply each digit number
			if(num<10)
				result=new LLInt(num); 
			else{
				result=new LLInt(num%RADIX);
				result.head.next=new Node(num/RADIX);
			}
		}
		return result;
	}


	// each digit numbers are saved into node
	public static class Node{
		Node next;
		int num;
		public Node(int num){
			this.num=num;
			next=null;
		}
		
	}

	// return result by string
	public String toString() {
		Node temp=head;
		StringBuffer result=new StringBuffer();
		while(temp!=null){
			result.insert(0,""+temp.num);
			temp=temp.next;
		}
		return result.toString();
	}
}
