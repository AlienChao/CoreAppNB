package com.powerrich.corelib.manager;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.powerrich.corelib.http.RxSchedulers;
import com.powerrich.corelib.http.exception.ExceptionHandle;
import com.powerrich.corelib.http.intercepter.UploadRequestBody;
import com.powerrich.corelib.msg.ProgressMsg;
import com.powerrich.corelib.view.dialog.LoadingDialog;
import com.powerrich.corelib.view.dialog.ProgressBarDialog;
import com.threshold.rxbus2.RxBus;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/30
 * 版权：
 * <p>
 * 上传类， start()启动下载，  close() 关闭相关资源
 */
public class UploadManager {


    //等待框 dialog
    public static final int LOADING_DIALOG = 0x001;
    //进度条框 dilog
    public static final int PROGRESS_DIALOG = 0x002;


    private Context mContext;
    //API返回结果
    private Observable<ResponseBody> mObservable;
    //下载后写入的文件
    private File mFile;

    Disposable subscribe = null;
    private ProgressBarDialog progressBarDialog;
    private LoadingDialog loadDialog;
    private UploadCallback mCallBack;
    private int mShowType;


    /**
     * 下载 带有用进度条等待框
     *
     * @param context    上下文
     * @param observable API返回结果
     */
    public UploadManager(Context context, Observable<ResponseBody> observable, int showType) {
        mContext = context;
        mObservable = observable;
        this.mShowType = showType;
        if (mShowType == PROGRESS_DIALOG) {
            progressBarDialog = progressBarDialog = new ProgressBarDialog(context);
            progressBarDialog.getCircleProgressBar().setCurProgress(0);
        } else if (mShowType == LOADING_DIALOG) {
            loadDialog = new LoadingDialog(context);
        }

    }


    /**
     * 设置下载回调
     *
     * @param callBack 这个在启动之前设置
     */
    public void setCallBack(UploadCallback callBack) {
        this.mCallBack = callBack;
    }

    /**
     * 启动下载
     */
    public void start() {
        if (mShowType == PROGRESS_DIALOG) {
            registerProgress();
            progressBarDialog.show();
        } else if (mShowType == LOADING_DIALOG) {
            loadDialog.show();
            loadDialog.setCanceledOnTouchOutside(false);
            loadDialog.setCancelable(false);
        } else {
            LogUtils.e("dialog类型不匹配");
            return;
        }
        mObservable
                .compose(RxSchedulers.io_main())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        mCallBack.success();
                    }

                    @Override
                    public void onError(Throwable e) {
                        closeDialog();
                        ExceptionHandle.ResponeThrowable throwable = ExceptionHandle.handleException(e);
                        ToastUtils.showShort(throwable.message);
                        mCallBack.fail();
                    }

                    @Override
                    public void onComplete() {
                        closeDialog();
                    }
                });
    }

    //注册进度条
    private void registerProgress() {
        if (subscribe == null) {
            subscribe = RxBus.getDefault().ofType(ProgressMsg.class).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ProgressMsg>() {
                @Override
                public void accept(ProgressMsg progressMsg) throws Exception {
                    if(UploadRequestBody.UPLOAD_KEY.equals(progressMsg.getKey())){

                        long cs = progressMsg.getProgress();
                        final long size = progressMsg.getSize();
                        float resaut = (float) cs / size;
                        progressBarDialog.getCircleProgressBar().setCurProgress((int) (resaut * 100 + 0.5));
                    }
                }
            });
        }
    }

    private void closeDialog() {
        if (progressBarDialog != null)
            progressBarDialog.dismiss();
        if (loadDialog != null)
            loadDialog.dismiss();
    }

    /**
     * 关闭下载相关文件
     */
    public void close() {
        if (subscribe != null)
            subscribe.dispose();
        closeDialog();
        mContext = null;
    }


    public interface UploadCallback {
        public void success();

        public void fail();
    }
}
