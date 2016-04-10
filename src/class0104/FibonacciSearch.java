package class0104;

import java.util.Arrays;

import stdlib.StdOut;

/**
 * search with only addition and subtraction.
 * @author soft01
 *
 */
public class FibonacciSearch {
	private int[] a;
	private Fibonacci fib;
	
	private class Fibonacci{
		private int[] fibonacci = new int[2];
		private int current;
		
		public Fibonacci() {
			this.current = 0;
			fibonacci[0] =0;
			fibonacci[1] =1;
		}
		/*move number list*/
		public int work() {
			int tmp = fibonacci[1];
			fibonacci[1] += fibonacci[0];
			fibonacci[0] = tmp;
			current += fibonacci[1];
			return current;
		}
		/*get current index space*/
		public int getCur() {
			return current;
		}
		/*same with initial*/
		public void reset(int current) {
			this.current = current;
			fibonacci[0] =0;
			fibonacci[1] =1;
		}
		/*if the index across turn back*/
		public int flashBack() {
			return current-fibonacci[1];
		}
	}
	
	public FibonacciSearch(int[] src) {
		this.a = src;
		Arrays.sort(a);
	}
	
	public int findKey(int key) {
		fib = new Fibonacci();
		return find(key, 0, a.length-1);
	}
	
	/**
	 * find the key in the array
	 * @param key target
	 * @param lo current begin index
	 * @param hi current end index
	 * @return the target's index
	 */
	private int find(int key, int lo, int hi) {
		/*put our number list to the current space*/
		fib.reset(lo);
		
		do{
			StdOut.print(fib.getCur()+" ");
			/*find target return */
			if(a[fib.getCur()]==key) {
				StdOut.println();
				return fib.getCur();
			}else if(a[fib.getCur()]>key) {
				/*across target index turn back*/
				StdOut.println();
				hi = fib.getCur();
				lo = fib.flashBack();
				return find(key, lo, hi);
			}
		}while(fib.work()<=hi);
		
		/*all ended check whether the target in the corner*/
		if(fib.flashBack()<a.length-1) {
			return find(key, fib.flashBack(), hi);
		}
		
		return -1;
	}
	
	public static void main(String[] args) {
		int[] a = {1,2,3,4,5,6,7,8,9,10,11,12,13};
		StdOut.println(new FibonacciSearch(a).findKey(13));
	}
}
