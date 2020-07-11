package com.zhaoshuang.nasaircraftwar.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhaoshuang.nasaircraftwar.R;
import com.zhaoshuang.nasaircraftwar.bean.NasResponseBean;
import com.zhaoshuang.nasaircraftwar.util.MyUtil;
import com.zhaoshuang.nasaircraftwar.util.SPUtil;
import com.zhaoshuang.nasaircraftwar.util.ToastUtil;

import io.nebulas.Constants;
import io.nebulas.api.SmartContracts;
import io.nebulas.utils.Util;

/**
 * Created by zhaoshuang on 2018/5/31.
 * 购买nas
 */

public class NasPayActivity extends BaseActivity {

    private final int REQUEST_PAY_CODE = 100;

    private TextView tv_boom_pay;
    private TextView tv_boom_bullet;
    private TextView tv_boom_sum;
    private TextView tv_bullet_sum;

    private String randomCode;
    private String payType;

    private int currActionType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nas_pay);

        tv_boom_pay = findViewById(R.id.tv_boom_pay);
        tv_boom_bullet = findViewById(R.id.tv_boom_bullet);
        tv_boom_sum = findViewById(R.id.tv_boom_sum);
        tv_bullet_sum = findViewById(R.id.tv_bullet_sum);

        initWeaponrySum();

        tv_boom_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                randomCode = Util.getRandomCode(Constants.RANDOM_LENGTH);
                payType = SPUtil.TYPE_BOOM;
                String content = (SPUtil.getBoomSum(mContext)+1)+" "+SPUtil.getBulletSum(mContext);
                MyUtil.setSum(mContext, SPUtil.getAndroidId(mContext), content, randomCode);

                currActionType = REQUEST_PAY_CODE;
            }
        });

        tv_boom_bullet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                randomCode = Util.getRandomCode(Constants.RANDOM_LENGTH);
                payType = SPUtil.TYPE_BULLET;
                String content = SPUtil.getBoomSum(mContext)+" "+(SPUtil.getBulletSum(mContext)+1);
                MyUtil.setSum(mContext, SPUtil.getAndroidId(mContext), content, randomCode);

                currActionType = REQUEST_PAY_CODE;
            }
        });
    }

    private void initWeaponrySum(){

        int boomSum = SPUtil.getBoomSum(mContext);
        tv_boom_sum.setText("(已拥有"+boomSum+"个)");

        int bulletSum = SPUtil.getBulletSum(mContext);
        tv_bullet_sum.setText("(已拥有"+bulletSum+"个)");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(currActionType == REQUEST_PAY_CODE) {
            if (!TextUtils.isEmpty(randomCode) && !TextUtils.isEmpty(payType)) {
                showProDialog();
                MyUtil.query(randomCode, new SmartContracts.StatusCallback() {
                    @Override
                    public void onSuccess(String response) {
                        dismissDialog();

                        try {
                            Gson gson = new Gson();
                            NasResponseBean bean = gson.fromJson(response, NasResponseBean.class);
                            if ("0".equals(bean.getCode())) {
                                MyUtil.addWeaponry(mContext, payType, 1);
                                myHandler.sendEmptyMessage(1);
                            } else {
                                myHandler.sendEmptyMessage(0);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            myHandler.sendEmptyMessage(0);
                        }

                        randomCode = null;
                        payType = null;
                    }

                    @Override
                    public void onFail(String error) {
                        dismissDialog();
                        myHandler.sendEmptyMessage(0);
                        randomCode = null;
                        payType = null;
                    }
                });
            }
        }

        currActionType = 0;
    }

    @SuppressLint("HandlerLeak")
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    ToastUtil.show(mContext, "支付失败");
                    break;
                case 1:
                    initWeaponrySum();
                    ToastUtil.show(mContext, "购买成功");
                    break;
            }
        }
    };
}
