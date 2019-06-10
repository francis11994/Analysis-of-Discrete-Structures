class AddDriver {
    public static void main(String[] args)
        throws InstantiationException
    {
        final LLInt x = new LLInt(args[0]), y = new LLInt(args[1]);

        /*
         * ADAPT THE NEXT PART AS REQUIRED, TO CALL YOUR add(),
         * WITH WHATEVER SIGNATURE YOU CHOSE:
         */

        System.out.println("" + x.add(x,y));

        /////// COMMON ALTERNATIVES: ///////////
        //System.out.println("" + LLInt.add(x, y));
        //System.out.println("" + x.add(x, y));  // ick
    }
}