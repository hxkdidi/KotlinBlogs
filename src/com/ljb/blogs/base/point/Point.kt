package com.ljb.blogs.base.point

/**
 * 坐标数据类
 * Created by L on 2017/6/16.
 */
data class Point(val x: Int, val y: Int) {

    /**
     * 累加方法，返回累加后的Potint
     */
    infix fun add(other: Point): Point {
        return Point(this.x + other.x, this.y + other.y)
    }

    override fun toString(): String {
        return "Point($x, $y)"
    }

}