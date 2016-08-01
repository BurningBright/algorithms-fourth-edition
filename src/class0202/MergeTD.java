package class0202;

import java.util.Arrays;

/**
 * describe: 
 * 		this is top to down merge method
 * 		from hole size to size 1 recursive return
 * 		merged array.until hole array merged.
 * date:	 20150328 23:04:04
 * @author:	 Chen
 *
 */
public class MergeTD {
	
	/* auxiliary array for merges */
	public static Comparable<Object>[] aux;
	
	@SuppressWarnings("unchecked")
	public static void sort(Comparable<Object>[] a) {
		/* initial the template array */
		aux = new Comparable[a.length];
		sort(0, a.length-1, a);
	}
	
	/*
	 * this method will recursively solve
	 * big part to little part sort problem.
	 * (Top to down solve)
	 */
	public static void sort(int lo, int hi, Comparable<Object>[] b) {
		/*
		 * if only one element in current array,
		 * we thought it's was sorted .
		 */
		if (lo >= hi) {
			return;
		}
		
		int mid = lo + (hi - lo) / 2;		/* got the middle one */
		sort(lo, mid, b);					/* sort the left part */
		sort(mid + 1, hi, b);				/* sort the right part */
		mergeFirst(lo, mid, hi, b);			/* merge two part */
	}
	
	public static void mergeFirst(int lo, int mid, int hi, Comparable<Object>[] c) {

		int i = lo, j = mid + 1;
		
		for (int k = lo; k <= hi; k++) {		/* copy all current array elements */
			aux[k] = c[k];
		}
		
		for (int k = lo; k <= hi; k++) {
			
			if (i > mid) {						/* if left part already be merged */
				c[k] = aux[j++];
			} else if (j > hi) {				/* if right part already be merged */
				c[k] = aux[i++];
			} else if (less(aux[j], aux[i])) {	/* if right one less than left one */
				c[k] = aux[j++];
			} else {							/* if right one bigger than left one */
				c[k] = aux[i++];
			}
			//System.out.println(Arrays.toString(c));
		}
	}
	
	private static boolean less(Comparable<Object> v, Comparable<Object> w) {
		return v.compareTo(w) < 0;
	}
	
	public static boolean isSorted(Comparable<Object>[] a) {
		for (int i = 1; i < a.length; i++)
			if (less(a[i], a[i - 1]))
				return false;
		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		//Comparable[] a = new Comparable[]{"E","E","G","M","R","A","C","E","R","T"};
		Comparable[] a = new Comparable[]{"E","A","S","Y","Q","U","E","S","T","I","O","N"};
		MergeTD.sort(a);
		//MergeBU.sort(a);
		System.out.println(Arrays.toString(a));
		assert isSorted(a);
	}

}
