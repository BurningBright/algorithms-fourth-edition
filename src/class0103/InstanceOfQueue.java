package class0103;

import java.util.Iterator;

import rlgs4.Queue;
import stdlib.StdIn;
/**
 * @Description 1.3.15
 *      an instance of queue
 * @author Leon
 * @date 2016-05-30 11:12:10
 */
public class InstanceOfQueue {

    public static void main(String[] args) {
        int K = Integer.parseInt(args[0]);
        Queue<String> strQueue = new Queue<String>();
        if(!StdIn.isEmpty()) {
            String tmp;
            while((tmp = StdIn.readLine()) != null) {
                if(tmp.length() == 0) break;
                strQueue.enqueue(tmp);
            }
            
            if(K < 1 || K >strQueue.size())
                throw new IllegalArgumentException();
            
            // iterator to the previous of target node
            Iterator<String> it = strQueue.iterator();
            for(int i=0; i<strQueue.size()-K; i++) {
                if(it.hasNext())
                    it.next();
            }
            System.out.println(it.next());
            
        }
    }

}
