package com.jwind.netease;

import com.alibaba.fastjson.JSONObject;
/**
 * Created by IntelliJ IDEA.
 * User: J.wind
 * Date: 2018/7/25
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class UrlParamPair {
    public String url;
    public JSONObject paras;

    public UrlParamPair() {
        this.paras = new JSONObject();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JSONObject getParas() {
        return paras;
    }

    public void setParas(JSONObject paras) {
        this.paras = paras;
    }

    public UrlParamPair addPara(String key, Object value) {
        this.paras.put(key, value.toString());
        return this;
    }

    public UrlParamPair(String url, JSONObject paras) {
        this.url = url;
        this.paras = paras;
    }
}