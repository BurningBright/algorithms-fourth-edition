package context02;


import class0401.StdDraw;
import rlgs4.Queue;

/**
 * @Description context02.17
 *              B-tree 绘画
 * @author Leon
 * @date 2018-05-07 11:10:00
 */
public class PageDraw<Key extends Comparable<Key>> {

    private Key[]           keys;
    private PageDraw<Key>[] pages;
    private boolean         isExternal;
    private final int       M;
    private int             n;
    
    private double x;
    private double y;
    // X轴间隔
    private double offsetX    = .01;
    // Y轴间隔
    private double offsetY    = .05;
    // 格子半宽
    private double halfWidth  = .009;
    // 格子半高
    private double halfHeight = .015;
    
    
    // create and open a page
    @SuppressWarnings("unchecked")
    PageDraw(boolean bottom, int M) {
        this.M = M;
        isExternal = bottom;
        keys = (Key[])new Comparable[M];
        pages = new PageDraw[M];
    }
    
    // close a page
    void close() {
        
    }
    

    // put key into the (external) page
    void put(Key key) {
        if (key == null) return;
        int flag = 0;
        for (int i=0; i<M; i++) {
            if (keys[i]!= null && eq(key, keys[i]))
                return;
            if (keys[i]!= null && more(key, keys[i]))
                flag++;
        }
        for (int i=M-1; i>flag; i--)
            keys[i] = keys[i-1];
        keys[flag] = key;
        n++;
    }
    
    // open p and put an entry into this (internal) page 
    // that associates the smallest key in p with p
    void put(PageDraw<Key> p) {
        if (p == null) return;
        
        Key key = p.keys().iterator().next();
        boolean isAdd = false;
        int flag = 0;
        for (int i=0; i<M; i++) {
            if (n == 0) {
                isAdd = true;
                break;
            }
            if (keys[i] != null && eq(key, keys[i])) {
                isAdd = false;
                flag = i;
                break;
            }
            if (keys[i] != null && more(key, keys[i])) {
                flag ++;
                isAdd = true;
            }
        }
        for (int i=M-1; i>flag && isAdd ; i--) {
            keys[i] = keys[i-1];
            pages[i] = pages[i-1];
        }
        if (isAdd) n++;
        keys[flag] = key;
        pages[flag] = p;
    }
    
    // is this page external?
    boolean isExternal() {
        return isExternal;
    }
    
    // is key in the page?
    boolean contains(Key key) {
        for (int i=0; i<M; i++)
            if (keys[i] != null && eq(key, keys[i]))
                return true;
        return false;
    }
    
    // the subtree that could contain the key
    PageDraw<Key> next(Key key) {
        for (int i=0; i<M; i++) {
            if (keys[i] == null || less(key, keys[i]))
                return pages[i-1];
            if (i == M-1)
                return pages[i];
        }
        return null;
    }
    
    // has the page overﬂowed?
    boolean isFull() {
        return n == M;
    }
    
    int size() {
        return n;
    }
    
    // move the highest-ranking half of the keys in the page to a new page
    PageDraw<Key> split(double level) {
        PageDraw<Key> another = new PageDraw<Key>(isExternal, M);
        another.setCoordinate(x, y);
        // 新节点右移， 老节点左移
        another.moveRight(level);
        moveLeft(level);
        for (int i=M/2; i < M && keys[i] != null; i++) {
            another.put(keys[i]);
            another.put(pages[i]);
            keys[i] = null;
            pages[i] = null;
            n--;
        }
        return another;
    }
    
    Iterable<Key> keys() {
        Queue<Key> q = new Queue<Key>();
        for (int i=0; i<M; i++)
            if (keys[i] != null)
                q.enqueue(keys[i]);
        return q;
    }
    
    Iterable<PageDraw<Key>> pages() {
        Queue<PageDraw<Key>> q = new Queue<PageDraw<Key>>();
        for (int i=0; i<M; i++)
            if (pages[i] != null)
                q.enqueue(pages[i]);
        return q;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private boolean more(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) > 0;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }
    
    public void setCoordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void moveLeft(double level) {
        Queue<PageDraw<Key>> q = new Queue<PageDraw<Key>>();
        q.enqueue(this);
        int prv = 1;
        // 其下子节点左移
        while(!q.isEmpty()) {
            PageDraw<Key> p = q.dequeue();
            prv--;
            p.x -= M * halfWidth * level + offsetX;
            for (PageDraw<Key> pa: p.pages())
                q.enqueue(pa);
            if(prv == 0) {
                prv = q.size();
            }
        }
    }
    
    public void moveRight(double level) {
        Queue<PageDraw<Key>> q = new Queue<PageDraw<Key>>();
        q.enqueue(this);
        int prv = 1;
        // 其下子节点右移
        while(!q.isEmpty()) {
            PageDraw<Key> p = q.dequeue();
            prv--;
            p.x += M * halfWidth * level + offsetX;
            for (PageDraw<Key> pa: p.pages())
                q.enqueue(pa);
            if(prv == 0) {
                prv = q.size();
            }
        }
    }
    
    public void moveDown() {
        Queue<PageDraw<Key>> q = new Queue<PageDraw<Key>>();
        q.enqueue(this);
        int prv = 1;
        // 其下子节点下移
        while(!q.isEmpty()) {
            PageDraw<Key> p = q.dequeue();
            prv--;
            p.y -= halfHeight * 5 + offsetY;
            for (PageDraw<Key> pa: p.pages())
                q.enqueue(pa);
            if(prv == 0) {
                prv = q.size();
            }
        }
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public void draw() {
        
        double start = x - (M/2) * (halfWidth * 2);
        start -= M % 2 == 0? halfWidth: 0;
        for (int i=0; i<M; i++) {
            // 绘格子
            StdDraw.rectangle(start + i*(halfWidth*2), y, halfWidth, halfHeight);
            // 绘文字
            if (keys[i] != null) 
                StdDraw.text(start + i*(halfWidth*2), y-.005, keys[i].toString());
            // 绘引用
            if (pages[i] != null) {
                double px = pages[i].getX();
                double py = pages[i].getY();
                StdDraw.line(x, y-halfHeight, px, py+halfHeight);
            }
        }
    }
    
    public void reArrange(int i) {
        x = .05 + (halfWidth * 2 * M + offsetX) * i;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n).append(":");
        for (Key k: keys)
            sb.append(k).append(",");
        return sb.substring(0, sb.length()-1);
    }
    
    public static void main(String[] args) {
        PageDraw<String> page = new PageDraw<String>(true, 5);
        page.setCoordinate(.5, .5);
        StdDraw.line(.1, .5, .9, .5);
        StdDraw.line(.5, .1, .5, .9);
        page.draw();
    }
    
}
