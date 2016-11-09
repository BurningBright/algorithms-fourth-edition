package class0205;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import stdlib.StdOut;

/**
 * @Description 2.5.28
 *      文件按文件名排序
 * @author Leon
 * @date 2016-11-07 16:07:55
 */
public class SortFilesByName {
    
    static class FileNameComparator implements Comparator<File> {
        
        @Override
        public int compare(File o1, File o2) {
            return o2.getName().compareTo(o1.getName());
        }
        
    }
    
    public static void main(String[] args) {
        String s = SortFilesByName.class.getResource("").getPath();
        StdOut.println(s);
        File[] fs = new File(".").listFiles();
        StdOut.println(Arrays.toString(fs));
        Arrays.sort(fs, new FileNameComparator());
        StdOut.println(Arrays.toString(fs));
    }

}
