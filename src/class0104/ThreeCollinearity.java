package class0104;

import rlgs4.Stopwatch;
import stdlib.StdDraw;
import stdlib.StdOut;

/**
 * Use algebra to show that (a, a3), (b, b3), and (c, c3)
 * are collinear if and only if a + b + c = 0.
 * @author soft01
 *
 */
public class ThreeCollinearity {
	public static void main(String[] args) {
		int N = -100;
		StdDraw.setScale(-100.0, 100.0);
		StdDraw.line(-100, 0, 100, 0);
		StdDraw.line(0, 100, 0, -100);
		for(int i=N; i<-N; i++) {
			double k=Math.pow(i, 3)/10000;
			StdDraw.circle(i, k, 0.1);
		}
		
		Stopwatch timer = new Stopwatch();
		int cnt = 0;
		for(int i=N; i<-N; i++) {
			for(int j=i; j<-N; j++) {
				for(int k=j; k<-N; k++) {
					if((i+j+k)==0) {
						cnt++;
					}
				}
			}
		}
		StdOut.println(cnt+" "+timer.elapsedTime());
	}
}
