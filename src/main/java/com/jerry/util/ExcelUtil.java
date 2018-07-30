package com.jerry.util;

import com.jerry.pojo.ExcelInfo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {
    public static void main(String[] args) throws Exception {
        File file = new File("D:\\data.xlsx");
//        String[][] result = getData(file, 1);
//        int rowLength = result.length;
//        for (int i = 0; i < rowLength; i++) {
//            System.out.println(result[i]);
//            for (int j = 0; j < result[i].length; j++) {
//                System.out.print(result[i][j] + "\t\t");
//            }
//            System.out.println();
//        }

        List<ExcelInfo> excelInfoList = readExcel2007(file);
        List<String> stringList = new ArrayList<>();
        if(excelInfoList.size() > 0){
            for(ExcelInfo excelInfo : excelInfoList){
                if("-1".equals(excelInfo.getSubject())){
                    //System.out.println(excelInfo.toString());
                } else {
                    String str = "INSERT INTO novel_info " +
                            "(id,novel_info.classify_id,novel_info.author_id,novel_info.name,novel_info.description,novel_info.label,novel_info.open) VALUE " +
                            "("+excelInfo.getNovelId()+",11042,"+excelInfo.getUserId()+",'"+excelInfo.getContent()+"','"+excelInfo.getContent()+"' ,'宅斗','"+excelInfo.getSubject()+"');";
                    System.out.println(str);
                }

            }
        }
    }

    /**
     * 仅支持2003
     *
     * @param file
     * @throws IOException
     */
    private static void readExcel2003(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        Workbook rwb = new HSSFWorkbook(is);
        Sheet sheet = rwb.getSheetAt(0);
        Row row = sheet.getRow(3);
        Cell cell = row.getCell(0);
        System.out.println(cell.getStringCellValue());
    }
    /**
     * 仅支持2007
     *
     * @param file
     * @throws IOException
     */
    private static List<ExcelInfo> readExcel2007(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        Workbook rwb = new XSSFWorkbook(is);
        Sheet sheet = rwb.getSheetAt(0);
        sheet.removeRow(sheet.getRow(0));
        Iterator<Row> iterator = sheet.rowIterator();
        List<ExcelInfo> excelInfoList = new ArrayList<>();
        while (iterator.hasNext()){
            Row row = iterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            int i = 0;
            ExcelInfo excelInfo = new ExcelInfo();
            boolean flag = true;
            while (cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                if(i == 0){
                    //System.out.println(cell.getNumericCellValue());
                    excelInfo.setNovelId(new Double(cell.getNumericCellValue()).intValue());
                }
                if(i == 1){
                    excelInfo.setUserId(new Double(cell.getNumericCellValue()).intValue());
                    //System.out.println(new Double(cell.getNumericCellValue()).intValue());
                }
                if(i == 2){
                    String cname = cell.getStringCellValue();
                    int start = cname.indexOf("《")+1;
                    int end = cname.indexOf("》");
                    if(start < 0 || end < 0){
                        flag = false;
                        break;
                    }
                    cname = cname.substring(start, end);
                    excelInfo.setContent(cname);
                    //System.out.println(cell.getStringCellValue());
                    //excelInfo.setContent(cell.getStringCellValue());
                }
                if(i == 3){
                    String subject = cell.getStringCellValue();
                    if(subject.indexOf("上架") >= 0){
                        subject = "1";
                    }
                    else if(subject.indexOf("书籍通过") >= 0){
                        subject = "4";
                    }
                    else if(subject.indexOf("书籍没有通过") >= 0){
                        subject = "3";
                    }
                    else if(subject.indexOf("作品创建通过") >= 0){
                        subject = "4";
                    }
                    else if(subject.indexOf("作品创建不通过") >= 0){
                        subject = "3";
                    }
                    else if(subject.indexOf("作者签约通过") >= 0){
                        subject = "6";
                    }
                    else{
                        subject = "2";
                    }

                    excelInfo.setSubject(subject);
                    //System.out.println(cell.getStringCellValue());
                }
                i++;
            }
            if(flag){
                excelInfoList.add(excelInfo);
                //System.out.println(excelInfo.toString());
            }
        }
        return excelInfoList;
    }

    /**
     * 支持2003/2007
     *
     * @param file
     * @throws Exception
     */
    private static void readExcel(File file) throws Exception {
        InputStream is = new FileInputStream(file);
        Workbook rwb = WorkbookFactory.create(is);
        Sheet sheet = rwb.getSheetAt(0);
        Row row = sheet.getRow(3);
        Cell cell = row.getCell(0);
        System.out.println(cell.getStringCellValue());
    }

    /**
     * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
     *
     * @param file       读取数据的源Excel
     * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
     * @return 读出的Excel中数据的内容
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String[][] getData(File file, int ignoreRows)
            throws FileNotFoundException, IOException {
        List<String[]> result = new ArrayList<String[]>();
        int rowSize = 0;
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                file));
        // 打开HSSFWorkbook
        POIFSFileSystem fs = new POIFSFileSystem(in);
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFCell cell = null;
        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
            HSSFSheet st = wb.getSheetAt(sheetIndex);
            // 第一行为标题，不取
            for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
                HSSFRow row = st.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                int tempRowSize = row.getLastCellNum() + 1;
                if (tempRowSize > rowSize) {
                    rowSize = tempRowSize;
                }
                String[] values = new String[rowSize];
                Arrays.fill(values, "");
                boolean hasValue = false;
                for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
                    String value = "";
                    cell = row.getCell(columnIndex);
                    if (cell != null) {
                        // 注意：一定要设成这个，否则可能会出现乱码,后面版本默认设置
                        //cell.setEncoding(HSSFCell.ENCODING_UTF_16);
                        switch (cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_STRING:
                                value = cell.getStringCellValue();
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    Date date = cell.getDateCellValue();
                                    if (date != null) {
                                        value = new SimpleDateFormat("yyyy-MM-dd")
                                                .format(date);
                                    } else {
                                        value = "";
                                    }
                                } else {
                                    value = new DecimalFormat("0").format(cell

                                            .getNumericCellValue());
                                }
                                break;
                            case HSSFCell.CELL_TYPE_FORMULA:
                                // 导入时如果为公式生成的数据则无值
                                if (!cell.getStringCellValue().equals("")) {
                                    value = cell.getStringCellValue();
                                } else {
                                    value = cell.getNumericCellValue() + "";
                                }
                                break;
                            case HSSFCell.CELL_TYPE_BLANK:
                                break;
                            case HSSFCell.CELL_TYPE_ERROR:
                                value = "";
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN:
                                value = (cell.getBooleanCellValue() == true ? "Y"

                                        : "N");
                                break;
                            default:
                                value = "";
                        }
                    }
                    if (columnIndex == 0 && value.trim().equals("")) {
                        break;
                    }
                    values[columnIndex] = rightTrim(value);
                    hasValue = true;
                }
                if (hasValue) {
                    result.add(values);
                }
            }
        }
        in.close();
        String[][] returnArray = new String[result.size()][rowSize];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = (String[]) result.get(i);
        }
        return returnArray;
    }


    /**
     * 去掉字符串右边的空格
     *
     * @param str 要处理的字符串
     * @return 处理后的字符串
     */

    public static String rightTrim(String str) {
        if (str == null) {
            return "";
        }
        int length = str.length();
        for (int i = length - 1; i >= 0; i--) {
            if (str.charAt(i) != 0x20) {
                break;
            }
            length--;
        }
        return str.substring(0, length);
    }
}