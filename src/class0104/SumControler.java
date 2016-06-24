package class0104;

import rlgs4.Stopwatch;
import rlgs4.ThreeSum;
import stdlib.StdOut;
import stdlib.StdRandom;

public class SumControler {
	public static double timeTrial(int N) {
		/*prepare random data*/
		int MAX = 1000000;
		int[] a = new int[N];
		for (int i = 0; i < N; i++) {
			a[i] = StdRandom.uniform(-MAX, MAX);
		}
		Stopwatch timer = new Stopwatch();
		
		/*start count*/
//		int cnt = new TwoSumFaster(a).count();
		int cnt = new ThreeSumFaster(a).count();
		
		/*much more slower*/
//		int cnt = new TwoSumMatrix(a).count();
//		int cnt = new ThreeSumMatrix(a).count();
		
		/*slowest*/
//		int cnt = ThreeSum.count(a);
		
		/*FibonacciSearch*/
//		new FibonacciSearch(a);
		
		StdOut.print(cnt +" ");
		return timer.elapsedTime();
	}
	
	public static void main(String[] args) {
		/*implementation start with doubling N*/
		for (int N = 2500; N < 41000; N += N) {
			double time = timeTrial(N);
			StdOut.printf("%7d %5.3f\n", N, time);
		}
	}
}
