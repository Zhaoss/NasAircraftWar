package com.zhaoshuang.nasaircraftwar.aircraft;

import android.graphics.Bitmap;

/**
 * Created by zhaoshuang on 16/8/12.
 * 所有单位的父
 */
public class BaseCompany {

    private Bitmap mBitmap;
    private float x;
    private float y;
    private boolean have = true;
    private int hp;

    public Bitmap getBitmap(){
        return mBitmap;
    }

    public void setBitmap(Bitmap mBitmap){
        this.mBitmap = mBitmap;
    }

    public int getWidth(){
        if(mBitmap != null) return mBitmap.getWidth();
        return 0;
    }

    public int getHeight(){
        if(mBitmap != null) return mBitmap.getHeight();
        return 0;
    }

    public void destroy(){
        mBitmap.recycle();
        mBitmap = null;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void notHave(){
        have = false;
    }

    public boolean isHave(){
        return have;
    }

    public void setHp(int hp){
        this.hp = hp;
    }

    public int getHp(){
        return hp;
    }

    public void resetHave(){
        have = true;
    }
}
