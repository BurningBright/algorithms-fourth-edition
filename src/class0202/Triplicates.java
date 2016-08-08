package class0202;

import stdlib.StdOut;

/**
 * @Description 2.2.21
 *      一式三分，要达到线性级性能，
 *      需要"同分"样本中元素不重复，性能4n+nlgn
 *      
 *      合并排序、折半查找，性能4nlgn
 * @author Leon
 * @date 2016-08-08 14:26:13
 */
public class Triplicates {
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static int find(Comparable[] a, Comparable[] b, Comparable[] c) {
//        MergeImprov.sort(a);
//        MergeImprov.sort(b);
//        MergeImprov.sort(c);
        int A = a.length;
        int B = a.length + b.length;
        int C = a.length + b.length + c.length;
        Comparable[] src = new Comparable[C];
        for(int i=0; i<A; i++)
            src[i] = a[i];
        for(int i=0, j=A; j<B; i++, j++)
            src[j] = b[i];
        for(int i=0, j=B; j<C; i++, j++)
            src[j] = c[i];
        
        MergeImprov.sort(src);
        int count = 0;
        for(int i=0; i<src.length-2; i++) {
            if(src[i]==src[i+1] && src[i+1]==src[i+2])
                count++;
        }
        
        return count;
    }
    
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        Comparable[] a = new Comparable[]{1,2,3,4,5};
        Comparable[] b = new Comparable[]{3,4,5,6,7};
        Comparable[] c = new Comparable[]{5,6,7,8,9};
        StdOut.println(find(a, b, c));
    }

}
