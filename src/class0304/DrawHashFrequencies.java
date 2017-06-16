package class0304;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import class0201.AnimationSortCompare;
import class0301.FrequencyCounter;
import rlgs4.SET;
import rlgs4.ST;
import stdlib.StdDraw;

/**
 * @Description draw hash frequencies
 * @author Leon
 * @date 2017-06-15 15:36:13
 */
public class DrawHashFrequencies {

    int SIZE = 97;
    ST<Integer, Integer> map = new ST<Integer, Integer>();
    SET<String> set = new SET<String>();

    private void traverseTxt() {

        File f = new File(FrequencyCounter.class.getResource("/").getPath().replace("bin", "lib")
                + "A Tale of Two Cities - Charles Dickens.txt");
        
        // char[] filter = {' ', '!', ',', '\'', '.', ';', '/'};
        
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            String cur = null;
            while ((cur = br.readLine()) != null) {
                Matcher matcher = Pattern.compile("\\b.+?\\b").matcher(cur);
                while (matcher.find()) {
                    String word = matcher.group();
                    
                    // filter repeat word
                    if(set.contains(word))
                        continue;
                    else {
                        set.add(word);
                        Integer key = word.hashCode() % SIZE;
                        @SuppressWarnings("unused")
                        Integer tmp;
                        map.put(key, (tmp = map.get(key)) == null ? 1 : map.get(key) + 1);
                    }
                    
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void drawHistogram() {
        traverseTxt();
        if (map.size() == 0)
            throw new RuntimeException("xxx");
        
        // rectangle parameter
        AnimationSortCompare.internalX = .006;
        AnimationSortCompare.halfWidth = .004;

        StdDraw.setCanvasSize(1024, 384);
        StdDraw.setYscale(0, 120);

        // rearrange data
        Comparable<Double>[] value = new Double[SIZE];
        int sum = 0;

        for (int i = 0; i < SIZE; i++) {
            Integer tmp = map.get(i);
            tmp = tmp == null ? 0 : tmp;
            value[i] = tmp.doubleValue();
            sum += tmp;
        }
        System.out.println(sum);

        // paint
        AnimationSortCompare.draw(value, 0, 0);

    }

    public static void main(String[] args) {
        (new DrawHashFrequencies()).drawHistogram();
    }

}
