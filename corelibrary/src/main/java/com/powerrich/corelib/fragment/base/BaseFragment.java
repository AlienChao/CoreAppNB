package com.powerrich.corelib.fragment.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.powerrich.corelib.R;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/16
 * 版权：
 */

public abstract class BaseFragment extends Fragment {

    private LayoutInflater mLayoutInflater;
    protected Context mContext;
    FrameLayout rootView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        this.mLayoutInflater = inflater;
        rootView = (FrameLayout) inflater.inflate(R.layout.fragment_base, null);
        //添加子类 内容区域
        View view = onCreateContentView();
        if (view != null){
            rootView.addView(view);
            bindViewAfter();
        }

        return rootView;
    }

    /**
     * 给子类实现的内容区域
     */
    protected abstract View onCreateContentView() ;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @SuppressLint("RestrictedApi")
    protected View inflateContentView(int resId) {
        View view = getLayoutInflater(null).inflate(resId, rootView, false);
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    /**
     * 绑定数据之后调用，自己继承
     */
    protected void bindViewAfter(){

    }

}
