package class0201;

import java.lang.reflect.Method;

import rlgs4.Stopwatch;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * describe: 
 * 		compare sort methods' time performance,
 * 		when input parameter is double increase.
 * 		
 * date:	 20150127 9:33:35
 * @author:	 Chen
 *
 */
public class SortCompare {

	@SuppressWarnings("rawtypes")
	public static double time(String alg, String met, Comparable[] a) {
		Stopwatch watch = new Stopwatch(); 
		try {
			Method src = Class.forName(alg)
					.getMethod(met, Comparable[].class);
			src.invoke(null, (Object)a);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return watch.elapsedTime();
	}

	public static double timeRandomInput(String alg, String met, int N, int T) {
		// Use alg to sort T random arrays of length N.
		double total = 0.0;
		Double[] a = new Double[N];
		for (int t = 0; t < T; t++) { 
			// Perform one experiment (generate and sort an array).
			for (int i = 0; i < N; i++)
				a[i] = StdRandom.uniform();
			total += time(alg, met, a);
		}
		return total;
	}

	public static void main(String[] args) {
		String alg1 = "class0201.InsertionSort";
//		String alg2 = "class0201.SelectionSort";
//		String alg3 = "class0201.ShellSort";
		
		String met1 = "sort";
		String met2 = "optimizedOneSort";
		String met3 = "optimizedTwoSort";
		String met4 = "optimizedThreeSort";
		
		int N = 20000;
		int T = 10;
		double t1 = timeRandomInput(alg1, met1, N, T);	//insertion
		double t2 = timeRandomInput(alg1, met2, N, T);
		double t3 = timeRandomInput(alg1, met3, N, T);
		double t4 = timeRandomInput(alg1, met4, N, T);
		
		//double t5 = timeRandomInput(alg2, met1, N, T);	//selection
		//double t6 = timeRandomInput(alg3, met1, N, T);	//shell
		
		
		StdOut.printf("For %d random Doubles\n %s is", N, met2);
		StdOut.printf(" %.3f times faster than %s\n", t1 / t2, met1);
		StdOut.printf("For %d random Doubles\n %s is", N, met3);
		StdOut.printf(" %.3f times faster than %s\n", t2 / t3, met2);
		StdOut.printf("For %d random Doubles\n %s is", N, met4);
		StdOut.printf(" %.3f times faster than %s\n", t3 / t4, met3);
		StdOut.printf(" %s is %.3f %s is %.3f\n", met1, t1, met2, t2);
		StdOut.printf(" %s is %.3f %s is %.3f", met3, t3, met4, t4);
		
		/*
		StdOut.printf("selection 		%.3f \n", t5);
		StdOut.printf("insertion 		%.3f	%.3f \n", t1, t5/t1);
		StdOut.printf("insertion update	%.3f	%.3f \n", t4, t1/t4);
		StdOut.printf("shell	 		%.3f	%.3f \n", t6, t4/t6);
		*/
	}

}
