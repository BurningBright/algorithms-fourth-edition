package class0401;

import java.util.Random;

import stdlib.In;
import stdlib.StdDraw;
/**
 * @Description 4.1.0
 *          画 mediumG.txt
 * @author Leon
 * @date 2017-11-03 16:50:00
 */
public class DrawMedium {
    
    public static void main(String[] args) {
        In in = new In("resource/4.1/mediumG.txt");
        int v = in.readInt();
        int e = in.readInt();
        StdDraw.setPenRadius(.003);
        
        
        Random r = new Random();
        double [] coodinateX = new double[v];
        double [] coodinateY = new double[v];
        for (int i=0; i<v; i++) {
            coodinateX[i] = r.nextDouble();
            coodinateY[i] = r.nextDouble();
            StdDraw.point(coodinateX[i], coodinateY[i]);
        }
        /*
        for (int i=0; i<e; i++) {
            int a = in.readInt();
            int b = in.readInt();
            StdDraw.line(coodinateX[a], coodinateY[a], coodinateX[b], coodinateY[b]);
        }
        */
        StdDraw.setPenRadius(.001);
        boolean[][] check = new boolean[v][e];
        for (int i=0; i<e; i++) {
            int a = in.readInt();
            int b = in.readInt();
            if(!check[a][b] && !check[b][a]) {
                StdDraw.line(coodinateX[a], coodinateY[a], coodinateX[b], coodinateY[b]);
                check[a][b] = check[b][a] = true;
            }
        }
    }

}
