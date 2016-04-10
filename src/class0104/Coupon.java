package class0104;

import rlgs4.ResizingArrayBag;
import stdlib.StdRandom;
/**
 * the number length of integers generated 
 * before all possible values are generated
 * @author soft01
 *
 */
public class Coupon {
	public static ResizingArrayBag<Integer> bag;
	public static int N = 30;
	
	public static boolean allPossible() {
		for(int i=0; i<N; i++) {
			boolean flag = false;
			for(int j: bag) {
				if(i==j) {
					flag = true;
					break;
				}
			}
			if(!flag) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		
		bag = new ResizingArrayBag<Integer>();
		int current;
		do{
			current = StdRandom.uniform(N);
			bag.add(current);
		}while(!allPossible());
		
		StringBuilder sb = new StringBuilder();
		for(int i: bag) {
			sb.append(i).append(" ");
		}
		System.out.println(sb.toString());
		System.out.println(bag.size());
		
	}
}
