package com.zhaoshuang.nasaircraftwar.aircraft;

import android.content.Context;

import com.zhaoshuang.nasaircraftwar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoshuang on 16/8/16.
 * 敌军管理类
 */
public class EnemyManage{

    private Context context;
    public List<Enemy> list;
    public static final int TYPE_SMALL = 001;
    public static final int TYPE_MIDDLE = 002;
    public static final int TYPE_BIG = 003;
    private final int dp10;

    private EnemyManage(Context context){
        this.context = context;
        list = new ArrayList<>();
        dp10 = (int) context.getResources().getDimension(R.dimen.dp10);
    }

    private static EnemyManage enemyManage;

    public static synchronized EnemyManage getInstance(Context context){
        if(enemyManage == null) enemyManage = new EnemyManage(context);
        return enemyManage;
    }

    public static void destroy(){
        enemyManage = null;
    }

    public void removeEnemy(int index){
        if(list.size() > 0) {
            list.get(index).destroy();
            list.remove(index);
        }
    }

    public Enemy newEnemy(int type){

        Enemy enemy = null;
        switch (type){
            case TYPE_SMALL:
                enemy = new Enemy(context, R.mipmap.small);
                enemy.setMoveSlide(dp10 / 2);
                break;
            case TYPE_MIDDLE:
                enemy = new Enemy(context, R.mipmap.middle);
                enemy.setMoveSlide(dp10 / 3);
                break;
            case TYPE_BIG:
                enemy = new Enemy(context, R.mipmap.big);
                enemy.setMoveSlide(dp10 / 5);
                break;
        }
        if(enemy != null){
            enemy.setEnemyType(type);
            list.add(enemy);
        }
        return enemy;
    }

    public List<Enemy> getEnemyList(){
        return list;
    }
}
