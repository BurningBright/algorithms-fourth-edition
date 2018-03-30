package class0503;

import rlgs4.Stopwatch;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 5.3.39
 *              时间对比
 *              Boyer-Moore很强
 * @author Leon
 * @date 2018-03-30 09:29:00
 */
public class Timings {
    
    private static int T = 10;
    
    private static double brute(String txt, String pat) {
        Stopwatch watch = new Stopwatch();
        StdOut.println(Brute.search_1(txt, pat));
        for (int i = 0; i < T; i++)
            Brute.search_1(txt, pat);
        return watch.elapsedTime();
    }
    
    private static double kmp(String txt, String pat) {
        KMP kmp = new KMP(pat);
        Stopwatch watch = new Stopwatch();
        StdOut.println(kmp.search(txt));
        for (int i = 0; i < T; i++)
            kmp.search(txt);
        return watch.elapsedTime();
    }
    
    private static double boyerMoore(String txt, String pat) {
        BoyerMoore bm = new BoyerMoore(pat);
        Stopwatch watch = new Stopwatch();
        StdOut.println(bm.search(txt));
        for (int i = 0; i < T; i++)
            bm.search(txt);
        return watch.elapsedTime();
    }
    
    private static double rabinKarp(String txt, String pat) {
        RabinKarp rp = new RabinKarp(pat);
        Stopwatch watch = new Stopwatch();
        StdOut.println(rp.search(txt));
        for (int i = 0; i < T; i++)
            rp.search(txt);
        return watch.elapsedTime();
    }
    
    public static void main(String[] args) {
        String pat = "It is a far, far better thing that I do, than I have ever done;";
        In in = new In("resource/3.1/tale.txt");
        StringBuilder sb = new StringBuilder();
        while (in.hasNextLine())
            sb.append(in.readLine());
        StdOut.println(sb.length());
        
        StdOut.println("-------- brute --------");
        StdOut.println(brute(sb.toString(), pat));
        
        StdOut.println("-------- kmp --------");
        StdOut.println(kmp(sb.toString(), pat));
        
        StdOut.println("-------- boyerMoore --------");
        StdOut.println(boyerMoore(sb.toString(), pat));
        
        StdOut.println("-------- rabinKarp --------");
        StdOut.println(rabinKarp(sb.toString(), pat));
    }

}
