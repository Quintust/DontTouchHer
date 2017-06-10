package com.quintus.tang.donttouchher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by de165 on 2017/6/7 0007.
 */

public class ConstantUtil {
    /*
    public static WindowManager manager;
    public static int width = ConstantUtil.manager.getDefaultDisplay().getWidth();
    public static int height = ConstantUtil.manager.getDefaultDisplay().getHeight();

    public int X_1 = 0;
    public int Y_1 = 0;

    public int X_2 = (ConstantUtil.width/2) + 1;
    public int Y_2 = 0;

    public int X_3 = 0;
    public int Y_3 =(ConstantUtil.height/2)+1;

    public int X_4 = (ConstantUtil.width/2) + 1;
    public int Y_4 = (ConstantUtil.height/2)+1;
    */

    public static List<Bitmap> getBlocks(View v, int height, int width){
        List<Bitmap> list = new ArrayList<Bitmap>();
        Bitmap block_white_yes = ConstantUtil.calculateInSampleSize( BitmapFactory.decodeResource(v.getResources(), R.drawable.block_white_yes),width/2,height/2 );
        Bitmap block_white_no = ConstantUtil.calculateInSampleSize( BitmapFactory.decodeResource(v.getResources(),R.drawable.block_white_no),width/2,height/2 );
        Bitmap block_black_yes = ConstantUtil.calculateInSampleSize( BitmapFactory.decodeResource(v.getResources(),R.drawable.block_black_yes),width/2,height/2 );
        Bitmap block_black_no = ConstantUtil.calculateInSampleSize( BitmapFactory.decodeResource(v.getResources(),R.drawable.block_black_no),width/2,height/2 );

        list.add(block_white_yes);
        list.add(block_white_no);
        list.add(block_black_yes);
        list.add(block_black_no);
        return list;
    }

    public static List<Block> getAllBlocks(View v, int height, int width){
        List<Block> list = new ArrayList<Block>();
        Block block_white = new Block(1,
                calculateInSampleSize( BitmapFactory.decodeResource(v.getResources(), R.drawable.block_white),width/2,height/3 ));
        Block block_black = new Block(0,
                calculateInSampleSize( BitmapFactory.decodeResource(v.getResources(),R.drawable.block_black),width/2,height/3 ));
        Block block_black1 = new Block(0,
                calculateInSampleSize( BitmapFactory.decodeResource(v.getResources(), R.drawable.block_black),width/2,height/3 ));
        Block block_white1 = new Block(1,
                calculateInSampleSize( BitmapFactory.decodeResource(v.getResources(),R.drawable.block_white),width/2,height/3 ));

        Block block_white2 = new Block(1,
                calculateInSampleSize( BitmapFactory.decodeResource(v.getResources(), R.drawable.block_white),width/2,height/3 ));
        Block block_black2 = new Block(0,
                calculateInSampleSize( BitmapFactory.decodeResource(v.getResources(),R.drawable.block_black),width/2,height/3 ));

        list.add(block_white);
        list.add(block_black);
        list.add(block_black1);
        list.add(block_white1);

        list.add(block_white2);
        list.add(block_black2);

        return list;
    }

    public static List<Block> getABlock(View v, int height, int width){
        List<Block> list = new ArrayList<Block>();
        Bitmap block_white = calculateInSampleSize( BitmapFactory.decodeResource(v.getResources(), R.drawable.block_white),width/2,height/2 );
        Bitmap block_black = calculateInSampleSize( BitmapFactory.decodeResource(v.getResources(),R.drawable.block_black),width/2,height/2 );
        Block w = new Block(1,block_white);
        Block b = new Block(0,block_black);
        list.add(0,w);
        list.add(1,b);
        return list;
    }

    public static Bitmap calculateInSampleSize(Bitmap bm, int reqWidth, int reqHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleW = ((float)reqWidth) / width;
        float scaleH = ((float)reqHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleW,scaleH);

        Bitmap newBm = Bitmap.createBitmap(bm,0,0,width,height,matrix,true);
        return newBm;
    }

}
