package class0105;

import class0103.CircularQueue;

/**
 * Uses the same basic strategy as weighted 
 * quick-union but keeps track of tree 
 * height and always links the shorter 
 * tree to the taller one
 * @author soft01
 *
 */
public class QWByHeight {
	private int[] id;
	private int [] dept;
	private int count;
	
	public QWByHeight(int N) {
		this.id = new int[N];
		this.dept = new int[N];
		this.count = N;
		for(int i=0; i<N; i++) {
			id[i] = i;
			dept[i] = 1;
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
		
		if(dept[rootP]>=dept[rootQ]) {
			dept[rootP]  += dept[rootP]==dept[rootQ]? 1:0;
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
			dept[rootQ]  += dept[rootP];
			id[rootP] = rootQ;
			
			for(int i=0; i<circP.size()-1; i++) {
				id[circP.dequeue()] = rootP;
			}
		}
		count--;
//		StdOut.print(p+" "+q+" ");
//		StdOut.print(Arrays.toString(id));
//		StdOut.println(Arrays.toString(dept));
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
	
}
