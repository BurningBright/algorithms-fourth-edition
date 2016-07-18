package class0105;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description 1.5.23
 *      Compare quick-find with 
 *      quick-union for Erdös-Renyi model.
 *      对比快联、快找性能
 * @author Leon
 * @date 2016-07-18 17:21:28
 */
public class ErdosRenyiQF_QU {
    
    private static List<Conn> date;
    
    private static void QU(int N) {
        if(date == null) {
            date = new LinkedList<Conn>();
            
            QuickUnionUF qu = new QuickUnionUF(N);
            
            while(qu.count() != 1) {
                int p = (int) (Math.random()*N);
                int q = (int) (Math.random()*N);
                date.add(new Conn(p, q));
                
                if(qu.connected(p, q))
                    qu.union(p, q);
            }
            
        } else {
            QuickUnionUF qu = new QuickUnionUF(N);
            for(Conn c: date)
                qu.union(c.p, c.q);
            assert qu.count() == 1;
        }
    }
    
    private static void QF(int N) {
        if(date == null) {
            date = new LinkedList<Conn>();
            
            QuickFindUF qf = new QuickFindUF(N);
            
            while(qf.count() != 1) {
                int p = (int) (Math.random()*N);
                int q = (int) (Math.random()*N);
                date.add(new Conn(p, q));
                
                if(qf.connected(p, q))
                	qf.union(p, q);
            }
            
        } else {
        	QuickFindUF qf = new QuickFindUF(N);
            for(Conn c: date)
            	qf.union(c.p, c.q);
            assert qf.count() == 1;
        }
    }
    
    public static void main(String[] args) {
        int T = 7;
        
        for(int i=100, j=0; j<T; i*=2,j++) {
            
            
        }
        
    }
    
    private static class Conn {
        int p,q;
        Conn(int p, int q) {
            this.p = p;
            this.q = q;
        }
    }
}
