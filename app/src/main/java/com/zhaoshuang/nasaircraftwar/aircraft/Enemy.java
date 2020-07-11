package com.zhaoshuang.nasaircraftwar.aircraft;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * Created by zhaoshuang on 16/8/16.
 * 敌军对象
 */
public class Enemy extends BaseCompany {

    private int slide;
    private int type = EnemyManage.TYPE_SMALL;

    public Enemy(Context context, int resource){

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resource);
        Matrix matrix = new Matrix();
        matrix.postScale(2f, 2f);
        setBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true));
    }

    public void setMoveSlide(int slide){
        this.slide = slide;
    }

    public void moveY(){
        setY(getY()+slide);
    }

    public void setEnemyType(int type){
        this.type = type;
    }

    public int getType(){
        return type;
    }
}
