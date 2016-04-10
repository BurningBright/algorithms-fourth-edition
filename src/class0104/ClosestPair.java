package class0104;

import java.util.Arrays;

import rlgs4.Stopwatch;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * two values whose difference is no greater
 * than the the difference of any other pair
 * @author soft01
 *
 */
public class ClosestPair {
	private double[] a;
	
	public ClosestPair(double[] src) {
		this.a = src;
		Arrays.sort(a);
	}
	
	public void compare() {
		int theNum = 0;
		double min = Math.abs(a[0]-a[1]);
		for(int i=1; i<a.length-1; i++) {
			double tmp;
			if((tmp=Math.abs(a[i]-a[i+1]))<min) {
				min = tmp;
				theNum = i;
			}
		}
		StdOut.print(theNum+"  "+a[theNum]+"|"+a[theNum+1]+"  ");
	}
	
	public static double timeTrial(int N) {
		/*prepare random data*/
		double MAX = 1;
		double[] a = new double[N];
		for (int i = 0; i < N; i++) {
			a[i] = StdRandom.uniform(-MAX, MAX);
		}
		Stopwatch timer = new Stopwatch();
		
		new ClosestPair(a).compare();
		return timer.elapsedTime();
	}
	
	public static void main(String[] args) {
		for (int N = 2500; N < 90000; N += N) {
			double time = timeTrial(N);
			StdOut.printf("%7d %5.3f\n", N, time);
		}
	}

}
