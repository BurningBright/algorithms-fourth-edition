package class0202;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * describe:
 * 		a natural mergesort for linked lists
 * 		top to down
 * date:	 2015年5月26日 下午5:01:06
 * @author:	 Chen
 *
 */
public class NaturalMergeInLinked {
	
	/* data preparation */
	public static void sort(Comparable<Object>[] src) {
		LinkedList<Comparable<Object>> tmp = new LinkedList<Comparable<Object>>();
		for(int i=0; i<src.length; i++) {
			tmp.add(src[i]);
		}
		sort(tmp, 1);
		
		System.out.println(Arrays.toString(tmp.toArray()));
	}
	
	/**
	 * @param src		data source
	 * @param scale		scale size
	 */
	private static void sort(LinkedList<Comparable<Object>> src,
			int scale) {
		/* key point */
		if(scale == src.size()) {
			return;
		}
		
		/* find the left part */
		int l_end = finder(src, 0);
		
		/* already done */
		if(l_end >= src.size()-1) return;
		
		/* find the right part */
		int r_end = finder(src, l_end+1);
		
		/* merge them */
		for(int i=0; i<=l_end; i++) {
			for(int j=l_end+1; j<=r_end; j++) {
				if(less(src.get(j), src.get(i))) {
					src.add(i, src.remove(j));
					i++;
				}
			}
		}
		
		sort(src, r_end);
	}
	
	
	private static int finder(LinkedList<Comparable<Object>> src, int current) {
		if(current >= src.size()-1) {
			return src.size()-1;
		}
		
		int i=current+1;
		for(; i<src.size(); i++) {
			if(less(src.get(i), src.get(i-1))) {
				return i-1;
			}
		}
		return i;
	}
	
	private static boolean less(Comparable<Object> v, Comparable<Object> w) {
		return v.compareTo(w) < 0;
	}
	
	//private static boolean isSorted(Comparable[])
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String in = "jfoqiaewgpoajidnebvfpoaueihro90qa";
		Comparable<Object>[] src = new Comparable[in.length()];
		
		for(int i=0; i<in.length(); i++) {
			src[i] = (Comparable<Object>) in.subSequence(i, i+1);
		}
		
		sort(src);
		
	}

}
