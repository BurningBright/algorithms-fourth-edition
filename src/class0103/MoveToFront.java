package class0103;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * a b c d e f g d c f e a c a d
 * d->a->c->e->f->g->b->
 * a linked list with no duplicates
 * 
 * applications where items that have been 
 * recently accessed are more likely to be reaccessed
 * @author soft01
 *
 */
public class MoveToFront<Item> implements Iterable<Item> {
	private Node first;
	private int N;
	
	private class Node {
		Item item;
		Node next;
	}
	
	public MoveToFront() {
		first = null;
		N = 0;
	}
	
	/**
	 * check if the item has in link already
	 * if is there pick it to the top of stack
	 * else return false;
	 * @param item
	 * @return is the item in this link?
	 */
	private boolean checkDuplicates(Item item) {
		Node front = first;
		if(front.item.equals(item)) {
			return true;
		}
		for(int i=1; i<N; i++) {
			Node current = front.next;
			if(current.item.equals(item)) {
//				System.out.println(front.item+" "+current.item);
				front.next = current.next;
				current.next = first;
				first = current;
				return true;
			}
			front = front.next;
		}
		return false;
	}
	
	/**
	 * check the data's using frequency
	 * @param item
	 */
	public void haveACheck(Item item) {
		Node in = new Node();
		in.item = item;
		if(N==0) {
			first = in;
			N++;
			return;
		}
		if(!checkDuplicates(item)) {
			in.next = first;
			first = in;
			N++;
		}
	}
	
	/**
	 * show the link
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Item i: this) {
			sb.append(i+"->");
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
		String[] src = {"a", "b", "c", "d", "e", "f", "g", "d", "c", "f", "e", "a", "c", "a", "d"};
		MoveToFront<String> mtf = new MoveToFront<String>();
		for(String s: src) {
			mtf.haveACheck(s);
		}
		System.out.println(mtf.toString());
	}
	
}
