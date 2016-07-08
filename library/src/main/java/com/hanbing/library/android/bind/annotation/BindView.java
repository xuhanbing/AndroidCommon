package com.hanbing.library.android.bind.annotation;

/**
 * Created by hanbing on 2016/7/7
 */
public @interface BindView {
    int value();
    String tag() default "";
}
