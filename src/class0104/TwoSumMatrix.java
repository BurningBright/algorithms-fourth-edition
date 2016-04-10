package class0104;

import java.util.Arrays;
/**
 * find double sum to zero in matrix
 * @author soft01
 *
 */
public class TwoSumMatrix {
	private int[] src;
	
	public TwoSumMatrix(int[] s) {
		src = s.clone();
		Arrays.sort(src);
	}
	
	public int count() {
		int sum = 0;
		FindInMatrix fm = new FindInMatrix(src);
		for(int i=0; i<src.length; i++) {
			/*begin triple count*/
			int curNum = fm.count(-src[i]);
			sum+= curNum;
		}
		return sum;
	}
}
