package class0103;

/**
 * @Description 1.3.47
 *          可连接接口
 * @author Leon
 * @date 2016-06-01 11:12:25
 */
public interface Catenable<Item> extends Iterable<Item>{

    public void catenation(Catenable<Item> c);

}
