/*
 * Author: Francis Kim
 * Professor: Andrew Predoehl
 * class: CSC345
 * Due Date: 09/20/2016 6:00 am
 * Purpose: Do the addition and subtraction with positive and negative numbers
 * Description : LLInt class save each numbers into node, and calculate addition and subtraction.
 * 				In project 1, we did addition and multiply, but we only can handle positive number.
 * 				However, in this project, I can also handle positive and negative numbers.
 * 				LLInt class is in the AddDriver class and SubtractDriver class, and if two LLInt value has given
 * 				then we can do addition in AddDriver class, and subtraction in SubtractDriver class.
 * 				LLInt has two constructor to instantiate integer and String.
 * 				Constructor which is accepted String can get number as string, and make a Linked List which consist of whole number.
 * 				Constructor which is accepted integer makes each node which has each digit numbers.
 * 				For example, if we put "12345" number as string, then String constructor accepted whole numbers, and through the push() method
 * 				, integer constructor get each digit numbers like "1", "2", "3", "4", "5", and put into each node. So, I made Node class on the below.
 * 				So, I can make liked list like " null <- 1 <- 2 <- 3 <- 4 <- 5 ".
 *				LLInt has 11 methods and they collaborate well, and print out result.
 *				Main methods which is add(LLInt a, LLInt b), and subtract(LLInt a, LLInt b) print out final result
 *				I will explain about my code really detail on the below.
 */

// I used Comparable<LLInt> to compare two numbers
public class LLInt implements Comparable<LLInt> {
	
	
	private LLInt node;	// this LLInt node make new LLInt to create linked list
	private int totalSum; // this will be use in add_nonneg_int method, this is each digit of sum
	Node head=null; // this is point head in linked list,
	private static final int RADIX=10; // I represent decimal number, so I used RADIX
	private String number; // This is input number as String
	private LLInt A, B, C, D, AC, BD, F;
	
	// This constructor make first node which is point to head
	public LLInt(int val){
		head = new Node(val);
	}
	
	// This constructor accepted String as number, and make linked list using push method
	public LLInt(String number){
		this.number = number;
		push(this.number); // make linked list of input number
	}
	
	// push(String token) -- This method accepted String number, and make linked list of String number (line: 30)
	void push(String token) {
		node=null; // First value of node is equal to null
		int n=token.length()-1; // n is length of input String number
		char digit; 
		digit = (char) (token.charAt(0)); // digit will accept first digit character from String number
		if(n==0 && digit=='-'){ // If there is only '-' sign without numbers, then It will print out error
			System.out.println("You only input negative sign");
			System.exit(1);
		}
		while(n>=0){ // this will be terminated when n goes to -1
			digit = (char) (token.charAt(n)); // digit will accept each digit character from String number
			if(n != 0 && (digit > '9' || digit < '0')){ // if there is any character in String number, then print out error
				System.out.println(" Please don't input any character ");
				System.exit(1); // terminate after error print
			}else if(n==0 && digit != '-' && (digit > '9' || digit < '0')){ // if first digit is any character except minus, then print out error
				System.out.println(" You can only input negative sign, not any character ");
				System.exit(1); //terminate after error print
			}
			
			if(digit <= '9' && digit >= '0'){ // digit accept character, so I need to change to integer
			digit = (char) (token.charAt(n)-'0'); // For example, '9' - '0' = 9
			}
			
			if(node==null){ // if node is null, then make first node which is head
				node = new LLInt(digit); // create new node and save into node
			}else{
				node.insert(digit); // we made head, so we can insert each digit number and connect.
			}
			n--; // n = n - 1
		}
	}

