package com.ljb.blogs.delegation.kotlin

/**
 * Created by L on 2017/6/28.
 */
class User(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int     by map
}