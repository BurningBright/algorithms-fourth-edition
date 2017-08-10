package class0303;

import java.awt.Color;
import java.awt.Font;

import class0104.Adjustable2DChart;
import class0301.AmortizedCostPlots;
import stdlib.StdDraw;

/**
 * @Description 3.3.43
 *      Instrument RedBlackBST 
 * @author Leon
 * @date 2017-08-10 13:06:13
 */
public class CostPlots extends AmortizedCostPlots{

    CostPlots() {
        super();
    }
    
    private void redBlackPlot() {
        
        BSTRedBlackPlot<String, Object> sst = new BSTRedBlackPlot<String, Object>();
        Adjustable2DChart a2d;
        StdDraw.setFont(new Font("consolas", Font.PLAIN, 15));
        a2d = new Adjustable2DChart(0.1, 0.1, 0, 0);
        a2d.setAxisDescDistanceChart(-.3);
        a2d.setAxisDescDistanceY(.12);
        a2d.setRadius(.0005);
        a2d.setChartDesc("sequential plot avg-12");
        a2d.setAxisXDesc("operations");
        a2d.setAxisYDesc("compares");
        a2d.setColorForChar(Color.RED);
        a2d.setColorForData(Color.GRAY);
        
        Object obj = new Object();
        
        for (int i=0; i<txt.length; i++) {
            
            int cpTime = sst.put4Cmp(txt[i], obj);
            calcAvg[i] = cpTime;
            a2d.addChartData(false, false, i, cpTime);
            
            if(i>0 && i%100 == 0) {
                int sum = 0;
                for (int j=i-100; j<i; j++) {
                    sum += calcAvg[j];
                }
                a2d.addChartData(false, false, i, sum/100, Color.RED);
            }
            
            if(i == txt.length - 1) {
                int sum = 0;
                for (int j=i-100; j<i; j++) {
                    sum += calcAvg[j];
                }
                a2d.addChartData(false, false, i, sum/100, Color.RED);
                a2d.addAxisDataY(sum/100.0, sum/100+"");
            }
            
            /*
            if(!sst.contains(txt[j])) {
                int cpTime = sst.put(txt[j]);
                a2d.addChartData(false, false, j, cpTime);
            }
            */
        }
        a2d.addAxisDataX((double)txt.length, txt.length+"");
        a2d.addAxisDataY((double)calcAvg[txt.length-1], calcAvg[txt.length-1]+"");
        
        a2d.reDraw();
        
    }
    
    public static void main(String[] args) {
        new CostPlots().redBlackPlot();
    }

}
