package class0102;
/**
 * @Description 1.2.6
 *              回环测试，应该是：
 *              俩串长度相等且一串在二串+二串中的位置>=0 即为回环
 * @author Leon
 * @date 2016-05-24 13:30:32
 */
public class CircularRotation {
    
    public static boolean checkCR(String a, String b) {
        String c = b+b;
        int begin = c.indexOf(a);
        if(begin == -1) return false;
        String ret = c.substring(0, begin)+c.substring(begin+a.length(), c.length());
        return b.equals(ret);
    }
    
    public static void main(String[] args) {
        String a = "ACTGACG";
        String b = "TGACGAC";
        System.out.println(checkCR(a, b));
        System.out.println(checkCR(a, "TAGCGAC"));
    }
    
}
