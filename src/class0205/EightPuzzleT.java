package class0205;

import java.util.Arrays;

import rlgs4.Bag;
import rlgs4.MinPQ;
import stdlib.StdOut;

/**
 * @Description 2.5.32
 *      A*算法+优先队列找最佳解决路径
 *      比较暴力，不优雅
 * @author Leon
 * @date 2018-03-08 10:05:07
 */
public class EightPuzzleT {
    
    private Grid end;
    private MinPQ<Grid> pq;
    
    public EightPuzzleT(Grid start, Grid end) {
        this.end = end;
        pq = new MinPQ<Grid>();
        pq.insert(start);
        
        while (!pq.isEmpty())
            if(aStar(pq.delMin()))
                break;
        
    }
    
    private boolean aStar(Grid grid) {
        StdOut.println(grid);
        if(grid.equals(end))
            return true;
        for (Grid g: grid.neighbors(end)) {
//            StdOut.println("-" + g);
            if (g.compareTo(grid) <= 0)
                pq.insert(g);
        }
        return false;
    }
    
    public static void main(String[] args) {
        Puzzle[][] p = new Puzzle[3][3];
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++)
                p[i][j] = new Puzzle(i*3+j);
        p[0][0].isBlank = true;
        Grid end = new Grid(p, .0);
        StdOut.println(end);
        /*
        Grid start = end.clone();
        Grid.exch(start, 0, 0, 1, 0);
        start.puzzles[1][0].isBlank = true;
        start.bI = end.bI+1;
        start.bJ = end.bJ;
        start.calcPrivilege(end);
        StdOut.println(start);
        */
        
        int[] src = {3, 1, 2, 6, 0, 5, 7, 4, 8};
        
        Puzzle[][] pp = new Puzzle[3][3];
        for (int i=0; i<3; i++)
            for (int j=0; j<3; j++)
                pp[i][j] = new Puzzle(src[i*3+j]);
        
        pp[1][1].isBlank = true;
        Grid start = new Grid(pp, .0);
        start.calcPrivilege(end);
        StdOut.println(start);
        
        StdOut.println("---------");
        
        @SuppressWarnings("unused")
        EightPuzzleT ep = new EightPuzzleT(start, end);
        
    }
    
}

class Puzzle {
    int serial;
    boolean isBlank;
    public Puzzle(int serial) {
        this.serial = serial;
    }
    public String toString() {
        return serial + (isBlank? "-": "+");
    }
}

class Grid implements Comparable<Grid>, Cloneable{
    Puzzle[][] puzzles;
    int bI;
    int bJ;
    private double G;           // 走到当前所需步数
    private double F;           // 步数 + 曼哈顿距离
    
    public Grid(Puzzle[][] puzzles, double step) {
        this.puzzles = puzzles;
        // 找空白格子
        for (int i=0; i<puzzles.length; i++)
            for (int j=0; j<puzzles[0].length; j++)
                if (puzzles[i][j].isBlank) {
                    bI = j;
                    bJ = i;
                }
        G = step;
    }
    
    public void calcPrivilege(Grid end) {
        F = G + manhattan(end);
    }
    
    public Bag<Grid> neighbors(Grid end) {
        Bag<Grid> bag = new Bag<Grid>();
        int x = puzzles.length-1;
        int y = puzzles[0].length-1;
        
        if (bI < x)
            bag.add(up(end));
        if (bI > 0)
            bag.add(down(end));
        if (bJ < y)
            bag.add(right(end));
        if (bJ > 0)
            bag.add(left(end));
        
        for (Grid g: bag) {
            g.G++;
            g.F = g.manhattan(end);
        }
        return bag;
    }
    
    private Grid up(Grid end) {
        Grid g = this.clone();
        exch(g, bI, bJ, bI+1, bJ);
        g.bI = bI+1;
        g.bJ = bJ;
//        g.G++;
//        g.F = g.G + g.manhattan(end);
        return g;
    }
    
    private Grid down(Grid end) {
        Grid g = this.clone();
        exch(g, bI, bJ, bI-1, bJ);
        g.bI = bI-1;
        g.bJ = bJ;
//        g.G++;
//        g.F = g.G + g.manhattan(end);
        return g;
    }
    
    private Grid left(Grid end) {
        Grid g = this.clone();
        exch(g, bI, bJ, bI, bJ-1);
        g.bI = bI;
        g.bJ = bJ-1;
//        g.G++;
//        g.F = g.G + g.manhattan(end);
        return g;
    }
    
    private Grid right(Grid end) {
        Grid g = this.clone();
        exch(g, bI, bJ, bI, bJ+1);
        g.bI = bI;
        g.bJ = bJ+1;
//        g.G++;
//        g.F = g.G + g.manhattan(end);
        return g;
    }
    
    public static void exch(Grid g, int a, int b, int c, int d) {
        Puzzle tmp = g.puzzles[a][b];
        g.puzzles[a][b] = g.puzzles[c][d];
        g.puzzles[c][d] = tmp;
    }
    
    public double manhattan(Grid grid) {
        double sum = .0;
        int x = puzzles.length;
        int y = puzzles[0].length;
        for (int i=0; i<x; i++) 
            std:for (int j=0; j<y; j++) 
                for (int m=0; m<x; m++) 
                    for (int n=0; n<y; n++) 
                        if (this.puzzles[i][j].serial 
                                == grid.puzzles[m][n].serial) {
                            sum += Math.abs(i - m) + Math.abs(j - n);
                            continue std;
                        }
        return sum;
    }
    
    public Grid clone() {
        int x = puzzles.length;
        int y = puzzles[0].length;
        Puzzle[][] tmp = new Puzzle[x][y];
        for (int i=0; i<x; i++)
            for (int j=0; j<x; j++)
                tmp[i][j] = new Puzzle(puzzles[i][j].serial);
        tmp[bI][bJ].isBlank = true;
        Grid g = new Grid(tmp, G);
        return g;
    }
    
    // 在堆中已F作为对比依据
    @Override
    public int compareTo(Grid b) {
        double ret = this.F - b.F;
        return ret > 0? 1: (ret < 0? -1: 0);
    }
    
    public boolean equals(Grid b) {
        int x = puzzles.length;
        int y = puzzles[0].length;
        for (int i=0; i<x; i++) 
            for (int j=0; j<y; j++) 
                if (this.puzzles[i][j].serial 
                        != b.puzzles[i][j].serial) {
                    return false;
                }
        return true;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Arrays.deepToString(puzzles));
        sb.append(G);
        sb.append(" ");
        sb.append(F);
        sb.append(" ");
        sb.append(bI);
        sb.append(" ");
        sb.append(bJ);
        return sb.toString();
    }
    
}