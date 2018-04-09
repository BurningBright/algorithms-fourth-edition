package class0504;

import java.util.HashMap;
import java.util.Map;

import class0402.Digraph;
import class0402.DirectedDFS;
import rlgs4.Bag;
import rlgs4.Stack;
import stdlib.StdOut;

/**
 * @Description 5.4.19
 *              集合字符构建自动机
 *              方扩号及其内[xxx]，作为一个整体字符
 *              匹配时跳过其余字符，到右方括号
 * @author Leon
 * @date 2018-04-09 13:15:00
 */
public class NFASet implements NFA {

    private char[] re;  // match transitions
    private Digraph G;  // epsilon transitions
    private int M;      // number of states
    // 索引 - 偏离
    private Map<Integer, Integer> sign;

    public NFASet(String regexp) { // Create the NFA for the given regular
                                // expression.
        Stack<Integer> ops = new Stack<Integer>();
        sign = new HashMap<Integer, Integer>();
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
            if (re[i] == '(' || re[i] == '*' || re[i] == ']' || re[i] == ')')
                G.addEdge(i, i + 1);
            
            if (re[i] == '[') {
                int j;
                // 左括号与字符集的连结 - 前连接【红链接】
                for (j=i+1; re[j] != ']'; j++)
                    G.addEdge(i, j);
                // 字符集与右括号的跳跃 - offset
                for (int k=i+1; re[k] != ']'; k++)
                    sign.put(k, j-k);
                // 预置遍历索引
                i = j-1;
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
                    if (re[v] == txt.charAt(i) || re[v] == '.')
                        // 遇到匹配字符集时，跳过其余字符，到右方括号索引
                        if (sign.keySet().contains(v))
                            match.add(v + sign.get(v));
                        else match.add(v + 1);
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
        NFA a = new NFASet("A[BCD]E");
        StdOut.println(a.recognizes("AE"));
        StdOut.println(a.recognizes("AKE"));
        StdOut.println(a.recognizes("ACE"));
    }

}
