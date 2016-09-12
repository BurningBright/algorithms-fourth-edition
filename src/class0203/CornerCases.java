package class0203;

import class0201.NonuniformData;
import rlgs4.QuickX;
import rlgs4.Stopwatch;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.3.30
 *      对2.1.35/36中的边际案例做性能对比
 *      研究“初始化随机”在快排处理边际案例时的影响
 *      越有序的样本被打乱所受影响越大
 * @author Leon
 * @date 2016-09-12 14:37:37
 */
public class CornerCases {
    
    private static final int N = 8000000;
    
    /** 
     * 分布的打乱会变慢
     * 其本身已具备一定有序性
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void distributeCmp() {
        Comparable<Object>[] srcA = new Comparable[N];
        for (int i = 0; i < N; i++) {
//            srcA[i] = (Comparable)StdRandom.gaussian();
//            srcA[i] = (Comparable)StdRandom.poisson(4.0);
//            srcA[i] = (Comparable)StdRandom.geometric(.2);
//            srcA[i] = (Comparable)StdRandom.uniform(1, 100);
            srcA[i] = (Comparable)i;
        }
        Comparable<Object>[] srcB = srcA.clone();
        
        Stopwatch sw;
        
        sw = new Stopwatch();
        QuickX.sort(srcA);
        StdOut.print(sw.elapsedTime() + "   ");
        
        sw = new Stopwatch();
        StdRandom.shuffle(srcB);
        QuickX.sort(srcB);
        StdOut.println(sw.elapsedTime());
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void nonuniforCmp() {
        try {
            Comparable[] srcA = new Comparable[N];
//            int[] src = NonuniformData.dataPrepareOne(N);
//            int[] src = NonuniformData.dataPrepareOneX(N);
//            int[] src = NonuniformData.dataPrepareTwo(N);
            int[] src = NonuniformData.dataPrepareThree(N);
            
            for(int i=0; i<N; i++) 
                srcA[i] = (Comparable)src[i];
            Comparable<Object>[] srcB = srcA.clone();
            
            Stopwatch sw;
            
            sw = new Stopwatch();
            QuickX.sort(srcA);
            StdOut.print(sw.elapsedTime() + "   ");
            
            sw = new Stopwatch();
            StdRandom.shuffle(srcB);
            QuickX.sort(srcB);
            StdOut.println(sw.elapsedTime());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
//        distributeCmp();
        nonuniforCmp();
    }

}
