package class0205;

import java.util.Arrays;

import rlgs4.Quick;
import stdlib.StdOut;

/**
 * @Description 多维排序
 * @author leon
 * @date 2016-11-6 14:52:25
 */
public class Vector<T extends Comparable<T>> implements Comparable<Vector<T>>{
    
    private T[] v;
    
    public Vector(T[] v) {
        this.v = v;
    }
    
    @Override
    public int compareTo(Vector<T> o) {
        int N = Math.min(v.length, o.v.length);
        for (int i=0; i<N; i++) 
            if(v[i].compareTo(o.v[i]) != 0)
                return v[i].compareTo(o.v[i]);
        
        return v.length - o.v.length;
    }
    
    @Override
    public String toString() {
        return "Vector [v=" + Arrays.toString(v) + "]\n";
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void main(String[] args) {
        Integer[][] src = new Integer[6][];
        src[0] = new Integer[]{3, 3, 3};
        src[1] = new Integer[]{3, 3, 2};
        src[2] = new Integer[]{3, 2, 3};
        src[3] = new Integer[]{3, 2, 2};
        src[4] = new Integer[]{2, 3, 3};
        src[5] = new Integer[]{2, 2, 3};
        
        Vector[] v = new Vector[6];
        for (int i=0; i<6; i++)
            v[i] = new Vector(src[i]);
            
        Quick.sort(v);
        
        StdOut.println(Arrays.toString(v));
    }


}
