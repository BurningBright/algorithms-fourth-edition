package class0101;
/**
 * @Description 1.1.20
 * @author leon
 * @date 2016-05-22 21:43:28
 */
public class NatureLogarithm {
    
    public static double ln(int N) {
        if(N == 1) return 0;
        return Math.log(N) + ln(N-1);
    }
    
    public static void main(String[] args) {
        System.out.println(ln(3));
    }

}
