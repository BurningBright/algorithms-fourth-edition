package context03;

import stdlib.In;
import stdlib.StdOut;

/**
 * @Description context03.32
 *              找到字符串中至少长度为k的最短回环子串
 * @author Leon
 * @date 2018-05-11 09:20:00
 */
public class CircularString {
    
    public static String find(String s, int k) {
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        
        for (int i=s.length()-1; i>=0; i--) 
            sb.append(s.charAt(i));
        s = sb.toString();
        
        SuffixArray suffix = new SuffixArray(s);
        String min = null;
        for (int i=0; i<s.length()-1; i++) {
            int p = suffix.index(i);
            int q = suffix.index(i+1);
            String x = lcp(s, p, s, q);
            if (x.length() > k && 
                    (min == null || x.length() < min.length()))
                min = x;
        }
        
        return min;
    }
    
    private static String lcp(String s, int p, String t, int q) {
        int n = Math.min(s.length() - p, t.length() - q);
        for (int i = 0; i < n; i++) {
            if (s.charAt(p + i) != t.charAt(q + i))
                return s.substring(p, p + i);
        }
        return s.substring(p, p + n);
    }
    
    public static void main(String[] args) {
        String src = "jabvp abcba eoirgodjfigjoeiorgh aca saeruhi";
        src = "Hello kitty";
        In in = new In("resource/5.5/ecoli.txt");
        src = in.readAll();
        StdOut.println(find(src, 10));
    }

}
