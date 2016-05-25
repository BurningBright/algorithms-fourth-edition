package class0102;
/**
 * @Description 1.2.9
 *              累计二分查找中检查键次数
 * @author Leon
 * @date 2016-05-25 11:20:58
 */
public class BiSCount {
    
    public static int rank(int key, int[] a, int lo, int hi, Counter counter) {
        if(lo > hi) return -1;
        int mid = lo + (hi-lo)/2;
        System.out.println(lo+"-"+mid+"-"+hi+"--"+counter.count);
        
        if(key < a[mid]) {
            counter.count++;
            return rank(key, a, lo, mid, counter);
        }
        else if(key > a[mid]) {
            counter.count++;
            return rank(key, a, mid, hi, counter);
        }
        else {
            counter.count++;
            return mid;
        }
    }
    
    public static void main(String[] args) {
//        Integer N = new Integer(0);
        Counter N = new Counter();
        int[] src = {1,3,5,7,9,11,13,15,17};
        System.out.println("result : " + rank(7, src, 0, src.length, N));
        System.out.println("step : " + N.count);
    }
    
    static class Counter{
        int count=0;
    }

}