	// void insert(int val) -- This method accepted integer, and insert it into each node and connect. (line: 13)
	void insert(int val){
		if(head==null){ //if head is null, then create new head, this is kind of error catch.
			head=new Node(val);
		}
		else{ // if head is not a null, then put new integer into newNode, and connect with previous node.
			Node temp=head; // temp point to head
			Node newNode; // this is make new node
			while(temp.next!=null){ // go until next is null
				temp=temp.next;
			}
			newNode = new Node(val);
			temp.next = newNode; // insert new node into linked list
		}
	}
	
	// add_nonneg_int(LLInt a, LLInt b) -- add two numbers between first input LLInt and Second input LLInt (x and y in driver program), (line: 48)
	public LLInt add_nonneg_int(LLInt a, LLInt b){
		Node nodeA, nodeB;
		LLInt sum=null; // This is return new linked list which is sum of first String number and second String number
		int firstNodeNum,check1=1,check2=1, secondNodeNum, firstDigit, carry=0;
		nodeA=a.head; // this nodeA point to linked list a's head
		nodeB=b.head; // this nodeB point to linked list b's head
		// do all add calculation
		while(check1!=0 || check2!=0){ // if  first linked list number go to null then check1 = 0, if second linked list number is go to null then check2 = 0
			if(check1==1){ // if first linked list is not null, then go into statement
				firstNodeNum=nodeA.num; // save each digit number into firstNodeNum
			}else{
				firstNodeNum=0; // if first Linked list number go to null, then accepted digit number is 0
			}
			if(check2==1){ // if second linked list is not null, then go into statement
				secondNodeNum=nodeB.num;
			}else{
				secondNodeNum=0; // if second Linked list number go to null, then accepted digit number is 0
			}
			totalSum = firstNodeNum+secondNodeNum+carry; // this is each digits addition with carry, for example, 1234 + 123 = 1357, so 1, 3, 5, 7 is totalSum 
			if(totalSum >= RADIX){ // if addition is bigger than 10, then we have to make carry 1
				firstDigit=totalSum%10; // it makes first digit number, subtract 10
				carry=1; // makes carry 1
				if(sum==null) // if sum is null, then create new linked list
					sum = new LLInt(firstDigit); // this is make head
				else
					sum.insert(firstDigit); // keep insert numbers, and make new node connected with previous node
			}else if(totalSum < RADIX){ // if addition is less than 10, then just add two digit numbers
				carry=0; // it makes carry 0
				if(sum==null) // if sum is null, then create new linked list
					sum = new LLInt(totalSum); // this is make head
				else
					sum.insert(totalSum); // keep insert numbers, and make new node connected with previous node
			}
			if(nodeA.next!=null){ // if nodeA's next is not null
			nodeA=nodeA.next; // then move to next number of node
			}else{ 
				check1=0; // if check1 is 0, it means there is no more number on nodeA
			}
			if(nodeB.next!=null){ // if nodeB's next is not null
			nodeB=nodeB.next; // then move to next number of node
			}else{
				check2=0; // if check2 is 0, it means there is no more number on nodeB
			}
		}
		if(totalSum >= RADIX){
			sum.insert(1); // if two values add and make three numbers result then add 1 into first digit
		}
		return removeZero(sum); // return new linked list sum of two numbers, and erase unnecessary zero at the front using removeZero method
	}
	
