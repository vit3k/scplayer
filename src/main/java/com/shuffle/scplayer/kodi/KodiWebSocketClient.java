package com.shuffle.scplayer.kodi;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.websocket.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
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
            webSocketContainer.connectToServer(this, new URI("ws://192.168.1.4:9090/jsonrpc"));


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
    ConcurrentMap<Integer, Consumer<JsonElement>> callMap = new ConcurrentHashMap<>();

    @OnMessage
    public void onMessage(String message) {
        try {
            log.debug("Kodi received message: " + message);
            JsonParser parser = new JsonParser();
            JsonObject responseObj = parser.parse(message).getAsJsonObject();
            int id = responseObj.get("id").getAsInt();
            Consumer<JsonElement> callback = callMap.get(id);
            JsonElement result = responseObj.get("result");
            callback.accept(result);
        }
        catch(Exception e) {
            log.error("OnMessage: "+e.getMessage());
        }
    }

    public <T> void callMethod(Consumer<T> callback, Type type, String method, HashMap<String, Object> params) {

        int id = getMessageId();
        JsonRpcMethodCall methodCall = new JsonRpcMethodCall(id, method, params);
        String message = gson.toJson(methodCall, JsonRpcMethodCall.class);
        session.getAsyncRemote().sendText(message);
        log.debug("Kodi sent message: "+message);
        callMap.put(id, responseElement -> {
            T result = gson.fromJson(responseElement, type);
            callback.accept(result);
        });
    }


    public void getActivePlayers(Consumer<Player[]> callback) {
        callMethod(callback, Player[].class, "Player.GetActivePlayers", null);
    }

    public void getProperties(int playerId, String[] properties, Consumer<HashMap<String, Object>> callback) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("playerid", playerId);
        params.put("properties", properties);

        callMethod(callback, new TypeToken<Map<String, Object>>(){}.getType(), "Player.GetProperties", params);
    }
    private synchronized Integer getMessageId() {
        return ++lastMessageId;
    }
}
