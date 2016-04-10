package class0202;

import java.util.LinkedList;
import java.util.Queue;

/**
 * describe: Develop a static method that takes two queues of sorted items as
 * arguments and returns a queue that results from merging the queues into
 * sorted order. date: 2015年5月19日 下午5:05:13
 * 
 * @author: Chen
 *
 */
public class MergeQueue {
	
	public static Queue<Comparable<Object>> mergeQueue(
			Queue<Comparable<Object>> active, Queue<Comparable<Object>> passive) {
		Queue<Comparable<Object>> tempQ = new LinkedList<Comparable<Object>>();
		while(!active.isEmpty() || !passive.isEmpty()) {
			if(active.isEmpty()) {
				tempQ.add(passive.poll());
				continue;
			}
			if(passive.isEmpty()) {
				tempQ.add(active.poll());
				continue;
			}
			if(less(active.peek(), passive.peek())) {
				tempQ.add(active.poll());
			} else {
				tempQ.add(passive.poll());
			}
		}
		
		return tempQ.isEmpty()? null: tempQ;
	}
	
	public static boolean less(Comparable<Object> v, Comparable<Object> w) {
		return v.compareTo(w) < 0;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String args[]) {
		//Comparable<Object>[] a = new Comparable[]{"E","E","G","M","R"};
		//Comparable<Object>[] b = new Comparable[]{"A","C","E","R","T"};
		
		Comparable<Object>[] a = new Comparable[]{"e", "h", "i", "r"};
		Comparable<Object>[] b = new Comparable[]{"0", "9", "o", "q"};
		
		Queue<Comparable<Object>> srcA = new LinkedList<Comparable<Object>>();
		Queue<Comparable<Object>> srcB = new LinkedList<Comparable<Object>>();
		
		for(int i=0; i<a.length; i++) {
			srcA.add(a[i]);
			srcB.add(b[i]);
		}
		
		Queue<Comparable<Object>> dest = mergeQueue(srcA, srcB);
		while (dest != null && !dest.isEmpty()) {
			System.out.println(dest.poll());
		}
	}
	
}
