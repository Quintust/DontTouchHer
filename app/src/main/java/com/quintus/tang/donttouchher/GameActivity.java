package com.quintus.tang.donttouchher;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import java.io.IOException;


/**
 * Created by de165 on 2017/6/7 0007.
 */

public class GameActivity extends Activity {
    private Game mgame;
    MediaPlayer mp;
    MediaPlayer mediaPlayer;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mgame = new Game(this);
        setContentView(R.layout.start_game);
    }

    public void setRestart(){
        if(mp!=null) {
            mp.stop();
            mp = null;
        }
        mediaPlayer = MediaPlayer.create(this,R.raw.over);
        mediaPlayer.setLooping(false);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            t.join();
            t.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mediaPlayer.stop();
        setContentView(R.layout.game_over);
    }
    public void startGame(View v){
        mp = MediaPlayer.create(this,R.raw.horizon);
        mp.setLooping(true);
        mp.start();
        if(mgame==null){
            mgame = new Game(this);
        }
        setContentView(mgame);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mp != null){
            mp.stop();
        }
        if(mediaPlayer != null){
            mediaPlayer.stop();
        }
    }
}
