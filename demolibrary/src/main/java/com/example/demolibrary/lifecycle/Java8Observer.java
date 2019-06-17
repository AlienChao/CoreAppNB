package com.example.demolibrary.lifecycle;

import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;
import android.util.Log;



/**
 * @author AlienChao
 * @date 2018/12/10 15:30
 */
public class Java8Observer implements LifecycleObserver {
    private static final String TAG = Java8Observer.class.getSimpleName();
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() { Log.d(TAG, "onCreate"); }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() { Log.d(TAG, "onStart"); }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() { Log.d(TAG, "onResume"); }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() { Log.d(TAG, "onPause"); }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() { Log.d(TAG, "onStop"); }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() { Log.d(TAG, "onDestroy"); }


}
