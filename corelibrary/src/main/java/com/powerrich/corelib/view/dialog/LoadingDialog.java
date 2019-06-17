package com.powerrich.corelib.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.powerrich.corelib.R;


public class LoadingDialog extends Dialog {

	public Context context;
	private TextView tvMeesage;
	
	private static LoadingDialog customProgressDialog = null;
	
	public LoadingDialog(Context context) {
		super(context, R.style.dialog);
		this.context = context;
		setContentView(R.layout.dialog_loading);
//		bindView();
	}

//	private void bindView() {
//		this.tvMeesage = (TextView)findViewById(R.id.tv_message);
//	}
	
//	public LoadingDialog setMessage(String message) {
//		this.tvMeesage.setText(message);
//		return customProgressDialog;
//	}

}

