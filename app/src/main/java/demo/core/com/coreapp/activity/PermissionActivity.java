package demo.core.com.coreapp.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.powerrich.corelib.activity.base.BaseActivity;
import com.powerrich.corelib.permission.CcPermissions;
import com.powerrich.corelib.permission.Consumer;
import com.powerrich.corelib.permission.PermissionName;
import com.powerrich.corelib.utils.DialogUtils;
import com.powerrich.corelib.utils.singclick.CustomClickListener;
import com.powerrich.corelib.utils.singclick.SingleClick;

import java.util.ArrayList;
import java.util.List;

import demo.core.com.coreapp.R;


/**
 * /**
 * *                    _ooOoo_
 * *                   o8888888o
 * *                   88" . "88
 * *                   (| -_- |)
 * *                    O\ = /O
 * *                ____/`---'\____
 * *              .   ' \\| |// `.
 * *               / \\||| : |||// \
 * *             / _||||| -:- |||||- \
 * *               | | \\\ - /// | |
 * *             | \_| ''\---/'' | |
 * *              \ .-\__ `-` ___/-. /
 * *           ___`. .' /--.--\ `. . __
 * *        ."" '< `.___\_<|>_/___.' >'"".
 * *       | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * *         \ \ `-. \_ __\ /__ _/ .-` / /
 * * ======`-.____`-.___\_____/___.-`____.-'======
 * *                    `=---='
 * *
 * * .............................................
 * *          佛祖保佑             永无BUG
 */
/*
 * 权限测试Activity
 */
public class PermissionActivity extends BaseActivity implements View.OnClickListener {


    private Button btn_diolog_list, btn_diolog_usual, btn_diolog_singlelist, btn_diolog_multilist,btn_click;
    private Button btn_permission;
    private FloatingActionButton fab;
    private List stringList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_permission);

        initView();
        initData();
    }


    @Override
    public int onCreateContentView() {
        return R.layout.activity_permission;
    }

    private void initView() {
        btn_permission = (Button) findViewById(R.id.btn_permission);
        btn_diolog_list = (Button) findViewById(R.id.btn_diolog_list);
        btn_diolog_singlelist = (Button) findViewById(R.id.btn_diolog_singlelist);
        btn_diolog_multilist = (Button) findViewById(R.id.btn_diolog_multilist);
        btn_click = (Button) findViewById(R.id.btn_click);
        btn_diolog_list = (Button) findViewById(R.id.btn_diolog_list);
        btn_diolog_usual = (Button) findViewById(R.id.btn_diolog_usual);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        btn_diolog_singlelist.setOnClickListener(this);
        btn_diolog_multilist.setOnClickListener(this);
        btn_diolog_list.setOnClickListener(this);
        btn_permission.setOnClickListener(this);
        btn_diolog_usual.setOnClickListener(this);
        btn_click.setOnClickListener(new CustomClickListener() {
            @Override
            protected void onClick() {
                Toast.makeText(mActivity, "点击", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_diolog_list:
                DialogUtils.showBasicListDialog(PermissionActivity.this, "弹出列表", stringList).show();
                break;
            case R.id.btn_diolog_usual:
                DialogUtils.showBasicDialog(this, "弹出常见的", "content",null).show();
                break;
            case R.id.btn_diolog_singlelist:

                DialogUtils.showSingleListDialog(this, "弹出单选", stringList).show();
                break;
            case R.id.btn_diolog_multilist:

                DialogUtils.showMultiListDialog(this, "弹出多选", stringList).show();
                break;
            case R.id.fab:
                startActivity(new Intent(PermissionActivity.this, DemoActivity.class));
                break;

            case R.id.btn_permission:
                CcPermissions.with(PermissionActivity.this)
                        //    .constantRequest()     //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                        //    .permission(PermissionName.SYSTEM_ALERT_WINDOW, PermissionName.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
                        .permission(PermissionName.CAMERA, PermissionName.WRITE_EXTERNAL_STORAGE) //, PermissionName.WRITE_EXTERNAL_STORAGE, PermissionName.READ_CALENDAR
                        .request(new Consumer() {
                            @Override
                            public void accept(List<String> granted, boolean isAll) {
                                // 如果只有一个权限就不用判断了
                                if (isAll) {
                                    Snackbar.make(view, "获取权限成功-hasPermission: " + granted.toString(), Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                } else {
                                    Log.i("jsc", "accept: " + granted.toString());
                                    Snackbar.make(view, "获取权限成功，部分权限未正常授予-hasPermission: " + granted.toString(), Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        });
                break;

        }
    }


    private void initData() {
        stringList = new ArrayList();
        stringList.add("aaa");
        stringList.add("bbb");
        stringList.add("ccc");
        stringList.add("ddd");
        stringList.add("eee");
        stringList.add("fff");
    }
}
