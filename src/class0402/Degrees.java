package class0402;

import rlgs4.Queue;

/**
 * @Description 4.2.7
 *          有向图的度 sink/source
 * @author Leon
 * @date 2017-12-08 13:42:00
 */
public class Degrees {
    
    Digraph G;
    
    public Degrees(Digraph G) {
        this.G = G;
    }

    public int indegree(int v) {
        return G.indegree(v);
    }

    public int outdegree(int v) {
        return G.outdegree(v);
    }

    public Iterable<Integer> sources() {
        Queue<Integer> src = new Queue<Integer>();
        for(int i=0; i<G.V(); i++) 
            if(indegree(i) == 0)
                src.enqueue(i);
        return src;
    }

    public Iterable<Integer> sinks() {
        Queue<Integer> src = new Queue<Integer>();
        for(int i=0; i<G.V(); i++) 
            if(outdegree(i) == 0)
                src.enqueue(i);
        return src;
    }

    public boolean isMap() {
        for(int i=0; i<G.V(); i++) {
            if(outdegree(i) != 1)
                return false;
        }
        return true;
    }

}
