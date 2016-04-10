package class0104;

import rlgs4.ResizingArrayBag;
import rlgs4.Stopwatch;
import stdlib.StdOut;
import stdlib.StdRandom;

public class DoublingRatio {

	public static double timeTrial(int N) {
		int MAX = 1000000;
		ResizingArrayBag<Integer> a = new ResizingArrayBag<Integer>();
//		Bag<Integer> a = new Bag<Integer>();
		Stopwatch timer = new Stopwatch();
		for (int i = 0; i < N; i++) {
			a.add(StdRandom.uniform(-MAX, MAX));
		}
		return timer.elapsedTime();
	}
	public static void main(String[] args) {
		double prev = timeTrial(5000);
		for (int N = 10000; N < 10250000; N += N) {
			double time = timeTrial(N);
			StdOut.printf("%6d %7.3f %5.1f\n", N, time, time / prev);
			prev = time;
		}
	}

}
