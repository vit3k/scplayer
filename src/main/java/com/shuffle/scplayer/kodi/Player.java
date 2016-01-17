package com.shuffle.scplayer.kodi;

/**
 * Created by pwitkows on 1/12/2016.
 */
public class Player {
    int playerid;
    String type;

    public Player(int playerid, String type) {
        this.playerid = playerid;
        this.type = type;
    }

    public int getPlayerid() {
        return playerid;
    }

    public String getType() {
        return type;
    }
}
