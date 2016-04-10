package class0104;

import rlgs4.Stopwatch;
import stdlib.StdDraw;
import stdlib.StdOut;
import stdlib.StdRandom;

public class ThreeSumModel {
	private static int[] a;
	private static int N;
	
	public static void drawAll(int[] a) {
		double Max, Min;
		Max=Min=0;
		Stopwatch timer = new Stopwatch();
		for (int i = 0; i < N; i++) {
			for (int j = i +1; j < N; j++) {
				for (int k = j +1; k < N; k++) {
					double step = i+j+k-100+i;
					double sum = (a[i] +a[j] +a[k])/30000;
					StdDraw.circle(step, sum, 0.1);
					Max = Max>sum? Max:sum;
					Min = Min<sum? Min:sum;
				}
			}
		}
		StdOut.printf("%5.3f ", timer.elapsedTime());
		StdOut.println(Max+" "+Min);
	}

	public static void main(String[] args) {
		StdDraw.setScale(-100.0, 100.0);
		StdDraw.line(-100, 0, 100, 0);
		StdDraw.line(0, 100, 0, -100);
		
		N=50;
		int Max = 1000000;
		a = new int[N];
		for (int i = 0; i < N; i++) {
			a[i] = StdRandom.uniform(-Max, Max);
		}
		drawAll(a);
	}
}
