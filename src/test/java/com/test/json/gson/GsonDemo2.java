package com.test.json.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;

/**
 * Gson流式化（流式序列化、流式反序列化）
 * Created by ES-BF-IT-126 on 2017/12/19.
 */
public class GsonDemo2 {

    /**
     * 手动流式反序列化
     * @author:ES-BF-IT-126 
     */
    public void jsonReaderBySelf(){
        String jsonStr = "{\"username\":\"han\",\"age\":2}";
        UserPojo userPojo = new UserPojo();
        JsonReader jsonReader = new JsonReader(new StringReader(jsonStr));
        try {
            jsonReader.beginObject();
            while (jsonReader.hasNext()){
                String s = jsonReader.nextName();
                switch (s){
                    case "username" :
                        userPojo.name = jsonReader.nextString();break;
                    case "age" :
                        userPojo.age = jsonReader.nextInt();break;
                }
            }
            jsonReader.endObject();
            System.out.println(userPojo.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 手动流式序列化
     * @author:ES-BF-IT-126
     */
    public void jsonWriterBySelf(){
        UserPojo userPojo = new UserPojo("write", 3);
        JsonWriter jsonWriter = new JsonWriter(new OutputStreamWriter(System.out));
        try {
            jsonWriter.beginObject()
                    .name("name").value("writer")
                    .name("age").value(11);
            jsonWriter.endObject();
            jsonWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * GsonBuilder 格式化导出空，由于Gson默认不导出空字段值，所以需要加上serializeNulls方法
     */
    public void exportNull(){
        Gson gson = new GsonBuilder().serializeNulls().create();
        UserPojo userPojo = new UserPojo(2);
        String json = gson.toJson(userPojo);
        System.out.println(json);
    }

    /**
     * GsonBuilder Expose字段过滤
     */
    public void exportExpose(){
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        ExposePojo exposePojo = new ExposePojo();
        String exposeStr = gson.toJson(exposePojo);
        System.out.println("Serializer 序列化："+exposeStr);
        //-----------------
        String jsonStr = "{\"privateString\":\"privatestr\",\"publicString\":publicstr,\"protectedString\":\"protectedstr\",\"finalString\":finalstr,\"static\":staticstr}";
        ExposePojo exposePojo1 = gson.fromJson(jsonStr, ExposePojo.class);
        System.out.println("deserializer 反序列化："+exposePojo1.toString());
    }

    /**
     * GsonBuilder 基于策略过滤
     */
    public void exportExclusion(){
        ExposePojo exposePojo = new ExposePojo();
        Gson gson = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                if("privateString".equals(fieldAttributes.getName())){
                    return true;
                }

                Expose expose = fieldAttributes.getAnnotation(Expose.class);
                //如果Expose注解的反序列化为true时，则过滤
                if(null != expose && expose.deserialize()){
                    return true;
                }
                return false;
            }
            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                if(aClass == int.class){
                    return true;
                }
                return false;
            }
        }).create();

        String exposeStr = gson.toJson(exposePojo);
        System.out.println("Serializer 序列化："+exposeStr);
    }

    public static void main(String[] args) {
        GsonDemo2 gsonDemo2 = new GsonDemo2();
        //gsonDemo2.jsonReaderBySelf();
        //gsonDemo2.jsonWriterBySelf();
        //gsonDemo2.exportNull();
        //gsonDemo2.exportExpose();
        gsonDemo2.exportExclusion();
    }
}
