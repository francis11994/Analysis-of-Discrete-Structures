class SubtractDriver {
    public static void main(String[] args)
        throws InstantiationException
    {
        final LLInt x = new LLInt(args[0]), y = new LLInt(args[1]);

        /*
         * ADAPT THE NEXT PART AS REQUIRED, TO CALL YOUR subtract(),
         * WITH WHATEVER SIGNATURE YOU CHOSE:
         */

        System.out.println("" + x.subtract(x, y));

        /////// COMMON ALTERNATIVES: ///////////
        //System.out.println("" + LLInt.subtract(x, y));
        //System.out.println("" + x.subtract(x, y));  // ick
    }
}