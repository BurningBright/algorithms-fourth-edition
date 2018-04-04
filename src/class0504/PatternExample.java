package class0504;

import java.util.regex.Pattern;

/**
 * @Description 5.4.12
 *              正则例子
 * @author Leon
 * @date 2018-03-23 17:15:00
 */
public class PatternExample {
    
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        Pattern a = Pattern.compile("\\(\\d{3}\\)\\d{3}-\\d{4}");
        Pattern b = Pattern.compile("\\d{3}-\\d{2}-\\d{4}");
        Pattern c = Pattern.compile("\\w{3,8} [123]?\\d, \\d{4}");
        Pattern d = Pattern.compile("(?:[0-9]{1,3}\\.){3}[0-9]{1,3}");
        Pattern e = Pattern.compile("\\d{4}[A-Z]{2}");
    }
    
}
