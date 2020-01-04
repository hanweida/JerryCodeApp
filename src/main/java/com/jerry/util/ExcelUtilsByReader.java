package com.jerry.util;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ExcelUtilsByReader {
    /**
     * 解析Excel
     * @param inputStream
     */
    private void readMaterialFromExcel(InputStream inputStream) {
        ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLS,null, new AnalysisEventListener<List<String>>() {

            @Override
            public void invoke(List<String> data, AnalysisContext analysisContext) {
                String date = data.get(0);
                if ((null != date) && !date.equals("日期")) {
                    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
                    Date dates = formatter.parseLocalDate(data.get(0)).toDate();
                    //data.get(1); //第二列
                    //data.get(2); //第三列
                }

            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                
            }
        });

        List<Sheet> sheets = excelReader.getSheets();
        if (!sheets.isEmpty()) {
            excelReader.read(sheets.get(0));
        }
    }
}
