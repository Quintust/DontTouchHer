package com.quintus.tang.donttouchher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by de165 on 2017/6/7 07.
 */

public class Game extends View {

    public WindowManager manager;
    public int width = 0;
    public int height = 0;
    GameActivity game;
    List<Block> blocks = new ArrayList<>();
    private int isOver = 0;
    private int score = 0;
    private boolean flag = false;
    private long delay = 2000;
    private int location = -1;
    private int touchCount = 0;
    public Game(GameActivity activity) {
        super(activity);
        this.manager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        this.game = activity;
        initParams();
        initBitmap();
    }

    private void initParams(){
        score = 0;
        isOver = 0;
        location = -1;
        touchCount = 0;
        width = manager.getDefaultDisplay().getWidth();
        height = manager.getDefaultDisplay().getHeight();
    }

    public void initBitmap(){

        if(blocks==null)
            blocks = ConstantUtil.getAllBlocks(this,height,width);
        else {
            blocks.clear();
            blocks = ConstantUtil.getAllBlocks(this,height,width);
        }

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(0,0);
        canvas.scale(1.0f,1.0f);
        initBlocks(canvas,blocks);
        Log.i("-----刷新画布-->", "onDraw: ");
    }

    public void initBlocks(Canvas canvas,List<Block> blocks){
        //初始化位置数组
        int[] X = { 0, (width/2) + 1, 0, (width/2) + 1,0, (width/2) + 1};
        int[] Y = { 0, 0, (height/3)+1,(height/3)+1, 2*(height/3)+1,2*(height/3)+1};

        Paint paint = new Paint();
        canvas.drawColor(getResources().getColor(R.color.white));
        for (int i = 0;i<blocks.size();i++) {
            canvas.drawBitmap(blocks.get(i).getBlock(),X[i],Y[i],paint);
        }
        paint.setColor(Color.RED);
        paint.setTextSize(72);
        String sc = "分数："+score;
        canvas.drawText(sc,width/2 - width/8,height/3-height/4,paint);
    }
    private void newLine(int loc){
        List<Block> kinds = ConstantUtil.getABlock(this,height,width);
        if(loc == 0) {
            blocks.remove(1);
            blocks.add(1,kinds.get(0));
        }
        if(loc == 1) {
            blocks.remove(0);
            blocks.add(0,kinds.get(0));
        }
        blocks.remove(loc);
        blocks.add(loc,kinds.get(1));
        invalidate();
    }

    private void moveDown(){
        int loc = new Random().nextInt(2);

        blocks.remove(4);
        blocks.add(4,blocks.get(2));
        blocks.remove(5);
        blocks.add(5,blocks.get(3));

        blocks.remove(2);
        blocks.add(2,blocks.get(0));
        blocks.remove(3);
        blocks.add(3,blocks.get(1));
        newLine(loc);
        score++;
    }

    private int notHer(float x, float y){
        int over = -1;
        if(y<height/3 && y>0){
            if(blocks.get(4).getColor() == 0 || blocks.get(5).getColor() == 0){
                over = 1;
            }
            if(blocks.get(2).getColor() == 0 || blocks.get(3).getColor() == 0){
                over = 1;
            }
            //左
            if(x<width/2) {
                if (blocks.get(0).getColor() == 0) {
                    moveDown();
                }else{
                    over = 1;
                }
            }
            //右
            if(x>width/2){
                if(blocks.get(1).getColor() == 0) {
                    moveDown();
                }else{
                    over = 1;
                }
            }
        }
        if(y>height/3 && y<2*(height/3)){
            if(blocks.get(4).getColor() == 0 || blocks.get(5).getColor() == 0){
                over = 1;
            }
            //左
            if(x<width/2) {
                if (blocks.get(2).getColor() == 0) {
                    moveDown();
                }else{
                    over = 1;
                }
            }
            //右
            if(x>width/2){
                if(blocks.get(3).getColor() == 0) {
                    moveDown();
                }else{
                    over = 1;
                }
            }
            location = 2;
        }
        if(y>2*(height/3)){
            location = 1;
            //左
            if(x<width/2) {
                if (blocks.get(4).getColor() == 0) {
                    moveDown();
                }else{
                    over = 1;
                }
            }
            //右
            if(x>width/2){
                if(blocks.get(5).getColor() == 0) {
                    moveDown();
                }else{
                    over = 1;
                }
            }
        }
        return over;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.dispatchGenericMotionEvent(event);
        if (event.getAction()== MotionEvent.ACTION_DOWN) {
            final float x = event.getX();
            final float y = event.getY();
            isOver = notHer(x, y);
            Log.i("Position---->", "X:" + x + " Y:" + y);
            flag = true;
            touchCount ++;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.what = isOver;
                    handler.sendMessage(msg);
                }
            });
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            thread.start();
        }
        return super.onTouchEvent(event);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    break;
                case 1:
                    initParams();
                    initBitmap();
                    game.setRestart();
                    invalidate();
                    break;
                default:
                    break;
            }
        }
    };
}
