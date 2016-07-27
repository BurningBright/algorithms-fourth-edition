package class0201;

import java.awt.Color;

import class0104.Adjustable2DChart;
import rlgs4.Stopwatch;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * sorting arrays of random Double values 
 * ,using array sizes that are increasing 
 * powers of 2, is a small constant number
 * @author Chen
 *
 */
public class InstrumentShell {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static double shellDoubling(int N) {
		Comparable<Object>[] src = (Comparable[]) new Double[N];
		for (int i = 0; i < N; i++) {
			src[i] = (Comparable)StdRandom.uniform();
		}
		
		Stopwatch sw = new Stopwatch();
		ShellSort.sort(src);
		if(!ShellSort.isSorted(src)) {
			return .1;
		}
		return sw.elapsedTime();
	}

	public static void main(String[] args) {

		double pre = shellDoubling(100);
		StdOut.printf("%7.3f\n", pre);
		/*
		int N = 100;
		for (int i = N; i < 100000001; i *= 10) {
			double cur = shellDoubling(i);
			StdOut.printf("%10d %7.3f %5.3f\n", i, cur, cur / pre);
			pre = cur;
		}
		*/
		
		Adjustable2DChart a2d = new Adjustable2DChart(0.1, 0.1, 0, 0);
		
		a2d.setAxisDescDistanceChart(-.3);
		a2d.setAxisDescDistanceY(.07);
		a2d.setChartDesc("Shellsort is subquadratic");
		a2d.setAxisXDesc("problem size N");
		a2d.setAxisYDesc("running time T(N)");

		a2d.setColorForChar(Color.RED);
		
		int N = 4096;
		for (int i = N; i < 4194305; i *= 2) {
			double cur = shellDoubling(i);
			
			if(i<10000) {
				a2d.addChartData((double)i, cur);
				a2d.addAxisDataX((double)i, i/1000+"K");
			} else if(i<100000000) {
				a2d.addChartData((double)i, cur);
				a2d.addAxisDataX((double)i, i/10000+"W");
			}
			a2d.reDraw();
			StdOut.printf("%10d %7.3f %5.3f\n", i, cur, cur / pre);
			pre = cur;
		}

	}
}
