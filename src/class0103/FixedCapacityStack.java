package class0103;

import stdlib.StdIn;
import stdlib.StdOut;

/**
 * An abstract data type for 
 * a fixed-capacity stack of strings
 * @author soft01
 * 
 */
public class FixedCapacityStack {

	private String[] a; // stack entries
	private int N; // size

	public FixedCapacityStack(int cap) {
		a = new String[cap];
	}

	public boolean isEmpty() {
		return N == 0;
	}

	public int size() {
		return N;
	}

	public void push(String item) {
		a[N++] = item;
	}

	public String pop() {
		return a[--N];
	}

	public boolean isFull() {
		return N==a.length;
	}
	public static void main(String[] args) {
		FixedCapacityStack s;
		s = new FixedCapacityStack(100);
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-")) {
				s.push(item);
			} else if (!s.isEmpty()) {
				StdOut.print(s.pop() + " ");
			}
		}
		StdOut.println("(" + s.size() + " left on stack)");
	}

}