	// subtract_nonneg_int(LLInt a, LLInt b) -- subtract two numbers between first input LLInt and Second input LLInt (x and y in driver program) (line: 59)
	public LLInt subtract_nonneg_int(LLInt a, LLInt b){
		LLInt bigNumber = null, smallNumber = null, subtract=null;
		Node bigNumberNode, smallNumberNode, findCarry, previousFindCarry = null;
		int bigNumberInt, smallNumberInt, check1=1, check2=1, totalNum=0;
		if(a.compareTo(b)==1){ // if a is bigger than b, then set up bigNumber = a smallNumber = b
			bigNumber=a;
			smallNumber=b;
		}else if(a.compareTo(b)==-1){ // if b is bigger than a, then set up bigNumber = b smallNumber = a
			bigNumber=b;
			smallNumber=a;
		}else if(a.compareTo(b)==0){ // if a and b have same value
			subtract = new LLInt(0);
			return subtract; // return 0 because subtract of two same number is 0
		}
		bigNumberNode = bigNumber.head; // bigNumberNode point to head of bigNumber
		smallNumberNode = smallNumber.head; // smallNumberNode point to head of smallNumber
		while(check1!=0 || check2!=0){ // if  first linked list number go to null then check1 = 0, if second linked list number is go to null then check2 = 0
			if(check1==1) // if first linked list is not null, then go into statement
				bigNumberInt = bigNumberNode.num; // save each digit number into bigNumberInt
			else
				bigNumberInt=0; // if first Linked list number go to null, then accepted digit number is 0
			if(check2==1) // if second linked list is not null, then go into statement
				smallNumberInt = smallNumberNode.num; // save each digit number into smallNumberInt
			else
				smallNumberInt=0; // if second Linked list number go to null, then accepted digit number is 0
			if(bigNumberInt < smallNumberInt){ // if small number digit is bigger then big Number digit, then big Number have to bring 10 from next digit
				findCarry = bigNumberNode.next;  // findCarry is bigNumberNode next
				while(findCarry.num == 0){ // findCaryy is find non zero number which can give 10
					findCarry = findCarry.next; // goes until there is no 0 digit number
				}
				findCarry.num--; // if find number, then minus 1 from that number
				previousFindCarry=bigNumberNode; // previousFindeCarry is previous of FindCarry
				while(bigNumberNode.next != findCarry){ // go until bigNumberNode next is findCarry
					previousFindCarry=bigNumberNode;
					while(previousFindCarry.next != findCarry){
						previousFindCarry = previousFindCarry.next;
					}
					findCarry = previousFindCarry;
					previousFindCarry.num = 9; // change all digit number 0 to 9. for example) 1006 - 8 = 998
				}
				previousFindCarry=bigNumberNode;
				previousFindCarry.num = previousFindCarry.num + RADIX; // add 10, for example) when we do 1006 - 8, 6 have to be add 10
				totalNum = previousFindCarry.num - smallNumberNode.num; // and 16-8 = 8
			}else if(bigNumberInt >= smallNumberInt) // if bigNumberInt digit is bigger than smallNumberInt digit
				totalNum = bigNumberInt - smallNumberInt; // then just calculate it, for example) 1009 - 6 = 1003, specifically 9-6 = 3
			if(subtract==null)
				subtract = new LLInt(totalNum); // make new LLInt head which is first subtract of two numbers
			else
				subtract.insert(totalNum); // keep add subtract digit of two numbers
			if(bigNumberNode.next!=null)
				bigNumberNode=bigNumberNode.next; // move to next number of first Node
			else
				check1=0; // if check1 is 0, it means there is no more number on nodeA which is first value of Stack
			if(smallNumberNode.next!=null)
				smallNumberNode=smallNumberNode.next; // move to next number of Second Node
			else
				check2=0; // if check2 is 0, it means there is no more number on nodeB which is second value of Stack
		}
		return removeZero(subtract); // return subtract number with removing unnecessary zero at the front
	}
	
