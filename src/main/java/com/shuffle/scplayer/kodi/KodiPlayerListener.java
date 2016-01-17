package com.shuffle.scplayer.kodi;

import com.shuffle.scplayer.core.PlayerListener;
import com.shuffle.scplayer.core.SpotifyConnectPlayer;
import com.shuffle.scplayer.core.Track;
import com.sun.scenario.effect.light.SpotLight;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by pwitkows on 1/11/2016.
 */
public class KodiPlayerListener implements PlayerListener {
    KodiWebSocketClient kodiWebSocketClient;

    public KodiPlayerListener(KodiWebSocketClient kodiWebSocketClient)  {
        this.kodiWebSocketClient = kodiWebSocketClient;
        //kodiWebSocketClient.sendMessage("{\"jsonrpc\": \"2.0\", \"method\": \"Player.GetProperties\", \"params\": { \"playerid\": 1, \"properties\":[\"speed\"] }, \"id\": 1}");
    }
    @Override
    public void onPlay() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onSeek(int millis) {

    }

    @Override
    public void onTrackChanged(Track track) {

    }

    @Override
    public void onNextTrack(Track track) {

    }

    @Override
    public void onPreviousTrack(Track track) {

    }

    @Override
    public void onShuffle(boolean enabled) {

    }

    @Override
    public void onRepeat(boolean enabled) {

    }

    @Override
    public void onActive() {

    }

    @Override
    public void onInactive() {

    }

    @Override
    public void onTokenLost() {

    }

    @Override
    public void onVolumeChanged(short volume) {

    }

    @Override
    public void onPlayerNameChanged(String playerName) {

    }
}
