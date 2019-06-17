package com.powerrich.corelib.activity.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.powerrich.corelib.R;
import com.powerrich.corelib.R2;
import com.powerrich.corelib.view.StatusView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/9/28
 * 版权：
 */

public abstract class BaseRefreshActivity<T> extends BaseActivity {

    protected String TAG = BaseRefreshActivity.this.getClass().getSimpleName();




    protected int page = 1;
    protected int pageSize = 10;
    protected BaseQuickAdapter<T, ? extends BaseViewHolder> adapter;
    protected boolean refreshBoo = false;
    protected boolean loadmoreBoo = false;
    //下拉刷新组件
    @BindView(R2.id.base_recycler_view)
    public RecyclerView mRecyclerView;
    @BindView(R2.id.base_status_view)
    public StatusView mStatusView;
    @BindView(R2.id.base_refresh_layout)
    public SmartRefreshLayout mRefreshLayout;


    private List<T> data = new ArrayList<>();


    @SuppressLint("ResourceType")
    @Override
    public int onCreateContentView() {
        return R.layout.activity_base_refresh;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initItemView();
    }

    protected void initItemView() {
        mRefreshLayout.setOnLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshBoo = false;
                loadmoreBoo = true;
                loadData(page, pageSize);
                page++;
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
            }
        });
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                initRefreshBoo();
                loadData(page, pageSize);
                page++;
            }
        });
        adapter = new BaseQuickAdapter<T, BaseViewHolder>(getItemRes(), data) {
            @Override
            protected void convert(BaseViewHolder helper, T item) {
                bindItem(helper, item);
            }
        };

        View headView = addHeadView();
        if (headView != null) {
            adapter.addHeaderView(headView);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mRecyclerView.setAdapter(adapter);
        initRefreshBoo();
        loadData(1, 10);
    }

    /**
     * 重新开始刷新，adapter能够添加新数据
     */
    protected void initRefreshBoo() {
        page = 1;
        refreshBoo = true;
        loadmoreBoo = false;
    }

    //填充数据
    protected void addData(List<T> list) {
        if (adapter == null) {
            mStatusView.showEmpty();
            return;
        }
        if (refreshBoo) {
            if (list == null || list.size() == 0) {
                mStatusView.showEmpty();
                return;
            }
            adapter.setNewData(list);
            mStatusView.showContent(mRecyclerView);
        } else if (loadmoreBoo) {
            adapter.addData(list);
            mStatusView.showContent(mRecyclerView);
        }

    }


    /**
     * 返回当前item的资源文件
     *
     * @return
     */
    protected abstract int getItemRes();

    /**
     * 将数据绑定每一个item
     *
     * @param helper
     * @param item
     */
    protected abstract void bindItem(BaseViewHolder helper, T item);

    /**
     * 刷新后将数据填充进来
     *
     * @param page
     * @param pageSize
     */
    protected abstract void loadData(int page, int pageSize);

    protected View addHeadView() {
        return null;
    }


}
