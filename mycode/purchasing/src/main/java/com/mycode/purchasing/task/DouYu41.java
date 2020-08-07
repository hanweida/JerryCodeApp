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
public class DouYu41 {
    private static final HttpUtil httpUtil = new HttpUtil();

    private static final String URL = "https://www.douyu.com/japi/carnival/nc/point/exchangeGift";

    Logger logger = LoggerFactory.getLogger(DouYu41.class);


    //formData 表单数据
    private static final String FORMDATA = "nameEn=2020721codsj_credits1&giftId=925&csrfToken=624fc1f9d1af402790f5af0f0f181f1d";

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
        hashMap4.put("cookie", "__guid=234720664.4230197565856326700.1589347950998.674; acf_did=5569df89fb1f36ddb1bf1c2800001501; dy_did=5569df89fb1f36ddb1bf1c2800001501; smidV2=20200416102420136dc66298108cf9f95f61871eb27f4f00c928ba0f296f1f0; Hm_lvt_e99aee90ec1b2106afe7ec3b199020a7=1595673999,1595674002,1595678365; acf_avatar=https%3A%2F%2Fapic.douyucdn.cn%2Fupload%2Favatar%2Fdefault%2F06_; PHPSESSID=97fghtj5vptrptbqb2irj2qlf4; acf_auth=3a3eRoNesiDuNyR3o%2Fi1%2FBdeTCu3fGi7OROABrLI6huQk1gM0NQdgEtcbP3fGdLC4n9755KePWQ0VTbbOY3iP0yEkvGOwqE2TuUZog5HQctYFEy%2FHs4sFi0; dy_auth=bebcCTIIqKNQckSH4DpRDwFFzS2q0O7GdU794eaBeOMdCyLRZdxGV3gCsEZywWe%2B5rcO9Qu94lrm%2FcUCumqR6UxFm9Cq50%2FTfihtnwHgMycLnpEnvRvLc9s; wan_auth37wan=ba96a06f774aSMVwS0liGOm2OOrsru6v47nB8GpnWKuZY9Kn%2BJBKSi3a7Mypu9TAwU%2BC76F0n0JviGj%2FfwYov7vhT%2F8ySsh%2BmTNTGH9b82PiKG43Ubw; acf_uid=353882924; acf_username=353882924; acf_nickname=%E7%94%A8%E6%88%B745053173; acf_own_room=1; acf_groupid=1; acf_phonestatus=1; acf_ct=0; acf_ltkid=15967982; acf_biz=1; acf_stk=7d5ef4aad7d6bd29; monitor_count=4; Hm_lpvt_e99aee90ec1b2106afe7ec3b199020a7=1596561069; cvl_csrf_token=624fc1f9d1af402790f5af0f0f181f1d");
        hashMap4.put("origin", "https://www.douyu.com");
        hashMap4.put("referer", "https://www.douyu.com/topic/cods45");
        hashMap4.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
        hashMap4.put("x-dy-traceid", "464c0ced2aaee923:464c0ced2aaee923:0:002039");
        hashMap4.put("x-requested-with", "XMLHttpRequest");
        return hashMap4;
    }
}
