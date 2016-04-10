package class0104;

import java.util.Arrays;

/**
 * uses a linear algorithm to count the pairs
 * that sum to zero after the array is sorted
 * @author soft01
 *
 */
public class TwoSumFaster {
	
	private int[] src;
	
	public TwoSumFaster(int[] s) {
		src = s.clone();
		Arrays.sort(src);
	}
	
	public int count() {
		int sum = 0;
		TripleSearch ts = new TripleSearch(src);
		for(int i=0; i<src.length; i++) {
			/*begin triple count*/
			int curNum = ts.triple(-1*src[i]);
			sum+= curNum>0? curNum:0;
		}
		return sum;
	}

}
