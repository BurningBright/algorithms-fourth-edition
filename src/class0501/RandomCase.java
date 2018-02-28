package class0501;

import stdlib.StdOut;
import stdlib.StdRandom;
/**
 * @Description 5.1.18/19/20/21
 *          随机案例
 * @author Leon
 * @date 2018-02-28 09:50:00
 */
public class RandomCase {
    
    public static String[] decimal(int N, int W) {
        if (N < 1 || W < 1)
            throw new IllegalArgumentException("error");
        String[] src = new String[N];
        String format = "%." + W +"f";
        for (int i=0; i<N; i++) 
            src[i] = String.format(format, StdRandom.uniform());
        return src;
    }
    
    public static String[] ca(int N) {
        if (N < 1)
            throw new IllegalArgumentException("error");
        String[] src = new String[N];
        for (int i=0; i<N; i++) 
            src[i] = StdRandom.uniform(10) + 
            "" + (char)(65+StdRandom.uniform(26)) +
            "" + (char)(65+StdRandom.uniform(26)) +
            "" + (char)(65+StdRandom.uniform(26)) +
            "" + (100 + StdRandom.uniform(900));
        return src;
    }
    
    public static String[] world(int N, int W) {
        if (N < 1 || W < 1)
            throw new IllegalArgumentException("error");
        String[] src = new String[N];
        for (int i=0; i<N; i++) {
            String tmp = "";
            for (int j=0; j<W; j++)
                tmp += (char)(97+StdRandom.uniform(26));
            src[i] = tmp;
        }
            
        return src;
    }
    
    public static String[] items(int N) {
        if (N < 1)
            throw new IllegalArgumentException("error");
        String[] src = new String[N];
        String[] set1 = world(10, 4);
        String[] set2 = world(50, 10);
        String[] set3 = world(2, 1);
        
        for (int i=0; i<N; i++) 
            src[i] = set1[StdRandom.uniform(10)] +
                    set2[StdRandom.uniform(50)] +
                    set3[StdRandom.uniform(2)] +
                    world(1, 4+StdRandom.uniform(12))[0];
        
        return src;
    }
    
    public static void main(String[] args) {
        StdOut.println(decimal(1, 5)[0]);
        StdOut.println(ca(1)[0]);
        StdOut.println(world(1, 3)[0]);
        
        String[] str = items(100);
        for (String s: str)
            StdOut.println(s);
    }
    
}
