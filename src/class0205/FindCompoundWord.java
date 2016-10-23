package class0205;

import stdlib.StdOut;

/**
 * @Description 2.5.2
 *      找到输入中的词组
 *      
 * @author leon
 * @date 2016-10-23 13:57:27
 */
public class FindCompoundWord {

    public static void main(String[] args) {
        
        String[] src = { "home", "work", "fish", "rain", "snow",
                "everyone", "football", "homework", "snowman" };
        // 当前单词
        for (String s1 : src) {
            String tmp1 = s1;
            // 记录分词数
            int counter = 0;
            
            // 遍历单词
            while (!"".equals(tmp1)){
                String tmp2 = tmp1;
                for (String s2 : src) {
                    if (tmp1.indexOf(s2) == 0) {
                        tmp1 = tmp1.substring(s2.length());
                        counter ++;
                        break;
                    }
                }
                if (tmp2 == tmp1)
                    break;
            }
            
            // 有符合且分词数大于1的才是组合词
            if ("".equals(tmp1) && counter > 1)
                StdOut.println(s1);
        }
    }

}
