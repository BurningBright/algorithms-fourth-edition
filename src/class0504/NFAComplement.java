package class0504;

import class0402.Digraph;
import class0402.DirectedDFS;
import rlgs4.Bag;
import rlgs4.SET;
import rlgs4.Stack;
import stdlib.StdOut;

/**
 * @Description 5.4.21
 *              字符补集构建自动机
 *              方扩号内[^x-x][^xxx]的补集，作为一个整体字符 ps.一个范围、一个集合
 *              不匹配时跳过其余字符，到右方括号。
 *              跳跃点选在集合的最后一个字符
 * @author Leon
 * @date 2018-04-09 13:15:00
 */
public class NFAComplement implements NFA{

    private char[] re;  // match transitions
    private Digraph G;  // epsilon transitions
    private int M;      // number of states
    
    private boolean complement;
    
    private SET<Character> set;
    // char range
    private int L;
    private int R;
    
    // offset 
    private int jump;

    public NFAComplement(String regexp) { // Create the NFA for the given regular
                                // expression.
        Stack<Integer> ops = new Stack<Integer>();
        re = regexp.toCharArray();
        M = re.length;
        G = new Digraph(M + 1);
        
        set = new SET<Character>();
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
                
                if (re[i+1] == '^')
                    complement = true;
                
                int j;  // 判断集合起始点
                for (j= complement? i+1: i; re[j] != ']'; j++) 
                    if (re[j] == '-') {
                        L = re[j - 1];
                        R = re[j + 1];
                    } else 
                        set.add(re[j]);
                
                jump = j;
                // 右边界与左方括号的连结 - 前连接【红链接】
                G.addEdge(i, jump);
                
                i += jump;
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
                            || (v == jump && 
                                !complement && // 非补集 & 集合包含字符
                                ((txt.charAt(i) >= L && txt.charAt(i) <= R) 
                                    || set.contains(txt.charAt(i)))
                            )
                            || (v == jump && 
                                complement && // 补集 & 集合不包含字符
                                (!(txt.charAt(i) >= L && txt.charAt(i) <= R) 
                                    && !set.contains(txt.charAt(i)))
                            ))
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
//        NFA a = new NFAComplement("A[^B-D XYZ]H");
        NFA a = new NFAComplement("A[B-D XYZ]H");
        StdOut.println(a.recognizes("AH"));
        StdOut.println(a.recognizes("ACH"));
        StdOut.println(a.recognizes("AYH"));
        StdOut.println(a.recognizes("AKH"));
    }

}
