package com.dong.dscustomview.customsuperman;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.LoginFilter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.dong.dscustomview.R;

/**
 * Created by Dong on 2017/9/5.
 */

public class CustomSuperManView extends View implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {

    private Paint paint;
    private Bitmap backBitmap;
    private Bitmap superMan;
    private ValueAnimator animator = ValueAnimator.ofInt(0, 100);
    private ValueAnimator superman_animator1 = ValueAnimator.ofInt(-50, 0);
    private ValueAnimator superman_animator2 = ValueAnimator.ofInt(0, -20);
    private ValueAnimator superman_animator3 = ValueAnimator.ofInt(-20, 0);
    private ValueAnimator superman_animator4 = ValueAnimator.ofInt(0, -50);

    private int mPercent;//背景移动百分比
    private int sPercent;//超人移动百分比


    public CustomSuperManView(Context context) {
        super(context);
    }

    public CustomSuperManView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSuperManView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wx_20170905133931);
        superMan = BitmapFactory.decodeResource(getResources(), R.drawable.wx_20170905143149);

        //背景移动动画
        animator.setDuration(800);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        //超人飞行动画阶段一
        superman_animator1.setDuration(1500);
        //超人飞行动画阶段二
        superman_animator2.setDuration(300);
        superman_animator2.setRepeatCount(2);
        superman_animator2.setRepeatMode(ValueAnimator.REVERSE);
        //超人飞行动画阶段三
        superman_animator3.setDuration(1000);
        superman_animator3.setInterpolator(new LinearInterpolator());
        superman_animator3.setRepeatCount(0);
        superman_animator3.setRepeatMode(ValueAnimator.REVERSE);
        //超人飞行动画阶段四
        superman_animator4.setDuration(1500);

        //初始化动画监听
        animator.addUpdateListener(this);
        superman_animator1.addListener(this);
        superman_animator2.addListener(this);
        superman_animator3.addListener(this);
        superman_animator4.addListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //获取canvas和bitmap百分比，使背景图填充整个canvas
        float dx = (float)canvas.getWidth()/(float)backBitmap.getWidth();
        float dy = (float)canvas.getHeight()/(float)backBitmap.getHeight();

        //绘制背景
        canvas.save();
        canvas.scale(dx, dy, (float)canvas.getWidth()/2, (float)canvas.getHeight()/2);
        canvas.drawBitmap(backBitmap,(canvas.getWidth()-backBitmap.getWidth())/2-backBitmap.getWidth()*mPercent/100 ,(canvas.getHeight()-backBitmap.getHeight())/2,paint);
        canvas.drawBitmap(backBitmap,(canvas.getWidth()-backBitmap.getWidth())/2-backBitmap.getWidth()*mPercent/100 + backBitmap.getWidth(),(canvas.getHeight()-backBitmap.getHeight())/2,paint);
        canvas.restore();

        dx = (float)(canvas.getWidth()/2)/(float)superMan.getWidth();
        dy = dx;
        //绘制超人
        canvas.save();
        canvas.scale(dx, dy, (float)canvas.getWidth()/2, (float)canvas.getHeight()/2);
        canvas.drawBitmap(superMan,(canvas.getWidth()-superMan.getWidth()*2)/2+superMan.getWidth()*sPercent/100 ,(canvas.getHeight()-superMan.getHeight())/2,paint);
        canvas.restore();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        if (animation == this.animator){
            int percent = (int) animation.getAnimatedValue();
            mPercent = percent;

            if(superman_animator1.isRunning()){
                sPercent = (int) superman_animator1.getAnimatedValue();
            }else if(superman_animator2.isRunning()){
                sPercent = (int) superman_animator2.getAnimatedValue();
            }else if(superman_animator3.isRunning()){
                sPercent = (int) superman_animator3.getAnimatedValue();
            }else if(superman_animator4.isRunning()){
                sPercent = (int) superman_animator4.getAnimatedValue();
            }

            invalidate();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animator.start();
        superman_animator1.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.end();
        superman_animator1.end();
        superman_animator2.end();
        superman_animator3.end();
        superman_animator4.end();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if(animation == superman_animator1){
            superman_animator2.start();
        }else if(animation == superman_animator2){
            superman_animator3.start();
        }else if(animation == superman_animator3){
            superman_animator4.start();
        }else if(animation == superman_animator4){
            superman_animator1.start();
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
