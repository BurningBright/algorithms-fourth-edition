package class0201;

import stdlib.StdDraw;
import stdlib.StdRandom;
/**
 * @Description 2.1.35
 * @author Leon
 * @date 2016-07-28 14:46:09
 */
public class DrawDistributions {
    
    public static void gaussian(int N) {
        StdDraw.setScale(-3.0, 3.0);
        StdDraw.line(-3, 0, 3, 0);
        StdDraw.line(0, 3, 0, -3);
        for(int i=0; i<N; i++) {
            StdDraw.circle(StdRandom.gaussian(), StdRandom.uniform(-2.5, 2.5), 0.03);
        }
    }
    
    public static void poisson(int N) {
        StdDraw.setYscale(-5, 5);
        StdDraw.setXscale(-1, 9);
        StdDraw.line(-1, 0, 9, 0);
        StdDraw.line(0, 5, 0, -5);
        for(int i=0; i<N; i++) {
            StdDraw.circle(StdRandom.poisson(4.0), StdRandom.uniform(-4.0, 4.0), 0.1);
        }
        StdDraw.line(4, 5, 4, -5);
    }
    
    public static void geometric(int N) {
        StdDraw.setYscale(-5, 5);
        StdDraw.setXscale(-1, 14);
        StdDraw.line(-1, 0, 14, 0);
        StdDraw.line(0, 5, 0, -5);
        for(int i=0; i<N; i++) {
            StdDraw.circle(StdRandom.geometric(.2), StdRandom.uniform(-4.0, 4.0), 0.1);
        }
        StdDraw.line(5, 5, 5, -5);
    }
    
    public static void main(String[] args) {
//        gaussian(3000);
        poisson(300);
//        geometric(300);
    }

}
