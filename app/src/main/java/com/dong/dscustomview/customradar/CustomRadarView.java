package com.dong.dscustomview.customradar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.dong.dscustomview.R;

/**
 * Created by Dong on 2017/9/5.
 */

public class CustomRadarView extends View implements ValueAnimator.AnimatorUpdateListener{

    private Paint paint;
    private Bitmap backBitmap;
    private ValueAnimator animator;
    private int degree;
    private int pencent = 0;
    String color1 = "#ffffff";
    String color2 = "#1fe947";

    public CustomRadarView(Context context) {
        super(context);
    }

    public CustomRadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wx_20170905164519);

        animator = ValueAnimator.ofInt(0, 360);
        animator.setDuration(1000*3);
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

        //获取canvas和bitmap百分比，使背景图填充整个canvas
        float dx = cw/(float)backBitmap.getWidth();
        float dy = ch/(float)backBitmap.getHeight();

        //绘制背景
        paint.setColor(Color.parseColor("#1fe947"));
        canvas.save();
        canvas.scale(dx, dy, cw/2, ch/2);
        canvas.drawBitmap(backBitmap,(cw-backBitmap.getWidth())/2,(ch-backBitmap.getHeight())/2,paint);
        canvas.restore();

        //绘制罗盘(没设计师提供图片，只能自己画)
        canvas.drawLine(cw/3, ch/2, cw*2/3, ch/2, paint);
        canvas.drawLine(cw/2, ch/2-cw/6, cw/2, ch/2+cw/6, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        canvas.drawCircle(cw/2, ch/2, cw/6, paint);
        canvas.drawCircle(cw/2, ch/2, cw*2/15, paint);
        canvas.drawCircle(cw/2, ch/2, cw/10, paint);
        canvas.drawCircle(cw/2, ch/2, cw/15, paint);
        canvas.drawCircle(cw/2, ch/2, cw/30, paint);

        paint.setStyle(Paint.Style.FILL);
        Shader shader1 = new RadialGradient(cw/2, ch/2, cw/6, Color.parseColor("#601fe947"), Color.parseColor("#001fe947"), Shader.TileMode.CLAMP);
        paint.setShader(shader1);
        canvas.drawCircle(cw/2, ch/2, cw/6, paint);
        paint.setShader(null);

        //绘制扫描(还是没有设计师)
        canvas.save();
        Shader shader2 = new LinearGradient(cw*7/12,ch/2 + cw/9,cw*7/12, ch/2, Color.parseColor("#aa1ff947"), Color.parseColor("#001fe947"), Shader.TileMode.CLAMP);
        paint.setShader(shader2);
        canvas.rotate(degree, cw/2, ch/2);
        RectF rectF = new RectF(cw/3, ch/2-cw/6, cw*2/3, ch/2+cw/6);
        canvas.drawArc(rectF, 0, 45, true, paint);
        canvas.restore();
        paint.setShader(null);

        //绘制扫描到的光斑(还是没有设计师)
        color1 = "#"+getTran(degree, 15)+"ffffff";
        color2 = "#"+getTran(degree, 15)+"1fe947";
        Shader shader3 = new RadialGradient(cw*7/12, ch/2+cw/36, cw/144, Color.parseColor(color1), Color.parseColor(color2), Shader.TileMode.CLAMP);
        paint.setShader(shader3);
        canvas.drawCircle(cw*7/12, ch/2+cw/36, cw/144, paint);
        paint.setShader(null);

        color1 = "#"+getTran(degree, 60)+"ffffff";
        color2 = "#"+getTran(degree, 60)+"1fe947";
        shader3 = new RadialGradient(cw*39/72, ch/2+cw/13, cw/132, Color.parseColor(color1), Color.parseColor(color2), Shader.TileMode.CLAMP);
        paint.setShader(shader3);
        canvas.drawCircle(cw*39/72, ch/2+cw/13, cw/132, paint);
        paint.setShader(null);

        color1 = "#"+getTran(degree, 75)+"ffffff";
        color2 = "#"+getTran(degree, 75)+"1fe947";
        shader3 = new RadialGradient(cw*19/36, ch/2+cw/9, cw/144, Color.parseColor(color1), Color.parseColor(color2), Shader.TileMode.CLAMP);
        paint.setShader(shader3);
        canvas.drawCircle(cw*19/36, ch/2+cw/9, cw/144, paint);
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
        int value = (int) animation.getAnimatedValue();
        degree = value;
        invalidate();
    }

    /*
        获取光斑透明度
     */
    private String getTran(int degree, int angle){

        degree = (degree + 45)%360;
        degree -= angle;
        degree = 270 - degree;

        if(degree > 0 && degree < 270 ){
            degree = degree*16*16/270;
            return Integer.toHexString(degree/16) +""+Integer.toHexString(degree%16);
        }else{
            return "00";
        }
    }
}
