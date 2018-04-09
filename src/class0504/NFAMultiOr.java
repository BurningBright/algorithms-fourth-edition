package class0504;

import class0402.Digraph;
import class0402.DirectedDFS;
import rlgs4.Bag;
import rlgs4.Stack;
import stdlib.StdOut;

/**
 * @Description 5.4.16
 *              多或模式构建自动机
 * @author Leon
 * @date 2018-04-09 10:00:00
 */
public class NFAMultiOr implements NFA{

    private char[] re;  // match transitions
    private Digraph G;  // epsilon transitions
    private int M;      // number of states

    public NFAMultiOr(String regexp) { // Create the NFA for the given regular
                                // expression.
        Stack<Integer> ops = new Stack<Integer>();
        re = regexp.toCharArray();
        M = re.length;
        G = new Digraph(M + 1);
        for (int i = 0; i < M; i++) {
            int lp = i;
            if (re[i] == '(' || re[i] == '|')
                ops.push(i);
            else if (re[i] == ')') {
                /*
                int or = ops.pop();
                if (re[or] == '|') {
                    lp = ops.pop();
                    G.addEdge(lp, or + 1);
                    G.addEdge(or, i);
                } else
                    lp = or;
                */
                lp = multiOr(ops, i);
            }
            if (i < M - 1 && re[i + 1] == '*') {
                // lookahead
                G.addEdge(lp, i + 1);
                G.addEdge(i + 1, lp);
            }
            if (re[i] == '(' || re[i] == '*' || re[i] == ')')
                G.addEdge(i, i + 1);
        }
    }
    
    /**
     * 构建多或图路径
     * @param ops   操作栈
     * @param index 右括号索引
     * @return      左括号索引
     */
    private int multiOr(Stack<Integer> ops, int index) {
        int or = ops.pop();
        if (re[or] == '|') {
            int lp = multiOr(ops, index);
            G.addEdge(lp, or + 1);
            G.addEdge(or, index);
            return lp;
        } else 
            return or;
    }
    
    public boolean recognizes(String txt) {
        // Does the NFA recognize txt?
        Bag<Integer> pc = new Bag<Integer>();
        DirectedDFS dfs = new DirectedDFS(G, 0);
        for (int v = 0; v < G.V(); v++)
            if (dfs.marked(v))
                pc.add(v);
        for (int i = 0; i < txt.length(); i++) {
            // Compute possible NFA states for txt[i+1].
            Bag<Integer> match = new Bag<Integer>();
            for (int v : pc)
                if (v < M)
                    if (re[v] == txt.charAt(i) || re[v] == '.')
                        match.add(v + 1);
            pc = new Bag<Integer>();
            dfs = new DirectedDFS(G, match);
            for (int v = 0; v < G.V(); v++)
                if (dfs.marked(v))
                    pc.add(v);
        }
        for (int v : pc)
            if (v == M)
                return true;
        return false;
    }
    
    public static void main(String[] args) {
//        "( . * A B ( ( C | D | E ) F ) * G )"
        NFA a = new NFAMultiOr("(A|B|C)*");
        NFA b = new NFAOrigin("(A|B|C)*");
        StdOut.println(a.recognizes("ABC"));
        StdOut.println(b.recognizes("ABC"));
    }

}
