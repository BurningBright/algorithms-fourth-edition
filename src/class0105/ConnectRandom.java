package class0105;

import rlgs4.Queue;
import rlgs4.ResizingArrayBag;
import stdlib.StdDraw;
import stdlib.StdOut;
import stdlib.StdRandom;

public class ConnectRandom {
	private final double  offsetX = 1;
	private final double  offsetY = -1;
	private int component = 0;
	private int edge = 0;
	
	private ResizingArrayBag<Connected> conBook;
	
	public ConnectRandom() {
		conBook = new ResizingArrayBag<Connected>();
		StdDraw.setXscale(0, 27.0);
		StdDraw.setYscale(-27.0, 0);
		for(int i=0; i<25; i++) {
			for(int j=0; j<25; j++) {
				/*if(i==0) {
					StdDraw.text(i-0.5, -j-1, "Y"+j);
				}
				if(j==0) {
					StdDraw.text(i+1, -j, "X"+i, -90);
				}*/
				StdDraw.filledCircle(i+offsetX, -j+offsetY, 0.1);
			}
		}
	}
	
	public void addPair(int a, int b) {
		if(judgeIn(a, b, conBook)) {
			return;
		}else {
			conBook.add(new Connected(a, b));
//			StdOut.println(a+" "+b);
			double x1 = a%25+offsetX;
			double x2 = b%25+offsetX;
			double y1 = -a/25+offsetY;
			double y2 = -b/25+offsetY;
			StdDraw.line(x1, y1, x2, y2);
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
	
	/**
	 * find out how many components and edges
	 * in the finished map
	 */
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
		Connected draw = cur.dequeue();
		double x1 = draw.a%25+offsetX;
		double y1 = -draw.a/25+offsetY;
		record.add(draw);
		StdDraw.text(x1, y1, component+"");
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
	
	public String print(ResizingArrayBag<Connected> print) {
		StringBuilder sb = new StringBuilder();
		for(Connected c: print) {
			sb.append("[").append(c.a).append(" ").append(c.b).append("] ");
		}
		return sb.toString();
	}
	
	/**
	 * this will give a random legal number
	 * @param num
	 * @return a legal random number
	 */
	public static int createPair(int num) {
		int dir = StdRandom.uniform(4);
		int pair = -1;
		switch(dir) {
			case 0: pair = num+1;break;
			case 1: pair = num-1;break;
			case 2: pair = num+25;break;
			case 3: pair = num-25;break;
		}
//		StdOut.print(" out:"+pair+" ");
		if(pair>624 || pair<0) {
//			StdOut.print(" in:"+pair+" ");
			pair = createPair(num);
		}
		if(pair%25==0 && dir==0) {
//			StdOut.print(" right:"+pair+" ");
			pair = createPair(num);
		}
		if((pair+1)%25==0 && dir==1) {
//			StdOut.print(" left:"+pair+" ");
			pair = createPair(num);
		}
		return pair;
	}
	
	public static void main(String[] args) {
		ConnectRandom conn = new ConnectRandom();
		for(int i=0; i<1000; i++) {
			int ran = StdRandom.uniform(625);
			int pair = createPair(ran);
//			StdOut.println(ran+" "+pair);
			conn.addPair(ran, pair);
		}
		/*int pair = createPair(24);
		conn.addPair(24, pair);*/
		conn.printComponent();
		StdOut.println(conn.component+"  "+conn.edge);
	}

	private class Connected {
		public Integer a;
		public Integer b;
		public Connected(int a, int b) {
			this.a = Integer.valueOf(a);
			this.b = Integer.valueOf(b);
		}
		/**
		 * problem is here
		 * if user == Integer will direct use
		 * it's cache to compare value -128~127
		 * @param that
		 * @return
		 */
		public boolean isNear(Connected that) {
			return a.equals(that.a) || b.equals(that.a) || a.equals(that.b) || b.equals(that.b);
//			return a==that.a || b== that.a || a== that.b || b== that.b;
		}
	}
}
