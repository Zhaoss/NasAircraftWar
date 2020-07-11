package com.zhaoshuang.nasaircraftwar;

import android.app.Application;

import com.zhaoshuang.nasaircraftwar.util.ToastUtil;

/**
 * Created by zhaoshuang on 2018/6/1.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ToastUtil.getToast(getApplicationContext(), "");
    }
}
