package class0202;

import java.util.LinkedList;
import java.util.List;

/**
 * describe: 
 * 		divide-and-conquer algorithm that 
 * 		randomly shuffles a linked list in 
 * 		linearithmic time and logarithmic extra space
 * date:	 2015年10月10日 上午09:11:29
 * @author:	 Chen
 *
 */
public class ShufflingLinkedList {
	
	/**
	 * recursion idea
	 * @param src data source
	 */
	public static void shufflingLL(LinkedList<Comparable<Object>> src) {
		int size = src.size();
		for (int sz = 1; sz < size; sz += sz) {
			for (int lo = 0; lo < size - sz; lo += 2 * sz) {
				int itl = Math.min(size-1, lo+2*sz-1)-lo;
				if(itl <= 1) { continue; }
				shuffling(src.subList(lo, Math.min(size-1, lo+2*sz-1)), itl);
			}
		}
	}
	
	private static void shuffling(List<Comparable<Object>> src, int itl) {
		for (int i=0, j=0; i<=itl; i++) {
			if (Math.random() > 0.5) {			// (0,1] -> (0,0.5] & (0.5,1]
				src.add(0,src.remove(j++));		// remove the left one to the temple space
			} else {							// remove the right one to the temple space
				src.add(0,src.remove(src.size()-1));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String in = "0123456789abcdefghijlmnopqrstuvwxyz";
		
		Comparable<Object>[] src = new Comparable[in.length()];
		for(int i=0; i<in.length(); i++) {
			src[i] = (Comparable<Object>) in.subSequence(i, i+1);
		}
		
		LinkedList<Comparable<Object>> lSrc = new LinkedList<Comparable<Object>>();
		for(int i=0; i<src.length; i++) {
			lSrc.add(src[i]);
		}
		
		shufflingLL(lSrc);
		
		System.out.println(lSrc);
	}

}
