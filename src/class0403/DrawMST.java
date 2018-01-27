package class0403;

import class0401.StdDraw;

/**
 * @Description 4.3.0/35
 *          page 615
 *          直接使用结构，不用组合结构
 * @author Leon
 * @date 2017-12-18 16:15:00
 */
public class DrawMST {
    
    public static void main(String[] args) {
        // create a weighted Random Euclidean graph
        double d = 28;
        int v = 250;
        /*
        EuclideanGraph eg = new EuclideanGraph(v);
        EdgeWeightedGraph ewg = new EdgeWeightedGraph(v);
        
        Random r = new Random();
        for(int i=0; i<v; i++) 
            eg.setVertical(i, r.nextDouble() * v, r.nextDouble() * v);
        
        boolean[][] check = new boolean[v][v];
        for(int i=0; i<v; i++) {
            for(int j=0; j<v; j++) {
                if(!check[i][j] && !check[j][i]) {
                    if(j != i) {
                        double dt = eg.distance(i, j);
                        if(dt <= d) {
                            StdOut.println(dt);
                            eg.addEdge(i, j);
                            ewg.addEdge(new Edge(i, j, dt, eg.getVerticalX(i), eg.getVerticalY(i), 
                                    eg.getVerticalX(j), eg.getVerticalY(j)));
                            // prevent parallel link
                            check[i][j] = check[j][i] = true;
                        }
                    }
                }
            }
        }
        */
        
        EdgeWeightedGraph eg = CaseGenerator.euclidean(v, d);
        
        StdDraw.setScale(0, v);
        
        // draw weighted graph
        StdDraw.setPenRadius(.003);
//        ewg.show();
        eg.show();
        
        StdDraw.setPenRadius(.005);
        
        /*
        // minimum spanning tree
        MST mst = new MST(ewg);
        mst.show();
        
        PrimMST mst = new PrimMST(ewg);
        mst.show();
        */
        
//        KruskalMST mst = new KruskalMST(ewg);
        KruskalMST mst = new KruskalMST(eg);
        mst.show();
        
    }
    
}
