package class0103;

import stdlib.StdOut;
/**
 * Use two stacks.
 * simulate text edit
 * @author soft01
 *
 */
public class TextEditorBuffer {
	private ResizeStack<Character> ls;
	private ResizeStack<Character> rs;
	private int N;
	private int cursor;
	
	/**
	 * initial buffer
	 */
	public TextEditorBuffer() {
		N = 0;
		cursor = 0;
		ls = new ResizeStack<Character>();
		rs = new ResizeStack<Character>();
	}
	
	/**
	 * cursor move left
	 */
	public void moveLeft(int step) {
		if(step>ls.size()) {
			step=ls.size();
		}
		for(int i=0; i<step; i++) {
			rs.push(ls.pop());
		}
		cursor -= step;
	}
	
	/**
	 * cursor move right
	 */
	public void moveRight(int step) {
		if(step>rs.size()) {
			step = rs.size();
		}
		for(int i=0; i<step; i++) {
			ls.push(rs.pop());
		}
		cursor += step;
	}
	
	/**
	 * replace a char behind the cursor
	 */
	public void insert(char c) {
		if(N==cursor) {
			enter(c);
			return;
		}
		rs.pop();
		ls.push(c);
		cursor++;
	}
	
	/**
	 * the size of this buffer
	 * @return char number
	 */
	public int size() {
		return N;
	}
	
	/**
	 * delete a char in front of the cursor
	 */
	public char delete() {
		if(N==0) {
			throw new RuntimeException();
		}
		if(N==cursor) {
			char left = ls.pop();
			N--;
			cursor--;
			return left;
		}
		char right = rs.pop();
		N--;
		return right;
	}
	
	/**
	 * add a char in front of the cursor
	 */
	public void enter(char c) {
		ls.push(c);
		N++;
		cursor++;
	}
	
	/**
	 * print buffer
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Character c: ls) {
			sb.append(c);
		}
		sb.reverse().append('^');
		for(Character c:rs) {
			sb.append(c);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		TextEditorBuffer teb = new TextEditorBuffer();
		/*enter*/
		for(int i=0; i<20; i++) {
			teb.enter((char)(i+97));
		}
		StdOut.println(teb.toString());
		/*delete*/
		for(int i=0; i<7; i++) {
			teb.delete();
		}
		StdOut.println(teb.toString());
		/*move left*/
		teb.moveLeft(20);
		StdOut.println(teb.toString());
		/*move right*/
		teb.moveRight(12);
		StdOut.println(teb.toString());
		/*insert*/
		teb.insert('n');
		teb.insert('m');
		StdOut.println(teb.toString());
	}
}
