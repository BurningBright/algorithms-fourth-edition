package class0204;

import java.awt.Color;

import class0104.Adjustable2DChart;
import rlgs4.MaxPQ;
import rlgs4.Stopwatch;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.4.36
 *          对随机的优先队列弹压性能做测试
 *          生成平均时间分布图表
 * @author Leon
 * @date 2016-09-29 09:53:22
 */
public class PerformanceDriverI {
    private static final int ALL = 500000;
    
    public static double time(int num) {
        MaxPQ<Double> pq = new MaxPQ<Double>();
        
        /** start */
        Stopwatch sw = new Stopwatch();
        
        for(int i=0; i<ALL; i++)
            pq.insert(StdRandom.uniform());
        for(int i=0; i<num; i++)
            pq.delMax();
        for(int i=0; i<num; i++)
            pq.insert(StdRandom.uniform());
        for(int i=0; i<ALL; i++)
            pq.delMax();
        
        /** end */
        return sw.elapsedTime();
    }
    
    public static void main(String[] args) {
        int N = 7;
        int M = 3;
        int T = 100000;
        
        Adjustable2DChart a2d = new Adjustable2DChart(0.1, 0.1, 0, 0);
        
        a2d.setAxisDescDistanceChart(-.3);
        a2d.setAxisDescDistanceY(.07);
        a2d.setChartDesc("Performance Driver I");
        a2d.setAxisXDesc("driver order N");
        a2d.setAxisYDesc("running time T(N)");
        a2d.setColorForChar(Color.RED);
        a2d.setLinked(false);
        
        for(int i=0; i<N; i++) {
            double sum = .0;
            for(int j=0; j<M; j++) 
                sum += PerformanceDriverI.time(StdRandom.uniform(T*(j+1)));
            
            StdOut.printf("%.3f\n", sum);
            
            a2d.addChartData((double)i+1, (int)((sum/M)*100)/100.0);
            a2d.reDraw();
        }
        
    }

}
