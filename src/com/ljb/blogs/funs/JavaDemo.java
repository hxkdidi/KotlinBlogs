package com.ljb.blogs.funs;

import com.ljb.blogs.funs.other.ButtonJ;

/**
 * Created by L on 2017/6/19.
 */
public class JavaDemo {

    public static void main(String[] args) {

        //递归求阶乘
        long result = factorial(10000);
        System.out.println(result);

        //高阶函数
        ButtonJ buttonJ = new ButtonJ();
        buttonJ.downClick(new ButtonJ.OnClick() {
            @Override
            public void onClick() {
                System.out.println("Java按钮被点击");
            }
        });
    }


    private static long factorial(int num) {
        if (num == 0 || num== 1) {
            return 1;
        } else {
            return num * factorial(num-1);
        }
    }

}

