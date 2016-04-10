package class0103;

import stdlib.StdOut;

/**
 * used to test whether a given permutation
 * can be generated as output by our test client
 * @author soft01
 *
 */
public class StackGenerability {

	private static ResizeStack<Integer> working;
	private static GeneralizedLQueue<Integer> prepare;
	private static GeneralizedLQueue<Integer> target;
	
	public static void init(int[] src) {
		target = new GeneralizedLQueue<Integer>();
		prepare = new GeneralizedLQueue<Integer>();
		working = new ResizeStack<Integer>();
		for(int i=0; i<src.length; i++) {
			prepare.insert(i);
		}
		for(int i=0; i<src.length; i++) {
			target.insert(src[i]);
		}
		StdOut.println(checkGener());
	}
	
	private static boolean checkGener() {
		while(target.size()!=0) {
			int stackTop = working.getTop()==null? -1:working.getTop();
			StdOut.print(target.size()+"  ");
			
			/*
			 * when prepare is empty and 
			 * the working top not target's
			 * top return false;
			 */
			if(prepare.size() == 0 && stackTop != target.peek()) {
				return false;
			}
			/* when not equal use prepare*/
			if(stackTop != target.peek()) {
				StdOut.println("push:   "+prepare.peek());
				working.push(prepare.delete(1));
				continue;
			}
			/* when equal pop the stack*/
			if(stackTop == target.peek()) {
				StdOut.println("pop:   "+working.pop());
				target.delete(1);
				continue;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		int[] i = {4, 3, 2, 1, 0, 9, 8, 7, 6, 5};
		init(i);
	}

}
