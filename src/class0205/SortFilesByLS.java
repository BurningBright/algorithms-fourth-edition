package class0205;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import stdlib.StdOut;

/**
 * @Description 2.5.29
 *      按文件大小、文件名、最后修改时间排序
 *      -s [a/d] -n [a/d] -t [a/d]
 * @author Leon
 * @date 2016-11-07 18:51:57
 */
public class SortFilesByLS {
    
    public static class FileLSComparator implements Comparator<File> {
        private Boolean sizeAesOrder = null;
        private Boolean nameAesOrder = null;
        private Boolean timeAesOrder = null;
        private int[] index = {-1, -1, -1};
        
        public FileLSComparator(String[] args) {
            for (int i=0, j=0; i<args.length; i++) {
                
                if (i < args.length - 1 && "d".equalsIgnoreCase(args[i + 1])) {
                    index[j++] = command(args[i], args[i+1]);
                    i++;
                } else {
                    index[j++] = command(args[i], "");
                }
                
            }
            
        }
        
        private int command(String a, String b) {
            if ("-s".equals(a)) {
                if ("d".equals(b))
                    sizeAesOrder = false;
                else
                    sizeAesOrder = true;
                return 0;
            } else if ("-n".equals(a)) {
                if ("d".equals(b))
                    nameAesOrder = false;
                else
                    nameAesOrder = true;
                return 1;
            } else if ("-t".equals(a)) {
                if ("d".equals(b))
                    timeAesOrder = false;
                else
                    timeAesOrder = true;
                return 2;
            }
            return -1;
        }
        
        @Override
        public int compare(File o1, File o2) {
            for (int i=0; i<index.length; i++) {
                if (index[i] != -1) 
                    if (find(index[i], o1, o2) != 0)
                        return find(index[i], o1, o2);
            }
            return 0;
        }
        
        @SuppressWarnings("resource")
        private int find(Integer index, File o1, File o2) {
            switch(index) {
                case 0:
                    try {
                        if (sizeAesOrder)
                            if (o1.isFile() && o2.isFile())
                                return (new FileInputStream(o1)).available() - (new FileInputStream(o2)).available();
                            else
                                return 0;
                        else
                            if (o1.isFile() && o2.isFile())
                                return (new FileInputStream(o2)).available() - (new FileInputStream(o1)).available();
                            else
                                return 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                    };
                    break;
                case 1:
                    if (nameAesOrder)
                        return o1.getName().compareTo(o2.getName());
                    else
                        return o2.getName().compareTo(o1.getName());
                case 2:
                    if (timeAesOrder)
                        return (int) (o1.lastModified()-o2.lastModified());
                    else
                        return (int) (o2.lastModified()-o1.lastModified());
                default: return 0;
            }
            return 0;
        }
        
    }
    
    public static void main(String[] args) throws Exception {
        StdOut.println(Arrays.toString(args));
        
        File[] fs = new File(".").listFiles(new FileFilter() {
            public boolean accept(File file) {
                if (file.isFile()) 
                    return true;
                return false;
            }
        });
        
        for (File f: fs) 
            if(f.isFile())
                StdOut.println(f.getName() + " \t" + (new Date(f.lastModified())) + " \t"
                        + (new FileInputStream(f)).available() + " Bytes");
        
        StdOut.println("-----------------------------");
        Arrays.sort(fs, new FileLSComparator(args));
        for (File f: fs) 
            if(f.isFile())
                StdOut.println(f.getName() + " \t" + (new Date(f.lastModified())) + " \t"
                        + (new FileInputStream(f)).available() + " Bytes");
    }

}
