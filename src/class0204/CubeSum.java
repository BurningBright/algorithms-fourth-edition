package class0204;

import java.util.Comparator;

import rlgs4.MinPQ;
import stdlib.StdOut;

/**
 * @Description 2.4.25
 *      计算数字理论，用优先队列堆找到
 *      a3+b3=c3+d3 其中abcd在0~N2中且不重复
 *      1000000电脑跑不出来缩小100倍
 * @author Leon
 * @date 2016-09-23 14:40:16
 */
public class CubeSum {
    
    private long a;
    private long b;
    private long c;
    
    public CubeSum(long a, long b) {
        this.a = a;
        this.b = b;
        this.c = a*a*a + b*b*b;
    }
    
    public long getC() {
        return c;
    }
    
    public String toString() {
        return "[ " + c + ", " + a + ", " + ", " + b + "]";
    }
    
    public static void main(String[] args) {
        // init
        int N = 10000;
        Comparator<CubeSum> cpr = new CubeCpr();
        MinPQ<CubeSum> mpq = new MinPQ<CubeSum>(N, cpr);
        for(int i=0; i<N; i++) {
            mpq.insert(new CubeSum(i, 0));
        }
        StdOut.println("----------------");
        
        CubeSum preCube = new CubeSum(-1, -1);
        for(int j=1; j<N; j++) {
            for(int i =j; i<N; i++) {
                CubeSum min = mpq.delMin();
                if(min.getC() == preCube.getC()) 
                    StdOut.println(preCube.toString()+"\t"+min.toString());
                else
                    preCube = min;
                
                mpq.insert(new CubeSum(i, j));
            }
//            if(j%1000==0)
//                StdOut.println(j);
        }
        StdOut.println("----------------");
        
        for(int i=0; i<N; i++) {
            CubeSum min = mpq.delMin();
            if(min.getC() == preCube.getC()) 
                StdOut.println(preCube.toString()+"\t"+min.toString());
            else
                preCube = min;
        }
        StdOut.println("----------------");
    }

}

class CubeCpr implements Comparator<CubeSum> {
    
    @Override
    public int compare(CubeSum o1, CubeSum o2) {
        long ret = o1.getC() - o2.getC();
        return ret > 0 ? 1 : ret == 0 ? 0 : -1;
    }
    
}