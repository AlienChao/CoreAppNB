package com.powerrich.corelib.activity.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.powerrich.corelib.R;
import com.powerrich.corelib.http.RxSchedulers;
import com.powerrich.corelib.utils.StatusBarUtils;
import com.powerrich.corelib.view.dialog.LoadingDialog;

import butterknife.ButterKnife;
import io.reactivex.Observable;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/16
 * 版权：
 */

public abstract class BaseActivity extends AppCompatActivity {


    protected LinearLayout rootLlt;
    protected FrameLayout contentFlt;
    protected View contentView;
    protected Activity mActivity;

    public RelativeLayout mRllBack, mRll;
    protected TextView mTvTitle, mTvRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(createView());
        StatusBarUtils.fitsSystemWindows(this,R.color.title_bar_color);
        Log.e("jsc", "BaseActivity-onCreate:");
    }
    @SuppressLint("ResourceType")
    protected View createView() {
        rootLlt = (LinearLayout) inflateContentView(R.layout.activity_base);
        contentFlt = rootLlt.findViewById(R.id.contentFlt);
        //引入一个常用的title
        View titleView = inflateContentView(R.layout.layout_title);
        mRllBack = (RelativeLayout) titleView.findViewById(R.id.rlt_back);
        mRll = (RelativeLayout) titleView.findViewById(R.id.llt);
        mTvTitle = (TextView) titleView.findViewById(R.id.tv_title);
        mTvRight = (TextView) titleView.findViewById(R.id.tv_right);
        rootLlt.addView(titleView, 0);

        //调用子类自己实现的布局页面
//        contentView = onCreateContentView();
        contentView = inflateContentView(onCreateContentView());
        if (contentView != null) {
            ButterKnife.bind(this, contentView);
            contentFlt.addView(contentView);
        }
        mActivity = this;
        return rootLlt;
    }

    /**
     * 取消FitSystemWindows属性，不暂用顶部systemBar
     */
    public void cancelFitSystemWindows(){
        StatusBarUtils.cancelSystemWindows(this);
    }

    /**
     * 获取状态栏高度
     * @param context context
     * @return 状态栏高度
     */
    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }


    /**
     * 替换掉默认的titleView
     */
    public void setTitleView(View view) {
        rootLlt.removeViewAt(0);
        rootLlt.addView(view, 0);
    }


    /**
     * 没有返回箭头的title
     */
    public void setTitle(String title) {
        mRllBack.setVisibility(View.GONE);
        mTvTitle.setText(title);
    }


    /**
     * 有返回箭头的title
     */
    public void setTitleBack(String title) {
        mRllBack.setVisibility(View.VISIBLE);
        mTvTitle.setText(title);
        mRllBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * 有箭头返回，右边是字符串
     */
    public void setTitleBackAndRightStr(String title, String rightStr, View.OnClickListener listener) {
        setTitleBack(title);
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText(rightStr);
        mTvRight.setOnClickListener(listener);
    }

    /**
     * 没有箭头返回，右边是图片
     */
    public void setTitleWithRightIv(String title, int ivRight, View.OnClickListener listener) {
        setTitleBack(title);
        mRllBack.setVisibility(View.GONE);
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText("");
        mTvRight.setBackgroundResource(ivRight);
        mTvRight.setOnClickListener(listener);
    }


    /**
     *
     */
    public View inflateContentView(int resId) {
        return getLayoutInflater().inflate(resId, contentFlt, false);
    }


    /**
     * 子类继承实现的布局
     */
    @SuppressLint("ResourceType")
    public abstract @IdRes int  onCreateContentView();


    /**
     * 执行http请求
     */
    public <T> Observable<T> exeHttp(Observable<T> observable) {
        return observable.compose(RxSchedulers.<T>io_main());
    }


    /**
     * 带有dialog的请求
     */
    public <T> Observable<T> exeHttpWithDialog(Observable<T> observable) {
        final LoadingDialog dialog = new LoadingDialog(this);
        return  exeHttp(observable).compose(RxSchedulers.<T>loadingDialog(dialog));
    }

}


