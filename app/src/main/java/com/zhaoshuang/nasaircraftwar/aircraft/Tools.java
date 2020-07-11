package com.zhaoshuang.nasaircraftwar.aircraft;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.zhaoshuang.nasaircraftwar.R;

/**
 * Created by zhaoshuang on 16/8/18.
 * 武器工具
 */
public class Tools extends BaseCompany {

    public Tools(Context context){

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.boom);
        Matrix matrix = new Matrix();
        matrix.postScale(2f, 2f);
        setBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true));
    }
}
