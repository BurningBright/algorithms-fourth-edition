package class0201;

import rlgs4.Stopwatch;
import stdlib.StdRandom;
/**
 * Obviously insertion sort 
 * in three value is quadratic
 * @author Chen
 *
 */
public class InsertionInThree {

	public static void sort(Comparable<Object>[] a) {
		int N = a.length;
		for (int i = 1; i < N; i++) {
			/*
			 * push a[i] to the vacated position
			 */
			for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
				exch(a, j, j - 1);
			}
		}
	}

	private static boolean less(Comparable<Object> v, Comparable<Object> w) {
		return v.compareTo(w) < 0;
	}

	private static void exch(Comparable<Object>[] a, int i, int j) {
		Comparable<Object> t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	/*
	 * Test whether the array entries are in order.
	 */
	public static boolean isSorted(Comparable<Object>[] a) {
		for (int i = 1; i < a.length; i++)
			if (less(a[i], a[i - 1]))
				return false;
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void general(int N) {

		Comparable<Object>[] data = (Comparable[]) new Integer[N];

		for (int i = 0; i < N; i++) {
			data[i] = (Comparable) StdRandom.uniform(3);
		}
		
		Stopwatch sw = new Stopwatch();
		sort(data);
		if(isSorted(data)) {
			System.out.print("OK ");
		}
		System.out.println(sw.elapsedTime());
	}

	public static void main(String[] args) {
		for (int i = 1000; i < 1000000; i *= 2) {
			general(i);
		}
	}

}
