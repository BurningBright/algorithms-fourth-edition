package class0104;
/**
 * @Description 4数和为0问题
 * @author Leon
 * @date 2016-06-24 14:05:47
 */
public class FourSum {
    
    public static int count(int[] a) {
        int N = a.length;
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                for (int k = j+1; k < N; k++) {
//                  if (i < j && j < k)
//                    if (a[i] + a[j] + a[k] == 0) {
//                        cnt++;
//                    }
                    for (int l = k+1; l < N; l++) {
                        if (a[i] + a[j] + a[k] + a[l] == 0) {
                            cnt++;
                        }
                    }
            }
        }
     }
        return cnt;
    } 
    
    public static void main(String[] args) {
        
    }

}
