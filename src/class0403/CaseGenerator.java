package class0403;

import java.util.Random;

import class0404.DirectedEdge;
import class0404.EdgeWeightedDigraph;
import class0404.EuclideanEdgeWeightedDigraph;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 4.3.25
 *      生成最糟案例，但感觉还是没到非线性
 *      lazy    ElogE   全联通
 *      eager   ElogV   扇形MST?
 *      可以试试欧几里得联通
 * @author Leon
 * @date 2017-12-27 10:00:00
 */
public class CaseGenerator {
    
    public static void lazy(int v) {
        for (int i=0; i<v; i++) {
            for (int j=i+1; j<v; j++) 
                StdOut.printf("%d - %d %.5f \r\n", i, j, StdRandom.uniform());
        }
    }
    
    public static void eager(int v) {
        for (int i=1; i<v; i++) 
            StdOut.printf("%d - %d %.5f \r\n", 0, i, StdRandom.uniform());
    }
    
    /**
     * @param v 结点数
     * @param p 百分比
     * @return 权重图
     * P > sqrt((lgV/pi) * V)
     * 权重图联通
     */
    public static EdgeWeightedGraph euclideanByPercent(int v, double p) {
        double threshold = Math.sqrt(Math.log10(v) / Math.PI * v);
        double d = threshold + (v - threshold) * p;
        return euclidean(v, d);
    }
    
    /**
     * 欧几里德无向权重图
     * @param v 节点个数
     * @param d 联通半径
     * @return 无向权重图
     */
    public static EdgeWeightedGraph euclidean(int v, double d) {
        EuclideanEdgeWeightedGraph eg = new EuclideanEdgeWeightedGraph(v);
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
                            eg.addEdge(new Edge(i, j, eg.distance(i, j), 
                                    eg.getVerticalX(i), eg.getVerticalY(i), 
                                    eg.getVerticalX(j), eg.getVerticalY(j)));
                            // prevent parallel link
                            check[i][j] = check[j][i] = true;
                        }
                    }
                }
            }
        }
        return eg;
    }
    
    /**
     * 欧几里德有向权重图
     * @param v 节点个数
     * @param d 联通半径
     * @return 有向权重图
     * 
     */
    public static EdgeWeightedDigraph euclideanInDigraph(int v, double d) {
        EuclideanEdgeWeightedDigraph eg = new EuclideanEdgeWeightedDigraph(v);
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
                            eg.addEdge(new DirectedEdge(i, j, eg.distance(i, j), 
                                    eg.getVerticalX(i), eg.getVerticalY(i), 
                                    eg.getVerticalX(j), eg.getVerticalY(j)));
                            eg.addEdge(new DirectedEdge(j, i, eg.distance(i, j), 
                                    eg.getVerticalX(j), eg.getVerticalY(j), 
                                    eg.getVerticalX(i), eg.getVerticalY(i)));
                            // prevent parallel link
                            check[i][j] = check[j][i] = true;
                        }
                    }
                }
            }
        }
        return eg;
    }
    
    public static void main(String[] args) {
//        lazy(5);
        eager(5);
    }

}
