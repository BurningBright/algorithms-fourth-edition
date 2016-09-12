package class0203;

import java.awt.Color;

import class0104.Adjustable2DChart;
import rlgs4.QuickX;
import rlgs4.Stopwatch;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 
 * @author Leon
 * @date 2016-09-12 10:47:59
 */
public class HistogramOfRunningTimes {
    
    public static int T = 100000;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static double histogram(int N) {
        Comparable<Object>[] src = (Comparable[]) new Double[N];
        for (int i = 0; i < N; i++) {
            src[i] = (Comparable)StdRandom.uniform();
        }
        Stopwatch sw;
        double time = 0;
        for(int i=0; i<T; i++) {
            sw = new Stopwatch();
            QuickX.sort(src);
            time += sw.elapsedTime();
        }
        
//        NumberFormat objFormat = new DecimalFormat("#.##");
//        return objFormat.format(time);
        return time/T;
    }
    
    public static void main(String[] args) {
        
        Adjustable2DChart a2d = new Adjustable2DChart(0.1, 0.1, 0, 0);
        
        a2d.setAxisDescDistanceChart(-.3);
        a2d.setAxisDescDistanceY(.07);
        a2d.setChartDesc("Quick Sort Histogram");
        a2d.setAxisXDesc("problem size N");
        a2d.setAxisYDesc("running time T(N)");
        a2d.setColorForChar(Color.RED);
        a2d.setLinked(true);
        
        int[] N = {1000, 10000, 100000};
        for (int i = 0; i < N.length; i++,T/=10) {
            double mark = histogram(N[i]);
            StdOut.println(N[i]+" \t "+mark);
            
            if(N[i]<10000) {
                a2d.addChartData((double)i+1, mark);
                a2d.addAxisDataX((double)i+1, N[i]/1000+"K");
                
            } else if(i<100000000) {
                a2d.addChartData((double)i+1, mark);
                a2d.addAxisDataX((double)i+1, N[i]/10000+"W");
                
            }
            a2d.reDraw();
        }
        
    }

}
