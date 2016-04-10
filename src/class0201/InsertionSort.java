package class0201;

import stdlib.In;
import stdlib.StdOut;

/**
 * As in selection sort, the items to the left of the current index are in
 * sorted order during the sort, but they are not in their final position, as
 * they may have to be moved to make room for smaller items encountered later.
 * The array is, however, fully sorted when the index reaches the right end.
 * 
 * @author Chen
 *
 */
public class InsertionSort {

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

	public static void optimizedOneSort(Comparable<Object>[] a) {
		int N = a.length;

		// as a sentinel
		for (int i = N - 1; i > 0; i--) {
			if (less(a[i], a[i - 1])) {
				exch(a, i, i - 1);
			}
		}
		for (int i = 2; i < N; i++) {
			int j = i;
			// wouldn't out of aboard cause of sentinel is the smallest
			while (less(a[j], a[j - 1])) {
				exch(a, j, j - 1);
				j--;
			}
		}
	}

	public static void optimizedTwoSort(Comparable<Object>[] a) {
		int N = a.length;
		for (int i = 1; i < N; i++) {
			/*
			 * attack programming? anticipate the conditions in sort
			 */
			Comparable<Object> temp = a[i];
			int target = i;
			for (int j = i; j > 0 && less(temp, a[j - 1]); j--) {
				a[j] = a[j - 1];
				target = j - 1;
			}
			a[target] = temp;
			// show(a);
		}
	}

	public static void optimizedThreeSort(Comparable<Object>[] a) {
		int N = a.length;

		// as a sentinel
		for (int i = N - 1; i > 0; i--) {
			if (less(a[i], a[i - 1])) {
				exch(a, i, i - 1);
			}
		}
		for (int i = 2; i < N; i++) {
			
			// variable j as the target flag
			int j = i;
			Comparable<Object> temp = a[i];
			// wouldn't out of aboard cause of sentinel is the smallest
			while (less(temp, a[j - 1])) {
				a[j] = a[j-1];
				j--;
			}
			a[j] = temp;
			
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
				.readStrings("2.0/2.1/tiny1.txt");
		// sort(a);
		optimizedThreeSort(a);
		assert isSorted(a);
		show(a);
	}

}
