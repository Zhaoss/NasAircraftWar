package com.zhaoshuang.nasaircraftwar.aircraft;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;

import com.zhaoshuang.nasaircraftwar.R;

/**
 * Created by zhaoshuang on 16/8/17.
 * 爆炸效果类
 */
public class Explosion extends BaseCompany {

    private int level = 0;
    private int frame = 1;
    private int width;
    private int speed = 1;

    public Explosion(Context context){

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.explosion);
        Matrix matrix = new Matrix();
        matrix.postScale(2f, 2f);
        setBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true));

        width = getWidth()/14;
    }

    public Rect getBitmapSrcRec() {

        Rect rect = new Rect();
        rect.left = width*level;
        rect.top = 0;
        rect.right = width*(level+1);
        rect.bottom = getHeight();

        frame++;
        if(frame%speed == 0) level++;

        return rect;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    public RectF getRectF(){
        float left = getX();
        float top = getY();
        float right = left + width;
        float bottom = top + getHeight();
        return new RectF(left, top, right, bottom);
    }

    public int getLevel(){
        return level;
    }
}
