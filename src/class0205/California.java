package class0205;

/******************************************************************************
 *  Compilation:  javac California.java
 *  Execution:    java California
 *  Dependencies: StdOut.java StdIn.java
 *  
 *  Sort names according to alphabet used in California runoff election.
 *
 *      R W Q O J M V A H B S G Z X N T C I E K U P D Y F L
 *
 *  Treats other characters as less than any uppercase letter
 *
 *
 *  % java California < california-gov.txt
 *  ROBERT "BUTCH" DOLE
 *  ROBERT C. MANNHEIM
 *  ROBERT C. NEWMAN II
 *  ROBERT CULLENBINE
 *  RONALD J. FRIEDMAN
 *  RONALD JASON PALMIERI
 *  RANDALL D. SPRAGUE
 *  RALPH A. HERNANDEZ
 *  RICH GOSSE
 *  RICHARD J. SIMMONS
 *  REVA RENEE RENZ
 *  WARREN FARRELL
 *  WILLIAM "BILL" S. CHAMBERS
 *  ...
 *  LAWRENCE STEVEN STRAUSS
 *  LINGEL H. WINTERS
 *  LEO GALLAGHER
 *  LEONARD PADILLA
 *
 *  Data file:  http://algs4.cs.princeton.edu/35applications/california-gov.txt
 *
 ******************************************************************************/

import java.util.Arrays;
import java.util.Comparator;

import stdlib.StdOut;

public class California {
    public static final Comparator<String> CANDIDATE_ORDER = new CandidateComparator();

    private static class CandidateComparator implements Comparator<String> {
        private static String order = "RWQOJMVAHBSGZXNTCIEKUPDYFL";
        public int compare(String a, String b) {
            int n = Math.min(a.length(), b.length());
            for (int i = 0; i < n; i++) {
                int aindex = order.indexOf(a.charAt(i));
                int bindex = order.indexOf(b.charAt(i));
                if (aindex < bindex) 
                    return -1;
                else if (aindex > bindex) 
                    return +1;
            }
            return a.length() - b.length();
        }
    }

    // test client
    public static void main(String[] args) {
//        String[] candidates = StdIn.readAll().toUpperCase().split("\\n+");
        String[] candidates = { "Malcolm ", "Joan", "Niki", "Betty", "Linda", "Whitney", "Lily", "Fred", "Gary",
                "William", "Charles", "Michael", "Karl", "Barbara", "Elizabeth", "Helen", "Katharine", "Lee", "Ann",
                "Diana", "Fiona", "Bob", "John", "Thomas", "Dean", "Paul", "Jack", "Brooke", "Judy", "Doris", "Rudy",
                "Amanda", "Shirley", "Joan", "Tracy", "Kevin", "Louis", "John", "George", "Henry", "Benjamin" };
        int n = candidates.length;
        Arrays.sort(candidates, California.CANDIDATE_ORDER);
        for (int i = 0; i < n; i++)
            StdOut.println(candidates[i]);
    }
}
