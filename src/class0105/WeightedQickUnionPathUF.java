package class0105;

import stdlib.StdOut;

import java.util.Arrays;

import class0103.CircularQueue;
/**
 * Weighted quick-union with path compression
 * 使用数组
 * @author soft01
 *
 */
public class WeightedQickUnionPathUF {
    private int[] id;
    private int[] sz;
    private int count;
    
    public WeightedQickUnionPathUF(int N) {
        this.id = new int[N];
        this.sz = new int[N];
        this.count = N;
        for(int i=0; i<N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }
    
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
//      StdOut.print(p + "  " + q + "  t:" +circQ.tail() + " ");
        
        if(rootP == rootQ) {
//          StdOut.println();
            return ;
        }
        
        if(sz[rootP]>sz[rootQ]) {
            sz[rootP]  += sz[rootQ];
            
            for(int i=0; i<id.length; i++) {
                if(id[i] == id[rootQ])
                    id[i] = id[rootQ];
            }
            
        } else {
            sz[rootQ]  += sz[rootP];
            
            for(int i=0; i<id.length; i++) {
                if(id[i] == id[rootQ])
                    id[i] = id[rootQ];
            }
            
        }
        count--;
//      StdOut.print(Arrays.toString(id));
//      StdOut.println(Arrays.toString(sz));
    }
    
    public int find(int p) {
        while(p != id[p]) {
            //find path's next node
            p = id[p];
        }
        //it's p node's root
        return p;
    }
    
    public int getCount() {
        return count;
    }
    
    public static void main(String[] args) {
        int[] a = { 4, 3, 3, 8, 6, 5, 9, 4, 2, 1, 5, 0, 7, 2, 6, 1, 1, 0, 6, 7 };
        WeightedQickUnionPathUF qwc = new WeightedQickUnionPathUF(10);
//        for(int i=0; i<a.length; i+=2) {
//            qwc.union(a[i], a[i+1]);
//        }
      qwc.union(0, 1);
      qwc.union(2, 3);
      qwc.union(0, 2);
        StdOut.println(qwc.getCount());
    }
}
