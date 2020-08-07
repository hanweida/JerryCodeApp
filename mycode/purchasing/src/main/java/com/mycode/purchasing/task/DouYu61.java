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
public class DouYu61 {
    private static final HttpUtil httpUtil = new HttpUtil();

    private static final String URL = "https://www.douyu.com/japi/carnival/nc/point/exchangeGift";

    Logger logger = LoggerFactory.getLogger(DouYu61.class);


    //formData 表单数据
    private static final String FORMDATA = "nameEn=2020721codsj_credits1&giftId=925&csrfToken=d949943d8afa41709c8736293e8b73b2";

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
        hashMap4.put("cookie", "dy_did=48469ef8337055e9ab423ff900091501; acf_did=48469ef8337055e9ab423ff900091501; smidV2=202007281515230f087e64ad4d1202ebb769286047747e003a93e8cb6442b30; Hm_lvt_e99aee90ec1b2106afe7ec3b199020a7=1595920518,1595926220; PHPSESSID=3gno7vhd6cs0hnp6276qt60ui1; acf_auth=8f29xgvxrCFu5PCFt7O0gDalFXcLfBw8OB6Jk5amo1IQtREI3SBn5hDTY0BQNWM0re%2FGqtafYfnTKrrAiTSikm5IAHEJHu%2BbyGCUrhqgie8kt6VSvjoMXVc; dy_auth=28766L1Cw44xbrU9yha09Om6j37dBO24hCYFZEt8HAFFj3JC74DhPXzSjocgd0J28ArjPf%2BE47wJPZp6Btj7bXAFUCW77PkozHjk8VqP%2F9seELRdXLGadJM; wan_auth37wan=46d49a70d15b4zlk4HLInnxIVDU9VPfuUDTvyZbvAulsdDyXKo0PTlj8ugpNnmLVAqRK0DNtRsTSmW0NOpdUhAbRMwkmGJBlH7RqIkCMzovvEeCFEWI; acf_uid=374358553; acf_username=374358553; acf_nickname=%E7%94%A8%E6%88%B798005824; acf_own_room=0; acf_groupid=1; acf_phonestatus=1; acf_avatar=https%3A%2F%2Fapic.douyucdn.cn%2Fupload%2Favatar%2Fdefault%2F12_; acf_ct=0; acf_ltkid=32827882; acf_biz=1; acf_stk=8fbd93d8adb99b4c; acf_isNewUser=1; Hm_lpvt_e99aee90ec1b2106afe7ec3b199020a7=1596636552; cvl_csrf_token=d949943d8afa41709c8736293e8b73b2");
        hashMap4.put("origin", "https://www.douyu.com");
        hashMap4.put("referer", "https://www.douyu.com/topic/cods45");
        hashMap4.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
        hashMap4.put("x-dy-traceid", "6a19142858718d8f:6a19142858718d8f:0:004080");
        hashMap4.put("x-requested-with", "XMLHttpRequest");
        return hashMap4;
    }
}
