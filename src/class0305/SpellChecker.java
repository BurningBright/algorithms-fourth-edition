package class0305;

import rlgs4.LinearProbingHashST;
import rlgs4.RedBlackBST;
import rlgs4.SeparateChainingHashST;
import rlgs4.Stopwatch;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 3.5.31
 *      检查战争与和平拼写
 *      resource/3.5/misspellings.csv resource/3.5/war+peace.txt
 *      LinearProbingHashST is the best
 * @author Leon
 * @date 2017-10-23 09:28:00
 */
public class SpellChecker {

    private SeparateChainingHashST<String, Object> seprate = 
            new SeparateChainingHashST<String, Object>();
    
    private LinearProbingHashST<String, Object> linear = 
            new LinearProbingHashST<String, Object>();
    
    private RedBlackBST<String, Object> redBlack = 
            new RedBlackBST<String, Object>();
    
    SpellChecker(String dictionary){
        
        Stopwatch watch = new Stopwatch();
        In in = new In(dictionary);
        in.readLine();
        while (in.hasNextLine()) {
            String line = in.readLine();
            if(!line.contains(","))
                continue;
            String[] tokens = line.split(",");
            String key = tokens[0];
            String val = tokens[1];
            seprate.put(key, val);
        }
        
        StdOut.println("SeparateChainingHashST : " + watch.elapsedTime());
        
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
            String val = tokens[1];
            redBlack.put(key, val);
        }
        
        StdOut.println("RedBlackBST : " + watch.elapsedTime());
        
    }
    
    public void sepFilter(String book) {
        StdOut.println("\r\n\r\n\r\n----------------------------");
        Stopwatch watch = new Stopwatch();
        In in = new In(book);
        while (!in.isEmpty()) {
            String tmp = in.readString();
            if(seprate.contains(tmp)) {
                StdOut.println("error:" + tmp + "| correct:" + seprate.get(tmp));
            }
        }
        StdOut.println("SeparateChainingHashST finished : " + watch.elapsedTime());
        
        StdOut.println("----------------------------");
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
    
    public void rbFilter(String book) {
        StdOut.println("\r\n\r\n\r\n----------------------------");
        Stopwatch watch = new Stopwatch();
        In in = new In(book);
        while (!in.isEmpty()) {
            String tmp = in.readString();
            if(redBlack.contains(tmp)) {
                StdOut.println("error:" + tmp + "| correct:" + redBlack.get(tmp));
            }
        }
        StdOut.println("RedBlackBST finished : " + watch.elapsedTime());
        
        StdOut.println("----------------------------");
    }
    
    
    public static void main(String[] args) {
        SpellChecker s = new SpellChecker(args[0]);
        s.sepFilter(args[1]);
        s.linFilter(args[1]);
        s.rbFilter(args[1]);
    }

}
