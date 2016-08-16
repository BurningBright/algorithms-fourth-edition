package class0203;

import java.util.Arrays;

import class0201.NonuniformData;
import stdlib.StdOut;

/**
 * @Description 2.3.5
 *      对只有俩key的样本排序
 * @author Leon
 * @date 2016-08-16 15:02:59
 */
public class TwoKeyAlgorithm {
    
    public static void sort(int[] a) {
        for (int i=0,j=1; j < a.length; j++) {
            /** 
             * i来作为定位
             * j来遍历，如果是第一次则初始化i
             */
            if(less(a[j], a[j-1])) {
                if(i==0) {
                    exch(a, j, j-1);
                    i = j;
                } else {
                    exch(a, i++, j);
                }
            }
        }
    }
    
    private static boolean less(int v, int w) {
        return v-w < 0;
    }
    
    private static void exch(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
    
    public static void main(String[] args) {
        try {
            int src[] = NonuniformData.dataPrepareOneX(10);
            StdOut.println(Arrays.toString(src));
            sort(src);
            StdOut.println(Arrays.toString(src));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
