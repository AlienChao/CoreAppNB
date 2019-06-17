package com.powerrich.corelib.activity.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.powerrich.corelib.R;
import com.powerrich.corelib.R2;
import com.powerrich.corelib.adapter.BasePagerAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/16
 * 带有指示器 viewpager的activity
 */

public abstract class BaseNavigatorActivity extends BaseActivity {
    @BindView(R2.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R2.id.vp_content)
    ViewPager mVpContent;


    @SuppressLint("ResourceType")
    @Override
    public int onCreateContentView() {
        return R.layout.activity_base_navigator;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    void initView() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        BasePagerAdapter myAdapter = new BasePagerAdapter(supportFragmentManager, getFragments(), getTitles());
        mVpContent.setAdapter(myAdapter);
        mVpContent.setOffscreenPageLimit(getTitles().size() - 1);
        myAdapter.notifyDataSetChanged();
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mVpContent);

        // 默认切换的时候，会有一个过渡动画，设为false后，取消动画，直接显示
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mVpContent.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    protected abstract List<Fragment> getFragments();

    protected abstract List<String> getTitles();



}


