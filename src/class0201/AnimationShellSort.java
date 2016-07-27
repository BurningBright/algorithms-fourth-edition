package class0201;

import java.util.ArrayList;
import java.util.List;

import stdlib.StdDraw;
import stdlib.StdIn;
import stdlib.StdRandom;

/**
 * to make them draw the array 
 * contents as vertical bars
 * 
 * 1.draw one array vertical bar
 * 2.step by shell interval
 * @author Chen
 *
 */
public class AnimationShellSort {
	
	//the distance between previous sort and current sort
	private static final double insertY = 7.0;
	private static final double visualY = 3.0;
	private static Comparable<Object>[] privous;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void sortProcess(int N) {
		AnimationSortCompare.internalX = .45;
		AnimationSortCompare.halfWidth = .30;
		StdDraw.setXscale(0, 23.0);
		StdDraw.setYscale(-200.0, 0);
		
		Comparable<Object>[] src = (Comparable[]) new Double[N];
		for (int i = 0; i < N; i++) {
			src[i] = (Comparable)(StdRandom.uniform()*6.0);
		}
		privous = src;
		sort(src);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void visualTrace(int N) {
		StdDraw.setCanvasSize(1024, 256);
		
		StdDraw.setXscale(0, 21.0);
		StdDraw.setYscale(-3.0, 0);
		
		Comparable<Object>[] src = (Comparable[]) new Double[N];
		for (int i = 0; i < N; i++) {
			src[i] = (Comparable)(StdRandom.uniform());
		}
		privous = src;
		sortTrace(src);
	}
	
	public static void sort(Comparable<Object>[] a) {
		
		int h = 1;
		int N = a.length;
		while (h < N / 3) {
			h = 3 * h + 1;
		}
		
		while (h >= 1) {
			
			int counter = 1;
			StdDraw.clear();
			AnimationSortCompare.draw(privous, 0, 0);
			
			for (int i = h; i < N; i++) {
				/*
				 * push a[i] to the vacated position
				 */
				int target = i;
				List<Integer> touched = new ArrayList<Integer>();
				for (int j = i; j >= h; j -= h) {
					if (ShellSort.less(a[j], a[j - h])) {
						touched.add(j);
						touched.add(j - h);
						target = j - h;
						ShellSort.exch(a, j, j - h);
					}
				}
				AnimationSortCompare.draw(a, 0, -insertY*(counter++), touched, target);
			}
			
			while(StdIn.readChar() != '\n')
			h /= 3;
		}
	}
	
	public static void sortTrace(Comparable<Object>[] a) {

		int h = 1;
		int N = a.length;
		while (h < N / 3) {
			h = 3 * h + 1;
		}
		
		while (h >= 1) {
			
//			AnimationSortCompare.draw(privous, 0, 0);
			
			for (int i = h; i < N; i++) {
				/*
				 * push a[i] to the vacated position
				 */
				int target = i;
				List<Integer> touched = new ArrayList<Integer>();
				for (int j = i; j >= h; j -= h) {
					if (ShellSort.less(a[j], a[j - h])) {
						touched.add(j);
						touched.add(j - h);
						target = j - h;
						ShellSort.exch(a, j, j - h);
					}
				}
				AnimationSortCompare.draw(a, 0, -visualY, touched, target);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(i != N-1)
					StdDraw.clear();
			}
			
			while(StdIn.readChar() != '\n')
			h /= 3;
		}
		
	}
	
	public static void main(String[] args) {
		
//		sortProcess(30);
		
		visualTrace(30);
		
	}
	
}
