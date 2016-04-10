package class0103;

import rlgs4.ResizingArrayQueue;
/**
 * Delete all of the elements from queue
 * and add these elements to both queue and target.
 * @author soft01
 *
 * @param <A>
 */
public class CopyFromQueue<A> extends ResizingArrayQueue<A>{
	public CopyFromQueue(GeneralizedLQueue<A> queue) {
		super();
		for(int i=queue.size(); i>0; i--) {
			A tmp = queue.delete(1);		//delete
			this.enqueue(tmp);				//add
			queue.insert(tmp);				//add
		}
	}
	public static void main(String[] args) {
		GeneralizedLQueue<String> queue = new GeneralizedLQueue<String>();
		queue.insert("a");
		queue.insert("b");
		queue.insert("c");
		CopyFromQueue<String> rq = new CopyFromQueue<String>(queue);
		System.out.println(queue.toString());
		rq.dequeue();
		System.out.println(rq.toString());
	}
}
