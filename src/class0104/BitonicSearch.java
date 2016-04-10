package class0104;

import java.util.Arrays;
import java.util.Comparator;

import rlgs4.BinarySearch;
import rlgs4.Stopwatch;
import stdlib.StdOut;
import stdlib.StdRandom;
/**
 * program should use ~3lg N
 * compares in the worst case
 * @author soft01
 *
 */
public class BitonicSearch{
	private int[] src;
	private int breakPoint;
	
	public BitonicSearch(int[] a) {
		breakPoint = StdRandom.uniform(a.length/3, a.length*2/3);
		Arrays.sort(a, 0, breakPoint);
		
		Integer[] tmp = new Integer[a.length];
		src = new int[a.length];
		
		for(int i=0; i<a.length; i++) {
			tmp[i] = Integer.valueOf(a[i]);
		}
		
		Arrays.sort(tmp, breakPoint, a.length, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return o2.intValue()-o1.intValue();
			}
		});
		
		for(int i=0; i<a.length; i++) {
			src[i] = tmp[i].intValue();
		}
	}
	
	public int find(int key) {
		return loopFinder(key, 0, src.length-1);
	}
	
	/**
	 * this method will return the key position
	 * in the array, whether the key is or not in.
	 * @param key the target
	 * @param lo low position
	 * @param hi high position
	 * @return target position
	 */
	private int loopFinder(int key, int lo,int hi) {
		int mid = lo+(hi-lo)/2;
		
		if(mid==breakPoint) {
			int tmp;
			return (tmp=BinarySearch.rank(key, lo, breakPoint, src))>0?
							tmp:BinarySearch.rank(key, breakPoint, hi, src);
		}
		
		int tmp = -1;
		if(mid<breakPoint) {
			tmp = BinarySearch.rank(key, lo, mid, src);
			return tmp>0? tmp: loopFinder(key, mid, hi);
		}
		if(mid>breakPoint) {
			tmp = BinarySearch.rank(key, mid, hi, src);
			return tmp>0? tmp: loopFinder(key, lo, mid);
		}
		return tmp;
	}
	
	public static int MIN = 1000000;
	public static double timeTrial(int N, int key) {
		/*prepare random data*/
		
		int[] a = new int[N];
		for (int i = 0; i < N; i++) {
			a[i] = MIN+i;
		}
		StdRandom.shuffle(a);
		
		Stopwatch timer = new Stopwatch();
		int find = new BitonicSearch(a).find(key);
		StdOut.print(find +" ");
		return timer.elapsedTime();
	}
	
	public static void main(String[] args) {
		for (int N = 25000; N < 900000; N += N) {
			int key = StdRandom.uniform(MIN, MIN+N);
			double time = timeTrial(N, key);
			StdOut.printf("%7d %5.3f\n", N, time);
		}
	}
}
