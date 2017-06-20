package com.ljb.blogs.lambda

/**
 * Created by L on 2017/6/20.
 */

fun main(args: Array<String>) {

    //普通调用
    tell(fun(): String { return "string" })

    //Lambda表达式调用
    tell({ "string" })

    //函数仅只有一个函数类型的形参，当传入的是Lambda表达式时，该函数的()小括号可以省略不写
    tell { "string" }

    //添加1个参数
    tell2 { "string:$it" } // 输出：string:123

    //添加多个参数
    tell3 { str1, str2 -> "string:$str1 and $str2" } // 输出：string:123 and abc

    //只是用1个参数
    tell3 { str1, _ -> "string:$str1" }  // 输出：string:123
}


/**
 * 函数名：tell
 * 参数：f
 * 参数类型：() 函数
 * 参数函数的返回值：String
 *
 * 函数功能：打印传入函数返回的字符串
 * */
fun tell(f: () -> String) {
    println(f())
}


/**
 * 参数函数中添加1个参数
 * */
fun tell2(f: (str: String) -> String) {
    println(f("123"))
}


/**
 * 参数函数中添加多个参数
 * */
fun tell3(f: (str1: String, str2: String) -> String) {
    println(f("123", "abc"))
}