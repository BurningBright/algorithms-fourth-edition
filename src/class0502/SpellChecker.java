package class0502;

import rlgs4.LinearProbingHashST;
import rlgs4.Stopwatch;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 5.2.24
 *      重新检查战争与和平拼写
 *      resource/3.5/misspellings.csv resource/3.5/war+peace.txt
 *      LinearProbingHashST 
 *      vs StringSET [is the best]
 * @author Leon
 * @date 2018-03-06 16:28:00
 */
public class SpellChecker {

    private LinearProbingHashST<String, Object> linear = 
            new LinearProbingHashST<String, Object>();
    
    private StringSET set = new StringSET();
    
    SpellChecker(String dictionary){
        
        Stopwatch watch = new Stopwatch();
        In in = new In(dictionary);
        in.readLine();
        
        watch = new Stopwatch();
        in = new In(dictionary);
        in.readLine();
        while (in.hasNextLine()) {
            String line = in.readLine();
            if(!line.contains(","))
                continue;
            String[] tokens = line.split(",");
            String key = tokens[0];
            String val = tokens[1];
            linear.put(key, val);
        }
        
        StdOut.println("LinearProbingHashST : " + watch.elapsedTime());
        
        watch = new Stopwatch();
        in = new In(dictionary);
        in.readLine();
        while (in.hasNextLine()) {
            String line = in.readLine();
            if(!line.contains(","))
                continue;
            String[] tokens = line.split(",");
            String key = tokens[0];
//            String val = tokens[1];
            set.add(key);
        }
        
        StdOut.println("StringSET : " + watch.elapsedTime());
        
    }
    
    public void linFilter(String book) {
        StdOut.println("\r\n\r\n\r\n----------------------------");
        Stopwatch watch = new Stopwatch();
        In in = new In(book);
        while (!in.isEmpty()) {
            String tmp = in.readString();
            if(linear.contains(tmp)) {
                StdOut.println("error:" + tmp + "| correct:" + linear.get(tmp));
            }
        }
        StdOut.println("LinearProbingHashST finished : " + watch.elapsedTime());
        
        StdOut.println("----------------------------");
    }
    
    public void setFilter(String book) {
        StdOut.println("\r\n\r\n\r\n----------------------------");
        Stopwatch watch = new Stopwatch();
        In in = new In(book);
        while (!in.isEmpty()) {
            String tmp = in.readString();
            if(set.contains(tmp)) {
                StdOut.println("error:" + tmp + "| correct:" );
            }
        }
        StdOut.println("StringSET finished : " + watch.elapsedTime());
        
        StdOut.println("----------------------------");
    }
    
    
    public static void main(String[] args) {
        SpellChecker s = new SpellChecker(args[0]);
        s.linFilter(args[1]);
        s.setFilter(args[1]);
    }

}
