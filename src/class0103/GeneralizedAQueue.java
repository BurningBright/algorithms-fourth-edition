package class0103;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * an array implementation
 * have generalized queue operation
 * @author soft01
 * @param <Item>
 */
public class GeneralizedAQueue<Item> implements Iterable<Item>{
	
	private int N;
	private int first;
	private int last;
	private Item[] q;
	
	@SuppressWarnings("unchecked")
	public GeneralizedAQueue() {
		N = 0;
		first = 0;
		last = 0;
		q = (Item[])(new Object[2]);
	}
	
	public boolean isEmpty() {
		return N==0;
	}

	/**
	 * resize the inner array's size
	 * for saving space
	 * @param max
	 */
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
	 * insert a select into array's last
	 * it's called classic enqueue
	 * @param item
	 */
	public void insert(Item item) {
		if(N == q.length) {
			resize(N*2);
		}
		q[last++] = item;
		/*if no one element in array put last to 0*/
		if (last == q.length) {
			last = 0;
		}
		N++;
	}
	
	/**
	 * delete a pointed element in array
	 * if out of bounds throw out a exception
	 * @param index
	 * @return delete one
	 */
	public Item delete(int index) {
		if(index<1 || index>N) {
			throw new RuntimeException();
		}
		index--;
		/*record first*/
		Item item = q[first+index];
		
		/*put pointed element to the last reference*/
		q[last] = q[index];
		/*move element*/
		for(int i=index; i>first; i--) {
			q[i] = q[i-1];
		}
		/*destroy reference*/
		q[last] = null;
		q[first] = null;
		/*set arguments*/
		N--;
		first++;
		if (first == q.length) {
			first = 0;
		}
		if (N > 0 && N == q.length/4) {
			resize(q.length/2); 
		}
		return item;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		/*for(Item i: this) {
			sb.append(i);
		}*/
		sb.append("[");
		for(int i=0; i<q.length; i++) {
			sb.append("["+i+" ");
			String j = (String) (q[i]==null? "null":q[i]);
			sb.append(j+"]");
		}
		sb.append("]");
		return sb.toString();
	}

	@Override
	public Iterator<Item> iterator() {
		return new ArrayIterator();
	}
	
	private class ArrayIterator implements Iterator<Item> {
		private int i = 0;
		@Override
		public boolean hasNext() {
			return i < N;
		}

		@Override
		public Item next() {
			if (!hasNext())
				throw new NoSuchElementException();
			Item item = q[(i + first) % q.length];
			i++;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	public static void main(String[] args) {
		GeneralizedAQueue<String> gaq = new GeneralizedAQueue<String>();
		gaq.insert("aa");
		gaq.insert("bb");
		gaq.insert("cc");
		gaq.insert("dd");
		gaq.insert("ee");
		System.out.println(gaq.toString());
		System.out.println(gaq.delete(1));
		System.out.println(gaq.toString());
		System.out.println(gaq.delete(2));
		System.out.println(gaq.toString());
		System.out.println(gaq.delete(3));
		System.out.println(gaq.toString());
	}
}
