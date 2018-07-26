package com.jwind.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: J.wind
 * Date: 2018/7/25
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestUtils {
    public static void main(String[] args) {
        try {
            System.out.println(FileReadAndWrite.python());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
