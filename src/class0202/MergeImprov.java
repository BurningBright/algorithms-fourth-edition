package class0202;

import java.util.Arrays;

/**
 * 
 * describe:
 * 		one takes its input from the given array 
 * 		and puts the sorted output in the auxiliary array;
 * 		the other takes its input from the auxiliary array 
 * 		and puts the sorted output in the given array. 
 * date:	 20150402 9:47:18
 * @author:	 Chen
 *
 */
public class MergeImprov {
	
	private static int CUTOFF = 4;
	
	public static void sort(Comparable<Object>[] src) {
		//Comparable<Object>[] aux = new Comparable[a.length];
		Comparable<Object>[] aux = src.clone();
		sort(0, src.length-1, aux, src);
		assert isSorted(src);
	}
	
	public static void sort(int lo, int hi, Comparable<Object>[] src,
			Comparable<Object>[] aux) {
		/* lower than cutoff use insert directly */
		if(hi - lo <= CUTOFF) {
			insertForMerge(lo, hi, aux);
			return;
		}
		int mid = lo + (hi-lo)/2;
		//when mergeForth merged,aux became the src one
		sort(lo, mid, aux, src);
		sort(mid+1, hi, aux, src);
		
		if(less(src[mid], src[mid+1])) {
			System.arraycopy(src, lo, aux, lo, hi-lo+1);
			return;
		}
		
		mergeFourth(lo, mid, hi, src, aux);
	}
	
	public static void mergeFourth(int lo, int mid, int hi,
			Comparable<Object>[] src, Comparable<Object>[] aux
			) {
		int i=lo, j=mid+1;
		
		assert isSorted(src, lo, mid);
		assert isSorted(src, mid+1, hi);
		
		//System.out.print(i+" "+j+" "+hi);
		//System.out.println(Arrays.toString(src));
		for (int k = lo; k <= hi; k++) {
			if (i > mid) {
				aux[k] = src[j++];
			} else if (j > hi) {
				aux[k] = src[i++];
			} else if (less(src[i], src[j])) {
				aux[k] = src[i++];
			} else {
				aux[k] = src[j++];
			}
			
			//System.out.print(Arrays.toString(aux)+i+" "+j);
			//System.out.println(Arrays.toString(src));
		}
		
		assert isSorted(src, lo, hi);
	}
	
	
	/* optimized insertion sort */
	public static Comparable<Object>[] insertForMerge(int lo, int hi,
			Comparable<Object>[] src) {
		
		// find the minimal one first, as a sentinel
		/* 放置哨兵，一个个换太慢 干脆遍历一边再换 */
		int min = 0;
		for (int i = hi-1; i > lo; i--) {
//			if (less(src[i], src[i - 1])) {
//				exch(src, i, i - 1);
//			}
			if (less(src[i], src[i - 1]))
				min = i;
		}
		exch(src, 0, min);
		
		for (int i = lo+1; i <= hi; i++) {
			
			// variable j as the target flag
			int j = i;
			Comparable<Object> temp = src[i];
			// wouldn't out of aboard cause of sentinel is the smallest
			while (less(temp, src[j - 1])) {
				src[j] = src[j-1];
				j--;
			}
			src[j] = temp;
		}
		return src;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}
	
	private static void exch(Comparable<Object>[] a, int i, int j) {
		Comparable<Object> t = a[i];
		a[i] = a[j];
		a[j] = t;
	}
	
	public static boolean isSorted(Comparable<Object>[] a) {
		for (int i = a.length - 1; i > 0; i--) {
			if (less(a[i], a[i - 1]))
				return false;
		}
		return true;
	}

	public static boolean isSorted(Comparable<Object>[] a, int lo, int hi) {
		for (int i = hi; i > lo + 1; i--) {
			if (less(a[i], a[i - 1]))
				return false;
		}
		return true;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		Comparable[] a = new Comparable[]{"E","E","G","M","R","A","C","E","R","T"};
		MergeImprov.sort(a);
		System.out.println(Arrays.toString(a));
	}

}
