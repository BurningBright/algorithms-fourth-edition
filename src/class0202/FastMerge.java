package class0202;

import java.util.Arrays;

/**
 * 
 * describe: 
 * 		copies the second half of a[] to aux[] 
 * 		in decreasing order and then does the 
 * 		merge back to a[].this change can prevent
 * 		the halves has been exhausted.
 * date:	 20150401 11:15:30
 * @author:	 Chen
 *
 */
public class FastMerge {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void sort(Comparable<Object>[] a) {
		int size = a.length;
		Comparable[] aux = new Comparable[size];
		for(int sz = 1; sz<size; sz+=sz) {
			for(int lo = 0; lo<size-sz; lo+=2*sz) {
				mergeThird(lo, lo+sz-1, Math.min(lo+2*sz-1, size-1), a, aux);
			}
		}
	}
	
	public static void mergeThird(int lo, int mid, int hi,
			Comparable<Object>[] a, Comparable<Object>[] aux) {
		int i = lo, j = mid + 1;
		for (int k = lo; k <= mid; k++)
			aux[k] = a[k];

		for (int k = hi; k > mid; k--)
			aux[j++] = a[k];
		System.out.print(Arrays.toString(aux)+ lo +" "+ mid +" "+ hi);
		j--; // set to end
		
		// 第二部分倒排后，越界即是目标对象
		for (int k = lo; k <= hi; k++) {
			if (less(aux[i], aux[j])) {
				a[k] = aux[i++];
			} else {
				a[k] = aux[j--];
			}
		}
		System.out.println(Arrays.toString(a));
		
	}
	
	private static boolean less(Comparable<Object> v, Comparable<Object> w) {
		return v.compareTo(w) < 0;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		Comparable[] a = new Comparable[]{"E","E","G","M","R","A","C","E","R","T"};
		sort(a);
		System.out.println(Arrays.toString(a));
	}

}
