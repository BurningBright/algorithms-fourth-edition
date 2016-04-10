package class0105;

import rlgs4.Stopwatch;

/**
 * model's performance test
 * 
 * @author Chen
 *
 */
public class ErdosRenyiPerf {

	public static double performance(int N) {
		Stopwatch timer = new Stopwatch();

//		QuickFindUF qu = new QuickFindUF(N);
//		QuickUnionUF qu = new QuickUnionUF(N);
//		QWByHeight qu = new QWByHeight(N);
		QWCompression qu = new QWCompression(N);

		int times = 0;
		while (qu.getCount() != 1) {
			int p = (int) (Math.random() * N);
			int q = (int) (Math.random() * N);
			qu.quickUnion(p, q);
			times++;
		}
		System.out.print(times);
		return timer.elapsedTime();
	}

	public static void main(String[] args) {
		
		double proPre = performance(5000);
		double progress = 0;
		for (int i = 10000; i < 100000; i *= 2) {
			progress = performance(i);
			System.out.println(" " + progress/proPre);
		}
		proPre = progress;
	}

}
