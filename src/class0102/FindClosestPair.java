package class0102;

import java.util.ArrayList;
import java.util.List;

import rlgs4.Point2D;
import stdlib.StdDraw;
import stdlib.StdRandom;
/**
 * 1.2.1
 * @author soft01
 *
 */
public class FindClosestPair {

	public static List<Point2D> list;
	public static Point2D a;
	public static Point2D b;
	
	public static void comparePoint() {
		double preDis = 10.0;
		
		for(int i=0; i<list.size(); i++) {
			for(int j=list.size()-1; j>i; j--) {
				double currDis = list.get(i).distanceTo(list.get(j));
				if(currDis < preDis) {
					preDis = currDis;
					a = list.get(i);
					b = list.get(j);
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		int N=200;
		list = new ArrayList<Point2D>();
		
		for(int i=0; i<N; i++) {
			double r1 = StdRandom.random();
			double r2 = StdRandom.random();
			Point2D p = new Point2D(r1, r2);
			list.add(p);
			p.draw();
		}
		comparePoint();

		StdDraw.circle(a.x(), a.y(), 0.01);
		StdDraw.circle(b.x(), b.y(), 0.01);
	}

}
