package class0103;

/**
 * uses a stack to determine whether its 
 * parentheses are properly balanced
 * @author soft01
 *
 */
public class ParenthesesBalance {
	
	public static boolean check(String src) {
		ResizeStack<Character> stack = new ResizeStack<Character>();
		for(int i=0; i<src.length(); i++) {
			char current = src.charAt(i);
			switch(current) {
			case ')':
				//when the previous item is '(' then pass
				if(stack.getTop()!=null && '('==stack.getTop()) {
					stack.pop();
				}else{
					return false;
				}break;
			case ']':
				if(stack.getTop()!=null && '['==stack.getTop()) {
					stack.pop();
				}else{
					return false;
				}break;
			case '}':
				if(stack.getTop()!=null && '{'==stack.getTop()) {
					stack.pop();
				}else{
					return false;
				}break;
			default: stack.push(current);break;
			}
			
			System.out.println(stack.toString());
		}
		return true;
	}
	
	public static void main(String[] args) {
		String src = "[()]{}{[()()]()}";
		String src_1 = "[(])";
		System.out.println(check(src));
		System.out.println(check(src_1));
	}

}
