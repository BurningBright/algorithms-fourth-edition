package class0104;


/**
 * returns the element with the smallest 
 * index that matches the search element
 * @author soft01
 *
 */
public class FindMinBinary {
	
	public static int find(int key, int[] a) {
		// Array must be sorted.
		int lo = 0;
		int hi = a.length - 1;
		while (lo <= hi) {
			// Key is in a[lo..hi] or not present.
			int mid = lo +(hi - lo) / 2;
			if (key < a[mid]) {
				hi = mid - 1;
			}else if (key > a[mid]) {
				lo = mid + 1;
			}else{
				/*
				 * if we find mid and still
				 * have some space we give
				 * mid the high sign and go on
				 */
//				StdOut.println(lo+" "+hi+" "+mid);
				if(lo<mid) {
					hi = mid;
				}else {
					return mid;	
				}
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		int[] a = {10, 10, 11, 11, 12, 16, 18, 23, 29, 33, 48
				, 54, 57, 68, 77, 84, 98, 100};
		System.out.println(find(11, a));
	}

}