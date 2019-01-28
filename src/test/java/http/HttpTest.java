package http;

import com.jerry.util.HttpClientUtil;
import com.jerry.util.HttpUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class HttpTest {
    @Test
    public void test(){
        HttpClientUtil httpClientUtil = new HttpClientUtil();
        HashMap<String, String> hashMap4 = new HashMap<String, String>();
        hashMap4.put("leftTicketDTO.train_date", "2019-01-02");
        hashMap4.put("leftTicketDTO.from_station", "BJP");
        hashMap4.put("leftTicketDTO.to_station", "HBB");
        hashMap4.put("purpose_codes", "ADULT");

        String url4 = "https://kyfw.12306.cn/otn/leftTicket/queryZ";
        String str4 = httpClientUtil.sendDataGet(url4, hashMap4);
        System.out.println(str4);

        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
    }
}
