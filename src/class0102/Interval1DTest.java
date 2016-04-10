package class0102;

import java.util.ArrayList;
import java.util.List;

import rlgs4.Interval1D;
import stdlib.StdIn;
import stdlib.StdOut;
/**
 * 1.2.2
 * @author soft01
 *
 */
public class Interval1DTest {

	public static void main(String[] args) {
		StdOut.println("Enter Num");
		int N = StdIn.readInt();
		
		List<Interval1D> list = new ArrayList<Interval1D>();
		
		for(int i=0; i<N; i++) {
			StdOut.print((i+1)+" first :");
			double first = StdIn.readDouble();
			StdOut.print((i+1)+" second :");
			double second = StdIn.readDouble();
			list.add(new Interval1D(first, second));
		}
		
		for(int i=0; i<list.size(); i++) {
			for(int j=list.size()-1; j>i; j--) {
				if(list.get(i).intersects(list.get(j))){
					System.out.println(i+"---"+j+" intserects");
				}
			}
		}
	}
}
