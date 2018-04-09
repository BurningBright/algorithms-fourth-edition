package class0504;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import stdlib.StdOut;

/**
 * @Description 5.4./8/10/12/13/14
 *              正则例子
 * @author Leon
 * @date 2018-04-04 18:00:00
 */
public class PatternExample {
    
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        // 5.4.8
        // Contains at least three consecutive 1s
        Pattern a1 = Pattern.compile(".*1{3,}.*");
        // Contains the substring 110
        Pattern b1 = Pattern.compile(".*110.*");
        // Contains the substring 1101100
        Pattern c1 = Pattern.compile(".*1101100.*");
        // Does not contain the substring 110
        Pattern d1 = Pattern.compile(".*(?:(?!110).)*"); // interesting
        
        // 5.4.10
        // Has at least 3 characters, and the third character is 0
        Pattern a2 = Pattern.compile("\\d\\d0\\d*");
        // Number of 0s is a multiple of 3
        Pattern b2 = Pattern.compile("(.*0.*0.*0.*)*");
        // Starts and ends with the same character
        Pattern c2 = Pattern.compile("(0|1).*\\1");
        // Odd length
        Pattern d2 = Pattern.compile("(00|01|10|11)*[0-1]"); // 奇偶不能用平衡组
        // Starts with 0 and has odd length, or starts with 1 and has even length
        Pattern e2 = Pattern.compile("(0(00|01|10|11)*|1(00|01|10|11)*[0-1])");
        // Length is at least 1 and at most 3
        Pattern f2 = Pattern.compile("[0-1]{1,3}");
        
        // 5.4.12
        // Phone numbers
        Pattern a = Pattern.compile("\\(\\d{3}\\)\\d{3}-\\d{4}");
        // Social Security numbers
        Pattern b = Pattern.compile("\\d{3}-\\d{2}-\\d{4}");
        // date
        Pattern c = Pattern.compile("\\w{3,8} [123]?\\d, \\d{4}");
        // ip
        Pattern d = Pattern.compile("(?:[0-9]{1,3}\\.){3}[0-9]{1,3}");
        // License plates
        Pattern e = Pattern.compile("\\d{4}[A-Z]{2}");
        
        // 5.4.13
        // All strings except 11 or 111
        Pattern a3 = Pattern.compile(".*(?:(?!11|111).)*");
        // Strings with 1 in every odd-number bit position
        Pattern b3 = Pattern.compile("(10|11)*1?");
        // Strings with at least two 0s and at most one 1
        Pattern c3 = Pattern.compile("(.*0.*1.*0.*)|(.*1.*0.*0.*)|(.*0.*0.*1.*)");
        // Strings with no two consecutive 1s
        Pattern d3 = Pattern.compile(".*(?:(?!11).)*");
        
        // 5.4.14
        // Divisible by 2               1->1
        Pattern a4 = Pattern.compile("1(1|01)*0");
        // Divisible by 3               1->2 2->1
        Pattern b4 = Pattern.compile("1(01*0|10*1)*10*");
        // Divisible by 4                1->3          3->3           3->1
        Pattern c4 = Pattern.compile("1((1|01|000*11)|(1|01|000*11)*|(000*1))*000*");
        // Divisible by 123?
        
//        Matcher m2 = d2.matcher("01011");
//        Matcher m2 = e2.matcher("11101");
        Matcher m2 = c3.matcher("11101");
        StdOut.println(m2.matches());
        StdOut.println(m2.toMatchResult());
    }
    
}
