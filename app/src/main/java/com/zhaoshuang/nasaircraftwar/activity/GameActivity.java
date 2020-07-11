package com.zhaoshuang.nasaircraftwar.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhaoshuang.nasaircraftwar.R;
import com.zhaoshuang.nasaircraftwar.aircraft.BootyManage;
import com.zhaoshuang.nasaircraftwar.aircraft.BulletManage;
import com.zhaoshuang.nasaircraftwar.aircraft.EnemyManage;
import com.zhaoshuang.nasaircraftwar.aircraft.ExplosionManage;
import com.zhaoshuang.nasaircraftwar.aircraft.My;
import com.zhaoshuang.nasaircraftwar.bean.NasResponseBean;
import com.zhaoshuang.nasaircraftwar.bean.RankingBean;
import com.zhaoshuang.nasaircraftwar.util.MyUtil;
import com.zhaoshuang.nasaircraftwar.util.RankingDBUtil;
import com.zhaoshuang.nasaircraftwar.util.ToastUtil;
import com.zhaoshuang.nasaircraftwar.view.GameView;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.nebulas.Constants;
import io.nebulas.api.SmartContracts;
import io.nebulas.model.GoodsModel;
import io.nebulas.utils.Util;

/**
 * Created by zhaoshuang on 16/8/12.
 * 游戏界面
 */
public class GameActivity extends BaseActivity {

    private final int REQUEST_PAY_CODE = 100;
    private final int REQUEST_CALL_CODE = 101;

    private GameView gameView;

    //游戏积分
    private int myScore;

    private int currActionType;
    private String randomCode;
    private RankingBean currRankingBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        gameView = findViewById(R.id.gameView);

        initGameView();
    }

    private void initGameView() {

        gameView.setOnGameOverListener(new GameView.OnGameOverListener() {
            @Override
            public void onGameOver(int score) {
                myScore = score;
                gameView.removeOnGameOverListener();
                showGameOverDialog(score);
            }
        });
    }

    private void showGameOverDialog(final int score){

        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setCancelable(false);
        builder.setMessage("游戏分数: "+score+"  是否1Gas复活?");
        builder.setNeutralButton("分享战绩到区块链", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismissDialog();
                showEditName();
            }
        });
        builder.setPositiveButton("算了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismissDialog();
                finish();
            }
        });
        builder.setNegativeButton("我要复活", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismissDialog();

                GoodsModel goods = new GoodsModel();
                goods.name = "星云飞机大战";
                goods.desc = "立即复活";
                randomCode = Util.getRandomCode(Constants.RANDOM_LENGTH);
                currActionType = REQUEST_PAY_CODE;
                MyUtil.pay(mContext, MyUtil.GAS_VALUE, goods, randomCode);
            }
        });
        builder.show();
    }

    private void showEditName(){

        View view = showEditNameDialog();

        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);
        final EditText et_name = view.findViewById(R.id.et_name);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissDialog();
                finish();
            }
        });

        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = et_name.getText().toString();
                if(TextUtils.isEmpty(name)){
                    name = "匿名";
                }

                randomCode = Util.getRandomCode(Constants.RANDOM_LENGTH);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                currRankingBean = new RankingBean(simpleDateFormat.format(date), randomCode, myScore+"", name);
                MyUtil.setRank(mContext, currRankingBean, randomCode);

                currActionType = REQUEST_CALL_CODE;
            }
        });

        et_name.post(new Runnable() {
            @Override
            public void run() {
                MyUtil.showInPut(mContext, et_name);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(currActionType == REQUEST_PAY_CODE) {
            if (!TextUtils.isEmpty(randomCode)) {
                showProDialog();
                MyUtil.query(randomCode, new SmartContracts.StatusCallback() {
                    @Override
                    public void onSuccess(String response) {
                        dismissDialog();

                        try {
                            Gson gson = new Gson();
                            NasResponseBean bean = gson.fromJson(response, NasResponseBean.class);
                            if ("0".equals(bean.getCode())) {
                                myHandler.sendEmptyMessage(1);
                            }else{
                                myHandler.sendEmptyMessage(0);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            myHandler.sendEmptyMessage(0);
                        }

                        randomCode = null;
                    }
                    @Override
                    public void onFail(String error) {
                        dismissDialog();
                        myHandler.sendEmptyMessage(0);

                        randomCode = null;
                    }
                });
            }
        }else if(currActionType == REQUEST_CALL_CODE){
            showProDialog();
            MyUtil.query(randomCode, new SmartContracts.StatusCallback() {
                @Override
                public void onSuccess(String response) {
                    dismissDialog();

                    try {
                        Gson gson = new Gson();
                        NasResponseBean bean = gson.fromJson(response, NasResponseBean.class);
                        if ("0".equals(bean.getCode())) {
                            if(currRankingBean != null) {
                                RankingDBUtil.save(mContext, currRankingBean);
                            }
                            myHandler.sendEmptyMessage(2);
                        }else{
                            myHandler.sendEmptyMessage(0);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        myHandler.sendEmptyMessage(0);
                    }

                    randomCode = null;
                    currRankingBean = null;
                }
                @Override
                public void onFail(String error) {
                    dismissDialog();
                    myHandler.sendEmptyMessage(0);

                    randomCode = null;
                    currRankingBean = null;
                }
            });
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
                    finish();
                    break;
                case 1:
                    initGameView();
                    gameView.resetMy();
                    ToastUtil.show(mContext, "复活成功");
                    break;
                case 2:
                    Intent intent = new Intent(mContext, RankingActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    private void release(){

        BootyManage.destroy();
        BulletManage.destroy();
        EnemyManage.destroy();
        ExplosionManage.destroy();
        My.destroyMy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        release();
    }
}
