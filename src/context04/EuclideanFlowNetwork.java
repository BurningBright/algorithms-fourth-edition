package context04;

import stdlib.StdRandom;

/**
 * @Description context04.42
 *              欧几里德流图，其中边的容量符合高斯分布
 * @author Leon
 * @date 2018-05-15 13:20:00
 */
public class EuclideanFlowNetwork extends FlowNetwork{

    private double[] x;
    private double[] y;
    
    public EuclideanFlowNetwork(int V) {
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
    

    public static FlowNetwork createFlow(int v, int d) {
        
        EuclideanFlowNetwork flow = new EuclideanFlowNetwork(v);
        for(int i=0; i<v; i++)
            flow.setVertical(i, StdRandom.uniform() * v, StdRandom.uniform() * v);
        
        boolean[][] check = new boolean[v][v];
        for(int i=0; i<v; i++) {
            for(int j=0; j<v; j++) {
                if(!check[i][j] && !check[j][i]) {
                    if(j != i) {
                        double dt = flow.distance(i, j);
                        if(dt <= d) {
                            flow.addEdge(new FlowEdge(i, j, CapacityGenerator.gaussian()));
                            // prevent parallel link
                            check[i][j] = check[j][i] = true;
                        }
                    }
                }
            }
        }
        return flow;
    }
    
}
