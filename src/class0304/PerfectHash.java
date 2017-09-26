package class0304;
/**
 * @Description 3.4.4
 *      完美散列表
 *      (a * k) % M
 * @author Leon
 * @date 2017-08-29 11:01:37
 */
public class PerfectHash {
    
    public static void phFunc(char[] src) {
        
        // CUR represent a
        int CUR = 1;
        // CUR's step
        int ROUND = 26;
        // alphabet number
        int ALPHA = 26;
        
        while(CUR <= ROUND) {
            int min = ALPHA * CUR;
            Begin:for (int M = ALPHA * CUR; M>0; M--) {
                // check
                int mark[] = new int[M];
                for (int i=0; i<src.length; i++) {
                    int index = (CUR * ((int)src[i] - 64)) % M;
                    if(mark[index] > 0)
                        continue Begin;
                    else
                        mark[index] += 1;
                }
                if(M < min)
                    min = M;
            }
            
            System.out.println("a: " + CUR + " | M:" + min);
            CUR++;
        }
        
    }
    
    public static void main(String[] args) {
        char[] src = {'S', 'E', 'A', 'R', 'C', 'H', 'X', 'M', 'P', 'L'};
        phFunc(src);
    }

}
