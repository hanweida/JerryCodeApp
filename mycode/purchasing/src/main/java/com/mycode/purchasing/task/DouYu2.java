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
public class DouYu2 {
    private static final HttpUtil httpUtil = new HttpUtil();

    private static final String URL = "https://www.douyu.com/japi/carnival/nc/point/exchangeGift";

    Logger logger = LoggerFactory.getLogger(DouYu2.class);


    //formData 表单数据
    private static final String FORMDATA = "nameEn=2020721codsj_credits1&giftId=927&csrfToken=0805bfe6becc4e16b92312cbab2a98e2";

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
        hashMap4.put("cookie", "dy_did=8c019e254ebdd00f67ec123f00031501; acf_did=8c019e254ebdd00f67ec123f00031501; smidV2=20200316142714de452177ebdec712c7c3981aff56acdc00cc052f03a722350; Hm_lvt_e99aee90ec1b2106afe7ec3b199020a7=1596460007; PHPSESSID=7gqj4ltptlfc7n9vcqam2oiev4; acf_auth=8eadfjgAwjUVaXrCnhE7PTu8tmy9l%2BgiuaFiX9eUq0Pkn25U9Bgad3QuDIxgpm79jtprDVsbmSXw%2BoLhBUbUAXE5C55oeC5MFgl%2Bf9uHTsy%2BsyVNgJahLnQ; dy_auth=b7c3gqjSy%2FHA5GK8AAAYH5vKZYndIenG5n%2FSvYnM8a9l3%2F9rcKM2mCfOT4qfD76e5HnxzeLFuHzxCzFFoyKOlBnF8CSsl8BM9EUVcCmyynFIUqU0NWU0O%2Fk; wan_auth37wan=187d421f883c3VmJ8%2FsfrCmjY5brDWnAoXFQb8bdRk2Gm%2BXlmZn6QiXB9U6eBqYj9XbQ1KIPiaB9y0oKgWCH%2FQCvwv1a9tdXhMweaV%2BGZ1NObHhWyHs; acf_uid=354331839; acf_username=354331839; acf_nickname=%E7%94%A8%E6%88%B760798137; acf_own_room=0; acf_groupid=1; acf_phonestatus=1; acf_avatar=https%3A%2F%2Fapic.douyucdn.cn%2Fupload%2Favatar%2Fdefault%2F04_; acf_ct=0; acf_ltkid=71092598; acf_biz=1; acf_stk=47c17c37bab8f801; Hm_lpvt_e99aee90ec1b2106afe7ec3b199020a7=1596460161; cvl_csrf_token=0805bfe6becc4e16b92312cbab2a98e2");
        hashMap4.put("origin", "https://www.douyu.com");
        hashMap4.put("referer", "https://www.douyu.com/topic/cods45");
        hashMap4.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
        hashMap4.put("x-dy-traceid", "22e59f323b5673b7:22e59f323b5673b7:0:003733");
        hashMap4.put("x-requested-with", "XMLHttpRequest");
        return hashMap4;
    }
}
