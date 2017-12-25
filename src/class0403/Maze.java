package class0403;

import class0401.StdDraw;
import rlgs4.IndexMinPQ;
import stdlib.StdRandom;

/**
 * @Description web.2
 *          MST 生成迷宫
 * @author Leon
 * @date 2017-12-25 10:47:00
 */
public class Maze {
    private int n;                 // dimension of maze
    private boolean[][] north;     // is there a wall to north of cell i, j
    private boolean[][] east;
    private boolean[][] south;
    private boolean[][] west;
    
    private double[][] northW;     // wall's weight
    private double[][] eastW;
    private double[][] southW;
    private double[][] westW;
    
    private boolean[][] visited;    // 是否已访问
    private boolean done = false;
    
    // algorithm part
    private Edge[][] edgeTo;
    private double[][] distTo;
    private IndexMinPQ<Double> pq;

    public Maze(int n) {
        this.n = n;
        StdDraw.setXscale(0, n+2);
        StdDraw.setYscale(0, n+2);
        init();
        generate();
    }

    private void init() {
        // initialize border cells as already visited
        visited = new boolean[n+2][n+2];
        for (int x = 0; x < n+2; x++) {
            visited[x][0] = true;
            visited[x][n+1] = true;
        }
        for (int y = 0; y < n+2; y++) {
            visited[0][y] = true;
            visited[n+1][y] = true;
        }


        // initialze all walls as present & walls weight
        north = new boolean[n+2][n+2];
        east  = new boolean[n+2][n+2];
        south = new boolean[n+2][n+2];
        west  = new boolean[n+2][n+2];
        
        // 初始化墙的权重
        northW = new double[n+2][n+2];
        eastW  = new double[n+2][n+2];
        southW = new double[n+2][n+2];
        westW  = new double[n+2][n+2];
        
        // 最小生成树数据结构
        edgeTo = new Edge[n+2][n+2];
        distTo = new double[n+2][n+2];
        
        for (int x = 0; x < n+2; x++) {
            for (int y = 0; y < n+2; y++) {
                north[x][y] = true;
                east[x][y]  = true;
                south[x][y] = true;
                west[x][y]  = true;
                northW[x][y] = StdRandom.uniform();
                eastW[x][y] = StdRandom.uniform();
                southW[x][y] = StdRandom.uniform();
                westW[x][y] = StdRandom.uniform();
                
                // 设初始边权重为无限大
                distTo[x][y] = Double.POSITIVE_INFINITY;
            }
        }
        
        // 初始化索引优先队列，放入第一个元素
        pq = new IndexMinPQ<Double>((n+2)*(n+2));
        distTo[1][1] = 0.0;
        pq.insert(n+3, 0.0);
    }

    private void generateMST(int xv, int yv) {
        // 标为已探访
        visited[xv][yv] = true;
        // 准备邻接节点和边权重
        int[][] adj = {{xv, yv+1}, {xv, yv-1}, {xv+1, yv}, {xv-1, yv}};
        double[] wei = {southW[xv][yv], northW[xv][yv], eastW[xv][yv], westW[xv][yv]};
        
        for (int i=0; i<adj.length; i++) {
            int xw = adj[i][0];
            int yw = adj[i][1];
            
            // 如已探访则跳过
            if (visited[xw][yw])
                continue;
            
            // 更新周边节点最小权重
            if (wei[i] < distTo[xw][yw]) {
                int v = xv * (n+2) + yv;
                int w = xw * (n+2) + yw;
                edgeTo[xw][yw] = new Edge(v, w, wei[i]);
                distTo[xw][yw] = wei[i];
                if (pq.contains(w))
                    pq.changeKey(w, distTo[xw][yw]);
                else
                    pq.insert(w, distTo[xw][yw]);
            }
        }
        
        // 下次出队节点即入树节点
        if (!pq.isEmpty()) {
            int next = pq.minIndex();
            int nx = next / (n+2);
            int ny = next % (n+2);
            Edge e = edgeTo[nx][ny];
            int ox = e.other(next) / (n+2);
            int oy = e.other(next) % (n+2);
            
            // 更新节点画图属性[有无墙]
            if (nx > ox) {
                west[nx][ny] = east[ox][oy] = false;
            } else if (nx < ox) {
                west[ox][oy] = east[nx][ny] = false;
            } else if (ny > oy) {
                north[ox][oy] = south[nx][ny] = false;
            } else if (ny < oy) {
                north[nx][ny] = south[ox][oy] = false;
            }
        }
    }

