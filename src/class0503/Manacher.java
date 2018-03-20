package class0503;

import java.util.Arrays;

import stdlib.StdOut;

/**
 * @Description web 5.3.15
 *          Longest palindromic substring
 * @author Leon
 * @date 2018-03-20 15:15:00
 */
public class Manacher {
    
    private String src;
    // 待分析字符数组
    private char[] S;
    // 对应下标回文半径  ps.in S
    private int[]  P;
    
    public Manacher(String src) {
        this.src = src;
        S = new char[src.length()*2 + 3];
        P = new int[S.length];
        process();
        
        // right = center + P[center]
        int center = 0, right = 0;
        // 排除最后位置
        for (int i=1; i<S.length-1; i++) {
            /*
             *  2*center -i      i相对center的镜像位置
             *  P[2*center -i]   i镜像位所拥有的回文长度 - 此项选中时说明right-i较大，则两个镜像区都被center覆盖
             *  right - i        i位所拥有的回文长度 ---- 此项选中时说明P[j]较大，则i的镜像和j的部分镜像被center覆盖
             *  right <= i       当前最右位比当前位小，则说明中心未覆盖i的回文串
             */
            P[i] = right > i? Math.min(P[2*center -i], right - i): 1;
            
            // 这里会把自身计数进P，最终结果比标准实现大1
            while(S[i + P[i]] == S[i - P[i]]) P[i]++;
            
            // 判断当前位新的的回文范围是否超过中心覆盖区
            if(i + P[i] > right) {
                right = i + P[i];
                center = i;
            }
            
        }
    }
    
    private void process() {
        /* 
         * 3个额外位置对应 0，1，src.length()*2+2
         * c语言有\0只需2个额外位置
         */
        S[0] = '^';
        for (int i=1; i<src.length()*2; i+=2) {
            S[i] = '#';
            S[i+1] = src.charAt(i/2);
        }
        S[S.length-2] = '#';
        S[S.length-1] = '$';
    }
    
    public String longestPalindromicSubstring() {
        int length = 0;
        int center = 0;
        // 找到最长长度回文的  下标&长度
        for (int i = 1; i < P.length-1; i++) {
            if (P[i] > length) {
                length = P[i];
                center = i;
            }
        }
        length--;
        /*
         * (center-1)/2 是真实中心
         * (length-1)/2 是真实半径
         */
        return src.substring((center - 1 - length) / 2, (center - 1 + length) / 2);
    }

    public String longestPalindromicSubstring(int i) {
        // 前移两位，到正文
        int length = P[i + 2] - 1;
        int center = i + 2;
        return src.substring((center - 1 - length) / 2, (center - 1 + length) / 2);
    }
    
    public static void main(String[] args) {
        String src = "12212321";
        Manacher man = new Manacher(src);
        StdOut.println(Arrays.toString(man.S));
        StdOut.println(Arrays.toString(man.P));
        StdOut.println(man.longestPalindromicSubstring());
    }

}
