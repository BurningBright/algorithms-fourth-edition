package class0503;

import stdlib.StdIn;
import stdlib.StdOut;

/**
 * @Description 5.3.23
 *              Rabin-Karp 探查回文
 *              字符输入的字符即是正序的低位，又是逆序的高位
 * @author Leon
 * @date 2018-03-26 10:15:00
 */
public class RabinKarpPalindrome {
    
    private int N;              // string length
    private long Q;             // a large prime
    private int R = 256;        // alphabet size
    private char[] chars;
    
    private long hashA;
    private long hashB;
    
    public RabinKarpPalindrome() {
        Q = 997;
        chars = new char[4];
        char next;
        while((next = StdIn.readChar()) != '$') {
            if (next < 32) continue;
            process(next);
        }
    }
    
    private void process(char c) {
        if (N == chars.length) resize(2*chars.length);
        chars[N++] = c;
        
        if (N == 1) {
            hashA = c % Q;
            hashB = hashA;
            StdOut.println(c);
            return;
        }
        
        long RM = 1;    // R^(N/2-1) % Q
        for (int i = 1; i <= N-1; i++)
            RM = (R * RM) % Q;
        
        // 旧信息推向高位，输入放入低位
        hashA = (hashA * R + c) % Q;
        // 输入直接放入高位
        hashB = (hashB + RM*c) % Q;
        
        StdOut.println(hashA + "  " + hashB);
        if (hashA == hashB)
            StdOut.println("*");
        
    }
    
    private void resize(int size) {
        char[] tmp = new char[size];
        for(int i=0; i<chars.length; i++)
            tmp[i] = chars[i];
        chars = tmp;
    }
    
    public static void main(String[] args) {
        new RabinKarpPalindrome();
    }

}
