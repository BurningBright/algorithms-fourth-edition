package class0304;

/**
 * @Description 3.4.31
 *      基础链表
 * @author Leon
 * @date 2017-10-06 16:53:13
 */
public class SequentialST <Key extends Comparable<Key>, Value> {
    private int n;
    private Node first;
    
    class Node {
        Key key;
        Value val;
        Node next;

        public Node(Key key, Value val, Node next) {
            this.key  = key;
            this.val  = val;
            this.next = next;
        }
    }
    
    public int size() {
        return n;
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public Node getFirst() {
        return first;
    }
    
    public boolean contains(Key key) {
        return get(key) != null;
    }

    public Value get(Key key) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key))
                return x.val;
        }
        return null;
    }

    public Node put(Key key, Value val) {
        if (val == null) {
            return delete(key);
        }

        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                Node tmp = new Node(x.key, x.val, null);
                x.val = val;
                return tmp;
            }
        }
        first = new Node(key, val, first);
        n++;
        return first;
    }

    public Node delete(Key key) {
        
        Node x = first;
        Node prv = null;
        for (int i=0; i<n; i++) {
            if(key.equals(x.key)) {
                n--;
                if(prv != null)
                    prv.next = x.next;
                else
                    first = x.next;
                return x;
            }
            prv = x;
            x = x.next;
        }
        return null;
    }
    
    public static void main(String[] args){
        SequentialST<String, String> st = new SequentialST<String, String>();
        System.out.println(st.put("1", "a").val);
        System.out.println(st.put("2", "b").val);
        System.out.println(st.put("3", "c").val);
        System.out.println(st.put("4", "d").val);
        System.out.println(st.put("3", "X").val);
        
        System.out.println("---------");
        
        System.out.println(st.delete("4").val);
        System.out.println(st.delete("3").val);
        System.out.println(st.delete("2").val);
        System.out.println(st.delete("1").val);
        System.out.println(st.delete("1"));
    }
}
