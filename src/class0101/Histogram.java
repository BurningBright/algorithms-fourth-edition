package class0101;

import java.util.Arrays;

import stdlib.StdDraw;
import stdlib.StdRandom;
/**
 * @Description 1.1.32
 * @author Leon
 * @date 2016-05-23 15:30:57
 */
public class Histogram {
	public static double width = 0;
	
	public static void dividing (double l , double r) {
		double x = l;
		double y = r / 2.0;
		double rw = width;
		double rh = r / 2.0;
		StdDraw.filledRectangle(x, y, rw, rh);
	}
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		int N = 10;
		int S = 50;
		width = 1.0/N;
		//System.out.println(1.0/30*5);
		
		//initial data
		int[][] record = new int[N][1];
		double[] box = new double[S];
		for(int i=0; i<S; i++) {
			box[i] = StdRandom.random();
		}
		
		//statistics
		for(int i=0; i<S; i++) {
			for(int j=0; j<N; j++) {
				if(box[i]>=1.0/N*j && box[i]<1.0/N*(j+1)) {
					record[j][0] = record[j][0] +1;
					break;
				}
			}
		}
		System.out.println(Arrays.toString(box));
		System.out.println(Arrays.deepToString(record));
		
		for(int i=0; i<N; i++) {
			dividing(1.0/N*i, record[i][0]*1.0/S);
		}
	}

}
