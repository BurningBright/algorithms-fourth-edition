package solution01.c1;

import stdlib.StdIn;
import stdlib.StdOut;
import stdlib.StdRandom;

/******************************************************************************
 *  Compilation:  javac Sattolo.java
 *  Execution:    java Sattolo < list.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/11model/cards.txt
 *  
 *  Reads in a list of strings and prints a uniformly random cycle
 *  using Sattolo's algorithm under the assumption that Math.random()
 *  generates independent and uniformly distributed numbers between
 *  0 and 1.
 *
 *  %  echo 0 1 2 3 4 | java Sattolo
 *  1
 *  2
 *  4
 *  0
 *  3
 *
 ******************************************************************************/

/**
 *  The <tt>Sattolo</tt> class provides a client for reading in a 
 *  sequence of <em>N</em> strings and computing a <em>unifomly random cycle</em>
 *  if length <em>N</em> using the Sattolo's algorithm.
 *  This algorithm guarantees to produce a unifomly random cycle under
 *  the assumption that Math.random() generates independent and
 *  uniformly distributed numbers between 0 and 1.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/11model">Section 1.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class Sattolo { 

    // this class should not be instantiated
    private Sattolo() { }

    /**
     * Rearranges an array of objects to be a uniformly random cycle
     * (under the assumption that <tt>Math.random()</tt> generates independent
     * and uniformly distributed numbers between 0 and 1).
     * @param a the array to be rearranged
     * @see StdRandom
     */
    public static void cycle(Object[] a) {
        int N = a.length;
        for (int i = N; i > 1; i--) {
            // choose index uniformly in [0, i-1)
            int r = (int) (Math.random() * (i-1));
            Object swap = a[r];
            a[r] = a[i-1];
            a[i-1] = swap;
        }
    }

    /**
     * Reads in a sequence of strings from standard input, shuffles
     * them, and prints out the results.
     */
    public static void main(String[] args) {

        // read in the data
        String[] a = StdIn.readAllStrings();

        // shuffle the array
        Sattolo.cycle(a);

        // print results.
        for (int i = 0; i < a.length; i++)
            StdOut.println(a[i]);
    }
}
