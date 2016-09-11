package class0203;
/**
 * @Description 2.3.23
 *          取9元素的中位元素作为标杆
 * @author Leon
 * @date 2016-09-11 09:31:49
 */
public class TukeyNinther {
    
    @SuppressWarnings("rawtypes")
    public static int Median9(Comparable[] a, int lo, int hi) {
        int N = hi - lo + 1;
        int eps = N/8;
        int mid = lo + N/2;
        int m1 = MedianOfThree.median3(a, lo, lo + eps, lo + eps + eps);
        int m2 = MedianOfThree.median3(a, mid - eps, mid, mid + eps);
        int m3 = MedianOfThree.median3(a, hi - eps - eps, hi - eps, hi);
        return MedianOfThree.median3(a, m1, m2, m3);
    }
    
}
