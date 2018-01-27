package class0404;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import class0401.StdDraw;
import class0403.CaseGenerator;
import rlgs4.Queue;
import rlgs4.Stack;
import stdlib.StdOut;

public class DrawSPT {

    private double[] distTo;                // length of path to v
    private DirectedEdge[] edgeTo;          // last edge on path to v
    private boolean[] onQ;                  // Is this vertex on the queue?
    private Queue<Integer> queue;           // vertices being relaxed/ has relaxed
    private int cost;                       // number of calls to relax()
    private Iterable<DirectedEdge> cycle;   // negative cycle in edgeTo[]?
    private int levelA;                     // 上层数量
    private int levelB;                     // 下层数量
//    private Queue<DirectedEdge> replace;    // 被替换边
    
    EdgeWeightedDigraph G;
    
    public DrawSPT(EdgeWeightedDigraph G, int s) {
        this.G = G;
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        onQ = new boolean[G.V()];
        queue = new Queue<Integer>();
//        replace = new Queue<DirectedEdge>();
        
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        
        distTo[s] = 0.0;
        queue.enqueue(s);
        levelB = 1;
        onQ[s] = true;
        
        // set tigger
        StdDraw.setOutsideHandle(new Tigger());
        
        int count = 0;
        while (!queue.isEmpty() && !this.hasNegativeCycle()) {
            
            if (levelA == 0) {
                StdOut.println(++count + "  " + levelB);
                levelA = levelB;
                
                show();
                
                synchronized(G){
                    try {
                        G.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                resetQueueColor();
            }
            
            int v = queue.dequeue();
            levelA--;
            levelB--;
            
            onQ[v] = false;
            relax(G, v);
        }
        show();
    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
//                if (edgeTo[w] != null)
//                    replace.enqueue(edgeTo[w]);
                edgeTo[w] = e;
                
                if (!onQ[w]) {
                    queue.enqueue(w);
                    onQ[w] = true;
                    levelB ++;
                }
            }
            
            if (cost++ % G.V() == 0)
                findNegativeCycle();
        }
    }
    
    public double distTo(int v) {
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        if (!hasPathTo(v))
            return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        // 向根遍历
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);
        return path;
    }

    private void findNegativeCycle() {
        int V = edgeTo.length;
        EdgeWeightedDigraph spt;
        spt = new EdgeWeightedDigraph(V);
        for (int v = 0; v < V; v++)
            if (edgeTo[v] != null)
                spt.addEdge(edgeTo[v]);
        EdgeWeightedCycleFinder cf;
        cf = new EdgeWeightedCycleFinder(spt);
        cycle = cf.cycle();
    }

    public boolean hasNegativeCycle() {
        return cycle != null;
    }
    
    public Iterable<DirectedEdge> negativeCycle() {
        return cycle;
    }
    
    private void show() {
//        StdDraw.setPenColor(StdDraw.GRAY);
//        for (DirectedEdge e: replace)
//            e.show();
        
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int v = 0; v < edgeTo.length; v++) 
            if (edgeTo[v] != null) 
                edgeTo[v].show();
        
        Queue<DirectedEdge> edges = new Queue<DirectedEdge>();
        for (Integer v: queue) 
            if (edgeTo[v] != null)
                edges.enqueue(edgeTo[v]);
        
        StdDraw.setPenColor(StdDraw.RED);
        for (DirectedEdge e: edges)
            e.show();
    }
    
    private void resetQueueColor() {
        StdDraw.setPenColor(StdDraw.BLACK);
        Queue<DirectedEdge> edges = new Queue<DirectedEdge>();
        for (Integer v: queue) 
            if (edgeTo[v] != null)
                edges.enqueue(edgeTo[v]);
        for (DirectedEdge e: edges)
            e.show();
    }
    
    class Tigger implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            synchronized(G){
                G.notify();
            }
        }
        
    }
    
    public static void main(String[] args) {
        double d = 28;
        int v = 250;
        
        EdgeWeightedDigraph ewd = CaseGenerator.euclideanInDigraph(v, d);
        StdDraw.setScale(0, v);
        
        // draw weighted graph
        StdDraw.setPenRadius(.003);
        ewd.show();
        
        StdDraw.setPenRadius(.005);
        @SuppressWarnings("unused")
        DrawSPT sp = new DrawSPT(ewd, 0);
    }
    
}
