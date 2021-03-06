package class0103;

import java.util.Arrays;
import java.util.Iterator;
/**
 * @Description 可变栈
 * @author Leon
 * @date 2016-05-30 10:07:20
 */
public class ResizeStack<Item> implements Iterable<Item> {

	@SuppressWarnings("unchecked")
	private Item[] a = (Item[]) new Object[1]; // stack items
	private int N = 0; // number of items

	public boolean isEmpty() {
		return N == 0;
	}

	public int size() {
		return N;
	}

	private void resize(int max) { // Move stack to a new array of size max.
		@SuppressWarnings("unchecked")
		Item[] temp = (Item[]) new Object[max];
		for (int i = 0; i < N; i++)
			temp[i] = a[i];
		a = temp;
//		System.arraycopy(a, 0, temp, 0, a.length);
	}

	public void push(Item item) { // Add item to top of stack.
		if (N == a.length)
			resize(2 * a.length);
		a[N++] = item;
	}

	public Item pop() { // Remove item from top of stack.
		Item item = a[--N];
		a[N] = null; // Avoid loitering (see text).
		if (N > 0 && N == a.length / 4)
			resize(a.length / 2);
		return item;
	}
	
	public Item getTop() {
		if(isEmpty()){
			return null;
		}
		return a[N-1];
	}
	
	@Override
	public String toString() {
		return "ResizeStack [a=" + Arrays.toString(a) + ", N=" + N + "]";
	}

	public Iterator<Item> iterator() {
		return new ReverseArrayIterator();
	}

	private class ReverseArrayIterator implements Iterator<Item> { // Support
																	// LIFO
																	// iteration.
		private int i = N;

		public boolean hasNext() {
			return i > 0;
		}

		public Item next() {
			return a[--i];
		}

		public void remove() {
		}
	}
}