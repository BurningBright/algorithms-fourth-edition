package class0501;

import java.util.Arrays;

import rlgs4.Queue;
import stdlib.StdOut;

/**
 * @Description 5.1.11
 *          用队列来排序 in MSD
 * @author Leon
 * @date 2018-02-26 18:50:00
 */
public class QueueSort {
    
    private static int R = 256;
    
    public static void sort(Alphabet alp, String[] a) {
        // data prepare
        Queue<String> q = new Queue<String>();
        for (int i=0; i<a.length; i++) 
            q.enqueue(a[i]);
        // sort
        q = sort(q, 0);
        
        // copy back
        int cnt = 0;
        for (String s: q)
            a[cnt++] = s;
    }
    
    @SuppressWarnings("unchecked")
    private static Queue<String> sort(Queue<String> q, int d) {
        // basic case
        boolean flag = false;
        for (String s: q)
            if (s.length()-1 >= d)
                flag = true;
        if (!flag)
            return q;
        
        // 散列
        Queue<String>[] queue = (Queue<String>[]) new Queue[R+1];
        for (int i=0; i<R+1; i++) 
            queue[i] = new Queue<String>();
        
        for (String s: q)
            if (s.length()-1 >= d) 
                queue[s.charAt(d)+1].enqueue(s);
            else
                queue[0].enqueue(s);
        
        // 排序子块
        for (int i=0; i<R+1; i++)
            queue[i] = sort(queue[i], d+1);
        
        // 整理区块
        Queue<String> aux = new Queue<String>();
        for (int i=0; i<R+1; i++)
            for (String s: queue[i])
                aux.enqueue(s);
        
        return aux;
    }
    
    public static void main(String[] args) {
        String[] src = "she sells sea shells by the sea shore".split(" ");
        sort(null, src);
        StdOut.println(Arrays.deepToString(src));
    }

}
