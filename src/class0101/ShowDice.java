package class0101;

import stdlib.StdRandom;

/**
 * @Description 1.1.35
 *           统计1700次左右会接近小数后三位
 * @author Leon
 * @date 2016-05-23 16:49:26
 */
public class ShowDice {

    public static void main(String[] args) {
        int SIDES = 6;
        // 加一个对脑子好哦
        int SIZE = 2 * SIDES + 1;
        double[] dist = new double[SIZE];
        for (int i = 1; i <= SIDES; i++) {
            for (int j = 1; j <= SIDES; j++) {
                dist[i + j] += 1.0;
            }
        }

        Histogram.width = 1.0 / SIZE;
        for (int k = 2; k <= 2 * SIDES; k++) {
            Histogram.dividing(k * 1.0 / SIZE, dist[k] / 36);
        }

        System.out.printf("%.3f", dist[3]);
        
        // 目标设为3，概率应为2/36
        long count = 0;
        int N = 10000;
        for(int i=0; i<N; i++) {
            long n = 0;
            long m = 0;
            double P = 0;
            
            while ((int)(P*1000) != (int)(dist[3]*1000/36)) {
                int a = StdRandom.uniform(6) + 1;
                int b = StdRandom.uniform(6) + 1;
                
                n++;
                if(a + b == 3) { m++; }
                
                P = (double)m/n;
                
//                System.out.printf("%.6f", P);
//                System.out.println();
            }
//            System.out.println(m+"   "+n);
            count += n;
        }
        System.out.println();
        System.out.println("--"+count/N);
    }

}
