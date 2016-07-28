package class0201;

import java.util.Arrays;

import rlgs4.Stopwatch;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.1.29
 *      compare the increment sequence
 * @author Leon
 * @date 2016-07-28 09:31:31
 */
public class ShellSortIncrements {
    
    public static int[] dataPrepare(int N) {
        int countOne = 0;
        int countTwo = 2;
        int[] sequence = new int[N*2];
        
        for(int i=0; i<N; i++,countOne++) 
            sequence[i] = funcOne(countOne);
        for(int i=N; i<2*N; i++,countTwo++) 
            sequence[i] = funcTwo(countTwo);
        
        Arrays.sort(sequence);
//        System.out.println(Arrays.toString(sequence));
        return sequence;
    }
    
    private static int funcOne(int k) {
        return (int) (9 * Math.pow(4, k) - 9 * Math.pow(2, k) + 1);
    }
    
    private static int funcTwo(int k) {
        return (int) (Math.pow(4, k) - 3 * Math.pow(2, k) + 1);
    }
    
    public static void sort(Comparable<Object>[] a, int[] sequence) {
        int h = 0;
        int N = a.length;
        while (h < sequence.length-1 && N > sequence[h]) 
            h++;
        
        while (h >= 0) {
            
            int H = sequence[h];
            for (int i = H; i < N; i++) {
                for (int j = i; j >= H && ShellSort.less(a[j], a[j - H]); j -= H) {
                    ShellSort.exch(a, j, j - H);
                }
            }
            h--;
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void main(String[] args) {
        Comparable<Object>[] a = (Comparable[]) "E A S Y S H E L L S O R T Q U E S T I O N".split(" ");
        sort(a, dataPrepare(10));
        assert ShellSort.isSorted(a);
        ShellSort.show(a);
        
        
        double preShell = 0.001;
        double preIncre = 0.001;
        double shellTime, increTime, shellMulti, increMulti;
        shellTime = increTime = shellMulti = increMulti = 0;
        
        int N = 4096;
        int count = 0;
        for (int i = N; i < 4194305; i *= 2) {
            
            Comparable<Object>[] src = (Comparable[]) new Double[i];
            for (int j = 0; j < i; j++) {
                src[j] = (Comparable)StdRandom.uniform();
            }
            
            Stopwatch sw = new Stopwatch();
            ShellSort.sort(src.clone());
            double cur = sw.elapsedTime();
            
            shellTime += cur;
            shellMulti += cur / (i==4096? cur: preShell);
            StdOut.printf("%10d %7.3f %5.3f\t", i, cur, cur / (i==4096? cur: preShell));
            preShell = cur;
            
            
            
            sw = new Stopwatch();
            sort(src, dataPrepare(10));
            cur = sw.elapsedTime();
            
            increTime += cur;
            increMulti += cur / (i==4096? cur: preIncre);
            StdOut.printf("%10d %7.3f %5.3f\n", i, cur, cur / (i==4096? cur: preIncre));
            preIncre = cur;
            
            count++;
        }
        StdOut.printf("\t\t %7.3f %5.3f \t\t%7.3f %5.3f \n", shellTime, shellMulti/count, increTime, increMulti/count);
    }
    
}
