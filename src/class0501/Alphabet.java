package class0501;
/**
 * @Description 5.1.12
 *          字母表
 * @author Leon
 * @date 2018-02-26 11:00:00
 */
public class Alphabet {
    
    private char[] alphabet;    // 字母表
    private int[] inverse;      // 索引
    private final int R;        // 大小
    
    Alphabet() {
        this.R = 256;
        alphabet = new char[R];
        inverse = new int[Character.MAX_VALUE];
        for (int i=0; i<Character.MAX_VALUE; i++)
            inverse[i] = -1;
        for (int i=0; i<R; i++)
            inverse[alphabet[i]] = i;
    }
    
    Alphabet(String s) {
        this.R = s.length();
        alphabet = new char[R];
        inverse = new int[Character.MAX_VALUE];
        for (int i=0; i<Character.MAX_VALUE; i++)
            inverse[i] = -1;
        for (int i=0; i<R; i++)
            inverse[alphabet[i]] = i;
    }
    
    char toChar(int index) {
        return alphabet[index];
    }
    
    int toIndex(char c) {
        return inverse[c];
    }
    
    boolean contains(char c) {
        return inverse[c] != -1;
    }
    
    int R() {
        return R;
    }
    
    // 计算存储单字符所需空间，可真实的实现是char -_-
    int lgR() {
        int lgR = 0;
        for (int t = R-1; t >= 1; t /= 2)
            lgR++;
        return lgR;
    }
    
    int[] toIndices(String s) {
        int[] indices = new int[s.length()];
        for (int i=0; i<s.length(); i++)
            indices[i] = inverse[s.indexOf(i)];
        return indices;
    }
    
    String toChars(int[] indices) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<indices.length; i++) {
            if (indices[i] > R)
                throw new IllegalArgumentException("wrong index");
            sb.append(alphabet[indices[i]]);
        }
        return sb.toString();
    }
    
}
