package com.ljb.blogs.funs.other

/**
 * Created by L on 2017/6/19.
 */
class ButtonK {

    fun downClick(click: () -> Unit) {
        println("---start---")
        click()
        println("---end---")
    }


    /**
     * 添加了一个额外参数，并将参数传递给点击事件处理
     * */
    fun downClick(arg: Int, click: (Int) -> Unit) {
        println("---start---")
        click(arg)
        println("---end---")
    }

}
