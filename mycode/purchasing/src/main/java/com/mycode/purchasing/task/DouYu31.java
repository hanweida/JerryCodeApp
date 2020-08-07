package com.mycode.purchasing.task;

import com.alibaba.fastjson.JSON;
import com.mycode.purchasing.utils.http.HttpExeResult;
import com.mycode.purchasing.utils.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DouYu31 {
    private static final HttpUtil httpUtil = new HttpUtil();

    private static final String URL = "https://www.douyu.com/japi/carnival/nc/point/exchangeGift";

    Logger logger = LoggerFactory.getLogger(DouYu31.class);


    //formData 表单数据
    private static final String FORMDATA = "nameEn=2020721codsj_credits1&giftId=925&csrfToken=97f337000b554d7e99e67a03cebc7aeb";

    public void excute() throws Exception {
        logger.info(getMessages());
    }

    private String getMessages() throws Exception {
        //String data = "{\"LoginId\":\""+loginId+"\",\"DeviceId\":689359,\"MessageId\":0,\"Count\":"+count+"}";
        String result = "";
        Map<String, String> headerMap = getHeaderMap();
        HttpExeResult herQuery = null;
        try {
            herQuery = httpUtil.exePostMethod(URL, headerMap, FORMDATA);
            result = herQuery.getResult();
            result = JSON.parseObject(result).getString("msg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Map<String, String> getHeaderMap() {
        Map<String, String> hashMap4 = new HashMap();
        hashMap4.put("accept", "application/json, text/plain, */*");
        hashMap4.put("accept-encoding", "gzip, deflate, br");
        hashMap4.put("accept-language", "zh-CN,zh;q=0.9");
        hashMap4.put("content-length", "82");
        hashMap4.put("content-type", "application/x-www-form-urlencoded");
        hashMap4.put("cookie", "acf_did=2027bb8bad1509215f59b08900001501; smidV2=2020041610352578c2e63d12c9d3a57d51f00cdda2f74800ac052931896f630; Hm_lvt_e99aee90ec1b2106afe7ec3b199020a7=1595674405; PHPSESSID=r5chtpmlttmimn1tbfpputs656; acf_avatar=https%3A%2F%2Fapic.douyucdn.cn%2Fupload%2Favatar%2Fdefault%2F04_; _dys_lastPageCode=page_home,page_home; dy_did=2027bb8bad1509215f59b08900001501; acf_auth=23796eeZ%2Bj8HAy%2BpLQkm18o62g48WWW8D7wH%2FMhXOc7uEO7jS6wKUUuLuoQqmz9WY4EJr0ouo5bAnh%2Br%2FqDU9OwFI%2FKzSYX7u3gLFTw2rjDnW4EmqH0xj9o; dy_auth=3dc83fsRiU3uV6u22K9sZSbjqIsfFbTVnllh2jr6FKvXm7CKjGCh4M5f9yAgMZqJ%2BtArjp2dswk8issPIsYwwD4i0WjwQAN406Il2tfFNwa48VhAemKshWk; wan_auth37wan=1b3275d9159c%2BaUI%2BzHyGDVqqn8j4RLcHZo22p%2BOUd7puFcHcUuNtWoYLQSGwmFonr3gHTidiixaZ2WdfKAXwl0n3NHW%2BjngbMkirgS9TvLWo9GjsRc; acf_uid=354331316; acf_username=354331316; acf_nickname=%E7%94%A8%E6%88%B785578401; acf_own_room=0; acf_groupid=1; acf_phonestatus=1; acf_ct=0; acf_ltkid=63835636; acf_biz=1; acf_stk=147284824229dc2b; Hm_lpvt_e99aee90ec1b2106afe7ec3b199020a7=1596636056; cvl_csrf_token=97f337000b554d7e99e67a03cebc7aeb");
        hashMap4.put("origin", "https://www.douyu.com");
        hashMap4.put("referer", "https://www.douyu.com/topic/cods45");
        hashMap4.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
        hashMap4.put("x-dy-traceid", "26145cfd1c2212bb:26145cfd1c2212bb:0:002758");
        hashMap4.put("x-requested-with", "XMLHttpRequest");
        return hashMap4;
    }
}
