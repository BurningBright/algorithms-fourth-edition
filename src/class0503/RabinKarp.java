package class0503;

import stdlib.StdOut;

/**
 * @Description 5.3.10
 *              Rabin-Karp
 * @author Leon
 * @date 2018-03-23 17:15:00
 */
public class RabinKarp {
    @SuppressWarnings("unused")
    private String pat;         // pattern (only needed for Las Vegas)
    private long patHash;       // pattern hash value
    private int M;              // pattern length
    private long Q;             // a large prime
    private int R = 256;        // alphabet size
    private long RM;            // R^(M-1) % Q

    public RabinKarp(String pat) {
        this.pat = pat; // save pattern (only needed for Las Vegas)
        this.M = pat.length();
//        Q = longRandomPrime(); // See Exercise 5.3.33.
        Q = 453l;
        RM = 1;
        for (int i = 1; i <= M - 1; i++) // Compute R^(M-1) % Q for use
            RM = (R * RM) % Q;          // in removing leading digit.
        patHash = hash(pat, M);
    }

    public boolean check(int i) // Monte Carlo (See text.)
    {
        return true;
    } // For Las Vegas, check pat vs txt(i..i-M+1).

    private long hash(String key, int M) {
        long h = 0;
        for (int j = 0; j < M; j++)
        h = (R * h + key.charAt(j)) % Q;
        return h;
    }
    
    @SuppressWarnings("unused")
    private int search(String txt) { // Search for hash match in text.
        int N = txt.length();
        long txtHash = hash(txt, M); // init hash value
        if (patHash == txtHash)
            return 0; // Match at beginning.
        for (int i = M; i < N; i++) { // Remove leading digit, add trailing
                                      // digit, check for match.
            txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
            txtHash = (txtHash * R + txt.charAt(i)) % Q;
            if (patHash == txtHash)
                if (check(i - M + 1))
                    return i - M + 1; // match
        }
        return N; // no match found
    }

    public int count(String txt) {
        int N = txt.length();
        int count = 0;
        // init first hash value
        long txtHash = hash(txt, M);
        if (patHash == txtHash) count = 1;
        // step forward
        for (int i = M; i < N; i++) {
            txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
            txtHash = (txtHash * R + txt.charAt(i)) % Q;
            if (patHash == txtHash)
                if (check(i - M + 1))
                    count++;
        }
        return count;
    }
    
    public int[] searchAll(String txt) {
        int N = txt.length();
        long txtHash = hash(txt, M);
        int k = 0;
        int[] index = new int[count(txt)];
        if (patHash == txtHash) index[k++] = 0;
        for (int i = M; i < N; i++) {
            txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
            txtHash = (txtHash * R + txt.charAt(i)) % Q;
            if (patHash == txtHash)
                if (check(i - M + 1))
                    index[k++] = i - (M - 1);
        }
        return index;
    }
    
    public static void main(String[] args) {
        String src = "she sells sea shells by the sea shore";
        StdOut.println(new RabinKarp("sh").count(src));
        int[] result = new KMP("sh").searchAll(src);
        for (int i=0; i<result.length; i++)
            StdOut.print(result[i] + " ");
    }

}
