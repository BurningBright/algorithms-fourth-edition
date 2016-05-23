package class0101;

import java.util.Arrays;

import stdlib.StdRandom;

/**
 * 
 * @Description 1.1.36
 *              every element in tar near M/N
 *              1.1.37
 *              can't see what different -_-
 * @author Leon
 * @date 2016-05-23 17:23:14
 */
public class ShuffleCheck {
    
    public static void shuffleCheck(int M, int N) {
        int src[] = new int[M];
        int tar[][] = new int[M][M];
        
        // init tar
        for(int i=0; i<M; i++)
            for(int j=0; j<M; j++)
                tar[i][j] = 0;
        
        for(int i=0; i<N; i++) {
            // loop init src
            for(int j=0; j<M; j++)
                src[j] = j;
            
            // upset
            StdRandom.shuffle(src);
            
            // calc
            for(int j=0; j<M; j++)
                tar[j][src[j]] += 1;
        }
        
        System.out.println(Arrays.deepToString(tar));
    }
    
    public static void shuffleCheckBad(int M, int N) {
        int src[] = new int[M];
        int tar[][] = new int[M][M];
        
        // init tar
        for(int i=0; i<M; i++)
            for(int j=0; j<M; j++)
                tar[i][j] = 0;
        
        for(int i=0; i<N; i++) {
            // loop init src
            for(int j=0; j<M; j++)
                src[j] = j;
            
            // upset
            badShuffling(src);
            
            // calc
            for(int j=0; j<M; j++)
                tar[j][src[j]] += 1;
        }
        
        System.out.println(Arrays.deepToString(tar));
    }
    
    private static void badShuffling(int[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = StdRandom.uniform(N);     // between 0 and N
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
    
    public static void main(String[] args) {
        shuffleCheck(7, 140);
        shuffleCheckBad(7, 140);
    }

}
