package class0504;

import class0402.Digraph;
import class0402.DirectedDFS;
import rlgs4.Bag;
import rlgs4.Stack;
import stdlib.StdOut;

/**
 * @Description 5.4.17
 *              通配模式构建自动机
 *              在识别时已做过滤，看起来和原始型没什么区别
 * @author Leon
 * @date 2018-04-09 10:00:00
 */
public class NFAWildcards implements NFA{

    private char[] re;  // match transitions
    private Digraph G;  // epsilon transitions
    private int M;      // number of states

    public NFAWildcards(String regexp) { // Create the NFA for the given regular
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
                int or = ops.pop();
                if (re[or] == '|') {
                    lp = ops.pop();
                    G.addEdge(lp, or + 1);
                    G.addEdge(or, i);
                } else
                    lp = or;
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
        NFA a = new NFAWildcards("(A.*|B)");
        NFA b = new NFAOrigin("(A.*|B)");
        StdOut.println(a.recognizes("AAAB"));
        StdOut.println(b.recognizes("AAAB"));
    }

}
