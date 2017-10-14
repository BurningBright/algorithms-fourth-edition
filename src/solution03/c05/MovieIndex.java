package solution03.c05;

import rlgs4.Bag;
import rlgs4.ST;
import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

/******************************************************************************
 *  Compilation:  javac MovieIndex.java
 *  Execution:    java MovieIndex movies.txt
 *  Dependencies: ST.java Bag.java In.java StdIn.java
 *
 *  % java MovieIndex movies-top-grossing.txt
 *  Stallone, Sylvester
 *    Rambo: First Blood Part II (1985)
 *    Rocky (1976)
 *    Rocky III (1982)
 *    Rocky IV (1985)
 *
 *  Hanks, Tom
 *    Apollo 13 (1995)
 *    Big (1988)
 *    Forrest Gump (1994)
 *    Green Mile, The (1999)
 *    League of Their Own, A (1992)
 *    Saving Private Ryan (1998)
 *    Sleepless in Seattle (1993)
 *    Toy Story (1995)
 *    Toy Story 2 (1999)
 *
 *  Apollo 13 (1995)
 *    Allen, Ivan
 *    Andrews, David
 *    Bacon, Kevin
 *    Barry, Thom
 *    Berkeley, Xander
 *    ...
 *
 ******************************************************************************/


public class MovieIndex { 

    public static void main(String[] args) {

        // key = actor / movie, value = list of movies / actors
        ST<String, Bag<String>> st = new ST<String, Bag<String>>();

        // create inverted index of all files
        In in = new In(args[0]);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] names = line.split("/");
            String movie = names[0];
            for (int i = 1; i < names.length; i++) {
                String actor = names[i];
                if (!st.contains(actor)) st.put(actor, new Bag<String>());
                if (!st.contains(movie)) st.put(movie, new Bag<String>());
                st.get(actor).add(movie);
                st.get(movie).add(actor);
            }
        }
        StdOut.println("Done indexing files");
        StdOut.println();

        StdOut.println("Type the name of a performer or movie");
        while (!StdIn.isEmpty()) {
            String name = StdIn.readLine();
            if (st.contains(name)) {
                for (String s : st.get(name))
                    StdOut.println("  " + s);
            }
            StdOut.println();
        }
    }

}
