package solution03.c05;

import rlgs4.ST;
import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

/******************************************************************************
 *  Compilation:  javac Concordance.java
 *  Execution:    java Concordance < input.txt
 *  Dependencies: ST.java SET.java In.java StdIn.java
 *
 *  % java Concordane tale.txt
 *  cities
 *  tongues of the two *cities* that were blended in 
 *
 *  majesty
 *  their turnkeys and the *majesty* of the law fired 
 *  me treason against the *majesty* of the people in 
 *  of his most gracious *majesty* king george the third 
 *
 *  princeton
 *
 *  Build a concordance for a text corpus.
 *
 ******************************************************************************/


public class Concordance {

    public static void main(String[] args) {
        int CONTEXT = 5;

        In in = new In(args[0]);
        String[] words = in.readAllStrings();
        ST<String, SET<Integer>> st = new ST<String, SET<Integer>>();

        // build up concordance
        for (int i = 0; i < words.length; i++) {
            String s = words[i];
            if (!st.contains(s)) {
                st.put(s, new SET<Integer>());
            }
            SET<Integer> set = st.get(s);
            set.add(i);
        }
        StdOut.println("Finished building concordance");

        // process queries
        while (!StdIn.isEmpty()) {
            String query = StdIn.readString();
            SET<Integer> set = st.get(query);
            if (set == null) set = new SET<Integer>();
            for (int k : set) {
                for (int i = Math.max(0, k - CONTEXT + 1); i < k; i++)
                    StdOut.print(words[i] + " ");
                StdOut.print("*" + words[k] + "* ");
                for (int i = k + 1; i < Math.min(k + CONTEXT, words.length); i++)
                    StdOut.print(words[i] + " ");
                StdOut.println();
            }
            StdOut.println();
        }
   
    }
}
