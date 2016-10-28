package class0205;

import java.util.Comparator;

import class0203.Quick;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.5.18
 *      强制稳定排序
 * @author leon
 * @date 2016-10-28 22:49:44
 */
public class ForceStability{
    
    @SuppressWarnings("rawtypes")
    public static void sort(Comparable[] a) {
        int N = a.length;
        Force[] f = new Force[N];
        for (int i=0; i<N; i++) 
            f[i] = new Force(i, a[i]);
        
        Quick.sort(f, new ForceStability().new ForceComparator());
        for(int i=0; i<N; i++) 
            a[i] = f[i].a;
    }
    
    private static class Force implements Comparable<Force>{
        
        int index;
        @SuppressWarnings("rawtypes")
        Comparable a;
        
        @SuppressWarnings("rawtypes")
        public Force(int index, Comparable a) {
            this.index = index;
            this.a = a;
        }

        @SuppressWarnings("unchecked")
        @Override
        public int compareTo(Force o) {
            if (a.compareTo(o.a) == 0)
                return index - o.index;
            else
                return a.compareTo(o.a);
        }

    }
    
    class ForceComparator implements Comparator<Force> {

        @SuppressWarnings("unchecked")
        @Override
        public int compare(Force o1, Force o2) {
            if (o1.a.compareTo(o2.a) == 0)
                return o1.index - o2.index;
            else
                return o1.a.compareTo(o2.a);
        }

    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void main(String[] args) {
        // 快排稳定性测试
        int N = 50;
        Comparable[] origin = new Integer[N];
        Comparable[] sorted = new Integer[N];
        
        for (int i=0; i<N; i++) {
            sorted[i] = StdRandom.uniform(512, 520);
            origin[i] = sorted[i];
        }
        
        CheckStability c1 = new CheckStability<Integer>();
        Quick.sort(sorted);
        StdOut.println(c1.check(origin, sorted));
        
        for (int i=0; i<N; i++) {
            sorted[i] = StdRandom.uniform(512, 520);
            origin[i] = sorted[i];
        }
        CheckStability c2 = new CheckStability<Integer>();
        ForceStability.sort(sorted);
        StdOut.println(c2.check(origin, sorted));
        
    }

}
