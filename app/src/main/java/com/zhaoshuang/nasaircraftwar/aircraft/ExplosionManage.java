package com.zhaoshuang.nasaircraftwar.aircraft;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoshuang on 16/8/17.
 */
public class ExplosionManage{

    private Context context;
    private List<Explosion> list;

    private ExplosionManage(Context context){
        this.context = context;
        list = new ArrayList<>();
    }

    private static ExplosionManage explosionManage;

    public static synchronized ExplosionManage getInstance(Context context){
        if(explosionManage == null) explosionManage = new ExplosionManage(context);
        return explosionManage;
    }

    public static void destroy(){
        explosionManage = null;
    }

    public Explosion newExplosion(float x, float y){

        Explosion explosion = new Explosion(context);
        explosion.setX(x);
        explosion.setY(y);
        list.add(explosion);
        return explosion;
    }

    public void drawExplosion(Canvas canvas, Paint paint){

        for (int x=0; x<list.size(); x++){
            Explosion explosion = list.get(x);
            canvas.drawBitmap(explosion.getBitmap(), explosion.getBitmapSrcRec(), explosion.getRectF(), paint);
            if(explosion.getLevel() >= 14){
                explosion.destroy();
                list.remove(x);
            }
        }
    }
}