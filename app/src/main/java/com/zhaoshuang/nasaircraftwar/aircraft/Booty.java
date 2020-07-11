package com.zhaoshuang.nasaircraftwar.aircraft;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.zhaoshuang.nasaircraftwar.R;

/**
 * Created by zhaoshuang on 16/8/18.
 * 战利品
 */
public class Booty extends BaseCompany{

    public static final int TYPE_BULLET = 101;
    public static final int TYPE_BOOM = 102;

    private int dp10;
    private int frame;

    private int type = TYPE_BULLET;

    public Booty(Context context, int type){

        this.type = type;
        int resources = TYPE_BULLET;
        if(type == TYPE_BULLET){
            resources = R.mipmap.bullet_award;
        }else if(type == TYPE_BOOM){
            resources = R.mipmap.boom_award;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resources);
        Matrix matrix = new Matrix();
        matrix.postScale(2f, 2f);
        setBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true));

        dp10 = (int) context.getResources().getDimension(R.dimen.dp10);
    }

    public int getType() {
        return type;
    }

    public void moveY(){

        frame++;
        float y = getY();
        if(y < dp10){
            y += dp10/5;
        }else if(y >= dp10 && frame>=60){
            y += dp10;
        }
        setY(y);
    }
}
