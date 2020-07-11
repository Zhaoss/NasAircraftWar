package com.zhaoshuang.nasaircraftwar.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhaoshuang.nasaircraftwar.R;
import com.zhaoshuang.nasaircraftwar.bean.NasGetBean;
import com.zhaoshuang.nasaircraftwar.util.MyUtil;
import com.zhaoshuang.nasaircraftwar.util.SPUtil;

import io.nebulas.api.SmartContracts;

public class MainActivity extends BaseActivity {

    private TextView tv_new_game;
    private TextView tv_pay;
    private TextView tv_ranking_list;
    private TextView tv_help;
    private TextView tv_close_game;
    private TextView tv_download_nas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        tv_new_game = findViewById(R.id.tv_new_game);
        tv_pay = findViewById(R.id.tv_pay);
        tv_ranking_list = findViewById(R.id.tv_ranking_list);
        tv_help = findViewById(R.id.tv_help);
        tv_close_game = findViewById(R.id.tv_close_game);
        tv_download_nas = findViewById(R.id.tv_download_nas);

        tv_new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, GameActivity.class));
            }
        });

        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, NasPayActivity.class));
            }
        });

        tv_ranking_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, RankingActivity.class));
            }
        });

        tv_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, HelpActivity.class));
            }
        });

        tv_close_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_download_nas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://nano.nebulas.io/download/app/app-1.1.0-ch-MainNet-release.apk");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        MyUtil.requestPermission(mContext);

        String androidId = Settings.System.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        //第一次进来
        if("0".equals(SPUtil.getAndroidId(mContext))) {
            SPUtil.setAndroidId(mContext, androidId);
            final int initSum = 5;
            SPUtil.setBoomSum(mContext, initSum);
            SPUtil.setBulletSum(mContext, initSum);
            MyUtil.getSum(androidId, new SmartContracts.StatusCallback() {
                @Override
                public void onSuccess(String response) {
                    try {
                        Gson gson = new Gson();
                        NasGetBean nasGetBean = gson.fromJson(response, NasGetBean.class);
                        String result = nasGetBean.getResult().getResult();
                        String substring = result.substring(1, result.length()-1);
                        String[] split = substring.split(" ");
                        SPUtil.setBoomSum(mContext, Integer.valueOf(split[0])+initSum);
                        SPUtil.setBulletSum(mContext, Integer.valueOf(split[1])+initSum);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFail(String error) {

                }
            });
        }
    }

    boolean isFirst = true;
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(isFirst){
            isFirst = false;
            tv_new_game.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(mContext, RankingActivity.class));
                }
            }, 800);
        }
    }
}
