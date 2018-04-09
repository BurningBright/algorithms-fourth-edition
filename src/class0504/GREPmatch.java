package class0504;

import stdlib.StdIn;
import stdlib.StdOut;

/**
 * @Description 5.4.7
 *          grep with no prefix & suffix
 * @author Leon
 * @date 2018-04-06 20:15:00
 */
public class GREPmatch {

    public static void main(String[] args) {
        String regexp = args[0];
        NFA nfa = new NFAOrigin(regexp);
        while (StdIn.hasNextLine()) {
            String txt = StdIn.readLine();
            if (nfa.recognizes(txt))
                StdOut.println(txt);
        }
    }

}
