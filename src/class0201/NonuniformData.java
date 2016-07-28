package class0201;

import java.util.Arrays;

import stdlib.StdRandom;

/**
 * @Description 2.1.36
 * @author Leon
 * @date 2016-07-28 16:39:42
 */
public class NonuniformData {
    
    // 一半0，一半1
    public static int[] dataPrepareOne(int N) throws Exception {
        if(N<1)
            throw new Exception("zzz");
        int[] data = new int[N];
        int point = (int) Math.ceil(N / 2.0);
//        int P = N/2;
        for(int i=0; i<point; i++) 
            data[i] = 0;
        
        for(int i=point; i<N; i++) 
            data[i] = 1;
        
        return data;
    }
    
    // 一半递归递增
    public static int[] dataPrepareTwo(int N) throws Exception {
        if(N<1)
            throw new Exception("zzz");
        int[] data = new int[N];
        double radio = 1/2.0;
        int start = 0;
        int num = 0;
        while (radio * N >= 1) {
            int point = (int) Math.ceil(N * (1-radio));
            for(int i=start; i<point; i++) {
                data[i] = num;
            }
            radio = radio/2;
            start = point;
            num++;
        }
        data[N-1] = num;
        return data;
    }
    
    // 一半同key，一半随机
    public static int[] dataPrepareThree(int N) throws Exception {
        if(N<1)
            throw new Exception("zzz");
        int[] data = new int[N];
        int point = (int) Math.ceil(N / 2.0);
        for(int i=0; i<point; i++) 
            data[i] = 0;
        
        for(int i=point; i<N; i++) 
            data[i] = StdRandom.uniform(100);
        
        return data;
    }
    
    
    public static void main(String[] args) {
        try {
            System.out.println(Arrays.toString(dataPrepareOne(50)));
            System.out.println(Arrays.toString(dataPrepareTwo(50)));
            System.out.println(Arrays.toString(dataPrepareThree(50)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
