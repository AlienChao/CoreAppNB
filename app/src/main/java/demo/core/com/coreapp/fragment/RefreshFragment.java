package demo.core.com.coreapp.fragment;

import com.chad.library.adapter.base.BaseViewHolder;
import com.powerrich.corelib.activity.base.BaseActivity;
import com.powerrich.corelib.fragment.base.BaseRefreshFragment;

import demo.core.com.coreapp.R;
import demo.core.com.coreapp.http.ApiServiceManager;
import demo.core.com.coreapp.http.BaseSubscriber;
import demo.core.com.coreapp.http.bean.BannerListBean;
import demo.core.com.coreapp.http.bean.info.BannerInfo;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2018/11/22
 * 版权：
 */

public class RefreshFragment extends BaseRefreshFragment<BannerInfo> {
    @Override
    protected int getItemRes() {
        return R.layout.item_refresh;
    }

    @Override
    protected void bindItem(BaseViewHolder helper, BannerInfo item) {
        helper.setText(R.id.tv1, item.getName());
        helper.setText(R.id.tv2, item.getId() + "");
    }

    @Override
    protected void loadData(final int page, int pageSize) {
        ((BaseActivity) mContext).exeHttp(ApiServiceManager.getApi().getBannerList(page, pageSize))
                .subscribe(new BaseSubscriber<BannerListBean>(mRefreshLayout, mStatusView, page) {
                    @Override
                    public void result(BannerListBean bean) {
                        addData(bean.getData().getItems());
                    }
                });
    }
}
