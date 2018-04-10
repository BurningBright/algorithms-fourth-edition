package class0504;

import class0402.Digraph;
import class0402.DirectedDFS;
import class0402.DirectedDFSPath;
import rlgs4.Bag;
import rlgs4.Queue;
import rlgs4.SET;
import rlgs4.Stack;
import stdlib.StdOut;

/**
 * @Description 5.4.22
 *              汇总无限状态自动机
 *              增加输出识别路径的方法
 * @author Leon
 * @date 2018-04-09 13:15:00
 */
public class NFAProof implements NFA {

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

    public NFAProof(String regexp) { // Create the NFA for the given regular
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
            else if (re[i] == ')') 
                lp = multiOr(ops, i);
            
            if (i < M - 1 && re[i + 1] == '*') {
                // lookahead
                G.addEdge(lp, i + 1);
                G.addEdge(i + 1, lp);
            }
            if (i < M - 1 && re[i + 1] == '+') {
                // lookahead
                G.addEdge(i + 1, lp);
            }
            if (re[i] == '(' || re[i] == '*' || re[i] == '+' || re[i] == ']' || re[i] == ')')
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
                
                jump = j - 1;
                // 右边界与左方括号的连结 - 前连接【红链接】
                G.addEdge(i, jump);
                
                i = jump;
            }
            
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
    
    public String path(String txt) {
        if (!recognizes(txt)) return null;
        StringBuilder sb = new StringBuilder();
        
        
        Queue<Integer> path = new Queue<Integer>();
        Bag<Integer> pc = new Bag<Integer>();
        DirectedDFSPath dfs = new DirectedDFSPath(G, 0);
        Stack<DirectedDFSPath> level = new Stack<DirectedDFSPath>();
        level.push(dfs);
        int preV = 0;
        
        for (int v = 0; v < G.V(); v++)
            if (dfs.marked(v))
                pc.add(v);
        for (int i = 0; i < txt.length(); i++) {
            Bag<Integer> match = new Bag<Integer>();
            
            DirectedDFSPath guide = level.pop();
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
                            )) {
                        match.add(v + 1);
                        Iterable<Integer> part = guide.pathTo(preV, v);
                        preV = v + 1;
                        for (Integer node: part)
                            path.enqueue(node);
                        path.enqueue(-1);
                    }
            pc = new Bag<Integer>();
            dfs = new DirectedDFSPath(G, match);
            for (int v = 0; v < G.V(); v++)
                if (dfs.marked(v))
                    pc.add(v);
            level.push(dfs);
        }
        
        int[] array = new int[path.size()];
        for (int i=0; i<array.length; i++)
            array[i] = path.dequeue();
        
        // remove the last sentry 状态机字符
        for (int i=0; i<array.length - 1; i++) {
            if (array[i] == -1){
                sb.append("=>");
                continue;
            }
            if (array[i+1] != -1)
                if (array[i]>9)
                    sb.append(re[array[i]]).append(" ->");
                else
                    sb.append(re[array[i]]).append("->");
            else
                if (array[i]>9)
                    sb.append(re[array[i]]).append(" ");
                else
                    sb.append(re[array[i]]);
        }
        sb.append("\r\n");
        
        // remove the last sentry 状态机索引
        for (int i=0; i<array.length - 1; i++) {
            if (array[i] == -1){
                sb.append("=>");
                continue;
            }
            if (array[i+1] != -1)
                sb.append(array[i]).append("->");
            else
                sb.append(array[i]);
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        NFAProof a = new NFAProof("A[BCDE-G](N|O|P)+T");
        
        StdOut.println(a.recognizes("ABPPT"));
        StdOut.println(a.recognizes("AHPPT"));
        StdOut.println(a.recognizes("ABMMT"));
        StdOut.println(a.recognizes("ABT"));
        
        NFAProof b = new NFAProof("(A|B|C)*");
        StdOut.println(b.path("ABCC"));
        StdOut.println(a.path("ADNOPPT"));
    }

}
