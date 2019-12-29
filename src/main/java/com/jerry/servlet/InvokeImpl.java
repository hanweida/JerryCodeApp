package com.jerry.servlet;

import java.lang.annotation.Annotation;

public class InvokeImpl implements Invoke {


    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }

    @Override
    public int a() {
        return 0;
    }
}