	// String add(LLInt a, LLInt b) -- this method is main method to print out result sum of two numbers (line: 46)
	public LLInt add(LLInt a, LLInt b){
		int left = compareWithZero(a); // if a is positive number, then left = 0, if not left = 1
		int right = compareWithZero(b), negativeExist=0; // if b is positive number, then right = 0, if not right = 1
		LLInt finalValue;
		a = removeZero(a.node); // erase unnecessary zero at the front
		b = removeZero(b.node);	// erase unnecessary zero at the front
		if(left == 0 && right == 0){ // if a is positive, and b is positive
			finalValue = removeZero(add_nonneg_int(a, b)); // just add two numbers
			return finalValue; // return final Value with String
		}else if(left == 0 && right == 1){ // if a is positive, and b is negative
			LLInt bChangeSign = removeZero(negate(b)); // change the sign of b, then it becomes positive
			if(a.compareTo(bChangeSign) == 1){ // if a is bigger than b 
				finalValue = subtract_nonneg_int(a, bChangeSign); // then subtract a - b
			}else if(a.compareTo(bChangeSign)==-1){ // if b is bigger than a
				finalValue = negate(subtract_nonneg_int(a, bChangeSign)); // then b - a, and change sign
				negativeExist=1; 
			}else{
				finalValue = subtract_nonneg_int(a, bChangeSign); // if a and b is same value
			}
			if(negativeExist==1){ // print out negative number
				// print out negative sign
				// change negative to positive
			}
			finalValue = removeZero(finalValue); // erase unnecessary zero at front
			return finalValue; // return final value with String
		}else if(left == 1 && right == 0){ // if a is negative, and b is positive
			LLInt aChangeSign = removeZero(negate(a)); // a is negative, so change to positive
			if(aChangeSign.compareTo(b) == 1){ // if a is bigger than b
				finalValue = negate(subtract_nonneg_int(aChangeSign, b)); //then subtract two number and change sign
				negativeExist=1;
			}else{
				finalValue = subtract_nonneg_int(aChangeSign, b); // if a and b is same
			}
			if(negativeExist==1){ // if there is negative sign
				// print out negative
				// change negative to positive
			}
			finalValue = removeZero(finalValue); //remove unnecessary zero at front
			return finalValue; // return finalValue with String
		}else{ // if a is negative and b is negative
			LLInt changeLeftSign = negate(a); // change sign
			LLInt changeRightSign = negate(b); // chagne sign
			 // print out negative sign
			finalValue = removeZero(add_nonneg_int(changeLeftSign, changeRightSign)); // add a and b
			finalValue = negate(finalValue);
			return finalValue; // print out final value with String
		}
	}
	
	// String subtract(LLInt a, LLInt b) -- this method is main method to print out result sum of two numbers (line: 47)
	public LLInt subtract(LLInt a, LLInt b){
		LLInt finalValue=null; // final Value is set to null at first
		int left = compareWithZero(a); // if a is positive number, then left = 0, if not left = 1
		int right = compareWithZero(b); // if b is positive number, then right = 0, if not right = 1
		a = removeZero(a.node); // I erased unnecessary zero at the front in a
		b = removeZero(b.node); // I erased unnecessary zero at the front in b
		if(left==0 && right ==0){ // if a and b is positive
			if(a.compareTo(b) == 1){ // if a is bigger than b
				finalValue = subtract_nonneg_int(a, b); // just subtract a - b
			}else if(a.compareTo(b)==-1){ // if b is bigger than a
				finalValue = subtract_nonneg_int(a, b); // then subtract b - a
				finalValue = negate(finalValue); // print out minus sign
			}else{
				finalValue = subtract_nonneg_int(a, b); // if two number is same
			}
			finalValue = removeZero(finalValue); 
			return finalValue; // return final value with String
		}else if(left==0 && right==1){ // if a is positive, and b is negative
			b = negate(b); // changed sign of b
			finalValue = add_nonneg_int(a, b); // add two value a + b
			finalValue = removeZero(finalValue); 
			return finalValue; // return final value with String
		}else if(left==1 && right==0){ // if a is negative, and b is positive
			a = negate(a); // change sign of a
			finalValue = add_nonneg_int(a, b); // a + b
			finalValue = negate(finalValue); // print out negative sign
			finalValue = removeZero(finalValue);
			return finalValue; // print out final value with String
		}else{ // if a and b is both negative
			a = negate(a); // change sign of a
			b = negate(b); // change sign of b
			a = removeZero(a); 
			b = removeZero(b);
			if(a.compareTo(b)==1){ // if a is bigger than b
				finalValue = subtract_nonneg_int(a, b); // a - b
				finalValue = negate(finalValue); // print minus
				finalValue = removeZero(finalValue);
				return finalValue; // print final value with String
			}else if(a.compareTo(b)==-1){ // if b is bigger than a
				finalValue = subtract_nonneg_int(a, b); // b - a
				finalValue = removeZero(finalValue);
				return finalValue; // return final value with String
			}else{
				finalValue = subtract_nonneg_int(a, b); // if a and b is same number
			}
		}
		return finalValue; //return final value with String
	}
	
