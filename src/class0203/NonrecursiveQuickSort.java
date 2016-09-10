package class0203;

import java.util.Arrays;

import rlgs4.Stack;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.3.20
 *      不用递归的快速排序，用栈来弹压分割点
 * @author Leon
 * @date 2016-09-09 10:14:03
 */
public class NonrecursiveQuickSort {
    
    @SuppressWarnings("rawtypes")
    public static void sort(Comparable[] a, int lo, int hi) {
        Stack<Partition> stack = new Stack<Partition>();
        stack.push(new Partition(lo, hi));
        /**
         *  
         *  如何判断域被排好 ？
         *  没必要纠结于变量中，面向对象吧
         *  1.lo/hi 入栈
         *  2.弹栈 对范围分区，如果栈空则结束
         *  3.如果右部没排好则压栈
         *  4.如果左边没排好则压栈
         */
        while(!stack.isEmpty()) {
            Partition part = stack.pop();
            int p = CountExactCn.partition(a, part.lo, part.hi);
            // 先看右部，再看左部，当循环启动会先弹出左部做分割
            if(part.hi - p > 1)
                stack.push(new Partition(p+1, part.hi));
            if(p - part.lo > 1)
                stack.push(new Partition(part.lo, p-1));
        }
        
    }
    
    static class Partition {
        int lo;
        int hi;
        public Partition(int lo, int hi) {
            this.lo = lo;
            this.hi = hi;
        }
    }
    
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        Comparable[] a = new Comparable[50];
        for(int i=0; i<a.length; i++)
            a[i] = i;
        StdRandom.shuffle(a);
        sort(a, 0, a.length-1);
        StdOut.println(Arrays.toString(a));
    }

}
