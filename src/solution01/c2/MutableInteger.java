package solution01.c2;

/******************************************************************************
 *  Compilation:  javac MutableInteger.java
 *  Execution:    java MutableInteger
 *  Dependencies: StdOut.java
 *
 *  Shows that Integerss are mutable if you allow reflection.
 *
 ******************************************************************************/

import java.lang.reflect.Field;

import stdlib.StdOut; 

public class MutableInteger {

    public static void main(String[] args) { 
        Integer x = 17;
        StdOut.println(x); 
        mutate(x);
        StdOut.println(x);
    } 

    // change the Integer to 9999999
    public static void mutate(Integer x) {
        try {
            Field value = Integer.class.getDeclaredField("value"); 
            value.setAccessible(true); 
            value.setInt(x, 999999999);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    } 

} 
