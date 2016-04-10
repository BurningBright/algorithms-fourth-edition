package class0105;

import java.util.ArrayList;

import stdlib.StdDraw;
import stdlib.StdIn;


/**
 * weighted quick union
 * removes the restriction on needing the 
 * number of objects ahead of time. Add a
 * method to add a new set and return 
 * connection size
 * @author Chen
 *
 */
public class DynamicGrowth {
	
	private ArrayList<Integer> id;
	private ArrayList<Integer> sz;
	private int count;
	private double xScale;
	private double yScale;
	
	public DynamicGrowth() {
		this.count = 0;
		id = new ArrayList<Integer>();
		sz = new ArrayList<Integer>();
		
		xScale = 27;
		yScale = 27;
		StdDraw.setXscale(0, xScale);
		StdDraw.setYscale(-yScale, 0);
	}
	
	public int quickFind(int p) {
		//find the root
		while (p != id.get(p)) {
			p = id.get(p);
		}
		return p;
	}
	
	public void wqUnion(int p, int q) {
		int i = quickFind(p);
		int j = quickFind(q);
		if (i == j) {
			return;
		}
		// Make smaller root point to larger one.
		if (sz.get(i) < sz.get(j)) {
			id.set(i, j);
			sz.set(j, sz.get(j)+sz.get(i));
		} else {
			id.set(j, i);
			sz.set(i, sz.get(i)+sz.get(j));
		}
		count--;
	}
	
	public int newSite() {
		id.add(id.size());
		sz.add(1);
		redraw();
		return ++count;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public void redraw() {
		StdDraw.clear();
		
		int N = (int) Math.sqrt(id.size()) + 1;
		
		double offsetX,offsetY;
		offsetX = offsetY = 27/(N-1);
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(i*N+j < id.size()) {					
					StdDraw.filledCircle(i*offsetX, -j*offsetY, 0.1);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		DynamicGrowth dg = new DynamicGrowth();
		
		while(StdIn.readLine().equals("a")) {
			System.out.println(dg.newSite());
		}
	}

}
