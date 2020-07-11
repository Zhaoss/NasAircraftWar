package com.zhaoshuang.nasaircraftwar.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.zhaoshuang.nasaircraftwar.R;
import com.zhaoshuang.nasaircraftwar.adapter.RankingAdapter;
import com.zhaoshuang.nasaircraftwar.bean.NasGetBean;
import com.zhaoshuang.nasaircraftwar.bean.RankingBean;
import com.zhaoshuang.nasaircraftwar.util.MyUtil;
import com.zhaoshuang.nasaircraftwar.util.RankingDBUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.nebulas.api.SmartContracts;

/**
 * Created by zhaoshuang on 2018/6/1.
 * 排行榜
 */

public class RankingActivity extends BaseActivity {

    private RecyclerView rv_ranking;
    private TextView tv_hint;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ranking);

        rv_ranking = findViewById(R.id.rv_ranking);
        tv_hint = findViewById(R.id.tv_hint);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        rv_ranking.setLayoutManager(new LinearLayoutManager(mContext));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
    }

    boolean isFirst = true;
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(isFirst){
            isFirst = false;
            swipeRefreshLayout.setRefreshing(true);
            getData();
        }
    }

    private void getData(){

        MyUtil.getRank(new SmartContracts.StatusCallback() {
            @Override
            public void onSuccess(String response) {
                try{

                    Gson gson = new Gson();
                    NasGetBean nasGetBean = gson.fromJson(response, NasGetBean.class);
                    String result = nasGetBean.getResult().getResult();
                    List<RankingBean> rankBeans = gson.fromJson(result, new TypeToken<List<RankingBean>>(){}.getType());
                    for (RankingBean bean : rankBeans){
                        try {
                            RankingBean rankingBeanById = RankingDBUtil.getRankingBeanById(mContext, bean.getHas());
                            if(rankingBeanById != null){
                                RankingDBUtil.removeRankingBean(mContext, rankingBeanById);
                            }
                            RankingDBUtil.save(mContext, bean);
                        }catch (JsonSyntaxException e){
                            e.printStackTrace();
                        }
                    }
                    myHandler.sendEmptyMessage(1);
                }catch (Exception e){
                    e.printStackTrace();
                    myHandler.sendEmptyMessage(0);
                }

            }

            @Override
            public void onFail(String error) {
                myHandler.sendEmptyMessage(1);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    tv_hint.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    ArrayList<RankingBean> rankingBeanList = RankingDBUtil.getRankingBeanList(mContext);
                    if(rankingBeanList!=null && rankingBeanList.size()>0){
                       Collections.sort(rankingBeanList, new Comparator<RankingBean>() {
                           @Override
                           public int compare(RankingBean o1, RankingBean o2) {
                               try {
                                   int score1 = Integer.valueOf(o1.getScore());
                                   int score2 = Integer.valueOf(o2.getScore());
                                   return score2-score1;
                               }catch (Exception e){
                                   return 0;
                               }
                           }
                       });
                       RankingAdapter rankingAdapter = new RankingAdapter(mContext, rankingBeanList);
                       rv_ranking.setAdapter(rankingAdapter);
                    }else{
                        tv_hint.setVisibility(View.VISIBLE);
                    }
                    break;
            }
            swipeRefreshLayout.setRefreshing(false);
        }
    };
}
