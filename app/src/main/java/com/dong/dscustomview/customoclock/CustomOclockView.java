package com.dong.dscustomview.customoclock;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.Calendar;


/**
 * Created by Dong on 2017/9/6.
 */

public class CustomOclockView extends View implements ValueAnimator.AnimatorUpdateListener {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Calendar calendar = Calendar.getInstance();
    private ValueAnimator animator;
    private float degree;

    public CustomOclockView(Context context) {
        super(context);
    }

    public CustomOclockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomOclockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    {
        int initSecond = calendar.get(Calendar.SECOND);
        animator = ValueAnimator.ofFloat(initSecond, initSecond+60);
        animator.setDuration(1000*60);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.addUpdateListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float cw = canvas.getWidth();
        float ch = canvas.getHeight();

        //画表盘
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#1fe947"));
        paint.setStrokeWidth(3);
        canvas.drawCircle(cw/2, ch/2, cw/6, paint);

        //刻盘
        paint.setTextSize(cw/72);
        for (int i = 0; i < 60; i++) {
            canvas.save();
            canvas.rotate(6*i, cw/2, ch/2);
            if(i%15 == 0){
                paint.setStrokeWidth(5);
                canvas.drawLine(cw/2, ch/2-cw/6, cw/2, ch/2-cw/6+cw/60, paint);
            } else if(i%5 == 0) {
                paint.setStrokeWidth(2);
                canvas.drawLine(cw / 2, ch / 2 - cw / 6, cw / 2, ch / 2 - cw / 6 + cw / 72, paint);
            }else{
                paint.setStrokeWidth(1);
                canvas.drawLine(cw/2, ch/2-cw/6, cw/2, ch/2-cw/6+cw/144, paint);
            }
            canvas.restore();
        }

        //中心点
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cw/2, ch/2, 8, paint);

        //秒针
        calendar = Calendar.getInstance();
        int second = calendar.get(Calendar.SECOND);

        paint.setStrokeWidth(1);
        canvas.save();
        canvas.rotate(6*degree, cw/2, ch/2);
        canvas.drawLine(cw/2, ch/2-cw/6+cw/36, cw/2, ch/2, paint);
        canvas.restore();

        //分针
        int minute = calendar.get(Calendar.MINUTE);

        paint.setStrokeWidth(3);
        canvas.save();
        canvas.rotate((float) (6*minute+0.1*second), cw/2, ch/2);
        canvas.drawLine(cw/2, ch/2-cw/6+cw/21, cw/2, ch/2, paint);
        canvas.restore();

        //时针
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        paint.setStrokeWidth(5);
        canvas.save();
        canvas.rotate((float) (30*hour+0.5*minute+0.01*second), cw/2, ch/2);
        canvas.drawLine(cw/2, ch/2-cw/6+cw/12, cw/2, ch/2, paint);
        canvas.restore();

        //波浪
        Shader shader = new RadialGradient(cw/2, ch/2, (cw/6)*((degree*10)%30)/30+1, Color.parseColor("#001fe947"), Color.parseColor("#201fe947"), Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawCircle(cw/2, ch/2, (cw/6)*((degree*10)%30)/30, paint);
        Log.i("TAG",second%5+"");
        paint.setShader(null);

        shader = new RadialGradient(cw/2, ch/2, (cw/6)*((degree*10+15)%30)/30+1, Color.parseColor("#001fe947"), Color.parseColor("#201fe947"), Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawCircle(cw/2, ch/2, (cw/6)*((degree*10+15)%30)/30, paint);
        Log.i("TAG",second%5+"");
        paint.setShader(null);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.end();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float value = (float) animation.getAnimatedValue();
        if(value>=60)
            degree = value -60;
        else
            degree = value;
        invalidate();
    }
}
