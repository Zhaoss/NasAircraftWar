package com.zhaoshuang.nasaircraftwar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.zhaoshuang.nasaircraftwar.R;
import com.zhaoshuang.nasaircraftwar.aircraft.Booty;
import com.zhaoshuang.nasaircraftwar.aircraft.BootyManage;
import com.zhaoshuang.nasaircraftwar.aircraft.Bullet;
import com.zhaoshuang.nasaircraftwar.aircraft.BulletManage;
import com.zhaoshuang.nasaircraftwar.aircraft.Enemy;
import com.zhaoshuang.nasaircraftwar.aircraft.EnemyManage;
import com.zhaoshuang.nasaircraftwar.aircraft.Explosion;
import com.zhaoshuang.nasaircraftwar.aircraft.ExplosionManage;
import com.zhaoshuang.nasaircraftwar.aircraft.My;
import com.zhaoshuang.nasaircraftwar.aircraft.Tools;
import com.zhaoshuang.nasaircraftwar.util.SPUtil;

import java.util.List;
import java.util.Random;

/**
 * Created by zhaoshuang on 16/8/12.
 * 游戏界面
 */
public class GameView extends View {

    private final int DOUBLE_BULLET_FRAME = 500;

    private Paint paint;
    private int dp10;
    private int windowWidth;
    private int windowHeight;
    private My my;
    private BulletManage bulletManage;
    private int frame = 1;
    private EnemyManage enemyManage;
    private Random random;
    private boolean flag = true;//游戏是否继续
    private long lastTime;
    private int frameForSS;
    private String frameForSSStr = "0";
    private Paint framePaint;
    private ExplosionManage explosionManage;
    private Explosion myExplosion;
    private int score;//游戏分数
    private Paint scorePaint;
    private BootyManage bootyManage;
    //子弹类型
    private int bulletType = Bullet.TYPE_SINGLE;
    private int bulletStateFrame;
    //武器工具
    private Tools tools;
    private int boomSum;
    private OnGameOverListener onGameOverListener;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        dp10 = (int) getResources().getDimension(R.dimen.dp10);

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        framePaint = new Paint();
        framePaint.setColor(Color.BLACK);
        framePaint.setTextSize(dp10 * 1.5f);
        framePaint.setAntiAlias(true);

        scorePaint = new Paint();
        scorePaint.setColor(Color.RED);
        scorePaint.setTextSize(dp10 * 2);
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        scorePaint.setTypeface(font);
        scorePaint.setAntiAlias(true);

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowWidth = wm.getDefaultDisplay().getWidth();
        windowHeight = wm.getDefaultDisplay().getHeight();


        my = My.getInstance(getContext());
        my.setX(windowWidth / 2 - my.getWidth() / 2);
        my.setY(windowHeight - dp10 * 10);

        bulletManage = BulletManage.getInstance(getContext());
        enemyManage = EnemyManage.getInstance(getContext());
        explosionManage = ExplosionManage.getInstance(getContext());
        bootyManage = BootyManage.getInstance(getContext());

        random = new Random();

        lastTime = System.currentTimeMillis();

        //初始化炸弹
        tools = new Tools(getContext());
        tools.setX(dp10);
        tools.setY(windowHeight-tools.getHeight()-dp10);
        boomSum = SPUtil.getBoomSum(context);

