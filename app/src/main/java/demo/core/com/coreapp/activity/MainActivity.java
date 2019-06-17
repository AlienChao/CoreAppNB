package demo.core.com.coreapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.powerrich.corelib.activity.base.BaseActivity;
import com.powerrich.corelib.utils.singclick.SingleClick;

import butterknife.BindView;
import butterknife.OnClick;
import demo.core.com.coreapp.R;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bt1)
    Button mBt1;
    @BindView(R.id.bt2)
    Button mBt2;
    @BindView(R.id.bt3)
    Button mBt3;
    @BindView(R.id.bt4)
    Button mBt4;
    @BindView(R.id.bt5)
    Button mBt5;

    @SuppressLint("ResourceType")
    @Override
    public int onCreateContentView() {
        setTitleBack("测试用例");
        return R.layout.activity_main;
    }

    @SingleClick
    @OnClick({R.id.bt1, R.id.bt2, R.id.bt3, R.id.bt4, R.id.bt5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                startActivity(new Intent(this, HttpActivity.class));
                break;
            case R.id.bt2:
                startActivity(new Intent(this, RefreshActivity.class));
                break;
            case R.id.bt3:
                //startActivity(new Intent(this, TestActivity.class));
                break;
            case R.id.bt4:
                startActivity(new Intent(this, NavigatorActivity.class));
                break;

            case R.id.bt5:
                startActivity(new Intent(this, PermissionActivity.class));
                break;
        }
    }



}
