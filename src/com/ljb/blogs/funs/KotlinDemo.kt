package com.ljb.blogs.funs

import java.math.BigInteger

/**
 * Created by L on 2017/6/16.
 */


fun main(arrgs: Array<String>) {

    //使用参数默认值
    println(add(1, 2))
    println(add(1, 2, 3))

    //指定命名参数调用
    println(add(1, n3 = 4))  //跳过第2个参数

    //可变参数
    println(addMore(1, 2, 3, 4, 5))

    //闭包函数
    println(outFun())

    //扩展函数
    val num :Int = 10
    //调用我们自定义的扩展函数
    println(num.add(1))

    //递归函数（阶乘数过大，依旧栈内存移除）
    println(factorial1(10))

    //伪递归函数
    var result = Result()
    factorial2(10000, result)
    println(result.value)

    //高阶函数
    val buttonK = ButtonK()
    buttonK.downClick(fun() {
        println("Kotlin按钮被点击1")
    })

    //省略写法
    buttonK.downClick {
        println("Kotlin按钮被点击2")
    }

    //增加额外参数
    buttonK.downClick(666, fun(arg: Int) {
        println("这是你传入的参数：$arg")
    })

    //再简化
    buttonK.downClick(777, { println("这是你传入的参数：$it") })

}

    /**
     * 默认参数
     * */

    fun add(n1: Int = 0, n2: Int = 0, n3: Int = 0): Int {
        return n1 + n2 + n3
    }

    /**
     * 可变参数
     * */

    fun addMore(vararg arr: Int): Int {
        var result = 0
        for (num in arr) {
            result += num
        }
        return result
    }

    /**
     * 单表达式函数
     * */

    fun add2(n1: Int = 0, n2: Int = 0, n3: Int = 0) = n1 + n2 + n3


    /**
     *局部函数（闭包函数）
     * */

    fun outFun(): Int {
        var n = 1

        fun inFun(): Int {
            n += 1
            return n
        }

        return inFun()
    }

/*
fun tell(str: String): Unit {
    println(str)
}

//Unit省略不写
fun tell(str: String) {
    println(str)
}*/


/**
 * 扩展函数,给Int类添加一个add()函数
 */
fun Int.add(num: Int): Int {
    return this.plus(num)
}


    /**
     * 递归函数
     * */

    fun factorial1(num: Int): Long {
        if (num == 1 || num == 0) {
            return 1
        } else {
            return num * factorial1(num - 1)
        }
    }


    /**
     * 伪递归其实编译器在编译后会将其改为遍历的方式实现
     */
    tailrec fun factorial2(num: Int, end: Result) {
        if (num == 1 || num == 0) {
            return
        } else {
            end.value *= BigInteger.valueOf(num.toLong())
            factorial2(num - 1, end)
        }
    }

