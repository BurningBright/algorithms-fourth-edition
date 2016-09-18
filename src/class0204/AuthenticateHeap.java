package class0204;

import java.util.Comparator;

import stdlib.StdOut;

/**
 * @Description 2.4.15
 *      对堆进行验证
 *      从开始到中间，如果可沉就不是堆
 * @author Leon
 * @date 2016-09-18 14:16:40
 */
public class AuthenticateHeap<Key> {
    
    private int N;
    private Key[] pq;
    private Comparator<Key> comparator;
    
    @SuppressWarnings("unchecked")
    public AuthenticateHeap(Key[] a) {
        N = a.length;
        pq = (Key[]) new Object[a.length + 1];
        for (int i = 0; i < N; i++)
            pq[i+1] = a[i];
    }
    
    public boolean authenticate() {
        for (int i = N/2; i >= 1; i--) {
            int k = i;
            while (k*2 <= N) {
                int j = k*2;
                if (j < N && less(j, j+1)) j++;
                // 可沉即不是堆
                if (less(i, j))
                    return false;
                // 向下走
                k=j;
            }
        }
        return true;
    }
    
    @SuppressWarnings("unchecked")
    private boolean less(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) < 0;
        }
    }
    
    public static void main(String[] args) {
        AuthenticateHeap<Integer> a = new AuthenticateHeap<Integer>(new Integer[]{7, 4, 5, 6, 3, 2, 1});
        StdOut.println(a.authenticate());
    }

}
