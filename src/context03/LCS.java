package context03;

import stdlib.In;
import stdlib.StdOut;

/**
 * @Description context03.30
 *              两文件中的最长共同子串
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @date 2018-05-10 10:50:00
 */
public class LCS {
    
    public static String lcs(String s, String t) {
        SuffixArray suffix1 = new SuffixArray(s);
        SuffixArray suffix2 = new SuffixArray(t);

        // find longest common substring by "merging" sorted suffixes 
        String lcs = "";
        int i = 0, j = 0;
        int a = 0, b = 0;
        while (i < s.length() && j < t.length()) {
            int p = suffix1.index(i);
            int q = suffix2.index(j);
            String x = lcp(s, p, t, q);
            if (x.length() > lcs.length()) {
                lcs = x;
                a = p;
                b = q;
            }
            if (compare(s, p, t, q) < 0) i++;
            else                         j++;
        }
        StdOut.println(a + " "+ b + " " + lcs.length());
        return lcs;
    }
    
    private static String lcp(String s, int p, String t, int q) {
        int n = Math.min(s.length() - p, t.length() - q);
        for (int i = 0; i < n; i++) {
            if (s.charAt(p + i) != t.charAt(q + i))
                return s.substring(p, p + i);
        }
        return s.substring(p, p + n);
    }
    
    private static int compare(String s, int p, String t, int q) {
        int n = Math.min(s.length() - p, t.length() - q);
        for (int i = 0; i < n; i++) {
            if (s.charAt(p + i) != t.charAt(q + i))
                return s.charAt(p+i) - t.charAt(q+i);
        }
        if      (s.length() - p < t.length() - q) return -1;
        else if (s.length() - p > t.length() - q) return +1;
        else                                      return  0;
    }
    
    public static void main(String[] args) {
        In in1 = new In("resource/3.1/tale.txt");
        In in2 = new In("resource/3.5/war+peace.txt");
        String s = in1.readAll().trim().replaceAll("\\s+", " ");
        String t = in2.readAll().trim().replaceAll("\\s+", " ");
        StdOut.println("'" + lcs(s, t) + "'");
    }

}
