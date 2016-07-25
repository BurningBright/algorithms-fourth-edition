package class0201;

import class0103.DequeDLink;
import stdlib.StdRandom;

/**
 * @Description 2.1.14
 *      出队排序,
 *       二元素逆序，头交换后入队；
 *       二元素顺序，头入队；
 * @author Leon
 * @date 2016-07-25 10:50:51
 */
public class DequeueSort {
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void sort(DequeDLink<Integer> queue) {
        
        int size = queue.size();
        
        for(int i=0; i<size; i++) {
            if(isSorted(queue))
                return;
            for(int j=i; j<size-1; j++) {
                Integer previous = queue.popLeft();
                if(!less((Comparable)previous, (Comparable)queue.peek())) {
                    queue.pushRight(queue.popLeft());
                    queue.pushLeft(previous);
                    System.out.println(previous + " -");
                    show(queue);
                } else {
                    queue.pushRight(previous);
                    System.out.println(previous + " +");
                    show(queue);
                }
                
            }
            System.out.println("----------------------");
            // 获得最小元素组，重新整理队列
            for(int k=0; k<i+1; k++)
                queue.pushRight(queue.popLeft());
            show(queue);
            System.out.println("----------------------");
        }
    }
    
    static boolean less(Comparable<Object> v, Comparable<Object> w) {
        return v.compareTo(w) < 0;
    }
    
    private static void show(DequeDLink<? extends Comparable<?>> queue) {
        for(Comparable<?> t: queue) {
            System.out.print(t+" ");
        }
        System.out.println();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static boolean isSorted(DequeDLink<? extends Comparable<?>> queue) {
        Comparable previous = null;
        if(queue.size() > 0) 
            previous = queue.peek();
        else
            return false;
        
        for(Comparable t: queue) {
            if(less(t, previous)) {
                return false;
            } else {
                previous = t;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        DequeDLink<Integer> queue = new DequeDLink<Integer>();
        int N = 10;
        for(int i=0; i<N; i++) {
            queue.pushRight(StdRandom.uniform(N));
        }
        show(queue);
        sort(queue);
        System.out.println(isSorted(queue));
        
    }

}
