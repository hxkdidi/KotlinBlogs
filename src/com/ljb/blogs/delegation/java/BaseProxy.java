package com.ljb.blogs.delegation.java;

/**
 * Created by L on 2017/6/28.
 */
public class BaseProxy implements Base {

    private Base impl;

    public BaseProxy(Base impl) {
        this.impl = impl;
    }

    @Override
    public void doSome() {
        impl.doSome();
    }
}
