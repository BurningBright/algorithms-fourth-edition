package class0103;

//import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import rlgs4.ResizingArrayBag;
import stdlib.StdOut;
import stdlib.StdRandom;
/**
 * @Description 1.3.34
 *      一个不能删的随机包
 * @author Leon
 * @date 2016-05-30 15:07:21
 */
public class RandomBag<Item> extends ResizingArrayBag<Item> {
	
	private Item[] src;
	public RandomBag() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public void doRandom() {
		src = (Item[])(new Object[super.size()]);
		Iterator<Item> up = super.iterator();
		int i=0;
		while(up.hasNext()) {
			src[i] = up.next();
			i++;
		}
		StdRandom.shuffle(src);
//		System.out.println(Arrays.toString(src));
	}
	
	public int ranSize() {
		return super.size();
	}
	
	/**
	 * Returns an iterator that iterates over 
	 * the items in the bag in arbitrary order.
	 * 
	 * @return an iterator that iterates over the 
	 * 			items in the bag in arbitrary order
	 */
	public Iterator<Item> iterator() {
		doRandom();
		return new RandomIterator();
	}
	
	private class RandomIterator implements Iterator<Item> {
		private int i = 0;
		
		public RandomIterator() {
			StdRandom.shuffle(src);
		}
		
		@Override
		public boolean hasNext() {
			return ranSize()>i;
		}

		@Override
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			return src[i++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	public static void main(String[] args) {
		RandomBag<String> bag = new RandomBag<String>();
		bag.add("Hello ");
		bag.add("random ");
		bag.add("bag ");
		bag.add("how ");
		bag.add("are ");
		bag.add("you ");
		
		for(String i: bag) {
			StdOut.print(i);
		}
		StdOut.println();
	}
}
