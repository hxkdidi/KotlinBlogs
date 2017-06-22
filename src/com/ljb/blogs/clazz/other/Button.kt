package com.ljb.blogs.clazz.other

/**
 * Created by L on 2017/6/22.
 */
class Button {

    private var mListener: OnClickListener? = null

    fun setOnClickListener(listener: OnClickListener) {
        this.mListener = listener
    }

    interface OnClickListener {
        fun onClick()
    }

}