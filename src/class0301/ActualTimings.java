package class0301;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import class0104.Adjustable2DChart;
import rlgs4.ST;
import rlgs4.Stopwatch;
import stdlib.StdDraw;

/**
 * @Description 3.1.38/39
 *      SequentialSearchST and BinarySearchST plot
 * @author Leon
 * @date 2017-03-08 15:36:37
 */
public class ActualTimings {
    
    private static File f = new File(FrequencyCounter.class.getResource("/").getPath().replace("bin", "lib")
            + "A Tale of Two Cities - Charles Dickens.txt");
    
    int gTimes;
    int pTimes;
    
    @SuppressWarnings("unused")
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
    
    private void binaryPlot() {
        
        ST<String, Integer> sst = new ST<String, Integer>();
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
        
//        at.sequentialPlot();
        at.binaryPlot();
        
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
        
        StdDraw.setFont(new Font("consolas", Font.PLAIN, 15));
        a2d = new Adjustable2DChart(0.1, 0.1, 0, 0);
        a2d.setAxisDescDistanceChart(-.3);
        a2d.setAxisDescDistanceY(.12);
        //a2d.setRadius(.003);
        a2d.setChartDesc("Binary sign table");
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
                
                a2d.addChartData(true, false, watch.elapsedTime(), at.gTimes);
                a2d.addAxisDataX(watch.elapsedTime(), String .format("%.1f", watch.elapsedTime()));
                a2d.addAxisDataY(at.gTimes/1.0, (at.gTimes/10000)+"W");
                
                a2d.addChartData(false, false, watch.elapsedTime(), at.pTimes);
                a2d.addAxisDataY(at.pTimes/1.0, (at.pTimes/10000)+"W");
                
                a2d.reDraw();
                
                pTimes = at.pTimes;
                Thread.sleep(10);
                if(pTimes == at.pTimes)
                    break;
            }
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }

}