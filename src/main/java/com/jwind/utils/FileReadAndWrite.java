package com.jwind.utils;

import java.io.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: J.wind
 * Date: 2018/7/25
 * Time: 4:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileReadAndWrite {

    /**
     * 写入文件
     * @param fileName
     * @param content
     */
    public static void write(String fileName, String content) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileName, true)));
            out.write(content+"\r\n");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件的路径
     * @return
     */
    public static String pwd() {
        try {
            String[] cmd = new String[]{"/bin/sh", "-c", "pwd"};
            Process ps = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/home/api";
    }
    /**
     * 执行命令下载音乐
     * @return
     */
    public static String python() {
        try {
            String path = pwd();
            String filePath = path+"/bin/music_list.txt";
            Process ps = Runtime.getRuntime().exec("python3 "+path+"/bin/Netease.py " + filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/home/api";
    }
}
