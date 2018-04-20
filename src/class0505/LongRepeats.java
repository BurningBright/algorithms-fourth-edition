package class0505;

import stdlib.StdRandom;

/**
 * @Description 5.5.27
 *          长重复时的 行程、霍夫曼、LZW压缩率
 *          预期 - 不变、不变、趋于一个极限
 *          
 *          lzw :
 *          8N -> 12N
 *          16(1/2N) -> 12(1/2N)
 *          radio = 18N/16N ≈ 1.125%
 * @author Leon
 * @date 2018-04-20 09:15:00
 */
public class LongRepeats {
    
    public static void main(String[] args) {
        for (int i = 0, j = 1000; i < 4; i++, j *= 2) {
            StringBuilder sb = new StringBuilder();
            for (int k = 0; k < j; k++)
                sb.append(StdRandom.uniform(255));
            sb.append(sb.toString());
            EstimateRatio.exp(sb.toString(), j*2);
        }
    }

}
