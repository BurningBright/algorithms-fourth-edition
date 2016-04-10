package class0103;

import java.util.Iterator;

import rlgs4.LinkedBag;
import stdlib.StdOut;
import stdlib.StdRandom;
/**
 * recursive the iterator to find the
 * max number in the random bag
 * @author soft01
 *
 */
public class MaximumKey {
	
	public static int recurFind(Iterator<Integer> lt) {
		
		int t1 = -1;
		if(lt.hasNext()) {
			t1 = lt.next();
		}
		if(!lt.hasNext()) {
			return t1;
		}
//		StdOut.println(t1);
		int sMax;
		return t1>(sMax=recurFind(lt))? t1:sMax;
	}
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		LinkedBag<Integer> lb = new LinkedBag<Integer>();
		int max = -1;
		for(int i=0; i<10; i++) {
			Integer tmp = (int)(StdRandom.random()*100);
			lb.add(tmp);
		}
//		lb.add(9);lb.add(7);lb.add(5);
		for(int i: lb) {
			if(i>max) {
				max = i;
			}
			StdOut.print(i+"  ");
		}
		StdOut.println();
		StdOut.println(max);
		
		StdOut.println(recurFind(lb.iterator()));
	}

}
