package com.zhaoshuang.nasaircraftwar.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhaoshuang.nasaircraftwar.R;
import com.zhaoshuang.nasaircraftwar.bean.RankingBean;

import java.util.ArrayList;

/**
 * Created by zhaoshuang on 2018/6/1.
 */

public class RankingAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<RankingBean> rankingBeanList;

    public RankingAdapter(Context context, ArrayList<RankingBean> rankingBeanList){
        this.mContext = context;
        this.rankingBeanList = rankingBeanList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(mContext, R.layout.layout_ranking, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyViewHolder vh = (MyViewHolder) holder;
        RankingBean bean = rankingBeanList.get(position);

        if(position == 0){
            vh.ll_title.setVisibility(View.VISIBLE);
        }else{
            vh.ll_title.setVisibility(View.GONE);
        }

        vh.tv_top.setText(String.valueOf(position+1));


        if(!TextUtils.isEmpty(bean.getName())){
            vh.tv_name.setText(bean.getName());
        }else{
            vh.tv_name.setText(bean.getHas());
        }
        vh.tv_score.setText(String.valueOf(bean.getScore()));

        vh.tv_time.setText(bean.getDate());
    }

    @Override
    public int getItemCount() {
        if(rankingBeanList == null){
            return 0;
        }
        return rankingBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout ll_title;
        private TextView tv_top;
        private TextView tv_name;
        private TextView tv_score;
        private TextView tv_time;

        public MyViewHolder(View itemView) {
            super(itemView);

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            itemView.setLayoutParams(layoutParams);

            ll_title = itemView.findViewById(R.id.ll_title);
            tv_top = itemView.findViewById(R.id.tv_top);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_score = itemView.findViewById(R.id.tv_score);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }
}
