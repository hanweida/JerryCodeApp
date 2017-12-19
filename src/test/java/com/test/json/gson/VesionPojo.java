package com.test.json.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;

/**
 * Created by ES-BF-IT-126 on 2017/12/19.
 */
public class VesionPojo {
    @Since(value = 4)
    private String privateString = "private";
    @Until(value = 5)
    public String publicString = "public";
    protected String protectedString = "protected";
    final String finalString = "final";
    static String staticString = "static";

    @Override
    public String toString() {
        return "VesionPojo{" +
                "privateString='" + privateString + '\'' +
                ", publicString='" + publicString + '\'' +
                ", protectedString='" + protectedString + '\'' +
                ", finalString='" + finalString + '\'' +
                '}';
    }
}
