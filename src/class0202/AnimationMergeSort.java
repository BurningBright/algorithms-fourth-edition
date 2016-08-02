package class0202;

import class0201.AnimationSortCompare;
import stdlib.StdDraw;
import stdlib.StdRandom;

/**
 * describe: 
 * 		show how merge sort from 
 * 		bottom to top and top down
 * date:	 20150331 10:11:43
 * @author:	 Chen
 *
 */
public class AnimationMergeSort {
	
	//the distance between select and insert sort
	private static final double insertX = 21.0;
	//the distance between previous sort and current sort
	private static final double insertY = 1.5;
	//record the current row num
	private static int flag = 0;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void sortProcess(int N) {
		AnimationSortCompare.internalX = .16;
		AnimationSortCompare.halfWidth = .13;
		
		StdDraw.setCanvasSize(1024, 768);
		StdDraw.setXscale(0, 40.0);
		StdDraw.setYscale(-13.0, 0);
		
		/* initial the template array */
		MergeTD.aux = new Comparable[N];
		
		Comparable<Object>[] src = (Comparable[]) new Double[N];
		for (int i = 0; i < N; i++) {
			src[i] = (Comparable)(StdRandom.uniform());
		}
		mergeBU(src.clone());
		flag = 0;				//reset the flag
		mergeTD(0, src.length-1, src);
	}
	
	public static void mergeBU(Comparable<Object>[] a) {
		int size = a.length;
		for(int sz=1; sz<size; sz += sz) {
			for(int i=0; i<size-sz; i+=2*sz) {
				MergeTD.mergeFirst(i, i+sz-1, Math.min(i+2*sz-1, size-1), a);
			}
			AnimationSortCompare.draw(a, 0, -insertY*++flag);
		}
	}
	
	public static void mergeTD(int lo, int hi, Comparable<Object>[] a) {
		if(lo>=hi)	return ;
		int mid = lo + (hi-lo)/2;
		mergeTD(lo, mid, a);
		mergeTD(mid+1, hi, a);		// can't miss +1 because the part is different
		MergeTD.mergeFirst(lo, mid, hi, a);
		if(hi-lo>10)
			AnimationSortCompare.draw(a, insertX, -insertY*++flag);
	}
	
	public static void main(String[] args) {
		sortProcess(70);
	}

}
