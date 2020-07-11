package com.zhaoshuang.nasaircraftwar.aircraft;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.zhaoshuang.nasaircraftwar.R;

/**
 * Created by zhaoshuang on 16/8/15.
 * 子弹对象
 */
public class Bullet extends BaseCompany{

    public static final int TYPE_SINGLE = 101;
    public static final int TYPE_DOUBLE = 102;

    private int dp10;
    private int type = TYPE_SINGLE;

    public Bullet(Context context, int type){

        this.type = type;
        int resource = R.mipmap.yellow_bullet;
        if(type == TYPE_SINGLE){
            resource = R.mipmap.yellow_bullet;
        }else if(type == TYPE_DOUBLE){
            resource = R.mipmap.blue_bullet;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resource);
        Matrix matrix = new Matrix();
        matrix.postScale(2f, 2f);
        setBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true));

        dp10 = (int) context.getResources().getDimension(R.dimen.dp10);
    }

    public void moveY(){
        setY((int) (getY()-dp10/1.5f));
    }
}
