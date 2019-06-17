package com.powerrich.corelib.app;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.blankj.utilcode.util.Utils;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/22
 * 版权：
 */

public class BaseApplication extends Application {


    public static BaseApplication mBaseApplication;


    /**
     * 这个最先执行
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        Utils.init(this);
    }


    /**
     * 程序启动的时候执行
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication = this;
        BaseLifecycleCallback.getInstance().init(this);

    }


    /**
     * 低内存的时候执行
     */
    @Override
    public void onLowMemory() {
        Log.d("Application", "onLowMemory");
        super.onLowMemory();
    }


    /**
     * HOME键退出应用程序
     * 程序在内存清理的时候执行
     */
    @Override
    public void onTrimMemory(int level) {
        Log.d("Application", "onTrimMemory");
        super.onTrimMemory(level);
    }


    /**
     * onConfigurationChanged
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d("Application", "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

}