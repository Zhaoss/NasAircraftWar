package com.zhaoshuang.nasaircraftwar.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.WindowManager;

import com.zhaoshuang.nasaircraftwar.R;

/**
 * Created by zhaoshuang on 2018/5/31.
 * 帮助界面
 */

public class HelpActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_help);

    }
}
