package class0302;

/**
 * @Description 3.2.14
 *      norecursive min/ max/ floor/ ceiling/ rank/ select
 * @author Leon
 * @date 2017-03-28 22:24:48
 */
public class NonrecursiveOthers<Key extends Comparable<Key>, Value> extends NonrecursiveGetPut<Key, Value> {
    
    @Override
    public Key min() {
        if(root == null) return null;
        Node x = root;
        while(x.left != null) 
            x = x.left;
        
        return x.key;
    }
    
    @Override
    public Key max() {
        if(root == null) return null;
        Node x = root;
        while(x.right != null) 
            x = x.right;
        
        return x.key;
    }
    
    public Key floor(Key key) {
        if (root == null) return null;
        
        Node prep = null;
        Node x = root;
        while(x != null) {
            int cmp = key.compareTo(x.key);
            if(cmp == 0) return x.key;
            if(cmp > 0) {prep = x; x = x.right;}
            if(cmp < 0) {x = x.left;}
            
        }
        
        return prep == null? null: prep.key;
    }
    
    public Key ceil(Key key) {
        if (root == null) return null;
        
        Node prep = null;
        Node x = root;
        while(x != null) {
            int cmp = key.compareTo(x.key);
            if(cmp == 0) return x.key;
            if(cmp < 0) {prep = x; x = x.left;}
            if(cmp > 0) {x = x.right;}
        }
        
        return prep == null? null: prep.key;
    }
    /*
    public Key select(int k) {
        
    }
    */
    /*
    public int rank(Key key) {
        
        
    }
    */
    public static void main(String[] args) {
        NonrecursiveOthers<String, String> bst = new NonrecursiveOthers<String, String>();
        bst.put("5", "v0");
        bst.put("3", "v1");
        bst.put("7", "v1");
        bst.put("2", "v2");
        bst.put("4", "v2");
        bst.put("6", "v2");
        bst.put("8", "v2");
        bst.put("1", "v3");
        bst.put("10", "v4");
        bst.put("9", "v3");
        
        System.out.println("----------");
        for(Object k: bst.keys()) {
            System.out.println(k.toString());
        }
        System.out.println("----------");
        
        System.out.println("min: "+ bst.min());
        System.out.println("max: "+ bst.max());
        
        System.out.println("floor: "+ bst.floor("50"));
        System.out.println("ceil: "+ bst.ceil("50"));
        
        System.out.println("select: "+ bst.select(3));
        System.out.println("rank: "+ bst.rank("2"));
    }

}
