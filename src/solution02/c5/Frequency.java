package solution02.c5;

/******************************************************************************
 *  Compilation:  javac Frequency.java
 *  Execution:    java Frequency < strings.txt
 *  Dependencies: StdOut.java StdIn.java Record.java
 *  
 *  Read strings from standard input and print the number of times
 *  each string occurs, in descending order.
 *
 *  % java Frequency < tale.txt
 *    7515 the
 *    4751 and
 *    4071 of
 *    3458 to
 *    2830 a
 *  ...
 *
 ******************************************************************************/

import java.util.Arrays;

import stdlib.StdIn;
import stdlib.StdOut;

public class Frequency {

    public static void main(String[] args) {
        /*
        // read in and sort the input strings
        String[] a = StdIn.readAllStrings();
        int n = a.length;
        Arrays.sort(a);

        // create an array of distinct strings and their frequencies
        Record[] records = new Record[n];
        String word = a[0];
        int freq = 1;
        int m = 0;
        for (int i = 1; i < n; i++) {
            if (!a[i].equals(word)) {
                records[m++] = new Record(word, freq);
                word = a[i];
                freq = 0;
            }
            freq++;
        }
        records[m++] = new Record(word, freq);

        // sort by frequency and print results
        Arrays.sort(records, 0, m);
        for (int i = m-1; i >= 0; i--) 
            StdOut.println(records[i]);
        */
    }
}
