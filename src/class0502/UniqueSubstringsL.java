package class0502;

import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 5.2.14
 *          长度为L的独特子串
 * @author Leon
 * @date 2018-03-06 10:10:00
 */
public class UniqueSubstringsL {
    
    public static void main(String[] args) {
        In in = new In();
        
        String src = in.readLine();         // 源字符串
        int l = in.readInt();               // 独特串长度
        
        TST<String> st = new TST<String>();
        for (int i = 0; i+l<src.length(); i++) 
            st.put(src.substring(i, i+l), src.substring(i, i+l));
        
        for (String s: st.keys())
            StdOut.println(s);
    }

}
