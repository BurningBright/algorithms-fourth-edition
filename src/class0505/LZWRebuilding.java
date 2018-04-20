package class0505;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import solution05.c05.TST;
import stdlib.BinaryIn;
import stdlib.BinaryOut;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 5.5.26
 *          重建型LZW，在超过极限时压缩率相比LZW会发生变换
 *          具体压缩率依赖于数据自身的结构，可能在分段数据中表现较好
 * @author Leon
 * @date 2018-04-20 09:55:00
 */
public class LZWRebuilding {
    
    private static final int R = 256;        // number of input chars
    private static final int L = 4096;       // number of codewords = 2^W
    private static final int W = 12;         // codeword width

    // Do not instantiate.
    private LZWRebuilding() { }

    public static void compress(BinaryIn in, BinaryOut out) { 
        String input = in.readString();
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;  // R is codeword for EOF

        while (input.length() > 0) {
            String s = st.longestPrefixOf(input);
            out.write(st.get(s), W);      // Print s's encoding.
            int t = s.length();
            if (t < input.length() && code < L)    // Add s to symbol table.
                st.put(input.substring(0, t + 1), code++);
            if (t < input.length() && code == L) {  // reset
                st = new TST<Integer>();
                for (int i = 0; i < R; i++)
                    st.put("" + (char) i, i);
                code = R+1;
            }
            input = input.substring(t);            // Scan past s in input.
        }
        out.write(R, W);
        out.close();
    } 

    public static void expand(BinaryIn in, BinaryOut out) {
        String[] st = new String[L];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = in.readInt(W);
        if (codeword == R) return;           // expanded message is empty string
        String val = st[codeword];

        while (true) {
            out.write(val);
            codeword = in.readInt(W);
            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L) st[i++] = val + s.charAt(0);
            if (i == L) {
                for (i = 0; i < R; i++)
                    st[i] = "" + (char) i;
                st[i++] = "";
            }
            val = s;
        }
        out.close();
    }
    
    public static void main(String[] args) {
        /*
        for (int i = 0, j = 1000; i < 4; i++, j *= 2) {
            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < j; k++)
                sb.append(StdRandom.uniform(255));
            sb.append(sb.toString());
            int a= lzw(sb.toString());
            int b= rlzw(sb.toString());
            StdOut.print("lzw: " + String.format("%.3f", (double)(a)/j*2) + "\t");
            StdOut.print("rebuild lzw: " + String.format("%.3f", (double)(b)/j*2) + "\r\n");
        }
        */
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8000; i++)
            sb.append((char)StdRandom.uniform(255));
        StdOut.println(sb.toString());
        
        String result = rlzwExpand(rlzwCompress(sb.toString()));
        
        StdOut.println(sb.length());
        StdOut.println(result.length());
        
        StdOut.println(sb.toString().equals(result));
    }

    private static byte[] rlzwCompress(String txt) {
        BinaryIn in = new BinaryIn(new ByteArrayInputStream(txt.getBytes()));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BinaryOut out = new BinaryOut(bos);
        compress(in, out);
        return bos.toByteArray();
    }
    
    private static String rlzwExpand(byte[] bytes) {
        BinaryIn in = new BinaryIn(new ByteArrayInputStream(bytes));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BinaryOut out = new BinaryOut(bos);
        expand(in, out);
        return bos.toString();
    }
    
    @SuppressWarnings("unused")
    private static int rlzw(String txt) {
        BinaryIn in = new BinaryIn(new ByteArrayInputStream(txt.getBytes()));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BinaryOut out = new BinaryOut(bos);
        compress(in, out);
        return bos.size();
    }
    
    @SuppressWarnings("unused")
    private static int lzw(String txt) {
        BinaryIn in = new BinaryIn(new ByteArrayInputStream(txt.getBytes()));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BinaryOut out = new BinaryOut(bos);
        LZW.compress(in, out);
        return bos.size();
    }
    
}
