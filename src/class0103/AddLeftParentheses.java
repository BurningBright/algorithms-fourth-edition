package class0103;

/**
 * infix expression with 
 * the parentheses inserted
 * 
 * @author soft01
 *
 */
public class AddLeftParentheses {
	public static String addPare(String src) {
		ResizeStack<String> operators = new ResizeStack<String>();
		ResizeStack<String> numbers = new ResizeStack<String>();
		String oper = src.replaceAll("\\s", "");
		
		for(int i=0; i<oper.length(); i++) {
			String curr = oper.substring(i, i+1);
//			String curr = oper.charAt(i) +"";
			if(curr.matches("[0-9]")) {
				numbers.push(curr);
			}
			if(curr.matches("[+-/*/]")) {
				operators.push(curr);
			}
			if(curr.matches("\\)")) {
				// met parenthese two stack out create new number
				String tmp = numbers.pop()+")";
				tmp = "(" + numbers.pop() + operators.pop() + tmp;
				numbers.push(tmp);
			}
		}
		return numbers.pop();
	}
	public static void main(String[] args) {
		String src = "1 + 2 ) * 3 - 4 ) * 5 - 6 ) ) )";
		System.out.println(addPare(src));
	}

}
