package class0304;

import java.awt.Color;
import java.awt.Font;

import class0104.Adjustable2DChart;
import class0301.AmortizedCostPlots;
import stdlib.StdDraw;

/**
 * @Description 3.4.40
 *      page 475
 * @author Leon
 * @date 2017-10-12 09:41:37
 */
public class PlotsLinear extends AmortizedCostPlots {


    public void plotsLinearProbingHashST() {
        
        LinearProbingHashSTPlot<String, Object> sst = 
                new LinearProbingHashSTPlot<String, Object>();
        Adjustable2DChart a2d;
        StdDraw.setFont(new Font("consolas", Font.PLAIN, 15));
        a2d = new Adjustable2DChart(1027, 512, 0.05, 0.1, 0, 0);
        a2d.setAxisDescDistanceChart(-.3);
        a2d.setAxisDescDistanceY(.05);
        a2d.setRadius(.0005);
        a2d.setChartDesc("Liear hash plot");
        a2d.setAxisXDesc("operations");
        a2d.setAxisYDesc("compares");
        a2d.setColorForChar(Color.RED);
        a2d.setColorForData(Color.GRAY);
        

        Object obj = new Object();
        
        int T = 100;
        for (int i=0; i<txt.length; i++) {
            
            int cpTime = sst.put4Cmp(txt[i], obj);
            calcAvg[i] = cpTime;
            a2d.addChartData(false, false, i, cpTime);
            
            if(i>0 && i%T == 0) {
                int sum = 0;
                for (int j=i-T; j<i; j++) {
                    sum += calcAvg[j];
                }
                a2d.addChartData(false, false, i, sum/(double)T, Color.RED);
            }
            
            if(i == txt.length - 1) {
                int sum = 0;
                for (int j=i-T; j<i; j++) {
                    sum += calcAvg[j];
                }
                a2d.addChartData(false, false, i, sum/(double)T, Color.RED);
                a2d.addAxisDataY(sum/(double)T, String.format("%.2f", sum/(double)T) + "");
            }
            
        }
        a2d.addAxisDataX((double)txt.length, txt.length+"");
        a2d.addAxisDataY((double)10, 10+"");
        a2d.reDraw();
    }
    
    public static void main(String[] args) {
        new PlotsLinear().plotsLinearProbingHashST();
    }

}
