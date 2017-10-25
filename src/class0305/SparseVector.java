package class0305;

import java.math.BigDecimal;

import rlgs4.ST;

/**
 * @Description 3.5.16 稀疏空间向量
 * @author Leon
 * @date 2017-10-18 16:16:00
 */
public class SparseVector {
    private int d;
    private ST<Integer, Double> st;

    public SparseVector() {
        st = new ST<Integer, Double>();
    }
    
    public SparseVector(int d) {
        this.d = d;
        st = new ST<Integer, Double>();
    }

    public int size() {
        return d;
    }

    public void put(int i, double x) {
        if (i < 0 || i >= d) throw new IllegalArgumentException("Illegal index");
        st.put(i, x);
    }

    public double get(int i) {
        if (i < 0 || i >= d) throw new IllegalArgumentException("Illegal index");
        if (!st.contains(i))
            return 0.0;
        else
            return st.get(i);
    }

    public double dot(SparseVector that) {
        double sum = 0.0;
     // iterate over the vector with the fewest nonzeros
        if (this.st.size() <= that.st.size()) {
            for (int i : this.st.keys())
                if (that.st.contains(i)) sum += this.get(i) * that.get(i);
        }
        else  {
            for (int i : that.st.keys())
                if (this.st.contains(i)) sum += this.get(i) * that.get(i);
        }
        return sum;
    }

    public SparseVector sum(SparseVector that) {
        if (this.d != that.d) throw new IllegalArgumentException("Vector lengths disagree");
        SparseVector c = new SparseVector(d);
        for (int i : this.st.keys())
            c.put(i, this.get(i));
        for (int i : that.st.keys()) {
            double result = add(that.get(i), c.get(i));
            if(result != 0)
                c.put(i, result);
        }
        return c;
    }
    
    public static double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }
    
    public String toString() {
        String s = "[";
        for(int i=0; i<d; i++) {
            if(i == d-1)
                s += get(i) + "]";
            else
                s += get(i) + " ";
        }
        return s;
    }
    
    public static void main(String[] args) {

    }

}
