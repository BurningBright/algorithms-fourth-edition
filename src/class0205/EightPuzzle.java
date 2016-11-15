package class0205;

import java.awt.Color;
import java.awt.Font;

import rlgs4.LinkedBag;
import rlgs4.MinPQ;
import stdlib.StdDraw;
import stdlib.StdOut;

/**
 * @Description 2.5.32
 *      A*算法+优先队列找最佳解决路径
 * @author Leon
 * @date 2016-11-14 16:39:07
 */
public class EightPuzzle {
    
    public static void aStar(PuzzleNode pa) {
        MinPQ<PuzzleNode> open = new MinPQ<PuzzleNode>();
        open.insert(pa);
        LinkedBag<PuzzleNode> close = new LinkedBag<PuzzleNode>();
        /*
         * 从a点开始，寻找可能，
         * 将F值最小的节点弹出，寻找可能入寻找队列
         * 直到目标节点在闭合列表中/或寻找队列弹空
         * 缺少重新寻找更短路径的部分 parent
         */
        while(open.size() != 0 && !isNodeInClose(close, PuzzleNode.target)) {
            PuzzleNode cur   = open.delMin();
//            cur.express();
            
            PuzzleNode top   = cur.moveT();
            PuzzleNode left  = cur.moveL();
            PuzzleNode down  = cur.moveD();
            PuzzleNode right = cur.moveR();
            if (top != null && !isNodeInClose(close, top))
                open.insert(top);
            if (left != null && !isNodeInClose(close, left))
                open.insert(left);
            if (down != null && !isNodeInClose(close, down))
                open.insert(down);
            if (right != null && !isNodeInClose(close, right))
                open.insert(right);
            close.add(cur);
        }
        for(PuzzleNode p: close)
            if (PuzzleNode.target.equals(p)) {
                PuzzleNode prv = p;
                while(prv.parent != null) {
                    prv.express();
                    prv = prv.parent;
                }
                break;
            }
    }
    
    private static boolean isNodeInClose(LinkedBag<PuzzleNode> close, PuzzleNode a) {
        for(PuzzleNode p: close)
            if (a.equals(p))
                return true;
        return false;
    }
    
    public static void main(String[] args) {
        String[] src = {"1", "2", "3", "4", "5", "6", "7", "8", " "};
        PuzzleNode B = new PuzzleNode(3, 3, 2, 2, src);
        PuzzleNode.target = B;
        
//        StdRandom.shuffle(src);
        String[] srcA = {"1", "2", " ", "4", "5", "3", "7", "8", "6"};
        PuzzleNode A = new PuzzleNode(3, 3, 2, 0, srcA);
        A.G = A.manhattan();
        
        aStar(A);
        /*
        A.express();
        B.express();
        StdOut.println(A.manhattan());
        */
    }

}

/**
 * @Description 拼图节点
 * @author Leon
 * @date 2016-11-15 11:31:49
 */
class PuzzleNode implements Comparable<PuzzleNode>{
    
//    int F;
    int G;
    int H;
    PuzzleNode parent;
    static PuzzleNode target;
    private int wNum;
    private int hNum;
    private int Ax;
    private int Ay;
    Puzzle[][] matrix;
    
    // 节点构造
    public PuzzleNode(int wNum, int hNum, int Ax, int Ay, String ...values) {
        G = 0;
        H = 0;
        this.wNum = wNum;
        this.hNum = hNum;
        this.Ax = Ax;
        this.Ay = Ay;
        matrix = new Puzzle[hNum][wNum];
        
        for(int i=0; i<hNum; i++)
            for(int j=0; j<wNum; j++) 
                matrix[i][j] = new Puzzle(i*wNum+j+1, values[i*wNum+j], j+1, i+1);
        
        StdDraw.setYscale(-1, 0);
    }
    
    // 过程节点构造
    public PuzzleNode(int wNum, int hNum, int Ax, int Ay, Puzzle[][] matrix) {
        this.wNum = wNum;
        this.hNum = hNum;
        this.Ax = Ax;
        this.Ay = Ay;
        this.matrix = new Puzzle[hNum][wNum];
        
        for(int i=0; i<hNum; i++)
            for(int j=0; j<wNum; j++) 
                this.matrix[i][j] = matrix[i][j].clone();
        
    }
    
    public void express() {
        for(int i=0; i<hNum; i++) {
            for(int j=0; j<wNum; j++) 
                StdOut.print(matrix[i][j].value + " ");
            StdOut.println();
        }
        StdOut.println(this.G+" "+this.H);
        StdOut.println();
    }
    
    
    public PuzzleNode moveT() {
        if (Ay > 0) {
            PuzzleNode tmp = new PuzzleNode(wNum, hNum, Ax, Ay-1, matrix);
            PuzzleNode.exch(tmp.matrix, Ax, Ay, Ax, Ay-1);
            tmp.G += 1;
            tmp.H = tmp.manhattan();
            tmp.parent = this;
            return tmp;
        } else
            return null;
    }
    
