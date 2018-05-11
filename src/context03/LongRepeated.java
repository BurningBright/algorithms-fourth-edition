package context03;

import java.util.HashMap;
import java.util.Map.Entry;

import stdlib.StdOut;

/**
 * @Description context03.34
 *              找到字符串中至少l长的所有子串
 *              Long l repeated substring
 * @author Leon
 * @date 2018-05-11 13:00:00
 */
public class LongRepeated {
    
    public static void find(String s, int l) {
        SuffixArray suffix = new SuffixArray(s);
        int n = s.length();
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int i=0; i<n-1; i++) {
            if (i%1000 == 0)
                StdOut.println(i);
            int p = suffix.index(i);
            int q = suffix.index(i+1);
            String x = lcp(s, p, s, q);
            // filter
            if (x.length() == 0) continue;
            
            for (int t=1; t<=x.length(); t++) {
                String sub = x.substring(0, t);
                if (map.containsKey(sub))
                    map.put(sub, map.get(sub)+1);
                else
                    map.put(sub, 2);
            }
        }
        
        for (Entry<String, Integer> entry : map.entrySet()) 
            if (entry.getKey().length() >= l) 
                StdOut.println(entry.getKey() + "\t\t" +entry.getValue());
        
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
        String src = "aacaagtttacaagc";
        find(src, 3);
    }

}
