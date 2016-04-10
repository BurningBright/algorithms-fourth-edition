package class0202;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * describe:
 * 		Bottom-up queue mergesort 
 * date:	 2015年5月20日 下午3:46:53
 * @author:	 Chen
 *
 */
public class MergeBUinQueue {
	
	public static void mergeBUQ(Comparable<Object>[] src) {
		int size = src.length;
		Queue<Comparable<Object>> dest;
		Queue<Comparable<Object>> left = new LinkedList<Comparable<Object>>();
		Queue<Comparable<Object>> right = new LinkedList<Comparable<Object>>();
		
		/*
		 * control the outter part's step
		 */
		for(int sz = 1; sz < size; sz += sz) {
			/*
			 * every loop jump to the next start point
			 */
			for(int lo = 0; lo < size-sz; lo += sz*2) {
				for(int i=lo; i<lo+sz; i++) {left.add(src[i]);}
				//(size>lo+2*sz?lo+2*sz:size)
				for(int i=lo+sz; i<Math.min(lo+2*sz, size); i++) {right.add(src[i]);}
				dest = MergeQueue.mergeQueue(left, right);
				for(int i=lo; i<Math.min(lo+2*sz, size); i++) {src[i] = dest.poll();}
			}
			
			System.out.println(Arrays.toString(src));
		}
		
	}
	
	public static void mergeBUQR(Comparable<Object>[] src) {
		Queue<Queue<Comparable<Object>>> qSrc = 
				new LinkedList<Queue<Comparable<Object>>>();
		
		for(int i=0; i<src.length; i++) {
			Queue<Comparable<Object>> tmp = new LinkedList<Comparable<Object>>();
			tmp.add(src[i]);
			qSrc.add(tmp);
		}
		
		mergeBUQR(qSrc);
		
		Queue<Comparable<Object>> tmp = qSrc.poll();
		for(int i=0; !tmp.isEmpty(); i++) {
			src[i] = tmp.poll();
		}
	}
	
	public static void mergeBUQR(Queue<Queue<Comparable<Object>>> src) {
		if(src.size() == 1) {
			
			return;
		}
		/*
		 * Queue is Button to top
		 * Stack is Top down
		 */
		src.add(MergeQueue.mergeQueue(src.poll(), src.poll()));
		mergeBUQR(src);
	}
	
	public static boolean less(Comparable<Object>v, Comparable<Object>w) {
		return v.compareTo(w) <0;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String src = StdIn.readLine();
		String in = "jfoqiaewgpoajidnebvfpoaueihro90qa";
		Comparable<Object>[] src = new Comparable[in.length()];
		
		for(int i=0; i<in.length(); i++) {
			src[i] = (Comparable<Object>) in.subSequence(i, i+1);
		}
		
		mergeBUQR(src);
		
		System.out.println(Arrays.toString(src));
		
	}

}
