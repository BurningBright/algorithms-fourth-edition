package class0105;

/**
 * takes N from the command line
 * calls count(), and prints the returned value
 * @author Chen
 *
 */
public class ErdosRenyi {

	public static void main(String[] args) {
		QuickUnionUF qu = new QuickUnionUF(10);
		int times = 0;
		while(qu.getCount() != 1) {
			int p = (int) (Math.random()*10);
			int q = (int) (Math.random()*10);
			System.out.println(p+" "+q+" "+qu.getCount());
			qu.quickUnion(p, q);
			times++;
		}
		System.out.println(times);
	} 

}
