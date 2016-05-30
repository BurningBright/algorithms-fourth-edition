package class0103;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * no links are null and the value of last.next is first whenever
 * the list is not empty. Keep only one Node instance variable (last).
 * @author soft01
 *
 */
public class CircularQueue<Item> implements Iterable<Item> {
	
	private int N;
	private Node last;
	
	private class Node{
		private Item item;
		private Node next;
	}
	
	/**
	 * Initializes a circular queue
	 */
	public CircularQueue() {
		last = null;
		N = 0;
	}

	/**
	 * Returns the number of items in this queue.
	 * @return number of items
	 */
	public int size() {
		return N;
	}
	
	/**
	 * Returns the least recently added in this queue
	 * @return the item least recently added to this queue
	 */
	public Item peek() {
		return last.next.item;
	}
	
	/**
	 * Returns the recently added in this queue
	 * @return the item recently added to this queue
	 */
	public Item tail() {
		return last.item;
	}
	
	/**
	 * Adds the item to this queue.
	 * @param item self defined class generic type
	 */
	public void enqueue(Item item) {
		if(last != null) {
			Node enter = new Node();
			enter.item = item;
			enter.next = last.next;
			last.next = enter;
			last = enter;
		}else{
			last = new Node();
			last.item = item;
			last.next = last;
		}
		N++;
	}
	
	/**
	 * Removes and returns the item on this queue that was least recently added.
	 * @return the item on this queue that was least recently added
	 */
	public Item dequeue() {
		if(last != null) {
			Node leave = last.next;
			last.next = leave.next;
			leave.next = null;
			N--;
			return leave.item;
		}else{
			return null;
		}
	}
	
	/**
	 * Returns a string representation of this queue.
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		Node current = last.next;
		if(current != null) {
			for (int i=0; i<N; i++) {
				s.append(current.item +" ");
				current = current.next;
			}
		}
		return s.toString();
	}
	public Iterator<Item> iterator() {
		return new CircularIterator();
	}
	
	private class CircularIterator implements Iterator<Item> {
		
		private Node current = last.next;
		
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	public static void main(String[] args) {
		CircularQueue<String> cq = new CircularQueue<String>();
		cq.enqueue("aa");
		cq.enqueue("bb");
		cq.enqueue("cc");
		cq.enqueue("dd");
		cq.enqueue("ee");
		
		System.out.println(cq.toString());
		System.out.println(cq.dequeue());
		System.out.println(cq.dequeue());
		System.out.println(cq.dequeue());
		System.out.println(cq.toString());
		
		System.out.println(cq.dequeue());
		System.out.println(cq.dequeue());
		System.out.println(cq.toString());
	}
}
