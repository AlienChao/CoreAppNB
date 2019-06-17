package com.powerrich.corelib.fragment.base;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.powerrich.corelib.R;
import com.powerrich.corelib.view.StatusView;

import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/26
 * 版权：
 */
public abstract class BaseRecyclerFragment<T> extends BaseFragment{

    RecyclerView mBaseRecyclerView;
    StatusView mBaseStatusView;
    // 行数
    private int row;
    // 列数
    private int cols;
    private List<T> mList;
    private BaseQuickAdapter mAdapter;


    @Override
    protected View onCreateContentView() {
        View view = inflateContentView(R.layout.recyclerview_base);
        mBaseRecyclerView = view.findViewById(R.id.base_recycler_view);
        mBaseStatusView = view.findViewById(R.id.base_status_view);
        loadData();//初始化数据
        return view;
    }


    /**
     * 判断实际的行数和列数
     */
    private void initCount() {
        int size = mList.size();
        int count = row * cols;
        if (size > count) {
            //截取所需要的数据，然后将最后一个修改为更多
            mList = mList.subList(0, count);
        } else if (size == count) {
            //不需截取，整个显示
        } else if (size == 0) {

        } else {
            //数据不够布局显示，那么调整相应的行数
            int c = size / cols;
            if (size % cols == 0) {
                cols = c;
            } else {
                cols = c + 1;
            }
        }
    }

    /**
     * 垂直布局
     * @param data
     */
    protected void initAdapter(List<T> data){
        this.initAdapter(data,0,0,null);
    }

    /**
     * grid布局 有多少条显示多少条
     */
    protected void initAdapter(List<T> data,int cols,BaseQuickAdapter.OnItemClickListener listener){
        this.initAdapter(data,0,cols,listener);
    }

    /**
     * 是grid布局,规定行数和列数，只显示规定多少条
     * @param
     */
    protected void initAdapter(List<T> list,int row,int cols,BaseQuickAdapter.OnItemClickListener listener){

        if(list != null && list.size() >0){
            mBaseStatusView.showContent(mBaseRecyclerView);
        }
        this.mList = list;
        this.row = row;
        this.cols = cols;
        //如果行数=0，表示有多少条显示多少
        if(row > 0){
            initCount();
        }
        mAdapter = new BaseQuickAdapter<T, BaseViewHolder>(getItemRes(), mList) {
            @Override
            protected void convert(BaseViewHolder helper, T item) {
                bindItem(helper, item);
            }
        };
        //如果 列数>0 表示是gridview 布局
        if(cols > 0){
            mBaseRecyclerView.setLayoutManager(new GridLayoutManager(mContext, cols));
        }else{
            mBaseRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mBaseRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        }
        if(listener != null){
            mAdapter.setOnItemClickListener(listener);
        }
        mBaseRecyclerView.setAdapter(mAdapter);

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
     * 加载数据
     */
    protected abstract void loadData();

}
