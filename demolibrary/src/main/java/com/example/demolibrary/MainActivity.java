package com.example.demolibrary;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.demolibrary.lifecycle.Java8Observer;
import com.example.demolibrary.retrofit.BlogService;

import java.sql.Array;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;

public class MainActivity extends AppCompatActivity {

    private Button btnClick;
    private CcCountDownView countdown;
    private RelativeLayout rlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("jsc", "onCreate: " + getTaskId());
        initView();
        // 直接调用getLifecycle()，添加Observer
        getLifecycle().addObserver(new Java8Observer());
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://wanandroid.com/")
//                .build();
//        BlogService blogService = retrofit.create(BlogService.class);
        //  blogService.getBlog("AlienCc");
//        bolog.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.i("jsc", "onResponse: "+response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                  t.printStackTrace();
//                Log.i("jsc", "onFailure: "+t.getMessage());
//            }
//        });
    }

    private void initView() {
        btnClick = (Button) findViewById(R.id.btn_click);
        btnClick.setText("MainActivity");
        countdown = (CcCountDownView) findViewById(R.id.countdown);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        rlContent = (RelativeLayout) findViewById(R.id.rl_content);
        rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("jsc", "onClick: RelativeLayout");
            }
        });
    }
}
