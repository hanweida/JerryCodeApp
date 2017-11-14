package com.jerry.util;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Class Name FileUtil
 * @Author zhenyu
 * @Create In 2012-3-27
 */
public class FileUtil {

    /**
     * 删除某个文件夹下的所有文件夹和文件
     *
     * @param delpath String
     * @return boolean
     * @throws FileNotFoundException
     * @throws IOException
     * @Create In 2012-4-4 By zhenyu
     */
    public static boolean deletefile(String delpath)
            throws FileNotFoundException, IOException {
        try {
            File file = new File(delpath);
            if (!file.isDirectory()) {
                System.out.println("1");
                file.delete();
            } else if (file.isDirectory()) {
                System.out.println("2");
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File delfile = new File(delpath + "" + filelist[i]);
                    if (!delfile.isDirectory()) {
                        System.out.println("path=" + delfile.getPath());
                        System.out.println("absolutepath="
                                + delfile.getAbsolutePath());
                        System.out.println("name=" + delfile.getName());
                        delfile.delete();
                        System.out.println("删除文件成功");
                    } else if (delfile.isDirectory()) {
                        deletefile(delpath + "" + filelist[i]);
                    }
                }
                file.delete();
            }
        } catch (FileNotFoundException e) {
            System.out.println("deletefile() Exception:" + e.getMessage());
        }
        return true;
    }

    /**
     * 读取某个文件夹下的所有文件夹的文件, 返回所有文件名
     *
     * @param filepath String
     * @return Map<Integer, String> pathMap
     * @throws FileNotFoundException
     * @throws IOException
     * @Create In 2012-4-4 By zhenyu
     */
    public static Map<Integer, String> readFileName(String filepath,
                                                    Map<Integer, String> pathMap) throws Exception {
        if (pathMap == null) {
            pathMap = new HashMap<Integer, String>();
        }
        File file = new File(filepath);
        // 文件
        if (!file.isDirectory()) {
            pathMap.put(pathMap.size(), file.getPath());
        } else if (file.isDirectory()) { // 如果是目录， 遍历所有子目录取出所有文件名
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                File readfile = new File(filepath + "/" + filelist[i]);
                if (!readfile.isDirectory()) {
                    pathMap.put(pathMap.size(), readfile.getPath());
                } else if (readfile.isDirectory()) { // 子目录的目录
                    readFileName(filepath + "/" + filelist[i], pathMap);
                }
            }
        }
        return pathMap;
    }

    /**
     * 读取某个文件夹下的所有文件夹的文件, 返回所有文件名
     *
     * @param filepath
     * @return
     * @throws Exception List<String>
     * @Methods Name readFileName
     * @Create In 2012-4-4 By zhenyu
     */
    public static ArrayList<String> readFileName(String filepath, ArrayList<String> fileList) throws Exception {
        if (filepath != null) {
            File file = new File(filepath);
            if (!file.isDirectory()) {// 文件
                fileList.add(file.getPath());
            } else if (file.isDirectory()) { // 如果是目录， 遍历所有子目录取出所有文件名
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    File readfile = new File(filepath + "/" + filelist[i]);
                    if (!readfile.isDirectory()) {
                        fileList.add(readfile.getPath());
                    } else if (readfile.isDirectory()) { // 子目录的目录
                        readFileName(filepath + "/" + filelist[i], fileList);
                    }
                }
            }
        }
        return fileList;
    }

    /**
     * 读文件
     *
     * @param filePath
     * @return
     * @throws Exception String
     * @Methods Name readFile
     * @Create In 2012-4-4 By zhenyu
     */
    public static String readFile(String filePath) throws Exception {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(filePath);
            return IOUtils.toString(stream);
        } finally {
            close(stream);
        }
    }


    /**
     * 读文件
     *
     * @param filePath
     * @return
     * @throws Exception ArrayList<String>
     * @Methods Name readFile2List
     * @Create In 2012-4-4 By zhenyu
     */
    public static ArrayList<String> readFile4List(String filePath) throws Exception {
        ArrayList<String> list = new ArrayList<String>();
        String data = "";
        BufferedReader sr = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        while ((data = sr.readLine()) != null) {
            list.add(data);
        }
        sr.close();
        return list;
    }


    /**
     * 写文件
     *
     * @param list
     * @param filePath
     * @throws Exception void
     * @Methods Name writeFile
     * @Create In 2012-4-4 By zhenyu
     */
    public static void writeFile(ArrayList<String> list, String filePath) throws Exception {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
            if (list != null && list.size() > 0) {
                int i = 0;
                for (String content : list) {
                    bw.write(content);
                    if (i != (list.size() - 1)) {
                        bw.write("\n");
                    }
                    i++;
                }
                bw.flush();
            }
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }


    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                //
            }
        }
    }


    /**
     * 写文件
     *
     * @param content
     * @param filePath
     * @throws Exception void
     * @Methods Name writeFile
     * @Create In 2012-4-4 By zhenyu
     */
    public static void writeFile(String content, String filePath) throws Exception {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
            bw.write(content);
            bw.flush();
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    /**
     * 追加写文化
     *
     * @param content
     * @param filePath
     * @throws Exception void
     * @Methods Name writeFileAppend
     * @Create In Jul 4, 2012 By zhenyu
     */
    public static void writeFileAppend(String content, String filePath) throws Exception {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true)));
            bw.write(content);
            bw.flush();
        } finally {
            if (bw != null) {
                bw.close();
            }
        }

    }

    /**
     * 字符串截取
     *
     * @param line
     * @param beginFlag
     * @param endFlag
     * @return String
     * @Methods Name lineAnalise
     * @Create In Apr 26, 2012 By zhenyu
     */
    public static String lineAnalise(String line, String beginFlag, String endFlag) {
        String tmp = line.substring(line.indexOf(beginFlag) + beginFlag.length(), line.indexOf(endFlag)).trim();
        return tmp;
    }

    /**
     * 字符串截取,以开始标志为开始位置，以第一个空格为结束地址
     *
     * @param line
     * @param beginFlag
     * @return String
     * @Methods Name lineAnalise
     * @Create In Apr 26, 2012 By zhenyu
     */
    public static int lineAnalise2Int(String line, String beginFlag) {
        String tmp = line.substring(line.indexOf(beginFlag) + beginFlag.length(), line.indexOf(" ", line.indexOf(beginFlag) + beginFlag.length()));
        return Integer.valueOf(tmp);
    }

    public static String lineAnalise2String(String line, String beginFlag) {
        String tmp = line.substring(line.indexOf(beginFlag) + beginFlag.length(), line.indexOf(" ", line.indexOf(beginFlag) + beginFlag.length()));
        return tmp;
    }

    public static boolean startsWithDigit(String s) {
        return Pattern.compile("^[0-9]_").matcher(s).find();
    }

    public static void main(String[] args) throws Exception {
    }
}