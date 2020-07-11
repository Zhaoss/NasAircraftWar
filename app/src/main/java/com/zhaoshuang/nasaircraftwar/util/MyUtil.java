package com.zhaoshuang.nasaircraftwar.util;

import android.Manifest;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.zhaoshuang.nasaircraftwar.bean.RankingBean;

import java.util.List;

import io.nebulas.Constants;
import io.nebulas.api.SmartContracts;
import io.nebulas.model.ContractModel;
import io.nebulas.model.GoodsModel;

/**
 * Created by zhaoshuang on 2018/6/1.
 */

public class MyUtil {

    public final static String GAS_VALUE = "1";

    //钱包地址
    public static String payHas = "n1cEgvscR3DxGqUfEpqkGHAH8kk717DAkFj";
    //合约地址
    public static String rankCallHas = "n1tDUMDmeXN5dnS2sHW4yqggj14rARMfjXG";

    public static void pay(Context context, String value, GoodsModel goods, String randomCode){

        SmartContracts.pay(context, Constants.MAIN_NET, goods, payHas, value , randomCode);
    }

    public static void setRank(Context context, RankingBean bean, String randomCode){

        GoodsModel goods = new GoodsModel();
        goods.name = "星云飞机大战";
        goods.desc = "分享积分";

        String[] args = {bean.getHas(), bean.getScore()+"", bean.getName()};

        SmartContracts.call(context, Constants.MAIN_NET, goods, "setRanking", rankCallHas, GAS_VALUE, args, randomCode);
    }

    public static void getRank(SmartContracts.StatusCallback statusCallback){

        ContractModel contractModel = new ContractModel();
        contractModel.function = "getRanking";
        SmartContracts.call(contractModel, rankCallHas, rankCallHas, 0, statusCallback);
    }

    public static void setSum(Context context, String androidId, String sum, String randomCode){

        GoodsModel goods = new GoodsModel();
        goods.name = "星云飞机大战";
        goods.desc = "设置武器弹药数量";
        SmartContracts.call(context, Constants.MAIN_NET, goods, "setSum", rankCallHas, "1", new String[]{androidId, sum}, randomCode);
    }

    public static void getSum(String androidId, SmartContracts.StatusCallback statusCallback){

        ContractModel contractModel = new ContractModel();
        contractModel.function = "getSum";
        contractModel.args = "[\""+androidId+"\"]";
        SmartContracts.call(contractModel, rankCallHas, rankCallHas, 0, statusCallback);
    }

    public static void query(String randomCode, SmartContracts.StatusCallback statusCallback){

        SmartContracts.queryTransferStatus(Constants.MAIN_NET, randomCode, statusCallback);
    }

    //增加武器弹药数目
    public static void addWeaponry(Context context, String type, int sum){

        switch (type){
            case SPUtil.TYPE_BOOM:
                int boomSum = SPUtil.getBoomSum(context);
                SPUtil.setBoomSum(context, boomSum + sum);
                break;
            case SPUtil.TYPE_BULLET:
                int bulletSum = SPUtil.getBulletSum(context);
                SPUtil.setBulletSum(context, bulletSum + sum);
                break;
        }
    }

    public static void showInPut(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, 0);
        }
    }

    public static void requestPermission(final Context context) {

        AndPermission.with(context)
            .runtime()
            .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .onGranted(new Action<List<String>>() {
                @Override
                public void onAction(List<String> data) {
                }
            })
            .onDenied(new Action<List<String>>() {
                @Override
                public void onAction(List<String> data) {

                }
            })
            .start();
    }

}
