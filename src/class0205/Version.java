package class0205;

import stdlib.StdOut;

/**
 * @Description 2.5.10
 *      对比版本号
 * @author Leon
 * @date 2016-10-26 11:04:51
 */
public class Version implements Comparable<Version>{
    
    private String[] v;
    
    public Version(String v) {
        this.v = v.split("\\.");
        if(v == null || v.length() < 1)
            throw new IllegalArgumentException("版本号不正确");
    }
    
    @Override
    public int compareTo(Version that) {
        if(this == that)
            return 0;
        int n = Math.min(this.v.length, that.v.length);
        // 逐位对比
        for (int i=0; i<n; i++) {
            if (Integer.parseInt(this.v[i]) < Integer.parseInt(that.v[i]) )
                return -1;
            if (Integer.parseInt(this.v[i]) > Integer.parseInt(that.v[i]) )
                return 1;
        }
        // 前n位相等， 对比位数
        return this.v.length - that.v.length;
    }
    
    public static void main(String[] args) {
        StdOut.println(new Version("115.1.1").compareTo(new Version("115.10.1")));
        StdOut.println(new Version("115.10.11").compareTo(new Version("115.10.1")));
        StdOut.println(new Version("115.10.10.1").compareTo(new Version("115.10.10")));
    }

}
