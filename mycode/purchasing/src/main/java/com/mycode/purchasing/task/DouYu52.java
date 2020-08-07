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
public class DouYu52 {
    private static final HttpUtil httpUtil = new HttpUtil();

    private static final String URL = "https://www.douyu.com/japi/carnival/nc/point/exchangeGift";

    Logger logger = LoggerFactory.getLogger(DouYu52.class);


    //formData 表单数据
    private static final String FORMDATA = "nameEn=2020721codsj_credits1&giftId=926&csrfToken=8e92d220ebf14e5eacf64788d09e0038";

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
        hashMap4.put("cookie", "dy_did=283b021adda443001ccb1bef00091501; acf_did=283b021adda443001ccb1bef00091501; Hm_lvt_e99aee90ec1b2106afe7ec3b199020a7=1595920922,1595921064; smidV2=20200728152429d4ee82ba6bda0f8cbc24b38e1ac7be1b00259a3f3964e87c0; PHPSESSID=k4qrnop54g7rr8ghvr7j9pfk67; acf_avatar=https%3A%2F%2Fapic.douyucdn.cn%2Fupload%2Favatar%2Fdefault%2F12_; acf_auth=eba7z9imiaQvK2IGS8joch35pzt6J%2BvXO3bkqMZ5oAL8T16NccVi7AVenAg5%2BBfWtGQuuzUz4ilBGIsQ45h0gxjOZl9%2B6TgZ4IxfhbfXbjcSe%2FeBiektXVM; dy_auth=1c3dQt%2FB6hlFC0XbrMPgt87p7EFaLT3H0GBEgGqxefwmTmoYZkn9jTRiLeh%2Fj5zZit7VsJghJovKqzEOWyB1v6WpnjORZWnYaya3wr9k%2BgUz7fDuXZgLstQ; wan_auth37wan=d31589d35891jtzwOFE15aoDeusLJVpcbxxcP%2FNdmEH2tlu4ees5fMpbdmlWoEiTItqrxPlWBCdCb95spPb%2B8AGBVQoH1YihNAAaMHyNqAD4b4uEWxg; acf_uid=374359233; acf_username=374359233; acf_nickname=%E7%94%A8%E6%88%B726141071; acf_own_room=0; acf_groupid=1; acf_phonestatus=1; acf_ct=0; acf_ltkid=31049176; acf_biz=1; acf_stk=b3a11a831870d263; acf_isNewUser=1; Hm_lpvt_e99aee90ec1b2106afe7ec3b199020a7=1596636342; cvl_csrf_token=8e92d220ebf14e5eacf64788d09e0038");
        hashMap4.put("origin", "https://www.douyu.com");
        hashMap4.put("referer", "https://www.douyu.com/topic/cods45");
        hashMap4.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
        hashMap4.put("x-dy-traceid", "5fd92d3240e2428e:5fd92d3240e2428e:0:007220");
        hashMap4.put("x-requested-with", "XMLHttpRequest");
        return hashMap4;
    }
}
