package com.ljb.blogs.funs;

/**
 * Created by L on 2017/6/19.
 */
public class ButtonJ {

    public void downClick(OnClick click) {
        System.out.println("---start---");
        if (click != null) {
            click.onClick();
        }
        System.out.println("---end---");
    }

    public interface OnClick {
        void onClick();
    }

}
