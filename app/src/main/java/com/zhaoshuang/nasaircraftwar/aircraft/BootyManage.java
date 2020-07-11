package com.zhaoshuang.nasaircraftwar.aircraft;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoshuang on 16/8/18.
 */
public class BootyManage{

    private Context context;
    private List<Booty> list;
    private static BootyManage bootyManage;

    private BootyManage(Context context){
        this.context = context;
        list = new ArrayList();
    }

    public static synchronized BootyManage getInstance(Context context){

        if(bootyManage == null) bootyManage = new BootyManage(context);
        return bootyManage;
    }

    public static void destroy(){
        bootyManage = null;
    }

    public Booty newBooby(int type){

        Booty bullet = new Booty(context, type);
        list.add(bullet);
        return bullet;
    }

    public void removeBooby(int index){
        if(list.size() > 0){
            list.get(index).destroy();
            list.remove(index);
        }
    }

    public List<Booty> getBootyList(){
        return list;
    }
}
