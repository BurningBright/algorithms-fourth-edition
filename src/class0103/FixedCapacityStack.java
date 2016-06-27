package class0103;

import stdlib.StdIn;
import stdlib.StdOut;

/**
 * An abstract data type for 
 * a fixed-capacity stack of strings
 * @author soft01
 * 
 */
public class FixedCapacityStack<T extends Object> {

	private Object[] a; // stack entries
	private int N; // size

	public FixedCapacityStack(int cap) {
		a = new Object[cap];
	}

	public boolean isEmpty() {
		return N == 0;
	}

	public int size() {
		return N;
	}

	public void push(T item) {
		a[N++] = item;
	}

	@SuppressWarnings("unchecked")
    public T pop() {
		return (T)a[--N];
	}

	public boolean isFull() {
		return N==a.length;
	}
	public static void main(String[] args) {
		FixedCapacityStack<String> s;
		s = new FixedCapacityStack<String>(100);
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-")) {
				s.push(item);
			} else if (!s.isEmpty()) {
				StdOut.print(s.pop() + " ");
				break;
			}
		}
		StdOut.println("(" + s.size() + " left on stack)");
	}

}
