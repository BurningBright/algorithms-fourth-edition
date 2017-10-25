package class0305;

import stdlib.StdOut;

/**
 * @Description 3.5.23 稀疏矩阵
 * @author Leon
 * @date 2017-10-25 09:24:00
 */
public class SparseMatrix {
    
    private int n;
    private SparseVector[] rows;
    
    public SparseMatrix(int n) {
        this.n = n;
        rows = new SparseVector[n];
        for (int i=0; i<n; i++) {
            rows[i] = new SparseVector(n);
        }
    }
    
    public double get(int i, int j) {
        if (i < 0 || i >= n) throw new IllegalArgumentException("Illegal index");
        if (j < 0 || j >= n) throw new IllegalArgumentException("Illegal index");
        return rows[i].get(j);
    }
    
    public void put(int i, int j, double data) {
        if (i < 0 || i >= n) throw new IllegalArgumentException("Illegal index");
        if (j < 0 || j >= n) throw new IllegalArgumentException("Illegal index");
        rows[i].put(j, data);
    }
    
    public SparseVector times(SparseVector x) {
        if(this.n != x.size()) throw new IllegalArgumentException("dimension not agree");
        SparseVector v = new SparseVector(n);
        for (int i=0; i<n; i++) {
            double ret = rows[i].dot(x);
            if(ret != .0) v.put(i, ret);
        }
        return v;
    }
    
    public SparseMatrix plus(SparseMatrix that) {
        if(this.n != that.n) throw new IllegalArgumentException("dimension not agree");
        SparseMatrix m = new SparseMatrix(n);
        for (int i=0; i<n; i++) {
            m.rows[i] = this.rows[i].sum(that.rows[i]);
        }
        return m;
    }
    
    public String toString() {
        String s = "";
        for (int i = 0; i < n; i++) {
            s += i + ": " + rows[i] + "\n";
        }
        return s;
    }
    
    public static void main(String[] args) {
        SparseMatrix A = new SparseMatrix(5);
        SparseVector x = new SparseVector(5);
        A.put(0, 0, 1.0);
        A.put(1, 1, 1.0);
        A.put(2, 2, 1.0);
        A.put(3, 3, 1.0);
        A.put(4, 4, 1.0);
        A.put(2, 4, 0.3);
        x.put(0, 0.75);
        x.put(2, 0.11);
        StdOut.println("x     : \r\n" + x);
        StdOut.println("A     : \r\n" + A);
        StdOut.println("Ax    : \r\n" + A.times(x));
        StdOut.println("A + A : \r\n" + A.plus(A));
    }

}
