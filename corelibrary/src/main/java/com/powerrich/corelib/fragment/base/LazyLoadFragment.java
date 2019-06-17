package com.powerrich.corelib.fragment.base;

import android.os.Bundle;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/22
 * 懒加载 fragment
 */

public abstract class LazyLoadFragment  extends BaseFragment {
    protected boolean isViewInitiated;
    protected boolean isDataLoaded;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareRequestData(false);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        prepareRequestData(false);
    }

    public boolean prepareRequestData(boolean forceUpdate) {
        if (getUserVisibleHint() && isViewInitiated && (!isDataLoaded || forceUpdate)) {
            loadData();
            isDataLoaded = true;
            return true;
        }
        return false;
    }

    /**
     *  继承该方法，然后
     */
    protected abstract void loadData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isDataLoaded = false;
    }
}