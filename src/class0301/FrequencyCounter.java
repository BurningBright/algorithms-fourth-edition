package class0301;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rlgs4.ST;
import stdlib.StdOut;

/**
 * @Description 3.1.19
 *      优先队列保留顶部数据
 * @author Leon
 * @date 2017-01-20 15:32:22
 */
public class FrequencyCounter {
    
    public static void main(String[] args) {
        int distinct = 0, words = 0;
//        int minlen = Integer.parseInt(args[0]);
        int minlen = 10, capacity = 10;
        ST<String, Integer> st = new ST<String, Integer>();
        
        File f = new File(FrequencyCounter.class.getResource("/").getPath().replace("bin", "lib")
                + "A Tale of Two Cities - Charles Dickens.txt");
        
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            String cur = null;
            while ((cur = br.readLine()) != null) {
                Matcher matcher = Pattern.compile("\\b.+?\\b").matcher(cur);
                while (matcher.find()) {
                    String word = matcher.group();
                    if (word.length() < minlen) continue;
                    words++;
                    if (st.contains(word)) {
                        st.put(word, st.get(word) + 1);
                    } else {
                        st.put(word, 1);
                        distinct++;
                    }
                    
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // find a key with the highest frequency count
        String max = "";
        st.put(max, 0);
        for (String word : st.keys()) {
            if (st.get(word) > st.get(max))
                max = word;
        }

        StdOut.println(max + " " + st.get(max));
        StdOut.println("distinct = " + distinct);
        StdOut.println("words    = " + words);
        
        //-----------------------------------------------------------
        
        Comparator<NameValuePair<String, Integer>> c = new Comparator<NameValuePair<String, Integer>>() {
            @Override
            public int compare(NameValuePair<String, Integer> o1, NameValuePair<String, Integer> o2) {
                return o1.getValue() - o2.getValue();
            }

        };
        
        PriorityQueue<NameValuePair<String, Integer>> pq = new PriorityQueue<NameValuePair<String, Integer>>(c);
        
        for (String word : st.keys()) {
            if(pq.size() == capacity) {
                pq.add(new NameValuePair<String, Integer>(word, st.get(word)));
                pq.remove();
            } else
                pq.add(new NameValuePair<String, Integer>(word, st.get(word)));
        }
        
        System.out.println(pq.size());
        
        for(int i=capacity-1; i>=0; i--) {
            NameValuePair<String, Integer> top = pq.remove();
            StdOut.println(top.getKey() + "\t" + top.getValue());
        }
    }
}
