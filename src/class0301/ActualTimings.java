package class0301;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import class0104.Adjustable2DChart;
import rlgs4.Stopwatch;

public class ActualTimings {
    
    private static File f = new File(FrequencyCounter.class.getResource("/").getPath().replace("bin", "lib")
            + "A Tale of Two Cities - Charles Dickens.txt");
    
    int gTimes;
    int pTimes;
    
    private void sequentialPlot() {
        
        OrderedSequentialSearchST<String, Integer> sst = new OrderedSequentialSearchST<String, Integer>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            String cur = null;
            while ((cur = br.readLine()) != null) {
                Matcher matcher = Pattern.compile("\\b.+?\\b").matcher(cur);
                while (matcher.find()) {
                    
                    String word = matcher.group();
                    
                    if (sst.contains(word)) {
                        sst.put(word, sst.get(word) + 1);
                        gTimes += 2;
                        pTimes++;
                    } else {
                        sst.put(word, 1);
                        gTimes++;
                        pTimes++;
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println(sst.size());
    }
    
    
    public static void main(String[] args) {
        ActualTimings at = new ActualTimings();
        DrawThread dt = new DrawThread(at);
        dt.start();
        
        at.sequentialPlot();
        
        try {
            dt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }

}

class DrawThread extends Thread {
    Adjustable2DChart a2d;
    ActualTimings at;
    int pTimes;
    
    public DrawThread(ActualTimings at) {
        this.at = at;
        
        a2d = new Adjustable2DChart(0.1, 0.1, 0, 0);
        a2d.setAxisDescDistanceChart(-.3);
        a2d.setAxisDescDistanceY(.07);
        a2d.setChartDesc("Sequential sign table");
        a2d.setAxisXDesc("G/P times N");
        a2d.setAxisYDesc("running time T(N)");
        a2d.setColorForChar(Color.RED);
        
    }
    
    @Override
    public void run() {
        
        try {
            while(at.gTimes == 0)
                Thread.sleep(10);
            
            System.out.println("begin");
            
            Stopwatch watch = new Stopwatch();
            
            while(true) {
                System.out.println("draw " + at.gTimes + " " + at.pTimes + " " + watch.elapsedTime());
                pTimes = at.pTimes;
                Thread.sleep(100);
                if(pTimes == at.pTimes)
                    break;
            }
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }

}