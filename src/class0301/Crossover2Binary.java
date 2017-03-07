package class0301;

import java.util.Random;

import rlgs4.Stopwatch;
import stdlib.StdRandom;
/**
 * @Description 3.1.40
 *      Crossover to binary search
 * @author Leon
 * @date 2017-03-07 15:33:09
 */
public class Crossover2Binary {
    
    public static void main(String[] args) {
        int N = 50000;
        int T = 1000;
        int bound = 100000;
        
        // data prepare
        String[] src = new String[N];
        for (int i=0; i<N; i++) 
            src[i] = i + "";
        StdRandom.shuffle(src);
        
        String[] rnd = new String[N];
        Random random = new Random();
        for (int i=0; i<N; i++) 
            rnd[i] = random.nextInt(bound) + "";
        
        // data structure
        Object obj = new Object();
        BinarySearchST<String, Object> bst = new BinarySearchST<String, Object>();
        OrderedSequentialSearchST<String, Object> sst = new OrderedSequentialSearchST<String, Object>();
        for (int i=0; i<N; i++) {
            bst.put(src[i], obj);
            sst.put(src[i], obj);
        }
        
        Stopwatch watch = new Stopwatch();
        for (int i=0; i<T; i++) 
            bst.get(rnd[i]);
        System.out.println(watch.elapsedTime());
        
        watch = new Stopwatch();
        for (int i=0; i<T; i++) 
            sst.get(rnd[i]);
        System.out.println(watch.elapsedTime());
    }

}
