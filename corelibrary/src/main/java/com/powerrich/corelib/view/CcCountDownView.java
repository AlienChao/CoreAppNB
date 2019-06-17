package com.powerrich.corelib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;


import com.powerrich.corelib.R;

/**
 * 倒计时View
 * @author AlienChao
 * @date 2018/12/04 11:27
 */


public class CcCountDownView extends android.support.v7.widget.AppCompatTextView implements Runnable {
    /**
     * 当前秒数
     */
    private int currentTime;
    /**
     * 时间单位
     */
    private String until = "s";
    /**
     * 倒计时长
     */
    private int totalTime = 60;
    //是否要重新执行
    private boolean isFlag = false;
    //当前文字
    private CharSequence currentStr;
    /**
     * 当不允许点击时显示的文字  （紧跟在倒计时后面的）
     */
    private String hintStr = "重新获取";

    private String hintStrColor = "#ea7805";

    public CcCountDownView(Context context) {
        this(context, null);
    }

    public CcCountDownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void getAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CountDown);
        //是否开启默认样式
        boolean aBoolean = array.getBoolean(R.styleable.CountDown_isShowDrawable, true);
        if (aBoolean) {
            Drawable drawable = getResources().getDrawable(R.drawable.countdown_selecter_shap);
            drawable.setBounds(0, 0, getMinimumWidth(), getMinimumHeight());
            setBackground(drawable);
        }
        //hint字符串颜色
        String CountDown_hintStrColor = array.getString(R.styleable.CountDown_hintStrColor);
        if (!TextUtils.isEmpty(CountDown_hintStrColor)) {
            hintStrColor = CountDown_hintStrColor;
        }
        //hint字符串
        String CountDown_hintStr = array.getString(R.styleable.CountDown_hintStr);
        if (!TextUtils.isEmpty(CountDown_hintStr)) {
            hintStr = CountDown_hintStr;
        }
        //倒计时 总时间
        int CountDown_totalTime = array.getInt(R.styleable.CountDown_totalTime,0);
        if (CountDown_totalTime!=0) {
            totalTime = CountDown_totalTime;
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public CcCountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttributes(context, attrs, defStyleAttr);

    }

    @Override
    protected void onAttachedToWindow() {
        setClickable(true);
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        //移除延迟任务 防止内存泄漏
        removeCallbacks(this);
        super.onDetachedFromWindow();
    }

    /**
     * 设置倒计时总秒数
     */
    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * 重置倒计时控件
     */
    public void reSet() {
        isFlag = true;
    }

    @Override
    public boolean performClick() {
        setEnabled(false);
        currentStr = getText();
        currentTime = totalTime;
        post(this);
        return super.performClick();
    }

    @Override
    public void run() {
        if (currentTime <= 0 || isFlag) {
            setEnabled(true);
            isFlag = false;
            currentTime = totalTime;
            setText(currentStr);
        } else {
            String text = currentTime + "秒" + hintStr;
            SpannableString spannableString = new SpannableString(text);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(hintStrColor)), 0, String.valueOf(currentTime).length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(spannableString);
            currentTime--;
            postDelayed(this, 1000);
        }

    }
}
