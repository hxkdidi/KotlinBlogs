package com.ljb.blogs.flow;

/**
 * Created by L on 2017/6/16.
 */
public class JavaDemo {

    public static void main(String[] args) {

        test3mu();

    }

    private static void test3mu() {
        int num = 3;
        String result = num % 2 == 0 ? "偶数" : "奇数";
        System.out.println(result);
    }

}
