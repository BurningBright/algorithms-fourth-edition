package class0205;

import java.util.Arrays;

import stdlib.StdOut;

/**
 * @Description 2.5.14
 *      逆域名排序，在网络日志分析中有用
 * @author Leon
 * @date 2016-10-26 14:15:36
 */
public class ReverseDomain {
    
    public static void sort(String[] a) {
        String[][] src = new String[a.length][];
        for (int i=0; i<a.length; i++) {
            String[] tmpArray = a[i].split("\\.");
            src[i] = new String[tmpArray.length];
            for (int j = tmpArray.length - 1, k=0; j >= 0; j--, k++) {
                src[i][k] = tmpArray[j];
            }
        }
        
        for (int i=1; i<a.length; i++) {
            for (int j=i; j>0; j--) {
                if (compareTo(src[j], src[j-1]) < 0) {
                    exch(src, j, j-1);
                }
            }
        }
        
        StdOut.println(Arrays.deepToString(src));
        for(String[] s: src) 
            StdOut.println(String.join(".", s));
    }
    
    private static int compareTo(String[] a, String[] b) {
        int n = Math.min(a.length, b.length);
        // 逐位对比
        for (int i=0; i<n; i++) {
            if (a[i].compareTo(b[i]) < 0)
                return -1;
            if (a[i].compareTo(b[i]) > 0)
                return 1;
        }
        // 前n位相等， 对比位数
        return a.length - b.length;
    }
    
    private static void exch(String[][] a, int i, int j) {
        String[] tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
    
    public static void main(String[] args) {
        String[] a = { "www.linchenguang.cn", "blog.linchenguang.cn", "act.game.linchenguang.cn",
                "strategy.game.linchenguang.cn", "canvas.linchenguang.cn" };
        sort(a);
    }

}
