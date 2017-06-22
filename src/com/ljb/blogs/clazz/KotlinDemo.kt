package com.ljb.blogs.clazz

import com.ljb.blogs.clazz.other.A
import com.ljb.blogs.clazz.other.Button
import com.ljb.blogs.clazz.other.Data
import com.ljb.blogs.clazz.other.Person

/**
 * Created by L on 2017/6/21.
 */

fun main(args: Array<String>) {

    //构造函数
    val per = Person("Jack", 10)
    per.tell()

    //主构造与参数，默认提供无参构造
    val wuMing = Person()

    //修改age getter()函数
    println(per.age) //输出0


    //嵌套类创建对象
    val b = A.B()

    //内部类创建对象
    val c = A().C()


    //测试对象声明
    Person.obj.num = 1
    println(Person.obj.num)

    //对象表达式
    val button = Button()
    button.setOnClickListener(object : Button.OnClickListener {

        override fun onClick() {
            println("onClick")

            per.tell()  //可以访问局部作用域里的变量，并且不用final修饰
        }

    })


    //数据类
    Data("哈哈").tell()


}