package class0505;

import stdlib.BinaryIn;
import stdlib.BinaryOut;

/**
 * @Description 5.5.0
 *              定制输入/输出的行程压缩
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @date 2018-04-19 14:15:00
 */
public class RunLength {

    private static final int R = 256;
    private static final int LG_R = 8;

    // Do not instantiate.
    private RunLength() {
    }

    public static void compress(BinaryIn in, BinaryOut out) {
        char run = 0;
        boolean old = false;
        while (!in.isEmpty()) {
            boolean b = in.readBoolean();
            if (b != old) {
                out.write(run, LG_R);
                run = 1;
                old = !old;
            } else {
                if (run == R - 1) {
                    out.write(run, LG_R);
                    run = 0;
                    out.write(run, LG_R);
                }
                run++;
            }
        }
        out.write(run, LG_R);
        out.close();
    }

    public static void expand(BinaryIn in, BinaryOut out) {
        boolean b = false;
        while (!in.isEmpty()) {
            int run = in.readInt(LG_R);
            for (int i = 0; i < run; i++)
                out.write(b);
            b = !b;
        }
        out.close();
    }

}
