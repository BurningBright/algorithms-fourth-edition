package solution_c.c03;

import stdlib.StdIn;
import stdlib.StdOut;

/******************************************************************************
 *  Compilation:  javac Manber.java
 *  Execution:    java Manber < text.txt
 *  Dependencies: StdIn.java
 *  
 *  Reads a text corpus from stdin and sorts the suffixes
 *  in subquadratic time using a variant of Manber's algorithm.
 *
 ******************************************************************************/

public class Manber {
    private int n;               // length of input string
    private String text;         // input text
    private int[] index;         // offset of ith string in order
    private int[] rank;          // rank of ith string
    private int[] newrank;       // rank of ith string (temporary)
    private int offset;
   
    public Manber(String s) {
        n = s.length();
        text = s;
        index   = new int[n+1];
        rank    = new int[n+1];
        newrank = new int[n+1];

        // sentinels
        index[n] = n;
        rank[n] = -1;

        msd();
        doit();
    }

    /**
     * Returns the length of the input string.
     * @return the length of the input string
     */
    public int length() {
        return n;
    }


    /**
     * Returns the index into the original string of the <em>i</em>th smallest suffix.
     * That is, {@code text.substring(sa.index(i), n)}
     * is the <em>i</em>th smallest suffix.
     * @param i an integer between 0 and <em>n</em>-1
     * @return the index into the original string of the <em>i</em>th smallest suffix
     * @throws java.lang.IllegalArgumentException unless 0 <= <em>i</em> < <em>n</em>
     */
    public int index(int i) {
        if (i < 0 || i >= n) throw new IllegalArgumentException();
        return index[i];
    }

    /**
     * Returns the <em>i</em>th smallest suffix as a string.
     * @param i the index
     * @return the <em>i</em> smallest suffix as a string
     * @throws java.lang.IllegalArgumentException unless 0 <= <em>i</em> < <em>n</em>
     */
    public String select(int i) {
        if (i < 0 || i >= n) throw new IllegalArgumentException();
        return text.substring(index[i]);
    }

    // do one pass of msd sorting by rank at given offset
    private void doit() {
        for (offset = 1; offset < n; offset += offset) {
            int count = 0;
            for (int i = 1; i <= n; i++) {
                if (rank[index[i]] == rank[index[i-1]]) count++;
                else if (count > 0) {
                    // sort
                    int left = i-1-count;
                    int right = i-1;
                    quicksort(left, right);

                    // now fix up ranks
                    int r = rank[index[left]];
                    for (int j = left + 1; j <= right; j++) {
                        if (less(index[j-1], index[j]))  {
                            r = rank[index[left]] + j - left; 
                        }
                        newrank[index[j]] = r;
                    }

                    // copy back - note can't update rank too eagerly
                    for (int j = left + 1; j <= right; j++) {
                        rank[index[j]] = newrank[index[j]];
                    }

                    count = 0;
                }
            }
        }
    }

    // sort by leading char, assumes extended ASCII (256 values)
    private void msd() {
        final int R = 256;

        // calculate frequencies
        int[] freq = new int[R];
        for (int i = 0; i < n; i++)
            freq[text.charAt(i)]++;

        // calculate cumulative frequencies
        int[] cumm = new int[R];
        for (int i = 1; i < R; i++)
            cumm[i] = cumm[i-1] + freq[i-1];

        // compute ranks
        for (int i = 0; i < n; i++)
            rank[i] = cumm[text.charAt(i)];

        // sort by first char
        for (int i = 0; i < n; i++)
            index[cumm[text.charAt(i)]++] = i;
    }



/******************************************************************************
 *  Helper functions for comparing suffixes.
 ******************************************************************************/

  /**********************************************************************
   * Is the substring text[v..n] lexicographically less than the
   * substring text[w..n] ?
   **********************************************************************/
    private boolean less(int v, int w) {
        return rank[v + offset] < rank[w + offset];
    }


/******************************************************************************
 *  Quicksort code from Sedgewick 7.1, 7.2.
 ******************************************************************************/

    // swap pointer sort indices
    private void exch(int i, int j) {
        int swap = index[i];
        index[i] = index[j];
        index[j] = swap;
    }


    // SUGGEST REPLACING WITH 3-WAY QUICKSORT SINCE ELEMENTS ARE
    // RANKS AND THERE MAY BE DUPLICATES
    private void quicksort(int lo, int hi) { 
        if (hi <= lo) return;
        int i = partition(lo, hi);
        quicksort(lo, i-1);
        quicksort(i+1, hi);
    }

    private int partition(int lo, int hi) {
        int i = lo-1, j = hi;
        int v = index[hi];

        while (true) { 

            // find item on left to swap
            while (less(index[++i], v))
                if (i == hi) break;   // redundant

            // find item on right to swap
            while (less(v, index[--j]))
                if (j == lo) break;

            // check if pointers cross
            if (i >= j) break;

            exch(i, j);
        }

        // swap with partition element
        exch(i, hi);

        return i;
    }


    /**
     * Unit tests the {@code Manber} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String s = StdIn.readAll().replaceAll("\\s+", " ").trim();
        Manber suffix = new Manber(s);

        StdOut.println("   i  ind     select");
        StdOut.println("------------------------");

        for (int i = 0; i < s.length(); i++) {
            int index = suffix.index(i);
            String ith = "\"" + s.substring(index, Math.min(index + 50, s.length())) + "\"";
            StdOut.printf("%4d %4d  %s\n", i, index, ith);
        }
    }

}
