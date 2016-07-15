package class0105;

import java.util.Arrays;

import stdlib.StdOut;

import class0103.CircularQueue;

/**
 * links every site on the paths from 
 * p and q to the roots of their trees to the
 * root of the new tree
 * 
 * @author soft01
 *
 */
public class QickUnionCompressionUF {
    private int[] id;
    private int count;

    public QickUnionCompressionUF(int N) {
        this.id = new int[N];
        this.count = N;
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public void union(int p, int q) {
        CircularQueue<Integer> circP = find(p);
        CircularQueue<Integer> circQ = find(q);
        
        StdOut.println(p + "  " + q + "  ");
        
        if (circP.tail().equals(circQ.tail())) {
            return;
        }
        // id[circP.tail()] = circQ.tail();
        count--;
        
        // put path's node to it's root
//        for (int i = 0; i < circP.size(); i++) {
//            id[circP.dequeue()] = circQ.tail();
//        }
        for (Integer i : circP)
            id[i] = circQ.tail();
        StdOut.println(Arrays.toString(id));
    }

    public CircularQueue<Integer> find(int p) {
        /* 路径放入队列，队尾为root */
        CircularQueue<Integer> cq = new CircularQueue<Integer>();
        cq.enqueue(p);
        while (p != id[p]) {
            // find path's next node
            p = id[p];
            cq.enqueue(p);
        }
        // it's p node's root
        return cq;
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) {
        int[] a = { 4, 3, 3, 8, 6, 5, 9, 4, 2, 1, 5, 0, 7, 2, 6, 1, 1, 0, 6, 7 };
        QickUnionCompressionUF quc = new QickUnionCompressionUF(10);
        for (int i = 0; i < a.length; i += 2) {
            quc.union(a[i], a[i + 1]);
        }
        StdOut.println(quc.getCount());
    }

}
