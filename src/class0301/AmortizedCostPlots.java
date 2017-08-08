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
import stdlib.StdRandom;

/**
 * @Description 3.1.38/39
 *      SequentialSearchST and BinarySearchST plot
 * @author Leon
 * @date 2017-08-08 16:42:37
 */
public class AmortizedCostPlots {
    
    int N = 1;
    
    String[] txt;
    
    int[] sumCp;
    
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
        sumCp = new int[txt.length];
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
        a2d.setRadius(.0003);
        a2d.setChartDesc("sequential plot");
        a2d.setAxisXDesc("operations");
        a2d.setAxisYDesc("compares");
        a2d.setColorForChar(Color.RED);
        a2d.setColorForData(Color.GRAY);
        a2d.reDraw();
        
        StdRandom.shuffle(txt);
        for (int j=0; j<txt.length; j++) {
            int cpTime = sst.put(txt[j]);
            sumCp[j] += cpTime;
            a2d.addChartData(false, false, j, cpTime);
//                if(j%100 == 0)
//                    a2d.reDraw();
        }
        a2d.reDraw();
        
        
        System.out.println("");
    }
    
    public static void main(String[] args) {
        new AmortizedCostPlots().sequentialPlot();
    }

}
