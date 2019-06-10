/*
 * Author: Francis Kim
 * Professor: Andrew Predoehl
 * class: CSC345
 * Due Date: 10/14/2016 6:00 am
 * Purpose: Do the es_multiply and ko_multiply
 * Description :We want to compare es_multiply() and ko_multiply(), to determine which is faster under various conditions.
 * 				And we want to optimize the size of the base case in ko_multiply() to determine what value gives best performance.
 *				Using  AC * 10^2N + 10*N(AC + BD - F) + BD formula we will get ko_multiply with recursion.
 *				es_multiply() is just elementary multiply system. Therefore, we will compare ko_multiply and es_multiply.
 */

// I used Comparable<LLInt> to compare two numbers
public class LLInt implements Comparable<LLInt> {
	
	
	private LLInt node;	// this LLInt node make new LLInt to create linked list
	private int totalSum; // this will be use in add_nonneg_int method, this is each digit of sum
	Node head=null; // this is point head in linked list,
	private static final int RADIX=10; // I represent decimal number, so I used RADIX
	private String number; // This is input number as String
	private int AhasNegative=0, BhasNegative=0, count=0, change=0;
	
	
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
			
			if(head==null){ // if node is null, then make first node which is head
				head = new Node(digit); // create new node and save into node
			}else{
				this.insert(digit); // we made head, so we can insert each digit number and connect.
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
		a = removeZero(a); // erase unnecessary zero at the front
		b = removeZero(b);	// erase unnecessary zero at the front
		if(left == 0 && right == 0){ // if a is positive, and b is positive
			finalValue = removeZero(add_nonneg_int(a, b)); // just add two numbers
			 // return final Value with String
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
			// return final value with String
			b = negate(b);
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
			a = negate(a);
			finalValue = removeZero(finalValue); //remove unnecessary zero at front
			 // return finalValue with String
		}else{ // if a is negative and b is negative
			LLInt changeLeftSign = negate(a); // change sign
			LLInt changeRightSign = negate(b); // change sign
			 // print out negative sign
			finalValue = removeZero(add_nonneg_int(changeLeftSign, changeRightSign)); // add a and b
			finalValue = negate(finalValue);
			 // print out final value with String
			a = negate(a);
			b = negate(b);
		}
		
		return finalValue;
	}
	
	// String subtract(LLInt a, LLInt b) -- this method is main method to print out result sum of two numbers (line: 47)
	public LLInt subtract(LLInt a, LLInt b){
		LLInt finalValue=null; // final Value is set to null at first
		int left = compareWithZero(a); // if a is positive number, then left = 0, if not left = 1
		int right = compareWithZero(b); // if b is positive number, then right = 0, if not right = 1
		a = removeZero(a); // I erased unnecessary zero at the front in a
		b = removeZero(b); // I erased unnecessary zero at the front in b
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
			// return final value with String
		}else if(left==0 && right==1){ // if a is positive, and b is negative
			b = negate(b); // changed sign of b
			finalValue = add_nonneg_int(a, b); // add two value a + b
			finalValue = removeZero(finalValue); 
			b = negate(b);
			// return final value with String
		}else if(left==1 && right==0){ // if a is negative, and b is positive
			a = negate(a); // change sign of a
			finalValue = add_nonneg_int(a, b); // a + b
			finalValue = negate(finalValue); // print out negative sign
			finalValue = removeZero(finalValue);
			a = negate(a);
			// print out final value with String
		}else{ // if a and b is both negative
			a = negate(a); // change sign of a
			b = negate(b); // change sign of b
			a = removeZero(a); 
			b = removeZero(b);
			if(a.compareTo(b)==1){ // if a is bigger than b
				finalValue = subtract_nonneg_int(a, b); // a - b
				finalValue = negate(finalValue); // print minus
				finalValue = removeZero(finalValue);
				 // print final value with String
			}else if(a.compareTo(b)==-1){ // if b is bigger than a
				
				finalValue = subtract_nonneg_int(a, b); // b - a
				finalValue = removeZero(finalValue); // remove unnecessary zero in finalValue
				 // return final value with String
			}else{
				finalValue = subtract_nonneg_int(a, b); // if a and b is same number
			}
			a = negate(a); // change sign of a
			b = negate(b); // change sign of b
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
		Node copyHead = input.head;
		while(copyHead.next != null){
			copyHead = copyHead.next;
		}
		if(copyHead.num == '-'){ // if there is minus sign, then it should be negative number, if not then it should be positive number
			negativeExist = 1;
			//System.out.println("ee");
		}
		return negativeExist; // return 0 or 1
	}
	
