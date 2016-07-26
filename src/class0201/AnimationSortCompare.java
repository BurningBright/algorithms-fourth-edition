package class0201;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import stdlib.StdDraw;
//import stdlib.StdRandom;
import stdlib.StdRandom;

/**
 * to make them draw the array 
 * contents as vertical bars
 * 
 * 1.draw one array vertical bar
 * @author Chen
 *
 */
public class AnimationSortCompare {
	
	//x coordinate's internal distance 
	public static double internalX = .25;
	//Rectangular's half width
	public static double halfWidth = .2;
	
	//the distance between select and insert sort
	private static final double insertX = 11.0;
	//the distance between previous sort and current sort
	private static final double insertY = 1.1;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void sortProgress(int N) {
		Comparable<Object>[] src = (Comparable[]) new Double[N];
		for (int i = 0; i < N; i++) {
//			src[i] = (Comparable)(1.0 - i/20.0);
			src[i] = (Comparable)StdRandom.uniform();
//			src[i] = StdRandom.uniform()>.4? (Comparable).7:(Comparable).3;
		}
		selectSort(src.clone());
		insertSort(src);
	}
	
	/**
	 * this is used in test
	 * have no use now
	 * @param src the array should be draw
	 * @param x	draw begin x coordinate
	 * @param y draw begin y coordinate
	 */
	@SuppressWarnings("rawtypes")
	public static void draw(Comparable[] src, double x, double y) {
		
		for (int i = 0; i < src.length; i++) {
			double curX = i*internalX + halfWidth*(i+1);
			double curY = (Double) src[i]/2;
			StdDraw.filledRectangle(x + curX, y + curY, halfWidth, curY);
		}
	}
	
	/**
	 * draw effect
	 * @param src the array should be draw
	 * @param x draw begin x coordinate
	 * @param y draw begin y coordinate
	 * @param touched number be touched in src array
	 * @param target number be found in src array
	 */
	@SuppressWarnings("rawtypes")
	public static void draw(Comparable[] src, double x, double y,
				List<Integer> touched, int target) {
		
		StdDraw.setPenColor(Color.GRAY);
		for (int i = 0; i < src.length; i++) {
			double curX = i*internalX + halfWidth*(i+1);
			double curY = (Double) src[i]/2;
			
			if(i == target) {
				StdDraw.setPenColor(Color.RED);
				StdDraw.filledRectangle(x + curX, y + curY, halfWidth, curY);
				StdDraw.setPenColor(Color.GRAY);
				continue;
			}
			if(touched.contains(i)) {
				StdDraw.setPenColor();
				StdDraw.filledRectangle(x + curX, y + curY, halfWidth, curY);
				StdDraw.setPenColor(Color.GRAY);
				continue;
			}
			
			StdDraw.filledRectangle(x + curX, y + curY, halfWidth, curY);
		}
	}
	
	public static void selectSort(Comparable<Object>[] a) {
		int N = a.length;
		for (int i = 0; i < N; i++) {
			int min = i;
			List<Integer> touched = new ArrayList<Integer>();
			for (int j = i + 1; j < N; j++) {
				touched.add(j);
				touched.add(min);
				if (less(a[j], a[min])) {
					min = j;
				}
			}
			draw(a, 0, -insertY*i, touched, min);
			exch(a, i, min);
		}
	}
	
	public static void insertSort(Comparable<Object>[] a) {
		int N = a.length;
		
		//insert sort's first sort should be draw directly
		draw(a, insertX, 0, new ArrayList<Integer>(), 0);
		for (int i = 1; i < N; i++) {
			/*
			 * push a[i] to the vacated position
			 */
			int target = i;
			List<Integer> touched = new ArrayList<Integer>();
			for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
				touched.add(j);
				touched.add(j - 1);
				target = j-1;
				exch(a, j, j - 1);
			}
			draw(a, insertX, -insertY*i, touched, target);
		}
	}
	
	private static boolean less(Comparable<Object> v, Comparable<Object> w) {
		return v.compareTo(w) < 0;
	}

	private static void exch(Comparable<Object>[] a, int i, int j) {
		Comparable<Object> t = a[i];
		a[i] = a[j];
		a[j] = t;
	}
	
	public static void main(String[] args) {
		StdDraw.setXscale(0, 21.0);
		StdDraw.setYscale(-21.0, 0);
		sortProgress(20);
	}
}
