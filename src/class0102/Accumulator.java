package class0102;
/**
 * @Description 1.2.18 累加方差
 *          /N-1 样本方差
 *          /N 方差
 *          (N-1)/N 无偏数学期望
 * @author Leon
 * @date 2016-05-26 10:36:46
 */
public class Accumulator {
    
    private double m;
    private double s;
    private int N;
    
    public Accumulator() {
        m = 0;
        s = 0;
        N = 0; 
    }
    
    public void addDataValue(double x) {
        N++;
//        System.out.println("-"+s+" - "+m);
        s = s + 1.0 * (N - 1) / N * (x - m) * (x - m);
//        System.out.println("+"+s);
        m = m + (x - m) / N;
//        System.out.println("#"+m);
//        System.out.println();
    }
    
    public double mean() {
        return m;
    }
    
    public double var() {
        return s / (N - 1);
    }
    
    public double stdDev() {
        return Math.sqrt(this.var());
    }
    
    public static void main(String[] args) {
        Accumulator a = new Accumulator();
        a.addDataValue(1);
        a.addDataValue(3);
        a.addDataValue(5);
        a.addDataValue(7);
        System.out.println(a.var());
        System.out.println(a.stdDev());
    }

}
