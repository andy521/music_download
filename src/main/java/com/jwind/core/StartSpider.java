package com.jwind.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jwind.netease.Api;
import com.jwind.netease.UrlParamPair;
import com.jwind.secret.JSSecret;
import com.jwind.utils.FileReadAndWrite;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: J.wind
 * Date: 2018/7/25
 * Time: 3:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class StartSpider {
    //用户请求池
    private static String[] User_Agent = {
            "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
            "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
            "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0",
            "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729; InfoPath.3; rv:11.0) like Gecko",
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)",
            "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)",
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
            "Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1",
            "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.8.131 Version/11.11",
            "Opera/9.80 (Windows NT 6.1; U; en) Presto/2.8.131 Version/11.11",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon 2.0)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; TencentTraveler 4.0)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; The World)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SE 2.X MetaSr 1.0; SE 2.X MetaSr 1.0; .NET CLR 2.0.50727; SE 2.X MetaSr 1.0)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; 360SE)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Avant Browser)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"
    };


    public static void main(String[] args) {
        try {
            //第一步：根据用户id获取清单
            String uid = readDataFromConsole("请输入用户id：");
            String userMusicList = getUserMusicList(uid);
            JSONObject json = JSONObject.parseObject(userMusicList);
            JSONArray playlist = json.getJSONArray("playlist");
            System.out.println("   歌单ID：            歌单名称： ");
            for (Object play : playlist) {
                JSONObject ids = JSONObject.parseObject(play.toString());
                String id = ids.getString("id");
                String name = ids.getString("name");
                System.out.println(id + "    " + name);
            }
            //第二步：获取用户每个歌单的详情
            String playId = readDataFromConsole("请输入歌单ID：");
            String rest = getPlayList(playId);
            JSONObject jsonObject = JSONObject.parseObject(rest);
            JSONObject playLists = jsonObject.getJSONObject("playlist");
            JSONArray tracks = playLists.getJSONArray("tracks");
            List<String> nameList = new ArrayList<>();
            System.out.println("歌曲名称：");
            for (Object track : tracks) {
                JSONObject name = JSONObject.parseObject(track.toString());
                System.out.println(name.getString("name"));
                nameList.add(name.getString("name"));
            }
            //第三步：写入文件列表
            String musicName = readDataFromConsole("请输入歌曲名称：(all：下载全部)");
            if ("all".equals(musicName) || "ALL".equals(musicName)) {
                for (String name : nameList) {
                    String path = FileReadAndWrite.pwd();
                    FileReadAndWrite.write(path + "/bin/music_list.txt", name);
                }
            } else {
                String[] names = musicName.split(",");
                for (String name : names) {
                    String path = FileReadAndWrite.pwd();
                    FileReadAndWrite.write(path + "/bin/music_list.txt", name);
                }
            }
            //第四步：下载文件
            System.out.println(FileReadAndWrite.python());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据ID获取音乐列表
     *
     * @param uid
     * @return
     */
    public static String getUserMusicList(String uid) {
        try {
            UrlParamPair upp = Api.getPlaylistOfUser(uid);
            String req_str = upp.getParas().toJSONString();
            String userAgent = getUserAgent();
            Connection.Response
                    response = Jsoup.connect("http://music.163.com/weapi/user/playlist?csrf_token=")
                    .userAgent(userAgent)
                    .header("Accept", "*/*")
                    .header("Cache-Control", "no-cache")
                    .header("Connection", "keep-alive")
                    .header("Host", "music.163.com")
                    .header("Accept-Language", "zh-CN,en-US;q=0.7,en;q=0.3")
                    .header("DNT", "1")
                    .header("Pragma", "no-cache")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .data(JSSecret.getDatas(req_str))
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .timeout(10000)
                    .execute();
            String list = response.body();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取 每个歌单的歌曲列表
     *
     * @param commentThreadId
     * @return
     */
    public static String getPlayList(String commentThreadId) {
        try {
            UrlParamPair upp = Api.getDetailOfPlaylist(commentThreadId);
            String req_str = upp.getParas().toJSONString();
            String userAgent = getUserAgent();
            Connection.Response
                    response = Jsoup.connect("https://music.163.com/weapi/v3/playlist/detail?csrf_token=")
                    .userAgent(userAgent)
                    .header("Accept", "*/*")
                    .header("Cache-Control", "no-cache")
                    .header("Connection", "keep-alive")
                    .header("Host", "music.163.com")
                    .header("Accept-Language", "zh-CN,en-US;q=0.7,en;q=0.3")
                    .header("DNT", "1")
                    .header("Pragma", "no-cache")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .data(JSSecret.getDatas(req_str))
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .timeout(10000)
                    .execute();
            String list = response.body();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 随机获取一个请求头部
     *
     * @return
     */
    private static String getUserAgent() {
        //产生0-(arr.length-1)的整数值,也是数组的索引
        int index = (int) (Math.random() * User_Agent.length);
        String rand = User_Agent[index];
        return rand;
    }

    /**
     * Use  java.io.console to read data from console
     *
     * @param prompt
     * @return input string
     */
    private static String readDataFromConsole(String prompt) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        try {
            System.out.print(prompt);
            str = br.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
