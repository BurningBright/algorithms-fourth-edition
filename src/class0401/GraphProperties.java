package class0401;

import rlgs4.Queue;
import rlgs4.Stack;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.1.16/18
 *      图的属性 - 偏心率
 *      图的最小循环探索 - 记录图的具体最短路径
 * @author Leon
 * @date 2017-11-06 13:40:00
 */
public class GraphProperties {
    
    private int ecc[];          // eccentricity
    private boolean marked[];   // is reached?
    private int min;
    private int max;
    private boolean initFlag;
    private Graph G;
    private Iterable<Integer> component;
    
    GraphProperties(Graph G) {
        // constructor (exception if G not connected)
        if(new CC(G).count() !=1) throw new IllegalArgumentException("not connected graph");
        this.G = G;
        ecc = new int[G.V()];
        for(int i=0; i<G.V(); i++) {
            marked = new boolean[G.V()];
            ecc[i] = bfs(i);
        }
    }
    
    // pointed component
    GraphProperties(Graph G, int c) {
        CC cc = new CC(G);
        if(c >= cc.count()) throw new IllegalArgumentException("no such component");
        this.G = G;
        ecc = new int[G.V()];
        
        // find component
        int M = cc.count();
        @SuppressWarnings("unchecked")
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[M];
        for (int i = 0; i < M; i++) 
            components[i] = new Queue<Integer>();
        for (int v = 0; v < G.V(); v++) {
            components[cc.id(v)].enqueue(v);
        }
        component = components[c];
        // loop
        for (Integer i: components[c]) {
            marked = new boolean[G.V()];
            ecc[i] = bfs(i);
        }
    }
    
    private int bfs(int s) {
        Queue<Integer> queueA = new Queue<Integer>();
        Queue<Integer> queueB = new Queue<Integer>();
        
        queueA.enqueue(s);
        marked[s] = true;
        int count = 0;
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
                if(!queueB.isEmpty())
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
                if(!queueA.isEmpty())
                    count++;
            }
        }
        if(!initFlag && count > 0) {
            min = max = count;
            initFlag = true;
        }
        
        if(count == 0)
            return count;
        
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
//                StdOut.print(i + " ");
            }
        }
//        StdOut.println();
        return c;
    }
    
    private boolean searched[];   // is reached?
    private Stack<Integer> cycle;
    private Stack<Integer> current;
    class Node implements Comparable<Node>{
        Integer v;
        Node p;
        Node(Integer v, Node p) {
            this.v = v;
            this.p = p;
        }
        @Override
        public int compareTo(Node o) {
            return v - o.v;
        }
    }
    
    public int girth() {
        int min = -1;
        cycle = new Stack<Integer>();
        // 元件迭代
        if(component != null) {
            for(Integer i: component) {
                int vmin = girth(i);
                
                if((vmin > 0 && min <0) || 
                        (vmin > 0 && min > vmin))
                    min = vmin;
            }
        } else {
            // 全文迭代
            for (int i=0; i<G.V(); i++) {
                int vmin = girth(i);
                
                if((vmin > 0 && min <0) || 
                        (vmin > 0 && min > vmin))
                    min = vmin;
            }
        }
        
        if(min > 0) {
            for(Integer i: cycle) {
                StdOut.print(i + "  ");
            }
            StdOut.println();
        }
        return min;
    }
    
    private int girth(int s) {
        for (Integer j: G.adj(s)) {
            searched = new boolean[G.V()];
            current = new Stack<Integer>();
            Node nc = bfs(j, s);
            // 未找到路径，跳过
            if(nc == null)
                continue;
            while(nc != null) {
                current.push(nc.v);
                nc = nc.p;
            }
            
            if ((cycle.isEmpty() && !current.isEmpty())
                    || (cycle.size() > current.size())) {
                cycle = current;
            }
        }
        /*
        if(!cycle.isEmpty()) {
            StdOut.print(s + "  ");
            for(Integer i: cycle) {
                StdOut.print(i + "  ");
            }
            StdOut.println();
        } else
            StdOut.println("acycle graph");
        */
        return !cycle.isEmpty()? cycle.size(): -1;
    }
    
    private Node bfs(int s, int p) {
        Queue<Node> queueA = new Queue<Node>();
        Queue<Node> queueB = new Queue<Node>();
        
        Node ns = new Node(s, null);
        queueA.enqueue(ns);
        
        searched[s] = true;
        int count = 1;
        while (!queueA.isEmpty() || !queueB.isEmpty()) {
            if(queueB.isEmpty()) {
                while(!queueA.isEmpty()) {
                    Node np = queueA.dequeue();
                    for(Integer i: G.adj(np.v)) {
                        
                        if(count == 1 && i == p)
                            continue;
                        else if(i == p) 
                            return new Node(i, np);
                        
                        if(!searched[i]) {
                            searched[i] = true;
                            queueB.enqueue(new Node(i, np));
                        }
                    }
                }
                count++;
            }
            
            if(queueA.isEmpty()) {
                while(!queueB.isEmpty()) {
                    Node np = queueB.dequeue();
                    for(Integer i: G.adj(np.v)) {
                        if(i == p) 
                            return new Node(i, np);
                        
                        if(!searched[i]) {
                            searched[i] = true;
                            queueA.enqueue(new Node(i, np));
                        }
                    }
                }
                count++;
            }
        }
        return null;
    }
    
    public static void main(String[] args) {
//        Graph g = new Graph(new In("resource/4.1/tinyG4.txt"));
        Graph g = new Graph(new In("resource/4.1/tinyG4.txt"));
        GraphProperties gp = new GraphProperties(g);
        StdOut.println(gp.diameter());
        StdOut.println(gp.radius());
        StdOut.println(gp.center());
        StdOut.println(gp.girth());
    }

}
