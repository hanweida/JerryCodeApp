package com.test.json.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Gson基础用法
 * Created with IntelliJ IDEA.
 * User: yingmule
 * Date: 17-12-19
 * Time: 上午12:08
 * To change this template use File | Settings | File Templates.
 */
public class GsonDemo1 {
    /**
     * Gson基础用法（fromJson,toJson）
     */
    public void baseGson(){
        Gson gson = new Gson();
        int i = gson.fromJson("1", Integer.class);
        System.out.println("Inter：" + i);
        String str = gson.fromJson("str", String.class);
        System.out.println("String：" + str);
        float flt = gson.fromJson("1.10f", Float.class);
        System.out.println("Float：" + flt);
        double doubled = gson.fromJson("1.10", Double.class);
        System.out.println("Double：" + doubled);
        boolean bl = gson.fromJson("true", Boolean.class);
        System.out.println("Boolean：" + bl);
        short sht = gson.fromJson("1", Short.class);
        System.out.println("Short：" + sht);
        byte b = gson.fromJson("10", Byte.class);
        System.out.println("Byte：" + b);

        System.out.println("------------------");
        String json = gson.toJson(100);
        String booleans = gson.toJson(true);
        String str1 = gson.toJson("str");
        String floats = gson.toJson(9.9);
        System.out.println(json);
        System.out.println(booleans);
        System.out.println(str1);
        System.out.println(floats);
    }

    /**
     * GsonPOJO类的基本用法
     */
    public void basePOJOGson(UserPojo userPojo){
        Gson gson = new Gson();
        String pojoJson = gson.toJson(userPojo);
        System.out.println("序列化："+pojoJson);

        String jsonStr = "{\"name\":\"han\",\"age\":2}";
        UserPojo userPojo1 = gson.fromJson(jsonStr, UserPojo.class);
        System.out.println("反序列化："+userPojo1.toString());
    }

    /**
     * Gson SerializedName的基本用法
     */
    public void baseSerializedGson(){
        Gson gson = new Gson();
        UserPojo userPojo = new UserPojo("han", 2);
        String pojoJson = gson.toJson(userPojo);
        System.out.println("序列化："+pojoJson);

        String jsonStr = "{\"username\":\"han\",\"age\":2}";
        UserPojo userPojo1 = gson.fromJson(jsonStr, UserPojo.class);
        System.out.println("反序列化："+userPojo1.toString());
        String jsonStr2 = "{\"name\":\"han1\",\"age\":2}";
        userPojo1 = gson.fromJson(jsonStr2, UserPojo.class);
        System.out.println("反序列化："+userPojo1.toString());
        String jsonStr3 = "{\"stuname\":\"han2\",\"age\":2}";
        userPojo1 = gson.fromJson(jsonStr3, UserPojo.class);
        System.out.println("反序列化："+userPojo1.toString());
    }

    /**
     * Gson 泛型的基本用法(数组、List、List<T>)
     */
    public void baseArrayListGson(){
        String jsonBase = "['Andorid','Java', 2]";

        Gson gson = new Gson();
        String[] arrayString = gson.fromJson(jsonBase, String[].class);
        for(String str : arrayString){
            System.out.println("Array:" + str);
        }

        System.out.println("--------List<String>----------");

        //List泛型，由于List<String> 与 List<UserPojo> 是一个字节码文件是一个，List.class，所以需要Token
        //List<String> stringList = gson.fromJson(jsonBase, List<String>.class);   List<String>.class报错： 不能选择参数类型
        List<String> stringList = gson.fromJson(jsonBase, new TypeToken<List<String>>(){}.getType());
        for(String str : stringList){
            System.out.println(str);
        }

        System.out.println("--------泛型 Json串中 data 为对象----------");
        String jsonT = "{\"code\":\"0\",\"message\":\"success\",\"data\":{\"name\":\"hanlist\",\"age\":\"27\"}}";
        //List泛型<T>
        Result<UserPojo> userPojo2 = gson.fromJson(jsonT, new TypeToken<Result<UserPojo>>(){}.getType());
        System.out.println("fromJson 反序列化："+ userPojo2.toString());
        System.out.println("toJson 序列化："+ gson.toJson(userPojo2));

        System.out.println("--------泛型 Json串中 data 为数组----------");
        String jsonArrayT = "{\"code\":\"0\",\"message\":\"success\",\"data\":[{\"name\":\"hanlist\",\"age\":\"27\"},{\"name\":\"han2\",\"age\":\"30\"}]}";
        //List泛型<T>
        Result<List<UserPojo>> userPojo2List = gson.fromJson(jsonArrayT, new TypeToken<Result<List<UserPojo>>>(){}.getType());
        System.out.println("fromJson 反序列化："+ userPojo2List.toString());
        System.out.println("toJson 序列化："+ gson.toJson(userPojo2List));
    }

    public static void main(String[] args) {
        GsonDemo1 gsonDemo1 = new GsonDemo1();
        //gsonDemo1.baseGson();
        //gsonDemo1.baseSerializedGson();
        gsonDemo1.baseArrayListGson();
    }
}
