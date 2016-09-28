package class0204;

import java.util.Arrays;

import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.4.35
 *      离散随机分布 用完全二叉树组织
 *      显式指针是什么鬼？
 * @author Leon
 * @date 2016-09-28 10:19:28
 */
public class DiscreteDistribution {
    
    private double[] a;
    private double[] w;
    
    public DiscreteDistribution(double[] a) {
        this.a = a;
        w = new double[a.length+1];
        for(int i=0; i<a.length; i++) {
            w[i+1] = a[i];
            for(int j=(i+1)/2; j>=1; j/=2) {
                w[j] += w[i+1]; 
            }
        }
        StdOut.println(Arrays.toString(w));
        print();
    }
    
    public void print() {
        int c=1;
        for(int i=1; i > 0; i=Math.min(i*2, a.length+1 - c)) {
            for(int j=i; j>0; j--)
                StdOut.printf("%.2f   ", w[c++]);
            StdOut.println();
        }
    }
    
    public int random() {
        double r = StdRandom.uniform(0, w[1]);
        int k = 1;
        int N = w.length-1;
        while (2*k <= N) {
            int j = 2*k;
            int t = j;
            if (j < N && less(w[j], w[j+1])) t = j+1;
            if (!less(r, w[t])) return k-1;
            
            if (less(Math.abs(r - w[j]), Math.abs(r - w[j + 1])))
                k = j;
            else
                k = j + 1;
            
        }
        return k -1;
    }
    
    public void change(int i, double v) {
        double offset = v - a[i++];
        for(i=i+1; i>=1; i/=2)
            w[i] += offset;
    }
    
    private boolean less(double i, double j) {
        return i-j < 0;
    }
    
    public static void main(String[] args) {
        int N = 15;
        double[] a = new double[N];
        for(int i=0; i<N; i++) {
            a[i] = StdRandom.uniform(0, 10.0);
        }
        StdOut.println(Arrays.toString(a));
        DiscreteDistribution dd = new DiscreteDistribution(a);
        /*
        for(int i=0; i<100; i++) {
            if(i%10 == 0)
                StdOut.println();
            StdOut.print(dd.random() + " ");
        }
        */
        
        dd.change(3, 10.0);
        dd.print();
    }

}
