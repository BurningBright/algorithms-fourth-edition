package class0101;

import stdlib.StdDraw;
import stdlib.StdRandom;

/**
 * 1.1.31
 * 
 * @author soft01
 * 
 */
public class CircleRanLine {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		//initial data
		double ran = StdRandom.random();
		double cenX, cenY, cenR, calX, calY, dotR;
		cenX = cenY = .5;
		cenR = .2;
		dotR = 0.005;
		int N = 10;
		double[][] dots = new double[N][2];
		
		
		//initial center circle
		System.out.println(ran);
		StdDraw.circle(cenX, cenY, cenR);
		StdDraw.circle(cenX, cenY, .01);
		StdDraw.filledCircle(cenX, cenY, .01);

		/*
		 * calculate dots spacing
		 * save them into two-dimension
		 * array at last draw them
		 */
		for (int i = 1; i <= N; i++) {
			calX = Math.sin(2 * Math.PI * i / N) * cenR + cenX;
			calY = Math.cos(2 * Math.PI * i / N) * cenR + cenY;

			dots[i - 1][0] = calX;
			dots[i - 1][1] = calY;

			StdDraw.circle(calX, calY, dotR);
			StdDraw.filledCircle(calX, calY, dotR);
		}

		//draw lines
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(j!=i && StdRandom.random()<ran) {
					StdDraw.line(dots[j][0], dots[j][1], dots[i][0], dots[i][1]);
				}
			}
		}
		
	}
}
