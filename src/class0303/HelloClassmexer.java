package class0303;

import com.javamex.classmexer.MemoryUtil;
/**
 * @Description the memory
 *      java -javaagent:classmexer.jar mypackage/MyClass
 * @author Leon
 * @date 2017-04-14 19:06:13
 */
class HelloClassmexer {

    public static void main(String[] args) {
        String[] src = new String[1000];
        for (int i = 0; i < src.length; i++) {
            src[i] = String.valueOf(i);
        }

        System.out.println(MemoryUtil.deepMemoryUsageOf(src));
    }

}