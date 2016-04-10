package class0103;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class DequeRArray<Item> implements Iterable<Item> {
	private Item[] q; // queue elements
	private int N = 0; // number of elements on queue
	private int first = 1; // index of first element of queue
	private int last = 1; // index of next available slot

	/**
	 * Initializes an empty queue.
	 */
	@SuppressWarnings("unchecked")
	public DequeRArray() {
		// cast needed since no generic array creation in Java
		q = (Item[]) new Object[3];
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

	/**
	 * get array's size
	 * @return array's size
	 */
	public int getArraySize() {
		return q.length;
	}
	
	/**
	 * clear all element
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void clear() {
		q = (Item[]) new Object[3];
		first = last = 1;
		N = 0;
	}
	
	/**
	 * add a node when link is empty
	 * @param item
	 * @return empty insert done or not empty
	 */
	public boolean emptySolution(Item item) {
		if(first==last) {
			q[first] = item;
			first = first-1;
			last = last +1;
			N++;
//			System.out.println("empty(item)  :"+first+" "+last);
			return true;
		}
		return false;
	}
	
	/**
	 * pop a node when link is empty
	 * @param item
	 * @return empty insert done or not empty
	 */
	public Item emptySolution() {
		Item item = null;
		if((first+1) == (last-1)) {
			item = q[++first];	//mast put ++font of first
			q[--last] = null;		//mast put -- font of last
			N--;
		}
//		System.out.println("empty(): "+first+" "+last);
		return item;
	}
	
	private void resizeLeft(int max) {
		assert max >= last;
		@SuppressWarnings("unchecked")
		Item[] temp = (Item[]) new Object[max];
		
//		System.out.println(first+" "+last+" "+max);
		int interval = temp.length - q.length;
		int lastPosition = interval+last-1;
		
		for(int i=0; i<N; i++) {
			temp[lastPosition-i] = q[lastPosition-interval-i];
		}
		first += interval;
		last += interval;
		q = temp;
	}
	
	// resize the underlying array
	private void resizeRight(int max) {
		assert max >= last;
		@SuppressWarnings("unchecked")
		Item[] temp = (Item[]) new Object[max];
		
//		System.out.println(first+" "+last);
		for (int i = first+1; i < last+1; i++) {
			temp[i] = q[i];
		}
		q = temp;
	}
	
	/**
	 * Adds the item to this queue's left.
	 * @param item
	 */
	public void pushLeft(Item item) {
		if(emptySolution(item)) {
			return;
		}
		// double size of array if necessary and recopy to front of array
		if (first == 0)
			resizeLeft(2 * q.length); // double size of array if necessary
		q[first--] = item; // add item
		N++;
	}
	
	/**
	 * Adds the item to this queue's right.
	 * @param item
	 *            the item to add
	 */
	public void pushRight(Item item) {
		if(emptySolution(item)) {
			return;
		}
		// double size of array if necessary and recopy to front of array
		if (last == q.length-1) {
//			System.out.println(2 * q.length);
			resizeRight(2 * q.length); // double size of array if necessary
		}
		q[last++] = item; // add item
		N++;
	}

	/**
	 * Removes and returns the item on this queue
	 *  that was least recently added at the left side.
	 * 
	 * @return the item on this queue that was least recently added
	 * @throws java.util.NoSuchElementException
	 *             if this queue is empty
	 */
	public Item popLeft() {
		if (isEmpty()) {
			throw new NoSuchElementException("Queue underflow");
		}
		Item empty = null;
		if((empty=emptySolution())!=null) {
//			System.out.println(empty);
			return empty;
		}
		Item item = q[first+1];
		q[first+1] = null; // to avoid loitering
		N--;
		first++;
		//the only one at the end of array
		if (first == q.length)
			first = 0; // wrap-around
		// shrink size of array if necessary
		if (N > 0 && first == q.length / 2)
			resizeLeft(q.length *3/ 4);
		return item;
	}

	/**
	 * Removes and returns the item on this queue
	 *  that was least recently added at the right side.
	 * @return the item on this queue that was least recently added
	 * @throws java.util.NoSuchElementException
	 *             if this queue is empty
	 */
	public Item popRight() {
		if (isEmpty()) {
			throw new NoSuchElementException("Queue underflow");
		}
		Item empty = null;
		if((empty=emptySolution())!=null) {
//			System.out.println(empty);
			return empty;
		}
		Item item = q[last-1];
		q[last-1] = null; // to avoid loitering
		N--;
		last--;
		// shrink size of array if necessary
		if (N > 0 && last == q.length / 2)
			resizeRight(q.length *3/ 4);
		return item;
	}
	
	/**
	 * Returns the item least recently added to this queue at the left side.
	 * 
	 * @return the item least recently added to this queue
	 * @throws java.util.NoSuchElementException
	 *             if this queue is empty
	 */
	public Item leftPeek() {
		if (isEmpty())
			throw new NoSuchElementException("Queue underflow");
		return q[first+1];
	}

	/**
	 * Returns the item least recently added to this queue at the right side.
	 * 
	 * @return the item least recently added to this queue
	 * @throws java.util.NoSuchElementException
	 *             if this queue is empty
	 */
	public Item rightPeek() {
		if (isEmpty())
			throw new NoSuchElementException("Queue underflow");
		return q[last-1];
	}
	
	/**
	 * used to show data
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i=0; i<q.length; i++) {
			String tmp = (String) (q[i]==null? "null":q[i]);
			sb.append("["+i+" "+tmp+"]");
		}
		sb.append("]");
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
		DequeRArray<String> da = new DequeRArray<String>();
		da.pushRight("aa");
		da.pushRight("bb");
		da.pushRight("cc");
		da.pushRight("cc");
		da.pushRight("cc");
		da.pushRight("cc");
		da.pushRight("cc");
		System.out.println(da.toString());
		for(int i=0; i<7; i++) {
			da.popRight();
		}
		System.out.println(da.toString());
		
		da.pushLeft("aa");
		da.pushLeft("bb");
		da.pushLeft("cc");
		da.pushLeft("cc");
		da.pushLeft("cc");
		da.pushLeft("cc");
		da.pushLeft("cc");
		System.out.println(da.toString());
		for(int i=0; i<7; i++) {
			da.popLeft();
		}
		System.out.println(da.toString());
		
		da.pushRight("aa");
		da.pushLeft("aa");
		da.pushRight("bb");
		da.pushLeft("bb");
		da.pushRight("cc");
		da.pushLeft("cc");
		System.out.println(da.toString());
		for(int i=0; i<6; i++) {
			System.out.print(da.popLeft());
		}
		System.out.println();
	}
}