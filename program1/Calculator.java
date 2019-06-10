/* 
 * Name: Francis Kim
 * Project: Program 1
 * Purpose: Do the calculation addition and multiply using node and Stack.
 */
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

/**
 * Basic command-line interface to StackCalc class, which does the real work.
 *
 * This handles reading from standard input, and writing to standard output
 * or standard error.
 */
class Calculator {

    public static void main(String[] args)
        throws java.io.IOException, java.lang.InstantiationException
    {
        // The calculator object does all the interesting work:
        StackCalc calculator = new StackCalc();

        // This flag is true iff the next input token should be a number.
        // In the language of automata, this is called the "state."
        boolean expecting_number = false;
        
        // Read from standard input
        BufferedReader br = new BufferedReader(
                                new InputStreamReader(System.in));
        
        // Loop through the lines of input, and divide each line at whitespace:
        for (String line = br.readLine(); line != null; line = br.readLine())
            for (StringTokenizer tz = new StringTokenizer(line);
                    tz.hasMoreTokens(); ) {

                // Next word or number to be handled:
                final String token = tz.nextToken();
                // What we do next depends on the token and the state:
                if (expecting_number) {
                    /*
                     * If "token" can be interpreted as a nonnegative integer,
                     * then push it on the stack.
                     * Otherwise, throw an exception.
                     */
                	
                    calculator.push(token);
                    
                    expecting_number = false;
                }
                else if (0 == token.compareTo("push"))
                    expecting_number = true;
                else if (0 == token.compareTo("add"))
                    System.out.println(calculator.add());
                else if (0 == token.compareTo("multiply"))
                    System.out.println(calculator.multiply());

                // Bad input, e.g., "multiply" -- sorry, no second chances!!
                else {
                    System.err.println("Error:  unexpected input (received " +
                        token + ", expected one of {push,add,multiply}).");
                    System.exit(1);
                }
            }
    }
    
 
}
