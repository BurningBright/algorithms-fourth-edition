package class0104;

import rlgs4.ResizingArrayBag;
import stdlib.StdRandom;
/**
 * the number length of integers generated before 
 * the first repeated value is found is
 * @author soft01
 *
 */
public class Birthday {
	
	public static ResizingArrayBag<Integer> bag;
	
	public static boolean contain(int cur) {
		if(!bag.isEmpty()) {
			for(int i: bag) {
				if(i==cur) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		int N = 2000;
		bag = new ResizingArrayBag<Integer>();
		int current;
		while(!contain((current=StdRandom.uniform(N)))){
			bag.add(current);
		}
		StringBuilder sb = new StringBuilder();
		for(int i: bag) {
			sb.append(i).append(" ");
		}
		System.out.println(sb.toString());
		System.out.println(bag.size());
	}
}
