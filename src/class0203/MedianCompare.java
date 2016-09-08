package class0203;

import java.awt.Color;

import class0104.Adjustable2DChart;
import rlgs4.Stopwatch;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.3.19
 *      compare performence within 
 *      median 3 & 5 & normal quick sort
 *      M3 is the best
 * @author Leon
 * @date 2016-09-08 16:59:55
 */
public class MedianCompare {
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static double[] quickDoubling(int N) {
        Comparable<Object>[] src = (Comparable[]) new Double[N];
        for (int i = 0; i < N; i++) {
            src[i] = (Comparable)StdRandom.uniform();
        }
        Comparable<Object>[] srcA = src.clone();
        Comparable<Object>[] srcB = src.clone();
        
        double[] marks = new double[3];
        
        Stopwatch sw = new Stopwatch();
//        CountExactCn.sort(srcA, 0, N-1);
//        marks[0] = sw.elapsedTime();
        
        sw = new Stopwatch();
        MedianOfThree.sort(srcB, 0, N-1);
        marks[1] = sw.elapsedTime();
        
        sw = new Stopwatch();
        MedianOfFive.sort(src, 0, N-1);
        marks[2] = sw.elapsedTime();
        
        return marks;
    }
    
    public static void main(String[] args) {
        
        Adjustable2DChart a2d = new Adjustable2DChart(0.1, 0.1, 0, 0);
        
        a2d.setAxisDescDistanceChart(-.3);
        a2d.setAxisDescDistanceY(.07);
        a2d.setChartDesc("M3 VS M5");
        a2d.setAxisXDesc("problem size N");
        a2d.setAxisYDesc("running time T(N)");
        a2d.setColorForChar(Color.RED);
        
        int N = 1000000;
        // 2097152;4194305
        for (int i = N; i < 8000001; i *= 2) {
            double[] marks = quickDoubling(i);
            StdOut.println(i+" \t "+marks[0]+" \t "+marks[1]+" \t "+marks[2]);
            
            if(i<10000) {
                a2d.addChartData((double)i, marks[1]);
                a2d.addAxisDataX((double)i, i/1000+"K");
                a2d.addChartData(false, true, (double)i, marks[2]);
                
                
            } else if(i<100000000) {
                a2d.addChartData((double)i, marks[1]);
                a2d.addAxisDataX((double)i, i/10000+"W");
                a2d.addChartData(false, true, (double)i, marks[2]);
                
            }
            a2d.reDraw();
        }
    }

}
