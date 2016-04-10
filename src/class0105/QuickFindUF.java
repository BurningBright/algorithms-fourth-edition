package class0105;
/**
 * quick find respectively
 * @author soft01
 *
 */
public class QuickFindUF {
	private int[] id;
	private int count;
	
	public QuickFindUF(int N) {
		id = new int[N];
		count = N;
		
		for(int i=0; i<N; i++) {
			id[i] = i;
		}
	}
	
	public void quickUnion(int p, int q) {
		int pID = find(p);
		int qID = find(q);
		if (pID == qID) return;

		for(int i = 0; i < id.length; i++) {
			if(id[i]==pID) {
				id[i] = qID;
			}
		}
		count--;
	}
	
	public int find(int p) {
		return id[p];
	}
	
	public int getCount() {
		return count;
	}
	
}
