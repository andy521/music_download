package com.jwind.netease;

/**
 * Created by IntelliJ IDEA.
 * User: J.wind
 * Date: 2018/7/25
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Api {
    private final static String BaseURL = "http://music.163.com/";

    /**
     * 获取用户歌单
     *
     * @param uid
     * @return
     */
    public static UrlParamPair getPlaylistOfUser(String uid) {
        UrlParamPair upp = new UrlParamPair();
        upp.setUrl(BaseURL + "weapi/user/playlist?csrf_token=");
        upp.addPara("offset", 0);
        upp.addPara("uid", uid);
        upp.addPara("limit", 5);
        upp.addPara("csrf_token", "nothing");
        return upp;
    }

    /**
     * 获取歌单详情
     *
     * @param playlist_id
     * @return
     */
    public static UrlParamPair getDetailOfPlaylist(String playlist_id) {
        UrlParamPair upp = new UrlParamPair();
        upp.setUrl(BaseURL + "weapi/v3/playlist/detail?csrf_token=");
        upp.addPara("id", playlist_id);
        upp.addPara("offset", 0);
        upp.addPara("total", "True");
        upp.addPara("limit", 1000);
        upp.addPara("n", 1000);
        upp.addPara("csrf_token", "nothing");
        return upp;
    }
    //todo:analyse more api
    //https://github.com/darknessomi/musicbox/wiki/%E7%BD%91%E6%98%93%E4%BA%91%E9%9F%B3%E4%B9%90%E6%96%B0%E7%89%88WebAPI%E5%88%86%E6%9E%90%E3%80%82


    /**
     * 获取歌曲URL：
     * @param ids
     * @return
     */
    public static UrlParamPair getMusicURL(String[] ids) {
        UrlParamPair upp = new UrlParamPair();
        upp.setUrl(BaseURL + "weapi/song/enhance/player/url?csrf_token=");
        upp.addPara("ids", ids);
        upp.addPara("br", 128000);
        upp.addPara("csrf_token", "nothing");
        return upp;
    }
}