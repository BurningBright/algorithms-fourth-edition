package class0104;

import rlgs4.DoublingTest;
import rlgs4.Stopwatch;
import stdlib.StdDraw;
import stdlib.StdOut;
/**
 * sum three number from an array
 * give a result
 * @author soft01
 *
 */
public class DrawDoubleTest {

	public static void main(String[] args) {
		final double[] a = new double[1];
		
//		StdDraw.arc(0.5, 0.5, 0.3, (90-end), 90, 2);
//		StdDraw.filledArc(0.5, 0.5, 0.3,(90-end) , 90, 2);
		
		new Thread(new Runnable() {
			Stopwatch timer = new Stopwatch();
			int circle = 0;
			
			public void run() {
				StdDraw.clear();
				StdDraw.setPenColor(0, 0, 0);
				StdDraw.circle(0.5, 0.5, 0.3);
				StdDraw.setPenColor(191, 46, 27);
				while(a[0]==0) {
					Double current = timer.elapsedTime();
//					StdOut.println(current);
					int minute = (int)(current/60);
					
					StdDraw.filledRectangle(0.5, 0.9, 0.20, .02);
					StdDraw.setPenColor(0, 0, 0);
					StdDraw.textLeft(0.4, 0.9, String.valueOf(minute));
					String seconds = String.valueOf(current%60);
					seconds = seconds.substring(0, seconds.indexOf(".")+2);
					StdDraw.textLeft(0.5, 0.9, seconds);
					StdDraw.setPenColor(191, 46, 27);
					
					double end = (current%60)/60*360;
					
					if(minute>circle) {
						circle++;
						run();
						return;
					}
					StdDraw.filledArc(0.5, 0.5, 0.3,(90-end) , 90, 2);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				int N=12000;
				a[0] = DoublingTest.timeTrial(N);
				StdOut.printf("%7d %5.1f\n", N, a[0]);
			}
		}).start();
	}
	

}
