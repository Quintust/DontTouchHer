package com.quintus.tang.donttouchher;

import android.graphics.Bitmap;

/**
 * Created by de165 on 2017/6/8 0008.
 */

public class Block {
    private int color;
    private Bitmap block;

    public Block(int color, Bitmap bm){
        this.color=color;
        this.block = bm;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Bitmap getBlock() {
        return block;
    }

    public void setBlock(Bitmap block) {
        this.block = block;
    }
}
