package class0105;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import class0104.Adjustable2DChart;
import rlgs4.Stopwatch;
/**
 * @Description 1.5.24
 *      对比 WeightedQuickUnionUF 和 WeightedQickUnionPathUF
 *      在 ErdosRenyi 模型下的性能表现
 *      貌似队列的使用影响了效率
 * @author Leon
 * @date 2016-07-19 17:13:03
 */
public class ErdosRenyiWQU_Path {

    private static List<Conn> date;
    
    private static List<Conn> datePrepare(int N) {
        date = new LinkedList<Conn>();
        WeightedQuickUnionUF wqu = new WeightedQuickUnionUF(N);
        
        while(wqu.count() != 1) {
            int p = (int) (Math.random()*N);
            int q = (int) (Math.random()*N);
            date.add(new Conn(p, q));
            wqu.union(p, q);
        }
        
        return date;
    }
    
    private static void WQU(int N) {
        WeightedQuickUnionUF wqu = new WeightedQuickUnionUF(N);
        for(Conn c: date)
            wqu.union(c.p, c.q);
    }
    
    private static void WQUP(int N) {
        WeightedQuickUnionPathQUF wqup = new WeightedQuickUnionPathQUF(N);
        for(Conn c: date)
            wqup.union(c.p, c.q);
    }
    
    public static void main(String[] args) {
        int T = 6;
        Adjustable2DChart a2d = new Adjustable2DChart(0.1, 0.1, 0, 0);
        
        a2d.setChartDesc("Erdös-Renyi model WQUP path vs nopath, nopath better");
        a2d.setAxisDescDistanceChart(-.4);
        a2d.setAxisXDesc("N");
        a2d.setAxisYDesc("T");
        a2d.setColorForChar(Color.RED);
        
        for (int i = 10000, j = 0; j < T; i *= 2, j++) {
            datePrepare(i);
            Stopwatch swatch = new Stopwatch();
            WQU(i);
            System.out.print(i+" weighted quick union:" + swatch.elapsedTime());
            a2d.addChartData(i, swatch.elapsedTime());
            a2d.reDraw();
            
            swatch = new Stopwatch();
            WQUP(i);
            System.out.println("  weighted quick union path:" + swatch.elapsedTime());
            a2d.addChartData(i, swatch.elapsedTime());
            a2d.reDraw();
        }
        
    }
    
    private static class Conn {
        int p,q;
        Conn(int p, int q) {
            this.p = p;
            this.q = q;
        }
    }
}
