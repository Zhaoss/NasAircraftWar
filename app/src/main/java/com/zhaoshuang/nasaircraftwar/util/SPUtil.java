package com.zhaoshuang.nasaircraftwar.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhaoshuang on 2018/6/1.
 */

public class SPUtil {

    public static final String TYPE_BOOM = "type_boom";
    public static final String TYPE_BULLET = "type_bullet";
    public static final String TYPE_ANDROID_ID = "type_android_id";

    private static SharedPreferences sp;

    private static SharedPreferences getSP(Context context){

        if (sp == null) {
            sp = context.getSharedPreferences("mySP", Context.MODE_PRIVATE);
        }
        return sp;
    }

    public static void setBoomSum(Context context, int sum){
        SharedPreferences.Editor editor = getSP(context).edit();
        editor.putInt(TYPE_BOOM, sum);
        editor.apply();
    }

    public static int getBoomSum(Context context){
        return getSP(context).getInt(TYPE_BOOM, 0);
    }

    public static void setBulletSum(Context context, int sum){
        SharedPreferences.Editor editor = getSP(context).edit();
        editor.putInt(TYPE_BULLET, sum);
        editor.apply();
    }

    public static int getBulletSum(Context context) {
        return getSP(context).getInt(TYPE_BULLET, 0);
    }

    public static void setAndroidId(Context context, String phoneId){
        SharedPreferences.Editor editor = getSP(context).edit();
        editor.putString(TYPE_ANDROID_ID, phoneId);
        editor.apply();
    }

    public static String getAndroidId(Context context) {
        return getSP(context).getString(TYPE_ANDROID_ID, "0");
    }

}
