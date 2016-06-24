package class0104;

/**
 * @Description 找到本地最小 [小-中-大]组
 * @author Leon
 * @date 2016-06-24 14:40:04
 */
public class LocalMin4Aray {
    public static int min;
    public static int count=0;
    
    public static int find(int[] src, int lo, int mid, int hi) {
        count++;
        if(hi - lo <= 2) 
            return (src[lo] < src[mid] && src[mid] < src[hi])? mid: -1;
        if(src[mid] < src[mid+1] && src[mid-1] < src[mid]) {
            // find in left
            int i = find(src, lo, lo + (mid - lo) / 2, mid);
            return i > 0? i: mid;
        } else {
            // find in left first / if not find in right
            int i = find(src, lo, lo + (mid - lo) / 2, mid);
            return i > 0? i: find(src, mid, mid + (hi - mid) / 2, hi);
        }
    }
    
    public static void find(int[] src) {
        //int min = -1;
        int mid, lo = 0;
        int hi = src.length -1;
        
        // 记忆点
        // int mid_tmp, lo_tmp, hi_tmp;
        // is there min find in left part
        // boolean flag = false;
        
        while (hi - lo >= 2) {
            mid = lo + (hi - lo)/2;
            if(src[mid] < src[mid+1] && src[mid-1] < src[mid]) {
                min = mid;
                // find in left directly
                hi = mid;
                continue;
            } else {
                /*
                 * 1. find left first
                 * 2. adjust the flag if true
                 * 3. ???
                 */
                
                if(hi - lo == 2) 
                    return;
                
                // recover by memory point
            }
        }
    }
    
    public static void main(String[] args) {
//        int N = 4;
        int[] src = new int[]{0,9,1,8,2,7,3,6,4,5};
//        for(int i = 0; i<N; i++) {
//            src[i] = i;
//        }
//        StdRandom.shuffle();
        
        System.out.println(find(src, 0, (src.length-1)/2, src.length-1));
        System.out.println(count);
    }

}