    // generate the maze starting from lower left
    private void generate() {
//        generate(1, 1);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            int xv = v / (n+2);
            int yv = v % (n+2);
            generateMST(xv, yv);
        }
     
    }



    // solve the maze using depth-first search
    private boolean solve(int x, int y) {
        if (x == 0 || y == 0 || x == n+1 || y == n+1) return false;
        if (done || visited[x][y]) return false;
        visited[x][y] = true;

        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
        StdDraw.show();
        StdDraw.pause(30);

        // reached middle
        if (x == n/2 && y == n/2) done = true;
/*
        if (!north[x][y]) solve(x, y + 1);
        if (!east[x][y])  solve(x + 1, y);
        if (!south[x][y]) solve(x, y - 1);
        if (!west[x][y])  solve(x - 1, y);
*/
        StdDraw.setPenColor(StdDraw.RED);
        if (!north[x][y]) {
            if (solve(x, y + 1)) {
                StdDraw.line(x + 0.5, y + 0.5, x + 0.5, y + 1.5);
                StdDraw.show();
                StdDraw.pause(30);
                return true;
            }
        }
        if (!east[x][y])  {
            if(solve(x + 1, y)) {
                StdDraw.line(x + 0.5, y + 0.5, x + 1.5, y + 0.5);
                StdDraw.show();
                StdDraw.pause(30);
                return true;
            }
        }
        if (!south[x][y]) {
            if(solve(x, y - 1)) {
                StdDraw.line(x + 0.5, y + 0.5, x + 0.5, y - 0.5);
                StdDraw.show();
                StdDraw.pause(30);
                return true;
            }
        }
        if (!west[x][y])  {
            if(solve(x - 1, y)) {
                StdDraw.line(x + 0.5, y + 0.5, x - 0.5, y + 0.5);
                StdDraw.show();
                StdDraw.pause(30);
                return true;
            }
        }
        if (done) return true;

        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.filledCircle(x + 0.5, y + 0.5, 0.25);
        StdDraw.show();
        StdDraw.pause(30);
        return false;
    }

    // solve the maze starting from the start state
    public void solve() {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(n/2.0 + 0.5, n/2.0 + 0.5, 0.375);
        StdDraw.filledCircle(1.5, 1.5, 0.375);
        for (int x = 1; x <= n; x++)
            for (int y = 1; y <= n; y++)
                visited[x][y] = false;
        done = false;
        solve(1, 1);
    }
    
    public void solveInMST(int startX, int startY, int endX, int endY) {
        if(startX < 1 || startX > n || startY < 1 || startY > n || 
                endX < 1 || endX > n || endY < 1 || endY > n )
            throw new IllegalArgumentException();
        
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(startX + .5, startY + .5, 0.375);
        StdDraw.filledCircle(endX + .5, endY + .5, 0.375);
        
        int startIndex = startX * (n+2) + startY;
        int endIndex = endX * (n+2) + endY;
        
        StdDraw.setPenColor(StdDraw.BLUE);
        
        // 倒过来，从结束点开始 ，如果结束点是(1,1) 会有问题。
        while(startIndex != endIndex) {
            int ox = endIndex / (n+2);
            int oy = endIndex % (n+2);
            Edge e = edgeTo[ox][oy];
            
            // 存储结构的另一节点即是下一步
            int next = e.other(endIndex);
            endIndex = next;
            
            int nx = next / (n+2);
            int ny = next % (n+2);
            
            StdDraw.filledCircle(ox + 0.5, oy + 0.5, 0.25);
            StdDraw.show();
            StdDraw.pause(30);
            
            if (nx > ox) {
                StdDraw.line(ox + 0.5, oy + 0.5, nx + 0.5, ny + 0.5);
            } else if (nx < ox) {
                StdDraw.line(ox + 0.5, oy + 0.5, nx + 0.5, ny + 0.5);
            } else if (ny > oy) {
                StdDraw.line(ox + 0.5, oy + 0.5, nx + 0.5, ny + 0.5);
            } else if (ny < oy) {
                StdDraw.line(nx + 0.5, ny + 0.5, ox + 0.5, oy + 0.5);
            }
        }
        StdDraw.filledCircle(startX + 0.5, startY + 0.5, 0.25);
        StdDraw.show();
        StdDraw.pause(30);
        StdDraw.show();
    }
    
    // draw the maze
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int x = 1; x <= n; x++) {
            for (int y = 1; y <= n; y++) {
                if (south[x][y]) StdDraw.line(x, y, x+1, y);
                if (north[x][y]) StdDraw.line(x, y+1, x+1, y+1);
                if (west[x][y])  StdDraw.line(x, y, x, y+1);
                if (east[x][y])  StdDraw.line(x+1, y, x+1, y+1);
            }
        }
        StdDraw.show();
        StdDraw.pause(1000);
    }

    // a test client
    public static void main(String[] args) {
        Maze maze = new Maze(20);
        StdDraw.enableDoubleBuffering();
        maze.draw();
        StdDraw.setPenRadius(.006);
//        maze.solve();
        maze.solveInMST(1, 1, 20, 20);
    }

}