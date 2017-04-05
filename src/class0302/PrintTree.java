package class0302;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description 3.2.0
 *      draw a tree
 * @author leon
 * @date 2017-03-20 23:06:13
 */
public class PrintTree<Key extends Comparable<Key>, Value> extends BSTbase<Key, Value>{
    
    private Queue<Node> que = new LinkedList<Node>();
    
    class NullObj extends Node{

        public NullObj(Key key, Value val, int N) {
            super(key, val, N);
        }
        
    }
    
    public void calcTree() {
        que.add(root);
        
        while(que.size() > 0)
            printTree();
    }
    
    public void printTree() {
        
        for(int i = que.size(); i > 0; i--) {
            Node tmp = que.poll();
            if(tmp.N > 0) {
                
                // 缩进
                for(int j=0; j<tmp.N; j++)
                    System.out.print(" ");
                
                // key
                System.out.print(tmp.key + " ");
                
                if(tmp.left != null) que.add(tmp.left);
                else que.add(new NullObj(null, null, 0));
                
                if(tmp.right != null) que.add(tmp.right);
                else que.add(new NullObj(null, null, 0));
                
            } else if(tmp.N == 0) {
                // null
                System.out.print("x ");
                que.add(new NullObj(null, null, -1));
                que.add(new NullObj(null, null, -1));
            } else {
                // blank
                System.out.print("- ");
            }
        }
        
        System.out.println();
    }
    
    public static void main(String[] args) {
        
        PrintTree<String, String> a2t = new PrintTree<String, String>();
        a2t.put("5", "v0");
        a2t.put("3", "v1");
        a2t.put("7", "v1");
        a2t.put("2", "v2");
        a2t.put("4", "v2");
        a2t.put("6", "v2");
        a2t.put("8", "v2");
        a2t.put("1", "v3");
        a2t.put("10", "v4");
        a2t.put("9", "v3");
        a2t.put("11", "v3");
        
        a2t.calcTree();
    }

}
