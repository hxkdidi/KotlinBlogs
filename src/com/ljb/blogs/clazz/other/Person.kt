package com.ljb.blogs.clazz.other

/**
 * Created by L on 2017/6/21.
 */

class Person(var name: String = "无名") {

    //对象申明
    object obj {
        var num = 0
    }


    var age: Int = 0
        get() {
            if (field < 16) {   //备用属性
                return 0
            } else if (field in 16..30) {
                return 1
            } else return 2
        }

    init {
        println("属性已被初始化：$name")
        println("主构造额外操作")
    }


    constructor (name: String, age: Int) : this(name) {
        this.age = age
    }


    fun tell() {
        println("My name is $name  and age is $age.")
    }

}