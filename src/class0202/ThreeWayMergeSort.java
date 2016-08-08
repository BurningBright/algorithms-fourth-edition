package class0202;

import java.util.Arrays;

import stdlib.StdOut;

/**
 * 
 * describe: sort each third, and combine using a 3-way merge
 * date:	 2015年10月14日 上午9:23:38
 * @author:	 Chen
 *
 */
public class ThreeWayMergeSort {
	
	@SuppressWarnings({"rawtypes", "unchecked"})
	public static void sort(Comparable<Object>[] a) {
		int N = a.length;
		Comparable[] aux = new Comparable[N];
		
		for (int sz = 1; sz < N; sz += sz + sz) {
			for (int lo = 0; lo < N - sz; lo += 3 * sz) {
				merge(lo, lo+sz-1, Math.min(lo+2*sz-1, N-1), Math.min(lo+3*sz-1, N-1), a, aux);
			}
		}
	}
	
	public static void merge(int lo, int m1, int m2, int hi, Comparable<Object>[] a, Comparable<Object>[] aux) {
//		StdOut.println("---" + lo + " " + m1 + " " + m2 + " " + hi);
		int i = lo, j1 = m1 + 1, j2 = m2+1;
		
		/* copy all current array elements */
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k];
		}
		
		for (int k = lo; k <= hi; k++) {
//			StdOut.println("+++"+i + " " + j1 + " " + j2);
			
			if (i > m1 && j1 > m2) {
				a[k] = aux[j2++];
			} else if (i > m1 && j2 > hi) {
				a[k] = aux[j1++];
			} else if (j1 > m2 && j2 > hi) {
				a[k] = aux[i++];
			} else if (i > m1) {	// 第一部分已完成比较后两部分
//				StdOut.println(j2 + " " + j1 + "[1");
				a[k] = less(aux[j2], aux[j1])? aux[j2++]: aux[j1++];
			} else if (j1 > m2) {	// 第二部分已完成
//				StdOut.println(j2 + " " + i + "[2");
				a[k] = less(aux[j2], aux[i])? aux[j2++]: aux[i++];
			} else if (j2 > hi) {	// 第三部分已完成
//				StdOut.println(j1 + " " + i + "[3");
				a[k] = less(aux[j1], aux[i])? aux[j1++]: aux[i++];
			} else if (less(aux[j1], aux[i]) || less(aux[j2], aux[i])) {	// 都没完成，且i不是最小
//				StdOut.println(j1 + " " + j2 + "[4");
				a[k] = less(aux[j2], aux[j1])? aux[j2++]: aux[j1++];
			} else {	// 都没完成，且i最小
//				StdOut.println(i + "[5");
				a[k] = aux[i++];
			}
			
//			StdOut.print(Arrays.toString(aux));
//			StdOut.println(Arrays.toString(a));
		}
//		StdOut.println();
		
	}
	
	private static boolean less(Comparable<Object> v, Comparable<Object> w) {
		return v.compareTo(w) < 0;
	}
	
	private static boolean isSorted(Comparable<Object>[] a) {
		for (int i = 1; i < a.length; i++)
			if (less(a[i], a[i - 1]))
				return false;
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String in = "jfoqiaewgpoajidnebvfpoaueihro90qa";
		Comparable<Object>[] src = new Comparable[in.length()];
		
		for(int i=0; i<in.length(); i++) {
			src[i] = (Comparable<Object>) in.subSequence(i, i+1);
		}
		
//		Comparable<Object>[] src = new Comparable[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
		
		sort(src);
		
		StdOut.println(Arrays.toString(src));
		assert isSorted(src);
	}

}
