package com.zhaoshuang.nasaircraftwar.aircraft;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.zhaoshuang.nasaircraftwar.R;

/**
 * Created by zhaoshuang on 16/8/12.
 * 本人飞机对象
 */
public class My extends BaseCompany{

    private static My my;

    private My(Context context){

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.my);
        Matrix matrix = new Matrix();
        matrix.postScale(2f, 2f);
        setBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true));
    }

    public static synchronized My getInstance(Context context){
        if(my == null) {
            my = new My(context);
        }
        return my;
    }

    public static void destroyMy(){
        my = null;
    }
}
