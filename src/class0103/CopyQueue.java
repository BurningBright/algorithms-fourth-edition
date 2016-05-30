package class0103;

/*************************************************************************
 *  Compilation:  javac Queue.java
 *  Execution:    java Queue < input.txt
 *  Data files:   http://algs4.cs.princeton.edu/13stacks/tobe.txt  
 *
 *  A generic queue, implemented using a linked list.
 *
 *  % java Queue < tobe.txt 
 *  to be or not to be (2 left on queue)
 *
 *************************************************************************/

import rlgs4.Queue;
import stdlib.StdIn;
import stdlib.StdOut;

public class CopyQueue<Item> extends Queue<Item> implements Cloneable{
    
    /**
     * Initializes an empty queue.
     */
    public CopyQueue() {
        super();
    }
    
    public CopyQueue(CopyQueue<Item> q) {
//        for (Node<Item> x = q.; x != null; x = x.next)
//            this.enqueue(x.item);
        for (int i=0; i<q.size(); i++) {
            Item n = q.dequeue();
            this.enqueue(n);
            q.enqueue(n);
        }
    }
    
/*
    @SuppressWarnings("unchecked")
    private CopyQueue<Item> superClone() {
        try {
            return (CopyQueue<Item>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    public CopyQueue<Item> clone() {
        CopyQueue<Item> qt = superClone();
        
        // Put clone into "virgin" state
        qt.first = qt.last = null;
        qt.N = 0;
        
        // Initialize clone with our elements
        for (Node<Item> x = first; x != null; x = x.next)
            qt.enqueue(x.item);
        
        return qt;
    }
*/
    
    /**
     * Unit tests the <tt>Queue</tt> data type.
     */
    public static void main(String[] args) {
        CopyQueue<String> q = new CopyQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) q.enqueue(item);
            else if (!q.isEmpty()) StdOut.print(q.dequeue() + " ");
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }
}
