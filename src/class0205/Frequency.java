package class0205;

import stdlib.StdIn;
import stdlib.StdOut;

/**
 * @Description 2.5.8
 *      词频倒序
 * @author Leon
 * @date 2016-10-25 17:35:49
 */
public class Frequency {
    
    private int[] num = new int[1];
    private String[] a = new String[1];
    private int N = 0;
    private int M = 0;
    
    public void add(String str) {
        if (N == a.length) 
            resizeInt(2*a.length);
        if (M == a.length) 
            resize(2*a.length);
        
        boolean flag = false;
        for (int i=0; i<M; i++) 
            if (a[i].equals(str)) {
                num[i] = num[i] + 1;
                flag = true;
                break;
            }
        
        if (!flag) {
            num[N++] = 1;
            a[M++] = str;
        }
    }
    
    public void print() {
        int[] copy = num.clone();
        for (int i=0; i<N; i++) {
            int max = i;
            for(int j=i; j<N; j++) {
                if (copy[max] < copy[j]) {
                    int tmp = copy[max];
                    copy[max] = copy[i];
                    copy[max] = tmp;
                }
            }
            StdOut.println(a[max]+"  "+num[max]);
        }
    }
    
    private void resize(int capacity) {
        assert capacity >= N;
        String[] tmp = new String[capacity];
        for (int i = 0; i < N; i++)
            tmp[i] = a[i];
        a = tmp;
    }
    
    private void resizeInt(int capacity) {
        assert capacity >= N;
        int[] tmp = new int[capacity];
        for (int i = 0; i < N; i++)
            tmp[i] = num[i];
        num = tmp;
    }
    
    public static void main(String[] args) {
        
        Frequency f = new Frequency();
        
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("*"))
                f.add(item);
        }
        
        f.add("a");
        f.add("a");
        f.add("b");
        f.add("b");
        f.add("b");
        f.add("c");
        f.print();
        
    }

}
