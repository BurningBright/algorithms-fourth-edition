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
		ResizeStack<String> operands = new ResizeStack<String>();
		String oper = src.replaceAll("\\s", "");
		
		for(int i=0; i<oper.length(); i++) {
			String curr = oper.substring(i, i+1);
			if(curr.matches("[0-9]")) {
				operands.push(curr);
			}
			if(curr.matches("[+-/*/]")) {
				operators.push(curr);
			}
			if(curr.matches("\\)")) {
				String tmp = operands.pop()+" )";
				tmp = "( "+operands.pop()+operators.pop()+tmp;
				operands.push(tmp);
			}
		}
		return operands.pop();
	}
	public static void main(String[] args) {
		String src = "1 + 2 ) * 3 - 4 ) * 5 - 6 ) ) )";
		System.out.println(addPare(src));
	}

}
