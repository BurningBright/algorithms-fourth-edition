package class0104;
/**
 * @Description prints all elements that appear in both arrays, in sorted order
 * @author Leon
 * @date 2016-06-24 09:46:31
 */
public class TheSameInTwo<T extends Comparable<T>> {
    
    public int findSame(T[] a, T[] b) {
        int step = 0;
        int j = 0;
        T previous = null;
        
        outer:for(int i=0; i<a.length; i++) {
            for(; j<b.length && a[i].compareTo(b[j])>=0; j++) {
                if(a[i].equals(previous))
                    continue outer;
                if(b[j].equals(previous))
                    continue;
                if(a[i].equals(b[j])) {
                    System.out.println(i+"\t-\t"+j+"\t-\t"+a[i]);
                    previous = b[j];
                }
                step++;
            }
        }
        
        return step;
    }
    
    public static void main(String[] args) {
        Integer[] a = {10, 10, 11, 11, 11, 16, 18, 23, 29, 33, 48 , 54, 57, 77, 77, 84, 98, 100};
        Integer[] b = {9, 13, 16, 17, 17, 21, 23, 23, 33, 47, 53, 55, 77, 99};
        
        TheSameInTwo<Integer> ait = new TheSameInTwo<Integer>();
        System.out.println("step is :"+ait.findSame(a, b));
        System.out.println("a size is:" + a.length + " | b size is:" + b.length);
    }

}
