package class0201;

import java.util.Arrays;

import stdlib.StdRandom;

/**
 * @Description 2.1.37
 * @author Leon
 * @date 2016-07-28 17:37:09
 */
public class PartiallySorted {
    
    // 95%有序，5%无序
    public static int[] dataPrepareOne(int N) throws Exception {
        if(N<1)
            throw new Exception("zzz");
        int[] data = new int[N]; 
        int point = (int) Math.ceil(N * 0.95);
        for(int i=0; i<point; i++) 
            data[i] = i;
        
        for(int i=point; i<N; i++) 
            data[i] = StdRandom.uniform(100);
            
        return data;
    }
    
    // All entries within 10 positions
    public static int[] dataPrepareTwo(int N) throws Exception {
        if(N<1)
            throw new Exception("zzz");
        int[] data = new int[N];
        int point = (int) Math.ceil(N * 0.95);
        
        
        return data;
    }
    
    // Sorted except for 5 percent
    public static int[] dataPrepareThree(int N) throws Exception {
        if(N<1)
            throw new Exception("zzz");
        int[] data = new int[N];
        int uniformNum = (int) (N * 0.05);
        uniformNum = uniformNum - (uniformNum % 2);
        for(int i=0; i<N; i++) 
            data[i] = i;
        
        for(int i=0; i<uniformNum/2; i++) {
            int a = StdRandom.uniform(N);
            int b = StdRandom.uniform(N);
            int tmp = data[a];
            data[a] = data[b];
            data[b] = tmp;
        }
        
        return data;
    }
    
    public static void main(String[] args) {
        try {
//            System.out.println(Arrays.toString(dataPrepareOne(50)));
            System.out.println(Arrays.toString(dataPrepareThree(50)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
