package class0104;

import stdlib.StdOut;
/**
 * change an arrays to a matrix
 * find element begin with the 
 * compare in row's first column
 * and then count the elements
 * @author soft01
 *
 */
public class FindInMatrix {
	private int[][] a;
	private int rows, cols;
	
	public FindInMatrix(int[] keys) {
		rows = (int)(Math.sqrt(keys.length))+1;
		cols = keys.length/rows+1;
		a = new int[rows][cols];
		for(int i=0; i<keys.length; i++) {
			a[i / cols][i % cols] = keys[i];
		}
//		StdOut.println(Arrays.deepToString(a));
	}
	
	public int count(int key) {
		int rowBegin = rows;
		int sum = 0;
		/*compare the first cols*/
		for(int i=0; i<rows; i++) {
			if(a[i][0] >= key && i!=0) {
				rowBegin = i-1;
				break;
			}else if(a[i][0] >= key && i==0) {
				return 0;
			}
		}
		
		/*search rows*/
		for(int i=rowBegin; i<rows; i++) {
			for(int j=0; j<cols; j++) {
				if(a[i][j] == key) {
					sum++;
				}
				if(a[i][j]>key){
					break;
				}
			}
		}
		return sum;
	}
	
	public static void main(String[] args) {
		int[] src = {1,2,3,4,5,6,7,8,9,10,11,12,12,13,14,14,15};
		StdOut.println(new FindInMatrix(src).count(14));
	}
}
