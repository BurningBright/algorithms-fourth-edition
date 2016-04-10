package class0103;

import java.util.Arrays;
import java.util.Iterator;

public class CopyStack<Item> implements Iterable<Item>,Cloneable{
	
	private Item[] a;
	private int N;
	
	@SuppressWarnings("unchecked")
	public CopyStack(int num) {
		a = (Item[]) new Object[num];
		N = 0;
	}
	
	public boolean isEmpty() {
		return N == 0;
	}

	public int size() {
		return N;
	}

	public void push(Item item) {
		a[N++] = item;
	}

	public Item pop() {
		return a[--N];
	}

	public boolean isFull() {
		return N==a.length;
	}
	
	public String toString() {
		return "CopyStack [a=" + Arrays.toString(a) + ", N=" + N + "]";
	}
	
	public CopyStack<Item> clone() {
		CopyStack<Item> tmp = new CopyStack<Item>(N);
		for(Item i: this) {
			tmp.push(i);
		}
		return tmp;
	}
	
	public Iterator<Item> iterator() {
		return new FixedCapacityStackIterator();
	}

	private class FixedCapacityStackIterator implements Iterator<Item>{
		int i = N;

		public boolean hasNext() {
			return i > 0;
		}

		public Item next() {
			return a[--i];
		}

		public void remove() {
			
		}
	}

	public static CopyStack<Integer> copy(CopyStack<Integer> cs) {
		int size = cs.size();
		CopyStack<Integer> tmp = new CopyStack<Integer>(size);
		Iterator<Integer> i = cs.iterator();
		while (i.hasNext()) {
			Integer s = i.next();
			tmp.push(s);
		}
		return tmp;
	}
	
	public static void main(String[] args) {
		CopyStack<Integer> cs = new CopyStack<Integer>(3);
		cs.push(5);
		cs.push(3);
		cs.push(2);
		System.out.println(copy(cs));
		System.out.println(cs.clone());
	}
}
