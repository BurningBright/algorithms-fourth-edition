package class0501;

import java.awt.Color;

import class0104.Adjustable2DChart;
import rlgs4.Stopwatch;
import solution05.c01.MSD;
import stdlib.StdOut;

/**
 * @Description 5.1.22
 *          性能对比
 * @author Leon
 * @date 2018-02-28 10:50:00
 */
public class Timings {
    
    private static int T = 3;
    
    @SuppressWarnings("unused")
    private static double[] MSDVsQuick3(int n) {
        double[] marks = new double[2];
        Stopwatch sw = null;
        String[] src = RandomCase.items(n);
        
        for (int i=0; i<T; i++) {
            sw = new Stopwatch();
            MSD.sort(src);
            marks[0] += sw.elapsedTime();
        }
        marks[0] /= T;
        
        for (int i=0; i<T; i++) {
            sw = new Stopwatch();
            Quick3String.sort(src);
            marks[1] += sw.elapsedTime();
        }
        marks[1] /= T;
        
        return marks;
    }
    
    private static double[] MSDVsLSD(int n) {
        double[] marks = new double[2];
        Stopwatch sw = null;
        String[] src = RandomCase.world(n, 5);
        
        for (int i=0; i<T; i++) {
            sw = new Stopwatch();
            MSD.sort(src);
            marks[0] += sw.elapsedTime();
        }
        marks[0] /= T;
        
        for (int i=0; i<T; i++) {
            sw = new Stopwatch();
            Quick3String.sort(src);
            marks[1] += sw.elapsedTime();
        }
        marks[1] /= T;
        
        return marks;
    }
    
    
    
    public static void main(String[] args) {
        Adjustable2DChart a2d = new Adjustable2DChart(0.1, 0.1, 0, 0);
        a2d.setAxisDescDistanceChart(-.3);
        a2d.setAxisDescDistanceY(.07);
        a2d.setChartDesc("MSD VS LSD");
        a2d.setAxisXDesc("string N");
        a2d.setAxisYDesc("running time T(N)");
        a2d.setColorForChar(Color.RED);
        
        for (int i=100000; i< 800001; i*=2) {
//            double[] marks = MSDVsQuick3(i);
            double[] marks = MSDVsLSD(i);
            StdOut.println(i+" \t "+marks[0]+" \t "+marks[1]);
            
            a2d.addChartData((double)i, marks[0]);
            a2d.addAxisDataX((double)i, i/10000+"W");
            a2d.addChartData(false, true, (double)i, marks[1]);
            
            a2d.reDraw();
        }
        
    }

}
