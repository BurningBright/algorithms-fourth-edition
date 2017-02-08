package class0301;

import java.util.Random;

import rlgs4.Stopwatch;

/**
 * @Description 3.1.27
 *      探索 何时 建N表 与 搜索S次表消耗相同
 * @author Leon
 * @date 2017-02-08 16:36:11
 */
public class SmallTables {

    public static void main(String[] args) {
        
        int N[] = {5000, 10000, 20000, 40000, 80000};
        Random rdm = new Random();
        Object obj = new Object();
        
        
        for (int i=0; i<N.length; i++) {
            
            /* build */
            double tBuild;
            Stopwatch wBuild = new Stopwatch();
            rlgs4.BinarySearchST<Double, Object> bst = new rlgs4.BinarySearchST<Double, Object>();
            for (int j=0; j<N[i]; j++) 
                bst.put(rdm.nextDouble(), obj);
            System.out.printf("%d -> %.3f \t", N[i], tBuild = wBuild.elapsedTime());
            
            /* search */
            Long times = 0l;
            Stopwatch wSearch = new Stopwatch();
            while(wSearch.elapsedTime() < tBuild) {
                bst.get(rdm.nextDouble());
                times++;
            }
            System.out.printf("%d -> %.3f \r\n", times, wSearch.elapsedTime());
            
            
        }
        
    }

}
