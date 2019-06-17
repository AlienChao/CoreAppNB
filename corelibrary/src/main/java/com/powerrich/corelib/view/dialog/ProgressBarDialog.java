package com.powerrich.corelib.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.powerrich.corelib.R;


/**
 * 文 件 名：ProgressBarDialog
 * 描   述：自定义进度条对话框
 * 作   者：Wangzheng
 * 时   间：2018-8-7 10:33:30
 * 版   权：v1.0
 */
public class ProgressBarDialog extends Dialog {

    private CircleProgressBar mCircleProgressBar;

    public CircleProgressBar getCircleProgressBar() {
        return mCircleProgressBar;
    }

    public void setCircleProgressBar(CircleProgressBar circleProgressBar) {
        mCircleProgressBar = circleProgressBar;
    }
    public ProgressBarDialog(Context context) {
        this(context, R.style.CustomProgressDialogStyle);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        View view = View.inflate(context, R.layout.layout_progress_dialog, null);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_layout);
        mCircleProgressBar = view.findViewById(R.id.progressBar);
        this.setContentView(layout, new LinearLayout.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, context.getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 160, context.getResources().getDisplayMetrics())));
    }
    public ProgressBarDialog(Context context, int theme) {
        super(context, theme);
    }
}
