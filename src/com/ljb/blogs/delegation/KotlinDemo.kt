package com.ljb.blogs.delegation

import com.ljb.blogs.delegation.kotlin.BaseImpl
import com.ljb.blogs.delegation.kotlin.BaseProxy
import com.ljb.blogs.delegation.kotlin.Person
import com.ljb.blogs.delegation.kotlin.User

/**
 * Created by L on 2017/6/28.
 */

fun main(args: Array<String>) {

    //委托类
    val proxy = BaseProxy(BaseImpl())
    proxy.doSome()

    //委托属性
    val per = Person("Jake")
    println(per.name)
    per.name = "haha"
    println(per.name)

    //常见的委托

    //lazy
    val str: String by lazy { "abc" }
    //...没使用前是不会被初始化的
    println(str)  //输出：abc

    //lazy多线程安全
    val str2: String by lazy(LazyThreadSafetyMode.PUBLICATION) { "bcd" }

    //可观察Delegates.observable()
    per.age = 10


    // map委托
    val user = User(mapOf(
            "name" to "John Doe",
            "age"  to 25
    ))

}
