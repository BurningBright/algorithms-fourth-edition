package class0305;

import java.math.BigDecimal;

import rlgs4.ST;

/**
 * @Description 3.5.16 空间向量
 * @author Leon
 * @date 2017-10-18 16:16:00
 */
public class SparseVector {
    private int d;
    private ST<Integer, Double> st;

    public SparseVector(int d) {
        this.d = d;
        st = new ST<Integer, Double>();
    }

    public int size() {
        return st.size();
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

    public double dot(double[] that) {
        double sum = 0.0;
        for (int i : st.keys())
            sum += that[i] * this.get(i);
        return sum;
    }

    public SparseVector sum(SparseVector that) {
        if (this.d != that.d) throw new IllegalArgumentException("Vector lengths disagree");
        SparseVector c = new SparseVector(d);
        for (int i : this.st.keys()) c.put(i, this.get(i));
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
    
    public static void main(String[] args) {

    }

}
