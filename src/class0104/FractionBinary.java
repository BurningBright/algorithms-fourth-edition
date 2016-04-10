package class0104;

import java.util.Arrays;

import class0102.Rational;

import stdlib.StdOut;
import stdlib.StdRandom;

public class FractionBinary {
	private Rational[] a;
	
	public FractionBinary(Rational[] src) {
		this.a = src;
		Arrays.sort(a);
		StdOut.println(Arrays.toString(a));
	}
	
	/**
	 * find the fraction in the array
	 * @param x target
	 * @return whether a rational number
	 * 			 bigger than x or equals x
	 */
	public void fraction(int x) {
		Rational rat;
		if((rat = rank(x, a)) != null) {
			StdOut.println(rat.toString());
		}else{
			StdOut.println("Not find");
		}
	}
	
	private Rational rank(int k, Rational[] a) {
		int lo = 0;
		int hi = a.length - 1;
		Rational key = new Rational(k, 1);
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			if (key.compareTo(a[mid])>0) {
				return a[mid];
			}else{
				hi = mid - 1;
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		int N= 10;
		Rational[] src = new Rational[N];
		for(int i=0; i<N; i++) {
			int num = StdRandom.uniform(1, 100);
			int den = StdRandom.uniform(1, 100);
			src[i] = new Rational(num, den);
		}
//		StdOut.println(Arrays.toString(src));
		new FractionBinary(src).fraction(1);
	}
}
