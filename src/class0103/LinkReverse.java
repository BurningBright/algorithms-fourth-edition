package class0103;

/*************************************************************************
 *  Compilation:  javac LinkedBag.java
 *  Execution:    java LinkedBag < input.txt
 *
 *  A generic bag or multiset, implemented using a singly-linked list.
 *
 *  % more tobe.txt 
 *  to be or not to - be - - that - - - is
 *
 *  % java Bag < tobe.txt
 *  size of bag = 14
 *  is
 *  -
 *  -
 *  -
 *  that
 *  -
 *  -
 *  be
 *  -
 *  to
 *  not
 *  or
 *  be
 *  to
 *
 *************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The <tt>LinkedBag</tt> class represents a bag (or multiset) of generic items.
 * It supports insertion and iterating over the items in arbitrary order.
 * <p>
 * This implementation uses a singly-linked list with a non-static nested class
 * Node. See {@link Bag} for a version that uses a static nested class. The
 * <em>add</em>, <em>isEmpty</em>, and <em>size</em> operations take constant
 * time. Iteration takes time proportional to the number of items.
 * <p>
 * For additional documentation, see <a
 * href="http://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 * 
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @author Lin Chen Guang
 */
public class LinkReverse<Item> implements Iterable<Item> {
	private int N; // number of elements in bag
	private Node first; // beginning of bag

	// helper linked list class
	private class Node {
		private Item item;
		private Node next;
	}

	/**
	 * Initializes an empty bag.
	 */
	public LinkReverse() {
		first = null;
		N = 0;
	}

	/**
	 * Is this bag empty?
	 * 
	 * @return true if this bag is empty; false otherwise
	 */
	public boolean isEmpty() {
		return first == null;
	}

	/**
	 * Returns the number of items in this bag.
	 * 
	 * @return the number of items in this bag
	 */
	public int size() {
		return N;
	}

	/**
	 * Adds the item to this bag.
	 * 
	 * @param item
	 *            the item to add to this bag
	 */
	public void add(Item item) {

		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.next = oldfirst;
		N++;
	}

	public void reverse() {
		Node reverse = null;
		while (first != null) {
			Node second = first.next;
			first.next = reverse;
			reverse = first;
			first = second;
		}
		first = reverse;
	}

	public void recursiveReverse() {
		first = reverse(first);
	}

	/**
	 * to a certainty reverse method will put the link that begin with first
	 * reverse
	 * 
	 * @param first
	 * @return
	 */
	public Node reverse(Node first) {
		if (first == null) {
			return null;
		}
		if (first.next == null) {
			return first;
		}
		Node second = first.next;
		Node rest = reverse(second);
		second.next = first;
		first.next = null;
		return rest;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		for (Item i : this) {
			s.append(i + " ");
		}
		return s.toString();
	}

	/**
	 * Returns an iterator that iterates over the items in the bag.
	 */
	public Iterator<Item> iterator() {
		return new ListIterator();
	}

	// an iterator, doesn't implement remove() since it's optional
	private class ListIterator implements Iterator<Item> {
		private Node current = first;

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}

	/**
	 * Unit tests the <tt>LinkedBag</tt> data type.
	 */
	public static void main(String[] args) {
		LinkReverse<String> reve = new LinkReverse<String>();
		reve.add("aa");
		reve.add("bb");
		reve.add("cc");
		reve.add("dd");
		reve.add("ee");
		System.out.println(reve.toString());
		reve.reverse();
		System.out.println(reve.toString());
		reve.recursiveReverse();
		System.out.println(reve.toString());
	}

}
