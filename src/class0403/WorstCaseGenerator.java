package class0403;

import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 4.3.25
 *      生成最糟案例，但感觉还是没到非线性
 *      lazy    ElogE   全联通
 *      eager   ElogV   扇形MST
 * @author Leon
 * @date 2017-12-27 10:00:00
 */
public class WorstCaseGenerator {
    
    public static void lazy(int v) {
        for (int i=0; i<v; i++) {
            for (int j=i+1; j<v; j++) 
                StdOut.printf("%d - %d %.5f \r\n", i, j, StdRandom.uniform());
        }
    }
    
    public static void eager(int v) {
        for (int i=1; i<v; i++) 
            StdOut.printf("%d - %d %.5f \r\n", 0, i, StdRandom.uniform());
    }
    
    public static void main(String[] args) {
//        lazy(5);
        eager(5);
    }

}
