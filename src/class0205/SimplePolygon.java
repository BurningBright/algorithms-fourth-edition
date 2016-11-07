package class0205;

import java.awt.Color;
import java.util.Arrays;

import class0104.Adjustable2DChart;
import rlgs4.Point2D;
import stdlib.StdOut;
import stdlib.StdRandom;
/**
 * @Description 2.5.26
 *          排序做成简单多边形
 * @author Leon
 * @date 2016-11-07 15:31:17
 */
public class SimplePolygon {

    public static void main(String[] args) {
        int N = 10;
        Point2D[] p = new Point2D[N];
        
        for (int i=0; i<N; i++) {
            p[i] = new Point2D(StdRandom.uniform(10), StdRandom.uniform(10));
        }
        StdOut.println(Arrays.toString(p));
        
        Arrays.sort(p, Point2D.Y_ORDER);
        for (int i=0, j=0; i<N-1; i++, j++) {
            if (p[i].y() != p[i+1].y()) {
                Arrays.sort(p, 0, j, Point2D.X_ORDER);
                break;
            }
        }
        StdOut.println(Arrays.toString(p));
        
        Arrays.sort(p, p[0].POLAR_ORDER);
        StdOut.println(Arrays.toString(p));
        
        
        Adjustable2DChart a2d = new Adjustable2DChart(0.03, 0.03, 0, 0);
        a2d.setAxisDescDistanceChart(-.3);
        a2d.setAxisDescDistanceY(.03);
        a2d.setAxisDescDistanceX(.03);
        a2d.setChartDesc("Simple polygon");
        a2d.setAxisXDesc("x");
        a2d.setAxisYDesc("y");
        a2d.setColorForChar(Color.RED);
        a2d.setLinked(true);
        
        for (int i=0; i<N; i++) 
            a2d.addChartData(false, false, p[i].x(), p[i].y());
        
        a2d.reDraw();
    }

}
