package com.zhaoshuang.nasaircraftwar.util;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by zhaoshuang on 2018/6/1.
 */

public class ToastUtil {

    private static Toast toast;

    public static Toast getToast(Context context, String text){
        if (toast == null) {
            if(Looper.getMainLooper() == Looper.myLooper()) {
                toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            }
        }
        return toast;
    }

    public static void show(Context context, String text){

        if (toast == null) {
            getToast(context, text);
        }else{
            toast.setText(text);
        }
        if(toast != null) {
            toast.show();
        }
    }
}
