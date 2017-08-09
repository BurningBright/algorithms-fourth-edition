package class0301;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import class0104.Adjustable2DChart;
import stdlib.StdDraw;

/**
 * @Description 3.1.38/39
 *      SequentialSearchST and BinarySearchST plot
 * @author Leon
 * @date 2017-08-08 16:42:37
 */
public class AmortizedCostPlots {
    
    int N = 1;
    
    String[] txt;
    int[] calcAvg; 
    
    private static File f = new File(FrequencyCounter.class.getResource("/").getPath().replace("bin", "lib")
            + "A Tale of Two Cities - Charles Dickens.txt");
    
    AmortizedCostPlots() {
        
        LinkedList<String> tmp = new LinkedList<String>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            String cur = null;
            while ((cur = br.readLine()) != null) {
                Matcher matcher = Pattern.compile("\\b.+?\\b").matcher(cur);
                while (matcher.find()) {
                    
                    String word = matcher.group();
                    if(word.length() >= 8) 
                        tmp.add(word);
                    
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        txt = tmp.toArray(new String[0]);
        calcAvg = new int[txt.length];
//        StdRandom.shuffle(txt);
    }
    
    private void sequentialPlot() {
        
        OrderedSequentialSearchST<String, Integer> sst = 
                new OrderedSequentialSearchST<String, Integer>();
        Adjustable2DChart a2d;
        StdDraw.setFont(new Font("consolas", Font.PLAIN, 15));
        a2d = new Adjustable2DChart(0.1, 0.1, 0, 0);
        a2d.setAxisDescDistanceChart(-.3);
        a2d.setAxisDescDistanceY(.12);
        a2d.setRadius(.0005);
        a2d.setChartDesc("sequential plot avg-3014");
        a2d.setAxisXDesc("operations");
        a2d.setAxisYDesc("compares");
        a2d.setColorForChar(Color.RED);
        a2d.setColorForData(Color.GRAY);
//        a2d.reDraw();
        
//        StdRandom.shuffle(txt);
        for (int i=0; i<txt.length; i++) {
            
            int cpTime = sst.put(txt[i]);
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
            }
            
            /*
            if(!sst.contains(txt[j])) {
                int cpTime = sst.put(txt[j]);
                a2d.addChartData(false, false, j, cpTime);
            }
            */
        }
        a2d.addAxisDataX((double)txt.length, txt.length+"");
        a2d.addAxisDataY((double)sst.size(), sst.size()+"");
        
        a2d.reDraw();
        
        
    }
    
    public static void main(String[] args) {
        new AmortizedCostPlots().sequentialPlot();
    }

}
