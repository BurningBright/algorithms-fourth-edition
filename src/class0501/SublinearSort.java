package class0501;

import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 5.1.15
 *          LSD + insert sort
 *          低于线性增长级？
 * @author Leon
 * @date 2018-02-13 14:34:00
 */
public class SublinearSort {
    
    public static void sort(int[] src) {
        int N = src.length;
        int R = 65536;
        int offset = 32768;
        int[] aux = new int[N];
        int[] count = new int[R + 1];   // 2^16 + 1
        
        /***********    LSD   ************/
        for (int i = 0; i < N; i++) {
            int prefix = (src[i] >> 16) + offset;
            count[prefix + 1]++;
        }
        
        for (int r = 0; r < R; r++)
            count[r+1] += count[r];
        
        for (int i = 0; i < N; i++) {
            int prefix = (src[i] >> 16) + offset;
            aux[count[prefix]++] = src[i];
        }
        
        for (int i = 0; i < N; i++) // Copy back.
            src[i] = aux[i];
        
        /***********  Insertion  ************/
        for (int i = 1; i < N; i++)
            for (int j = i; j > 0 && less(src[j], src[j - 1]); j--) 
                exch(src, j, j - 1);
    }
    
    private static boolean less(int v, int w) {
        return v - w < 0;
    }

    private static void exch(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
    
    public static void main(String[] args) {
        // prepare
        In in = new In("resource/5.1/sublinear.txt");
        int n = in.readInt();
        int[] data = new int[n];
        for (int i=0; i<n; i++)
            data[i] = in.readInt();
        
        // sort
        sort(data);
        
        // print
        for (int i=0; i<n; i++)
            StdOut.println(data[i]);
        
        // check
        for (int i=0; i<n-1; i++)
            if(data[i]>data[i+1])
                StdOut.println(data[i]);
        
    }

}
