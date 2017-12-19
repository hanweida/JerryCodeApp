package com.test.json.gson;

import com.google.gson.annotations.Expose;

/**
 * Created by ES-BF-IT-126 on 2017/12/19.
 */
public class ExposePojo {
    @Expose
    private String privateString = "private";
    @Expose
    public String publicString = "public";
    @Expose(deserialize = true, serialize = false)
    protected String protectedString = "protected";
    @Expose(deserialize = false, serialize = true)
    final String finalString = "final";

    static String staticString = "static";
    private int privateint = 1;
    private Integer privateInteger = 2;
}
