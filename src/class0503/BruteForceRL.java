package class0503;

import stdlib.StdOut;

/**
 * @Description 5.3.5
 *          从右向左的蛮力解法
 * @author Leon
 * @date 2018-03-23 15:50:00
 */
public class BruteForceRL {
    
    public static int search(String txt, String pat) {
        int N = txt.length();
        int M = pat.length();
        int space = N - M;
        for (int i=N-1; i>=space; i--) {
            int j;
            for (j=M-1; j>=0; j--)
                if (txt.charAt(i+j-(M-1)) != pat.charAt(j))
                    break;
            // 定位左边位置
            if (j < 0) return i-(M-1);
        }
        return N;
    }
    
    public static void main(String[] args) {
        String txt = "synchronized";
        int index = search(txt, "chron");
        StdOut.println(index);
    }

}
