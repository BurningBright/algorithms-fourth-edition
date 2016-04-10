package class0201;

import rlgs4.Stopwatch;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * sorting arrays of random Double values 
 * ,using array sizes that are increasing 
 * powers of 2, is a small constant number
 * @author Chen
 *
 */
public class InstrumentShell {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static double shellDoubling(int N) {
		Comparable<Object>[] src = (Comparable[]) new Double[N];
		for (int i = 0; i < N; i++) {
			src[i] = (Comparable)StdRandom.uniform();
		}
		
		Stopwatch sw = new Stopwatch();
		ShellSort.sort(src);
		if(!ShellSort.isSorted(src)) {
			return .1;
		}
		return sw.elapsedTime();
	}

	public static void main(String[] args) {

		double pre = shellDoubling(100);
		StdOut.printf("%7.3f\n", pre);
		
		int N = 1000;
		for (int i = N; i < 100000001; i *= 2) {
			double cur = shellDoubling(i);
			StdOut.printf("%10d %7.3f %5.3f\n", i, cur, cur / pre);
			pre = cur;
		}
	}
}
