package class0302;

/**
 * @Description 3.2.9
 *      like permutate, but the tail 
 *      middle-big-small / midlle-small-big 
 *      should be count in one shape
 * @author Leon
 * @date 2017-03-20 17:42:47
 */
public class BSTshapes {

    public static int total = 0;

    public static void swap(String[] str, int i, int j) {
        String temp = new String();
        temp = str[i];
        str[i] = str[j];
        str[j] = temp;
    }

    public static void arrange(String[] str, int st, int len) {
        if (st == len - 1) {
            if ((less(str[str.length - 3], str[str.length - 2]) && more(str[str.length - 3], str[str.length - 1]))) {
//                    || (more(str[str.length - 3], str[str.length - 2]) && less(str[str.length - 3], str[str.length - 1]))) {
                return;
            }
            for (int i = 0; i < len; i++) {
                System.out.print(str[i] + "  ");
            }
            System.out.println();
            total++;
        } else {
            for (int i = st; i < len; i++) {
                swap(str, st, i);
                arrange(str, st + 1, len);
                swap(str, st, i);
            }
        }

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static boolean more(Comparable a, Comparable b) {
        return a.compareTo(b) > 0;
    }
    
    public static void main(String[] args) {
        String[] src = new String[] {"a", "b", "c", "d"};
        
        arrange(src, 0, src.length);
        System.out.println(total);
    }

}
