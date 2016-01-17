package com.shuffle.scplayer.kodi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.websocket.*;
import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

@ClientEndpoint
public class KodiWebSocketClient {
    private static final transient Log log = LogFactory.getLog(KodiWebSocketClient.class);
    private static final Gson gson = new GsonBuilder().create();
    Session session;

    int lastMessageId = 1;
    public KodiWebSocketClient() {
        try {
            WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
            webSocketContainer.connectToServer(this, new URI("ws://localhost:9090/jsonrpc"));


        } catch(Exception e) {
            log.warn("Can't connect to Kodi: "+e.getMessage());
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        log.debug("Kodi session opened");
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        this.session = null;
        log.debug("Kodi session closed");
    }
    ConcurrentMap<Integer, Consumer<JsonObject>> callMap = new ConcurrentHashMap<>();

    @OnMessage
    public void onMessage(String message) {
        log.debug("Kodi received message: "+message);
        JsonParser parser = new JsonParser();
        JsonObject responseObj = parser.parse(message).getAsJsonObject();
        int id = responseObj.get("id").getAsInt();
        //responseObj.get("result").getas
        Consumer<JsonObject> callback = callMap.get(id);
        callback.accept(responseObj);
    }

    public <T> void callMethod(Consumer<T> callback, Class<T> type, String method, Object... params) {
        int id = getMessageId();
        JsonObject rpcMethodCall = new JsonObject();
        rpcMethodCall.addProperty("jsonrpc","2.0");
        rpcMethodCall.addProperty("id", id);
        rpcMethodCall.addProperty("method", method);
        rpcMethodCall.addProperty("params", gson.toJson(params));
        String message = gson.toJson(rpcMethodCall);
        session.getAsyncRemote().sendText(message);

        callMap.put(id, reponseObj -> {
            //T result =
        });
        log.debug("Kodi sent message: "+message);
    }


    public Player[] getActivePlayers() {
        return null;
    }
    private synchronized Integer getMessageId() {
        return ++lastMessageId;
    }
}
