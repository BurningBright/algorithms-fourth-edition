package class0104;

import java.util.Arrays;
/**
 * find triple sum to zero in matrix
 * @author soft01
 *
 */
public class ThreeSumMatrix {
	private int[] src;
	
	public ThreeSumMatrix(int[] s) {
		src = s.clone();
		Arrays.sort(src);
	}
	
	public int count() {
		int sum = 0;
		FindInMatrix fm = new FindInMatrix(src);
		for(int i=0; i<src.length; i++) {
			for(int j=i; j<src.length; j++) {
				/*begin triple count*/
				int curNum = fm.count(-src[i]-src[j]);
				sum+= curNum>0? curNum:0;
			}
		}
		return sum;
	}
}
