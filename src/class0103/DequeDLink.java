package class0103;
/**
 * extends double link
 * @author soft01
 * @param <Item>
 */
public class DequeDLink<Item> extends DoubleNode<Item>{
	public DequeDLink() {
		super();
	}
	
	public int size() {
		return super.getNum();
	}
	
	public void pushLeft(Item item) {
		super.insertBegin(item);
	}
	
	public void pushRight(Item item) {
		super.insertEnd(item);
	}
	
	public Item popLeft() {
		return super.removeBegin();
	}
	
	public Item popRight() {
		return super.removeEnd();
	}
	
	public static void main(String[] args) {
		DequeDLink<String> de = new DequeDLink<String>();
		de.pushLeft("aa");
		de.pushLeft("bb");
		de.pushLeft("cc");
		de.pushRight("dd");
		de.pushRight("ee");
		System.out.println(de.toString());
		de.popRight();
		System.out.println(de.toString());
		de.popLeft();
		System.out.println(de.toString());
	}
}
