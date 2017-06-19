package com.ljb.blogs.packages.b //包名和实际的文件目录可以不同

import DefaultKotlin
import com.ljb.blogs.packages.c.Person as CPerson   //设置别名
import com.ljb.blogs.packages.d.Person as DPerson

/**
 * Package Kotlin测试类
 * Created by L on 2017/6/15.
 */
fun main(args: Array<String>) {

    //---测试没有定义包名的类---

    testDefault()

    //---测试类型相同包名不同的类---

    testSameNameJava()

    testSameNameKotlin()

}

/**
 * Kotlin中提供的方法 , 使用as设置别名
 * */
fun testSameNameKotlin() {

    val c = CPerson()
    val d = DPerson()

    c.tell()
    d.tell()
}


/**
 * 类似java中的方法，通过包名区分
 * */
fun testSameNameJava() {

    val c = com.ljb.blogs.packages.c.Person()
    val d = com.ljb.blogs.packages.d.Person()

    c.tell()
    d.tell()
}

/**
 *测试没有定义包名的类
 * */
fun testDefault() {
    val def = DefaultKotlin()
    def.tell()
}
