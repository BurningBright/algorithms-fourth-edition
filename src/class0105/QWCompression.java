package class0105;

import stdlib.StdOut;
import class0103.CircularQueue;
/**
 * Weighted quick-union with path compression
 * @author soft01
 *
 */
public class QWCompression {
	private int[] id;
	private int[] sz;
	private int count;
	
	public QWCompression(int N) {
		this.id = new int[N];
		this.sz = new int[N];
		this.count = N;
		for(int i=0; i<N; i++) {
			id[i] = i;
			sz[i] = 1;
		}
	}
	
	public void quickUnion(int p, int q) {
		CircularQueue<Integer> circP = find(p);
		CircularQueue<Integer> circQ = find(q);
		int rootP = circP.tail();
		int rootQ = circQ.tail();
		
		if(rootP == rootQ) {
			return ;
		}
		
		if(sz[rootP]>sz[rootQ]) {
			sz[rootP]  += sz[rootQ];
			id[rootQ] = rootP;
			/*
			 * the root no need to point to it's self
			 * so use circQ.size()-1, the end of the
			 * queue's element is root.
			 */
			for(int i=0; i<circQ.size()-1; i++) {
				id[circQ.dequeue()] = rootQ;
			}
			
		}else{
			sz[rootQ]  += sz[rootP];
			id[rootP] = rootQ;
			
			for(int i=0; i<circP.size()-1; i++) {
				id[circP.dequeue()] = rootP;
			}
		}
		count--;
//		StdOut.print(p+" "+q+" ");
//		StdOut.print(Arrays.toString(id));
//		StdOut.println(Arrays.toString(sz));
	}
	
	public CircularQueue<Integer> find(int p) {
		CircularQueue<Integer> cq = new CircularQueue<Integer>();
		cq.enqueue(p);
		while(p != id[p]) {
			//find path's next node
			p = id[p];
			cq.enqueue(p);
		}
		//it's p node's root
		return cq;
	}
	
	public int getCount() {
		return count;
	}
	
	public static void main(String[] args) {
		int[] worst = {0, 1, 2, 3, 4, 5, 6, 7, 0, 2, 4, 6, 0, 4};
		QWCompression qwc = new QWCompression(8);
		for(int i=0; i<worst.length; i+=2) {
			qwc.quickUnion(worst[i], worst[i+1]);
		}
		StdOut.println(qwc.getCount());
	}
}