    public PuzzleNode moveL() {
        if (Ax > 0) {
            PuzzleNode tmp = new PuzzleNode(wNum, hNum, Ax-1, Ay, matrix);
            PuzzleNode.exch(tmp.matrix, Ax, Ay, Ax-1, Ay);
            tmp.G += 1;
            tmp.H = tmp.manhattan();
            tmp.parent = this;
            return tmp;
        } else
            return null;
    }
    
    public PuzzleNode moveD() {
        if (Ay < hNum-1) {
            PuzzleNode tmp = new PuzzleNode(wNum, hNum, Ax, Ay+1, matrix);
            PuzzleNode.exch(tmp.matrix, Ax, Ay, Ax, Ay+1);
            tmp.G += 1;
            tmp.H = tmp.manhattan();
            tmp.parent = this;
            return tmp;
        } else
            return null;
    }
    
    public PuzzleNode moveR() {
        if (Ax < wNum-1) {
            PuzzleNode tmp = new PuzzleNode(wNum, hNum, Ax+1, Ay, matrix);
            PuzzleNode.exch(tmp.matrix, Ax, Ay, Ax+1, Ay);
            tmp.G = G + 1;
            tmp.H = tmp.manhattan();
            tmp.parent = this;
            return tmp;
        } else
            return null;
    }
    
    private static void exch(Puzzle[][] p, int x1, int y1, int x2, int y2) {
        Puzzle tmp = p[y1][x1];
        p[y1][x1] = p[y2][x2];
        p[y1][x1].moveTo(y2, x2);
        p[y2][x2] = tmp;
        p[y2][x2].moveTo(y1, x1);
    }
    
    public int manhattan () {
        int sum = 0;
        for(int i=0; i<hNum; i++) 
            for(int j=0; j<wNum; j++) 
                for(int xi=0; xi<hNum; xi++) 
                    for(int xj=0; xj<wNum; xj++) 
                        if(matrix[i][j].equals(target.matrix[xi][xj]))
                            sum += Math.abs(xi - i) + Math.abs(xj -j);
        return sum;
    }
    
    @Override
    public int compareTo(PuzzleNode o) {
        return this.G + this.H - o.G - o.H;
    }
    
    public boolean equals(PuzzleNode o) {
        for(int i=0; i<o.matrix.length; i++)
            for(int j=0; j<o.matrix[0].length; j++)
                if (!matrix[i][j].equals(o.matrix[i][j]))
                    return false;
        return true;
    }
    
    
    
    
    
    /**
     * @Description 拼图
     * @author Leon
     * @date 2016-11-15 11:31:32
     */
    class Puzzle implements Cloneable, Comparable<Puzzle> {
        
        int id;
        String value;
        int orderX;
        int orderY;
        double width;
        double height;
        Color  color;
        
        public Puzzle(int id, String value, int orderX, int orderY) {
            this.id = id;
            this.value = value;
            this.orderX = orderX;
            this.orderY = orderY;
            this.width = 1;
            this.height = -1;
            this.color = StdDraw.RED;
        }
        
        public void moveTo(int y, int x) {
            orderX = x + 1;
            orderY = y + 1;
        }
        
        public void draw() {
            Color tmp = StdDraw.getPenColor();
            StdDraw.setPenColor(color);
            StdDraw.setFont(new Font("Consolas", Font.ITALIC, 21));
            StdDraw.text((width/(wNum+1))*orderX, (height/(hNum+1))*orderY, " ".equals(value)? "X": value);
            StdDraw.setPenColor(tmp);
        }
        
        public Puzzle clone() {
            return new Puzzle(id, value, orderX, orderY);
        }

        @Override
        public int compareTo(Puzzle o) {
            return value.compareTo(o.value);
        }
        
        public boolean equals(Puzzle o) {
            return value.equals(o.value);
        }
        
    }
    
    /**
     * @Description 线框
     * @author Leon
     * @date 2016-11-15 11:31:18
     */
    class Frame {
        
        double width;
        double height;
        double border;
        Color  color;
        
        public Frame() {
            width  = 1;
            height = -1;
            border = .005;
            color  = StdDraw.RED;
        }
        
        public void draw() {
            Color tmp = StdDraw.getPenColor();
            StdDraw.setPenColor(color);
            StdDraw.filledRectangle(width/2, height/3, width/2, border/2);
            StdDraw.filledRectangle(width/2, height*2/3, width/2, border/2);
            StdDraw.filledRectangle(width/3, height/2, border/2, Math.abs(height/2));
            StdDraw.filledRectangle(width*2/3, height/2, border/2, Math.abs(height/2));
            StdDraw.setPenColor(tmp);
        }
    }

}
