package class0503;

import stdlib.StdOut;

/**
 * @Description 5.3.1
 *          蛮力 一型 二型
 * @author Leon
 * @date 2018-03-22 14:15:00
 */
public class Brute {
    
    public static int search_1(String txt, String pat) {
        int N = txt.length();
        int M = pat.length();
        int space = N - M;
        for (int i=0; i<=space; i++) {
            int j;
            for (j=0; j<M; j++) 
                if (txt.charAt(i+j) != pat.charAt(j))
                    break;
            // 当匹配时，内层循环最后一次++会将索引变为长度
            if (j == M)
                return i;
        }
        return N;
    }
    
    public static int search_2(String txt, String pat) {
        int N = txt.length();
        int M = pat.length();
        int i, j;
        // 防止 i, j越界
        for (i=0, j=0; i<N && j<M; i++) {
            if (txt.charAt(i) != pat.charAt(j)) {
                // reset txt & pat index
                i = i-j;
                j = 0;
            } else 
                j++;
        }
        if (j == M)
            return i-M;
        return N;
    }
    
    public static int count(String txt, String pat) {
        int N = txt.length();
        int M = pat.length();
        int space = N - M;
        int count = 0;
        for (int i=0; i<=space; i++) {
            int j;
            for (j=0; j<M; j++) 
                if (txt.charAt(i+j) != pat.charAt(j))
                    break;
            // 当匹配时，内层循环最后一次++会将索引变为长度
            if (j == M) count++;
        }
        return count;
    }
    
    public static int[] searchAll(String txt, String pat) {
        int N = txt.length();
        int M = pat.length();
        int space = N - M;
        int[] index = new int[count(txt, pat)];
        for (int i=0, k=0; i<=space; i++) {
            int j;
            for (j=0; j<M; j++) 
                if (txt.charAt(i+j) != pat.charAt(j))
                    break;
            /*
             *  当匹配时，内层循环最后一次++会将索引变为长度
             *  i+1为当前正文长度 - M匹配长度 = 符合子串的起始索引
             */
            if (j == M) index[k++] = i+1 - M;
        }
        return index;
    }
    
    public static void main(String[] args) {
        String txt = "synchronized";
        int index = search_1(txt, "chron");
        StdOut.println(index == txt.length()? null: txt.substring(index, index+5));
        index = search_2(txt, "chron");
        StdOut.println(index == txt.length()? null: txt.substring(index, index+5));
        
        StdOut.println(count(txt, "n"));
        StdOut.println(searchAll(txt, "n")[1]);
    }

}
