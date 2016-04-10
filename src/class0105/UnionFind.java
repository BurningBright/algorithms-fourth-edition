package class0105;

import java.util.Arrays;

import stdlib.StdDraw;
import stdlib.StdOut;
/**
 * union find/quick union/ wight quick union
 * @author soft01
 *
 */
public class UnionFind {
	private int[] id;
	private int[] sz;
	private int count;
	private double[][] dots;
	
	public UnionFind(int N) {
		this.count = N;
		id = new int[N];
		sz = new int[N];
		
		for(int i = 0; i < N; i++) {
			id[i] = i;
		}
		
		for (int i = 0; i < N; i++) {
			sz[i] = 1;
		}
		
		double cenX, cenY, cenR, dotR;
		cenX = cenY = .5;
		cenR = .4;
		dotR = 0.005;
		dots = new double[N][2];
		
		for (int i = 0; i < N; i++) {
			double degree = 2 * Math.PI * i / N;
			double calX = Math.sin(degree) * cenR + cenX;
			double calY = Math.cos(degree) * cenR + cenY;
			
			double textX = Math.sin(degree) * .45 + cenX;
			double textY = Math.cos(degree) * .45 + cenY;
			
			dots[i][0] = calX;
			dots[i][1] = calY;

			StdDraw.circle(calX, calY, dotR);
			StdDraw.filledCircle(calX, calY, dotR);
			StdDraw.text(textX, textY, i+"");
		}
		
	}
	
	public void union(int p, int q) {
		int pID = find(p);
		int qID = find(q);
		if (pID == qID) return;

		for(int i = 0; i < id.length; i++) {
			if(id[i]==pID) {
				id[i] = qID;
			}
		}
		count--;
//		StdOut.print(p+" "+q+" ");
//		StdOut.println(Arrays.toString(id));
//		StdDraw.line(dots[p][0], dots[p][1], dots[q][0], dots[q][1]);
	}
	
	public int find(int p) {
		return id[p];
	}
	
	public void qUnion(int p, int q) {
		//p's root element component number
		int rootP = qFind(p);
		//q's root element component number
		int rootQ = qFind(q);
		if(rootP==rootQ) {
			return ;
		}
		/*
		 * if not equal put p's root point to
		 * the q's root component number 
		 */
		id[rootP] = rootQ;
		count--;
//		StdOut.print(p+" "+q+" ");
//		StdOut.println(Arrays.toString(id));
//		StdDraw.line(dots[p][0], dots[p][1], dots[q][0], dots[q][1]);
	}
	
	public int qFind(int p) {
		//find the root
		while (p != id[p]) {
			p = id[p];
		}
		return p;
	}
	
	public void wqUnion(int p, int q) {
		int i = qFind(p);
		int j = qFind(q);
		if (i == j) {
			return;
		}
		// Make smaller root point to larger one.
		if (sz[i] < sz[j]) {
			id[i] = j;
			sz[j] += sz[i];
		} else {
			id[j] = i;
			sz[i] += sz[j];
		}
		count--;
		StdOut.print(p+" "+q+" ");
		StdOut.print(Arrays.toString(id));
		StdOut.println(Arrays.toString(sz));
		StdDraw.line(dots[p][0], dots[p][1], dots[q][0], dots[q][1]);
	}
	
	public int count() {
		return count;
	}
	
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
//		int[] a = {9, 0, 3, 4, 5, 8, 7, 2, 2, 1, 5, 7, 0, 3, 4, 2};
		int[] reference = {4, 3, 3, 8, 6, 5, 9, 4, 2, 1, 8, 9, 5, 0, 7, 2, 6, 1, 1, 0, 6, 7};
		int[] worst = {0, 1, 2, 3, 4, 5, 6, 7, 0, 2, 4, 6, 0, 4};
		
		UnionFind uf = new UnionFind(worst.length);
		/*for(int i=0; i<reference.length; i+=2) {
			uf.wqUnion(reference[i], reference[i+1]);
		}*/
		for(int i=0; i<worst.length; i+=2) {
//			uf.wqUnion(worst[i], worst[i+1]);
			uf.qUnion(worst[i], worst[i+1]);
		}
		StdOut.println(uf.count());
	}
}
