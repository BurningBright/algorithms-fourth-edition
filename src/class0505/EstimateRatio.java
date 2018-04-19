package class0505;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import stdlib.BinaryIn;
import stdlib.BinaryOut;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 5.5.7/8/9 
 *              估算run-length/ huffman/ lzw 的压缩率及变化
 * @author Leon
 * @date 2018-04-16 15:15:00
 */
public class EstimateRatio {

    /**
     * 正文单字节重复时：
     * 行程压缩率不变、霍夫曼趋于一个稳定的值(12.5%)、lzw趋向于0
     */
    public static void testGroundA() {
        for (int i = 0, j = 100; i < 15; i++, j *= 2) {
            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < j; k++)
                sb.append("a");
            exp(sb.toString(), j);
        }
    }
    
    /**
     * 正文双字节重复时：
     * 行程压缩率不变、霍夫曼趋于一个稳定的值(25%)、lzw趋向于0
     */
    public static void testGroundB() {
        for (int i = 0, j = 100; i < 8; i++, j *= 2) {
            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < j; k++)
                sb.append("ab");
            exp(sb.toString(), j);
        }
    }

    /**
     * 随机的ascii码
     * 压缩率均大于1
     */
    public static void testGroundC() {
        for (int i = 0, j = 100; i < 8; i++, j *= 2) {
            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < j; k++)
                sb.append(StdRandom.uniform(255));
            exp(sb.toString(), j);
        }
    }
    
    private static void exp(String txt, int N) {
        int a = runLength(txt);
        int b = huffman(txt);
        int c= lzw(txt);
        StdOut.print("runLength: " + String.format("%.3f", (double)(a)/N) + "\t");
        StdOut.print("huffman: " + String.format("%.3f", (double)(b)/N) + "\t");
        StdOut.print("lzw: " + String.format("%.3f", (double)(c)/N) + "\r\n");
    }

    private static int runLength(String txt) {
        BinaryIn in = new BinaryIn(new ByteArrayInputStream(txt.getBytes()));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BinaryOut out = new BinaryOut(bos);
        RunLength.compress(in, out);
        return bos.size();
    }

    private static int huffman(String txt) {
        BinaryIn in = new BinaryIn(new ByteArrayInputStream(txt.getBytes()));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BinaryOut out = new BinaryOut(bos);
        Huffman.compress(in, out);
        return bos.size();
    }

    private static int lzw(String txt) {
        BinaryIn in = new BinaryIn(new ByteArrayInputStream(txt.getBytes()));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BinaryOut out = new BinaryOut(bos);
        LZW.compress(in, out);
        return bos.size();
    }

    public static void main(String[] args) {
//        testGroundA();
//        testGroundB();
        testGroundC();
    }

}
