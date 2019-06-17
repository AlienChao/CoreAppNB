package com.powerrich.corelib.http;


import com.powerrich.corelib.view.dialog.LoadingDialog;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * 主线程切换
 */
public class RxSchedulers {

    public static <T> ObservableTransformer<T, T> io_main() {
//        return upstream ->
//                upstream.subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());


        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {

                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 带有dialog的异步流程
     *
     * @param dialog
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> loadingDialog(final LoadingDialog dialog) {
        dialog.show();
//        return upstream -> upstream.doOnTerminate(() -> {
//            dialog.dismiss();
//        });

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.doOnTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        dialog.dismiss();
                    }
                });
            }
        };
    }
}
