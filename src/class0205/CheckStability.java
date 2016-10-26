package class0205;

import class0201.InsertionSort;
import class0201.SelectionSort;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.5.17
 *      检查排序稳定性
 * @author Leon
 * @date 2016-10-26 16:14:52
 */
public class CheckStability<T extends Comparable<T>> {
    
    public boolean check(T[] origin, T[] sorted) {
        for (int i=1; i<sorted.length; i++) {
            if (sorted[i].compareTo(sorted[i-1]) == 0) {
                int indexA = 0;
                int indexB = 0;
                for (int j=0; j<origin.length; j++)
                    if (origin[j] == sorted[i-1]) {
                        indexA = j;
                        break;
                    }
                
                for (int j=0; j<origin.length; j++)
                    if (origin[j] == sorted[i]) {
                        indexB = j;
                        break;
                    }
                
                if(indexA > indexB)
                    return false;
            }
        }
        return true;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void main(String[] args) {
//        Integer[] a = {1, 2, 2, 3};
        Integer[] a = {1000, 2000, 2000, 3000};
        Integer[] b = new Integer[a.length];
        b[0] = a[3];
        b[1] = a[2];
        b[2] = a[1];
        b[3] = a[0];
        
        CheckStability<Integer> c1 = new CheckStability<Integer>();
        StdOut.println(c1.check(b, a));
        
        
        // 选排稳定性测试
        int N = 50;
        Comparable[] origin = new Integer[N];
        Comparable[] sorted = new Integer[N];
        
        for (int i=0; i<N; i++) {
            sorted[i] = StdRandom.uniform(512, 520);
            origin[i] = sorted[i];
        }
        SelectionSort.sort(sorted);
        CheckStability c2 = new CheckStability<Integer>();
        StdOut.println(c2.check(origin, sorted));
        
        
        // 插排稳定性测试
        for (int i=0; i<N; i++) {
            sorted[i] = StdRandom.uniform(512, 520);
            origin[i] = sorted[i];
        }
        InsertionSort.sort(sorted);
        StdOut.println(c2.check(origin, sorted));
        
    }

}
