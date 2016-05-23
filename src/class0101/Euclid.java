package class0101;
/**
 * @Description 欧几里德算法，辗转相除
 *              1.1.24
 * @author Leon
 * @date 2016-05-23 14:28:11
 */
public class Euclid {

    public static int GCD(int a, int b) {
        if(b == 0) return a;
        if(a > b) {
            System.out.println("+"+a+" "+b);
            return GCD(b, a%b);
        } else {
            System.out.println("-"+a+" "+b);
            return GCD(a, b%a);
        }
    }
    
    public static void main(String[] args) {
        System.out.println(GCD(1111111, 1234567));
    }

}
