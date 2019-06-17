package com.powerrich.corelib.utils.singclick;

import android.view.View;

/**
 * @author AlienChao
 * @date 2018/12/10 15:15
 */
public abstract class CustomClickListener implements View.OnClickListener {

    private long mLastClickTime;
    private long timeInterval = 1000L;

    public CustomClickListener() {

    }

    public CustomClickListener(long interval) {
        this.timeInterval = interval;
    }

    @Override
    public void onClick(View v) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastClickTime > timeInterval) {
            // 单次点击事件
            onClick();
            mLastClickTime = nowTime;
        }
    }

    protected abstract void onClick();


}
