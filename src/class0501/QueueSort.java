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
        sort(a, 0);
    }
    
    @SuppressWarnings("unchecked")
    private static void sort(String[] a, int d) {
        
        Queue<String>[] queue = (Queue<String>[]) new Queue[R];
        Queue<String>[] aux = (Queue<String>[]) new Queue[R];
        
        for (int i=0; i<R; i++) 
            queue[i] = new Queue<String>();
        
        for (int i=0; i<a.length; i++)
            if (a[i].length()-1 >= d)
                queue[a[i].charAt(d)].enqueue(a[i]);
        
        for (int i=0; i<R; i++)
            aux[i] = sort(queue[i], d+1);
        
        // copy back
        int cnt = 0;
        for (int i=0; i<R; i++)
            if(aux[i] != null)
                for (String s: aux[i])
                    a[cnt++] = s;
        
    }
    
    @SuppressWarnings("unchecked")
    private static Queue<String> sort(Queue<String> q, int d) {
        if (q == null || q.size() == 0 ) return null;
        Queue<String>[] queue = (Queue<String>[]) new Queue[R];
        for (int i=0; i<R; i++) 
            queue[i] = new Queue<String>();
        
        for (String s: q)
            if (s.length()-1 >= d) {
                queue[s.charAt(d)].enqueue(s);
            }
        
        Queue<String> aux = new Queue<String>();
        for (int i=0; i<R; i++)
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
