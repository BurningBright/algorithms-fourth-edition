package class0104;

import java.util.Arrays;

import rlgs4.BinarySearch;

/**
 * uses a linear algorithm to count the pairs
 * that sum to zero after the array is sorted
 * @author soft01
 *
 */
public class ThreeSumFaster {

private int[] src;
	
	public ThreeSumFaster(int[] s) {
		src = s.clone();
		Arrays.sort(src);
	}
	
	public int count() {
		int sum = 0;
//		TripleSearch ts = new TripleSearch(src);
		for(int i=0; i<src.length; i++) {
			for(int j=i; j<src.length; j++) {
				/*begin triple count*/
//				int curNum = ts.triple(-src[i]-src[j]);
				int curNum = BinarySearch.rank(-src[i]-src[j], src);
				if(curNum>0) {
					sum++;
				}
			}
		}
		return sum;
	}

}
