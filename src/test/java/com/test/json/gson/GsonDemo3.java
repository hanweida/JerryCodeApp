package com.test.json.gson;

import com.google.gson.*;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * TypeAdapter、JsonDeserializer、JsonDeserializer、registerTypeAdatper、registerTypeHierarchyAdapter
 * Created with IntelliJ IDEA.
 * User: yingmule
 * Date: 17-12-19
 * Time: 下午11:27
 * To change this template use File | Settings | File Templates.
 */
public class GsonDemo3 {
    static final String jsonStr = "{\"name\":\"han\",\"age\":2}";

    /**
     *   typeAdatper 适配器
     */
    public void typeAdatper(){
        Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(UserPojo.class, new TypeAdapter<UserPojo>() {
            @Override
            public void write(JsonWriter jsonWriter, UserPojo userPojo) throws IOException {
                jsonWriter.beginObject();
                jsonWriter.name("name").value("typeAdapter_writer");
                jsonWriter.name("age").value(27);
                jsonWriter.endObject();
            }

            @Override
            public UserPojo read(JsonReader jsonReader) throws IOException {
                UserPojo userPojo = new UserPojo();
                jsonReader.beginObject();
                while (jsonReader.hasNext()){
                    switch (jsonReader.nextName()) {
                        case "name" :
                            userPojo.name=jsonReader.nextString(); //如果自己写字符串值会报错
                            break;
                        case "age" :
                            userPojo.age=jsonReader.nextInt();
                            break;
                    }
                }
                jsonReader.endObject();
                return userPojo;  //To change body of implemented methods use File | Settings | File Templates.
            }
        }).create();

        UserPojo userPojo = gson.fromJson(jsonStr, UserPojo.class);
        System.out.println("反序列化："+userPojo.toString());
        System.out.println("--------------");
        UserPojo userPojo1 = new UserPojo("writeJson", 30);
        System.out.println("序列化："+gson.toJson(userPojo1));
    }

    /**
     *   typeAdatper 适配器
     */
    public void jsonAdatper(){
        JsonParser jsonParser = new JsonParser();

        Gson gson = new GsonBuilder().registerTypeAdapter(UserPojo.class,new JsonSerializer<UserPojo>() {
            @Override
            public JsonElement serialize(UserPojo userPojo, Type type, JsonSerializationContext jsonSerializationContext) {
                if("writeJson".equals(userPojo.name)){
                    userPojo.name = "serializeJson";
                    return jsonSerializationContext.serialize(userPojo);
                } else {
                    return new Gson().toJsonTree(userPojo);
                }
            }
        }
        ).create();

        Gson gson2 = new GsonBuilder().registerTypeAdapter(UserPojo.class, new JsonDeserializer<UserPojo>() {
                    @Override
                    public UserPojo deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                        return null;
                    }
                }
        ).create();

        UserPojo userPojo = gson2.fromJson(jsonStr, UserPojo.class);
        System.out.println("反序列化："+userPojo.toString());
        System.out.println("--------------");
        UserPojo userPojo1 = new UserPojo("writeJson", 30);
        System.out.println("序列化："+gson.toJson(userPojo1));
    }

        /**
         * 由于TypeAdapter<String> 这种可以覆盖Gson里面的默认的 TypeAdapter<String>，但是有一些是Adapter 无法重写，类似于数组，所以需要AdapterFactory
         * @author:ES-BF-IT-126
         * @method:
         * @date:Date 2017/12/21
         * @params:
         * @returns:
         */
        public void jsonAdapterFactory(){
            GsonBuilder gsonBuilder = new GsonBuilder();
            try {
                Class builder = (Class) gsonBuilder.getClass();
                Field f = builder.getDeclaredField("instanceCreators");
                f.setAccessible(true);
                Map<Type, InstanceCreator<?>> val = null;//得到此属性的值
                try {
                    val = (Map<Type, InstanceCreator<?>>) f.get(gsonBuilder);
                    gsonBuilder.registerTypeAdapterFactory(new CollectionTypeAdapterFactory(new ConstructorConstructor(val)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                //注册数组的处理器
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }


    public static void main(String[] args) {
        GsonDemo3 gsonDemo3 = new GsonDemo3();
        //gsonDemo3.typeAdatper();
        gsonDemo3.jsonAdatper();
        //gsonDemo3.jsonAdapterFactory();

    }


}




