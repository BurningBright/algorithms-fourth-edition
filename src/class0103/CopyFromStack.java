package class0103;
/**
 * reference to a new and
 *  independent copy of the stack
 * @author soft01
 *
 */
public class CopyFromStack<A> extends ResizeStack<A>{
	public CopyFromStack(ResizeStack<A> stack) {
		super();
		ResizeStack<A> tmp = new ResizeStack<A>();
		for(int i=stack.size(); i>0; i--) {
			tmp.push(stack.pop());
		}
		for(int i=tmp.size(); i>0; i--) {
			A current = tmp.pop();
			this.push(current);
			stack.push(current);
		}
	}
	
	public static void main(String[] args) {
		ResizeStack<String> stack = new ResizeStack<String>();
		stack.push("a");
		stack.push("b");
		stack.push("c");
		CopyFromStack<String> ct = new CopyFromStack<String>(stack);
		System.out.println(stack.toString());
		ct.pop();
		System.out.println(ct.toString());
	}
}
