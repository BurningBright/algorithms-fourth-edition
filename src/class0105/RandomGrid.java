package class0105;

import java.util.Arrays;
import java.util.Iterator;

import stdlib.StdRandom;
import class0103.RandomBag;

/**
 * takes an int value N from the command line, 
 * generates all the connections in an N-by-N grid, 
 * puts them in random order, randomly orients them 
 * (so that p q and q p are equally likely to occur), 
 * and prints the result to standard output
 * 
 * @author Chen
 *
 */
public class RandomGrid{

	public static Connection[] generate(int N) {
		
		RandomBag<Connection> rb = new RandomBag<Connection>();
		
		QuickUnionUF qu = new QuickUnionUF(N);
		
		int preCount = 0;
		while(qu.count() != 1) {
			int p = (int) (StdRandom.uniform()*N);
			int q = (int) (StdRandom.uniform()*N);
			qu.union(p, q);
			if(preCount != qu.count()) {
				rb.add(new Connection(p, q));
				preCount = qu.count();
			}
		}
		
		Iterator<Connection> it = rb.iterator();
		Connection[] con = new Connection[rb.size()];
		
		for(int i=0; it.hasNext(); i++) {
			con[i] = it.next();
		}
		
		return con;
	}
	
	public static void main(String[] args) {
		Connection[] con = RandomGrid.generate(3);
		System.out.println(Arrays.toString(con));
	}

	static class Connection {
		int p;
		int q;

		public Connection(int p, int q) {
			this.p = p;
			this.q = q;
		}

		@Override
		public String toString() {
			return "Connection [p=" + p + ", q=" + q + "]";
		}
		
	}
}
