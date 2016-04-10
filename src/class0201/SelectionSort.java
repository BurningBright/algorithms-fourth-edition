package class0201;

import stdlib.In;
import stdlib.StdOut;

/**
 * One of the simplest sorting algorithms
 * 
 * @author Chen
 *
 */
public class SelectionSort {

	public static void sort(Comparable<Object>[] a) {
		int N = a.length;
		for (int i = 0; i < N; i++) {
			int min = i;
			for (int j = i + 1; j < N; j++) {
				if (less(a[j], a[min])) {
					min = j;
				}
			}
			exch(a, i, min);
//			show(a);
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
	 * Print the array, on a single line.
	 */
	@SuppressWarnings("unused")
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
		Comparable<Object>[] a = (Comparable[])In.readStrings("2.0/2.1/tiny1.txt");
		sort(a);
		assert isSorted(a);
//		show(a);
	}

}
