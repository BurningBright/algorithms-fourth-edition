package class0202;

import java.util.Arrays;

/**
 * 
 * describe: 
 * 		this is button to up merge method,
 * 		from size 1 to hole array.
 * 		the loop loop the array from 1 to the
 * 		size not bigger than array size.
 * 		loop until hole array merged.
 * 
 * date:	 20150328 23:00:29
 * @author:	 Chen
 *
 */
public class MergeBU {
	
	@SuppressWarnings("unchecked")
	public static void sort(Comparable<Object>[] a) {
		/*
		 * set the template array's length
		 */
		int size = a.length;
		MergeTD.aux = new Comparable[size];
		
		for (int sz = 1; sz < size; sz += sz) {
			/*
			 * make the boundary at the end minus step 
			 */
			for (int lo = 0; lo < size - sz; lo += 2 * sz) {
				/*
				 * do the merge operation
				 * the hi flag is not bigger than size-1 
				 * 
				 * choose mini one then the last time loop
				 * would't miss the end few elements.
				 */
				MergeTD.mergeFirst(lo, lo+sz-1, Math.min(lo+2*sz-1, size-1), a);
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		Comparable[] a = new Comparable[]{"E","E","G","M","R","A","C","E","R","T"};
		MergeBU.sort(a);
		System.out.println(Arrays.toString(a));
		assert MergeTD.isSorted(a);
	}
}
