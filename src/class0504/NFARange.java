package class0504;

import class0402.Digraph;
import class0402.DirectedDFS;
import rlgs4.Bag;
import rlgs4.Stack;
import stdlib.StdOut;

/**
 * @Description 5.4.20
 *              字符范围构建自动机
 *              方扩号及其内[x-x]，作为一个整体字符
 *              匹配时跳过其余字符，到右方括号
 * @author Leon
 * @date 2018-04-09 14:15:00
 */
public class NFARange implements NFA {

    private char[] re;  // match transitions
    private Digraph G;  // epsilon transitions
    private int M;      // number of states
    
    // char range
    private int L;
    private int R;
    
    // offset 
    private int jump;

    public NFARange(String regexp) { // Create the NFA for the given regular
                                // expression.
        Stack<Integer> ops = new Stack<Integer>();
        re = regexp.toCharArray();
        M = re.length;
        G = new Digraph(M + 1);
        
        L = R = -1;
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
            if (re[i] == '(' || re[i] == '*' || re[i] == ']' || re[i] == ')')
                G.addEdge(i, i + 1);
            
            if (re[i] == '[') {
                
                L = re[i + 1];
                R = re[i + 3];
                jump = i + 3;
                // 右边界与左方括号的连结 - 前连接【红链接】
                G.addEdge(i, jump);
                
                i += 3;
            }
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
                    // 在跳跃点做范围判断，在范围内-字符匹配【黑链接】
                    if (re[v] == txt.charAt(i) || re[v] == '.'
                        || (v == jump && txt.charAt(i) >= L && txt.charAt(i) <= R))
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
        NFA a = new NFARange("A[B-G]H");
        StdOut.println(a.recognizes("AH"));
        StdOut.println(a.recognizes("AKH"));
        StdOut.println(a.recognizes("ACH"));
    }

}
