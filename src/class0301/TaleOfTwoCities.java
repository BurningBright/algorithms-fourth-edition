package class0301;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import stdlib.StdOut;

/**
 * @Description 3.1.8
 *          统计双城记10字符以上单词出现最多的单词
 * @author Leon
 * @date 2016-12-12 16:15:27
 */
public class TaleOfTwoCities {

    public static void main(String[] args) {
        
        String path = TaleOfTwoCities.class.getResource("/").getPath();
        path = path.substring(0, path.lastIndexOf("bin", path.length())) + "lib/A Tale of Two Cities - Charles Dickens.txt";
        System.out.println(path);
        File f = new File(path);
        System.out.println(f.isFile());
        
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            
//            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
            
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            String cur = null;
            while ((cur = br.readLine()) != null) {
                
                Matcher matcher = Pattern.compile("\\b.+?\\b").matcher(cur);
                while (matcher.find()) {
                    String word = matcher.group();
                    if(word.length() > 10) 
                        map.put(word, map.get(word) == null ? 1: map.get(word) + 1);
                }
                
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        for(Entry<String, Integer> entry: map.entrySet()) {
            if(entry.getValue() > 10)
                StdOut.println(entry.getKey() + "\t\t" + entry.getValue());
        }
        
    }

}
