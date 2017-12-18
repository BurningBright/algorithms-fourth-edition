package class0401;

import stdlib.In;
import stdlib.StdDraw;

/**
 * @Description 4.1.37
 *          欧几里德图？
 * @author Leon
 * @date 2017-11-03 16:50:00
 */
public class EuclideanGraph extends Graph{
    
    private double[] x;
    private double[] y;
    
    public EuclideanGraph(int V) {
        super(V);
        x = new double[V];
        y = new double[V];
    }
    
    public EuclideanGraph(In in) {
        this(in.readInt()); // Read V and construct this graph.
        for (int i = 0; i < V(); i++) 
            setVertical(i, in.readDouble(), in.readDouble());
        
        int E = in.readInt(); // Read E.
        for (int i = 0; i < E; i++) 
            addEdge(in.readInt(), in.readInt());
    }
    
    public void setVertical(int i, double x, double y) {
        if (i < 0 || i >= V()) throw new IndexOutOfBoundsException();
        this.x[i] = x;
        this.y[i] = y;
    }
    
    public double getVerticalX(int i) {
        if (i < 0 || i >= V()) throw new IndexOutOfBoundsException();
        return this.x[i];
    }
    
    public double getVerticalY(int i) {
        if (i < 0 || i >= V()) throw new IndexOutOfBoundsException();
        return this.y[i];
    }
    
    public void show() {
        
        double xMax = .0;
        double yMax = .0;
        for (int i = 0; i < V(); i++) {
            if (x[i] > xMax)
                xMax = x[i];
            if (y[i] > yMax)
                yMax = y[i];
        }
        StdDraw.setXscale(0, xMax);
        StdDraw.setYscale(0, yMax);
        StdDraw.setPenRadius(.003);
        
        for (int i = 0; i < V(); i++) {
            StdDraw.point(x[i], y[i]);
//            StdDraw.text(x[i], y[i], i+"");
        }
        
        StdDraw.setPenRadius(.001);
        boolean[][] check = new boolean[V()][V()];
        for (int i = 0; i < V(); i++) {
            
            for (Integer j: adj(i)) {
                if(!check[i][j] && !check[j][i]) {
                    StdDraw.line(x[i], y[i], x[j], y[j]);
                    check[i][j] = check[j][i] = true;
                }
            }
            
        }
    }
    
    public double distance(int a, int b) {
        if (a < 0 || a >= V()) throw new IndexOutOfBoundsException();
        if (b < 0 || b >= V()) throw new IndexOutOfBoundsException();
        return Math.sqrt(Math.pow(Math.abs(x[a] - x[b]), 2) + 
                            Math.pow(Math.abs(y[a] - y[b]), 2));
    }
    
    public static void main(String[] args) {
        EuclideanGraph eg = new EuclideanGraph(new In("resource/4.1/euclidean.txt"));
        eg.show();
    }

}
