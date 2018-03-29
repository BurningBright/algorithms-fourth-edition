package class0503;

import rlgs4.SET;
import stdlib.StdOut;

/**
 * @Description 5.3.32 - 5.2.14
 *              Rabin-Karp 寻找独特子字符串?
 *              感觉并不是什么好主意
 * @author Leon
 * @date 2018-03-29 09:25:00
 */
public class UniqueSubstringsL {
    
    private static long[] hashs;
    private static long Q = 457l;
    private static int R = 256;
    
    public static void search(String txt, int M) {
        hashs = new long[txt.length() - M + 1];
        long RM = 1;
        for (int i = 1; i <= M - 1; i++)
            RM = (R * RM) % Q;
        
        hashs[0] = hash(txt, M);
        long txtHash = hashs[0];
        for (int i=M; i<txt.length(); i++) {
            txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
            txtHash = (txtHash * R + txt.charAt(i)) % Q;
            hashs[i + 1 - M] = txtHash;
        }
        
        for (long i: hashs)
            StdOut.print(i + " ");
        StdOut.println();
        
        SET<Long> set = new SET<Long>();
        for (int i=0; i<hashs.length; i++)
            if (!set.contains(hashs[i])) {
                StdOut.println(hashs[i] +"\t"+ txt.substring(i, i+M));
                set.add(hashs[i]);
            }
    }
    
    private static long hash(String key, int M) {
        long h = 0;
        for (int j = 0; j < M; j++)
        h = (R * h + key.charAt(j)) % Q;
        return h;
    }
    
    public static void main(String[] args) {
        UniqueSubstringsL.search("cgcgggcgcg", 3);
    }

}
