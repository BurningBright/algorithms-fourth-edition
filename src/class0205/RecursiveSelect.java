package class0205;

import java.util.Arrays;

import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.5.6
 *      递归选排
 * @author Leon
 * @date 2016-10-25 17:05:11
 */
public class RecursiveSelect {
    
    public static void sort(int[] a, int i) {
        if(i == a.length - 1)
            return;
        
        int min = i;
        for (int j=i; j<a.length; j++) {
            if (a[min] > a[j])
                min = j;
        }
        int tmp = a[min];
        a[min] = a[i];
        a[i] = tmp;
        sort(a, i+1);
    }
    
    public static void main(String[] args) {
        int N = 30;
        int[] a = new int[N];
        for (int i=0; i<N; i++) {
            a[i] = StdRandom.uniform(10);
        }
        sort(a, 0);
        StdOut.println(Arrays.toString(a));
    }

}
