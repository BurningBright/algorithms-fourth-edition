package class0201;

import rlgs4.Stopwatch;
import stdlib.StdRandom;

/**
 * arrays that contain just two key values, assuming that the values are equally
 * likely to occur.
 * 
 * @author Chen
 *
 */
public class EqualKeys {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void general(int N) {
		Comparable<Object>[] data = (Comparable[]) new Integer[N];

		for (int i = 0; i < N; i++) {
			data[i] = (Comparable) Integer.parseInt(Math.round(StdRandom.uniform())+"");
		}
		Comparable<Object>[] data2 = data.clone();
		
		Stopwatch sw = new Stopwatch();
		InsertionSort.sort(data);
		System.out.print(sw.elapsedTime()+" ");
		
		Stopwatch sw2 = new Stopwatch();
		SelectionSort.sort(data2);
		System.out.println(sw2.elapsedTime()+" "+N);
	}
	
	public static void main(String[] args) {
		
		for (int i = 1000; i < 500000; i *= 2) {
			general(i);
		}
		
		
	}

}
