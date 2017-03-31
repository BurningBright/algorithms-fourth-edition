package class0302;

import java.util.Arrays;

import stdlib.StdRandom;

/**
 * @Description 3.2.25
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
            int mid = lo + (hi - lo) / 2 + 1;
            st.put(args[mid], "lv"+level);
            
            // left
            pb(st, args, lo, mid-1, level+1);
            
            // right
            pb(st, args, mid+1, hi, level+1);
        }
    }
    
    public static void main(String[] args) {
        int N = 10;
        String[] src = new String[N];
        for(int i=0; i<N; i++) 
            src[i] = String.valueOf(StdRandom.uniform(100));
        
        Arrays.sort(src);
        Adjustable2DTree<String, String> a2t = new Adjustable2DTree<String, String>();
        pb(a2t, src);
        
        a2t.calcTree();
    }

}
