package com.powerrich.corelib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;




public class LoadingView extends View {
    private Paint loadPaint;
    private Paint paint;
    private String text = "今日头条";
    private float percent;
    private Handler handler = new Handler();
    public LoadingView(Context context) {
        this(context,null);
    }
    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        setMeasuredDimension(bounds.width(),bounds.height());
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(Color.parseColor("#999999"));
        paint.setTextSize(60);

        loadPaint = new Paint();
        loadPaint.setStyle(Paint.Style.FILL);
        loadPaint.setColor(0x70ffffff); //0x70ffffff
    }
    @Override
    protected void onDraw(Canvas canvas) {
        drawText(canvas);
        drawLine(canvas);
    }

    private void drawLine(Canvas canvas) {
        if(null==canvas){
            return;
        }
        canvas.save();
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        Rect rect = new Rect(0, 0, (int) (bounds.width()*percent), bounds.height());
        canvas.clipRect(rect);
        canvas.drawRect(rect,loadPaint);
        canvas.restore();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(percent>=1.0){
                    percent=0;
                }else{
                    percent+=0.05f;
                }
                postInvalidate();
            }
        },200);
    }

    private void drawText(Canvas canvas) {
        if(null==canvas){
            return;
        }
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        canvas.drawText(text, getWidth() / 2 - paint.measureText(text) / 2,
                getHeight() / 2 - (fm.bottom + fm.top) / 2, paint);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        if(visibility==View.VISIBLE){
            percent+=0.05f;
            invalidate();
        }
    }
}

