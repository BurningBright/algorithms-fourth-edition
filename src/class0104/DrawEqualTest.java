package class0104;

import java.util.Arrays;

import rlgs4.Stopwatch;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * determine the number pairs of
 *  values in an input file that are equal
 * @author soft01
 *
 */
public class DrawEqualTest {
	
	public static int count(int[] a) {
		int N = a.length;
		int cnt = 0;
		Arrays.sort(a);
		for(int i=0; i<N; i++) {
			for(int j=i; j<N; j++) {
				if(a[i]==a[j]) {
					cnt++;
				}else{
					break;
				}
			}
		}
		return cnt;
	}
	
	public static double timeTrial(int N) {
		int MAX = 1000000;
		int[] a = new int[N];
		for (int i = 0; i < N; i++) {
			a[i] = StdRandom.uniform(-MAX, MAX);
		}
		Stopwatch timer = new Stopwatch();
		int cnt = DrawEqualTest.count(a);
		StdOut.print(cnt + " ");
		return timer.elapsedTime();
	}
	
	public static void main(String[] args) {
		final double[] a = new double[1];
		
		Thread t1 = new Thread(new Runnable() {

			public void run() {
				int N=1000000;
				a[0] = DrawEqualTest.timeTrial(N);
				StdOut.printf("%7d %5.3f\n", N, a[0]);
			}
			
		});
		t1.start();
	}

}
