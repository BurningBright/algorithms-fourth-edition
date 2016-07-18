package class0105;

import rlgs4.Stopwatch;

/**
 * serials test this model's result ratio
 * @author Chen
 *
 */
public class ErdosRenyiSerial {

	public static void main(String[] args) {
		for(int i=1000; i<100000; i*=2) {
			QuickUnionUF qu = new QuickUnionUF(i);
			int times = 0;
			Stopwatch sw = new Stopwatch();
			while(qu.count() != 1) {
				int p = (int) (Math.random()*i);
				int q = (int) (Math.random()*i);
				qu.union(p, q);
				times++;
			}
			System.out.println(i+" "+sw.elapsedTime()+" "+times);
		}
	}

}
