package class0302;
/**
 * @Description 3.2.8
 *      完美平衡树的平均对比次数
 * @author Leon
 * @date 2017-03-23 17:53:26
 */
public class OptCompares {
    
    public static double optCompares(int N) {
        if (N <= 0)
            throw new IllegalArgumentException();
        
        if (N == 1)
            return 1;
        
        int hyp = 0, sum = 0, exp = 0;
        while(hyp + Math.pow(2, exp) <= N) {
//            if(hyp + Math.pow(2, exp) > N) 
//                break;
            
            hyp += Math.pow(2, exp);
            sum += Math.pow(2, exp) * (exp+1);
            exp ++;
        }
        
        if (N - hyp > 0) 
            sum += (N - hyp) * (exp+1);
        
        System.out.println(hyp +"   "+ sum +"   "+ --exp);
        
        return .0;
    }
    
    public static void main(String[] args) {
        optCompares(9);
    }

}
