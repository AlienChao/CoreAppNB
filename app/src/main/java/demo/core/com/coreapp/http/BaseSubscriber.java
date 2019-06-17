package demo.core.com.coreapp.http;


import com.blankj.utilcode.util.ToastUtils;
import com.powerrich.corelib.http.exception.ExceptionHandle;
import com.powerrich.corelib.view.StatusView;
import com.powerrich.corelib.view.dialog.LoadingDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import demo.core.com.coreapp.http.bean.BaseBean;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by fanliang on 18/1/3.
 */

public abstract class BaseSubscriber<T extends BaseBean> implements Observer<T> {

    private Disposable disposable;

    private RefreshLayout mRefreshLayout;
    //多状态View
    private StatusView mStatusView;
    //当前的页数，如果是第一页并且出现异常，那么弹出无内容界面
    private int mPage;

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }




    public BaseSubscriber() {
    }
    public BaseSubscriber(RefreshLayout refreshLayout) {
        this.mRefreshLayout = refreshLayout;
    }
    public BaseSubscriber(RefreshLayout refreshLayout, StatusView statusView) {
        this.mRefreshLayout = refreshLayout;
        this.mStatusView = statusView;
    }
    public BaseSubscriber(RefreshLayout refreshLayout, StatusView statusView,int page) {
        this.mRefreshLayout = refreshLayout;
        this.mStatusView = statusView;
        this.mPage = page;
    }


    private LoadingDialog mLoadingDialog;

    public BaseSubscriber(LoadingDialog dialog) {
        this.mLoadingDialog = dialog;
    }


    @Override
    public void onComplete() {
        if (mRefreshLayout != null) {
            mRefreshLayout.finishLoadmore();
            mRefreshLayout.finishRefresh();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }

        //如果是第一页，并且出现异常，那么现实无内容
        if(mPage == 1 && mStatusView != null){
            mStatusView.showEmpty();
        }

        ExceptionHandle.ResponeThrowable throwable = ExceptionHandle.handleException(e);
        ToastUtils.showShort(throwable.message);

        e.printStackTrace();
        onComplete();
    }

    @Override
    public void onNext(T t) {
        if (200 == t.getCode()) {
            result(t);
        } else if ("-403".equals(t.getCode())) {
        } else {
        }

    }

    public abstract void result(T t);

}
