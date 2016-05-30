package class0103;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * @Description 1.3.38
 *          常规队列列表实现
 * @author Leon
 * @date 2016-05-30 16:06:15
 */
public class GeneralizedLQueue<Item> implements Iterable<Item> {
	private int N;
	private Node first;
	private Node last;
	
	private class Node {
		Item item;
		Node next;
	}
	
	public GeneralizedLQueue() {
		N = 0;
		first = null;
		last = null;
	}
	
	public int size() {
		return N;
	}
	
	/**
	 * insert a select into link's last
	 * it's called classic enqueue
	 * @param item
	 */
	public void insert(Item item) {
		Node in = new Node();
		in.item = item;
		if(N==0) {
			first = in;
			last = in;
			N++;
			return;
		}
		last.next = in;
		last = in;
		N++;
	}
	
	/**
	 * delete the index one on the link
	 * @param index
	 * @return delete one
	 */
	public Item delete(int index) {
		if(index<1 || index>N) {
			throw new RuntimeException();
		}
		Item item = null;
		/*if only one element just delete it*/
		if(index == 1) {
			item = first.item;
			first = first.next;
			N--;
			return item;
		}
		/*move*/
		Node front = first;
		Node back = null;
		for(int i=2; i<index; i++) {
			front = front.next;
		}
		back = front.next;
		item = back.item;
		front.next = back.next;
		back.next = null;
		N--;
		return item;
	}

	public Item peek() {
		return first.item;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Item i: this) {
			sb.append(i);
		}
		return sb.toString();
	}
	
	public Iterator<Item> iterator() {
		return new LinkIterator();
	}
	
	private class LinkIterator implements Iterator<Item> {
		
		private Node current = first;
		@Override
		public boolean hasNext() {
			return current!=null;
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
		GeneralizedLQueue<String> glq = new GeneralizedLQueue<String>();
		glq.insert("aa");
		glq.insert("bb");
		glq.insert("cc");
		glq.insert("dd");
		glq.insert("ee");
		System.out.println(glq.toString());
		System.out.println(glq.delete(1));
		System.out.println(glq.toString());
		System.out.println(glq.delete(2));
		System.out.println(glq.toString());
		System.out.println(glq.delete(3));
		System.out.println(glq.toString());
	}
}
