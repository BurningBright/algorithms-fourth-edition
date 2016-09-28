package solution02.c5;

/******************************************************************************
 *  Compilation:  javac LPT.java
 *  Execution:    java LPT m < jobs.txt
 *  Dependencies: Processor.java Job.java
 *  
 *  Find an approximate solution to the load balancing
 *  problem using the LPT (longest processing time first) rule.
 *
 ******************************************************************************/

import java.util.Arrays;

import javax.annotation.processing.Processor;

import rlgs4.MinPQ;
import stdlib.StdIn;
import stdlib.StdOut;

public class LPT {

    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);   // number of machines
        /*
        int n = StdIn.readInt();
        Job[] jobs = new Job[n];
        for (int i = 0; i < n; i++) {
            String name = StdIn.readString();
            double time = StdIn.readDouble();
            jobs[i] = new Job(name, time);
        }

        // sort jobs in ascending order of processing time 
        Arrays.sort(jobs);

        // generate m empty processors and put on priority queue
        MinPQ<Processor> pq = new MinPQ<Processor>(m);
        for (int i = 0; i < m; i++)
            pq.insert(new Processor());
        
        
        // assign each job (in decreasing order of time) to processor that is least busy
        for (int j = n-1; j >= 0; j--) {
            Processor min = pq.delMin();
            min.add(jobs[j]);
            pq.insert(min);
        }
        
        // print out contents of each processor
        while (!pq.isEmpty())
            StdOut.println(pq.delMin());
        */
    }

}
