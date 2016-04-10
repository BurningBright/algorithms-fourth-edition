package class0103;
/**
 * use circular queue to solve josephus
 * kill people problem
 * @author soft01
 *
 */
public class Josephus {
	private int N;
	private int interval;
	
	public Josephus(int N, int interval) {
		this.N = N;
		this.interval = interval;
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
	public void kill() {
		CircularQueue<Man> cq = new CircularQueue<Man>();
		for(int i=0; i<N; i++) {
			cq.enqueue(new Man(i));
		}
		int clip = 1;
		int count = 0;
		
		/*circle iterator*/
		for(Man m: cq) {
			//when all be killed we break circle
			if(count==N) {
				break;
			}
			if(clip%interval==0 && !m.dead) {
				System.out.print(m.num);
				m.dead = true;			//kill
				clip++;
				count++;
			}else if(!m.dead){
				clip++;
			}
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		int interval = Integer.parseInt(args[1]);
		System.out.println(N+" "+interval);
		new Josephus(N, interval).kill();
	}
}
