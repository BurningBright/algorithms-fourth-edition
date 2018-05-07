package context02;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import class0401.StdDraw;
import rlgs4.Queue;
import stdlib.StdOut;

/**
 * @Description context02.17
 *              B-tree 绘画
 * @author Leon
 * @date 2018-05-07 11:10:00
 */
public class BTreeDraw<Key extends Comparable<Key>> {
    
    private int M = 5;
    private PageDraw<Key> root = new PageDraw<Key>(true, M);

    {
        // 初始化画板
        StdDraw.setCanvasSize(1024, 512);
        StdDraw.setPenRadius(.003);
        StdDraw.enableDoubleBuffering();
        StdDraw.setOutsideHandle(new Tigger());
    }
    
    @SuppressWarnings("unchecked")
    public BTreeDraw() {
        root.setCoordinate(.5, .8);
        add((Key)"*");
    }
    
    public BTreeDraw(Key sentinel) {
        add(sentinel);
    }

    public boolean contains(Key key) {
        return contains(root, key);
    }

    private boolean contains(PageDraw<Key> h, Key key) {
        if (h.isExternal())
            return h.contains(key);
        return contains(h.next(key), key);
    }

    public void add(Key key) {
        // 等待回调唤醒
        synchronized(root){
            try {
                root.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 分裂度渐进减小
        double K = 3.0;
        add(root, key, K);
        if (root.isFull()) {
            double xRoot = root.getX();
            double yRoot = root.getY();
            PageDraw<Key> lefthalf = root;
            PageDraw<Key> righthalf = root.split(K);
            root = new PageDraw<Key>(false, M);
            root.setCoordinate(xRoot, yRoot);
            // 分裂子节点下移
            lefthalf.moveDown();
            righthalf.moveDown();
            root.put(lefthalf);
            root.put(righthalf);
        }
        draw();
    }

    public void add(PageDraw<Key> h, Key key, double level) {
        if (h.isExternal()) {
            h.put(key);
            return;
        }
        PageDraw<Key> next = h.next(key);
        // 渐进
        level -= 1.0;
        add(next, key, level);
        if (next.isFull()) 
            h.put(next.split(level));
        next.close();
    }
    
    private void draw() {
        StdDraw.clear();
        Queue<PageDraw<Key>> q = new Queue<PageDraw<Key>>();
        q.enqueue(root);
        // 广度遍历节点绘图
        while(!q.isEmpty()) {
            PageDraw<Key> p = q.dequeue();
            p.draw();
            for (PageDraw<Key> pa: p.pages())
                q.enqueue(pa);
        }
        StdDraw.pause(20);
        StdDraw.show();
    }
    
    public int size() {
        Queue<PageDraw<Key>> q = new Queue<PageDraw<Key>>();
        q.enqueue(root);
        int count = 0;
        while(!q.isEmpty()) {
            PageDraw<Key> p = q.dequeue();
            if (p.isExternal())
                count += p.size();
            for (PageDraw<Key> pa: p.pages())
                q.enqueue(pa);
        }
        return count - 1;
    }
    
    public String toString() {
        return toString(root);
    }
    
    private String toString(PageDraw<Key> page) {
        StringBuilder sb = new StringBuilder();
        Queue<PageDraw<Key>> q = new Queue<PageDraw<Key>>();
        q.enqueue(page);
        int prv = 1;
        while(!q.isEmpty()) {
            PageDraw<Key> p = q.dequeue();
            sb.append(p.toString()).append(" | ");
            prv--;
            for (PageDraw<Key> pa: p.pages())
                q.enqueue(pa);
            if(prv == 0) {
                sb.append("\r\n");
                prv = q.size();
            }
        }
        return sb.toString();
    }
    
    // 唤醒句柄监听类
    class Tigger implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            synchronized(root){
                root.notify();
            }
        }
        
    }
    
    public static void main(String[] args) {
        String org = "A B C D E F G H I J K L M N O P Q R S T";
//        org = "B L K R O A I S D M P F E J G Q H T C N";
        String[] src = org.split(" ");
        BTreeDraw<String> tree = new BTreeDraw<String>();
        
        for (String s: src) {
            tree.add(s);
            StdOut.println(s);
        }
        
    }
}
