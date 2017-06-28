package com.ljb.blogs.delegation.kotlin

/**
 * Created by L on 2017/6/28.
 */


interface Base {

    fun doSome()
}


class BaseImpl : Base {

    override fun doSome() {
        println("do some thing...")
    }

}

class BaseProxy(val b: Base) : Base by b{


    override fun doSome() {
        println("per")
        b.doSome()
        println("next")
    }
}


