package class0204;

import java.util.Comparator;
import java.util.NoSuchElementException;

import rlgs4.MaxPQ;
import rlgs4.MinPQ;
import stdlib.StdOut;

/**
 * @Description 2.4.30
 *      通过使用两堆，对中位数的
 *      查找都是常数级，插入、删除都是对数级
 * @author Leon
 * @date 2016-09-26 11:39:10
 */
public class DynamicMedianFinding<Key> {
    
    private int N = 0;
    private Key median;
    private MaxPQ<Key> maxPQ;
    private MinPQ<Key> minPQ;
    private Comparator<Key> comparator;
    
    public DynamicMedianFinding() {
        maxPQ = new MaxPQ<Key>();
        minPQ = new MinPQ<Key>(); 
    }
    
    public DynamicMedianFinding(Key[] a, Comparator<Key> comparator) {
        maxPQ = new MaxPQ<Key>();
        minPQ = new MinPQ<Key>(); 
        this.comparator = comparator;
        for(Key k: a) {
            this.insert(k);
        }
    }
    
    public Key median() {
        if(median == null || N <= 0)
            throw new NoSuchElementException("no median");
//        throw new UnsupportedOperationException();
        return median;
    }
    
    public void insert(Key k) {
        if(median == null || N <= 0) {
            median = k;
            N++;
            return;
        }
        /**
         * 从1开始，偶数插左，奇数插右
         * 比median小的放maxPQ中--左
         * 比median大的放minPQ中--右
         */
        if (N % 2 == 0) {
            if(minPQ.isEmpty())
                maxPQ.insert(k);
            else {
                Key tmp = median3(median, minPQ.min(), k);
                if(tmp == median)
                    maxPQ.insert(k);
                else if(tmp == k) {
                    maxPQ.insert(median);
                    median = k;
                } else if(tmp == minPQ.min()){
                    maxPQ.insert(median);
                    median = minPQ.delMin();
                    minPQ.insert(k);
                }
            }
        } else {
            if(maxPQ.isEmpty())
                minPQ.insert(k);
            else {
                Key tmp = median3(median, maxPQ.max(), k);
                if(tmp == median)
                    minPQ.insert(k);
                else if(tmp == k) {
                    minPQ.insert(median);
                    median = k;
                } else if(tmp == minPQ.min()){
                    minPQ.insert(median);
                    median = maxPQ.delMax();
                    maxPQ.insert(k);
                }
            }
        }
        N++;
    }
    
    public Key delete() {
        if(median == null || N <= 0)
            throw new NoSuchElementException("no median");
        /**
         * 到1为止，奇数取左，偶数取右
         */
        Key tmp = median;
        if (N == 1) 
            median = null;
        else if(N % 2 == 0)
            median = minPQ.delMin();
        else
            median = maxPQ.delMax();
        N--;
        return tmp;
    }
    
    private Key median3(Key i, Key j, Key k) {
        return (less(i, j) ?
               (less(j, k) ? j : less(i, k) ? k : i) :
               (less(k, j) ? j : less(k, i) ? k : i));
    } 
    
    @SuppressWarnings("unchecked")
    private boolean less(Key i, Key j) {
        if (comparator == null) {
            if(i == null)
                return true;
            if(j == null)
                return false;
            return ((Comparable<Key>) i).compareTo(j) < 0;
        }
        else {
            return comparator.compare(i, j) < 0;
        }
    }
    
    public static void main(String[] args) {
        Integer[] a = {1,2,3,4,5,6};
        DynamicMedianFinding<Integer> dmf = new DynamicMedianFinding<Integer>(a, null);
        StdOut.println(dmf.median());
        StdOut.println("-"+dmf.delete());
        StdOut.println(dmf.median());
        StdOut.println("-"+dmf.delete());
        StdOut.println(dmf.median());
        StdOut.println("-"+dmf.delete());
        StdOut.println(dmf.median());
        StdOut.println("-"+dmf.delete());
        StdOut.println(dmf.median());
        StdOut.println("-"+dmf.delete());
        StdOut.println(dmf.median());
        StdOut.println("-"+dmf.delete());
        StdOut.println("-"+dmf.delete());
    }

}
