package class0502;

import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 5.2.16
 *          用字典做 拼写检查
 * @author Leon
 * @date 2018-03-06 10:40:00
 */
public class SpellChecking {

    public static void main(String[] args) {
        In dictionary = new In("resource/5.2/dictionary.txt");
        String word = null;
        TST<String> st = new TST<String>();                     // 字典
        while ((word = dictionary.readLine()) != null)
            st.put(word, word);
        
        In in = new In();
        while ((word = in.readLine()) != null) {
            String[] src = word.split(" |\\.|,|\"|'|-");        // 源字符串
            for (int i=0; i<src.length; i++)
                if (st.get(src[i]) == null)
                    StdOut.println(src[i]);
        }
    }

}
