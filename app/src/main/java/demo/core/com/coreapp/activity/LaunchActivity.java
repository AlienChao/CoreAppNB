package demo.core.com.coreapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;


import demo.core.com.coreapp.R;
import yanzhikai.textpath.SyncTextPathView;
import yanzhikai.textpath.painter.FireworksPainter;

public class LaunchActivity extends AppCompatActivity {

    private SyncTextPathView atpvAs,atpv_as2;
    private RelativeLayout rl_content;
    Runnable runnable;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    private void initView() {

        atpvAs = (SyncTextPathView) findViewById(R.id.atpv_as);
        atpv_as2 = (SyncTextPathView) findViewById(R.id.atpv_as2);
        rl_content =  findViewById(R.id.rl_content);

        atpvAs.setPathPainter(new FireworksPainter());
        atpvAs.startAnimation(0,1);
        atpv_as2.setPathPainter(new FireworksPainter());
        atpv_as2.startAnimation(0,1);
        runnable = new Runnable() {
            @Override
            public void run() {
                rl_content.setEnabled(false);
                startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                finish();
            }
        };
        handler = new Handler();
        handler.postDelayed(runnable, 1900);
        rl_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacks(runnable);
                startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                finish();
            }
        });

    }


}