	// LLInt negate(LLInt input) -- this method change sign of input (line: 20)
	public LLInt negate(LLInt input){
		Node negative = input.head; // negative node point head of input
		Node previousNegative; // this is previous node of negative node
		while(negative.next != null){ // go until input node is null
			negative = negative.next;
		}
		if(negative.num == '-'){ // if end of node value is negative
			previousNegative = input.head; // set up previous Negative 
			while(previousNegative.next.num != '-'){ // go before minus sign
				previousNegative = previousNegative.next;
			//	System.out.println(previousNegative.num);
			}
			//System.out.println(negative.num);
			previousNegative.next=null; // if there is negative sign, then erase sign, and put null
		}else{ // if there is no minus sign
			Node minus = new Node('-');
			negative.next = minus; // if it is not negative, then add negative sign
		}
		
		return input; // return new input
	}
	
	// int compareWithZero(LLInt input) -- This method is compare input number with zero, if input number is positive then return 0, if not return 1 (line: 10)
	public int compareWithZero(LLInt input){
		int negativeExist = 0;
		Node copyHead = input.node.head;
		while(copyHead.next != null){
			copyHead = copyHead.next;
		}
		if(copyHead.num == '-'){ // if there is minus sign, then it should be negative number, if not then it should be positive number
			negativeExist = 1;
		}
		return negativeExist; // return 0 or 1
	}
	
	// LLInt removeZero(LLInt input) -- This method erase 0 if there is 0 on the first digit, ex) 001209 = 1209 (line: 35)
	public LLInt removeZero(LLInt input){
		int negativeAtFront=0, count=0;
		Node sumHead, current, next;
		LLInt result = input; // Result point to input, and will return final value
		sumHead = input.head; // sumHead point to input of head
		current = sumHead; // current point sumHead
		next = current.next; // next is next of current node
		// this while loop go to end of input node, next node is right before null, and current is right before next
		while(sumHead.next != null){
			sumHead = sumHead.next;
			count++;
		}
		if(count==0){ // if length is 1, then return original value
			return result;
		}
		while(next.next != null){ // go until next's next is null
			current=current.next;
			next = current.next;
		}
		// this loop erased unnecessary 0
		while(next.num == 0){
			current.next = null; // make null if there is unnecessary 0
			current = input.head; // set current point to head
			next = current.next; // set next is next of current
			if(next != null){ // if next is null then it goes to else loop
				while(next.next != null){
					current=current.next;
					next = current.next;
				}
			}else{
				negativeAtFront=0;
				break; // if result is 0, then break it
			}
		}
		return result; // return final result
	}
	
	public LLInt ko_multiply(LLInt a, LLInt b){
		int n;
		LLInt answer;
		n = minimumSize(a, b);
		if(n==1){
			return one_digit_multiply_basecase(a, b);
		}
		
		AC = ko_multiply(A, C);
		BD = ko_multiply(B, D);
		F = ko_multiply(subtract(A, B), subtract(C, D));
		answer = result(AC, BD, F, n);
		return answer;
	}
	
	public LLInt result(LLInt AC, LLInt BD, LLInt F, int n){
		LLInt result;
		LLInt left;
		LLInt middle;
		LLInt right;
		left = addZero(AC, 2*n);
		middle = add(AC, BD);
		middle = subtract(middle, F);
		middle = addZero(middle, n);
		right = BD;
		result = add(left, middle);
		result = add(result, right);
		return result;
	}
	
