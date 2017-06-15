package com.ljb.blogs.packages.a;   //包名和实际的文件目录必须相同


import com.ljb.blogs.packages.c.Person;

/**
 * Package Java测试类
 * Created by L on 2017/6/15.
 */
public class PackageJava {

    public static void main(String[] args){

        //两个同名类，至少你得写一个完成包名的类来进行区分
        Person c = new Person();
        com.ljb.blogs.packages.d.Person d = new com.ljb.blogs.packages.d.Person();

        c.tell();
        d.tell();
    }

}
