package com.ljb.blogs.base

import com.ljb.blogs.base.point.Point


/**
 * 基本类型Demo
 * Created by L on 2017/6/15.
 */

fun main(args: Array<String>) {

//    testChar()
//
//    textLXTD()

//    testZX()

//    testGJDZX()

//    testOperation()

//    testPoint()

    testString()

}


/**
 * 字符串
 * */
fun testString() {

    val str: String = "abcdef"
    //索引访问
    println(str[0])

    //迭代
    for (c in str) {
        println(c)
    }

    //段落字符串
    val lines = """
line1
line2
line3
"""
    println(lines)

    //字符串模板
    val a = 1
    val b = 2.5
    val c = true
    println("a=$a , b=$b , c=$c")


    val s = "123456"
    println("size=${s.length}")

}

/**
 * 中缀运算符
 * */
fun testPoint() {

    val point1 = Point(1, 2)
    val point2 = Point(2, 2)
//    val result = point1.add(point2)
    val result = point1 add point2
    println(result)

}


/***
 * 算数运算符
 */
fun testOperation() {

    //算数运算符
    val num: Int = 10
    println(num + 1)
    println(num.plus(1))

    //Kotlin位运算符（有符号右移）
    println(2 shr 1)
}

/**
 * 高精度转型
 * */
fun testGJDZX() {
    val numInt: Int = 1
    val numLong: Long = numInt.toLong()

    //如果Kotlin支持隐式转换会怎么样（伪代码，会报错）
//    val a : Int  = 1
//    val b : Long = a
//
//    println(a == b) //false
}

/**
 * 自动装箱
 * */
fun testZX() {
    val num1: Int = 128
    val num2: Int? = num1
    val num3: Int? = num1

    println(num2 == num3)   //true
    println(num2 === num3)  //false
}


/**
 * 类型推到
 * */
fun textLXTD() {
    //通过后面的1可以推到出是Int型数据，所以可以写成num2的形式
    val num1: Int = 1
    val num2 = 2
}


/**
 * Char类型不是数值型
 * */
fun testChar() {
    val c: Char = 'a'
    println(c.toInt() == 97)
    println(c + 1)
}