	public LLInt addZero(LLInt value, int n){
		Node zero;
		for(int a=1; a < n; a++){
			zero = new Node(0);
			zero.next = value.node.head;
			value.node.head = zero;
		}
		return value;
	}
	private LLInt one_digit_multiply_basecase(LLInt a, LLInt b) {
		Node copyA = a.head;
		Node copyB = b.head;
		String stringTotal;
		LLInt basecase;
		int trueOrFalse = 0;
		int valueA = copyA.num;
		int valueB = copyB.num;
		int total = valueA * valueB;
		while(copyA != null){
			if(copyA.num == '-'){
				trueOrFalse = 1;
			}
			copyA = copyA.next;
		}
		if(trueOrFalse==0){
			stringTotal = Integer.toString(total); // change integer to String		
		}else{
			total = total * -1;
			stringTotal = Integer.toString(total);
		}
		basecase = new LLInt(stringTotal);
		return basecase;
	}

	private int minimumSize(LLInt a, LLInt b) {
		int left=1, right=1, sameDigit;
		Node copyA = a.head;
		Node copyB = b.head;
		
		while(copyA.next != null){
			copyA = copyA.next;
			if(copyA.num == '-'){
				
			}else{
				left++;
			}
		}
		while(copyB.next != null){
			copyB = copyB.next;
			if(copyB.num == '-'){
				
			}else{
				right++;
			}
		}
		if(left > right){
			return right;
		}else if(left < right){
			return left;
		}else{
			sameDigit=right;
			return sameDigit;
		}
	}

	// String toString() -- return final Value by string (line: 8)
	public String toString() {
		Node temp=head;
		StringBuffer result=new StringBuffer();
		while(temp!=null){
			result.insert(0,""+temp.num);
			temp=temp.next;
		}
		return ""+result;
	}
	
	// int compareTo(LLInt b) -- compare two numbers and return which is more big using 0 or 1 (line: 41)
	@Override
	public int compareTo(LLInt b) {
		// TODO Auto-generated method stub
		LLInt left=this, right=b; // left is a, right is b
		Node leftHead=left.head, rightHead=right.head; // leftHead is head of a, and rightHead is head of b
		int lengthLeft=0, lengthRight=0, result=0, count, copyCount;
		while(leftHead != null){ // count length of a
			lengthLeft++;
			leftHead = leftHead.next;
		}
		while(rightHead != null){ // count length of b
			lengthRight++;
			rightHead = rightHead.next;
		}
		if(lengthLeft > lengthRight){ // if length of a is big that means a is bigger, then result is 1
			result=1;
		}else if(lengthRight > lengthLeft){ // if length of b is big that means b is bigger, then result is -1
			result=-1;
		}else if(lengthRight==lengthLeft){ // if length of a and b is same, then compare each digit number
			count=lengthLeft; // count is length of a and b
			result = 0;
			while(count != 0){ // loop until count is 0
				leftHead = left.head; // leftHead is head of a
				rightHead = right.head; // rightHead is head of b
				copyCount = count; // copy length of a and b
				while(copyCount != 1){ // loop until most significant
					copyCount--;
					leftHead = leftHead.next;
					rightHead = rightHead.next;
				}
				if(leftHead.num > rightHead.num){ // if left most significant number is big, then left is bigger than right, return 1
					result=1;
					break; // already determine which one is big, so break
				}else if(leftHead.num < rightHead.num){ // if right most significant number is big, then right is bigger than left, return -1
					result=-1;
					break; // already determine which one is big, so break
				}
				count--; // count = count - 1
			}
			
		}
		return result; // return 1 or 0, which number is big
	}
	
	// Node class -- digit number is save into node, and next point to null (line: 8)
	public static class Node{
		Node next;
		int num;
		public Node(int num){
			this.num=num;
			next=null;
		}
		
	}
}
