package com.mycode.purchasing;

import com.mycode.purchasing.utils.ExcelUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
class PurchasingApplicationTests {

  @Test
  void contextLoads() throws IOException {

    List<Map> maps = ExcelUtils.readExcel2007("D:\\easoucode\\JerryCodeApp\\mycode\\purchasing\\src\\main\\resources\\weixin1125.xlsx", "weixin1125");
    int yingValue = 0;
    int junValue = 0;
    int index = 0;
    int valueIndex = 0;
    for(Map map : maps) {
      index++;
      if(map.size() > 5){
        valueIndex++;
//        Set<Map.Entry> set = map.entrySet();
//        Iterator<Map.Entry> iterator = set.iterator();
//        while (iterator.hasNext()){
//          Map.Entry entry = iterator.next();
//          System.out.println(entry.getKey() +"   " + entry.getValue());
//        }


        String user = map.get("交易对方").toString();
        String value =  map.get("金额").toString();
        //System.out.println(map.get("交易时间").toString());
        if(user.contains("Ying")){
          yingValue+= Integer.parseInt(value);
        } else if(user.contains("Jun")){
          junValue+= Integer.parseInt(value);
        }  else {
          System.err.println("Index：" + index  + user);
        }
      }
    }
    System.out.println("周颖："+yingValue);
    System.out.println("翟哥："+junValue);
    System.out.println("共计：" + (yingValue + junValue));
    System.out.println("总共条数：" + valueIndex);
  }
}
