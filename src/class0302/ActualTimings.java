package class0302;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import class0104.Adjustable2DChart;
import class0301.FrequencyCounter;
import rlgs4.BST;
import rlgs4.BinarySearchST;
import rlgs4.Stopwatch;
import stdlib.StdDraw;

/**
 * @Description 3.2.45
 * @author Leon
 * @date 2017-04-07 20:19:14
 */
public class ActualTimings {
    
    Adjustable2DChart a2d = new Adjustable2DChart(0.1, 0.1, 0, 0);
    private List<String> src = new ArrayList<String>();
    private int RT = 3;
    
    {
        try {
            File f = new File(FrequencyCounter.class
                    .getResource("/").getPath().replace("bin", "lib")
                    + "A Tale of Two Cities - Charles Dickens.txt");
            @SuppressWarnings("resource")
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(f), "UTF-8"));
            String cur = null;
            while ((cur = br.readLine()) != null) {
                Matcher matcher = Pattern.compile("\\b.+?\\b").matcher(cur);
                while (matcher.find()) {
                    String word = matcher.group();
                    src.add(word);
                }
            }
            System.out.println(src.size());
            
            
            StdDraw.setFont(new Font("consolas", Font.PLAIN, 15));
            a2d.setAxisDescDistanceChart(-.3);
            a2d.setAxisDescDistanceY(.12);
            a2d.setChartDesc("BinaryST vs BST");
            a2d.setAxisXDesc("G/P times N");
            a2d.setAxisYDesc("running time T(N)");
            a2d.setColorForChar(Color.RED);
            a2d.setRadius(.003);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    public void process() {
        
        ST<String, Integer> st1 = (ST<String, Integer>)Proxy.newProxyInstance(
                ActualTimings.class.getClassLoader(), 
                new Class[]{ST.class}, 
                new STHandler(new ExBinarySearchST<String, Integer>()));
        
        ST<String, Integer> st2 = (ST<String, Integer>)Proxy.newProxyInstance(
                ActualTimings.class.getClassLoader(), 
                new Class[]{ST.class}, 
                new STHandler(new ExBST<String, Integer>()));
        
        for(int i=10000; i< 330000; i*=2) 
            st1.draw2D(a2d, i, .0);
        
//        a2d.reDraw();
        
        for(int i=10000; i< 330000; i*=2) 
            st2.draw2D(a2d, i, .0);
        
//        a2d.reDraw();
    }
    
    
    interface ST<K, V>{
        void put(K k, V v);
        V get(K k);
        boolean contains(K k);
        void draw2D(Adjustable2DChart a2d, int GP, double time);
    }
    
    class ExBST<K, V> extends BST<String, Integer> implements ST<String, Integer> {
        @Override
        public void draw2D(Adjustable2DChart a2d, int GP, double time) {
            a2d.addAxisDataX(GP/1.0, (GP/10000)+"W");
            a2d.addAxisDataY(time, String .format("%.1f", time));
        }
    }
    class ExBinarySearchST<K, V> extends BinarySearchST<String, Integer> implements ST<String, Integer> {
        @Override
        public void draw2D(Adjustable2DChart a2d, int GP, double time) {
            a2d.addAxisDataX(GP/1.0, (GP/10000)+"W");
            a2d.addAxisDataY(time, String .format("%.1f", time));
            a2d.reDraw();
        }
    }
    
    class STHandler implements InvocationHandler {
        ST<String, Integer> st;
        public STHandler(ST<String, Integer> st) {
            this.st = st;
        }
        
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            
            if("draw2D".equals(method.getName())) {
                
                int stop = (Integer) args[1];
                Stopwatch watch = new Stopwatch();
                
                for(int i=RT; i>0; i--) {
                    
                    int gTimes = 0;
                    for(String word: src) {
                        if (st.contains(word)) {
                            st.put(word, st.get(word) + 1);
                            gTimes += 2;
                        } else {
                            st.put(word, 1);
                            gTimes++;
                        }
                        
                        if(gTimes >= stop) 
                            break;
                        
                    }
                    
                }
                
                double time = watch.elapsedTime()/RT;
                args[2] = time;
                method.invoke(st, args);
            }
            
            return null;
        }
        
    }
    
    public static void main(String[] args) {
        new ActualTimings().process();
        
    }

}
