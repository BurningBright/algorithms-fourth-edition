package class0304;

/**
 * @Description 3.4.1
 *      11k % M
 *      
 * 0    O<-T<-Y<-E
 * 1    U<-A
 * 2    Q
 * 3
 * 4    N<-I<-S
 * 
 * @author Leon
 * @date 2017-08-28 14:26:13
 */
public class EasyQution {
    
    private static int M = 5;
    
    public static void calc() {
        char[] src = {'E', 'A', 'S', 'Y', 'Q', 'U', 'T', 'I', 'O', 'N'};
        for(int i=0; i< src.length; i++) {
            int code = (int)src[i] - 64;
            System.out.print(code);
            System.out.print("\t");
            System.out.println(11 * code % M);
        }
    }
    
    public static void main(String[] args) {
        calc();
    }

}
