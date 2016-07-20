package class0105;

import stdlib.StdOut;
/**
 * Weighted quick-union with path compression
 * 使用数组 保存子树
 * @author soft01
 *
 */
public class WeightedQuickUnionPathUF {
    private int[] id;
    private int[][] sz;
    private int count;
    
    public WeightedQuickUnionPathUF(int N) {
        this.id = new int[N];
        this.sz = new int[N][];
        this.count = N;
        for(int i=0; i<N; i++) {
            id[i] = i;
            sz[i] = new int[0];
        }
    }
    
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        
        if(rootP == rootQ) {
            return ;
        }
        
        if(sz[rootP].length>sz[rootQ].length) {
            id[rootQ] = id[rootP];
            
            for(int i=0; i<sz[rootQ].length; i++)
                id[sz[rootQ][i]] = id[rootP];
            
            // Q树合并到P树上
            int[] tmp = new int[sz[rootP].length + sz[rootQ].length + 1];
            System.arraycopy(sz[rootP], 0, tmp, 0, sz[rootP].length);
            System.arraycopy(sz[rootQ], 0, tmp, sz[rootP].length, sz[rootQ].length);
            tmp[tmp.length-1] = rootQ;
            sz[rootP] = tmp;
        } else {
            id[rootP] = id[rootQ];
            
            for(int i=0; i<sz[rootP].length; i++)
                id[sz[rootP][i]] = id[rootQ];
            
            // P树合并到Q树上
            int[] tmp = new int[sz[rootP].length + sz[rootQ].length + 1];
            System.arraycopy(sz[rootQ], 0, tmp, 0, sz[rootQ].length);
            System.arraycopy(sz[rootP], 0, tmp, sz[rootQ].length, sz[rootP].length);
            tmp[tmp.length-1] = rootP;
            sz[rootQ] = tmp;
        }
        count--;
        
//        StdOut.print(p+" "+q);
//        StdOut.println(Arrays.toString(id));
//        StdOut.print("P:"+rootP+Arrays.toString(sz[rootP]));
//        StdOut.println("Q:"+rootQ+Arrays.toString(sz[rootQ]));
    }
    
    public int find(int p) {
        return p!=id[p] ? id[p]: p;
    }
    
    public int count() {
        return count;
    }
    
    public static void main(String[] args) {
        int[] a = { 4, 3, 3, 8, 6, 5, 9, 4, 2, 1, 5, 0, 7, 2, 6, 1, 1, 0, 6, 7 };
        WeightedQuickUnionPathUF qwc = new WeightedQuickUnionPathUF(10);
        for(int i=0; i<a.length; i+=2) {
            qwc.union(a[i], a[i+1]);
        }
//      qwc.union(0, 1);
//      qwc.union(2, 3);
//      qwc.union(0, 2);
        StdOut.println(qwc.count());
    }
}
