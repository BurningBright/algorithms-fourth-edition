package class0304;

/**
 * @Description 3.4.31
 *      基础散列
 * @author Leon
 * @date 2017-10-06 17:33:13
 */
public class SeparateHashSTB <Key extends Comparable<Key>, Value> 
                                    extends SeparateHashST<Key, Value>{

    @Override
    int hash(Key key) {
        int code = (key.hashCode() & 0x7fffffff) % M;
        code = code-1 < 0 ? 0: code;
        return code;
    }

}
