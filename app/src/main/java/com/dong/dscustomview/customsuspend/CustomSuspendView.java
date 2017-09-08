package com.dong.dscustomview.customsuspend;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by Dong on 2017/9/8.
 */

public class CustomSuspendView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF rectF;
    private int lw;//分割线宽度
    private int cw;//画布宽度
    private int ch;//画布高度
    private int index;
    private int newIndex;
    private int oldIndex;
    private float degree;
    private final int TYPE_LEFT = 0;
    private final int TYPE_TOP = 1;
    private final int TYPE_RIGHT = 2;
    private final int TYPE_BOTTOM = 3;
    private final int TYPE_NONE = 4;

    private ObjectAnimator animator = ObjectAnimator.ofFloat(this, "degree", 90, 0);

    public void setDegree(float degree) {
        this.degree = degree;
        invalidate();
    }

    public CustomSuspendView(Context context) {
        super(context);
    }

    public CustomSuspendView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSuspendView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        cw = canvas.getWidth();
        ch = canvas.getHeight();
        lw = cw/648;

        drawGreenSquare(canvas);
        drawRedSquare(canvas);
    }

    private void drawRedSquare(Canvas canvas) {
        mPaint.setColor(Color.RED);

        drawRedSuspendSquare(canvas, newIndex, false);

        if(oldIndex!=newIndex)
            drawRedSuspendSquare(canvas, oldIndex, true);
    }

    private void drawGreenSquare(Canvas canvas) {

        mPaint.setColor(Color.GREEN);

        rectF = new RectF(lw, lw, cw/3-lw, ch/3-lw);
        canvas.drawRoundRect( rectF ,10, 10, mPaint);

        rectF = new RectF(cw/3+lw, lw, cw*2/3-lw, ch/3-lw);
        canvas.drawRoundRect( rectF ,10, 10, mPaint);

        rectF = new RectF(cw*2/3+lw, lw, cw-lw, ch/3-lw);
        canvas.drawRoundRect( rectF ,10, 10, mPaint);

        rectF = new RectF(lw, ch/3+lw, cw/3-lw, ch*2/3-lw);
        canvas.drawRoundRect( rectF ,10, 10, mPaint);

        rectF = new RectF(cw/3+lw, ch/3+lw, cw*2/3-lw, ch*2/3-lw);
        canvas.drawRoundRect( rectF ,10, 10, mPaint);

        rectF = new RectF(cw*2/3+lw, ch/3+lw, cw-lw, ch*2/3-lw);
        canvas.drawRoundRect( rectF ,10, 10, mPaint);

        rectF = new RectF(lw, ch*2/3+lw, cw/3-lw, ch-lw);
        canvas.drawRoundRect( rectF ,10, 10, mPaint);

        rectF = new RectF(cw/3+lw, ch*2/3+lw, cw*2/3-lw, ch-lw);
        canvas.drawRoundRect( rectF ,10, 10, mPaint);

        rectF = new RectF(cw*2/3+lw, ch*2/3+lw, cw-lw, ch-lw);
        canvas.drawRoundRect( rectF ,10, 10, mPaint);    }

    private void drawRedSuspendSquare(Canvas canvas, int index, boolean reverse){

        RectF rectF = null;
        switch (index){
            case 1:
                rectF = new RectF(lw, lw, cw/3-lw, ch/3-lw);
                break;
            case 2:
                rectF = new RectF(cw/3+lw, lw, cw*2/3-lw, ch/3-lw);
                break;
            case 3:
                rectF = new RectF(cw*2/3+lw, lw, cw-lw, ch/3-lw);
                break;
            case 4:
                rectF = new RectF(lw, ch/3+lw, cw/3-lw, ch*2/3-lw);
                break;
            case 5:
                rectF = new RectF(cw/3+lw, ch/3+lw, cw*2/3-lw, ch*2/3-lw);
                break;
            case 6:
                rectF = new RectF(cw*2/3+lw, ch/3+lw, cw-lw, ch*2/3-lw);
                break;
            case 7:
                rectF = new RectF(lw, ch*2/3+lw, cw/3-lw, ch-lw);
                break;
            case 8:
                rectF = new RectF(cw/3+lw, ch*2/3+lw, cw*2/3-lw, ch-lw);
                break;
            case 9:
                rectF = new RectF(cw*2/3+lw, ch*2/3+lw, cw-lw, ch-lw);
                break;
            default:
                return;
        }

        int dictionary = getDictionary();
        Log.i("TAG", "Dir:"+dictionary);

        canvas.save();
        Camera camera = new Camera();
        camera.save();

        if(!reverse) {
            switch (dictionary) {
                case TYPE_LEFT:
                    camera.rotateY(degree);
                    canvas.translate(rectF.left, rectF.centerY());
                    camera.applyToCanvas(canvas);
                    canvas.translate(-rectF.left, -rectF.centerY());
                    break;
                case TYPE_RIGHT:
                    camera.rotateY(-degree);
                    canvas.translate(rectF.right, rectF.centerY());
                    camera.applyToCanvas(canvas);
                    canvas.translate(-rectF.right, -rectF.centerY());
                    break;
                case TYPE_TOP:
                    camera.rotateX(-degree);
                    canvas.translate(rectF.centerX(), rectF.top);
                    camera.applyToCanvas(canvas);
                    canvas.translate(-rectF.centerX(), -rectF.top);
                    break;
                case TYPE_BOTTOM:
                    camera.rotateX(degree);
                    canvas.translate(rectF.centerX(), rectF.bottom);
                    camera.applyToCanvas(canvas);
                    canvas.translate(-rectF.centerX(), -rectF.bottom);
                    break;
                case TYPE_NONE:
                    canvas.scale((90-degree)/90,(90-degree)/90,rectF.centerX(), rectF.centerY());
                    break;
            }
        }else{
            switch (dictionary){
                case TYPE_LEFT:
                    camera.rotateY(-90+degree);
                    canvas.translate(rectF.right, rectF.centerY());
                    camera.applyToCanvas(canvas);
                    canvas.translate(-rectF.right, -rectF.centerY());
                    break;
                case TYPE_RIGHT:
                    camera.rotateY(90-degree);
                    canvas.translate(rectF.left, rectF.centerY());
                    camera.applyToCanvas(canvas);
                    canvas.translate(-rectF.left, -rectF.centerY());
                    break;
                case TYPE_TOP:
                    camera.rotateX(90-degree);
                    canvas.translate(rectF.centerX(), rectF.bottom);
                    camera.applyToCanvas(canvas);
                    canvas.translate(-rectF.centerX(), -rectF.bottom);
                    break;
                case TYPE_BOTTOM:
                    camera.rotateX(-90+degree);
                    canvas.translate(rectF.centerX(), rectF.top);
                    camera.applyToCanvas(canvas);
                    canvas.translate(-rectF.centerX(), -rectF.top);
                    break;
                case TYPE_NONE:
                    canvas.scale(degree/90,degree/90,rectF.centerX(), rectF.centerY());
                    break;
            }
        }

        camera.restore();

        canvas.drawRoundRect( rectF ,10, 10, mPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                getLocation(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                getLocation(x, y);
                break;
            case MotionEvent.ACTION_UP:
                index = 0;
                break;
        }

        if(newIndex != index){
            if(newIndex!=0)
                oldIndex = newIndex;
            newIndex = index;

            animator.start();
        }
        return true;
    }

    private void getLocation(float x, float y) {
        if(x>=lw && x<=cw/3-lw){
            if(y>=lw && y<=ch/3-lw){
                index = 1;
            }else if(y>=ch/3+lw && y<=ch*2/3-lw){
                index = 4;
            }else if(y>=ch*2/3+lw && y<=ch-lw){
                index = 7;
            }else{
                index = 0;
            }
        }else if(x>=cw/3+lw && x<=cw*2/3-lw){
            if(y>=lw && y<=ch/3-lw){
                index = 2;
            }else if(y>=ch/3+lw && y<=ch*2/3-lw){
                index = 5;
            }else if(y>=ch*2/3+lw && y<=ch-lw){
                index = 8;
            }else{
                index = 0;
            }
        }else if(x>=cw*2/3+lw && x<=cw-lw){
            if(y>=lw && y<=ch/3-lw){
                index = 3;
            }else if(y>=ch/3+lw && y<=ch*2/3-lw){
                index = 6;
            }else if(y>=ch*2/3+lw && y<=ch-lw){
                index = 9;
            }else{
                index = 0;
            }
        }else{
            index = 0;
        }
    }

    private int getDictionary(){
        if(oldIndex!=0){
            switch (index-oldIndex){
                case 1:
                    return TYPE_LEFT;
                case -1:
                    return TYPE_RIGHT;
                case 3:
                    return TYPE_TOP;
                case -3:
                    return TYPE_BOTTOM;
                default:
                    return TYPE_NONE;
            }
        }else{
            return TYPE_NONE;
        }
    }
}
