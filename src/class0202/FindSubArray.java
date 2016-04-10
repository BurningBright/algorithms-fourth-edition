package class0202;

import stdlib.StdRandom;
/**
 * describe:
 * 		find out how many subarries be created
 * 		in mergeTD and mergeBU 
 * date:	 20150401 13:12:40
 * @author:	 Chen
 *
 */
public class FindSubArray {
	
	private static Comparable<Object>[] aux;
	
	private static int mergeTd(int lo, int hi, Comparable<Object>[] a) {
		if(lo>=hi) {
			return 0;
		}
		int mid = lo + (hi - lo)/2;
		int left = mergeTd(lo, mid, a);
		int right = mergeTd(mid+1, hi, a);
		mergeSecond(lo, mid, hi, a);
		return left+right+1;
	}
	
	private static int mergeBu(Comparable<Object>[] a) {
		int size = a.length;
		int flag = 0;
		for(int sz=1; sz<size-1; sz+=sz) {
			for(int lo=0; lo<size-sz; lo+=sz*2) {
				mergeSecond(lo, lo+sz-1, Math.min(size-1, lo+2*sz-1), a);
				flag++;
			}
		}
		return flag;
	}
	
	public static void mergeSecond(int lo, int mid, int hi, Comparable<Object>[] a) {
		int i=lo, j = mid+1;
		for(int k=lo; k<a.length; k++) {
			aux[k] = a[k];
		}
		
		for(int k=lo; k<hi; k++) {
			if(i > mid) {
				a[k] = aux[j++];
			} else if(j > hi) {
				a[k] = aux[i++];
			} else if(less(aux[i], aux[j])) {
				a[k] = aux[i++];
			} else {
				a[k] = aux[j++];
			}
		}
	}
	
	private static boolean less(Comparable<Object> v, Comparable<Object> w) {
		return v.compareTo(w) < 0;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		int N = 39;
		aux = new Comparable[N];
		
		Comparable<Object>[] src = (Comparable[]) new Double[N];
		for (int i = 0; i < N; i++) {
			src[i] = (Comparable)(StdRandom.uniform());
		}
		
		System.out.println(mergeTd(0, N-1, src));
		System.out.println(mergeBu(src));
	}

}
