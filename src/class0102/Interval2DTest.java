package class0102;

import java.util.ArrayList;
import java.util.List;

import rlgs4.Interval1D;
import rlgs4.Interval2D;
import stdlib.StdRandom;

//1.2.3
public class Interval2DTest {
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		int N = 5;
		double min = .05;
		double max = .5;
		
		List<Interval2D> list = new ArrayList<Interval2D>();
		
		for(int i=0; i<N; i++) {
			double xLo = StdRandom.random()/2;
			double xHi = xLo+StdRandom.random()*(max-min)+min;
			double yLo = StdRandom.random()/2;
			double yHi = yLo+StdRandom.random()*(max-min)+min;
			System.out.println("x,y "+xHi+" "+yHi);
			Interval1D coordOriX = xLo>xHi? new Interval1D(xHi, xLo) : new Interval1D(xLo, xHi);
			Interval1D coordOriY = yLo>yHi? new Interval1D(yHi, xLo) : new Interval1D(yLo, yHi);
			Interval2D curr2D = new Interval2D(coordOriX, coordOriY);
			list.add(curr2D);
			curr2D.draw();
		}
		
		for(int i=0; i<list.size(); i++) {
			for(int j=list.size()-1; j>i; j--) {
				if(list.get(i).intersects(list.get(j))){
					System.out.println(i+"---"+j+" intserects");
				}
			}
		}
		
		for(int i=0; i<list.size(); i++) {
			for(int j=0; j<list.size(); j++) {
				if(i!=j && list.get(i).contains(list.get(j))){
					System.out.println(i+" contains "+j);
				}
			}
		}
	}

}
