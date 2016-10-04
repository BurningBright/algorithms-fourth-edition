package class0204;

import java.util.Arrays;

import rlgs4.MaxPQ;
import rlgs4.Stopwatch;
import stdlib.StdOut;
import stdlib.StdRandom;
/**
 * @Description 2.4.38
 *      边际案例
 * @author leon
 * @date 2016-10-04 18:00:50
 */
public class ExerciseDriver {
    
    private static int N = 5000000;
    
    public static void main(String[] args) {
        MaxPQ<Double> pq = new MaxPQ<Double>();
        
        double[] test1 = new double[N];
        for(int i=0; i<N; i++) 
            test1[i] = StdRandom.uniform();
        Arrays.sort(test1);
        Stopwatch sw = new Stopwatch();
        for(int i=0; i<N; i++)
            pq.insert(test1[i]);
        StdOut.printf("%s  %.3f\n", "ordered", sw.elapsedTime());
        for(int i=0; i<N; i++)
            pq.delMax();
        
        
        double[] test2 = new double[N];
        for(int i=0; i<N; i++) 
            test2[N-i-1] = test1[i];
        sw = new Stopwatch();
        for(int i=0; i<N; i++)
            pq.insert(test2[i]);
        StdOut.printf("%s  %.3f\n", "reverse ordered", sw.elapsedTime());
        for(int i=0; i<N; i++)
            pq.delMax();
        
        
        double[] test3 = new double[N];
        for(int i=0; i<N; i++) 
            test3[i] = .1;
        sw = new Stopwatch();
        for(int i=0; i<N; i++)
            pq.insert(test3[i]);
        StdOut.printf("%s  %.3f\n", "same key", sw.elapsedTime());
        for(int i=0; i<N; i++)
            pq.delMax();
        
        
        double[] test4 = new double[N];
        for(int i=0; i<N; i++) 
            test4[i] = StdRandom.uniform(0, 2);
        sw = new Stopwatch();
        for(int i=0; i<N; i++)
            pq.insert(test4[i]);
        StdOut.printf("%s  %.3f\n", "two key", sw.elapsedTime());
        for(int i=0; i<N; i++)
            pq.delMax();
        
        
        StdRandom.shuffle(test1);
        for(int i=0; i<N; i++)
            pq.insert(test1[i]);
        StdOut.printf("%s  %.3f\n", "refernce", sw.elapsedTime());
        for(int i=0; i<N; i++)
            pq.delMax();
    }

}
