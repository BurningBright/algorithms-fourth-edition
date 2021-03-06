package class0105;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import class0104.Adjustable2DChart;
import rlgs4.Stopwatch;

/**
 * @Description 1.5.23
 *      Compare quick-find with 
 *      quick-union for Erdös-Renyi model.
 *      对比快联、快找性能
 * @author Leon
 * @date 2016-07-18 17:21:28
 */
public class ErdosRenyiQF_QU {
    
    private static List<Conn> date;
    
    private static List<Conn> datePrepare(int N) {
        date = new LinkedList<Conn>();
        QuickFindUF qf = new QuickFindUF(N);
        
        while(qf.count() != 1) {
            int p = (int) (Math.random()*N);
            int q = (int) (Math.random()*N);
            date.add(new Conn(p, q));
            qf.union(p, q);
        }
        
        return date;
    }
    
    private static void QU(int N) {
        QuickUnionUF qu = new QuickUnionUF(N);
        for(Conn c: date)
            qu.union(c.p, c.q);
    }
    
    private static void QF(int N) {
        QuickFindUF qf = new QuickFindUF(N);
        for(Conn c: date)
        	qf.union(c.p, c.q);
    }
    
    public static void main(String[] args) {
        int T = 5;
        Adjustable2DChart a2d = new Adjustable2DChart(0.1, 0.1, 0, 0);
        
        a2d.setChartDesc("Erdös-Renyi model");
        a2d.setAxisXDesc("N");
        a2d.setAxisYDesc("T");
        a2d.setColorForChar(Color.RED);
        
        for (int i = 10000, j = 0; j < T; i *= 2, j++) {
            datePrepare(i);
            Stopwatch swatch = new Stopwatch();
            QF(i);
            System.out.print(i+" quick find:" + swatch.elapsedTime());
            a2d.addChartData(i, swatch.elapsedTime());
            a2d.reDraw();
            
            swatch = new Stopwatch();
            QU(i);
            System.out.println("  quick union:" + swatch.elapsedTime());
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
