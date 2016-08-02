package class0202;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import class0201.AnimationSortCompare;
import stdlib.StdDraw;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * describe: 
 * 		show how merge sort from 
 * 		bottom to top and top down
 * date:	 20150331 10:11:43
 * @author:	 Chen
 *
 */
public class AnimationMergeSortCompare {
	
	//the distance between select and insert sort
	private static final double insertX = 11.0;
	//the distance between previous sort and current sort
	private static final double insertY = 1.75;
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void visualTrace(int N) {
		StdDraw.setCanvasSize(1024, 256);
		
		StdDraw.setXscale(0, 21.0);
		StdDraw.setYscale(-3.0, 0);
		
		/* initial the template array */
		MergeTD.aux = new Comparable[N];
		
		final Comparable<Object>[] src = (Comparable[]) new Double[N];
		for (int i = 0; i < N; i++) {
			src[i] = (Comparable)(StdRandom.uniform());
		}
		
		final VisualTrace vt = new VisualTrace();
		
		Thread bu = new Thread(new Runnable() {
			@Override
			public void run() {
				vt.mergeBUTrace(src.clone());
			}
		});
		
		
		Thread td = new Thread(new Runnable() {
			@Override
			public void run() {
				vt.mergeTDTrace(0, src.length-1, src);
			}
		});
		
		try {
			td.start();
			bu.start();
			bu.join();
			td.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		vt.printSortCount();
	}
	
	class VisualTrace {
		private ReentrantLock lock = new ReentrantLock();
		private Condition visualTrace = lock.newCondition();
		private boolean BURoundDone = false;
		private boolean BUDead = false;
		private boolean TDDead = false;
		private int BUCounter = 0;
		private int TDCounter = 0;
		
		private List<Integer> prvTouched = null;
		private Comparable<Object>[] previous = null;
		
		public void mergeBUTrace(Comparable<Object>[] a) {
			int N = a.length;
			
			for(int sz=1; sz<N; sz += sz) {
				List<Integer> touched = new ArrayList<Integer>();
				for(int i=0; i<N-sz; i+=2*sz) {
					int hi = Math.min(i+2*sz-1, N-1);
					for(int j=i; j<=hi; j++)
						touched.add(j);
					MergeTD.mergeFirst(i, i+sz-1, hi, a);
//					StdOut.println("+++");
					BUCounter++;
					
					try {
						lock.lock();
						// 如果BU一回合结束，则等待TD一回合结束，除非TD死了
						while(BURoundDone == true) {
							visualTrace.await();
						}
						
//						StdOut.println("+++");
						StdDraw.clear();
						AnimationSortCompare.draw(a, 0, -insertY, touched, -1);
						if(previous != null) 
							AnimationSortCompare.draw(previous, insertX, -insertY, prvTouched, -1);
						
						// TD不死，自己更新引用、进入等待、唤醒TD
						if(TDDead == false) {
							BURoundDone = true;
							previous = a;
							prvTouched = touched;
							
							Thread.sleep(1000);
							visualTrace.signal();
						}
						else
							StdOut.println("TD dead first");
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						lock.unlock();
					}
					
				}
			}
			BUDead = true;
			StdOut.println("BU Done");
		}
		
		public void mergeTDTrace(int lo, int hi, Comparable<Object>[] a) {
			if(lo>=hi)	return ;
			int mid = lo + (hi-lo)/2;
			mergeTDTrace(lo, mid, a);
			mergeTDTrace(mid+1, hi, a);		// can't miss +1 because the part is different
			MergeTD.mergeFirst(lo, mid, hi, a);
//			StdOut.println("---");
			TDCounter++;
			
			List<Integer> touched = new ArrayList<Integer>();
			for(int i=lo; i<=hi; i++)
				touched.add(i);
			
			try {
				lock.lock();
				// 如果TD一回合结束，则等待BU一回合结束，除非BU死了
				while(BURoundDone == false) {
					visualTrace.await();
				}
				
//				StdOut.println("---");
				StdDraw.clear();
				AnimationSortCompare.draw(a, insertX, -insertY, touched, -1);
				if(previous != null) 
					AnimationSortCompare.draw(previous, 0, -insertY, prvTouched, -1);
					
				// BU不死，自己更新引用、进入等待、唤醒BU
				if(BUDead == false) {
					BURoundDone = false;
					previous = a;
					prvTouched = touched;
					
					Thread.sleep(1000);
					if(lo == 0 && hi == a.length -1) {
						TDDead = true;
						StdOut.println("TD Done");
					}
					visualTrace.signal();
				}
				else
					StdOut.println("BU dead first");
				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
			
			
			
		}
		
		public void printSortCount() {
			StdOut.println("BU Counter:"+BUCounter +"\t TD Counter:"+TDCounter);
		}
		
	}
	
	
	public static void main(String[] args) {
		(new AnimationMergeSortCompare()).visualTrace(20);
	}

}
