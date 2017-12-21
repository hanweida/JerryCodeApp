package com.test.json.gson;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;

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
        Gson gson = new GsonBuilder().registerTypeAdapter(UserPojo.class, new TypeAdapter<UserPojo>() {
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
                }
                return jsonSerializationContext.serialize(userPojo);  //To change body of implemented methods use File | Settings | File Templates.
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

        //UserPojo userPojo = gson.fromJson(jsonStr, UserPojo.class);
        //System.out.println("反序列化："+userPojo.toString());
        System.out.println("--------------");
        UserPojo userPojo1 = new UserPojo("writeJson", 30);
        System.out.println("序列化："+gson.toJson(userPojo1));
    }

    public static void main(String[] args) {
        GsonDemo3 gsonDemo3 = new GsonDemo3();
        //gsonDemo3.typeAdatper();
        gsonDemo3.jsonAdatper();

    }
}
