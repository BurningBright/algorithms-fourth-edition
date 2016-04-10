package class0105;
/**
 * quick union respectively
 * @author soft01
 *
 */
public class QuickUnionUF {
	private int[] id;
	private int count;
	
	public QuickUnionUF(int N) {
		this.count = N;
		id = new int[N];
		
		for(int i = 0; i < N; i++) {
			id[i] = i;
		}
		
	}
	public  void quickUnion(int p, int q) {
		// p's root element component number
		int rootP = find(p);
		// q's root element component number
		int rootQ = find(q);
		if (rootP == rootQ) {
			return;
		}
		/*
		 * if not equal put p's root point 
		 * to the q's root component number
		 */
		id[rootP] = rootQ;
		count--;
	}
	
	public int find(int p) {
		while(p != id[p]) {
			p = id[p];
		}
		return p;
	}
	
	public int getCount() {
		return count;
	}
	
	public static void main(String[] args) {
		QuickUnionUF qu = new QuickUnionUF(10);
		qu.quickUnion(0, 1);
		System.out.println(qu.getCount());
	}
}
