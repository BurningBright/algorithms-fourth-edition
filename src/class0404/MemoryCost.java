package class0404;

import com.javamex.classmexer.MemoryUtil;

import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.4.11
 *          有向权重图-内存模型
 *          56.00 + 40.00 V + 72.00 E bytes
 *  -XX:-UseCompressedOops -javaagent:lib/classmexer.jar
 * @author Leon
 * @date 2018-01-11 14:40:00
 */
public class MemoryCost {

    public static void main(String[] args) {
        DirectedEdge e = new DirectedEdge(0, 1, 1.0);
        StdOut.println("size of DirectedEdge = " + MemoryUtil.deepMemoryUsageOf(e) + " bytes");
        
        EdgeWeightedDigraph ewd = new EdgeWeightedDigraph(2);
        ewd.addEdge(e);
        StdOut.println("size of Digraph = " + MemoryUtil.deepMemoryUsageOf(ewd) + " bytes");
        
        EdgeWeightedDigraph ewd_tiny = new EdgeWeightedDigraph(new In("resource/4.4/tinyEWD.txt"));
        StdOut.println("size of Digraph tiny = " + MemoryUtil.deepMemoryUsageOf(ewd_tiny) + " bytes");
        
        String str = "java";
        StdOut.println("size of String = " + MemoryUtil.deepMemoryUsageOf(str) + " bytes");
    }

}
