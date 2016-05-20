package class0101;

import java.util.Arrays;
/**
 * @Description 1.1.14 lg()
 * @author Leon
 * @date 2016-05-20 18:03:57
 */
public class HandMath {
    
    public static double sqrt(double src, int bit) {
        int c_int = 0;
        // 计数整数数目
        for(int i = (int)src; i > 0; i = i/10) { c_int++; }
        // 组数
        c_int = c_int > 1? (c_int/2.0 > c_int/2? c_int/2 + 1: c_int/2): 1;
        int[] s_int = new int[c_int];   // 源数组
        
        // 两两分组
        for(int i = c_int, j = (int)src; i > 0; i--) {
            s_int[i-1] = j%100;
            j /= 100;
        }
        
        System.out.println(Arrays.toString(s_int));
        
        double acc = .0;
        for(int i = 0; i<c_int; i++) {
            // 两组单元混合不超999，试商至32足够
            for(int j = 1; j < 33; j++) {
                // 真实命中
                double real = (acc * 20 + j -1) * (j - 1);
                if((acc * 20 + j) * j > s_int[i]) {
                    // 试商命中累加商
                    System.out.print(acc +"  "+j+"  "+(10*acc + (j-1)) +"  ");
                    acc = 10*acc + (j-1);
                    
                    // 不到大循环最后就把下次数据源加上当前余的乘阶 并且 商非零
                    if(i != c_int-1 && j != 1) {
                        s_int[i+1] += 100*(s_int[i] - real); 
                        System.out.print(s_int[i] +"  "+real +"  ");
                    }
                    
                    System.out.println(Arrays.toString(s_int));
                    
                    break;
                }
            }
        }
        
        System.out.println(acc);
        
        return acc;
        
        /*
        int[] a_src = new int[c_int + 1 + bit];
        for(int i = 0; i < a_src.length; i++) {
            a_src[i] = i == c_int? -1: 0;
        }
        System.out.println(Arrays.toString(a_src));
        */
        
    }
    
    public static void main(String[] args) {
        sqrt(123456789.9, 3);
    }
    
}
