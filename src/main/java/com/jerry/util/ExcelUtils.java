package com.jerry.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    /**
     * 读取97-2003格式
     *
     * @param filePath 文件路径
     * @throws IOException
     */
    public static List<Map> readExcel2003(String filePath,String sheetName) throws IOException {
        //返回结果集
        List<Map> valueList = new ArrayList<Map>();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            HSSFWorkbook wookbook = new HSSFWorkbook(fis);    // 创建对Excel工作簿文件的引用
            HSSFSheet sheet = wookbook.getSheet(sheetName);    // 按名读取sheet
            int rows = sheet.getPhysicalNumberOfRows();    // 获取到Excel文件中的所有行数?
            Map<Integer, String> keys = new HashMap<Integer, String>();
            int cells = 0;
            // 遍历行?（第1行  表头） 准备Map里的key
            HSSFRow firstRow = sheet.getRow(0);
            if (firstRow != null) {
                // 获取到Excel文件中的所有的列
                cells = firstRow.getPhysicalNumberOfCells();
                // 遍历列
                for (int j = 0; j < cells; j++) {
                    // 获取到列的值?
                    try {
                        HSSFCell cell = firstRow.getCell(j);
                        String cellValue = getCellValue(cell);
                        keys.put(j, cellValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // 遍历行?（从第二行开始）
            for (int i = 1; i < rows; i++) {
                // 读取左上端单元格(从第二行开始)
                HSSFRow row = sheet.getRow(i);
                // 行不为空
                if (row != null) {
                    //准备当前行 所储存值的map
                    Map<String, Object> val = new HashMap<String, Object>();
                    boolean isValidRow = false;
                    // 遍历列
                    for (int j = 0; j < cells; j++) {
                        // 获取到列的值?
                        try {
                            HSSFCell cell = row.getCell(j);
                            String cellValue = getCellValue(cell);
                            val.put(keys.get(j), cellValue);
                            if (!isValidRow && cellValue != null && cellValue.trim().length() > 0) {
                                isValidRow = true;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //第I行所有的列数据读取完毕，放入valuelist
                    if (isValidRow) {
                        valueList.add(val);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fis.close();
        }
        return valueList;
    }

    /**
     * 本地读取2007-2013格式
     *
     * @return
     * @throws IOException
     */
    public static List<Map> readExcel2007(String filePath, String sheetName) throws IOException {
        DecimalFormat df = new DecimalFormat("0.0000");
        List<Map> valueList = new ArrayList<Map>();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            XSSFWorkbook xwb = new XSSFWorkbook(fis);    // 构造 XSSFWorkbook 对象，strPath 传入文件路径
            XSSFSheet sheet = xwb.getSheet(sheetName);            // 按名读取sheet
            // 定义 row、cell
            XSSFRow row;
            // 循环输出表格中的第一行内容   表头
            Map<Integer, String> keys = new HashMap<Integer, String>();
            row = sheet.getRow(0);
            if (row != null) {
                //System.out.println("j = row.getFirstCellNum()::"+row.getFirstCellNum());
                //System.out.println("row.getPhysicalNumberOfCells()::"+row.getPhysicalNumberOfCells());
                for (int j = row.getFirstCellNum(); j <= row.getPhysicalNumberOfCells(); j++) {
                    // 通过 row.getCell(j).toString() 获取单元格内容，
                    if (row.getCell(j) != null) {
                        if (!row.getCell(j).toString().isEmpty()) {
                            keys.put(j, row.getCell(j).toString());
                        }
                    } else {
                        keys.put(j, "K-R1C" + j + "E");
                    }
                }
            }
            // 循环输出表格中的从第二行开始内容
            for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    boolean isValidRow = false;
                    Map<String, Object> val = new HashMap<String, Object>();
                    for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                        XSSFCell cell = row.getCell(j);
                        if (cell != null) {
                            String cellValue = null;
                            if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    cellValue = new DataFormatter().formatRawCellContents(cell.getNumericCellValue(), 0, "yyyy-MM-dd HH:mm:ss");
                                } else {
                                    BigDecimal bd = new BigDecimal(cell.getNumericCellValue());
                                    cellValue = bd.toString();
                                    //小数保留四位
                                    if (cellValue.contains(".")) {
                                        cellValue = df.format(bd);
                                    }
//                                    System.out.println(cellValue);
//                                    if (String.valueOf(cell.getNumericCellValue()).contains(".")) {
//                                        cellValue = String.valueOf(cell.getNumericCellValue());
//                                    } else {
//                                        DecimalFormat df = new DecimalFormat("0");
//                                        cellValue = df.format(cell.getNumericCellValue());
//                                    }
                                }
                            } else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING ) {
                                cellValue = cell.getStringCellValue().trim();
                            } else if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA ) {
                                cellValue = df.format(cell.getNumericCellValue());
                            } else {
                                cellValue = cell.toString();
                            }

                            if (cellValue != null && cellValue.trim().length() <= 0) {
                                cellValue = null;
                            }
                            if (keys.get(j) != null) {
                                val.put(keys.get(j), cellValue);
                            }
                            if (!isValidRow && cellValue != null && cellValue.trim().length() > 0) {
                                isValidRow = true;
                            }
                        }
                    }

                    // 第I行所有的列数据读取完毕，放入valuelist
                    if (isValidRow) {
                        valueList.add(val);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return valueList;
    }


    /**
     * 读取2007-2013格式
     *
     * @return
     * @throws IOException
     */
    public static List<Map> readExcel2007(CommonsMultipartFile file, String sheetName) throws IOException {
        DecimalFormat df = new DecimalFormat("0.0000");
        List<Map> valueList = new ArrayList<Map>();
        try {
            XSSFWorkbook xwb = new XSSFWorkbook(file.getInputStream());    // 构造 XSSFWorkbook 对象，strPath 传入文件路径
            XSSFSheet sheet = xwb.getSheet(sheetName);            // 按名读取sheet
            // 定义 row、cell
            XSSFRow row;
            // 循环输出表格中的第一行内容   表头
            Map<Integer, String> keys = new HashMap<Integer, String>();
            row = sheet.getRow(0);
            if (row != null) {
                //System.out.println("j = row.getFirstCellNum()::"+row.getFirstCellNum());
                //System.out.println("row.getPhysicalNumberOfCells()::"+row.getPhysicalNumberOfCells());
                for (int j = row.getFirstCellNum(); j <= row.getPhysicalNumberOfCells(); j++) {
                    // 通过 row.getCell(j).toString() 获取单元格内容，
                    if (row.getCell(j) != null) {
                        if (!row.getCell(j).toString().isEmpty()) {
                            keys.put(j, row.getCell(j).toString());
                        }
                    } else {
                        keys.put(j, "K-R1C" + j + "E");
                    }
                }
            }
            // 循环输出表格中的从第二行开始内容
            for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    boolean isValidRow = false;
                    Map<String, Object> val = new HashMap<String, Object>();
                    for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                        XSSFCell cell = row.getCell(j);
                        if (cell != null) {
                            String cellValue = null;
                            if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    cellValue = new DataFormatter().formatRawCellContents(cell.getNumericCellValue(), 0, "yyyy-MM-dd HH:mm:ss");
                                } else {
                                    BigDecimal bd = new BigDecimal(cell.getNumericCellValue());
                                    cellValue = bd.toString();
                                    //小数保留四位
                                    if (cellValue.contains(".")) {
                                        cellValue = df.format(bd);
                                    }
//                                    System.out.println(cellValue);
//                                    if (String.valueOf(cell.getNumericCellValue()).contains(".")) {
//                                        cellValue = String.valueOf(cell.getNumericCellValue());
//                                    } else {
//                                        DecimalFormat df = new DecimalFormat("0");
//                                        cellValue = df.format(cell.getNumericCellValue());
//                                    }
                                }
                            } else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING ) {
                                cellValue = cell.getStringCellValue().trim();
                            } else if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA ) {
                                cellValue = df.format(cell.getNumericCellValue());
                            } else {
                                cellValue = cell.toString();
                            }

                            if (cellValue != null && cellValue.trim().length() <= 0) {
                                cellValue = null;
                            }
                            if (keys.get(j) != null) {
                                val.put(keys.get(j), cellValue);
                            }
                            if (!isValidRow && cellValue != null && cellValue.trim().length() > 0) {
                                isValidRow = true;
                            }
                        }
                    }

                    // 第I行所有的列数据读取完毕，放入valuelist
                    if (isValidRow) {
                        valueList.add(val);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return valueList;
    }

    /**
     * 文件操作 获取文件扩展名
     *
     * @param filename 文件名称包含扩展名
     * @return
     * @Author: sunny
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    private static String getCellValue(HSSFCell cell) {
        DecimalFormat df = new DecimalFormat("0.0000");
        String cellValue = null;
        if (cell != null) {
            if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = new DataFormatter().formatRawCellContents(cell.getNumericCellValue(), 0, "yyyy-MM-dd HH:mm:ss");
                } else {
                    BigDecimal bd = new BigDecimal(cell.getNumericCellValue());
                    cellValue = bd.toString();
                    //小数保留两位
                    if (cellValue.contains(".")) {
                        cellValue = df.format(bd);
                    }
//                                    System.out.println(cellValue);
//                                    if (String.valueOf(cell.getNumericCellValue()).contains(".")) {
//                                        cellValue = String.valueOf(cell.getNumericCellValue());
//                                    } else {
//                                        DecimalFormat df = new DecimalFormat("0");
//                                        cellValue = df.format(cell.getNumericCellValue());
//                                    }
                }
            } else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                cellValue = cell.getStringCellValue().trim();
            } else if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
                cellValue = df.format(cell.getNumericCellValue());
            } else {
                cellValue = cell.toString();
            }

            if (cellValue != null && cellValue.trim().length() <= 0) {
                cellValue = null;
            }
        }
        return cellValue;
    }
}
