package class0103;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * a doubly-linked lists, where
 * each node contains a reference 
 * to the item preceding it and the 
 * item following it in the list.
 * 
 * @author soft01
 *
 * @param <Item>
 */

public class DoubleNode<Item> implements Iterable<Item>{
	
	private int N;
	private Node point;
	private Node begin;
	
	/**
	 * initial variable
	 * begin is the link's head
	 * point is the link's end
	 */
	public DoubleNode() {
		point = null;
		N = 0;
	}
	
	/**
	 *the inner class show the Node's structure 
	 * @author soft01
	 */
	private class Node {
		Node prev;
		Item item;
		Node next;
	}
	
	/**
	 * make sure the link empty or not
	 * @return empty or not
	 */
	public boolean isEmpty() {
		return N==0;
	}
	
	/**
	 * get the link node number;
	 * @return node number
	 */
	public int getNum() {
		return N;
	}
	
	/**
	 * add a node when link is empty
	 * @param item
	 * @return empty insert done or not empty
	 */
	public boolean emptySolution(Item item) {
		if(isEmpty()) {
			point = new Node();
			point.item = item;
			begin = point;
			N++;
			return true;
		}
		return false;
	}

	/**
	 * insert a node at the beginning
	 * @param item
	 */
	public void insertBegin(Item item)  {
		if(!emptySolution(item)) {
			Node insert = new Node();
			insert.item = item;

			insert.next = begin;
			begin.prev = insert;
			begin = insert;
			N++;
		}
	}
	
	/**
	 * insert a node at the end
	 * @param item
	 */
	public void insertEnd(Item item) {
		if(!emptySolution(item)) {
			Node insert = new Node();
			insert.item = item;
			
			insert.prev = point;
			point.next = insert;
			point = insert;
			N++;
		}
	}
	
	/**
	 * remove a node from the beginning
	 */
	public Item removeBegin() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		/*when only one node destroy the reference*/
		if(N == 1) {
			Item out = null;
			out = point.item;
			begin = null;
			point = null;
			N--;
			return out;
		}
		Node tmp = begin;
		begin = begin.next;
		/*destroy the no use reference*/
		begin.prev = null;
		tmp.next = null;
		N--;
		return tmp.item;
	}
	
	/**
	 * remove a node from the ending
	 */
	public Item removeEnd() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		
		/*when only one node destroy the reference*/
		if(N == 1) {
			Item out = null;
			out = point.item;
			begin = null;
			point = null;
			N--;
			return out;
		}
		Node tmp = point;
		point = point.prev;
		/*destroy the no use reference*/
		point.next = null;
		tmp.prev = null;
		N--;
		return tmp.item;
	}
	
	/**
	 * insert a node before a given index
	 * @param sequence in the link
	 * @param the insert element
	 */
	public void insertBefore(int index, Item s) {
		/*throw exception when parameter illegal*/
		if(index>N || index <0) {
			throw new NoSuchElementException();
		}
		/*when index point to the head of the link we just insert it*/
		if(index==0 || index==1){
			insertBegin(s);
			return;
		}
		/*prepare data*/
		Node flag = begin;
		Node src = new Node();
		src.item = s;
		
		/*move flag to the target*/
		for(int i=1; i<index; i++) {
			flag = flag.next;
		}
		Node prevOne = flag.prev;
		/*connect previous one and new one*/
		prevOne.next = src;
		src.prev = prevOne;
		/*connect new one and target*/
		src.next = flag;
		flag.prev = src;
		N++;
	}
	
	/**
	 * insert a node after a given index
	 * @param sequence in the link
	 * @param the insert element
	 */
	public void insertAfter(int index, Item s) {
		/*throw exception when parameter illegal*/
		if(index>N || index <0) {
			throw new NoSuchElementException();
		}
		/*when index point to the end of the link we just insert it */
		if(index == N || (N==1 && index==1)){
			insertEnd(s);
			return;
		}
		/*prepare data*/
		Node flag = begin;
		Node src = new Node();
		src.item = s;
		/*move flag to the target*/
		for(int i=1; i<index; i++) {
			flag = flag.next;
		}
		Node nextOne = flag.next;
		/*connect previous one and new one*/
		nextOne.prev = src;
		src.next = nextOne;
		/*connect new one and target*/
		src.prev = flag;
		flag.next = src;
		N++;
	}
	
	/**
	 * remove a given index node from the link
	 * @param index
	 */
	public void removeGiven(int index) {
		/*if the link is empty or parameters illegal we just throw it*/
		if(isEmpty() || index>N || index <1) {
			throw new NoSuchElementException();
		}
		/*if point the head or end just delete it*/
		if(index ==1) {
			removeBegin();
			return;
		}else if(index == N) {
			removeEnd();
			return;
		}
		/*prepare data*/
		Node flag = begin;
		for(int i=1; i<index; i++) {
			flag = flag.next;
		}
		Node prevOne = flag.prev;
		Node nextOne = flag.next;
		/*connect these two variable*/
		prevOne.next = nextOne;
		nextOne.prev = prevOne;
		/*destroy the target reference*/
		flag.next = null;
		flag.prev = null;
		N--;
	}
	
	public Item peek() {
		return begin.item;
	}
	
	public Item tail() {
		return point.item;
	}
	
	/**
	 * used to show data
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Item i: this) {
			sb.append(i);
		}
		return sb.toString();
	}
	
	/**
	 * used to get iterator object
	 */
	public Iterator<Item> iterator() {
		return new  BothWayIterator();
	}
	
	/**
	 * inner class used to implementation iterator
	 * @author soft01
	 */
	private class BothWayIterator implements Iterator<Item>{
		
		Node current = begin;
		
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
		DoubleNode<String> dn = new DoubleNode<String>();
		dn.insertBegin("aa");
		dn.insertBegin("bb");
		dn.insertBegin("cc");
		System.out.println("InsertB: "+dn.toString()+" "+dn.getNum());
		dn.insertEnd("dd");
		System.out.println("InsertE: "+dn.toString()+" "+dn.getNum());
		dn.removeBegin();
		dn.removeBegin();
		System.out.println("RemoveB: "+dn.toString()+" "+dn.getNum());
		dn.removeEnd();
		System.out.println("RemoveE: "+dn.toString()+" "+dn.getNum());
		dn.insertBefore(1, "zz");
		dn.insertBefore(2, "yy");
		System.out.println("InsertBe:"+dn.toString()+" "+dn.getNum());
		dn.insertAfter(2, "bb");
		dn.insertAfter(4, "cc");
		System.out.println("InsertAf:"+dn.toString()+" "+dn.getNum());
		dn.removeGiven(3);
		dn.removeGiven(1);
		dn.removeGiven(3);
		System.out.println("InsertGi:"+dn.toString()+" "+dn.getNum());
	}
}
