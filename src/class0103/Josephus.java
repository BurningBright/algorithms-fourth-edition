package class0103;

import java.util.Iterator;

/**
 * use circular queue to solve josephus
 * kill people problem
 * 
 * update custom kill function
 * @author soft01
 * @date 2016-05-30 16:05:00
 */
public class Josephus {
	private int N;
	private int S;
	private int interval;
	private CircularQueue<Man> cq;
	
	public Josephus(int N, int interval) {
		if(interval > N) 
			throw new IllegalArgumentException();
		this.N = N;
		this.interval = interval;
		
		cq = new CircularQueue<Man>();
		for (int i = 0; i < N; i++) 
			cq.enqueue(new Man(i));
	}
	
	/**
	 * @param N people in game
	 * @param S people survive
	 * @param interval step
	 */
	public Josephus(int N, int S,int interval) {
		this(N, interval);
		if(S > N) 
			throw new IllegalArgumentException();
		this.S = S;
	}
	
	/**
	 * inner class show man in queue
	 * @author soft01
	 *
	 */
	private class Man {
		int num;
		boolean dead;
		public Man(int num) {
			this.num = num;
			this.dead = false;
		}
	}
	
	/**
	 * kill the man in queue
	 */
	public Josephus kill() {
		
		int clip = 1;
		int count = 0;

		/* circle iterator */
		for (Man m : cq) {
			// when all be killed we break circle
			if (count == N) {
				break;
			}
			if (clip % interval == 0 && !m.dead) {
				System.out.print(m.num+" -> ");
				m.dead = true; // kill
				clip++;
				count++;
			} else if (!m.dead) {
				clip++;
			}
		}
		System.out.println();
		return this;
	}
	
	public Josephus customKill() {
		
		int clip = 1;
		int count = 0;
		
		/*circle iterator*/
		for(Man m: cq) {
			// when all be killed we break circle
			if (count == N-S) {
				break;
			}
			if (clip % interval == 0 && !m.dead) {
				System.out.print(m.num+" -> ");
				m.dead = true; // kill
				clip++;
				count++;
			} else if (!m.dead) {
				clip++;
			}
		}
		System.out.println();
		return this;
	}
	
	/**
	 * people live
	 */
	public void survive() {
		boolean flag = false;
		Iterator<Man> it = cq.iterator();
		for(int i=0; i<N; i++) {
			Man lucky = it.next();
			if(!lucky.dead) {
				System.out.print(lucky.num+" v ");
				flag = true;
			}
		}
		
		System.out.println(flag ? "":"no one servived");
	}
	
	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		int S = Integer.parseInt(args[1]);
		int interval = Integer.parseInt(args[2]);
		System.out.println(N+" "+interval);
		new Josephus(N, interval).kill().survive();
		System.out.println("---------------------");
		System.out.println(N+" "+S+" "+interval);
		new Josephus(N, S, interval).customKill().survive();
	}
	
}
