package class0104;

import stdlib.StdDraw;
import stdlib.StdOut;

/**
 * tilde approximations
 * @author soft01
 *
 */
public class TildeJudge {

	public static void main(String[] args) {
		StdDraw.setPenRadius(0.005);
		for(int i=3; i<100; i++) {
			double y1 = Math.log(i)/10;
			double y2 = Math.log(Math.pow(i, 2)+1)/10;
			double y3 = y2/y1/10;
			StdOut.println(y3);
			StdOut.println((double)i/100);
			StdDraw.point((double)i/100, y1);
			StdDraw.point((double)i/100, y2);
			StdDraw.point((double)i/100, y3);
		}
	}

}
