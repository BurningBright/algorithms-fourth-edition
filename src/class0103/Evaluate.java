package class0103;

import rlgs4.Queue;
import rlgs4.Stack;
/**
 * @Description 
 *      输入字符串形式的表达式，计算表达式结果
 * @author Leon
 * @date 2016-05-31 11:27:04
 */
public class Evaluate {
    
    /*
     * 操作符1优先级大于操作符2   --LEVEL_BIGGER 
     * 操作符1优先级小于操作符2   --LEVEL_SMALLER 
     * 操作符1优先级等于操作符2   --LEVEL_SAME 
     * 操作符1操作符2对比不合法   --LEVEL_INVALID 
     */
    private static final int LEVEL_SMALLER = 1;
    private static final int LEVEL_BIGGER= 2;
    private static final int LEVEL_SAME= 0;
    private static final int LEVEL_INVALID = -1;
    
    /**
     * 这里'+'和'-'，'*'和'/'在实际四则运算时的优先顺序 
     *      是相同的,所以这里的优先级判定时设置两个操作符级别 
     *      列表,一个操作符列表是把另一个操作符级别列表中的相 
     *      同级别的操作符的顺序做了颠倒 
     * @param operator1
     * @param operator2
     * @return
     */
    public static int CompareLevel(char operator1, char operator2) {

        char levelTable1[] = { '#', '(', '+', '-', '*', '/', ')' };
        char levelTable2[] = { '#', ')', '-', '+', '/', '*', '(' };
        int nTable1Index1, nTable1Index2;
        int nTable2Index1, nTable2Index2;

        // 不合法情况判断
        if ((operator1 == ')' && operator2 == '(') || (operator1 == '#' && operator2 == ')')
                || (operator1 == '(' && operator2 == '#')) {
            return LEVEL_INVALID;
        }
        // 判断相等情况
        if ((operator1 == '(' && operator2 == ')') || (operator1 == '#' && operator2 == '#')) {
            return LEVEL_SAME;
        }
        
        // 判断两个操作符在两个优先级表中的位置
        nTable1Index1 = nTable1Index2 = -1;
        nTable2Index1 = nTable2Index2 = -1;
        while (levelTable1[++nTable1Index1] != operator1);
        while (levelTable1[++nTable1Index2] != operator2);
        while (levelTable2[++nTable2Index1] != operator1);
        while (levelTable2[++nTable2Index2] != operator2);
        
        // 1.判断两个操作符的优先级关系
        // 2.'#' < '+', '-' < '*', '/'
        // 3.两个相同的运算符或同级别的运算符('+'和'-'，'*'和'/')
        // 对比时应判定为前一个运算符优先级别高,这样才能保证连续
        // 两个相同的运算符或同级别的运算符出现时前一个运算符出栈
        // 完成一次计算
        if (nTable1Index1 - nTable1Index2 < 0 && nTable2Index1 - nTable2Index2 < 0 || operator1 == '('
                || operator2 == '(') {
            return LEVEL_SMALLER;
        } else {
            return LEVEL_BIGGER;
        }
    }
    
    private static void transNum(Queue<String> src, String exp) {
        String tmp = "";
        for(char c: exp.toCharArray()) {
            if(c >= '0' && c <= '9' || c=='.') {
                tmp += c;
            } else {
                if(tmp != "")
                    src.enqueue(tmp);
                src.enqueue(c+"");
                tmp = "";
            }
        }
        if(tmp != "")
            src.enqueue(tmp);
    }
    
    private static boolean isNum(String src) {
        try {
            Double.parseDouble(src);
            return true;
        } catch(Exception e) {
            return false;
        }
    }
    
    private static double calc(double num1, double num2, char c) {
        switch(c) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                if(num2 == 0) 
                    num1 = 1/0;
                return num1 / num2;
        }
        return 0;
    }
    
    public static double evaExpression(String exp) {
        
        Stack<Character> ops = new Stack<Character>();
        Stack<Double> vals = new Stack<Double>();
        Queue<String> src = new Queue<String>();
        
        transNum(src, exp);
        
        // 检查是否非法
        boolean flag = true;
        // 结果、运算1、运算2
        double num1, num2;
        
        // 初始化操作符栈,入栈'#'表示计算的开始和结束的'#'对应
        ops.push('#');
        
        while(!src.isEmpty()) {
            
            String pCur = src.peek();
            if(isNum(pCur)) {
                // 数字逻辑
                vals.push(Double.parseDouble(pCur));
                src.dequeue();
                
                // 遍历数据源后清空余留操作
                if(src.isEmpty() && vals.size()!=1){
                    while(vals.size()!=1) {
                        num2 = vals.pop();
                        num1 = vals.pop();
                        vals.push(calc(num1, num2, ops.pop()));
                    }
                }
                
            } else {
                // 字符逻辑
                switch(CompareLevel(ops.peek(), pCur.charAt(0))) {
                    case LEVEL_INVALID:
                        flag = false;
                        break;
                    case LEVEL_SMALLER:
                        ops.push(pCur.charAt(0));
                        src.dequeue();
                        break;
                    case LEVEL_SAME:
                        ops.pop();
                        src.dequeue();
                        break;
                    case LEVEL_BIGGER:
                        num2 = vals.pop();
                        num1 = vals.pop();
                        vals.push(calc(num1, num2, ops.pop()));
                        break;
                }
                
                // 遍历数据源后清空余留操作
                if(src.isEmpty() && vals.size()!=1){
                    while(vals.size()!=1) {
                        num2 = vals.pop();
                        num1 = vals.pop();
                        vals.push(calc(num1, num2, ops.pop()));
                    }
                }
                
            }
            
        }
        
        if(flag == false) {
            throw new IllegalArgumentException();
        }
        
        return vals.peek();
    }
    
    public static void main(String args[]) {
        System.out.println(evaExpression("1+2*3-4/5"));
        System.out.println(evaExpression("1+(2*3)-(4/5)"));
        System.out.println(evaExpression("1+(2*3)+(4/5)"));
        System.out.println(evaExpression("1+(2*3)-(4/0)"));
    }
}