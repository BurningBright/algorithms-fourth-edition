package class0104;

//import java.util.Arrays;
import stdlib.StdOut;
/**
 * finds the number of occurrences of a given
 * key in time proportional to log N in the worst case
 * @author soft01
 *
 */
public class FindNumBinary {
	private int[] a;

	public FindNumBinary(int[] keys) {
		/*a = new int[keys.length];
		for (int i = 0; i < keys.length; i++)
			a[i] = keys[i]; // defensive copy
		Arrays.sort(a);*/
		this.a = keys;
	}

	public boolean contains(int key) {
		return count(key) != -1;
	}

	public int count(int key) { // Binary search.
		int  flagBegin, flagEnd;
		flagBegin=flagEnd=-1;
		int lo = 0;
		int hi = a.length - 1;
		while (lo <= hi) { // Key is in a[lo..hi] or not present.
			int mid = lo + (hi - lo) / 2;
			if (key < a[mid])
				hi = mid - 1;
			else if (key > a[mid])
				lo = mid + 1;
			else{
				/*find the smallest element*/
				flagBegin = lo<mid? countLeft(key, lo, mid):mid;
				/*find the biggest element*/
				flagEnd = hi>mid? countRight(key, mid, hi):mid;
//				StdOut.println(flagBegin+" "+flagEnd);
				return flagBegin>-1 && flagEnd>-1? flagEnd-flagBegin+1:-1;
			}
		}
		return -1;
	}
	
	//find the small one
	private int countLeft(int key, int lo, int hi) {
//		StdOut.println("l:"+lo+" "+hi);
		int low = lo;
		int hig = hi;
		while (true) {
			// Key is in a[lo..hi] or not present.
			int mid = low +(hig - low) / 2;
			
//			StdOut.println("left:"+low+" "+mid+" "+hig);
			if (key > a[mid]) {
				low = mid + 1;
			}else{
				/*
				 * if we find mid and still
				 * have some space we give
				 * mid the high sign and go on
				 */
				if(low<mid) {
					hig = mid;
				}else {
					return mid;	
				}
			}
		}
	}
	
	//find the big one
	private int countRight(int key, int lo, int hi) {
//		StdOut.println("r:"+lo+" "+hi);
		int low = lo;
		int hig = hi;
		while (true) {
			// Key is in a[lo..hi] or not present.
			int mid = low +(hig - low) / 2;
			
//			StdOut.println("right:"+low+" "+mid+" "+hig);
			if (key < a[mid]) {
				hig = mid - 1;
			}else{
				/*
				 * if we find mid and still
				 * have some space we give
				 * mid the low sign and go on
				 */
				/*if(mid==low) {
					if(key==a[hig]) {
						return hig;
					}
					return mid;
				}*/
				if(hig>mid+1) {
					low = mid;
				}else{
					return key==a[hig]?hig:mid;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int[] a = {10, 10, 11, 11, 11, 16, 18, 23, 29, 33, 48
				, 54, 57, 77, 77, 84, 98, 100};
//		int[] b = {7, 7, 7, 7};
		FindNumBinary set = new FindNumBinary(a);
		StdOut.println(set.count(100));
	}
}
