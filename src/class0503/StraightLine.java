package class0503;

import stdlib.StdOut;

/**
 * @Description 5.3.34
 *              输出状态机的代码形式
 *              感觉书上的例子有点问题，有待考证
 * @author Leon
 * @date 2018-03-29 13:25:00
 */
public class StraightLine {
    
    public static void translate(String pat) {
        int M = pat.length();
        int[][] dfa;
        dfa = new int[2][M];
        dfa[pat.charAt(0)-65][0] = 1;
        for (int X = 0, j = 1; j < M; j++) {
            for (int c = 0; c < 2; c++)
                dfa[c][j] = dfa[c][X];
            // 匹配的字符进入下一状态
            dfa[pat.charAt(j)-65][j] = j + 1;
            // 构建DFA的关键所在，状态的传递
            // **Update restart state.**
            X = dfa[pat.charAt(j)-65][X];
        }
        
        
        StringBuilder sb = new StringBuilder();
        sb.append("int i = -1;")
        .append("\r\n")
        .append("sm: i++;")
        .append("\r\n");
        
        for (int i = 0; i < pat.length(); i++) {
            if (i == 0)
                sb.append('s').append(i).append(": if (txt[i] != '")
                .append(pat.charAt(i)).append("') goto sm;").append("\r\n");
            else
                sb.append('s').append(i).append(": if (txt[i] != '")
                .append(pat.charAt(i)).append("') goto s")
                .append(pat.charAt(i)=='A'? dfa[1][i]: dfa[0][i])
                .append(";").append("\r\n");
        }
        
        sb.append("return i-8;");
        
        StdOut.println(sb);
    }
    
    public static void main(String[] args) {
        StraightLine.translate("AABAAA");
        //StraightLine.translate("AABC");
    }

}
