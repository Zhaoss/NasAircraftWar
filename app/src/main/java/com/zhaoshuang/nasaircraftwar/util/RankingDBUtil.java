package com.zhaoshuang.nasaircraftwar.util;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.zhaoshuang.nasaircraftwar.bean.RankingBean;

import java.util.ArrayList;

/**
 * Created by zhaoshuang on 2018/6/1.
 */

public class RankingDBUtil {

    private static LiteOrm rankingDB;

    private static LiteOrm getRankingDB(Context context){
        if(rankingDB == null) {
            rankingDB = LiteOrm.newSingleInstance(new DataBaseConfig(context, "RankingDB"));
        }
        return  rankingDB;
    }

    public static RankingBean getRankingBeanById(Context context, String has) {
        return getRankingDB(context).queryById(has, RankingBean.class);
    }

    public static int removeRankingBean(Context context, RankingBean rankingBean) {
        return getRankingDB(context).delete(rankingBean);
    }

    public static ArrayList<RankingBean> getRankingBeanList(Context context) {
        return getRankingDB(context).query(new QueryBuilder<>(RankingBean.class).appendOrderDescBy(RankingBean.SCORE));
    }

    public static void save(Context context, RankingBean chatNotifyBean) {
        getRankingDB(context).save(chatNotifyBean);
    }
}
