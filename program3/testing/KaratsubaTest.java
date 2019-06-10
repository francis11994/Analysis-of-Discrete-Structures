/**
 * @author Andrew Predoehl
 * 
 * For CSC 345 Fall 2016, project 2:  empirically testing speed of the
 * Karatsuba-Ofman integer multiplication algorithm, compared to the
 * conventional elementary-school algorithm.
 *
 * This repeats the experiment "reps" times, so as to average out the
 * random variation due to unpredictable integers.  The first 10% of the
 * test is not counted because the JVM does runtime optimization, and we
 * don't want those effects to be mixed into the timing measurements.
 *
 * Version 2 (2016-09-14), tested.
 */
class KaratsubaTest
{
    static private java.util.Random gen = new java.util.Random();

    // build random string
    private static String get_random_ndigit_int_string(int num_digits)
    {
        java.lang.StringBuilder s = new java.lang.StringBuilder();
        s.append("" + (1 + gen.nextInt(9)));
        while(--num_digits > 0)
            s.append("" + gen.nextInt(10));
        return s.toString();
    }

    /**
     * Print final results.
     *
     * @param n         Number of digits per product in all tests
     * @param t_ko_ms   Average time for Karatsuba-Ofman multiplication,
     *                  per pair of factors, each of size n.
     * @param t_es_ms   Average time for elementary-school algorithm to
     *                  multiply, per pair of factors, each of size n.
     *
     * Output is sent to stdout, all on one line to make it easier to pipe
     * the lines into a file and import the file into a spreadsheet.
     */
    private static void print_results(int n, float t_ko_ms, float t_es_ms)
    {
        System.out.println(
                    "Factor size (# digits): " + n
                    + " \tKaratsuba time (ms): " + t_ko_ms
                    + " \tConventional time (ms): " + t_es_ms);
    }

    // Test whether number of digits is valid:  n is the number of digits
    // that the user asked for.  The value should be positive, that's all.
    private static void validate(int n)
    {
        if (n <= 0) {
            System.err.println("Error:  test input size (number of "
                + "digits) must be positive.");
            System.exit(1);
        }
    }

    /**
     * Perform speed test of multiplication algorithms.
     * Input parameter is the standard array of command-line arguments.
     */
    public static void main( String[] argv )
        throws InstantiationException
    {
        // Get number of digits from command line arguments:
        final int n = Integer.parseInt( argv[0] ),
                  reps = 1000, // repeat experiment for average time
                  trigger = reps / 10;

        // Check the input value.
        validate(n);

        // Storage for random factors
        LLInt[] factor_x = new LLInt[reps], factor_y = new LLInt[reps],
                es_product = new LLInt[reps], ko_product = new LLInt[reps];

        // Generate random factors, all of size n digits.
        for (int i = 0; i < reps; ++i) {
            factor_x[i] = new LLInt(get_random_ndigit_int_string(n));
            factor_y[i] = new LLInt(get_random_ndigit_int_string(n));
        }

        // Storage for start times
        long es_starttime = 0, ko_starttime = 0;

        for (int j = 0; j < reps; ++j) {
            // First 10% of time is to warm up the compiler,
            // last 90% of time is test time.
            if (j == trigger) {
                java.lang.System.gc();
                java.lang.System.runFinalization();
                es_starttime = java.lang.System.currentTimeMillis();
            }

            // Multiply these numbers using the elementary-school
            // algorithm.  (Adapt the next line if necessary.)
            es_product[j] = factor_x[j].es_multiply(factor_x[j], factor_y[j]);
        }
        final long es_endtime = java.lang.System.currentTimeMillis();

        for (int j = 0; j < reps; ++j) { // test counter
            if (j == trigger) {
                java.lang.System.gc();
                java.lang.System.runFinalization();
                ko_starttime = java.lang.System.currentTimeMillis();
            }

            // Multiply these numbers using Karatsuba's clever idea.
            // (Adapt this line if necessary.)
            ko_product[j] = factor_x[j].ko_multiply(factor_x[j], factor_y[j]);
        }
        final long ko_endtime = java.lang.System.currentTimeMillis();

        // Compute average time and print out the timing results.
        float ko_avg_time_ms=(ko_endtime-ko_starttime)/(float)(reps-trigger),
              es_avg_time_ms=(es_endtime-es_starttime)/(float)(reps-trigger);

        print_results(n, ko_avg_time_ms, es_avg_time_ms);

        // Verify that the results are identical
        for (int j = 0; j < reps; ++j)
            assert( 0 == ko_product[j].compareTo(es_product[j]) );
    }
}