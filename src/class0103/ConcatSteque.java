package class0103;
/**
 * @Description 1.3.47
 *          既然可破坏，那直接入队即可，加入数据结构意义何在？
 *          如果要产生新的对象，那环型队列同样需要实现接口。
 * @author Leon
 * @date 2016-06-01 13:24:13
 */
public class ConcatSteque<T> extends Steque<T> implements Catenable<T> {
    
    //CircularQueue<Node> cq = new CircularQueue<Node>();
    
    @Override
	public void catenation(Catenable<T> c) {
        for(T t: c) {
        	this.enqueue(t);
        }
	}

}
