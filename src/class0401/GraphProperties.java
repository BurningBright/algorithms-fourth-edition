package class0401;

import rlgs4.CC;
import rlgs4.Graph;
import rlgs4.Queue;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.1.16 
 *      图的属性 - 偏心率
 * @author Leon
 * @date 2017-11-06 13:40:00
 */
public class GraphProperties {
    
    private int ecc[];          // eccentricity
    private boolean marked[];   // is reached?
    private int min;
    private int max;
    private Graph G;
    
    GraphProperties(Graph G) {
        // constructor (exception if G not connected)
        if(new CC(G).count() !=1) throw new IllegalArgumentException("not connected graph");
        this.G = G;
        ecc = new int[G.V()];
        marked = new boolean[G.V()];
        for(int i=0; i<G.V(); i++) {
            ecc[i] = bfs(i);
        }
    }
    
    private int bfs(int s) {
        Queue<Integer> queueA = new Queue<Integer>();
        Queue<Integer> queueB = new Queue<Integer>();
        
        queueA.enqueue(s);
        marked[s] = true;
        int count = 1;
        while (!queueA.isEmpty() || !queueB.isEmpty()) {
            if(queueB.isEmpty()) {
                while(!queueA.isEmpty()) {
                    int v = queueA.dequeue();
                    for(Integer i: G.adj(v)) {
                        if(!marked[i]) {
                            marked[i] = true;
                            queueB.enqueue(i);
                        }
                    }
                }
                count++;
            }
            
            if(queueA.isEmpty()) {
                while(!queueB.isEmpty()) {
                    int v = queueB.dequeue();
                    for(Integer i: G.adj(v)) {
                        if(!marked[i]) {
                            marked[i] = true;
                            queueA.enqueue(i);
                        }
                    }
                }
                count++;
            }
        }
        if(s == 0) {
            min = max = count;
        }
        min = count < min? count: min;
        max = count > max? count: max;
        return count;
    }
    
    public int eccentricity(int v) {
        // eccentricity of v - the furthest vertex from v
        if(v < 0 || v>=G.V()) throw new IllegalArgumentException("out of boundary");
        return ecc[v];
    }
    public int diameter() {
        // diameter of G - maximum eccentricity
        return max;
    }
    public int radius() {
        // radius of G - smallest eccentricity
        return min;
    }
    public int center() {
        // a center of G - eccentricity is the radius
        int c = -1;
        for(int i=0; i<ecc.length; i++) {
            if(ecc[i] == min) {
                c = i;
                StdOut.print(i + " ");
            }
        }
        StdOut.println();
        return c;
    }
    
    private boolean searched[];   // is reached?
    private Queue<Integer> cycle;
    private Queue<Integer> current;
    
    public int girth() {
        
        for (Integer i: G.adj(0)) {
            searched = new boolean[G.V()];
            current = new Queue<Integer>();
            bfs(i, 0);
            
            if(cycle == null || cycle.size() > current.size())
                cycle = current;
//            StdOut.println(i + "  " + bfs(i, 0));
        }
        
        for(Integer i: cycle) {
            StdOut.print(i + "  ");
        }
        StdOut.println();
        
        return 0;
    }
    
    private int bfs(int s, int p) {
        Queue<Integer> queueA = new Queue<Integer>();
        Queue<Integer> queueB = new Queue<Integer>();
        
        
        queueA.enqueue(s);
//        current.enqueue(s);
        
        searched[s] = true;
        int count = 1;
        while (!queueA.isEmpty() || !queueB.isEmpty()) {
            if(queueB.isEmpty()) {
                while(!queueA.isEmpty()) {
                    int v = queueA.dequeue();
                    for(Integer i: G.adj(v)) {
                        
                        if(count == 1 && i == p)
                            continue;
                        else if(i == p) 
                            return ++count;
                        
                        if(!searched[i]) {
                            searched[i] = true;
                            queueB.enqueue(i);
//                            current.enqueue(i);
                        }
                    }
                }
                count++;
            }
            
            if(queueA.isEmpty()) {
                while(!queueB.isEmpty()) {
                    int v = queueB.dequeue();
                    for(Integer i: G.adj(v)) {
                        if(i == p) 
                            return ++count;
                        
                        if(!searched[i]) {
                            searched[i] = true;
                            queueA.enqueue(i);
//                            current.enqueue(i);
                        }
                    }
                }
                count++;
            }
        }
        return count;
    }
    
    public static void main(String[] args) {
        Graph g = new Graph(new In("resource/4.1/tinyG3.txt"));
        GraphProperties gp = new GraphProperties(g);
//        StdOut.println(gp.diameter());
//        StdOut.println(gp.radius());
//        StdOut.println(gp.center());
        StdOut.println(gp.girth());
    }

}
