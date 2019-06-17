package demo.core.com.coreapp.activity;

import com.chad.library.adapter.base.BaseViewHolder;
import com.powerrich.corelib.activity.base.BaseRefreshActivity;

import demo.core.com.coreapp.R;
import demo.core.com.coreapp.http.ApiServiceManager;
import demo.core.com.coreapp.http.BaseSubscriber;
import demo.core.com.coreapp.http.bean.BannerListBean;
import demo.core.com.coreapp.http.bean.info.BannerInfo;

public class RefreshActivity extends BaseRefreshActivity<BannerInfo> {


    @Override
    protected int getItemRes() {
        return R.layout.item_refresh;
    }

    @Override
    protected void bindItem(BaseViewHolder helper, BannerInfo item) {
        helper.setText(R.id.tv1, item.getName());
        helper.setText(R.id.tv2, item.getId()+"");
    }

    @Override
    protected void loadData(final int page, int pageSize) {
        setTitleBack("下拉刷新界面");

        this.exeHttp(ApiServiceManager.getApi().getBannerList(page,pageSize))
                .subscribe(new BaseSubscriber<BannerListBean>(mRefreshLayout,mStatusView,page) {
                    @Override
                    public void result(BannerListBean bean) {
                        addData(bean.getData().getItems());
                    }
                });
    }

}
