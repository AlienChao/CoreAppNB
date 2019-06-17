package demo.core.com.coreapp.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.powerrich.corelib.activity.base.BaseActivity;

import demo.core.com.coreapp.R;

public class DemoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_demo3);
    }


    @Override
    public int onCreateContentView() {
        return R.layout.activity_demo3;
    }
}
