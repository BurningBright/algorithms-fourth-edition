package class0503;

import java.util.HashMap;

import stdlib.StdOut;

/**
 * @Description 5.3.21
 *              Rabin-Karp 指纹通配
 *              重点在于记录通配位的索引及对应RM，在滑动时去除对应位信息
 * @author Leon
 * @date 2018-03-27 13:22:00
 */
public class RabinKarpWildcard {
    
    private long patHash;       // pattern hash value
    private int M;              // pattern length
    private long Q;             // a large prime
    private int R = 256;        // alphabet size
    private long RM;            // R^(M-1) % Q
    
    private HashMap<Integer, Long> wildMap;

    public RabinKarpWildcard(String pat) {
        this.M = pat.length();
//        Q = longRandomPrime(); // See Exercise 5.3.33.
        wildMap = new HashMap<Integer, Long>();
        Q = 1000l;
        RM = 1;
        for (int i = 1; i <= M - 1; i++) // Compute R^(M-1) % Q for use
            RM = (R * RM) % Q;           // in removing leading digit.
        patHash = hashInPattern(pat, M);
    }

    public boolean check(int i) {
        return true;
    }

    private long hashInPattern(String pat, int M) {
        long h = 0;
        for (int i = 0; i < M; i++)
            // 初始化通配符索引集合-权位、过滤通配位字符信息
            if (pat.charAt(i) == '_') {
                long tmp = 1l;
                for (int j = 0; j < M-i-1; j++)
                    tmp = (R * tmp) % Q;
                wildMap.put(i, tmp);
                h = R * h % Q;
            } else 
                h = (R * h + pat.charAt(i)) % Q;
        return h;
    }
    
    private long hash(String txt, int M) {
        long h = 0;
        for (int i = 0; i < M; i++)
            h = (R * h + txt.charAt(i)) % Q;
        return h;
    }

    private int search(String txt) {        // Search for hash match in text.
        int N = txt.length();
        long txtHash = hash(txt, M);        // init hash value
        if (patHash == txtHash) return 0;   // Match at beginning.
        for (int i = M; i < N; i++) {
                                      // Remove leading digit, add trailing
                                      // digit, check for match.
            // 通配位字符信息忽略
            txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
            txtHash = (txtHash * R + txt.charAt(i)) % Q;
            
            // 重算子字符串去除通配信息后的散列码
            long tmp = txtHash;
            for(Integer j: wildMap.keySet())
                tmp = (tmp + Q - wildMap.get(j) * txt.charAt(i - M + 1 + j) % Q) %Q;
                
            if (patHash == tmp)
                if (check(i - M + 1))
                    return i - M + 1; // match
        }
        return N; // no match found
    }
    
    public static void main(String[] args) {
        RabinKarpWildcard rkw = new RabinKarpWildcard("_a_");
        StdOut.println(rkw.search("what a fuck"));
    }

}
