class MultiplyDriver {
    public static void main(String[] args)
        throws InstantiationException
    {
        final LLInt x = new LLInt(args[0]), y = new LLInt(args[1]);

        /*
         * You can adapt the next line if necessary, but keep the
         * changes simple -- do not add loops.
         */
        LLInt es_prod = x.es_multiply(x,y), ko_prod = x.ko_multiply(x,y);

        /////// STANDARD ALTERNATIVES: ///////////
        // LLInt es_product = x.es_multiply(x,y), ...
        // LLInt es_product = LLInt.es_multiply(x,y), ...
        // and similar stuff.

        String ess = es_prod.toString(), kos = ko_prod.toString();

        if (ess.equals(kos))
            System.out.println(ess);
        else {
            System.err.println("Error:  results disagree."
                                + "\n\tes_multiply: " + ess
                                + "\n\tko_multiply: " + kos);
            System.exit(1);
        }
    }
}
