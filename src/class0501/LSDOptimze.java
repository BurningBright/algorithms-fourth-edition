package class0501;

import java.util.Arrays;

import stdlib.StdOut;

/**
 * @Description 5.1.9
 *          LSD 适配不同长度字符串
 * @author Leon
 * @date 2018-02-13 14:34:00
 */
public class LSDOptimze {

    public static void sort(String[] a) { // Sort a[]
        
        // 确定最长字符串长度
        int W = 0;
        for (int i=0; i<a.length; i++)
            if (a[i].length() > W)
                W = a[i].length();
        
        int N = a.length;
        int R = 256;
        String[] aux = new String[N];
        
        for (int d = W - 1; d >= 0; d--) {  // Sort by key-indexed counting on dth char.
            int[] count = new int[R + 2];   // Compute frequency counts.
            
            // 1位空出处理字符长度不到目标的字符串
            for (int i = 0; i < N; i++)
                if (a[i].length()-1 < d)
                    count[1]++;
                else
                    count[a[i].charAt(d) + 2]++;
            
            for (int r = 0; r < R+1; r++)     // Transform counts to indices.
                count[r + 1] += count[r];
            
            //下标0的值作为索引，所以字符集目标后移一位
            for (int i = 0; i < N; i++)     // Distribute.
                if (a[i].length()-1 < d)
                    aux[count[0]++] = a[i];
                else
                    aux[count[a[i].charAt(d)+1]++] = a[i];
            for (int i = 0; i < N; i++)     // Copy back.
                a[i] = aux[i];
        }
    }
    
    public static void main(String[] args) {
        String[] src = "she sells sea shells by the sea shore".split(" ");
        sort(src);
        StdOut.println(Arrays.deepToString(src));
    }

}
