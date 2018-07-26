package com.jwind.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jwind.utils.FileReadAndWrite;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: J.wind
 * Date: 2018/7/25
 * Time: 4:45 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/api")
public class OptController {

    @GetMapping("/")
    public String test(HttpServletRequest request) {
        //逻辑处理
        request.setAttribute("key", "hello world");
        return "index";
    }


    /**
     * 获取用户歌单
     * @param request
     * @param uid
     * @return
     */
    @RequestMapping("/getListByMusicB")
    public String getListByMusicB(HttpServletRequest request,String uid){
        String user = StartSpider.getUserMusicList(uid);
        JSONObject json = JSONObject.parseObject(user);
        JSONArray playlist = json.getJSONArray("playlist");
        request.setAttribute("prods", playlist);
        return "music_list";
    }

    /**
     * 歌单的音乐详细列表
     * @param request
     * @param playId
     * @return
     */
    @RequestMapping("/getPlayListB")
    public String getPlayListB(HttpServletRequest request, String playId){
        String playList = StartSpider.getPlayList(playId);
        JSONObject jsonObject = JSONObject.parseObject(playList);
        JSONObject playLists = jsonObject.getJSONObject("playlist");
        JSONArray tracks = playLists.getJSONArray("tracks");
        request.setAttribute("prods", tracks);
        return "play_list";
    }

    /**
     * 下载某个音乐
     * @param request
     * @param name
     * @return
     */
    @RequestMapping("/downloadMusic")
    public String downloadMusic(HttpServletRequest request, String name){
        //第三步：写入文件列表
        String path = FileReadAndWrite.pwd();
        FileReadAndWrite.write(path + "/bin/music_list.txt", name);
        String result = FileReadAndWrite.python();
        request.setAttribute("result", result);
        return "result";
    }

}
