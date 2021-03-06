package class0103;

import java.util.Iterator;
import java.util.NoSuchElementException;

import stdlib.StdRandom;

/**
 * @Description 1.3.35
 *      随机队列
 * @author Leon
 * @date 2016-05-30 15:09:48
 */
public class RandomQueue<Item> implements Iterable<Item> {
	private Item[] q; // queue elements
	private int N = 0; // number of elements on queue
	private int first = 0; // index of first element of queue
	private int last = 0; // index of next available slot

	/**
	 * Initializes an empty queue.
	 */
	@SuppressWarnings("unchecked")
	public RandomQueue() {
		// cast needed since no generic array creation in Java
		q = (Item[]) new Object[2];
	}

	/**
	 * Is this queue empty?
	 * 
	 * @return true if this queue is empty; false otherwise
	 */
	public boolean isEmpty() {
		return N == 0;
	}

	/**
	 * Returns the number of items in this queue.
	 * 
	 * @return the number of items in this queue
	 */
	public int size() {
		return N;
	}

	// resize the underlying array
	private void resize(int max) {
		assert max >= N;
		@SuppressWarnings("unchecked")
		Item[] temp = (Item[]) new Object[max];
		for (int i = 0; i < N; i++) {
			temp[i] = q[(first + i) % q.length];
		}
		q = temp;
		first = 0;
		last = N;
	}

	/**
	 * Adds the item to this queue.
	 * 
	 * @param item
	 *            the item to add
	 */
	public void enqueue(Item item) {
		// double size of array if necessary and recopy to front of array
		if (N == q.length)
			resize(2 * q.length); // double size of array if necessary
		q[last++] = item; // add item
		if (last == q.length)
			last = 0; // wrap-around
		N++;
	}

	/**
	 * Removes and returns the item on this queue that was least recently added.
	 * 
	 * @return the item on this queue that was least recently added
	 * @throws java.util.NoSuchElementException
	 *             if this queue is empty
	 */
	public Item dequeue() {
		if (isEmpty())
			throw new NoSuchElementException("Queue underflow");
		StdRandom.shuffle(q, first, last-1);
		Item item = q[first];
		q[first] = null; // to avoid loitering
		N--;
		first++;
		if (first == q.length)
			first = 0; // wrap-around
		// shrink size of array if necessary
		if (N > 0 && N == q.length / 4)
			resize(q.length / 2);
		return item;
	}
	
	public Item sample() {
		if (isEmpty())
			throw new NoSuchElementException("Queue underflow");
		StdRandom.shuffle(q, first, last-1);
		return q[first];
	}

	/**
	 * Returns the item least recently added to this queue.
	 * 
	 * @return the item least recently added to this queue
	 * @throws java.util.NoSuchElementException
	 *             if this queue is empty
	 */
	public Item peek() {
		if (isEmpty())
			throw new NoSuchElementException("Queue underflow");
		return q[first];
	}

	/**
	 * used to show data
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Item i : this) {
			sb.append(i);
		}
		return sb.toString();
	}

	/**
	 * Returns an iterator that iterates over the items in this queue in FIFO
	 * order.
	 * 
	 * @return an iterator that iterates over the items in this queue in FIFO
	 *         order
	 */
	public Iterator<Item> iterator() {
		return new ArrayIterator();
	}

	// an iterator, doesn't implement remove() since it's optional
	private class ArrayIterator implements Iterator<Item> {
		private int i = 0;
		
		public ArrayIterator() {
			StdRandom.shuffle(q, first, last-1);
		}
		
		public boolean hasNext() {
			return i < N;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = q[(i + first) % q.length];
			i++;
			return item;
		}
	}

	public static void main(String[] args) {
		RandomQueue<String> rq = new RandomQueue<String>();
		rq.enqueue("aa");
		rq.enqueue("bb");
		rq.enqueue("cc");
		rq.enqueue("dd");
		rq.enqueue("ee");
		System.out.println(rq.toString());
		System.out.println(rq.sample());
		System.out.println(rq.toString());
		System.out.println(rq.dequeue());
		System.out.println(rq.toString());
	}
}
