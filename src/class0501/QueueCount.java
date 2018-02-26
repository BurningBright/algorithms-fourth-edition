package class0501;

import rlgs4.Queue;
import stdlib.StdOut;

/**
 * @Description 5.1.7
 *          用队列来计数？
 * @author Leon
 * @date 2018-02-26 18:30:00
 */
public class QueueCount {
    
    private Queue<String>[] counter;
    private Alphabet alp;
    
    @SuppressWarnings("unchecked")
    public QueueCount(Alphabet alp) {
        this.alp = alp;
        counter = (Queue[]) new Queue[alp.R()];
        for (int i=0; i<alp.R(); i++)
            counter[i] = new Queue<String>();
    }
    
    public int[] count(String[] data) {
        int[] indices = new int[alp.R()];
        for (int i=0; i<data.length; i++)
            counter[data[i].charAt(0)].enqueue(data[i]);
        
        for (int i=0; i<alp.R(); i++)
            indices[i] = counter[i].size();
        return indices;
    }
    
    public String getString(String[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<alp.R(); i++)
            if(counter[i].size()>0)
                sb.append(counter[i].toString());
        return sb.toString();
    }
    
    public static void main(String[] args) {
        String[] src = "ijeoijogjrjqirpindfibspirtg".split("");
        QueueCount count = new QueueCount(new Alphabet());
        count.count(src);
        StdOut.println(count.getString(src));
    }

}
