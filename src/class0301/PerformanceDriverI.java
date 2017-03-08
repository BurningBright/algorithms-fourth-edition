package class0301;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import class0104.Adjustable2DChart;
import rlgs4.Stopwatch;
import stdlib.StdOut;

/**
 * @Description 3.1.35
 * @author Leon
 * @date 2016-09-29 09:53:22
 */
public class PerformanceDriverI {
    private static int ALL;
    private static String[] Data;
    private static File f = new File(FrequencyCounter.class.getResource("/").getPath().replace("bin", "lib")
            + "A Tale of Two Cities - Charles Dickens.txt");
    
    static {
        try {
            List<String> src = new ArrayList<String>(); 
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            String cur = null;
            while ((cur = br.readLine()) != null) {
                Matcher matcher = Pattern.compile("\\b.+?\\b").matcher(cur);
                while (matcher.find()) {
                    String word = matcher.group();
                    src.add(word);
                }
            }
            br.close();
            Data = src.toArray(new String[0]);
            ALL = src.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public static double time(int num) {
        OrderedSequentialSearchST<String, Integer> sst = new OrderedSequentialSearchST<String, Integer>();
        
        /** start */
        Stopwatch sw = new Stopwatch();
        
        for(int i=0; i<num; i++) {
            if (sst.contains(Data[i])) {
                sst.put(Data[i], sst.get(Data[i]) + 1);
            } else {
                sst.put(Data[i], 1);
            }
        }
        
        /** end */
        return sw.elapsedTime();
    }
    
    public static void main(String[] args) {
        int N = 7;
        int M = 3;
        int T = 1000;
        
        Adjustable2DChart a2d = new Adjustable2DChart(0.1, 0.1, 0, 0);
        
        a2d.setAxisDescDistanceChart(-.3);
        a2d.setAxisDescDistanceY(.07);
        a2d.setChartDesc("Performance Driver I");
        a2d.setAxisXDesc("driver order N");
        a2d.setAxisYDesc("running time T(N)");
        a2d.setColorForChar(Color.RED);
        a2d.setLinked(false);
        
        for(int i=0; i<N && N<ALL; i++) {
            double sum = .0;
            T *= 2;
            for(int j=0; j<M; j++) 
                sum += PerformanceDriverI.time(T);
            
            StdOut.printf("%.3f\n", sum);
            
            a2d.addChartData(false, true, T/1.0, sum/M);
            a2d.addAxisDataX(T/1.0, (T/1000)+"K");
            a2d.reDraw();
        }
        
    }

}
