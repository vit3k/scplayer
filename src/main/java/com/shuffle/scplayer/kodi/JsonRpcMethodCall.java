package com.shuffle.scplayer.kodi;

import java.util.HashMap;

/**
 * Created by pwitkows on 1/13/2016.
 */
public class JsonRpcMethodCall {
    private String jsonrpc = "2.0";
    private int id;
    private String method;
    private HashMap<String, Object> params;

    public JsonRpcMethodCall(int id, String method, HashMap<String, Object> params) {
        this.id = id;
        this.method = method;
        this.params = params;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public HashMap<String, Object> getParams() {
        return params;
    }

    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }
}
