package com.zhaoshuang.nasaircraftwar.aircraft;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoshuang on 16/8/12.
 * 子弹管理类
 */
public class BulletManage{

    private Context context;
    private List<Bullet> list;
    private static BulletManage bulletManage;

    private BulletManage(Context context){
        this.context = context;
        list = new ArrayList();
    }

    public static synchronized BulletManage getInstance(Context context){

        if(bulletManage == null) bulletManage = new BulletManage(context);
        return bulletManage;
    }

    public static void destroy(){
        bulletManage = null;
    }

    public Bullet newBullet(int type){

        Bullet bullet = new Bullet(context, type);
        list.add(bullet);
        return bullet;
    }

    public void removeBullet(int index){
        if(list.size() > 0){
            list.get(index).destroy();
            list.remove(index);
        }
    }

    public List<Bullet> getBulletList(){
        return list;
    }
}
