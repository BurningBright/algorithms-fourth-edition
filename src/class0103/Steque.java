package class0103;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * A stack-ended queue
 * @author soft01
 *
 * @param <Item>
 */
public class Steque<Item> implements Iterable<Item> {
	
	private Node first;
	private Node last;
	private int N;
	
	public Steque() {
		first = null;
		last = null;
		N = 0;
	}
	
	private class Node {
		private Item item;
		private Node next;
	}
	
	/**
	 * enter a Node to the head
	 * @param item
	 */
	public void push(Item item) {
		Node newOne = new Node();
		newOne.item = item;
		if(N == 0) {
			first = newOne;
			last = newOne;
			N++;
			return;
		}
		newOne.next = first;
		first = newOne;
		N++;
	}
	
	/**
	 * out a Node from head
	 * @return the head element
	 */
	public Item pop() {
		Node tmp = null;
		if(N == 1) {
			tmp = first;
			first = null;
			last = null;
			N--;
			return tmp.item;
		}
		tmp = first;
		first = first.next;
		tmp.next = null;
		N--;
		return tmp.item;
	}
	
	/**
	 * enter a Node to the end
	 * @param item
	 */
	public void enqueue(Item item) {
		Node newOne = new Node();
		newOne.item = item;
		if(N == 0) {
			first = newOne;
			last = newOne;
			N++;
			return;
		}
		last.next = newOne;
		last = newOne;
		N++;
	}
	
	/**
	 * print the link
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Item i: this) {
			sb.append(i);
		}
		return sb.toString();
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new StequeIterator();
	}
	
	private class StequeIterator implements Iterator<Item> {
		
		private Node current = first;
		
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
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
		Steque<String> ste = new Steque<String>();
		ste.push("aa");
		ste.push("bb");
		ste.push("cc");
		ste.push("dd");
		ste.push("ee");
		System.out.println(ste.toString());
		ste.pop();
		ste.pop();
		System.out.println(ste.toString());
		ste.enqueue("oo");
		System.out.println(ste.toString());
	}

}
