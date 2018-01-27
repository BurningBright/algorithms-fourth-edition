package class0404;

public class EuclideanEdgeWeightedDigraph extends EdgeWeightedDigraph{

    private double[] x;
    private double[] y;
    
    public EuclideanEdgeWeightedDigraph(int V) {
        super(V);
        x = new double[V];
        y = new double[V];
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
    
    public double distance(int a, int b) {
        if (a < 0 || a >= V()) throw new IndexOutOfBoundsException();
        if (b < 0 || b >= V()) throw new IndexOutOfBoundsException();
        return Math.sqrt(Math.pow(Math.abs(x[a] - x[b]), 2) + 
                            Math.pow(Math.abs(y[a] - y[b]), 2));
    }
    
}
