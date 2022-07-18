package jdk.collections;

import org.junit.Test;

import java.util.HashMap;

public class testHashMap {
    @Test
    public void test() {
        HashMap test = new HashMap(1);
        test.put(1, 1);
        test.put(2, 1);
        test.put(3, 1);
        test.put(4, 1);
        test.put(5, 1);
        test.put(6, 1);
        test.put(7, 1);
        test.put(8, 1);
        test.put(9, 1);
        test.put(10, 1);
        test.put(11, 1);
        test.put(12, 1);
        test.put(13, 1);
        test.put(14, 1);
        test.put(15, 1);
        test.put(16, 1);
        test.put(17, 1);
    }


    @Test
    public void test2() {
        for(int i = 0; i < 1000; i++) {
            String s = "{" +
                    "\"account_id\": 2644750941,\n" +
                    "\"campaign_name\": \"五一节大促推广计划"+i+"\",\n" +
                    "\"campaign_type\": \"CAMPAIGN_TYPE_NORMAL\",\n" +
                    "\"promoted_object_type\": \"PROMOTED_OBJECT_TYPE_APP_IOS\"}\n" +
                    ",";
            System.out.println(s);
        }
    }
}