	// LLInt removeZero(LLInt input) -- This method erase 0 if there is 0 on the first digit, ex) 001209 = 1209 (line: 35)
	public LLInt removeZero(LLInt input){
		int negativeAtFront=0, count=0, zeroCheck=0;
		Node sumHead, current, next;
		LLInt newNode=null;
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
	// LLInt es_multiply(LLInt a, LLInt b) -- return finalValue which multiply a and b
	public LLInt es_multiply(LLInt a, LLInt b){
		LLInt finalValue = null; // finalValue set to null
		int left = compareWithZero(a); // compare a with 0
		int right = compareWithZero(b); // compare b with 0
		int negative=0;
		a = removeZero(a); // remove zero in a
		b = removeZero(b); // remove zero in b
		
		if(left == 0 && right == 0){	// if a and b both positive
			finalValue = multiplication(a, b);
		}else if(left == 0 && right == 1){ // if a is positive, and b is negative
			b = negate(b);
			finalValue = multiplication(a, b);
			negative=1;
		}else if(left == 1 && right == 0){ // if a is negative, and b is positive
			a = negate(a);
			finalValue = multiplication(a, b);
			negative=1;
		}else{ // if a and b both negative
			a = negate(a); // change negative to positive
			b = negate(b); // change negative to positive
			finalValue = multiplication(a, b);
		}
		if(negative==1 && (finalValue.toString().equals("0")==false)){ // if it is negative and not zero print negative sign
			System.out.print("-");
		}
		return finalValue;
	}
	public LLInt multiplication(LLInt a, LLInt b){
		int count_b=0; // the number of digits in LLInt a which is second node from the Stack
		
		LLInt mul=new LLInt(0); // set initial value
		Node a_node=a.head, b_node=b.head;
		while(b_node!=null){
			int count_a=0; // the number of digits in LLInt a which is first node from the Stack
			Node a_temp=a_node;
			while(a_temp!=null){
				mul=add_nonneg_int(multiplicationDegit(count_a, count_b,a_temp.num,b_node.num),mul); // do addition of each multiplicaion number
				a_temp=a_temp.next;
				count_a++;
			}
			count_b++;
			b_node=b_node.next;
		}
		
		return mul; //print mul.head.num
	}
	

	// Multiply each digit number and return result
	private LLInt multiplicationDegit(int a, int b, int num, int num2) {
		LLInt result=null; // this is final value
		Node temp=null; // this is temporary variable
		if(a+b>0){ // if sum of digits number of a and b bigger than 0
			result=new LLInt(0);
			temp=result.head;
			int i=1;
			while(i<a+b){ // append 0
				temp.next=new Node(0);
				temp=temp.next;
				i++;
			}
			num=num*num2;	// this is multiply digit with digit
			
			if(num<RADIX)	// if num is less than 10
				temp.next=new Node(num); // save left number into node when multiply is less than 10
			else{
				temp.next=new Node(num%RADIX); // save left number into node
				temp.next.next=new Node(num/RADIX); // save real number into node
			}
		}
		else{ // if sum of digits number of a and b less or equal to 0, this is first time execute
			num=num*num2; // multiply each digit number
			if(num<10)
				result=new LLInt(num); // put first digit number into node
			else{
				result=new LLInt(num%RADIX); // put remain number into node
				result.head.next=new Node(num/RADIX);	// put main number into node
			}
		}
		return result;
	}
	
	// LLInt ko_multiply(LLInt a, LLInt b) -- this is execute ko_multiply
	public LLInt ko_multiply(LLInt a, LLInt b){
		LLInt finalValue = null; // finalValue set to null
		int left = compareWithZero(a); // a compare with 0
		int right = compareWithZero(b); // b compare with 0
		int negative=0;
		a = removeZero(a); // remove unnecessary 0 in a
		b = removeZero(b); // remove unnecessary 0 in b
		//System.out.println(a.toString());
		if(left==0 && right==0){ // if a and b both positive
			finalValue = ko_multiplication(a, b); 
		}else if(left==0 && right==1){
			b = negate(b); // change b sign
			negative=1;
			finalValue = ko_multiplication(a, b);
		}else if(left==1 && right==0){
			a = negate(a); // change a sign
			negative=1;
			finalValue = ko_multiplication(a, b);
		}else{
			a = negate(a); // change a sign
			b = negate(b); // change b sign
			finalValue = ko_multiplication(a, b);
		}
		if(negative==1 && (finalValue.toString().equals("0")==false)){ // if it is negative and not zero print negative sign
			System.out.print("-");
		}
		return finalValue;
	}

	public LLInt ko_multiplication(LLInt a, LLInt b){
		int n; //LEFT FIRST
		LLInt answer;
		LLInt A, B, C, D, AC, BD, F;

		n = minimumSize(a, b); // return small length one of a and b
		
		if(n==1){
			return one_digit_multiply_base_case(a, b);
		}
		
		if(checkNegative(a)==0){	// if a is negative
			A = split(a, n/2, 0);	// split A
			B = split(a, n/2, 1);	// split B
		}else{
			A = split(a, n/2, 0);	// split A
			B = negate(split(a,n/2, 1)); // split B with negative sign
		}
		
		if(checkNegative(b)==0){	// if b is negative
			C = split(b, n/2, 0);	// split C
			D = split(b, n/2, 1);	// split D
		}else{
			
			C = split(b, n/2, 0);	// split C
			D = negate(split(b, n/2, 1));
		}
		AC = ko_multiplication(A, C);	// recursive to get AC
		BD = ko_multiplication(B, D);	// recursive to get BD
		F = ko_multiplication(subtract(A, B), subtract(C, D));	// recursive to get F
		answer = result(AC, BD, F, n/2);	// get answer with formula
	
		return answer;
	}
	
	public LLInt one_digit_multiply_base_case(LLInt a, LLInt b){
		
		LLInt finalValue = null;
		int left = compareWithZero(a);	// compare 0 with a
		int right = compareWithZero(b); // compare 0 with b
		
		a = removeZero(a);	// remove a
		b = removeZero(b);	// remove b
		
		if(left == 0 && right == 0){	// a and b both positive
			finalValue = multiplication(a, b);
		}else if(left == 0 && right == 1){	// if a positive, and b negative
			b = negate(b);
			finalValue = multiplication(a, b);
			finalValue = negate(finalValue);
			b = negate(b);
		}else if(left == 1 && right == 0){	// if a negative, and b positive
			a = negate(a);
			finalValue = multiplication(a, b);
			finalValue = negate(finalValue);
			a = negate(a);
		}else{	// if a negative, and b negative
			a = negate(a);
			b = negate(b);
			finalValue = multiplication(a, b);
			a = negate(a);
			b = negate(b);
		}
		
		return finalValue;
	}
	
	// int checkNegative(LLInt c) -- check it is negative number
	public int checkNegative(LLInt c){
		Node headC = c.head;	
		// iterate until c head reach null
		while(headC.next != null){
			headC = headC.next;
		}
		//System.out.println(headC.num);
		if(headC.num == '-'){ // if last number is negative sign
			return 1;	// return 1
		}
		return 0;
	}
	
	// LLInt split(LLInt wholeNumber, int n, int leftOrRight) -- return split LLInt
	public LLInt split(LLInt wholeNumber, int n, int leftOrRight){

		LLInt llint=null;
		Node copyHead = wholeNumber.head;
		int i=1;
		if(leftOrRight==1){	// this is return right side of split LLInt
			for(int a=1; a <= n; a++){
				if(a==1){
					llint = new LLInt(copyHead.num);	//make initial node which has first right digit
				}else{
					llint.insert(copyHead.num);	// insert next number
				}
				
				copyHead = copyHead.next;
			}
		
			return llint;	// return right side of LLInt
		}else{	// this is return left side of split LLInt
			for(int a=1; a <= n; a++){	// iterate until first digit of left side
				copyHead = copyHead.next;
			}
			while(copyHead != null){	// loop until head is equal to null
				if(i==1){
					llint = new LLInt(copyHead.num);	// make initial digit number
				}else{
					llint.insert(copyHead.num);	// insert next number
				}
				copyHead = copyHead.next;	
				i++;
			}
	
			return llint;	// return left side of LLInt
			
		}
		
	}
	
	// LLInt result(LLInt AC, LLInt BD, LLInt F, int n) -- return answer with ko_multiply formula
	public LLInt result(LLInt AC, LLInt BD, LLInt F, int n){
		LLInt result;
		LLInt left;
		LLInt middle;
		LLInt right;
		left = addZero(AC, 2*n); // AC * 10^2N
		middle = add(AC, BD);	// AC + BD
		middle = subtract(middle, F);	// AC + BD - F
		middle = addZero(middle, n);	// 10*N(AC + BD - F)
		right = BD;
		result = add(left, middle);	// AC * 10^2N + 10*N(AC + BD - F)
		result = add(result, right); // AC * 10^2N + 10*N(AC + BD - F) + BD
		
		return result; // return AC * 10^2N + 10*N(AC + BD - F) + BD
	}
	
	// addZero(LLInt value, int n) -- add zero which means time 10^N
	public LLInt addZero(LLInt value, int n){
		LLInt result=null;
		int b=1;
		Node zero;
		Node valueHead = value.head; 
		
		// copy value number
		while(valueHead != null){
			if(b==1){
				result = new LLInt(valueHead.num);
			}else{
				result.insert(valueHead.num);
			}
			valueHead = valueHead.next;
			b++;
		}
		
		// add zero at the end which means multiply 10^N
		for(int a=1; a <= n; a++){
			zero = new Node(0);
			zero.next = result.head;
			result.head = zero;
		}
		return result; // return value * 10^2N
	}

	// int minimumSize(LLInt a, LLInt b) -- return minimum length of a and b
	private int minimumSize(LLInt a, LLInt b) {
		int left=1, right=1, sameDigit;
		Node copyA = a.head;
		Node copyB = b.head;
		//count legnth of a
		while(copyA.next != null){
			
			copyA = copyA.next;
			if(copyA.num == '-'){ // if there is negative sign, then skip
			
			}else{
				left++; // increase length of a
			}
		}
		// count length of b
		while(copyB.next != null){
			copyB = copyB.next;
			if(copyB.num == '-'){	// if there is negative sign, then skip
				
			}else{
				right++;	// increase length of b
			}
		}
		
		// compare length between a and b
		if(left > right){ // if length a is large, then return b length
			return right;
		}else if(left < right){ // if length b is large, then return a length
			return left;
		}else{	// if length is same
			sameDigit=right;
			return sameDigit;	// return same length
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
