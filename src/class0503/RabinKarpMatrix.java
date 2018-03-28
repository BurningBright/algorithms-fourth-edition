package class0503;

import stdlib.StdOut;

/**
 * @Description 5.3.22
 *              Rabin-Karp 矩阵匹配
 *              H*V pattern in N*N matrix
 *              递归实现，寻找所有字符串匹配且左对齐的散列组
 * @author Leon
 * @date 2018-03-28 10:22:00
 */
public class RabinKarpMatrix {
    
    private int H;              // pattern length
    private int V;              // pattern length
    private long Q;             // a large prime
    private int R = 256;        // alphabet size
    private long RM;            // R^(M-1) % Q
    private long[] patHash;     // fingerprint
    
    public RabinKarpMatrix(String[] pat) {
        this.H = pat[0].length();
        this.V = pat.length;
        Q = 1000l;
        RM = 1;
        patHash = new long[V];
        for (int i = 1; i <= H - 1; i++)
            RM = (R * RM) % Q;
        // 初始化匹配散列数组
        for (int i=0; i<V; i++)
            patHash[i] = hash(pat[i], 0, H);
    }
    
    private long hash(String key, int s, int H) {
        long h = 0;
        for (int j = s; j < s + H; j++)
        h = (R * h + key.charAt(j)) % Q;
        return h;
    }
    
    public int[] search(String[] src) {
        return search(src, 0, 0, 0);
    }
    
    // int[] 0-x, 1-y
    private int[] search(String[] src, int horizonal, int vertical, int v) {
        
        if (v == V) return new int[]{horizonal, vertical};   // base case match
        if (vertical > src.length - V) return null;          // base case miss at the end
        
        String txt = src[vertical];
        int N = txt.length();
        long txtHash = hash(txt, horizonal, H);
        if (patHash[v] == txtHash) {
            // 检查下Vi行是否匹配对齐
            int[] next = search(src, horizonal, vertical+1, v+1);
            if (next == null) return null;
            if (next[0] == horizonal)       // 左对齐，返回坐标
                return new int[]{horizonal, vertical};
        }
        // 从左对齐点开始
        if (v == 0)
            for (int i = horizonal + H; i < N; i++) {
                txtHash = (txtHash + Q - RM * txt.charAt(i - H) % Q) % Q;
                txtHash = (txtHash * R + txt.charAt(i)) % Q;
                if (patHash[v] == txtHash) {
                    // 检查下Vi行是否匹配对齐
                    int hIndex = i + 1 - H;
                    int[] next = search(src, hIndex, vertical+1, v+1);
                    if (next == null) return null;
                    if (next[0] == hIndex)       // 左对齐，返回坐标
                        return new int[]{hIndex, vertical};
                }
            }
        // 重置，找下一行
        return search(src, 0, vertical+1, 0);
    }
    
    public static void main(String[] args) {
        String[] src = {
                "The Java Str", 
                "ing is not a", 
                " primitive t", 
                "ype. The sta", 
                "ndard implem", 
                "entation pro", 
                "vides the op", 
                "erations jus", 
                "t described ", 
                "to facilitat", 
                "e client pro", 
                "gramming.   "};
        
        String[] pat = {
                "d im", 
                "tion", 
                "s th"};
        
        int[] result = new RabinKarpMatrix(pat).search(src);
        StdOut.println(result[0]+" "+result[1]);
    }

}
