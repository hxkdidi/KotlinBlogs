package com.ljb.blogs.clazz;

import com.ljb.blogs.clazz.other.X;

/**
 * Created by L on 2017/6/22.
 */
public class JavaDemo {

    public static void main(String args[]) {
        X.Y y = new X().new Y();

        X.Z z = new X.Z();
    }
}
