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
        for(int i=0; i<N; i++) 
            data[i] = i;
        
        for(int i=0; i<N; i++) {
            // 目标样本范围
            int T = data[i];
            int a = T-10<=0? 0: T-10;
            int b = T+10>=N-1? N-1: T+10;
            
            /*
             *  每次都要遍历准备目标-_-
             *  1.当前移动值需要随机到可接受范围
             *  2.被移动值如果可接受下标，纳入随机样本范围
             */
            int count = 0;
            int[] src;
            for(int j=a; j<=b; j++) {
                int abs = Math.abs(data[j] - i);
//                System.out.print(abs + " ");
                if (abs <= 10)
                    count += 1;
            }
            
            src = new int[count];
            for(int j=a, k=0; j<=b; j++) {
                
                int abs = Math.abs(data[j] - i) ;
                if (abs <= 10) {
                    src[k] = j;
                    k++;
                }
            }
            
            int r = StdRandom.uniform(count);
//            System.out.print(Arrays.toString(data));
//            System.out.printf("\t | %3d - %3d %3d - %3d\t", i, src[r], a, b);
//            System.out.println(Arrays.toString(src));
            
            int temp = data[i];
            data[i] = data[src[r]];
            data[src[r]] = temp;
        }
        /*
        for(int i=0; i<N; i++) {
            System.out.print(Math.abs(data[i]-i) + " ");
        }
        System.out.println();
        */
        
        return data;
    }
    
    // Sorted except for 5 percent
    public static int[] dataPrepareThree(int N) throws Exception {
        if(N<1)
            throw new Exception("zzz");
        int[] data = new int[N];
        int uniformNum = (int) (N * 0.05);
        for(int i=0; i<N; i++) 
            data[i] = i;
        
        for(int i=0; i<uniformNum/2; i++) {
            int r = i + StdRandom.uniform(N-i);     // between i and N-1
            int temp = data[i];
            data[i] = data[r];
            data[r] = temp;
        }
        /*
        int count = 0;
        for(int i=0; i<N; i++) {
            if(data[i] != i)
                count++;
        }
        System.out.println(count);
        */
        return data;
    }
    
    public static void main(String[] args) {
        try {
//            System.out.println(Arrays.toString(dataPrepareOne(50)));
            System.out.println(Arrays.toString(dataPrepareTwo(50)));
//            System.out.println(Arrays.toString(dataPrepareThree(100)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
