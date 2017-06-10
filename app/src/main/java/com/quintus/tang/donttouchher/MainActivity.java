package com.quintus.tang.donttouchher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by de165 on 2017/6/7 0007.
 */

public class MainActivity extends Activity {
    private View contentView;
    private Animation mAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = LayoutInflater.from(this).inflate(R.layout.welcome,null);
        mAnim = AnimationUtils.loadAnimation(this,R.anim.anim_logo);
        mAnim.setDuration(1000);
        contentView.setAnimation(mAnim);
        setContentView(contentView);
        delay(1000);
    }
    public void delay(int dur){
        final Intent it = new Intent(this,GameActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it);
                MainActivity.this.finish();
            }
        };
        timer.schedule(task,dur);
    }
}
