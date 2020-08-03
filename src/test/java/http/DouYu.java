package http;

import com.alibaba.fastjson.JSON;
import com.jerry.util.http.HttpExeResult;
import com.jerry.util.http.HttpUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class DouYu {
    private static final HttpUtil httpUtil = new HttpUtil();

    private static final String URL = "https://www.douyu.com/japi/carnival/nc/point/exchangeGift";

    //formData 表单数据
    private static final String FORMDATA = "nameEn=2020721codsj_credits1&giftId=927&csrfToken=5924f99fcfd94f19b2889e7d8a2f8e84";

    @Test
    public void test() throws Exception {
        System.out.println(getMessages());
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
        hashMap4.put("cookie", "acf_did=44e0f3831d643fc08740296900061501; smidV2=20200713124920eb961839f5b780b70fbc78e29ab3c076009b5ce1121b4c4b0; PHPSESSID=lging4k53p15ei1rqog9isiu40; dy_did=44e0f3831d643fc08740296900061501; Hm_lvt_e99aee90ec1b2106afe7ec3b199020a7=1595997819,1596104183,1596181923,1596437725; acf_auth=32a0VJMW00YuxuDuYdIJ%2Fwe98yXZsiv%2FiIxuvSRIme95B3jhPZ50VDdw6dPoK%2Fcm5o%2BZjGwb5akBixksZdsc73snYj%2FcdmNZTfCy4MVbhjzApYXykjML; dy_auth=5ac6%2FbHXiZmjtQOeFxdgwU54POBtjk%2F5VPNBulI8g6SQhJqxHxjXsYMMP7Eu8otQB0J1DKcWmbT8BmFMruK3XPICTluSTShmNPoE0hy9Vz52yVyBXhmI; wan_auth37wan=3a5f6de730fa52PvuKC7j0FJb3QcgzUMloSYPwIFW1hK2%2FGcyLf4U17rrB%2FIu%2FLy07oiO3DBhuBdbTrweUWQb8tut7bc59Mq4AqCa2juZl6OPoHEhA; acf_uid=97870048; acf_username=97870048; acf_nickname=%E5%95%8A%E5%95%8A%E5%93%92%E5%95%8A%E5%95%8A; acf_own_room=1; acf_groupid=1; acf_phonestatus=1; acf_avatar=https%3A%2F%2Fapic.douyucdn.cn%2Fupload%2Favatar_v3%2F202005%2F06e07a1ab93049ea91a6ce32c28e7e23_; acf_ct=0; acf_ltkid=82751986; acf_biz=1; acf_stk=69530ba8156cd683; Hm_lpvt_e99aee90ec1b2106afe7ec3b199020a7=1596437747; cvl_csrf_token=5924f99fcfd94f19b2889e7d8a2f8e84");
        hashMap4.put("origin", "https://www.douyu.com");
        hashMap4.put("referer", "https://www.douyu.com/topic/cods45");
        hashMap4.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36");
        hashMap4.put("x-dy-traceid", "19da9540350a446f:19da9540350a446f:0:001890");
        hashMap4.put("x-requested-with", "XMLHttpRequest");
        return hashMap4;
    }
}
