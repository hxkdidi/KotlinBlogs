package com.ljb.blogs.clazz.other

/**
 * Created by L on 2017/6/22.
 */

//外部类
class A {

    //伴生对象
    companion object {
        val z: String = "z"  //全局变量

        fun z() {
            println(z)
//            println(a)  //伴生对象只能调用伴生对象中的资源
//            a()
        }
    }

    val a: String = "a"

    fun a() {
        println(a)
    }


    /**
     *测试外部类访问内部类以及嵌套类的资源
     * */
    fun tell() {
//        println(b)
//        println(c)
//
//        b()
//        c()
    }


    //嵌套类
    class B {

        val b: String = "b"

        fun b() {
            println(b)
        }

        fun tell() {
//            println(a)
//            println(c)
//            a()
//            c()

            //嵌套类可以调用伴生对象中的资源（类似Java静态内部类）
            println(z)
            z()
        }

    }

    //内部类
    inner class C {

        val c: String = "c"

        fun c() {
            println(c)
        }

        fun tell() {
            println(a)
//            println(b)
//            a()
//            b()
        }

    }
}