package com.ljb.blogs.delegation.kotlin

import kotlin.properties.Delegates
import kotlin.reflect.KProperty
import kotlin.reflect.full.valueParameters

/**
 * Created by L on 2017/6/28.
 */
class Person() {

    var name: String by PrefixName()

    var age: Int by Delegates.observable(0, {
        property, oldValue, newValue ->
        println("属性名：${property.name} , 旧值：$oldValue ,  新值：$newValue")
    })

    constructor(name: String) : this() {
        this.name = name
    }

}


class PrefixName {

    private var proxyName: String = "admin_"

    operator fun getValue(person: Person, property: KProperty<*>): String {
        return "admin_$proxyName"
    }

    operator fun setValue(person: Person, property: KProperty<*>, s: String) {
        proxyName = s
    }

}