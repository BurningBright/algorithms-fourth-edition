package class0204;

import class0103.DequeDLink;
import rlgs4.LinkedQueue;
import stdlib.StdOut;

/**
 * @Description 2.4.24
 *      使用三链接构建堆结构
 * @author Leon
 * @date 2016-09-22 09:16:06
 */
public class LinkPQ<Key extends Comparable<Key>> {
    
    private int N = 0;
    private Node<Key> top;
    private DequeDLink<Node<Key>> ddl= new DequeDLink<Node<Key>>();
    
    public LinkPQ(){
        
    }
    
    public LinkPQ(Key[] a){
        for(Key k: a) {
            insert(k);
        }
    }
    
    /* 插入 上浮 */
    void insert(Key v){
        
        if(N == 0) {
            top = new Node<Key>(null, null, null, v);
            ddl.pushRight(top);
            N++;
            return;
        }
        
        N++;
        if(ddl.peek().chL==null) {
            Node<Key> node = new Node<Key>(ddl.peek(), null, null, v);
            ddl.peek().chL = node;
            ddl.pushRight(node);
            swim(ddl.peek().chL);
            return;
        }
        
        if(ddl.peek().chR==null) {
            Node<Key> node = new Node<Key>(ddl.peek(), null, null, v);
            ddl.peek().chR = node;
            ddl.pushRight(node);
            ddl.popLeft();
            swim(node);
            return;
        }
        
    }
    
    Key max(){
        return top.key;
    }
    
    /* 移除 末尾上台 下沉 */
    Key delMax(){
        if(isEmpty()) {
            throw new UnsupportedOperationException();
        }
        N--;
        
        // 交换首尾节点
        Node<Key> node = ddl.popRight();
        exch(node, top);
        
        // 下沉
        sink(top);
        
        return node.key;
    }
    
    boolean isEmpty(){
        return N == 0;
    }
    
    int size() {
        return N;
    }
    
    public void print() {
        LinkedQueue<Node<Key>> lq = new LinkedQueue<Node<Key>>();
        lq.enqueue(top);
        int n = 1;
        while(lq.size() > 0) {
            String level = "";
            int counter = 0;
            for(int i=0; i<n; i++) {
                Node<Key> tmp = lq.dequeue();
                level += tmp.key;
                if(tmp.chL != null) {
                    lq.enqueue(tmp.chL);
                }
                if(tmp.chR != null) {
                    lq.enqueue(tmp.chR);
                }
            }
            
            n = lq.size();
            StdOut.println(level);
        }
    }
    
    private void swim(Node<Key> node) {
        Node<Key> p = node.parent;
        while(p != null && less(p, node)) {
            exch(node, p);
            if(p.parent == null)
                break;
            else
                p = p.parent;
        }
    }
    
    private void sink(Node<Key> node) {
        Node<Key> c = node.chL == null ? 
                (node.chR == null ? null : node.chR) : 
                    (node.chR == null ? node.chL : less(node.chL, node.chR) ? node.chR : node.chL);
        while(c != null && less(node, c)){
            exch(node, c);
            Node<Key> tmp = c.chL == null ? 
                    (c.chR == null ? null : c.chR) : 
                        (c.chR == null ? c.chL : less(c.chL, c.chR) ? c.chR : c.chL);
            if(tmp == null)
                break;
            else
                c = tmp;
        }
    }
    
    private boolean less(Node<Key> i, Node<Key> j) {
        return i.key.compareTo(j.key) < 0;
    }
    
    private void exch(Node<Key> i, Node<Key> j) {
        
        if(i == j)
            return;
        
        // 傻
        Key tmp = i.key;
        i.key = j.key;
        j.key = tmp;
        
    }
    
    
    @SuppressWarnings("hiding")
    private class Node<Key> {
        Node<Key> parent;
        Node<Key> chL;
        Node<Key> chR;
        Key key;
        Node(Node<Key> parent, Node<Key> chL, Node<Key> chR,Key key) {
            this.parent = parent;
            this.chL = chL;
            this.chR = chR;
            this.key = key;
        }
    }
    
    public static void main(String[] args) {
        LinkPQ<Integer> lpq = new LinkPQ<Integer>();
        lpq.insert(3);
        lpq.insert(4);
        lpq.insert(5);
        lpq.insert(6);
        lpq.print();
    }

}
