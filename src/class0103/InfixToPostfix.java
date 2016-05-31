package class0103;

import java.util.HashMap;

import rlgs4.Stack;
/**
 * @Description 算术表达式的中序遍历变成后序遍历
 * @author Leon
 * @date 2016-05-31 17:08:35
 */
public class InfixToPostfix {
    
    private static HashMap<Character,Integer> priority=new HashMap<Character,Integer>(){
        private static final long serialVersionUID = 3714469304942582582L;
        {
            put('+', 1);
            put('-', 1);
            put('/', 2);
            put('*', 2);
            put('(', 0);
        }
    };
    
    public static boolean isOperator(char c) {
        return c=='+'||c=='-'||c=='*'||c=='/';
    }
    
    /**
     * 将带有计算的优先级和括号的中序表达式inOrder变成符合前述文法的后序表达式
     */
    public static String inToPost(String inOrder) {
        // 保存操作符
        Stack<Character> stack = new Stack<Character>();
        // postfix expresultsion
        String result = "";
        // 保存s.pop()
        char tmp; 
        char[] exp = inOrder.trim().toCharArray();
        
        for (char x : exp) {
            // 如果是操作数，直接输出
            if (!isOperator(x) && (x != '(') && (x != ')')) {
                result = result + x;
            } else if (x == '(') {
                stack.push(x);          // 入栈
            } else if (x == ')') {      // 输出栈中的右操作符直到弹出'('为止
                tmp = stack.pop();
                while (tmp != '(') {
                    result = result + tmp;
                    tmp = stack.pop();
                }
                tmp = '\0';
            } else if (isOperator(x)) {// 与栈顶操作符比较优先级
                if (!stack.isEmpty()) {
                    tmp = stack.pop();
                    int prio1 = priority.get(tmp);
                    int prio2 = priority.get(x);
                    while (prio1 >= prio2) {
                        result = result + tmp;
                        // 如果栈顶优先级高则直接合并，出栈并重算
                        prio1 = -1;
                        if (!stack.isEmpty()) {
                            tmp = stack.pop();
                            prio1 = priority.get(tmp);
                        }
                    }
                    if ((prio1 < prio2) && (prio1 != -1)) {
                        stack.push(tmp);
                    }
                }
                stack.push(x);
            }
        }
        
        while (!stack.isEmpty()) {
            tmp = stack.pop();
            result = result + tmp;
        }
        return result;
    }
    
    
    public static void main(String[] args) {
        System.out.println(inToPost("1+2*3-4/5"));
    }

}
