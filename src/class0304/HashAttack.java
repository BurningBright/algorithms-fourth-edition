package class0304;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 3.4.32
 *      2^N Strings 2^N length, same hash ?
 *      [Aa BB] [Bb CC] ...
 *      char from 32 - 126 as example
 * @author Leon
 * @date 2017-10-06 10:01:37
 */
public class HashAttack {
    
    private static int N = 2;
    private static char[] EXP;
    static {
        EXP = new char[95];
        for (int i=32; i<127; i++) 
            EXP[i-32] = (char)i;
    }
    
    public static void attack() {
        // combine all possible
        List<String> result = combinationSelect(EXP, 0, 
                new char[N], 0, new ArrayList<String>());
        // get hash code
        String[] str = result.toArray(new String[0]);
        int[] hash = new int[str.length];
        System.out.println(str.length);
        
        for (int i=0; i<str.length; i++) {
            hash[i] = str[i].hashCode();
        }
        // loop find out
        int count = 0;
        for (int i=0; i<hash.length; i++) {
            for (int j=i+1; j<hash.length; j++) {
                if(hash[i] == hash[j]) {
                    System.out.println(str[i] + "\t" + str[j]);
                    count++;
                }
            }
        }
        System.out.println(count);
    }

    private static List<String> combinationSelect(char[] dataList, int dataIndex, 
            char[] resultList, int resultIndex, List<String> result) {
        int resultLen = resultList.length;
        int resultCount = resultIndex+1;
        if (resultCount > resultLen) { // 全部选择完时，输出组合结果
//            System.out.println(resultList[0] + " " + resultList[1]);
            result.add(new String(resultList));
            return result;
        }

        // 递归选择下一个
        for (int i = dataIndex, j=i-1; i < dataList.length; i++, j++) {
            resultList[resultIndex] = dataList[i];
            combinationSelect(dataList, j + 1, resultList, resultIndex + 1, result);
        }
        return result;
    }
    
    public static void main(String[] args) {
        // s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
        attack();
    }

}
