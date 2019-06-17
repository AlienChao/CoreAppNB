package com.example.demolibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * @author AlienChao
 * @date 2018/12/04 11:27
 */

@SuppressLint("AppCompatCustomView")
public class CcCountDownView extends TextView implements Runnable{
    /**
     * 当前秒数
     */
    private int currentTime ;
    /**
     * 时间单位
     */
    private String until = "s";
    /**
     * 倒计时长
     */
    private int totalTime=10;
    //是否要重新执行
    private boolean isFlag =false;
    //当前文字
    private CharSequence currentStr;
    /**
     * 当不允许点击时显示的文字  （紧跟在倒计时后面的）
     */
    private String hintStr = "重新获取";

    public CcCountDownView(Context context) {

        this(context, null);
        Log.i("jsc", "CcCountDownView:11 ");
    }

    public CcCountDownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CcCountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i("jsc", "CcCountDownView: ");
        Resources resources = getResources();
        Drawable drawable = resources.getDrawable(R.drawable.valicode_selecter_shap);
        drawable.setBounds(0,0,getMinimumWidth(),getMinimumHeight());
//        setCompoundDrawables(drawable,null,null,null);
        setBackground(drawable);
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
    public void reSet(){
        isFlag =true;
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
        if(currentTime<=0||isFlag){
            setEnabled(true);
            isFlag =false;
            currentTime=totalTime;
            setText(currentStr);
        }else{
            String text  =currentTime+"秒"+hintStr;
            SpannableString spannableString = new SpannableString(text);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ea7805")), 0, String.valueOf(currentTime).length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(spannableString);
            currentTime--;
            postDelayed(this,1000);
        }

    }
}
