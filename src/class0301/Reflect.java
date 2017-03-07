package class0301;

import java.lang.reflect.Method;

import rlgs4.Stopwatch;

public class Reflect {
    
    public void print(String world) {
        System.out.println("Hello " + world);
    }
    
    public static double time(Reflect obj, String origin, String met, String str) {
        Stopwatch watch = new Stopwatch();
        try {
            Method method = Class.forName(origin).getMethod(met, String.class);
            method.invoke((Object)obj, (Object)str);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return watch.elapsedTime();
    }
    
    public static void main(String[] args) {
        System.out.println(time(new Reflect(), "class0301.Reflect", "print", "wold"));
    }
    
}
