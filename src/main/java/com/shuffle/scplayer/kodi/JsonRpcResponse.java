package com.shuffle.scplayer.kodi;

/**
 * Created by pwitkows on 1/17/2016.
 */
public class JsonRpcResponse {
    protected int id;
    protected String jsonrpc;

    public JsonRpcResponse(int id, String jsonrpc) {
        this.id = id;
        this.jsonrpc = jsonrpc;
    }

    public int getId() {
        return id;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }
}
