package com.ljb.blogs.flow


/**
 * Created by L on 2017/6/16.
 */

fun main(args: Array<String>) {

    test3mu()

    testWhen()

    testFor()

    testBreak()
}


/**
 * 标签跳转
 * */
fun testBreak() {

    var arr = arrayOf(
            arrayOf(arrayOf(1, 2, 3), arrayOf(1, 88, 3), arrayOf(1, 2, 3)),
            arrayOf(arrayOf(1, 2, 3), arrayOf(1, 2, 3), arrayOf(1, 88, 3)),
            arrayOf(arrayOf(1, 88, 3), arrayOf(1, 2, 3), arrayOf(1, 2, 3))
    )

    for (item in arr) {
        loop@ for (item2 in item) {
            for (item3 in item2) {
                if (item3 == 88) {
                    println("找到了：$item3")
                    break@loop
                }
            }
        }
    }


}

/**
 * for循环语句
 * */
fun testFor() {

    val arr = intArrayOf(1, 2, 3, 4)

    //获取item
    for (item: Int in arr) {
        println(item)
    }

    //获取索引
    for (index in arr.indices) {
        println(arr[index])
    }

    //即获取对象，又获取索引
    for ((index, item) in arr.withIndex()) {
        println("$index :: $item")
    }


}


/**
 * when语句
 * */
fun testWhen() {

    val num = 3
    val result = when (num) {
        1 -> "num=1"
        2 -> "num=2"
        3 -> "num=3"
        4, 5, 6 -> "num = 4 or 5 or 6"
        else -> "不认识这个数"
    }
    println(result)

}

/**
 * if表达式
 * */
fun test3mu() {
    val num = 3
    val result = if (num % 2 == 0) {
        println("哈哈哈哈")
        "偶数"
    } else {
        println("啦啦啦啦")
        "奇数"
    }
    println(result)
}
