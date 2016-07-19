package class0105;

import class0103.CircularQueue;
import stdlib.StdOut;
/**
 * Weighted quick-union with path compression
 * 使用用了循环队列就不用再维护权重数组
 * @author soft01
 *
 */
public class WeightedQickUnionPathQUF {
	private int[] id;
	private int count;
	
	public WeightedQickUnionPathQUF(int N) {
		this.id = new int[N];
		this.count = N;
		for(int i=0; i<N; i++) {
			id[i] = i;
		}
	}
	
	public void union(int p, int q) {
		CircularQueue<Integer> circP = find(p);
		CircularQueue<Integer> circQ = find(q);
		int rootP = circP.tail();
		int rootQ = circQ.tail();
//		StdOut.print(p + "  " + q + "  t:" +circQ.tail() + " ");
		
		if(rootP == rootQ) {
//			StdOut.println();
			return ;
		}
		
		if(circP.size() > circQ.size()) {
			
			/*
			 * the root no need to point to it's self
			 * so use circQ.size()-1, the end of the
			 * queue's element is root.
			 */
			int size = circQ.size();
			for(int i=0; i<size; i++) {
				id[circQ.dequeue()] = rootP;
			}
//			StdOut.print("-");
		}else{
			
			int size = circP.size();
			for(int i=0; i<size; i++) {
				id[circP.dequeue()] = rootQ;
			}
//			StdOut.print("+");
		}
		count--;
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
		int[] a = { 4, 3, 3, 8, 6, 5, 9, 4, 2, 1, 5, 0, 7, 2, 6, 1, 1, 0, 6, 7 };
		WeightedQickUnionPathQUF qwc = new WeightedQickUnionPathQUF(10);
		for(int i=0; i<a.length; i+=2) {
			qwc.union(a[i], a[i+1]);
		}
//		qwc.union(0, 1);
//		qwc.union(2, 3);
//		qwc.union(0, 2);
		StdOut.println(qwc.getCount());
	}
}
