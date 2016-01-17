package com.shuffle.scplayer.kodi;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by pawit on 17.01.2016.
 */
public class KodiTest {
    public static void main(String[] args) throws Exception {
        KodiWebSocketClient webSocketClient = new KodiWebSocketClient();
        CountDownLatch latch = new CountDownLatch(1);
        webSocketClient.getActivePlayers(players -> {
            if(players.length > 0) {

                webSocketClient.getProperties(players[0].getId(),new String[] {"speed"}, properties->{
                            System.out.println(properties.get("speed"));
                    latch.countDown();
                        }
                    );
            }

        });

        latch.await(10, TimeUnit.SECONDS);
    }
}
