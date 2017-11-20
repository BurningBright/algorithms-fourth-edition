package class0401;

import java.util.Random;

import stdlib.StdOut;

/**
 * @Description 4.1.42
 *          随机欧几里德图，圆范围联通
 *          sqrt((lgV/pi) * V)
 *          V = 100 result about 17.84
 * @author Leon
 * @date 2017-11-03 16:50:00
 */
public class RandomEuclideanRadius {

    public static void main(String[] args) {
        double d = 18;
        int v = 100;
        
        EuclideanGraph eg = new EuclideanGraph(v);
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
                            // prevent parallel link
                            check[i][j] = check[j][i] = true;
                        }
                    }
                }
            }
        }
        
        eg.show();
        CC c = new CC(eg);
        StdOut.println(c.count());
    }

}
