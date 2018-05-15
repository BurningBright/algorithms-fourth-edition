package context04;

import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description context04.41
 *              容量随机生成方法 范围：0 - 2^20
 * @author Leon
 * @date 2018-05-15 10:00:00
 */
public class CapacityGenerator {
    
    public static int uniformly() {
        return StdRandom.uniform(1048576);
    }
    
    public static int gaussian() {
        double num = StdRandom.gaussian();
        while (Math.abs(num) > 2)
            num = StdRandom.gaussian();
        return (int) (1048576/2 * Math.abs(num));
    }
    
    public static void main(String[] args) {
        for (int i=0; i<100; i++)
            StdOut.println(gaussian());
    }

}
