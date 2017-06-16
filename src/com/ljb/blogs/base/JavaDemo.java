package com.ljb.blogs.base;

/**
 * Created by L on 2017/6/15.
 */
public class JavaDemo {

    public static void main(String[] args) {
        testChar();
        testGJDZX();
        testOperation();
    }

    private static void testOperation() {

        //java 位运算符
        System.out.println(2>>1);
    }


    private static void testGJDZX() {
        int numInt =  1;
        long numLong = numInt;
    }


    private static void testChar() {
        char c = 'a';
        System.out.println(c == 97);
        System.out.println(c + 1);
    }
}
