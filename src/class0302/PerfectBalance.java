package class0302;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import stdlib.StdRandom;

/**
 * @Description 3.2.25
 *      equivalent to binary search
 * @author Leon
 * @date 2017-03-30 17:58:37
 */
public class PerfectBalance {
    
    public static void pb(Adjustable2DTree<String, String> st, String[] args) {
        
        pb(st, args, 0, args.length-1, 1);
        
    }
    
    private static void pb(Adjustable2DTree<String, String> st, String[] args, int lo, int hi, int level) {
        
        if(lo == hi) 
            st.put(args[lo], "lv"+level);
        else if(lo < hi) {
            /*
             * int mid = lo + (hi - lo) / 2 + 1;
             * direct choose middle is not right
             */
            
            int mid = calcMid(lo, hi);
            
//            System.out.println(mid);
            
            st.put(args[mid], "lv"+level);
            
            // left
            pb(st, args, lo, mid-1, level+1);
            
            // right
            pb(st, args, mid+1, hi, level+1);
        }
    }
    
    private static int calcMid (int lo, int hi) {
        
        if(hi-lo == 1)
            return hi;
        
        int N = hi - lo + 1;
        int n = (int)(Math.log(N+1)/Math.log(2));
        int offset = (int) (N - (Math.pow(2.0, n) - 1));
        offset = (int) (offset < Math.pow(2.0, n-1)? offset: Math.pow(2.0, n-1));
        int mid = (int)(Math.pow(2.0, n-1) - 1) + offset;
        
        return lo + mid;
    }
    
    public static void main(String[] args) {
        int N = 26;
        
        Set<String> src = new HashSet<String>();
        for(int i=0; i<N; i++) {
            String tmp;
            if(src.contains(tmp = String.valueOf(10 + StdRandom.uniform(90))))
                i--;
            else
                src.add(tmp);
        }
        String[] srcX = src.toArray(new String[]{});
        
        /*
        String[] srcX = new String[N];
        for(int i=0; i<N; i++) 
            srcX[i] = String.valueOf(10 + StdRandom.uniform(90));
        */
        Arrays.sort(srcX);
        Adjustable2DTree<String, String> a2t = new Adjustable2DTree<String, String>();
        pb(a2t, srcX);
        
        a2t.calcTree();
    }

}
