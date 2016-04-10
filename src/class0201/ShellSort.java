package class0201;

import stdlib.In;
import stdlib.StdOut;
/**
 * shell sort in insertion sort
 * every h-sort as a insertion sort example
 * make list sorted from big to 1,make list
 * sorted.shell sort can reduce to movement 
 * between elements.
 * @author Chen
 *
 */
public class ShellSort {

	public static void sort(Comparable<Object>[] a) {
		int h = 1;
		int N = a.length;
		while (h < N / 3) {
			h = 3 * h + 1;
		}
		while (h >= 1) {
			for (int i = h; i < N; i++) {
				for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
					exch(a, j, j - h);
					// show(a);
				}
				// show(a);
			}
			h /= 3;
		}
	}

	static boolean less(Comparable<Object> v, Comparable<Object> w) {
		return v.compareTo(w) < 0;
	}

	static void exch(Comparable<Object>[] a, int i, int j) {
		Comparable<Object> t = a[i];
		a[i] = a[j];
		a[j] = t;
	}

	/*
	 * Print the array, on a single line.
	 */
	private static void show(Comparable<Object>[] a) {
		for (int i = 0; i < a.length; i++)
			StdOut.print(a[i] + " ");
		StdOut.println();
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

	public static void main(String[] args) {
		@SuppressWarnings({ "unchecked", "deprecation" })
		Comparable<Object>[] a = (Comparable[]) In
				.readStrings("2.0/2.1/tiny2.txt");
		sort(a);
		assert isSorted(a);
		show(a);
	}

}
