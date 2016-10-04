
package class0204;

import java.awt.Color;

import class0104.Adjustable2DChart;
import rlgs4.MaxPQ;
import rlgs4.Stopwatch;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.4.37
 *      对优先队列在一秒内所能做的最大弹压次数做测试
 *      生成平均次数分布图表
 * @author Leon
 * @date 2016-09-29 15:43:49
 */
public class PerformanceDriverII {
    
    private static MaxPQ<Double> pq = new MaxPQ<Double>();
    private final static int ALL = 500000;
    
    public static void init() {
        int n = ALL - pq.size();
        for(int i=0; i<n; i++)
            pq.insert(StdRandom.uniform());
    }
    
    public static void main(String[] args) {
        int N = 5;
        int M = 3;
        int T = 100000;
        
        Adjustable2DChart a2d = new Adjustable2DChart(0.1, 0.1, 0, 0);
        
        a2d.setAxisDescDistanceChart(-.3);
        a2d.setAxisDescDistanceY(.07);
        a2d.setChartDesc("Performance Driver II");
        a2d.setAxisXDesc("driver order N");
        a2d.setAxisYDesc("running times T(N)");
        a2d.setColorForChar(Color.RED);
        a2d.setLinked(false);
        
        // 次数
        for(int i=0; i<N; i++) {
            long count = 0l;
            
            // 重复
            for(int j=0; j<M; j++) {
                init();
                boolean signal = false;
                int range = StdRandom.uniform(i*T, (i+1)*T);
                
                Stopwatch sw = new Stopwatch();
                
                // 计数
                while(sw.elapsedTime() < 1.0) {
                    
                    for(int k=0; k<range && signal && sw.elapsedTime() < 1.0; k++) {
                        pq.insert(StdRandom.uniform());
                        count++;
                        if(k+1 == range)
                            signal = !signal;
                    }
                    
                    for(int k=0; k<range && !signal && sw.elapsedTime() < 1.0; k++) {
                        pq.delMax();
                        count++;
                        if(k+1 == range)
                            signal = !signal;
                    }
                    
                }
                
            }
            count = (int)(count/M);
            a2d.addChartData(true, false, i+1, count);
            a2d.addAxisDataY((double)count, count/10000+"W");
            a2d.reDraw();
            StdOut.println(i+" "+count);
            count = 0;
        }
        
    }

}
