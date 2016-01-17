package com.shuffle.scplayer.kodi;

/**
 * Created by pwitkows on 1/12/2016.
 */
public class Player {
    int id;
    String type;

    public Player(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
