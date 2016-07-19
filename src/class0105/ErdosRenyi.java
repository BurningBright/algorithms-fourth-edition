package class0105;

/**
 * takes N from the command line
 * calls count(), and prints the returned value
 * @author Chen
 *
 */
public class ErdosRenyi {
	
	public static int count(int N) {
		
		QuickUnionUF qu = new QuickUnionUF(N);
		int times = 0;
		while(qu.count() != 1) {
			int p = (int) (Math.random()*N);
			int q = (int) (Math.random()*N);
			System.out.println(p+" "+q+" "+qu.count());
			
			if(!qu.connected(p, q))
				qu.union(p, q);
			times++;
		}
		
		return times;
	}
	
	public static void main(String[] args) {
		
		System.out.println(count(10));
	} 

}
