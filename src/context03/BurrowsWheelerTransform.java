package context03;

import java.util.Arrays;

import stdlib.StdOut;

/**
 * @Description context03.31
 *              字符矩阵转换、还原
 *              转换时的循环造成了首尾列的对应关系，还原时寻找对应的首列即可
 * @author Leon
 * @date 2018-05-10 15:50:00
 */
public class BurrowsWheelerTransform {
    
    // BWT
    public static String transform(String s) {
        String ss = s + s;
        int N = s.length();
        String[] result = new String[N];
        for (int i=0; i<N; i++)
            result[i] = ss.substring(i, i+N);
        Arrays.sort(result);
        String ret = "";
        for (int i=0; i<N; i++)
            ret += result[i].substring(N-1, N);
        return ret;
    }
    
    // BWI
    public static String invert(String s) {
        char[] last = s.toCharArray();
        char[] first = s.toCharArray();
        // 首列字符排序
        Arrays.sort(first);
        
        // 初始定位
        int init = charAt(last, '$');
        if (init <= 0)
            throw new IllegalArgumentException();
        
        // 寻找合理位置
        String ret = "";
        for (int i=init; i>0; ) {
            last[i] = 0;
            // 定位第一列字符
            char next = first[i];
            i = charAt(last, next);
            ret += next;
        }
        
        return ret;
    }
    
    private static int charAt(char[] src, char s) {
        // 转换时从头至尾，恢复从尾至头
        for (int i=src.length-1; i>0; i--)
            if (src[i] == s)
                return i;
        return -1;
    }
    
    public static void main(String[] args) {
        String s = "mississippi$";
        StdOut.println(transform(s));
        StdOut.println(invert(transform(s)));
    }

}
