package class0105;
/**
 * serials test this model's result ratio
 * @author Chen
 *
 */
public class ErdosRenyiSerial {

	public static void main(String[] args) {
		for(int i=10; i<100000; i*=i) {
			QuickUnionUF qu = new QuickUnionUF(i);
			int times = 0;
			while(qu.getCount() != 1) {
				int p = (int) (Math.random()*i);
				int q = (int) (Math.random()*i);
				qu.quickUnion(p, q);
				times++;
			}
			System.out.println(times);
		}
	}

}
