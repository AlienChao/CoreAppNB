package com.powerrich.corelib.manager;

import android.content.Context;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.powerrich.corelib.http.exception.ExceptionHandle;
import com.powerrich.corelib.http.intercepter.DownloadResponseBody;
import com.powerrich.corelib.msg.ProgressMsg;
import com.powerrich.corelib.view.dialog.LoadingDialog;
import com.powerrich.corelib.view.dialog.ProgressBarDialog;
import com.threshold.rxbus2.RxBus;

import java.io.File;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/30
 * 版权：
 *
 * 下载类， start()启动下载，  close() 关闭相关资源
 *
 */
public class DownloadManager {


    private Context mContext;
    //API返回结果
    private Observable<ResponseBody> mObservable;
    //文件大小
    private long size;
    //下载后写入的文件
    private File mFile;

    Disposable subscribe = null;
    private ProgressBarDialog progressBarDialog;
    private LoadingDialog loadDialog;
    private DownloadCallback mCallBack;


    /**
     * 下载 没有用进度条等待框
     * @param context  上下文
     * @param observable API返回结果
     * @param file 下载后写入的文件
     */
    public DownloadManager(Context context, Observable<ResponseBody> observable, File file) {
       this(context,observable,0,file);

    }


    /**
     * 下载 带有用进度条等待框
     * @param context  上下文
     * @param observable API返回结果
     * @param size 文件大小
     * @param file 下载后写入的文件
     */
    public DownloadManager(Context context, Observable<ResponseBody> observable, long size, File file) {
        mContext = context;
        mObservable = observable;
        this.size = size;
        mFile = file;
        if(size > 0){
            progressBarDialog =  progressBarDialog = new ProgressBarDialog(context);
            progressBarDialog.getCircleProgressBar().setCurProgress(0);
        }else{
            loadDialog = new LoadingDialog(context);
        }

    }


    /**
     * 设置下载回调
     * @param callBack
     *
     * 这个在启动之前设置
     */
    public void setCallBack(DownloadCallback callBack){
        this.mCallBack = callBack;
    }

    /**
     * 启动下载
     */
    public void start(){
        if(size > 0){
            registerProgress();
            progressBarDialog.show();
        }else{
            loadDialog.show();
            loadDialog.setCanceledOnTouchOutside(false);
            loadDialog.setCancelable(false);
        }

        mObservable.subscribeOn(Schedulers.io())
                .map(new Function<ResponseBody, InputStream>() {
                    @Override
                    public InputStream apply(ResponseBody responseBody) throws Exception {
                        return responseBody.byteStream();
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<InputStream>() {
                    @Override
                    public void accept(InputStream inputStream) throws Exception {
                        FileUtils.createFileByDeleteOldFile(mFile);
                        FileIOUtils.writeFileFromIS(mFile, inputStream);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InputStream>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(InputStream inputStream) {
                        mCallBack.success();
                    }

                    @Override
                    public void onError(Throwable e) {
                        closeDialog();
                        ExceptionHandle.ResponeThrowable throwable = ExceptionHandle.handleException(e);
                        ToastUtils.showShort( throwable.message);
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
                    if(DownloadResponseBody.DOWN_LOAD_KEY.equals(progressMsg.getKey())){
                        long cs = progressMsg.getProgress();
                        float resaut = (float) cs / size;
                        progressBarDialog.getCircleProgressBar().setCurProgress((int) (resaut * 100 + 0.5));
                    }
                }
            });
        }
    }

    private void closeDialog(){
        if (progressBarDialog != null)
            progressBarDialog.dismiss();
        if(loadDialog != null)
            loadDialog.dismiss();
    }

    /**
     * 关闭下载相关文件
     */
    public void close(){
        if(subscribe != null)
            subscribe.dispose();
        closeDialog();
        mContext = null;
    }


    public interface DownloadCallback{
        public void success();
        public void fail();
    }
}
