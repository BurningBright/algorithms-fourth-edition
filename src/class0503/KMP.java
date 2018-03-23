package class0503;

import stdlib.StdOut;

/**
 * @Description 5.3.8
 *              Knuth-Morris-Pratt
 * @author Leon
 * @date 2018-03-23 15:50:00
 */
public class KMP {
    private String pat;
    private int[][] dfa;

    public KMP(String pat) {
        this.pat = pat;
        int M = pat.length();
        int R = 256;
        // 正文字母表索引作为第一维
        // 匹配字符位置索引作为第二维
        dfa = new int[R][M];
        dfa[pat.charAt(0)][0] = 1;
        for (int X = 0, j = 1; j < M; j++) {
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][X];       // Copy mismatch cases.
            // 匹配的字符进入下一状态
            dfa[pat.charAt(j)][j] = j + 1;   // Set match case.
            // 构建DFA的关键所在，状态的传递
            X = dfa[pat.charAt(j)][X];       // **Update restart state.**
        }
    }

    public int search(String txt) { // Simulate operation of DFA on txt.
        int i, N = txt.length();
        int j, M = pat.length();
        for (i = 0, j = 0; i < N && j < M; i++)
            j = dfa[txt.charAt(i)][j];
        if (j == M)
            return i - M;           // found (hit end of pattern)
        else
            return N;               // not found (hit end of text)
    }

    public int count(String txt) {
        int i, N = txt.length();
        int j, M = pat.length();
        int count = 0;
        for (i = 0, j = 0; i < N && j < M; i++) {
            j = dfa[txt.charAt(i)][j];
            if (j == M) {
                count++;
                j = 0;
            }
        }
        return count;
    }

    public int[] searchAll(String txt) {
        int i, N = txt.length();
        int j, M = pat.length();
        int k;
        int[] index = new int[count(txt)];
        for (i = 0, j = 0, k = 0; i < N && j < M; i++) {
            j = dfa[txt.charAt(i)][j];
            if (j == M) {
                // 索引相减
                index[k++] = i - (M-1);
                j = 0;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        String src = "she sells sea shells by the sea shore";
        StdOut.println(new KMP("sh").count(src));
        int[] result = new KMP("sh").searchAll(src);
        for (int i=0; i<result.length; i++)
            StdOut.print(result[i] + " ");
    }

}
