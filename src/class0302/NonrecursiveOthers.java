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
    
    @Override
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
    
    @Override
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
    
    public Key select(int k) {
        if (root == null) return null;
        
        Node x = root;
        while(k >= 0 && x != null) {
            
            // prevent the 0 case, loop to the x's left end
            if(k == 0 && x.left == null) return x.key;
            
            // if left is null turn right , minus 1[x]
            if(x.left == null) {k--; x = x.right; continue;}
            // if left is k, then x is the target
            else if(x.left.N == k) return x.key;
            // if left is too big, then more left
            else if(x.left.N > k) {x = x.left;}
            // if left is too small, then turn right, minus left and 1[x]
            else if(x.left.N < k) {k -= x.left.N + 1; x = x.right;}
        }
        
        return null;
    }
    
    
    public int rank(Key key) {
        if (root == null) return 0;
        
        Node x = root;
        int k = 0;
        while(x != null) {
            
            int cmp = key.compareTo(x.key);
            // if left blank, check x; the cmp < 0 condition is same as below
            if(x.left == null) {
                if(cmp == 0) return k;
                // node not big enough, left blank, add 1[x], turn to right
                if(cmp > 0) {k++; x = x.right; continue;}
            }
            
            if(cmp == 0) return k + x.left.N;
            // node not big enough, add left part + 1[x], turn to right
            else if(cmp > 0) {k += x.left.N + 1; x = x.right;}
            // node too big, turn to left
            else if(cmp < 0) {x = x.left;}
            
        }
        
        return k;
    }
    
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
        
        System.out.println("----------");
        for(int i=0; i< 11; i++)
            System.out.println("select: "+ bst.select(i));
        System.out.println("----------");
        
        
        String[] src = {"1", "10", "2", "3", "4", "5", "6", "7", "8", "9", "11", "99"};
        
        System.out.println("----------");
        for(int i=0; i<src.length; i++) 
            System.out.println("rank: "+ bst.rank(src[i]));
        System.out.println("----------");
        
        System.out.println(bst.rank(bst.select(7)));
        
    }

}