        //初始化增强子弹
        int bulletSum = SPUtil.getBulletSum(context);
        if(bulletSum > 0){
            bulletType = Bullet.TYPE_DOUBLE;
            bulletStateFrame += bulletSum*DOUBLE_BULLET_FRAME;
        }
    }

    float downX;
    float downY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                downX = event.getX(event.getActionIndex());
                downY = event.getY(event.getActionIndex());
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                if(x>my.getX()-my.getWidth()/2 && x<my.getX()+my.getWidth()+my.getWidth()/2
                        && y>my.getY()-my.getHeight()/2 && y<my.getY()+my.getHeight()+my.getHeight()/2){
                    float moveX = x - my.getWidth() / 2;
                    float moveY = y - my.getHeight() / 2;
                    if(moveX < 0) moveX = 0;
                    if(moveX > windowWidth-my.getWidth()) moveX = windowWidth-my.getWidth();
                    if(moveY < 0) moveY = my.getHeight();
                    if(moveY > windowHeight-my.getHeight()) moveY = windowHeight-my.getHeight();
                    my.setX(moveX);
                    my.setY(moveY);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                checkBoomUpListener(downX, downY, event.getX(event.getActionIndex()), event.getY(event.getActionIndex()));
                break;
        }
        return true;
    }

    private void checkBoomUpListener(float clickX, float clickY, float upX, float upY){

        if(Math.abs(upX-clickX)<dp10 && Math.abs(upY-clickY)<dp10){
            if(upX>tools.getX()-tools.getWidth()/2 && upX<tools.getX()+tools.getWidth()+tools.getWidth()/2
                    && upY>tools.getY()-tools.getHeight()/2 && upY<tools.getY()+tools.getHeight()+tools.getHeight()/2){
                for (Enemy e : enemyManage.getEnemyList()){
                    e.notHave();
                }
                boomSum--;
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if(boomSum < 0){
            boomSum = 0;
        }
        SPUtil.setBoomSum(getContext(), boomSum);

        int bulletSum = 0;
        if(bulletStateFrame >= DOUBLE_BULLET_FRAME){
            bulletSum = bulletStateFrame/DOUBLE_BULLET_FRAME;
        }
        SPUtil.setBulletSum(getContext(), bulletSum);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        drawMy(canvas);
        drawBullet(canvas);
        drawEnemy(canvas);
        drawBooty(canvas);
        drawScore(canvas);
        drawFrame(canvas);
        drawTools(canvas);

        checkBullet();
        checkEnemy();
        checkBooty();

        explosionManage.drawExplosion(canvas, paint);
        if(flag) invalidate();
        frame++;
        frameForSS++;
    }

    public void setOnGameOverListener(OnGameOverListener onGameOverListener){
        this.onGameOverListener = onGameOverListener;
    }

    public void removeOnGameOverListener(){
        onGameOverListener = null;
    }

    public interface OnGameOverListener{
        void onGameOver(int score);
    }

    //绘制武器工具
    private void drawTools(Canvas canvas) {

        if(boomSum > 0){
            canvas.drawBitmap(tools.getBitmap(), tools.getX(), tools.getY(), paint);
            canvas.drawText(" x " + boomSum, tools.getX()+tools.getWidth(),
                    tools.getY()+tools.getHeight()/2+dp10/2, framePaint);
        }
    }

    private void checkBooty() {

        float x = my.getX();
        float y = my.getY();
        int width = my.getWidth();
        int height = my.getHeight();
        for (Booty b : bootyManage.getBootyList()){
            if (b.getX() > x - b.getWidth() && b.getX() < x + width
                    && b.getY() > y - b.getHeight() && b.getY() < y + height) {
                b.notHave();
                if(b.getType() == Booty.TYPE_BULLET){
                    bulletType = Bullet.TYPE_DOUBLE;
                    bulletStateFrame += DOUBLE_BULLET_FRAME;
                }else if(b.getType() == Booty.TYPE_BOOM){
                    boomSum++;
                }
            }
        }
    }

    /**
     * 检查子弹碰撞
     */
    private void checkBullet() {

        for (Enemy e : enemyManage.getEnemyList()){
            for (Bullet b : bulletManage.getBulletList()){
                float x = e.getX();
                float y = e.getY();
                int width = e.getWidth();
                int height = e.getHeight();
                if(b.getX()>x-b.getWidth() && b.getX()<x+width
                        && b.getY()>y-b.getHeight() && b.getY()<y+height){
                    e.setHp(e.getHp() - 1);
                    b.notHave();
                    if(e.getHp() <= 0){
                        e.notHave();
                        break;
                    }
                }
            }
        }

        List<Enemy> enemyList = enemyManage.getEnemyList();
        for (int x=0; x<enemyList.size(); x++){
            Enemy enemy = enemyList.get(x);
            if(!enemy.isHave()){
                int type = enemy.getType();
                switch (type){
                    case EnemyManage.TYPE_SMALL:
                        score += 200;
                        break;
                    case EnemyManage.TYPE_MIDDLE:
                        score += 400;
                        break;
                    case EnemyManage.TYPE_BIG:
                        score += 1000;
                        break;
                }
                explosionManage.newExplosion(enemy.getX(), enemy.getY());
                enemyManage.removeEnemy(x);
            }
        }
    }

    /**
     * 检查敌军碰撞
     */
    private void checkEnemy() {

        float x = my.getX();
        float y = my.getY();
        int width = my.getWidth();
        int height = my.getHeight();
        for (Enemy e : enemyManage.getEnemyList()){
            if (e.getX() > x - e.getWidth() && e.getX() < x + width
                    && e.getY() > y - e.getHeight() && e.getY() < y + height) {
                if(my.isHave()){
                    myExplosion = explosionManage.newExplosion(x, y);
                    myExplosion.setSpeed(3);
                    my.notHave();
                }
            }
        }
    }

    /**
     * 绘制分数
     */
    private void drawScore(Canvas canvas) {

        canvas.drawText("积分: "+score, dp10, dp10*4, scorePaint);
    }

    /**
     * 绘制帧数
     */
    private void drawFrame(Canvas canvas) {

        long currentTime = System.currentTimeMillis();
        float time = currentTime-lastTime;
        if(time > 1000){
            lastTime = currentTime;
            frameForSSStr = (int)(frameForSS/(time/1000f))+"";
            frameForSS = 0;

        }
        canvas.drawText("FPS: "+frameForSSStr, dp10, dp10*2, framePaint);
    }

    /**
     * 绘制空投装备
     */
    private void drawBooty(Canvas canvas) {

        if(frame%1000 == 0){
            Booty booty = bootyManage.newBooby(Booty.TYPE_BULLET);
            booty.setX(random.nextInt(windowWidth - booty.getWidth()));
            booty.setY(-booty.getHeight());
            canvas.drawBitmap(booty.getBitmap(), booty.getX(), booty.getY(), paint);
        }else if((frame+500)%1000 == 0){
            Booty booty = bootyManage.newBooby(Booty.TYPE_BOOM);
            booty.setX(random.nextInt(windowWidth - booty.getWidth()));
            booty.setY(-booty.getHeight());
            canvas.drawBitmap(booty.getBitmap(), booty.getX(), booty.getY(), paint);
        }
        for (int x=0; x<bootyManage.getBootyList().size(); x++){
            Booty b = bootyManage.getBootyList().get(x);
            b.moveY();
            canvas.drawBitmap(b.getBitmap(), b.getX(), b.getY(), paint);
            if(b.getY()>windowHeight || !b.isHave()){
                bootyManage.removeBooby(x);
            }
        }
    }

    /**
     * 绘制敌军
     */
    private void drawEnemy(Canvas canvas) {

        Enemy enemy = null;
        if (frame%200 == 0) {
            enemy = enemyManage.newEnemy(EnemyManage.TYPE_BIG);
            enemy.setHp(10);
        }else if(frame%100 == 0){
            enemy = enemyManage.newEnemy(EnemyManage.TYPE_MIDDLE);
            enemy.setHp(4);
        }else if(frame%30 == 0){
            enemy = enemyManage.newEnemy(EnemyManage.TYPE_SMALL);
            enemy.setHp(2);
        }
        if(enemy != null){
            enemy.setX(random.nextInt(windowWidth - enemy.getWidth()));
            enemy.setY(-enemy.getHeight());
        }

        for (int x = 0; x < enemyManage.getEnemyList().size(); x++) {
            Enemy e = enemyManage.getEnemyList().get(x);
            e.moveY();
            canvas.drawBitmap(e.getBitmap(), e.getX(), e.getY(), paint);
        }

        for (int x = 0; x < enemyManage.getEnemyList().size(); x++){
            Enemy e = enemyManage.getEnemyList().get(x);
            if (e.getY() > windowHeight) {
                enemyManage.removeEnemy(x);
            }
        }
    }

    /**
     * 绘制子弹
     */
    private void drawBullet(Canvas canvas) {

        if(my.isHave()){
            if(bulletType==Bullet.TYPE_DOUBLE && bulletStateFrame>0){
                bulletStateFrame--;
            }else if(bulletStateFrame <= 0){
                bulletType = Bullet.TYPE_SINGLE;
                bulletStateFrame = 0;
            }
            if(frame%6 == 0){
                Bullet bullet = bulletManage.newBullet(bulletType);
                if(bulletType == Bullet.TYPE_SINGLE){
                    bullet.setX(my.getX() + my.getWidth() / 2 - bullet.getWidth() / 2);
                    bullet.setY(my.getY() - bullet.getHeight());
                    bullet.setHp(1);
                }else if(bulletType == Bullet.TYPE_DOUBLE){
                    bullet.setX(my.getX() + my.getWidth() / 2 - dp10);
                    bullet.setY(my.getY() - bullet.getHeight());
                    bullet.setHp(1);

                    Bullet bullet2 = bulletManage.newBullet(bulletType);
                    bullet2.setX(my.getX() + my.getWidth() / 2 + dp10);
                    bullet2.setY(my.getY() - bullet2.getHeight());
                    bullet2.setHp(1);
                    canvas.drawBitmap(bullet2.getBitmap(), bullet2.getX(), bullet2.getY(), paint);
                }
                canvas.drawBitmap(bullet.getBitmap(), bullet.getX(), bullet.getY(), paint);
            }
            for (int x=0; x< bulletManage.getBulletList().size(); x++){
                Bullet bullet = bulletManage.getBulletList().get(x);
                bullet.moveY();
                canvas.drawBitmap(bullet.getBitmap(), bullet.getX(), bullet.getY(), paint);
            }
            for (int x=0; x< bulletManage.getBulletList().size(); x++){
                Bullet bullet = bulletManage.getBulletList().get(x);
                if(bullet.getY()<0 || !bullet.isHave()) {
                    bulletManage.removeBullet(x);
                }
            }
        }
    }

    /**
     * 绘制自己
     */
    private void drawMy(Canvas canvas) {

        if(my.isHave()) {
            canvas.drawBitmap(my.getBitmap(), my.getX(), my.getY(), paint);
        }else{
            if(myExplosion!=null && myExplosion.getLevel() >= 14){
                flag = false;
                if(onGameOverListener != null){
                    onGameOverListener.onGameOver(score);
                }
            }
        }
    }

    public void resetMy(){

        flag = true;
        my.resetHave();
        my.setX(windowWidth / 2 - my.getWidth() / 2);
        my.setY(windowHeight - dp10 * 10);
        invalidate();
    }
}