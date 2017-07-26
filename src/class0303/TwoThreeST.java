package class0303;

/**
 * @Description 3.3.35
 *     自建结构23树
 * @author Leon
 * @date 2017-07-26 10:29:13
 */
public class TwoThreeST<Key extends Comparable<Key>, Value> {
    
    Node root;
    
    interface Node{
        // 大小
        int size();
        // 节点类型
        boolean isThree();
        // 是否传递[被合并]
        boolean isMerge();
        void setMerge(boolean m);
    }
    
    class NodeTwo implements Node {
        Key key;
        Value val;
        
        Node left, right;
        int N;
        
        boolean merge;
        
        public NodeTwo(Key key, Value val, int N, boolean merge) {
            this.key = key;
            this.val = val;
            this.N = N;
            this.merge = merge;
        }
        public String toString() {
            return N +"   " +key + ":" + val;
        }
        public int size() {return N;}
        public boolean isThree() {return false;}
        public boolean isMerge() {return merge;}
        public void setMerge(boolean merge) {this.merge = merge;}
    }
    
    class NodeThree implements Node {
        Key keyA;
        Value valA;
        Key keyB;
        Value valB;
        Node left, middle, right;
        int N;
        
        public NodeThree(Key keyA, Value valA, Key keyB, Value valB, int N) {
            this.keyA = keyA;
            this.valA = valA;
            this.keyB = keyB;
            this.valB = valB;
            this.N = N;
        }
        public String toString() {
            return N +"   " +keyA + ":" + valA + "|" + keyB + ":" + valB;
        }
        public int size() {return N;}
        public boolean isThree() {return true;}
        public boolean isMerge() {return false;}
        public void setMerge(boolean merge) {}
    }
    
    public int size() {
        return size(root);
    }

    protected int size(Node x) {
        if (x == null)
            return 0;
        else
            return x.size();
    }
    
    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.setMerge(false);
    }
    
    @SuppressWarnings("unchecked")
    private Node put(Node x, Key key, Value val) {
        if (x == null)
            return new NodeTwo(key, val, 1, true);
        
        if (!x.isThree()){
            NodeTwo xt = (NodeTwo)x;
            int cmp = key.compareTo(xt.key);
            if (cmp > 0) {
                Node tmp = put(xt.right, key, val);
                if (tmp.isMerge()){
                    x = mergeTwo(xt, (NodeTwo)tmp, false);
                } else
                    x = tmp;
            } else if (cmp < 0) {
                Node tmp = put(xt.left, key, val);
                if (tmp.isMerge()){
                    x = mergeTwo(xt, (NodeTwo)tmp, true);
                } else
                    x = tmp;
            } else {
                xt.val = val;
            }
        } else {
            NodeThree xt = (NodeThree)x;
            int cmpA = key.compareTo(xt.keyA);
            int cmpB = key.compareTo(xt.keyB);
            if (cmpA < 0) {
                Node tmp = put(xt.left, key, val);
                if (tmp.isMerge()){
                    x = mergeThree(xt, (NodeTwo)tmp, 1);
                } else
                    x = tmp;
            } else if(cmpA > 0 && cmpB < 0) {
                Node tmp = put(xt.middle, key, val);
                if (tmp.isMerge()){
                    x = mergeThree(xt, (NodeTwo)tmp, 2);
                } else
                    x = tmp;
            } else if(cmpB > 0) {
                Node tmp = put(xt.right, key, val);
                if (tmp.isMerge()){
                    x = mergeThree(xt, (NodeTwo)tmp, 3);
                } else
                    x = tmp;
            } else if(cmpA == 0) {
                xt.valA = val;
            } else if(cmpB == 0) {
                xt.valB = val;
            }
        }
        
        return x;
    }
    
    private Node mergeTwo(NodeTwo main, NodeTwo attach, boolean isLeft) {
        NodeThree n = null;
        if(isLeft) {
            n = new NodeThree(attach.key, attach.val, main.key, main.val, size(main));
            n.left = attach;
            n.middle = main.left;
            n.right = main.right;
        } else {
            n = new NodeThree(main.key, main.val, attach.key, attach.val, size(main));
            n.left = main.left;
            n.middle = main.right;
            n.right = attach;
        }
        return n;
    }
    
    private Node mergeThree(NodeThree main, NodeTwo attach, int direct) {
        switch(direct) {
        case 1: {NodeTwo m = new NodeTwo(main.keyA, main.valA, main.N, true);
            attach.setMerge(false);
            NodeTwo r = new NodeTwo(main.keyB, main.valB, main.N, false);
            m.left = attach;
            m.right = r;
            return m;
        }
        case 2: {NodeTwo l = new NodeTwo(main.keyA, main.valA, main.N, false);
            NodeTwo r = new NodeTwo(main.keyA, main.valA, main.N, false);
            attach.left = l;
            attach.right = r;
            return attach;
        }
        case 3: {NodeTwo m = new NodeTwo(main.keyB, main.valB, main.N, true);
            attach.setMerge(false);
            NodeTwo l = new NodeTwo(main.keyA, main.valA, main.N, false);
            m.left = l;
            m.right = attach;
            return m;
        }
        }
        return null;
    }
    public static void main(String[] args) {

    }

}
