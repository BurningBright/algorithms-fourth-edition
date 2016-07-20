package class0105;

import stdlib.StdOut;

/**
 * 
 * @Description 权重快联，将小树并上大树
 * @author Leon
 * @date 2016-07-14 17:13:23
 */
public class WeightedQuickUnionUF {
    private int[] id; // parent link (site indexed)
    private int[] sz; // size of component for roots (site indexed)
    private int count; // number of components

    public WeightedQuickUnionUF(int N) {
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;
        sz = new int[N];
        for (int i = 0; i < N; i++)
            sz[i] = 1;
    }

    public int count() {
        return count;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    private int find(int p) { // Follow links to find a root.
        while (p != id[p])
            p = id[p];
        return p;
    }

    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j)
            return;
        // Make smaller root point to larger one.
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i];
        } else {
            id[j] = i;
            sz[i] += sz[j];
        }
        count--;
//        StdOut.print(p + "  " + q + "  " + i + "  " + j);
//        StdOut.print(Arrays.toString(sz));
//        StdOut.println(Arrays.toString(id));
    }
    
    public static void main(String[] args) {
        int[] a = { 4, 3, 3, 8, 6, 5, 9, 4, 2, 1, 5, 0, 7, 2, 6, 1, 1, 0, 6, 7 };
        WeightedQuickUnionUF wqu = new WeightedQuickUnionUF(10);
        for(int i=0; i<a.length; i+=2) {
            wqu.union(a[i], a[i+1]);
        }
//      qwc.union(0, 1);
//      qwc.union(2, 3);
//      qwc.union(0, 2);
        StdOut.println(wqu.count());
    }
    
}