package class0303;

/**
 * @Description 3.3.35
 *     自建结构23树
 * @author Leon
 * @date 2017-07-26 10:29:13
 */
public class TwoThreeST<Key extends Comparable<Key>, Value> {
    
    Node root;
    
    // 节点接口
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
        
        // 节点是否需要合并
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
        
        // 2节点对比一次
        if (!x.isThree()){
            NodeTwo xt = (NodeTwo)x;
            int cmp = key.compareTo(xt.key);
            if (cmp > 0) {
                // 如果返回节点需要合并则合并替换当前节点, 否则递归返回节点链接对应方向
                Node tmp = put(xt.right, key, val);
                if (tmp.isMerge()){
                    x = mergeTwo(xt, (NodeTwo)tmp, false);
                } else
                    xt.right = tmp;
            } else if (cmp < 0) {
                Node tmp = put(xt.left, key, val);
                if (tmp.isMerge()){
                    x = mergeTwo(xt, (NodeTwo)tmp, true);
                } else
                    xt.left = tmp;
            } else {
                xt.val = val;
            }
        } else {
            // 3节点对比两次
            NodeThree xt = (NodeThree)x;
            int cmpA = key.compareTo(xt.keyA);
            int cmpB = key.compareTo(xt.keyB);
            if (cmpA < 0) {
                Node tmp = put(xt.left, key, val);
                if (tmp.isMerge()){
                    x = mergeThree(xt, (NodeTwo)tmp, 1);
                } else
                    xt.left = tmp;
            } else if(cmpA > 0 && cmpB < 0) {
                Node tmp = put(xt.middle, key, val);
                if (tmp.isMerge()){
                    x = mergeThree(xt, (NodeTwo)tmp, 2);
                } else
                    xt.middle = tmp;
            } else if(cmpB > 0) {
                Node tmp = put(xt.right, key, val);
                if (tmp.isMerge()){
                    x = mergeThree(xt, (NodeTwo)tmp, 3);
                } else
                    xt.right = tmp;
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
            /*
             *  合并两2节点，得到3节点
             *  从左合并，主节点放'B位'
             *  3节点     右引用继承主节点右引用
             *  3节点     左中引用，使用'A位'左右引用
             */
            n = new NodeThree(attach.key, attach.val, main.key, main.val, size(main));
            n.left = attach.left;
            n.middle = attach.right;
            n.right = main.right;
        } else {
            /*
             *  合并两2节点，得到3节点
             *  从右合并，主节点放'A位'
             *  3节点     左引用继承主节点左引用
             *  3节点     右中引用，使用'B位'左右引用
             */
            n = new NodeThree(main.key, main.val, attach.key, attach.val, size(main));
            n.left = main.left;
            n.middle = attach.left;
            n.right = attach.right;
        }
        n.N = size(n.left) + size(n.middle) + size(n.right) + 2;
        return n;
    }
    
    private Node mergeThree(NodeThree main, NodeTwo attach, int direct) {
        switch(direct) {
        case 1: {
            /*
             *  Left 三节点 从左引用合并
             *  'B位'作为右节点，左右引用，继承主节点的'中右引用'
             *  'A位'作为新父节点，指向子节点【新晋节点在左】
             */
            NodeTwo m = new NodeTwo(main.keyA, main.valA, main.N, true);
            attach.setMerge(false);
            NodeTwo r = new NodeTwo(main.keyB, main.valB, main.N, false);
            
            r.left = main.middle;
            r.right = main.right;
            r.N = size(r.left) + size(r.right) + 1;
            
            m.left = attach;
            m.right = r;
            m.N = size(m.left) + size(m.right) + 1;
            
            return m;
        }
        case 2: {
            /*
             *  Middle 三节点 从中引用合并
             *  AB分别作为新子节点，分别继承主节点的'左右引用'
             *  新晋节点的左右子节点，分别作为新子节点的'右左引用'
             *  新晋节点成为新父节点，指向新子节点
             */
            NodeTwo l = new NodeTwo(main.keyA, main.valA, main.N, false);
            NodeTwo r = new NodeTwo(main.keyB, main.valB, main.N, false);
            
            l.left = main.left;
            l.right = attach.left;
            l.N = size(l.left) + size(l.right) + 1;
            
            r.left = attach.right;
            r.right = main.right;
            r.N = size(r.left) + size(r.right) + 1;
            
            attach.left = l;
            attach.right = r;
            attach.N = size(attach.left) + size(attach.right) + 1;
            
            return attach;
        }
        case 3: {
            /*
             *  Right 三节点 从右引用合并
             *  'A位'作为左节点，左右引用，继承主节点的'左中引用'
             *  'B位'作为新父节点，指向子节点【新晋节点在右】
             */
            NodeTwo m = new NodeTwo(main.keyB, main.valB, main.N, true);
            attach.setMerge(false);
            NodeTwo l = new NodeTwo(main.keyA, main.valA, main.N, false);
            
            l.left = main.left;
            l.right = main.middle;
            l.N = size(l.left) + size(l.right) + 1;
            
            m.left = l;
            m.right = attach;
            m.N = size(m.left) + size(m.right) + 1;
            
            return m;
        }
        }
        return null;
    }
    public static void main(String[] args) {
        TwoThreeST<String, Object> ttst = new TwoThreeST<String, Object>();
        ttst.put("A", 1);
        ttst.put("B", 1);
        ttst.put("C", 1);
        ttst.put("D", 1);
        ttst.put("E", 1);
        ttst.put("B1", 1);
        ttst.put("B2", 1);
//        ttst.put("F", 1);
//        ttst.put("G", 1);
        System.out.println("xxx");
    }

}
