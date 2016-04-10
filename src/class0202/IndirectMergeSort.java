package class0202;

import java.util.Arrays;

/**
 * describe: 
 * 		returns an int[] array perm such that perm[i] 
 * 		is the index of the i th smallest entry in the array
 * date:	 2015年10月8日 下午5:05:53
 * @author:	 Chen
 *
 */
public class IndirectMergeSort {
	
	public static void sort(Comparable<Object>[] src, int[] perm) {
		int size = src.length;
		
		// step interval
		for (int sz = 1; sz < size; sz += sz) {
			// low index flag
			for (int lo = 0; lo< size - sz; lo += sz * 2) {
				// make sure would't miss the end sort
				//System.out.println(lo+" "+(lo+sz-1)+" "+Math.min(lo+sz*2-1, size-1));
				//for (int i :perm){System.out.print(src[i]+"|"+i+"	");}
				//System.out.println();
				mergeSecond(lo, lo+sz-1, Math.min(lo+sz*2-1, size-1), src, perm);
				//for (int i :perm){System.out.print(src[i]+"|"+i+"	");}
				//System.out.println();
			}
		}
	}
	
	public static void mergeSecond(int lo, int mid, int hi,
			Comparable<Object>[] src, int[] perm) {
		// left flag	right flag
		int i = lo, j = mid+1;
		int[] tmp = new int[hi-lo+1];	// a temple space
		
		for(int k = lo, f=0; k <= hi; k++,f++) {
			if(i > mid) {				// left part already be merged, do nothing
				tmp[f] = perm[j++];
			} else if(j > hi) {			// right part already be merged, do nothing
				tmp[f] = perm[i++];
			} else if(less(src[perm[j]], src[perm[i]])) {
										// right one less than left one, exchange index
				tmp[f] = perm[j++];
			} else {					// right one bigger than left one
				tmp[f] = perm[i++];
			}
		}
		
		// copy back
		for(int k = lo, f=0; k <= hi; k++,f++) {
			perm[k] = tmp[f];
		}
	}
	
	public static boolean less(Comparable<Object> v, Comparable<Object> w) {
		return v.compareTo(w) < 0;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String in = "jfoqiaewgpoajidnebvfpoaueihro90qa";
		//String in = "jfoqiae";
		int[] perm = new int[in.length()];
		
		Comparable<Object>[] src = new Comparable[in.length()];
		for(int i=0; i<in.length(); i++) {
			src[i] = (Comparable<Object>) in.subSequence(i, i+1);
			perm[i] = i;
		}
		sort(src, perm);
		
		System.out.println(Arrays.toString(src));
		System.out.println(Arrays.toString(perm));
		for (int i : perm) {
			System.out.print(src[i]+" ");
		}
		
		// 断言已排序
		assert class0202.MergeImprov.isSorted(src);
	}

}
