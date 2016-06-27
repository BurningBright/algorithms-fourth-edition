package class0104;

import class0103.FixedCapacityStack;
import rlgs4.Stopwatch;

/**
 * @Description 1.4.37 
 *      测试拆装箱对性能的影响
 * @author Leon
 * @date 2016-06-27 16:54:34
 */
public class FixedCapacityStackOfInts {
    
    private int[] a; // stack entries
    private int N; // size

    public FixedCapacityStackOfInts(int cap) {
        a = new int[cap];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void push(Integer item) {
        a[N++] = item;
    }

    public Integer pop() {
        return a[--N];
    }

    public boolean isFull() {
        return N==a.length;
    }
    
    public static void main(String[] args) {
        for(int i=100000; i< 100000000; i+=i) {
            
            Integer[] src = new Integer[i];
            for(int j =0; j<i; j++)
                src[j] = j;
            
            
            
            Stopwatch timer = new Stopwatch();
            FixedCapacityStack<Integer> fcs = new FixedCapacityStack<Integer>(i);
            for(int j=0; j<i; j++)
                fcs.push(src[j]);
            for(int j=0; j<i; j++)
                fcs.pop();
            System.out.print(i + " generic " + timer.elapsedTime() + " | ");
            
            
            
            Stopwatch timer_int = new Stopwatch();
            FixedCapacityStackOfInts fcsi = new FixedCapacityStackOfInts(i);
            for(int j=0; j<i; j++)
                fcsi.push(src[j]);
            for(int j=0; j<i; j++)
                fcsi.pop();
            System.out.println("no generic " + timer_int.elapsedTime());
            
        }
    }

}
