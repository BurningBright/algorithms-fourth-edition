package class0105;

import rlgs4.Queue;
import rlgs4.ResizingArrayBag;
import stdlib.StdDraw;
import stdlib.StdOut;

public class Connectivity {
	private final      double  offsetX = 0.2;
	private final double  offsetY = -0.3;
	private int component = 0;
	private int edge = 0;
	
	private ResizingArrayBag<Connected> conBook;
	
	public Connectivity() {
		conBook = new ResizingArrayBag<Connected>();
		StdDraw.setYscale(-1, 0);
		for(int i=0; i<5; i++) {
			for(int j=0; j<2; j++) {
				StdDraw.filledCircle(0.1*i+offsetX, -0.1*j+offsetY, 0.01);
			}
		}
	}
	
	public void addPair(int a, int b) {
		if(judgeIn(a, b, conBook)) {
			return;
		}else {
			conBook.add(new Connected(a, b));
//			StdOut.println(a+" "+b);
			double x1 = a%5*0.1+offsetX;
			double x2 = b%5*0.1+offsetX;
			double y1 = a/5*-0.1+offsetY;
			double y2 = b/5*-0.1+offsetY;
			StdDraw.line(x1, y1, x2, y2);
		}
	}
	
	public void printComponent() {
		if(conBook.isEmpty()) {
			return;
		}
		Queue<Connected> cur = new Queue<Connected>();
		for(Connected con: conBook) {
			cur.enqueue(con);
		}
		judgeCom(cur);
	}
	
	public void judgeCom(Queue<Connected> cur) {
		ResizingArrayBag<Connected> record = new ResizingArrayBag<Connected>();
		record.add(cur.dequeue());
		component++;
		edge++;
		for(int i=0; i<cur.size(); i++) {
			Connected tmp = cur.dequeue();
			boolean flag = false;
			for(Connected rec: record) {
				if(tmp.isNear(rec)) {
					flag = true;
					record.add(tmp);
					edge++;
					i=-1;
					break;
				}
			}
			if(!flag) {
				cur.enqueue(tmp);
			}
		}
		StdOut.println(print(record));
		if(cur.size()!=0) {
			judgeCom(cur);
		}
	}
	
	/**
	 * this will judge whether the 
	 * connection is in the connection Book
	 * @param p1 point one
	 * @param p2 point two
	 * @return result
	 */
	public boolean judgeIn(int p1,int p2, ResizingArrayBag<Connected> cur) {
		ResizingArrayBag<Connected> tmp;
		int counter=0;
		for(Connected con: cur) {
			/*
			 * if we found the link return true
			 */
			if(con.a==p1 && con.b==p2 || con.b==p1 && con.a==p2) {
				return true;
			}
			
			/*
			 * if only one port be found
			 * we put the rest link into a
			 * bag begin find the other port
			 */
			if(con.a==p1) {
				int newCounter=0;
				tmp = new ResizingArrayBag<Connected>();
				/*copy*/
				for(Connected c: cur) {
					if(newCounter!=counter) {
						tmp.add(c);
					}
					newCounter++;
				}
//				StdOut.println("-"+print(tmp));
				/*begin the new find task*/
				if(judgeIn(con.b, p2, tmp)){
					return true;
				}
			}else if(con.b==p1) {
				int newCounter=0;
				tmp = new ResizingArrayBag<Connected>();
				for(Connected c: cur) {
					if(newCounter!=counter) {
						tmp.add(c);
					}
					newCounter++;
				}
//				StdOut.println("+"+print(tmp));
				/*begin the new find task*/
				if(judgeIn(con.a, p2, tmp)){
					return true;
				}
			}
			counter++;
		}
		return false;
	}
	
	public String print(ResizingArrayBag<Connected> print) {
		StringBuilder sb = new StringBuilder();
		for(Connected c: print) {
			sb.append("[").append(c.a).append(" ").append(c.b).append("] ");
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
//		int[] a = {4, 3, 3, 8, 6, 5, 9, 4, 2, 1, 8, 9, 5, 0, 7, 2, 6, 1, 1, 0, 6, 7};
		int[] a = {4, 3, 3, 8, 6, 5, 9, 4, 2, 1, 8, 9, 5, 0, 7, 2};
		Connectivity conn = new Connectivity();
		for(int i=0; i<a.length; i+=2) {
			conn.addPair(a[i], a[i+1]);
		}
		conn.printComponent();
		StdOut.println(conn.component+"   "+conn.edge);
	}
	
	private class Connected {
		public Integer a;
		public Integer b;
		public Connected(int a, int b) {
			this.a = Integer.valueOf(a);
			this.b = Integer.valueOf(b);
		}
		public boolean isNear(Connected that) {
			return a==that.a || b== that.a || a== that.b || b== that.b;
		}
	}
}