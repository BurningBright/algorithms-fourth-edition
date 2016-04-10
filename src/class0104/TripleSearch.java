package class0104;

//import java.util.Arrays;

import stdlib.StdOut;

/**
 * finds the number of occurrences of a given
 * key in time proportional to log base 3 N in the worst case
 * @author soft01
 *
 */
public class TripleSearch {
	private int[] a;
	
	public TripleSearch(int[] keys) {
/*		a = keys.clone();
		Arrays.sort(a);*/
		this.a = keys;
	}
	
	public boolean contains(int key) {
		return triple(key) != -1;
	}
	
	public int triple(int key) {
		int lo = 0;
		int hi = a.length- 1;
		int  flagBegin, flagEnd;
		flagBegin=flagEnd=-1;
		
		while (lo <= hi) {
			int left = lo + (hi - lo) / 3;
			int right = lo + (hi - lo)*2 / 3;
//			StdOut.println(lo +" "+left+" "+right+" "+hi);
			if(key < a[left]) {
				hi = left-1;
			}else if(key > a[left]) {
				if(key < a[right]) {
					hi = right-1;
					lo = left+1;
				}else if(key > a[right]) {
					lo = right+1;
				}else{
					flagBegin = lo<right? findMin(key, lo, right):right;
					flagEnd = hi>right? findMax(key, right, hi):right;
//					StdOut.println("right part: "+flagBegin+" "+flagEnd);
					return flagBegin>-1 && flagEnd>-1? flagEnd-flagBegin+1:-1;
//					return key==src[right]? right:-3;
				}
			}else{
				flagBegin = lo<left? findMin(key, lo, left):left;
				flagEnd = hi>left? findMax(key, left, hi):left;
//				StdOut.println("left part: "+flagBegin+" "+flagEnd);
				return flagBegin>-1 && flagEnd>-1? flagEnd-flagBegin+1:-1;
//				return key==src[left]? left:-2;
			}
		}
		return -1;
	}
	
	private int findMin(int key, int begin, int end) {
		int lo = begin;
		int hi = end;
		while (true) {
			int left = lo + (hi - lo) / 3;
			int right = lo + (hi - lo)*2 / 3;
			
			if(key>a[left]) {
				boolean flag = false;
				//in middle condition
				if(key>a[right]) {
					flag = true;
				}else{
					if(lo<right) {
						hi = right;
					}else{
						return right;
					}
				}
				lo = flag? right+1: left+1;
			}else{
				if(lo<left) {
					hi = left;
				}else{
					return left;
				}
			}
		}
	}
	
	private int findMax(int key, int begin, int end) {
		int lo = begin;
		int hi = end;
		while (true) {
			int left = lo + (hi - lo) / 3;
			int right = lo + (hi - lo)*2 / 3;
			if(key<a[right]) {
				boolean flag = false;
				//in middle condition
				if(key<a[left]) {
					flag = true;
				}else{
					if(hi>left+1) {
						lo = left;
					}else{
						return key==a[hi]?hi:left;
					}
				}
				hi = flag? left-1:right-1;
			}else{
				if(hi>right+1) {
					lo = right;
				}else{
					return key==a[hi]?hi:right;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int[] a = {10, 11, 12, 16, 18, 20, 21, 23, 29, 33, 48, 54, 54, 54, 54, 89, 98};
		StdOut.println(new TripleSearch(a).triple(98));
	}

}
