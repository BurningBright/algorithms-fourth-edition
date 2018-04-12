package class0505;

import stdlib.BinaryOut;
import stdlib.StdRandom;

/**
 * @Description 5.5.0
 *          测试随机数据的压缩率
 * @author Leon
 * @date 2018-04-12 10:15:00
 */
public class CreateRandomData {

    public static void main(String[] args) {
        int BITS = 10 * 1024 * 1024 * 8;
        BinaryOut out = new BinaryOut("E:\\random.txt");
        for (int i = 0; i < BITS; i++) 
          out.write(StdRandom.uniform() > .5);
        out.close();
    }

}
