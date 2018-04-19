package class0505;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import stdlib.StdOut;

/**
 * @Description 5.5.5
 *          行程编码压缩 q128x192.bin 测试压缩率
 *          java solution05.c05.BinaryDump 0 < ../resource/5.5/q128x192.bin
 *          24576 bit
 *          java solution05.c05.RunLength - < ../resource/5.5/q128x192.bin | java solution05.c05.BinaryDump 0
 *          4680 bit
 *          ratio -> 19%
 * @author Leon
 * @date 2018-04-18 14:15:00
 */
public class RunLengthExp {
    
    public static void main(String[] args) throws Exception {
        
        String workPath = System.getProperty("user.dir") + File.separator + "bin";
        // String command = "java solution05.c05.BinaryDump 0 < ../resource/5.5/q128x192.bin";
        String command = "java solution05.c05.RunLength - < ../resource/5.5/q128x192.bin | java solution05.c05.BinaryDump 0";
        Process p = Runtime.getRuntime().exec(new String[]{"D:\\Program Files\\Git\\bin\\sh.exe", "-c", command}, null, new File(workPath));
        
        // 读取命令的输出信息
        InputStream is = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        p.waitFor();
        if (p.exitValue() != 0) 
            StdOut.println(p.exitValue() + "error");
        
        // 打印输出信息
        String s = null;
        while ((s = reader.readLine()) != null) {
            System.out.println(s);
        }
        
    }
    
}
