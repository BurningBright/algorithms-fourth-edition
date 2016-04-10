package class0202;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * describe: Develop a mergesort implementation based on the 
 * 			idea of doing k-way merges (rather than 2-way merges).
 * date:	 2015年10月10日 下午1:42:55
 * @author:	 Chen
 *
 */
public class MultiwayMergeSort {
	
	public static Comparable<Object>[] mmSort(Comparable<Object>[] src, int way) {
		if(way <= 2) {
			return null;
		}
		
		LinkedList<Comparable<Object>> lSrc = new LinkedList<Comparable<Object>>();
		for(Comparable<Object> tmp : src) {
			lSrc.add(tmp);
		}
		
		int size = lSrc.size();
		for(int sz = 1; sz < size; sz += sz) {
			for(int lo=0; lo< size-sz; lo += lo*way) {
				/*
				 * lo	:	最低下标
				 * sz	:	合并粒度
				 * way	:	合并通道数
				 * lSrc	:	合并目标
				 */
				mmSort(lo, sz, way, lSrc);
			}
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static void mmSort(int lo, int sz, int way, LinkedList<Comparable<Object>> src) {
//		Comparable<Object>[] tmp = new Comparable[Math.min(src.size() - lo, sz * way)];
//		List<Comparable<Object>>[] subLHolder;
		for (int i=0; i<way; i++) {
			
		}
	}
	
	public static boolean less(Comparable<Object> v, Comparable<Object> w) {
		return v.compareTo(w) < 0;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String in = "jfoqiaewgpoajidnebvfpoaueihro90qa";
		Comparable<Object>[] src = new Comparable[in.length()];
		
		for(int i=0; i<in.length(); i++) {
			src[i] = (Comparable<Object>) in.subSequence(i, i+1);
		}
		
		mmSort(src, 3);
	}

}
