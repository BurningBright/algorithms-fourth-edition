package class0503;

import stdlib.StdOut;

/**
 * @Description 5.3.4
 *          探索连续空格符
 * @author Leon
 * @date 2018-03-23 13:15:00
 */
public class BlanksCheck {
    
    public static int search(String txt, int M) {
        int i, j;
        for (i=0,j=0; i<txt.length(); i++) {
            j = txt.charAt(i) == ' '? j+1: 0;
            // i+1为当前正文长度 - M匹配长度 = 符合子串的起始索引
            if (j == M) return i+1 - M;
        }
        return txt.length();
    }
    
    public static void main(String[] args) {
        String src = "jifg3n 2234 245 43   25  234    235  jdf2w32df9  ";
        int index = search(src,4);
        StdOut.println(src.substring(index, index + 4));
    }

}
